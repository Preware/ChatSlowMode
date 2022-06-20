package com.preware.chatslowmode;

import com.preware.chatslowmode.commands.CommandManager;
import com.preware.chatslowmode.events.SlowChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {


    public String color(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }
    private File messagesFile = new File(getDataFolder(), "messages.yml");
    private FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);

    @Override
    public void onEnable() {

        System.out.println(ChatColor.BLUE + "Plugin made by Preware, for craftbabe");

        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }


        getServer().getPluginManager().registerEvents(new SlowChatEvent(this), this);

        getCommand("slowmode").setExecutor(new CommandManager(this));
        getCommand("slowmode").setTabCompleter(new CommandManager(this));

        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        SlowChatEvent.delayTime.clear();

    }


    public FileConfiguration getMessages() {
        return messagesConfig;
    }

    public File getMessagesFile() {
        return messagesFile;
    }


}
