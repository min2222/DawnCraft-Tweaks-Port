package com.afunproject.dawncraft.dungeon.item;

import java.util.List;

import javax.annotation.Nullable;

import com.afunproject.dawncraft.CreativeTabs;
import com.afunproject.dawncraft.dungeon.KeyColour;
import com.afunproject.dawncraft.dungeon.block.LockedBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;

public class KeyItem extends Item implements AdventureItem {

	private final KeyColour colour;

	public KeyItem(KeyColour colour) {
		super(new Properties().tab(CreativeTabs.DUNGEON_ITEMS));
		this.colour = colour;
	}

	public KeyColour getColour() {
		return colour;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
		LockItem lock = DungeonItems.getLock(colour);
		lines.add(Component.translatable("tooltip.dawncraft.key_0", lock.getName(new ItemStack(lock))));
		lines.add(Component.translatable("tooltip.dawncraft.key_1"));
	}

	@Override
	public MutableComponent getName(ItemStack stack) {
		MutableComponent component = ((MutableComponent)super.getName(stack));
		return component.withStyle(component.getStyle().withColor(colour.getColour()));
	}

	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Level level = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		BlockStateBase state = level.getBlockState(pos);
		if (state.getBlock() instanceof LockedBlock) {
			LockedBlock block = (LockedBlock) state.getBlock();
			if (block.getColour(level, pos, state) == colour) {
				if (block.open(level, pos, state, true)) {
					level.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS, 0.75f, 0, false);
					if (!ctx.getPlayer().isCreative()) ctx.getItemInHand().shrink(1);
					return InteractionResult.CONSUME;
				}
			}
		}
		return super.useOn(ctx);
	}

}
