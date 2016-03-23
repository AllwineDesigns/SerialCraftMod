package org.cmbozeman.forge.mods.serialcraft;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;

public class GuiSerialRedstone extends GuiScreen {
    private GuiButton buttonDone;
    private GuiTextField textField;
    private String redstoneID;
    
    private TileEntity tileEntity;

    
    public GuiSerialRedstone(TileEntity te) {
    	tileEntity = te;
    }
    
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    @Override
    public void initGui() 
    {
     // DEBUG
     //System.out.println("GuiSerialRedstone initGUI()");
        buttonList.clear();

        buttonDone = new GuiButton(0, 100,150,  98, 20, I18n.format("gui.done", new Object[0]));
        textField = new GuiTextField(fontRendererObj, 100, 100,200, 20);
        textField.setFocused(true);
        buttonList.add(buttonDone);
    }
    
    public void keyTyped(char c, int i){
    	super.keyTyped(c, i);
    	textField.textboxKeyTyped(c, i);
    }
    
    public void mouseClicked(int i, int j, int k){
    	super.mouseClicked(i, j, k);
    	textField.mouseClicked(i, j, k);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen() 
    {
    	if(textField != null) {
            redstoneID = textField.getText();
    	}
    }
 
    /**
     * Draws the screen and all the components in it.
     */
    @Override
    public void drawScreen(int parWidth, int parHeight, float p_73863_3_)
    {
        textField.drawTextBox();

        super.drawScreen(parWidth, parHeight, p_73863_3_);
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. 
     * Parameters are : mouseX, mouseY, lastButtonClicked & 
     * timeSinceMouseClick.
     */
    @Override
    protected void mouseClickMove(int parMouseX, int parMouseY, 
          int parLastButtonClicked, long parTimeSinceMouseClick) 
    {
     
    }

    @Override
    protected void actionPerformed(GuiButton parButton) 
    {
     if (parButton == buttonDone)
     {
    	 //System.out.println("in actionPerformed");
    	 //System.out.println(SerialCraft.network);
    	 //System.out.println(tileEntity);
    	 SerialCraft.network.sendToServer(new SerialRedstoneIDMessage(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, redstoneID));
         mc.displayGuiScreen((GuiScreen)null);
     }

   }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat 
     * events
     */
    @Override
    public void onGuiClosed() 
    {
     
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in 
     * single-player
     */
    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }

}