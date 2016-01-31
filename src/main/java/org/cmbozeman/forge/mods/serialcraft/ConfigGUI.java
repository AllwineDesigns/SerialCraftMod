package org.cmbozeman.forge.mods.serialcraft;

import java.util.ArrayList;
import java.util.List;

import jssc.SerialPortList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

public class ConfigGUI extends GuiScreen {
	private List<GuiTextField> textfields;
	private String[] portNames;
	
    @Override
    public void initGui() 
    {
        ClientProxy.getSerialCraftListener().updatePorts();
        
    	textfields = new ArrayList<GuiTextField>();
     // DEBUG
     System.out.println("ConfigGUI initGUI()");
        buttonList.clear();
        
    	portNames = SerialPortList.getPortNames();
    	int y = 20;
    	for(String name : portNames) {
    		String label;
    		int baud = 115200;
    		if(ClientProxy.getSerialCraftListener().isPortConnected(name)) {
        		label = I18n.format("serialcraft.disconnect", new Object[0]);
        		baud = ClientProxy.getSerialCraftListener().getBaudRate(name);
    		} else {
        		label = I18n.format("serialcraft.connect", new Object[0]);
    		}
    		GuiButton button = new GuiButton(0, 100,y,  98, 20, label);
    		GuiTextField textfield = new GuiTextField(fontRendererObj, 200, y,50, 20);
            textfield.setText(String.valueOf(baud));
    		buttonList.add(button);
    		textfields.add(textfield);
    		y += 25;
    	}
    }
    
    @Override
    public void updateScreen() 
    {

    }
    
    @Override
    public void drawScreen(int parWidth, int parHeight, float p_73863_3_)
    {
    	this.drawDefaultBackground();
    	
    	int y = 25;
    	for(String portName : portNames) {
    		this.fontRendererObj.drawString(portName, 255, y, 0xffffffff, true);
    		y += 25;
    	}
    	
    	for(GuiTextField textfield : textfields) {
    		textfield.drawTextBox();
    	}

        super.drawScreen(parWidth, parHeight, p_73863_3_);
    }
    
    public void keyTyped(char c, int i){
    	super.keyTyped(c, i);
    	for(GuiTextField textfield : textfields) {
    	    textfield.textboxKeyTyped(c, i);
    	}
    }
    
    public void mouseClicked(int i, int j, int k){
    	super.mouseClicked(i, j, k);
    	for(GuiTextField textfield : textfields) {
    	    textfield.mouseClicked(i, j, k);
    	}
    }
    
    @Override
    protected void actionPerformed(GuiButton b) {
    	System.out.println("button pressed");
    	System.out.println(b);
    	SerialCraftListener sc = ClientProxy.getSerialCraftListener();
    	String connect = I18n.format("serialcraft.connect", new Object[0]);
    	String disconnect = I18n.format("serialcraft.disconnect", new Object[0]);

        for(int i = 0; i < buttonList.size(); i++) {
        	System.out.println(i + ": " + buttonList.get(i));
        	if(buttonList.get(i) == b) {
        		System.out.println("clicked button " + i);
        		int baud = 115200;
        		try {
        		    baud = Integer.parseInt(textfields.get(i).getText());
        		} catch(NumberFormatException e) {
        		}
        		
        		if(sc.isPortConnected(portNames[i])) {
        			sc.disconnectPort(portNames[i]);
        			b.displayString = connect;
        		} else {
                    sc.connectPort(portNames[i], baud);
                    if(sc.isPortConnected(portNames[i])) {
                        b.displayString = disconnect;
                    }

        		}	
        	}
        }
    }
}
