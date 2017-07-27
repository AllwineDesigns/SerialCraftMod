package com.allwinedesigns.forge.mods.serialcraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.world.World;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CommonProxy {
    @EventHandler
    public void preinit(FMLPreInitializationEvent event) {
    }
	
    @EventHandler
    public void init(FMLInitializationEvent event) {
        BlockSerialRedstone serialRedstoneBlock = new BlockSerialRedstone();
        BlockSendSerialMessage sendSerialMessageBlock = new BlockSendSerialMessage();

    	GameRegistry.registerTileEntity(TileEntitySerialRedstone.class, "serialRedstone");
    	GameRegistry.registerBlock(serialRedstoneBlock, "serialRedstone");
    	
    	GameRegistry.registerTileEntity(TileEntitySendSerialMessage.class, "sendSerialMessageBlock");
    	GameRegistry.registerBlock(sendSerialMessageBlock, "sendSerialMessageBlock");

        GameRegistry.addRecipe(new ItemStack(serialRedstoneBlock), new Object[]{ 
          "ABA",
          "BCB",
          "ADA",
          'A', Blocks.redstone_block,
          'B', Blocks.redstone_ore,
          'C', Items.clock,
          'D', Items.sign
        });
        GameRegistry.addRecipe(new ItemStack(sendSerialMessageBlock), new Object[]{ 
          "ABA",
          "BCB",
          "ADA",
          'A', Blocks.iron_block,
          'B', Blocks.redstone_ore,
          'C', Items.clock,
          'D', Items.sign
        });

    }
    
    public void openSerialRedstoneGUI(World world, int x, int y, int z) {

    }
    
    public void openConfigGUI() {
    
    }
}
