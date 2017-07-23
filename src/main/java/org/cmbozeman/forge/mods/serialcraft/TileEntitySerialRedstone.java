package org.cmbozeman.forge.mods.serialcraft;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySerialRedstone extends TileEntity
{
	private int redstonePower = 0;
	private String redstoneID = "default";

    public int getRedstonePower() {
    	return redstonePower;
    }
    
    public void setRedstonePower(int r) {
    	redstonePower = r;
    }
    
    public String getRedstoneID() {
        return redstoneID;
    }
    
    public void setID(String i) {
    	redstoneID = i;
    }

    public String getID() {
    	return redstoneID;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound var1) {
	    var1.setInteger("redstonePower", this.redstonePower);
	    var1.setString("redstoneID", this.redstoneID);
	    super.writeToNBT(var1);
	}
    
    @Override
    public void readFromNBT(NBTTagCompound var1) {
	    this.redstonePower = var1.getInteger("redstonePower");
	    this.redstoneID = var1.getString("redstoneID");
	    super.readFromNBT(var1);
	}
    
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        writeToNBT(tagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, tagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        this.redstonePower = pkt.func_148857_g().getInteger("redstonePower");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

}
