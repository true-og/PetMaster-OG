package com.hm.petmaster.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.RayTraceResult;

import com.hm.petmaster.PetMaster;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class PetOwnerLabelListener implements Listener {

    private static final long UPDATE_PERIOD_TICKS = 2L;
    private static final double DOG_OFFSET = 1.5D;
    private static final double CAT_OFFSET = 1.42D;
    private static final double HORSE_OFFSET = 2.32D;
    private static final double LLAMA_OFFSET = 2.42D;
    private static final double PARROT_OFFSET = 1.15D;

    private final PetMaster plugin;
    private final Map<UUID, ArmorStand> activeLabels = new HashMap<>();
    private final BukkitTask tickTask;

    private boolean hoverMessage;
    private double hoverRange;
    private boolean displayDog;
    private boolean displayCat;
    private boolean displayHorse;
    private boolean displayLlama;
    private boolean displayParrot;
    private boolean displayToOwner;

    public PetOwnerLabelListener(PetMaster plugin) {

        this.plugin = plugin;
        extractParameters();
        this.tickTask = plugin.getServer().getScheduler().runTaskTimer(plugin, this::tick, UPDATE_PERIOD_TICKS,
                UPDATE_PERIOD_TICKS);

    }

    public void extractParameters() {

        hoverMessage = plugin.getPluginConfig().getBoolean("hoverMessage", true);
        hoverRange = Math.max(1D, plugin.getPluginConfig().getDouble("hoverRange", 8D));
        displayDog = plugin.getPluginConfig().getBoolean("displayDog", true);
        displayCat = plugin.getPluginConfig().getBoolean("displayCat", true);
        displayHorse = plugin.getPluginConfig().getBoolean("displayHorse", true);
        displayLlama = plugin.getPluginConfig().getBoolean("displayLlama", true);
        displayParrot = plugin.getPluginConfig().getBoolean("displayParrot", true);
        displayToOwner = plugin.getPluginConfig().getBoolean("displayToOwner", false);

        if (!hoverMessage) {

            clearLabels();

        }

    }

    public void shutdown() {

        if (tickTask != null) {

            tickTask.cancel();

        }

        clearLabels();

    }

    private void tick() {

        if (!hoverMessage || plugin.getEnableDisableCommand().isDisabled()) {

            clearLabels();
            return;

        }

        final Set<UUID> visibleTargets = new HashSet<>();
        for (Player player : plugin.getServer().getOnlinePlayers()) {

            final PetTarget target = findTargetedPet(player);
            if (target == null) {

                continue;

            }

            visibleTargets.add(target.entityId());
            upsertLabel(target);

        }

        final Set<UUID> staleLabels = new HashSet<>(activeLabels.keySet());
        staleLabels.removeAll(visibleTargets);
        staleLabels.forEach(this::removeLabel);

    }

    private PetTarget findTargetedPet(Player player) {

        final RayTraceResult rayTrace = player.getWorld().rayTraceEntities(player.getEyeLocation(),
                player.getEyeLocation().getDirection(), hoverRange, 0.25D, entity -> entity instanceof Tameable);
        if (rayTrace == null || !(rayTrace.getHitEntity() instanceof Tameable tameable)) {

            return null;

        }

        final Entity entity = (Entity) tameable;
        if (!entity.isValid() || entity.isDead()) {

            return null;

        }

        final AnimalTamer owner = tameable.getOwner();
        if (owner == null || owner.getName() == null || !canDisplayToPlayer(player, tameable, owner)) {

            return null;

        }

        return new PetTarget(entity.getUniqueId(), entity, tameable, owner);

    }

    private boolean canDisplayToPlayer(Player player, Tameable tameable, AnimalTamer owner) {

        final boolean isOwner = player.getUniqueId().equals(owner.getUniqueId());
        if (!displayToOwner && isOwner) {

            return false;

        }

        if (!player.hasPermission("petmaster.showowner.*")) {

            return false;

        }

        return canDisplayAnimalType(player, tameable);

    }

    private boolean canDisplayAnimalType(Player player, Tameable tameable) {

        final int version = plugin.getServerVersion();
        if (tameable instanceof Ocelot || version >= 14 && tameable instanceof Cat) {

            return displayCat && player.hasPermission("petmaster.showowner.cat");

        }

        if (tameable instanceof Wolf) {

            return displayDog && player.hasPermission("petmaster.showowner.dog");

        }

        if (version >= 11 && tameable instanceof Llama) {

            return displayLlama && player.hasPermission("petmaster.showowner.llama");

        }

        if (version >= 12 && tameable instanceof Parrot) {

            return displayParrot && player.hasPermission("petmaster.showowner.parrot");

        }

        if (tameable instanceof Vehicle) {

            return displayHorse && player.hasPermission("petmaster.showowner.horse");

        }

        return true;

    }

    private void upsertLabel(PetTarget target) {

        final Location labelLocation = getLabelLocation(target);
        final Component labelText = buildLabel(target.owner());
        final ArmorStand existing = activeLabels.get(target.entityId());
        if (existing != null && existing.isValid()) {

            existing.teleport(labelLocation);
            existing.customName(labelText);
            return;

        }

        final ArmorStand label = target.entity().getWorld().spawn(labelLocation, ArmorStand.class, stand -> {

            stand.setInvisible(true);
            stand.setGravity(false);
            stand.setMarker(true);
            stand.setSmall(true);
            stand.setInvulnerable(true);
            stand.setSilent(true);
            stand.setPersistent(false);
            stand.setBasePlate(false);
            stand.setArms(false);
            stand.customName(labelText);
            stand.setCustomNameVisible(true);

        });

        activeLabels.put(target.entityId(), label);

    }

    private Location getLabelLocation(PetTarget target) {

        return target.entity().getLocation().add(0D, getLabelOffset(target.tameable()), 0D);

    }

    private double getLabelOffset(Tameable tameable) {

        final int version = plugin.getServerVersion();
        if (tameable instanceof Ocelot || version >= 14 && tameable instanceof Cat) {

            return CAT_OFFSET;

        }

        if (tameable instanceof Wolf) {

            return DOG_OFFSET;

        }

        if (version >= 11 && tameable instanceof Llama) {

            return LLAMA_OFFSET;

        }

        if (version >= 12 && tameable instanceof Parrot) {

            return PARROT_OFFSET;

        }

        return HORSE_OFFSET;

    }

    private Component buildLabel(AnimalTamer owner) {

        return plugin.getMessageSender().parseMessage(
                plugin.getPluginLang().getString("petmaster-hover", "Pet owned by <gold><owner></gold>."),
                Placeholder.component("owner", Component.text(owner.getName() != null ? owner.getName() : "null")));

    }

    private void removeLabel(UUID entityId) {

        final ArmorStand stand = activeLabels.remove(entityId);
        if (stand != null && stand.isValid()) {

            stand.remove();

        }

    }

    private void clearLabels() {

        final Set<UUID> entityIds = new HashSet<>(activeLabels.keySet());
        entityIds.forEach(this::removeLabel);

    }

    private record PetTarget(UUID entityId, Entity entity, Tameable tameable, AnimalTamer owner) {
    }

}
