package org.cmbozeman.forge.mods.serialcraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = SerialCraft.MODID, version = SerialCraft.VERSION)
public class SerialCraft {
	
	 @Mod.Instance(SerialCraft.MODID)
     public static SerialCraft instance;
	 
    public static final String MODID = "SerialCraft";
    public static final String VERSION = "1.7.10-0.3-dev";
    
	public static SimpleNetworkWrapper network;
	
    
    @SidedProxy(
    	      clientSide="org.cmbozeman.forge.mods.serialcraft.ClientProxy", 
    	      serverSide="org.cmbozeman.forge.mods.serialcraft.CommonProxy"
    	    )
    public static CommonProxy proxy;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
    	proxy.preinit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	proxy.init(event);
    	
    	//System.out.println("in init on SerialCraft");
    	
    	network = NetworkRegistry.INSTANCE.newSimpleChannel("MyChannel");
    	network.registerMessage(SerialRedstoneMessage.Handler.class, SerialRedstoneMessage.class, 0, Side.SERVER);
        network.registerMessage(SerialRedstoneIDMessage.Handler.class, SerialRedstoneIDMessage.class, 1, Side.SERVER);
    }
}
