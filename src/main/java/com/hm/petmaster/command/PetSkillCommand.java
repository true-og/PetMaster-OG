package com.hm.petmaster.command;

import com.hm.petmaster.PetMaster;
import net.trueog.utilitiesog.UtilitiesOG;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetSkillCommand {

    PetMaster plugin = PetMaster.getPlugin(PetMaster.class);

    public PetSkillCommand(PetMaster petMaster) {

        this.plugin = petMaster;
    }

    public void petSkillCommand(CommandSender sender) {

        Player player = (Player) sender;

        UtilitiesOG.trueogMessage(player, " ");
        UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
        UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
        UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
        UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " This is a test feature!");
        UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
        UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");
        UtilitiesOG.trueogMessage(player, " ");

        if (player.hasPermission("petmaster.admin") || player.isOp()) {

            UtilitiesOG.trueogMessage(player, "Pet Boosts Info");
            UtilitiesOG.trueogMessage(player, "#-------------------------#");
            UtilitiesOG.trueogMessage(
                    player,
                    plugin.getChatHeader() + "&d" + " Pet Boost(Enabled): " + "&2"
                            + plugin.getConfig().getBoolean("Pet-Boost-Enabled"));
            UtilitiesOG.trueogMessage(
                    player,
                    plugin.getChatHeader() + "&d" + " Health Boost(Enabled): " + "&2"
                            + plugin.getConfig().getBoolean("Health-Boost-Enabled"));
            UtilitiesOG.trueogMessage(
                    player,
                    plugin.getChatHeader() + "&d" + " Damage Boost(Enabled): " + "&2"
                            + plugin.getConfig().getBoolean("Damage-Boost-Enabled"));
            UtilitiesOG.trueogMessage(
                    player,
                    plugin.getChatHeader() + "&d" + " Speed Boost(Enabled): " + "&2"
                            + plugin.getConfig().getBoolean("Speed-Boost-Enabled"));
            UtilitiesOG.trueogMessage(
                    player,
                    plugin.getChatHeader() + "&d" + " Defense Boost(Enabled): " + "&2"
                            + plugin.getConfig().getBoolean("Defense-Boost-Enabled"));
            UtilitiesOG.trueogMessage(player, " ");
        }

        // Stats of boosts current pet is giving them.
        UtilitiesOG.trueogMessage(player, "Pet Boosts");
        UtilitiesOG.trueogMessage(player, "#---------------------#");
        UtilitiesOG.trueogMessage(
                player,
                plugin.getChatHeader() + "&d" + " Health Boost: " + "&2"
                        + plugin.getConfig().getDouble("Health-Boost-Amount") + "&d" + " %");
        UtilitiesOG.trueogMessage(
                player,
                plugin.getChatHeader() + "&d" + " Damage Boost: " + "&2"
                        + plugin.getConfig().getDouble("Damage-Boost-Amount") + "&d" + " %");
        UtilitiesOG.trueogMessage(
                player,
                plugin.getChatHeader() + "&d" + " Speed Boost: " + "&2"
                        + plugin.getConfig().getDouble("Speed-Boost-Amount") + "&d" + " %");
        UtilitiesOG.trueogMessage(
                player,
                plugin.getChatHeader() + "&d" + " Defense Boost: " + "&2"
                        + plugin.getConfig().getDouble("Defense-Boost-Amount") + "&d" + " %");

        return;
    }
}
