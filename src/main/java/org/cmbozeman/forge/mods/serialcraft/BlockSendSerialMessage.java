package com.allwinedesigns.forge.mods.serialcraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSendSerialMessage extends BlockContainer
{
    public BlockSendSerialMessage()
    {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setBlockName("sendSerialMessageBlock");
        this.setBlockTextureName("serialcraft:sendSerialMessageBlock");
    }
    

    @Override()
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack) {
        super.onBlockPlacedBy(world, x, y, z, entity, itemstack);
    	SerialCraft.proxy.openSerialRedstoneGUI(world, x, y, z);
    }

    
    @Override()
    public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {

    	if(!world.isRemote) {
    		TileEntity te = world.getTileEntity(x, y, z);
    		if(te instanceof TileEntitySendSerialMessage) {

    			TileEntitySendSerialMessage ssm = (TileEntitySendSerialMessage)te;
    			int power = world.getBlockPowerInput(x, y, z);
    			if(ssm.getPowerLevel() != power) {
	                world.scheduleBlockUpdate(x, y, z, this, 4);
    			}
    		}
    	}
    }


/**
* Ticks the block if it's been scheduled
*/
    public void updateTick(World world, int x, int y, int z, Random ran) {

        if (!world.isRemote) { 

    		TileEntity te = world.getTileEntity(x, y, z);
    		if(te instanceof TileEntitySendSerialMessage) {
    			TileEntitySendSerialMessage ssm = (TileEntitySendSerialMessage)te;
    			int power = world.getBlockPowerInput(x, y, z);
    			if(ssm.getPowerLevel() != power) {
	                ssm.setPowerLevel(power);
	                world.addBlockEvent(x, y, z, this, 0, power);
    			}
    		}
        }
    }



    public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
    {
        return 0;
    }


    /**
     * Can this block provide power. Only wire currently seems to have this change based on its state.
     */
    public boolean canProvidePower()
    {
        return false;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntitySendSerialMessage();
    }

}
