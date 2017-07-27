package com.allwinedesigns.forge.mods.serialcraft;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

public class SerialCraftRedstoneEvent extends Event {
	private int power;
	private String id;
	private EntityPlayer player;
	
    public SerialCraftRedstoneEvent(EntityPlayer p, int pow) {
    	player = p;
    	power = pow;
    	id = "default";
    }
	
    public SerialCraftRedstoneEvent(EntityPlayer p, int pow, String i) {
    	player = p;
    	power = pow;
    	id = i;
    }
    
    public String getID() {
    	return id;
    }
    
    public int getPower() {
        return power;
    }
    
    public EntityPlayer getPlayer() {
    	return player;
    }
}
