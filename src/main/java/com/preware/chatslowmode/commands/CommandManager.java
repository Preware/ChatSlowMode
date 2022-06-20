package com.preware.chatslowmode.commands;

import com.preware.chatslowmode.Main;
import com.preware.chatslowmode.events.SlowChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter {

    public Main main;

    public CommandManager(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = main.getConfig();
        FileConfiguration messagesConfig = main.getMessages();


        if (sender.hasPermission("chatslowmode.setdelay") || sender.hasPermission("chatslowmode.on") || sender.hasPermission("chatslowmode.off") || sender.hasPermission("chatslowmode.reload")) {
            if (command.getName().equalsIgnoreCase("slowmode")) {
                if (args.length < 1) {
                    sender.sendMessage(main.color("/slowmode setdelay [number]"));
                }

                if (args[0].equalsIgnoreCase("setdelay")) {

                    int arg1;
                    try {
                        arg1 = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(main.color("&e&lSlowMode &4&l→ " + args[1] + " &fis not a valid number!"));
                        return false;
                    }

                    if (arg1 == Integer.parseInt(args[1])) {
                        config.set("chatSlowMode.chatDelay", Integer.parseInt(args[1]));
                        sender.sendMessage(main.color("&e&oSlowmode delay has been set to " + arg1));
                        main.saveConfig();
                        main.reloadConfig();
                    }
                }

                if (args[0].equalsIgnoreCase("on")) {
                    if (!SlowChatEvent.slowmode) {
                        SlowChatEvent.slowmode = true;
                        sender.sendMessage(main.color("&e&lSlowMode &f&l→ Slowmode has been turned on!"));

                        for (Player player : Bukkit.getOnlinePlayers()) {

                            if (!main.color(messagesConfig.getString("messages.announcements.delayOn")).contains("%delay%")) {
                                player.sendMessage(main.color(main.getMessages().getString("messages.announcements.delayOn")));
                            } else {
                                player.sendMessage(main.color
                                        (main.getMessages().getString("messages.announcements.delayOn")
                                                .replace("%delay%", main.getConfig().getString("chatSlowMode.chatDelay"))));
                            }
                        }


                    } else {
                        sender.sendMessage(main.color("&e&lSlowMode &f&l→ Is already active!"));
                    }

                }

                if (args[0].equalsIgnoreCase("off")) {
                    if (SlowChatEvent.slowmode) {
                        SlowChatEvent.slowmode = false;
                        sender.sendMessage(main.color("&e&lSlowMode &f&l→ Slowmode has been turned off!"));

                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(main.color(main.getMessages().getString("messages.announcements.delayOff")));

                        }

                    }
                } else {
                    messagesConfig.getString("messages.commands.commandsDenied");
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    try {
                        main.getMessages().load(main.getMessagesFile());
                        sender.sendMessage(main.color("&e&lMessages file has been reloaded!"));
                    } catch (IOException | InvalidConfigurationException e) {
                        e.printStackTrace();
                    }
                }


            }

        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = Arrays.asList("setdelay", "on", "off", "reload");
        List<String> Flist = new ArrayList<>();

        if (sender.hasPermission("chatslowmode.setdelay") || sender.hasPermission("chatslowmode.on") || sender.hasPermission("chatslowmode.off") || sender.hasPermission("chatslowmode.reload")) {
            if (args.length == 1) {
                for (String s : arguments) {
                    if (s.toLowerCase().startsWith(args[0].toLowerCase())) Flist.add(s);
                }
                return Flist;
            }
            return Collections.emptyList();
        }
        return Collections.emptyList();
    }
}
