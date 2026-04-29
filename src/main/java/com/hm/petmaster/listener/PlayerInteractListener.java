package com.hm.petmaster.listener;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.hm.mcshared.event.PlayerChangeAnimalOwnershipEvent;
import com.hm.petmaster.PetMaster;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.trueog.diamondbankog.DiamondBankException.EconomyDisabledException;
import net.trueog.diamondbankog.DiamondBankException.InsufficientFundsException;
import net.trueog.diamondbankog.DiamondBankException.InvalidPlayerException;
import net.trueog.diamondbankog.DiamondBankException.PlayerNotOnlineException;
import net.trueog.diamondbankog.api.DiamondBankAPIJava;
import net.trueog.utilitiesog.UtilitiesOG;

/**
 * Class used to display owner chat messages, change the owner of a pet or free
 * a pet.
 *
 * @author Pyves
 *
 */
public class PlayerInteractListener implements Listener {

    private final PetMaster plugin;

    // Configuration parameters.
    private boolean chatMessage;
    private boolean displayToOwner;
    private int freePetPrice;
    private boolean showHealth;
    private boolean disableRiding;

    public PlayerInteractListener() {

        this.plugin = PetMaster.getPlugin();

    }

    public void extractParameters() {

        displayToOwner = plugin.getPluginConfig().getBoolean("displayToOwner", false);
        freePetPrice = plugin.getPluginConfig().getInt("freePetPrice", 0);
        chatMessage = plugin.getPluginConfig().getBoolean("chatMessage", true);
        showHealth = plugin.getPluginConfig().getBoolean("showHealth", true);
        disableRiding = plugin.getPluginConfig().getBoolean("disableRiding", false);

    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {

        if (!shouldHandleEvent(event)) {

            return;

        }

        final Tameable tameable = (Tameable) event.getRightClicked();
        final AnimalTamer currentOwner = tameable.getOwner();
        if (currentOwner == null || currentOwner.getName() == null) {

            return;

        }

        // Has the player clicked on one of his own pets?
        final Player player = event.getPlayer();
        final boolean isOwner = player.getUniqueId().equals(currentOwner.getUniqueId());
        // Retrieve new owner from the map and delete corresponding entry.
        final Player newOwner = plugin.getSetOwnerCommand().collectPendingSetOwnershipRequest(player);
        // Has the player requested to free one of his pets?
        final boolean freePet = plugin.getFreeCommand().collectPendingFreeRequest(player);
        if (disableRiding && !isOwner && !player.hasPermission("petmaster.admin") && tameable instanceof Vehicle) {

            plugin.getMessageSender().sendMessage(player, "not-owner");
            event.setCancelled(true);
            return;

        }

        // Cannot change ownership or free pet if not owner and no bypass permission.
        if ((newOwner != null || freePet) && !isOwner && !player.hasPermission("petmaster.admin")) {

            plugin.getMessageSender().sendMessage(player, "not-owner");
            return;

        }

        if (newOwner != null) {

            changeOwner(player, currentOwner, newOwner, tameable);

        } else if (freePet) {

            freePet(player, currentOwner, tameable);

        } else if ((displayToOwner || !isOwner) && player.hasPermission("petmaster.showowner.*")) {

            displayOwnerChatMessage(player, currentOwner, tameable);

        }

    }

    /**
     * Determines whether the current PlayerInteractEntityEvent should be handled.
     *
     * @param event
     * @return true if the event should be handled, false otherwise
     */
    private boolean shouldHandleEvent(PlayerInteractEntityEvent event) {

        return !plugin.getEnableDisableCommand().isDisabled() && event.getRightClicked() instanceof Tameable
        // On Minecraft 1.9+, the event is fired once per hand (HAND and OFF_HAND).
                && (plugin.getServerVersion() < 9 || event.getHand() == EquipmentSlot.HAND);

    }

    /**
     * Change the owner of a pet.
     *
     * @param player
     * @param oldOwner
     * @param newOwner
     * @param tameable
     */
    private void changeOwner(Player player, AnimalTamer oldOwner, Player newOwner, Tameable tameable) {

        // Change owner.
        tameable.setOwner(newOwner);
        player.sendMessage(plugin.getChatHeader()
                + plugin.getPluginLang().getString("owner-changed", "This pet was given to a new owner!"));
        newOwner.sendMessage(plugin.getChatHeader()
                + plugin.getPluginLang().getString("new-owner", "Player PLAYER gave you ownership of a pet!")
                        .replace("PLAYER", oldOwner.getName()));

        // Create new event to allow other plugins to be aware of the ownership change.
        final PlayerChangeAnimalOwnershipEvent playerChangeAnimalOwnershipEvent = new PlayerChangeAnimalOwnershipEvent(
                oldOwner, newOwner, tameable);
        Bukkit.getServer().getPluginManager().callEvent(playerChangeAnimalOwnershipEvent);

    }

    /**
     * Frees a pet; it will no longer be tamed.
     *
     * @param player
     * @param oldOwner
     * @param tameable
     */
    private void freePet(Player player, AnimalTamer oldOwner, Tameable tameable) {

        if (!chargePrice(player, freePetPrice)) {

            return;

        }

        // Free pet.
        tameable.setTamed(false);
        // Make freed pet stand up.
        if (plugin.getServerVersion() >= 12 && tameable instanceof Sittable) {

            ((Sittable) tameable).setSitting(false);

        } else if (tameable instanceof Wolf) {

            ((Wolf) tameable).setSitting(false);

        } else if (tameable instanceof Ocelot) {

            // Since Minecraft 1.14, ocelots are no longer Sittable, use reflection for old
            // game versions.
            try {

                final Method setSitting = Ocelot.class.getMethod("setSitting", boolean.class);
                setSitting.invoke(tameable, false);

            } catch (ReflectiveOperationException reflectiveOperationException) {

                plugin.getLogger().warning("Failed to make freed ocelot stand up.");

            }

        }

        plugin.getMessageSender().sendMessage(player, "pet-freed");
        // Create new event to allow other plugins to be aware of the freeing.
        final PlayerChangeAnimalOwnershipEvent playerChangeAnimalOwnershipEvent = new PlayerChangeAnimalOwnershipEvent(
                oldOwner, null, tameable);
        Bukkit.getServer().getPluginManager().callEvent(playerChangeAnimalOwnershipEvent);

    }

    /**
     * Displays the owner in chat when right-click owner messages are enabled.
     *
     * @param player
     * @param owner
     * @param tameable
     */
    @SuppressWarnings("deprecation")
    private void displayOwnerChatMessage(Player player, AnimalTamer owner, Tameable tameable) {

        if (!chatMessage) {

            return;

        }

        Component healthInfo = null;
        if (showHealth) {

            final Animals animal = (Animals) tameable;
            healthInfo = plugin.getMessageSender().parseMessage(plugin.getPluginLang().getString("petmaster-health"),
                    Placeholder.component("current-health", Component.text("%.1f".formatted(animal.getHealth()))),
                    Placeholder.component("max-health",
                            Component.text(plugin.getServerVersion() < 9 ? "%.1f".formatted(animal.getMaxHealth())
                                    : "%.1f".formatted(animal.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()))));

        }

        Component parsedComponent = plugin.getMessageSender().parseMessage(
                plugin.getPluginLang().getString("petmaster-chat"),
                Placeholder.component("owner", Component.text(owner.getName() != null ? owner.getName() : "null")));
        if (healthInfo != null) {

            parsedComponent = parsedComponent.append(healthInfo);

        }

        plugin.getMessageSender().sendComponent(player, parsedComponent);

    }

    /**
     * Charges a player through DiamondBank-OG and displays relevant messages.
     *
     * @param player
     * @param price  price in DiamondBank-OG shards
     * @return true if money should be withdrawn from the player, false otherwise
     */
    private boolean chargePrice(Player player, int price) {

        if (price > 0 && !player.hasPermission("petmaster.admin")) {

            final DiamondBankAPIJava economy = PetMaster.getDiamondBankAPI();

            if (economy == null) {

                PetMaster.disableSelf("DiamondBank-OG API is null while charging for pet freeing!");
                return false;

            }

            try {

                if (economy.getTotalShards(player.getUniqueId()) < price) {

                    plugin.getMessageSender().sendMessage(player, "not-enough-money", Placeholder.component("amount",
                            UtilitiesOG.trueogColorize(economy.shardsToDiamonds(price))));

                    return false;

                }

                economy.consumeFromPlayer(player.getUniqueId(), price, "Player " + player.getName() + " paid "
                        + economy.shardsToDiamonds(price) + " Diamonds to free a pet.", "");
                plugin.getMessageSender().sendMessage(player, "change-owner-price",
                        Placeholder.component("amount", UtilitiesOG.trueogColorize(economy.shardsToDiamonds(price))));
                return true;

            } catch (EconomyDisabledException economyDisabledException) {

                economyDisabledException.printStackTrace();

                PetMaster.disableSelf("DiamondBank-OG Economy Disabled!");
                return false;

            } catch (InvalidPlayerException invalidPlayerException) {

                invalidPlayerException.printStackTrace();

                PetMaster.disableSelf("Invalid player: + " + player.getName() + " when charging for pet freeing!");
                return false;

            } catch (PlayerNotOnlineException playerNotOnlineException) {

                PetMaster.disableSelf("Player " + player.getName() + " is not online when charging for pet freeing!");
                return false;

            } catch (InsufficientFundsException insufficientFundsException) {

                plugin.getMessageSender().sendMessage(player, "not-enough-money",
                        Placeholder.component("amount", UtilitiesOG.trueogColorize(economy.shardsToDiamonds(price))));

                return false;

            }

        }

        return true;

    }

}
