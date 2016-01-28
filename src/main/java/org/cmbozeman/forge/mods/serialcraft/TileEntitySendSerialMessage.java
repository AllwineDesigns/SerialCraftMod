package org.cmbozeman.forge.mods.serialcraft;


import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySendSerialMessage extends TileEntity
{
	private String serialMessage = "message";
	private int powerLevel = 0;
	
	public int getPowerLevel() {
		return powerLevel;
	}
	
	public void setPowerLevel(int p) {
		powerLevel = p;
	}
	
    public String getSerialMessage() {
    	return serialMessage;
    }

    public void setSerialMessage(String m) {
    	serialMessage = m;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound var1) {
	    var1.setString("serialMessage", this.serialMessage);
	    super.writeToNBT(var1);
	}
    
    @Override
    public void readFromNBT(NBTTagCompound var1) {
	    this.serialMessage = var1.getString("serialMessage");
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
        this.serialMessage = pkt.func_148857_g().getString("serialMessage");
    }
    
    @Override
    public boolean receiveClientEvent(int action, int param) {
    	if(this.worldObj.isRemote) {
    	    System.out.println("client received " + action + " " + param);
    	    // TODO make minecraft forge event that is posted to the event bus
    	    if(ClientProxy.getSerialCraftListener() != null) {
    	        ClientProxy.getSerialCraftListener().sendSerialMessage(" " + param + " ");
    	    } else {
    	        System.out.println("serialCraftListener not initialized correctly");
    	    }
    	}
    	return true;
    }

}