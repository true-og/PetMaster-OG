package com.hm.petmaster;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import com.hm.mcshared.file.CommentedYamlConfiguration;
import com.hm.petmaster.command.EnableDisableCommand;
import com.hm.petmaster.command.FreeCommand;
import com.hm.petmaster.command.HelpCommand;
import com.hm.petmaster.command.InfoCommand;
import com.hm.petmaster.command.PetInvincibleCommand;
import com.hm.petmaster.command.PetSkillCommand;
import com.hm.petmaster.command.ReloadCommand;
import com.hm.petmaster.command.SetColorCommand;
import com.hm.petmaster.command.SetOwnerCommand;
import com.hm.petmaster.command.ShareCommand;
import com.hm.petmaster.files.PetAbilityFile;
import com.hm.petmaster.listener.PlayerAttackListener;
import com.hm.petmaster.listener.PlayerBreedListener;
import com.hm.petmaster.listener.PlayerInteractListener;
import com.hm.petmaster.listener.PlayerLeashListener;
import com.hm.petmaster.listener.PlayerQuitListener;
import com.hm.petmaster.listener.PlayerTameListener;
import com.hm.petmaster.utils.MessageSender;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

/**
 * Manage pets and display useful information via holograms, action bar or chat
 * messages!
 *
 * PetMaster is under GNU General Public License version 3. Please visit the
 * plugin's GitHub for more information : https://github.com/PyvesB/PetMaster
 *
 * Official plugin's server: hellominecraft.fr
 *
 * Bukkit project page: dev.bukkit.org/bukkit-plugins/pet-master
 *
 * Spigot project page: spigotmc.org/resources/pet-master.15904
 *
 * @since December 2015.
 * @version 1.12.5
 * @author DarkPyves
 */
public class PetMaster extends JavaPlugin {

    // Plugin options and various parameters.
    private TextComponent chatHeader;
    private int serverVersion;

    // Fields related to file handling.
    private CommentedYamlConfiguration config;
    private CommentedYamlConfiguration lang;

    // Plugin listeners.
    private PlayerInteractListener playerInteractListener;
    private PlayerLeashListener playerLeashListener;
    private PlayerQuitListener playerQuitListener;
    private PlayerAttackListener playerAttackListener;
    private PlayerTameListener playerTameListener;
    private PlayerBreedListener playerBreedListener;

    // Additional classes related to plugin commands.
    private HelpCommand helpCommand;
    private InfoCommand infoCommand;
    private SetOwnerCommand setOwnerCommand;
    private FreeCommand freeCommand;
    private EnableDisableCommand enableDisableCommand;
    private ReloadCommand reloadCommand;
    private SetColorCommand setColorCommand;
    private ShareCommand shareCommand;
    private PetInvincibleCommand petInvincibleCommand;
    private PetSkillCommand petSkillCommand;

    // Messaging System.
    private BukkitAudiences adventure;
    private MessageSender messageSender;

    public @NotNull BukkitAudiences adventure() {

        if (this.adventure == null) {

            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");

        }

        return this.adventure;

    }

    /**
     * Called when server is launched or reloaded.
     */
    @Override
    public void onEnable() {

        // Start enabling plugin.
        final long startTime = System.currentTimeMillis();

        // Initializing the Messaging System
        this.adventure = BukkitAudiences.create(this);
        this.messageSender = new MessageSender(this);

        getLogger().info("Server version..." + Bukkit.getServer().getBukkitVersion());
        getLogger().info("Registered subVersion..."
                + Bukkit.getServer().getBukkitVersion().replace(".", ",").split(",")[1].split("-")[0]);
        getLogger().info("Registering listeners...");

        serverVersion = Integer
                .parseInt(Bukkit.getServer().getBukkitVersion().replace(".", ",").split(",")[1].split("-")[0]);

        playerInteractListener = new PlayerInteractListener(this);
        playerLeashListener = new PlayerLeashListener(this);
        playerQuitListener = new PlayerQuitListener(this);
        playerTameListener = new PlayerTameListener(this);
        playerBreedListener = new PlayerBreedListener(this);

        final PluginManager pm = getServer().getPluginManager();
        // Register listeners.
        pm.registerEvents(playerInteractListener, this);
        pm.registerEvents(playerLeashListener, this);
        pm.registerEvents(playerQuitListener, this);
        pm.registerEvents(playerTameListener, this);
        if (getServerVersion() >= 10) {

            pm.registerEvents(playerBreedListener, this);

        }

        extractParametersFromConfig(true);

        PetAbilityFile.petAbilitySetup();
        PetAbilityFile.getPetAbilities().options().copyDefaults(true);
        PetAbilityFile.petAbilitySave();

        chatHeader = Component.text("<gray>[<gold>\u25b2<gray>]");

        final File playerColorConfig = new File(getDataFolder() + File.separator + "playersettings.yml");

        helpCommand = new HelpCommand(this);
        infoCommand = new InfoCommand(this);
        setOwnerCommand = new SetOwnerCommand(this);
        freeCommand = new FreeCommand(this);
        enableDisableCommand = new EnableDisableCommand(this);
        reloadCommand = new ReloadCommand(this);
        setColorCommand = new SetColorCommand(this, playerColorConfig);
        shareCommand = new ShareCommand(this);
        petInvincibleCommand = new PetInvincibleCommand(this);
        petSkillCommand = new PetSkillCommand(this);

        // Warn if an outdated entry is contained in the language file
        if (lang.contains("petmaster-command-info-hover")) {

            getLogger().log(Level.WARNING,
                    "Your language file contains outdated entrys! It is highly reccomended to delete it and let it regenerate so that all messages appear correctly.");

        }

        if (getServer().getPluginManager().isPluginEnabled(this)) {

            getLogger()
                    .info("Plugin enabled and ready to run! Took " + (System.currentTimeMillis() - startTime) + "ms.");

        }

    }

