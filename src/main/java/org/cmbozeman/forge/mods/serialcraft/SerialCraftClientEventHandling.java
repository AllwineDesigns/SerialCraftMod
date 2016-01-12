package org.cmbozeman.forge.mods.serialcraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.entity.EntityClientPlayerMP;

// Minecraft client side event handing
public class SerialCraftClientEventHandling {
    @SubscribeEvent
    public void onChatEvent(SerialCraftChatEvent event) {
    	if(event.getPlayer() != null) {
    		if(event.getPlayer() instanceof EntityClientPlayerMP) {
    	        ((EntityClientPlayerMP)event.getPlayer()).sendChatMessage("/" + event.getMessage());
    		}
    	}
    }
    
    @SubscribeEvent
    public void onRedstoneEvent(SerialCraftRedstoneEvent event) {
    	System.out.println("in OnRedstoneEvent");
    	if(event.getPlayer() != null) {
    		System.out.println("player is not null");
    		System.out.println(event.getPower());
    		System.out.println(event.getID());
    	    SerialCraft.network.sendToServer(new SerialRedstoneMessage(event.getPower(), event.getID()));
    	}
    }
    
    @SubscribeEvent
    public void onTimeEvent(SerialCraftTimeEvent event) {
    	if(event.getPlayer() != null) {
    		if(event.getPlayer() instanceof EntityClientPlayerMP) {
    	        ((EntityClientPlayerMP)event.getPlayer()).sendChatMessage("/" + event.getMessage());
    		}
    	}
    }
    
   // @SubscribeEvent
   // public void onSendSerialMessage(SerialCraftSenderSerialMessageEvent event) {
   // 	
   // }
}
