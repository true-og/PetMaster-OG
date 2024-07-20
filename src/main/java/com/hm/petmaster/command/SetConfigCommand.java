package com.hm.petmaster.command;

import org.bukkit.entity.Player;

import com.hm.petmaster.PetMaster;
import com.hm.petmaster.files.PetAbilityFile;

import me.barny1094875.utilitiesog.UtilitiesOG;

public class SetConfigCommand {
	PetMaster plugin = PetMaster.getPlugin(PetMaster.class);

	public SetConfigCommand(PetMaster plugin) {
		this.plugin = plugin;
	}

	public void setConfigCommand(Player player, String[] args) {
		if(player.hasPermission("petmaster.admin")) {
			if(args[0].equalsIgnoreCase("set")) {
				if(args[1].equalsIgnoreCase("config")) {
					if(args[2].equalsIgnoreCase("set")) {
						UtilitiesOG.trueogSendMessage(player, " ");
						UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
						UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
						UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
						UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
						UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
						UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");


					}
				}
				if(args[1].equalsIgnoreCase("pet-abilities")) {
					if(args[2].equalsIgnoreCase("enabled")) {
						if(args[3].equalsIgnoreCase("true")) {
							UtilitiesOG.trueogSendMessage(player, " ");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

							UtilitiesOG.trueogSendMessage(player, "You have enabled Pet Abilities!");
							PetAbilityFile.getPetAbilities().set("Is-Pet-Abilities-Enabled", true);
						}
						if(args[3].equalsIgnoreCase("false")) {
							UtilitiesOG.trueogSendMessage(player, " ");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

							UtilitiesOG.trueogSendMessage(player, "You have disabled Pet Abilities!");
							PetAbilityFile.getPetAbilities().set("Is-Pet-Abilities-Enabled", false);
						}
					}
					if(args[2].equalsIgnoreCase("set")) {
						if(args[3].equalsIgnoreCase("health")) {
							UtilitiesOG.trueogSendMessage(player, " ");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

							UtilitiesOG.trueogSendMessage(player, "You have enabled Pet Abilities!");


						}
						if(args[3].equalsIgnoreCase("damage")) {
							UtilitiesOG.trueogSendMessage(player, " ");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

							UtilitiesOG.trueogSendMessage(player, "You have enabled Pet Abilities!");

						}
						if(args[3].equalsIgnoreCase("speed")) {
							UtilitiesOG.trueogSendMessage(player, " ");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

							UtilitiesOG.trueogSendMessage(player, "You have enabled Pet Abilities!");

						}
						if(args[3].equalsIgnoreCase("protection")) {
							UtilitiesOG.trueogSendMessage(player, " ");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
							UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
							UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

							UtilitiesOG.trueogSendMessage(player, "You have enabled Pet Abilities!");

						}
					}
				}
			}
		}
	}

}