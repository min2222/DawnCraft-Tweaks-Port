package com.afunproject.dawncraft.dungeon.block.entity.interfaces;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public interface Disguisable {

	public ResourceLocation getTexture();

	public default void setTexture(Block block) {
		setTexture(ForgeRegistries.BLOCKS.getKey(block));
	}

	public void setTexture(ResourceLocation block);

}
