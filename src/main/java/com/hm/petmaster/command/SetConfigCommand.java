package com.hm.petmaster.command;

import com.hm.petmaster.PetMaster;
import com.hm.petmaster.files.PetAbilityFile;
import net.trueog.utilitiesog.UtilitiesOG;
import org.bukkit.entity.Player;

public class SetConfigCommand {

    PetMaster plugin = PetMaster.getPlugin(PetMaster.class);

    public SetConfigCommand(PetMaster plugin) {

        this.plugin = plugin;

    }

    public void setConfigCommand(Player player, String[] args) {

        if (player.hasPermission("petmaster.admin")) {

            if (args[0].equalsIgnoreCase("set")) {

                if (args[1].equalsIgnoreCase("config")) {

                    if (args[2].equalsIgnoreCase("set")) {

                        UtilitiesOG.trueogMessage(player, " ");
                        UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                        UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
                        UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                        UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " This is a test feature!");
                        UtilitiesOG.trueogMessage(player,
                                plugin.getChatHeader() + "&d" + " It will be available soon!");
                        UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");

                    }

                }

                if (args[1].equalsIgnoreCase("pet-abilities")) {

                    if (args[2].equalsIgnoreCase("enabled")) {

                        if (args[3].equalsIgnoreCase("true")) {

                            UtilitiesOG.trueogMessage(player, " ");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " This is a test feature!");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " It will be available soon!");
                            UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");
                            UtilitiesOG.trueogMessage(player, "You have enabled Pet Abilities!");

                            PetAbilityFile.getPetAbilities().set("Is-Pet-Abilities-Enabled", true);

                        }

                        if (args[3].equalsIgnoreCase("false")) {

                            UtilitiesOG.trueogMessage(player, " ");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " This is a test feature!");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " It will be available soon!");
                            UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");
                            UtilitiesOG.trueogMessage(player, "You have disabled Pet Abilities!");

                            PetAbilityFile.getPetAbilities().set("Is-Pet-Abilities-Enabled", false);

                        }

                    }

                    if (args[2].equalsIgnoreCase("set")) {

                        if (args[3].equalsIgnoreCase("health")) {

                            UtilitiesOG.trueogMessage(player, " ");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " This is a test feature!");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " It will be available soon!");
                            UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");
                            UtilitiesOG.trueogMessage(player, "You have enabled Pet Abilities!");

                        }

                        if (args[3].equalsIgnoreCase("damage")) {

                            UtilitiesOG.trueogMessage(player, " ");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " This is a test feature!");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " It will be available soon!");
                            UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");
                            UtilitiesOG.trueogMessage(player, "You have enabled Pet Abilities!");

                        }

                        if (args[3].equalsIgnoreCase("speed")) {

                            UtilitiesOG.trueogMessage(player, " ");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " This is a test feature!");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " It will be available soon!");
                            UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");
                            UtilitiesOG.trueogMessage(player, "You have enabled Pet Abilities!");

                        }

                        if (args[3].equalsIgnoreCase("protection")) {

                            UtilitiesOG.trueogMessage(player, " ");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player, "&d" + "#  PetMaster Dev Note           #");
                            UtilitiesOG.trueogMessage(player, "&d" + "#-------------------------#");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " This is a test feature!");
                            UtilitiesOG.trueogMessage(player,
                                    plugin.getChatHeader() + "&d" + " It will be available soon!");
                            UtilitiesOG.trueogMessage(player, plugin.getChatHeader() + "&d" + " Enjoy!");
                            UtilitiesOG.trueogMessage(player, "You have enabled Pet Abilities!");

                        }

                    }

                }

            }

        }

    }

}
