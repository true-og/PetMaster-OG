package com.hm.petmaster.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hm.petmaster.PetMaster;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class MessageSender {

    private final PetMaster plugin;
    private final MiniMessage miniMessage;

    public MessageSender(PetMaster plugin) {

        this.plugin = plugin;
        miniMessage = MiniMessage.miniMessage();

    }

    public Component parseMessage(String message, TagResolver... tagResolvers) {

        final List<TagResolver> allTagResolvers = new ArrayList<>(Arrays.stream(tagResolvers).toList());
        allTagResolvers.add(Placeholder.parsed("prefix",
                plugin.getPluginLang().getString("petmaster-prefix", "<gray>[<gold>♞<gray>] ")));
        return miniMessage.deserialize(message, allTagResolvers.toArray(new TagResolver[] {}));

    }

    public void sendComponent(Player player, Component component) {

        player.sendMessage(component);

    }

    public void sendMessage(Audience audience, String key, TagResolver... tagResolvers) {

        audience.sendMessage(parseMessage(plugin.getPluginLang().getString(key), tagResolvers));

    }

    public void sendMessage(Player player, String key, TagResolver... tagResolvers) {

        sendMessage((Audience) player, key, tagResolvers);

    }

    public void sendMessage(CommandSender sender, String key, TagResolver... tagResolvers) {

        sendMessage((Audience) sender, key, tagResolvers);

    }

    public void sendNewLine(CommandSender sender, boolean sendPrefix) {

        if (sendPrefix) {

            sendMessage((Audience) sender, "petmaster-prefix");

        } else {

            sender.sendMessage(Component.newline());

        }

    }

}
