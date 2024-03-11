package com.afunproject.dawncraft.dungeon.item;

import java.util.List;

import javax.annotation.Nullable;

import com.afunproject.dawncraft.CreativeTabs;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class MaskOfAthoraItem extends MaskItem {

	public MaskOfAthoraItem() {
		super(new Properties().tab(CreativeTabs.DUNGEON_ITEMS).stacksTo(1).rarity(Rarity.EPIC));
	}

	@Override
	public Component getName(ItemStack stack) {
		MutableComponent component = ((MutableComponent)super.getName(stack));
		return component.withStyle(component.getStyle().withColor(0xFF9300).withBold(true));
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
		lines.add(Component.translatable("tooltip.dawncraft.cursed_mask_0").withStyle(Style.EMPTY.withItalic(true)));
		lines.add(Component.translatable("tooltip.dawncraft.cursed_mask_1").withStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
	}

}
