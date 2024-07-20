package com.hm.petmaster.listener;

import org.bukkit.event.Listener;

import com.hm.petmaster.PetMaster;

public class PetDeathListener implements Listener{
	PetMaster plugin;

	/*Tameable tameable;
	public PetDeathListener(PetMaster petMaster) {
		this.plugin = petMaster;
	}

	boolean isTamed = tameable.isTamed();
	boolean isDead = tameable.isDead();

	public void onPetDeath (EntityDeathEvent event) {
		if(event.getEntity() instanceof org.bukkit.entity.Tameable && isTamed == true && isDead == true) {
			tameable.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			isDead = true;
			isTamed = true;
		}
		else if(tameable == null) {
			return;

		}else {
			isDead = false;
			isTamed =false;
		}
	}*/
}