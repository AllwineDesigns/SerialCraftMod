package com.allwinedesigns.forge.mods.serialcraft;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {
	 public static KeyBinding openConfigGUI;

	 public static void init() {
		//System.out.println("in KeyBindings init");
	    openConfigGUI = new KeyBinding("key.openConfigGUI", Keyboard.KEY_K, "key.categories.serialcraft");

	    ClientRegistry.registerKeyBinding(openConfigGUI);
	 }
}
