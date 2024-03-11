package com.afunproject.dawncraft.integration.champions;

import net.minecraft.world.entity.Entity;

public class ChampionsIntegration {
    public static boolean isChampion(Entity entity) {
    	//TODO
        /*LazyOptional<IChampion> optional = ChampionCapability.getCapability(entity);
        if (!optional.isPresent()) return false;
        IChampion champion = optional.orElse(null);
        return champion.getServer().getRank().isPresent() ? champion.getServer().getRank().get().getTier() > 0 : false;*/
    	return false;
    }
    
}
