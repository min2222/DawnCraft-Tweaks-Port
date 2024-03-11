package com.afunproject.dawncraft.effects;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

public class DCEffect extends MobEffect {

	protected DCEffect(int colour) {
		super(MobEffectCategory.HARMFUL, colour);
	}

	@Override
	public List<ItemStack> getCurativeItems() {
		return Lists.newArrayList();
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable(getDescriptionId()).withStyle(Style.EMPTY.withColor(getColor()));
	}

}
