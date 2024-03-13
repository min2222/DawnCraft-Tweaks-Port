package com.afunproject.dawncraft.dungeon.block.entity;

import com.afunproject.dawncraft.Constants;
import com.afunproject.dawncraft.dungeon.block.DungeonBlocks;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DungeonBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Constants.MODID);

	public static final RegistryObject<BlockEntityType<DungeonDoorBlockEntity>> DUNGEON_DOOR = BLOCK_ENTITIES.register("dungeon_door",  () -> BlockEntityType.Builder.of(DungeonDoorBlockEntity::new,
			DungeonBlocks.FIRE_DOOR.get(), DungeonBlocks.RUST_DOOR.get(), DungeonBlocks.SAND_DOOR.get(), DungeonBlocks.STONE_DOOR.get(), DungeonBlocks.WOOD_DOOR.get()).build(null));
	public static final RegistryObject<BlockEntityType<ChestSpawnerBlockEntity>> CHEST_SPAWNER = BLOCK_ENTITIES.register("chest_spawner", () -> BlockEntityType.Builder.of(
			ChestSpawnerBlockEntity::new, DungeonBlocks.CHEST_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<RedstoneTriggerBlockEntity>> REDSTONE_TRIGGER = BLOCK_ENTITIES.register("redstone_trigger", () -> BlockEntityType.Builder.of(
			RedstoneTriggerBlockEntity::new, DungeonBlocks.REDSTONE_TRIGGER.get()).build(null));
	public static final RegistryObject<BlockEntityType<RedstoneActivatorBlockEntity>> REDSTONE_ACTIVATOR = BLOCK_ENTITIES.register("redstone_activator", () -> BlockEntityType.Builder.of(
			RedstoneActivatorBlockEntity::new, DungeonBlocks.REDSTONE_ACTIVATOR.get()).build(null));
}
