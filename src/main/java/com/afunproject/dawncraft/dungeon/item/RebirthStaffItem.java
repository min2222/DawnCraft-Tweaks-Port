package com.afunproject.dawncraft.dungeon.item;

import java.util.List;

import javax.annotation.Nullable;

import com.afunproject.dawncraft.CreativeTabs;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class RebirthStaffItem extends Item {

	public RebirthStaffItem() {
		super(new Properties().stacksTo(1).tab(CreativeTabs.DUNGEON_ITEMS).rarity(Rarity.RARE));
	}

	@Override
	public Component getName(ItemStack stack) {
		MutableComponent component = ((MutableComponent)super.getName(stack));
		return isPowered(stack)? component.withStyle(ChatFormatting.LIGHT_PURPLE) : component;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flag) {
		boolean isPowered = isPowered(stack);
		String text = "tooltip.dawncraft.rebirth_staff." + (isPowered ? "powered_" : "inert_");
		lines.add(Component.translatable(text+"0").withStyle(isPowered ? ChatFormatting.LIGHT_PURPLE : ChatFormatting.AQUA));
		lines.add(Component.translatable(text+"1").withStyle(Style.EMPTY.withItalic(true)));
	}

	@Override
	public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
		if (this.allowedIn(tab)) {
			items.add(new ItemStack(this));
			items.add(createPowered());
		}
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return super.isFoil(stack) || isPowered(stack);
	}

	public static boolean isPowered(ItemStack stack) {
		if (!stack.hasTag()) return false;
		CompoundTag tag = stack.getOrCreateTag();
		if (tag.contains("powered")) {
			if (tag.getBoolean("powered")) return true;
		}
		return false;
	}

	public static ItemStack createPowered() {
		return createPowered(new ItemStack(DungeonItems.REBIRTH_STAFF.get()));
	}

	public static ItemStack createPowered(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.putBoolean("powered", true);
		stack.setTag(tag);
		return stack;
	}

}
