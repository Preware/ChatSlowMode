package com.preware.chatslowmode;

import com.preware.chatslowmode.commands.SetDelayCommand;
import com.preware.chatslowmode.events.SlowChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {


    public String color(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }

    @Override
    public void onEnable() {

        System.out.println(ChatColor.BLUE + "Plugin made by Preware, for craftbabe");

        getServer().getPluginManager().registerEvents(new SlowChatEvent(this), this);

        getCommand("slowmode").setExecutor(new SetDelayCommand(this));
        getCommand("slowmode").setTabCompleter(new SetDelayCommand(this));


        getConfig().options().copyDefaults();
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        SlowChatEvent.delayTime.clear();

    }


}
