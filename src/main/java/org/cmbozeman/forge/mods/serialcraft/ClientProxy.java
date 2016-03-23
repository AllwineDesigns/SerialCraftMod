package org.cmbozeman.forge.mods.serialcraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	private static MovementController movementController;
	private static SerialCraftListener serialCraftListener;
	private static ClientState clientState;
	
	public static ClientState getClientState() {
		return clientState;
	}
	
	public static MovementController getMovementController() {
	    return movementController;
	}
	
	public static SerialCraftListener getSerialCraftListener() {
		return serialCraftListener;
	}
	
	@Override
    public void preinit(FMLPreInitializationEvent event) {
		super.preinit(event);
		
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		KeyBindings.init();
	}
	
	
    @Override
    public void init(FMLInitializationEvent event) {
    	super.init(event);
    	    	
    	SerialCraftClientEventHandling events = new SerialCraftClientEventHandling();
    	
    	MinecraftForge.EVENT_BUS.register(events);
    	FMLCommonHandler.instance().bus().register(events);
    	serialCraftListener = new SerialCraftListener();
    	movementController = new MovementController();
    	clientState = new ClientState();
    }
    
    @Override
    public void openSerialRedstoneGUI(World world, int x, int y, int z) {
    	if(world.isRemote) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiSerialRedstone(world.getTileEntity(x, y, z))); 
    	}
    }
    
    @Override
    public void openConfigGUI() {
        Minecraft.getMinecraft().displayGuiScreen(new ConfigGUI());
    }

}
