package com.afunproject.dawncraft.integration.dcmobs;

import net.mcreator.simplemobs.init.SimpleMobsModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SimpleMobsCompat {

    public static void addItemsToCreative(NonNullList<ItemStack> items) {
        for (RegistryObject<Item> reg : SimpleMobsModItems.REGISTRY.getEntries()) {
            Item item = reg.get();
            if (!(item instanceof BlockItem) && ForgeRegistries.ITEMS.getKey(item).getNamespace().equals("simple_mobs") &!
            		ForgeRegistries.ITEMS.getKey(item).getPath().contains("spawn_egg")) items.add(new ItemStack(item));
        }
    }

}
