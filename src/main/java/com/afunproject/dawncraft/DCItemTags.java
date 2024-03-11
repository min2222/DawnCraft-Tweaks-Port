package com.afunproject.dawncraft;

import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DCItemTags {

    public static final TagKey<Item> MASKS = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("masks"));
    public static final TagKey<Item> BARK = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("bark"));
    public static final TagKey<Item> BAT_WING = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("bat_wing"));
    public static final TagKey<Item> BEAR_PELT = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("bear_pelt"));
    public static final TagKey<Item> HONEYCOMB = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("honeycomb"));
    public static final TagKey<Item> RABBIT_FOOT = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("rabbit_foot"));
    public static final TagKey<Item> SPIDER_EYE = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("spider_eye"));
    public static final TagKey<Item> EMERALD = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("emerald"));
    public static final TagKey<Item> LESSER_MONSTER_DROP = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("lesser_monster_drop"));
    public static final TagKey<Item> GREATER_MONSTER_DROP = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("greater_monster_drop"));
    public static final TagKey<Item> MINIBOSS_MONSTER_DROP = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("miniboss_monster_drop"));
    public static final TagKey<Item> VALUABLES = TagKey.create(Registry.ITEM_REGISTRY, Constants.loc("valuables"));
}
