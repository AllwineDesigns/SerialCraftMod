package com.allwinedesigns.forge.mods.serialcraft;

public class ClientState {
    private float health;
    private int foodLevel;
    private int air;
    private int distanceToCreeper;
    
    public ClientState() {
    	health = 20;
    	air = 300;
    	distanceToCreeper = -1;
    }
    
    public int getFoodLevel() {
    	return foodLevel;
    }
    
    public boolean setFoodLevel(int f) {
    	if(f != foodLevel) {
    		foodLevel = f;
    		return true;
    	}
    	return false;
    }
    public int getAir() {
        return air;
    }
    
    public boolean setAir(int a) {
    	if(a < 0) a = 0;
    	
    	if(a != air) {
    		air = a;
    		return true;
    	}
    	return false;
    }
    
    public int getDistanceToCreeper() {
    	return distanceToCreeper;
    }
    
    public boolean setDistanceToCreeper(int d) {
        if(distanceToCreeper != d) {
        	distanceToCreeper = d;
        	return true;
        }
        return false;
    }
    
    public float getHealth() {
        return health;
    }
    
    public boolean setHealth(float h) {
        if(health != h) {
        	health = h;
        	return true;
        }
        return false;
    }
}
