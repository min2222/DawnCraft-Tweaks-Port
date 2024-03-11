package com.afunproject.dawncraft.dungeon.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class BlockItemDungeon extends BlockItem {

	private final String name;

	public BlockItemDungeon(Block block, String name, CreativeModeTab tab) {
		super(block, new Properties().tab(tab));
		this.name = name;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
		lines.add(Component.translatable("tooltip.dawncraft."+name));
	}

}
