package com.afunproject.dawncraft.integration.journeymap.client;

import java.util.EnumSet;
import java.util.List;

import com.afunproject.dawncraft.Constants;
import com.afunproject.dawncraft.integration.epicfight.client.KeyToast;
import com.afunproject.dawncraft.integration.journeymap.network.AddWaypointMessage;
import com.google.common.collect.Lists;

import journeymap.client.JourneymapClient;
import journeymap.client.api.ClientPlugin;
import journeymap.client.api.IClientAPI;
import journeymap.client.api.IClientPlugin;
import journeymap.client.api.display.Waypoint;
import journeymap.client.api.display.WaypointGroup;
import journeymap.client.api.event.ClientEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

@ClientPlugin
public class JourneyMapPlugin implements IClientPlugin {

    private static final WaypointGroup GROUP = new WaypointGroup(Constants.MODID, "structures");
    private static JourneyMapPlugin instance;
    private IClientAPI api;
    private List<AddWaypointMessage> waypoints = Lists.newArrayList();
    private KeyToast toast;

    private boolean startedMapping;

    public JourneyMapPlugin() {
        instance = this;
    }

    @Override
    public void initialize(IClientAPI api) {
        this.api = api;
        api.subscribe(Constants.MODID, EnumSet.of(ClientEvent.Type.MAPPING_STARTED, ClientEvent.Type.DISPLAY_UPDATE));
    }

    @Override
    public String getModId() {
        return Constants.MODID;
    }

    @Override
    public void onEvent(ClientEvent clientEvent) {
        if (clientEvent.type == ClientEvent.Type.MAPPING_STARTED) {
            startedMapping = true;
            for (AddWaypointMessage message : waypoints) addWaypoint(message, false);
            waypoints.clear();
        }
        if (clientEvent.type == ClientEvent.Type.DISPLAY_UPDATE) if (toast != null) toast.setPressed();
    }

    public void addWaypoint(AddWaypointMessage message, boolean sendMessage) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (sendMessage) player.sendSystemMessage(Component.translatable("message.dawncraft.waypoint", message.getStructure()));
        if (startedMapping) {
            try {
                String name = Component.translatable(message.getStructure()).getString();
                BlockPos pos = message.getPos();
                ResourceKey<Level> dim = player.level.dimension();
                Waypoint waypoint = new Waypoint(Constants.MODID, name, dim, pos);
                waypoint.setColor(0xFFFFFF);
                waypoint.setGroup(GROUP);
                api.show(waypoint);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else waypoints.add(message);
    }

    public static JourneyMapPlugin getInstance() {
        return instance;
    }

    public void displayToast(ToastComponent toasts) {
        toast = new KeyToast("toasts.dawncraft.map", JourneymapClient.getInstance().getKeyEvents().getHandler().kbFullscreenToggle, Items.FILLED_MAP);
        toasts.addToast(toast);
    }

}
