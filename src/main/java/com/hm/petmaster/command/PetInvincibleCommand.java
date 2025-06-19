package com.hm.petmaster.command;

import com.hm.petmaster.PetMaster;
import net.trueog.utilitiesog.UtilitiesOG;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetInvincibleCommand {

    PetMaster plugin = PetMaster.getPlugin(PetMaster.class);

    public PetInvincibleCommand(PetMaster petMaster) {

        this.plugin = petMaster;
    }

    public void godPetCommand(CommandSender sender) {

        Player player = (Player) sender;

        UtilitiesOG.trueogMessage(player, " ");
        UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
        UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
        UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
        UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " This is a test feature!");
        UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " It will be available soon!");
        UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

        return;
    }
}
