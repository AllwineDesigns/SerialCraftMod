package com.allwinedesigns.forge.mods.serialcraft;

import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.monster.EntityCreeper;

// Minecraft client side event handing
public class SerialCraftClientEventHandling {
    @SubscribeEvent
    public void onChatEvent(SerialCraftChatEvent event) {
    	if(event.getPlayer() != null) {
    		if(event.getPlayer() instanceof EntityClientPlayerMP) {
    	        ((EntityClientPlayerMP)event.getPlayer()).sendChatMessage(event.getMessage());
    		}
    	}
    }
    
    @SubscribeEvent
    public void onRedstoneEvent(SerialCraftRedstoneEvent event) {
    	//System.out.println("in OnRedstoneEvent");
    	if(event.getPlayer() != null) {
    		//System.out.println("player is not null");
    		//System.out.println(event.getPower());
    		//System.out.println(event.getID());
    	    SerialCraft.network.sendToServer(new SerialRedstoneMessage(event.getPower(), event.getID()));
    	}
    }
    
    @SubscribeEvent
    public void onTimeEvent(SerialCraftTimeEvent event) {
    	if(event.getPlayer() != null) {
    		if(event.getPlayer() instanceof EntityClientPlayerMP) {
    	        ((EntityClientPlayerMP)event.getPlayer()).sendChatMessage("/time " + event.getMessage());
    		}
    	}
    }
    
    @SubscribeEvent
    public void onTickEvent(ClientTickEvent event) {
    	if (!Minecraft.getMinecraft().isGamePaused() && Minecraft.getMinecraft().thePlayer != null) {
    		SerialCraftListener serial = ClientProxy.getSerialCraftListener();
    		ClientState state = ClientProxy.getClientState();
    		EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
    		List entities = Minecraft.getMinecraft().theWorld.loadedEntityList;
    		int closestCreeper = 255;
    		for(int i = 0; i < entities.size(); i++) {
    			if(entities.get(i) instanceof EntityCreeper) {
    				int dist = (int)player.getDistanceToEntity((EntityCreeper)entities.get(i));
    				if(dist < closestCreeper) {
    					closestCreeper = dist;
    				}

    			}
    		}
    		//System.out.println("closest creeper: " + closestCreeper);
    		if(state.setDistanceToCreeper(closestCreeper)) {
    			serial.sendCreeperMessage(state.getDistanceToCreeper());
    		}
    		if(state.setHealth(player.getHealth())) {
    			serial.sendHealthMessage((int)player.getHealth());
    		}    
    		
    		if(state.setFoodLevel(player.getFoodStats().getFoodLevel())) {
    			serial.sendFoodLevelMessage(state.getFoodLevel());
    		}
    		
    		if(state.setAir(player.getAir())) {
    			serial.sendAirMessage(player.getAir());
    		}
    		
    	}
    }
    
   // @SubscribeEvent
   // public void onSendSerialMessage(SerialCraftSenderSerialMessageEvent event) {
   // 	
   // }
}
