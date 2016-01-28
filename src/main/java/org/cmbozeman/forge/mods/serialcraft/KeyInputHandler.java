package org.cmbozeman.forge.mods.serialcraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if(KeyBindings.openConfigGUI.isPressed()) {
            System.out.println("Open config GUI!!");
            SerialCraft.proxy.openConfigGUI();
        }
    }

}
