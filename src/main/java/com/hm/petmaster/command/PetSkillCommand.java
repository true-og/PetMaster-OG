package com.hm.petmaster.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hm.petmaster.PetMaster;

import me.barny1094875.utilitiesog.UtilitiesOG;
import net.md_5.bungee.api.ChatColor;

public class PetSkillCommand {

	PetMaster plugin = PetMaster.getPlugin(PetMaster.class);

	public PetSkillCommand(PetMaster petMaster) {
		this.plugin = petMaster;
	}

	public void petSkillCommand(CommandSender sender) {
		Player player = (Player) sender;
		UtilitiesOG.trueogSendMessage(player, " ");
		UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
		UtilitiesOG.trueogSendMessage(player, "&d" +"#  PetMaster Dev Note           #");
		UtilitiesOG.trueogSendMessage(player, "&d" +"#-------------------------#");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" This is a test feature!");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");
		UtilitiesOG.trueogSendMessage(player, " ");

		if(player.hasPermission("petmaster.admin") || player.isOp()) {
			UtilitiesOG.trueogSendMessage(player, "Pet Boosts Info");
			UtilitiesOG.trueogSendMessage(player, "#-------------------------#");
			UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" Pet Boost(Enabled): " + ChatColor.GREEN +plugin.getConfig().getBoolean("Pet-Boost-Enabled"));
			UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" Health Boost(Enabled): " + ChatColor.GREEN + plugin.getConfig().getBoolean("Health-Boost-Enabled"));
			UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" Damage Boost(Enabled): " + ChatColor.GREEN + plugin.getConfig().getBoolean("Damage-Boost-Enabled"));
			UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" Speed Boost(Enabled): " + ChatColor.GREEN + plugin.getConfig().getBoolean("Speed-Boost-Enabled"));
			UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader() + "&d" +" Defense Boost(Enabled): " + ChatColor.GREEN + plugin.getConfig().getBoolean("Defense-Boost-Enabled"));
			UtilitiesOG.trueogSendMessage(player, " ");
		}

		// Stats of boosts current pet is giving them
		UtilitiesOG.trueogSendMessage(player, "Pet Boosts");
		UtilitiesOG.trueogSendMessage(player, "#---------------------#");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader()+ "&d" +" Health Boost: "+ ChatColor.GREEN + plugin.getConfig().getDouble("Health-Boost-Amount")+ "&d"+ " %");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader()+ "&d" +" Damage Boost: "+ ChatColor.GREEN + plugin.getConfig().getDouble("Damage-Boost-Amount")+ "&d"+ " %");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader()+ "&d" +" Speed Boost: "+ ChatColor.GREEN + plugin.getConfig().getDouble("Speed-Boost-Amount")+ "&d"+ " %");
		UtilitiesOG.trueogSendMessage(player, plugin.getChatHeader()+ "&d" +" Defense Boost: "+ ChatColor.GREEN + plugin.getConfig().getDouble("Defense-Boost-Amount")+ "&d"+ " %");

		return;
	}

}