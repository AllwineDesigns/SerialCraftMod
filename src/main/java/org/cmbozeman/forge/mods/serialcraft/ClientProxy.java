package org.cmbozeman.forge.mods.serialcraft;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	public static MovementController movementController;
	
    @Override
    public void init(FMLInitializationEvent event) {
    	super.init(event);
    	
    	System.out.println("in init on ClientProxy");
    	
    	SerialCraftClientEventHandling events = new SerialCraftClientEventHandling();
    	
    	MinecraftForge.EVENT_BUS.register(events);
    	SerialCraft.serialCraftListener = new SerialCraftListener();
    	movementController = new MovementController();
    }
    
    @Override
    public void openSerialRedstoneGUI(World world, int x, int y, int z) {
    	if(world.isRemote) {
        	System.out.println("in openSerialRedstoneGUI on ClientProxy");

            Minecraft.getMinecraft().displayGuiScreen(new GuiSerialRedstone((TileEntitySerialRedstone)world.getTileEntity(x, y, z))); 
    	}
    }

}
