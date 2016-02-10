package org.cmbozeman.forge.mods.serialcraft;

import java.nio.charset.StandardCharsets;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;

// Monitors serial data an individual serial port and sends data to serial port
public class SerialPortIO implements SerialPortEventListener  {
	private StringBuilder buffer;
	private SerialPort serialPort;
	private int baud;
	
	public SerialPortIO(String portName, int b) throws SerialPortException {
		baud = b;
    	buffer = new StringBuilder();
    	//System.out.print("Connecting to port ");
    	//System.out.println(portName);
        serialPort = new SerialPort(portName); 
        serialPort.openPort();//Open port
        serialPort.setParams(baud, 8, 1, 0);//Set params
        int mask = SerialPort.MASK_RXCHAR;//Prepare mask
        serialPort.setEventsMask(mask);//Set mask
        serialPort.addEventListener(this);//Add SerialPortEventListener
        //System.out.println("Successfully connected to serial port");
	}
	
	public void sendMessage(String str) throws SerialPortException {
		serialPort.writeString(str);
	}
	
	public int getBaudRate() {
		return baud;
	}
	
	public void disconnect() {
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			System.out.println(e);
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
            	    ClientProxy.getSerialCraftListener().triggerEvent(strEvent);
                	index = buffer.indexOf("\r\n");
                }
            } catch (SerialPortException ex) {
                System.out.println(ex);
            }
            
        }
    }
}