    /**
     * Extracts plugin parameters from the configuration file.
     *
     * @param attemptUpdate
     */
    public void extractParametersFromConfig(boolean attemptUpdate) {

        getLogger().info("Backing up and loading configuration files...");

        config = loadAndBackupYamlConfiguration("config.yml");
        lang = loadAndBackupYamlConfiguration(config.getString("languageFileName", "lang.yml"));

        if (!getServer().getPluginManager().isPluginEnabled(this)) {

            return;

        }

        playerInteractListener.extractParameters();
        playerLeashListener.extractParameters();

        if (config.getBoolean("disablePlayerDamage", false)) {

            if (playerAttackListener == null) {

                playerAttackListener = new PlayerAttackListener(this);
                getServer().getPluginManager().registerEvents(playerAttackListener, this);
                playerAttackListener.extractParameters();

            }

        } else {

            if (playerAttackListener != null) {

                HandlerList.unregisterAll(playerAttackListener);
                playerAttackListener = null;

            }

        }

    }

    /**
     * Loads and backs up file fileName.
     *
     * @param fileName
     * @return the loaded CommentedYamlConfiguration
     */
    private CommentedYamlConfiguration loadAndBackupYamlConfiguration(String fileName) {

        final CommentedYamlConfiguration yamlConfiguration = new CommentedYamlConfiguration(fileName, this);
        try {

            yamlConfiguration.loadConfiguration();

        } catch (IOException | InvalidConfigurationException e) {

            getLogger().severe("Error while loading " + fileName + " file, disabling plugin.");
            getLogger().log(Level.SEVERE,
                    "Verify your syntax by visiting yaml-online-parser.appspot.com and using the following logs: ", e);
            getServer().getPluginManager().disablePlugin(this);

        }

        try {

            yamlConfiguration.backupConfiguration();

        } catch (IOException error) {

            getLogger().log(Level.SEVERE, "Error while backing up configuration file: ", error);

        }

        return yamlConfiguration;

    }

    /**
     * Called when server is stopped or reloaded.
     */
    @Override
    public void onDisable() {

        // Closing Adventure API
        if (this.adventure != null) {

            this.adventure.close();
            this.adventure = null;

        }

        getLogger().info("PetMaster has been disabled.");

    }

    /**
     * Called when a player or the console enters a command.
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!StringUtils.equalsIgnoreCase("petm", cmd.getName())) {

            return false;

        }

        if (args.length == 0 || args.length == 1 && StringUtils.equalsIgnoreCase("help", args[0])) {

            helpCommand.getHelp(sender);

        } else if (StringUtils.equalsIgnoreCase("info", args[0])) {

            infoCommand.getInfo(sender);

        } else if (StringUtils.equalsIgnoreCase("reload", args[0])) {

            reloadCommand.reload(sender);

        } else if (StringUtils.equalsIgnoreCase("disable", args[0])) {

            enableDisableCommand.setState(sender, true);

        } else if (StringUtils.equalsIgnoreCase("enable", args[0])) {

            enableDisableCommand.setState(sender, false);

        } else if (StringUtils.equalsIgnoreCase("setowner", args[0]) && sender instanceof Player) {

            setOwnerCommand.setOwner(((Player) sender), args);

        } else if (StringUtils.equalsIgnoreCase("free", args[0]) && sender instanceof Player) {

            freeCommand.freePet(((Player) sender), args);

        } else if (StringUtils.equalsIgnoreCase("setcolor", args[0]) && sender instanceof Player) {

            setColorCommand.setColor(((Player) sender), args);

        } else if (StringUtils.equalsIgnoreCase("sharepet", args[0]) && sender instanceof Player) {

            shareCommand.sharePetCommand((Player) sender);

        } else if (StringUtils.equalsIgnoreCase("godpet", args[0]) && sender instanceof Player) {

            petInvincibleCommand.godPetCommand((Player) sender);

        } else if (StringUtils.equalsIgnoreCase("petskill", args[0]) && sender instanceof Player) {

            petSkillCommand.petSkillCommand((Player) sender);

        } else {

            getMessageSender().sendMessage(sender, "misused-command");

        }

        return true;

    }

    public int getServerVersion() {

        return serverVersion;

    }

    public TextComponent getChatHeader() {

        return chatHeader;

    }

    public CommentedYamlConfiguration getPluginConfig() {

        return config;

    }

    public CommentedYamlConfiguration getPluginLang() {

        return lang;

    }

    public SetOwnerCommand getSetOwnerCommand() {

        return setOwnerCommand;

    }

    public FreeCommand getFreeCommand() {

        return freeCommand;

    }

    public EnableDisableCommand getEnableDisableCommand() {

        return enableDisableCommand;

    }

    public SetColorCommand getSetColorCommand() {

        return setColorCommand;

    }

    public MessageSender getMessageSender() {

        return messageSender;

    }

}