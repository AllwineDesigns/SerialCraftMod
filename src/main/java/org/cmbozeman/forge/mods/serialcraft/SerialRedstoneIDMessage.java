package org.cmbozeman.forge.mods.serialcraft;

import java.util.List;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SerialRedstoneIDMessage implements IMessage {
	private int x;
	private int y;
	private int z;
	private String id;
	
	
	public SerialRedstoneIDMessage() { }

	public SerialRedstoneIDMessage(int xx, int yy, int zz, String i) {
		x = xx;
		y = yy;
		z = zz;
		id = i;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public String getID() {
		return id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt(); 
		y = buf.readInt();
		z = buf.readInt();
		id = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		ByteBufUtils.writeUTF8String(buf, id);
	}

	public static class Handler implements IMessageHandler<SerialRedstoneIDMessage, IMessage> {

		@Override
		public IMessage onMessage(SerialRedstoneIDMessage message, MessageContext ctx) {
			//System.out.println("setting id for redstone serial block " + message.getX() + ", " + message.getY() + ", " + message.getZ() + " - " + message.getID());
			
			World world = ctx.getServerHandler().playerEntity.worldObj;
			TileEntity te = world.getTileEntity(message.getX(), message.getY(),  message.getZ());
			if(te instanceof TileEntitySerialRedstone) {
				TileEntitySerialRedstone sr = (TileEntitySerialRedstone)te;
				if(sr.getID().equals("default")) {
			        sr.setID(message.getID());
			        world.markBlockForUpdate(message.getX(), message.getY(), message.getZ());
				}
			}
			return null; // no response in this case
		}

	        // or in 1.8:
	  //      @Override
	  //      public IMessage onMessage(MyMessage message, MessageContext ctx) {
	  //          IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.worldObj; // or Minecraft.getMinecraft() on the client
	  //          mainThread.addScheduledTask(new Runnable() {
	  //              @Override
	  //              public void run() {
	  //                  System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
	  //              }
	  //          });
	  //          return null; // no response in this case
	  //      }
	}

}

