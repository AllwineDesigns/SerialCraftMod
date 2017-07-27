package com.allwinedesigns.forge.mods.serialcraft;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

public class SerialCraftChatEvent extends Event {
	private String message;
	private EntityPlayer player;
	
    public SerialCraftChatEvent(EntityPlayer p, String str) {
    	player = p;
    	message = str;
    }
    
    public String getMessage() {
        return message;
    }
    
    public EntityPlayer getPlayer() {
    	return player;
    }
}
