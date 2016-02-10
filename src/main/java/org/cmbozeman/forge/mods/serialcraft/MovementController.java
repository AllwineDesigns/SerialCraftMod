package org.cmbozeman.forge.mods.serialcraft;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class MovementController {
	private Robot robot;
	
	float mouseDX;
	float mouseDY;
	
	private enum MovementSpeed { SNEAK, NORMAL, SPRINT };
	private MovementSpeed speed = MovementSpeed.NORMAL;
	
    public MovementController() {
    	try {
    		robot = new Robot();
    	} catch(AWTException e) {
    		System.out.println(e);
    	}
    }
    
    public void setSpeed() {
    	switch(speed) {
    	case SNEAK:
    		sneak();
    		break;
    	case NORMAL:
    		normalMovementSpeed();
    		break;
    	case SPRINT:
    		sprint();
    		break;
    	}
    }
    
    public void moveForward() {
        setSpeed();

    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindBack.getKeyCode(), false);
    	KeyBinding.setKeyBindState(gs.keyBindForward.getKeyCode(), true);
    }
    
    public void moveBack() {
        setSpeed();

    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindBack.getKeyCode(), true);
    	KeyBinding.setKeyBindState(gs.keyBindForward.getKeyCode(), false);
    }
    
    public void stopForwardMovement() {
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindBack.getKeyCode(), false);
    	KeyBinding.setKeyBindState(gs.keyBindForward.getKeyCode(), false);
    }
    
    public void moveLeft() {
        setSpeed();

    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindLeft.getKeyCode(), true);
    	KeyBinding.setKeyBindState(gs.keyBindRight.getKeyCode(), false);
    }
    
    public void moveRight() {
        setSpeed();

    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindLeft.getKeyCode(), false);
    	KeyBinding.setKeyBindState(gs.keyBindRight.getKeyCode(), true);
    }
    
    public void stopStrafeMovement() {
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindLeft.getKeyCode(), false);
    	KeyBinding.setKeyBindState(gs.keyBindRight.getKeyCode(), false);
    }
    
    public void sneak() {
    	speed = MovementSpeed.SNEAK;
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	if(Minecraft.getMinecraft().thePlayer.onGround) {
    	    KeyBinding.setKeyBindState(gs.keyBindSneak.getKeyCode(), true);
    	}
	    KeyBinding.setKeyBindState(gs.keyBindSprint.getKeyCode(), false);
    }
    
    public void sprint() {
    	speed = MovementSpeed.SPRINT;
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindSneak.getKeyCode(), false);
    	KeyBinding.setKeyBindState(gs.keyBindSprint.getKeyCode(), true);
    }
    
    public void normalMovementSpeed() {
    	speed = MovementSpeed.NORMAL;
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindSneak.getKeyCode(), false);
    	KeyBinding.setKeyBindState(gs.keyBindSprint.getKeyCode(), false);
    }
    
    public void startJumping() {
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindJump.getKeyCode(), true);    
    }
    
    public void stopJumping() {
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindJump.getKeyCode(), false);   
    }
    
    public void moveDown() {
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindSneak.getKeyCode(), true);  
    }
    
    public void stopMovingDown() {
    	GameSettings gs = Minecraft.getMinecraft().gameSettings;
    	KeyBinding.setKeyBindState(gs.keyBindSneak.getKeyCode(), false);  
    }
    public void moveMouseWithJoystick(int x, int y) { 
        // dx and dy range from -512 to 511 with updates expected to come about 60 times a second
    	float dx = ((float)x)/64;
	    float dy = ((float)y)/64;
	    Minecraft mc = Minecraft.getMinecraft();
    	if(mc.inGameHasFocus && Display.isActive()) {
	        if(mc.gameSettings.invertMouse) {
	            dy *= -1;
	        }
	        mc.thePlayer.setAngles(dx, dy);
    	} else {
    		Point loc = MouseInfo.getPointerInfo().getLocation();
    		float xx = loc.x+dx+mouseDX;
    		float yy = loc.y-dy+mouseDY;
    		mouseDX = xx-(int)(loc.x+dx+mouseDX);
    		mouseDY = yy-(int)(loc.y-dy+mouseDY);
    		
    		robot.mouseMove((int)xx, (int)yy);
    	}
    }
    
    public void leftMousePress() {
    	robot.mousePress(InputEvent.getMaskForButton(1));
    }
    public void leftMouseRelease() {
    	robot.mouseRelease(InputEvent.getMaskForButton(1));
    }
    public void rightMousePress() {
    	robot.mousePress(InputEvent.getMaskForButton(3));
    }
    public void rightMouseRelease() {
    	robot.mouseRelease(InputEvent.getMaskForButton(3));
    }
}
