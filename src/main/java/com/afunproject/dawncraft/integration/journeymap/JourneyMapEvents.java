package com.afunproject.dawncraft.integration.journeymap;

import java.util.function.Supplier;

import com.afunproject.dawncraft.event.DCSubCommandsEvent;
import com.afunproject.dawncraft.integration.journeymap.client.JourneyMapPlugin;
import com.afunproject.dawncraft.integration.journeymap.network.AddWaypointMessage;
import com.afunproject.dawncraft.network.DCNetworkHandler;
import com.afunproject.dawncraft.network.NetworkUtils;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.datafixers.util.Pair;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public class JourneyMapEvents {

	public static void init() {
		MinecraftForge.EVENT_BUS.register(new JourneyMapEvents());
		NetworkUtils.registerMessage(DCNetworkHandler.NETWORK_INSTANCE, 5, AddWaypointMessage.class, JourneyMapEvents::receivePacket);
	}
	
	private static final DynamicCommandExceptionType ERROR_STRUCTURE_INVALID = new DynamicCommandExceptionType((p_207534_) -> {
		return Component.translatable("commands.locate.structure.invalid", p_207534_);
	});

	@SubscribeEvent
	public void registerSubCommands(DCSubCommandsEvent event) {
		event.addSubCommand(Commands.literal("addWaypoint").requires(stack -> stack.hasPermission(2))
				.then(Commands.argument("player", EntityArgument.players()).then(Commands.argument("structure", ResourceOrTagLocationArgument.resourceOrTag(Registry.CONFIGURED_FEATURE_REGISTRY))
						.then(Commands.argument("name", StringArgumentType.string()).executes(this::addWaypoint)))));
	}

	private int addWaypoint(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
		ResourceOrTagLocationArgument.Result<Structure> result = ResourceOrTagLocationArgument.getRegistryType(ctx, "structure", Registry.STRUCTURE_REGISTRY, ERROR_STRUCTURE_INVALID);
		ServerLevel level = ctx.getSource().getLevel();
		Registry<Structure> registry = level.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY);
		HolderSet<Structure> holderset = result.unwrap().map(rk ->
				registry.getHolder(rk).map(HolderSet::direct), registry::getTag)
				.orElseThrow(new SimpleCommandExceptionType(Component.translatable("commands.locate.invalid", result.asPrintable()))::create);
		Pair<BlockPos, Holder<Structure>> pair = level.getChunkSource().getGenerator()
				.findNearestMapStructure(level, holderset, new BlockPos(ctx.getSource().getPosition()), 100, false);
		if (pair == null) throw new SimpleCommandExceptionType(Component.translatable("commands.locate.failed", result.asPrintable())).create();
		for (ServerPlayer player : EntityArgument.getPlayers(ctx, "player"))
			addWaypoint(pair.getFirst(), StringArgumentType.getString(ctx, "name"), player);
		return 1;
	}

	public static void addWaypoint(BlockPos pos, String name, ServerPlayer player) {
		DCNetworkHandler.NETWORK_INSTANCE.sendTo(new AddWaypointMessage(player.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos), name),
				player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}

	private static void receivePacket(AddWaypointMessage message, Supplier<NetworkEvent.Context> supplier) {
		supplier.get().enqueueWork(() -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () ->
				() -> JourneyMapPlugin.getInstance().addWaypoint(message, true)));
	}

}
