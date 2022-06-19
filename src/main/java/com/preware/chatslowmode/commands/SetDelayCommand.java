package com.preware.chatslowmode.commands;

import com.preware.chatslowmode.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetDelayCommand implements CommandExecutor, TabCompleter {

    Main main;

    public SetDelayCommand(Main main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("chatslowmode.setdelay")) {
            if (command.getName().equalsIgnoreCase("slowmode")) {
                if (args.length < 1) {
                    sender.sendMessage(main.color("/slowmode setdelay [number]"));
                }

                if (args[0].equalsIgnoreCase("setdelay")) {

                    int arg1;
                    try {
                        arg1 = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(main.color("&e&lSlowMode &7&l→ " + args[1] + " " + " &fis not a valid number!"));
                        return false;
                    }

                    if (arg1 == Integer.parseInt(args[1])) {
                        main.getConfig().set("chatSlowMode.chatDelay", Integer.parseInt(args[1]));
                        sender.sendMessage(main.color("&e&lSlowmode delay has been set to " + arg1));
                        main.saveConfig();
                        main.reloadConfig();
                    }
                }

            }
        } else {
            sender.sendMessage(main.color("&4&lYou do not have permission for this command!"));
        }



        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> arguments = Collections.singletonList("setdelay");
        List<String> Flist = new ArrayList<>();

        if (sender.hasPermission("chatslowmode.setdelay")) {
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
