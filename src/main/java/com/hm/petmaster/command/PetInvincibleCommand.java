package com.hm.petmaster.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hm.petmaster.PetMaster;

import me.barny1094875.utilitiesog.UtilitiesOG;

public class PetInvincibleCommand {
	PetMaster plugin = PetMaster.getPlugin(PetMaster.class);

	public PetInvincibleCommand(PetMaster petMaster) {
		this.plugin = petMaster;
	}

	public void godPetCommand(CommandSender sender) {
		Player player = (Player) sender;
		UtilitiesOG.trueogSendMessage(player, " ");
		UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
		UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
		UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

		return;
	}

}