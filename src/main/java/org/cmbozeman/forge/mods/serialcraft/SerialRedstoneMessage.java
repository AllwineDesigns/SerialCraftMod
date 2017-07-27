package com.allwinedesigns.forge.mods.serialcraft;

import java.util.List;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SerialRedstoneMessage implements IMessage {
	private int power;
	private String id;
	
	public SerialRedstoneMessage() { }

	public SerialRedstoneMessage(int p, String i) {
		this.power = p;
		this.id = i;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.power = buf.readInt(); 
		this.id = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(power);
		ByteBufUtils.writeUTF8String(buf, id);
	}

	public static class Handler implements IMessageHandler<SerialRedstoneMessage, IMessage> {

		@Override
		public IMessage onMessage(SerialRedstoneMessage message, MessageContext ctx) {
			//System.out.println("in onMessage(SerialRedstoneMessage message, MessageContext ctx)");
			World world = ctx.getServerHandler().playerEntity.worldObj;
			List<TileEntity> allTEs = ctx.getServerHandler().playerEntity.worldObj.loadedTileEntityList;
			for(TileEntity te : allTEs) {
				if(te instanceof TileEntitySerialRedstone) {
                    te.blockType = te.getBlockType();
		            if (te.blockType instanceof BlockSerialRedstone)
		            {
		            	if(((TileEntitySerialRedstone)te).getRedstoneID().equals(message.id)) {
		            		//System.out.println("found a tileentity with the same ID as redstone event");
		                    ((TileEntitySerialRedstone)te).setRedstonePower(message.power);
		                    world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
		                    world.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, message.power, 3);
		            	}
		            }
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
