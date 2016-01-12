package org.cmbozeman.forge.mods.serialcraft;

import java.nio.charset.StandardCharsets;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

// Client side detecting of serial communication
public class SerialCraftListener implements SerialPortEventListener {
	public SerialPort serialPort;
	public StringBuilder buffer; // assuming we're reading ASCII text from serial port
	
    public SerialCraftListener() {
    	buffer = new StringBuilder();
    	String[] portNames = SerialPortList.getPortNames();
    	System.out.println("in SerialCraftListener constructor");
    	if(portNames.length > 0) {
	    	System.out.print("Connecting to port ");
	    	System.out.println(portNames[0]);
	        serialPort = new SerialPort(portNames[0]); 
	        try {
	            serialPort.openPort();//Open port
	            serialPort.setParams(115200, 8, 1, 0);//Set params
	            int mask = SerialPort.MASK_RXCHAR;//Prepare mask
	            serialPort.setEventsMask(mask);//Set mask
	            serialPort.addEventListener(this);//Add SerialPortEventListener
	            System.out.println("Successfully connected to serial port");
	        } catch (SerialPortException ex) {
	            System.out.println(ex);
	        }
    	} else {
    		System.out.println("no port names found");
    	}
    }
    
    public void triggerEvent(String str) {
    	// TODO build dictionary of events rather than huge if statement
    	// split on white space and use first argument as event type
    	if(str.startsWith("chat ")) {
    		MinecraftForge.EVENT_BUS.post(new SerialCraftChatEvent(
    				                           Minecraft.getMinecraft().thePlayer, 
    				                           str.substring(5)
    				                      )
    				                     );
    	} else if(str.startsWith("redstone ")) {
    		try {
	    		System.out.println(str);
	    		String[] args = str.substring(9).split(" "); // TODO handle no provided ID
	
	    		MinecraftForge.EVENT_BUS.post(new SerialCraftRedstoneEvent(
	                    Minecraft.getMinecraft().thePlayer, 
	                    Integer.parseInt(args[0]),
	                    args[1]
	               )
	              );
    		} catch(Exception e) {
    			System.out.println(e);
    		}
    	} else if(str.startsWith("time ")) {
    		MinecraftForge.EVENT_BUS.post(new SerialCraftTimeEvent(Minecraft.getMinecraft().thePlayer, str));
    	} else if(str.startsWith("move_forward ")) {
    		String arg = str.substring(13);
    		if(arg.equals("forward")) {
    		    ClientProxy.movementController.moveForward();
    		} else if(arg.equals("back")) {
    			ClientProxy.movementController.moveBack();
    		} else {
    			ClientProxy.movementController.stopForwardMovement();
    		}
    	} else if(str.startsWith("move_speed ")) {
    		String arg = str.substring(11);
    		if(arg.equals("sprint")) {
    		    ClientProxy.movementController.sprint();
    		} else if(arg.equals("sneak")) {
    			ClientProxy.movementController.sneak();
    		} else {
    			ClientProxy.movementController.normalMovementSpeed();
    		}
    	} else if(str.startsWith("move_strafe ")) {
    		String arg = str.substring(12);
    		if(arg.equals("left")) {
    		    ClientProxy.movementController.moveLeft();
    		} else if(arg.equals("right")) {
    			ClientProxy.movementController.moveRight();
    		} else {
    			ClientProxy.movementController.stopStrafeMovement();
    		}
    	} else if(str.equals("start_jumping")) {
			ClientProxy.movementController.startJumping();
    	} else if(str.equals("stop_jumping")) {
			ClientProxy.movementController.stopJumping();
    	} else if(str.startsWith("m ")) {
    		String[] args = str.substring(2).split(" ");
    		try {
    		    int x = Integer.parseInt(args[0]);
    		    int y = Integer.parseInt(args[1]);
    			ClientProxy.movementController.moveMouseWithJoystick(x, y);
    		} catch(Exception e) {
    			System.out.println(e);
    		}
    	} else if(str.equals("move_down")) {
    		ClientProxy.movementController.moveDown();
    	} else if(str.equals("stop_move_down")) {
    		ClientProxy.movementController.stopMovingDown();
    	} else if(str.equals("left_button_press")) {
    		ClientProxy.movementController.leftMousePress();
    	} else if(str.equals("left_button_release")) {
    		ClientProxy.movementController.leftMouseRelease();
    	} else if(str.equals("right_button_press")) {
    		ClientProxy.movementController.rightMousePress();
    	} else if(str.equals("right_button_release")) {
    		ClientProxy.movementController.rightMouseRelease(); 
    	}
    }
    
    public void sendSerialMessage(String msg) {
    	try {
    		if(serialPort != null) {
                serialPort.writeString(msg);
    		}
    	} catch(SerialPortException ex) {
    		System.out.println(ex);
    	}
    }
    
    public void serialEvent(SerialPortEvent event) {
        if(event.isRXCHAR()){//If data is available
            try {
            	int value = event.getEventValue();
                byte data[] = serialPort.readBytes(value);
                buffer.append(new String(data, StandardCharsets.UTF_8)); // assuming we're reading ASCII text from serial port
                int index = buffer.indexOf("\r\n"); // and are looking for lines that end with carriage return line feed (like Arduino's Serial.println)
                while(index != -1) {
            	    String strEvent = buffer.substring(0,index);
            	    buffer.delete(0, index+2);
            	    triggerEvent(strEvent);
                	index = buffer.indexOf("\r\n");
                }
            } catch (SerialPortException ex) {
                System.out.println(ex);
            }
            
        }
    }
}
