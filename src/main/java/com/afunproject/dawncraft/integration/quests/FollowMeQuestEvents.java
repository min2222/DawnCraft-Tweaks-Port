package com.afunproject.dawncraft.integration.quests;

import java.util.Optional;

import com.afunproject.dawncraft.Constants;
import com.afunproject.dawncraft.ModUtils;
import com.afunproject.dawncraft.capability.DCCapabilities;
import com.afunproject.dawncraft.capability.FollowQuest;
import com.afunproject.dawncraft.integration.quests.task.FollowTask;
import com.feywild.quest_giver.entity.QuestGuardVillager;
import com.feywild.quest_giver.quest.player.QuestData;
import com.feywild.quest_giver.quest.task.TaskTypes;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.smileycorp.followme.common.FollowHandler;
import net.smileycorp.followme.common.FollowMe;
import net.smileycorp.followme.common.ai.FollowUserGoal;
import net.smileycorp.followme.common.capability.IFollower;

public class FollowMeQuestEvents {

	public FollowMeQuestEvents() {
		TaskTypes.register(Constants.loc("follow_quest"), FollowTask.INSTANCE);
	}

	@SubscribeEvent
	public void entityTick(LivingTickEvent event) {
		if (event.getEntity() instanceof Mob) {;
		Mob entity = (Mob) event.getEntity();
		if (entity.level instanceof ServerLevel) {
			LazyOptional<FollowQuest> questOptional = entity.getCapability(DCCapabilities.FOLLOW_QUEST);
			if (questOptional.isPresent()) {
				FollowQuest questCap = questOptional.resolve().get();
				if (questCap.hasStructure()) {
					LazyOptional<IFollower> followOptional = entity.getCapability(FollowMe.FOLLOW_CAPABILITY);
					if (followOptional.isPresent()) {
						IFollower followCap = followOptional.resolve().get();
						LivingEntity followedEntity = followCap.getFollowedEntity();
						if (followedEntity instanceof ServerPlayer) {
							if (isInStructure(entity.blockPosition(), (ServerLevel)entity.level, questCap.getStructure())) {
								QuestData quests = QuestData.get((ServerPlayer) followedEntity);
								if (quests.checkComplete(FollowTask.INSTANCE, questCap.getStructure())) {
									//remove follow ai
									for (WrappedGoal entry : entity.goalSelector.getRunningGoals().toArray(WrappedGoal[]::new)) {
										if (entry.getGoal() instanceof FollowUserGoal) {
											FollowUserGoal task = (FollowUserGoal) entry.getGoal();
											if (task.getUser() == followedEntity) {
												FollowHandler.removeAI(task);
											}
											break;
										}
									};

									//remove capabilities
									followCap.setForcedToFollow(false);
									questCap.setStructure(null);
								}
							} else if (questCap.getStructure().equals("#minecraft:village")  &! entity.level.getEntitiesOfClass(QuestGuardVillager.class, entity.getBoundingBox().inflate(10)).isEmpty()) {
								QuestData quests = QuestData.get((ServerPlayer) followedEntity);
								if (quests.checkComplete(FollowTask.INSTANCE, questCap.getStructure())) {
									//remove follow ai
									for (WrappedGoal entry : entity.goalSelector.getRunningGoals().toArray(WrappedGoal[]::new)) {
										if (entry.getGoal() instanceof FollowUserGoal) {
											FollowUserGoal task = (FollowUserGoal) entry.getGoal();
											if (task.getUser() == followedEntity) {
												FollowHandler.removeAI(task);
											}
											break;
										}
									};

									//remove capabilities
									followCap.setForcedToFollow(false);
									questCap.setStructure(null);
								}
							}
						}
					}
				}
			}
		}
		}
	}

	private static boolean isInStructure(BlockPos pos, ServerLevel level, String structure) {
		if (structure.contains("#")) return isInStructureTag(pos, level , structure.replace("#", ""));
		if (!ModUtils.isValidResourceLocation(structure)) return false;
		Registry<Structure> registry = level.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
		ResourceKey<Structure> structureKey = ResourceKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(structure));
		Optional<Holder<Structure>> structureOptional = registry.getHolder(structureKey);
		if (structureOptional.isPresent()) {
			Pair<BlockPos, Holder<Structure>> pair = level.getChunkSource().getGenerator()
					.findNearestMapStructure(level, HolderSet.direct(structureOptional.get()), pos, 1, false);
			if (pair == null) return false;
			BlockPos villagePos = pair.getFirst();
			if (Math.abs(villagePos.getY() - pos.getY()) > 15)
				return Math.pow(villagePos.getX()-pos.getX(), 2) + Math.pow(villagePos.getZ()-pos.getZ(), 2) <= 6400;
		} return false;
	}

	private static boolean isInStructureTag(BlockPos pos, ServerLevel level, String structure) {
		if (!ModUtils.isValidResourceLocation(structure)) return false;
		Registry<Structure> registry = level.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
		TagKey<Structure> structureTag = TagKey.create(Registry.STRUCTURE_REGISTRY, new ResourceLocation(structure));
		Optional<HolderSet.Named<Structure>> structureOptional = registry.getTag(structureTag);
		if (structureOptional.isPresent()) {
			Pair<BlockPos, Holder<Structure>> pair = level.getChunkSource().getGenerator()
					.findNearestMapStructure(level, structureOptional.get(), pos, 1, false);
			if (pair == null) return false;
			BlockPos villagePos = pair.getFirst();
			return Math.pow(villagePos.getX()-pos.getX(), 2) + Math.pow(villagePos.getZ()-pos.getZ(), 2)<=1024;
		} return false;
	}
}
