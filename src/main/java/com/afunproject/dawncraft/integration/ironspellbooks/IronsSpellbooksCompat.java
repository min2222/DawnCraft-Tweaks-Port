package com.afunproject.dawncraft.integration.ironspellbooks;

import io.redspace.ironsspellbooks.api.spells.LegacySpellBookData;
import io.redspace.ironsspellbooks.api.spells.LegacySpellData;
import net.minecraft.world.item.ItemStack;

public class IronsSpellbooksCompat {
    
    public static boolean isSpellBook(ItemStack stack) {
        return LegacySpellData.hasSpellData(stack) || LegacySpellBookData.getSpellBookData(stack).spellCount > 0;
    }
}
