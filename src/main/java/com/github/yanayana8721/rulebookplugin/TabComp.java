package com.github.yanayana8721.rulebookplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TabComp implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String Label, String[] args){
        if (cmd.getName().equals("rulebook")) {
            if (args.length == 1) {
                return (sender.hasPermission("rulebook")
                        ? Stream.of("on", "off", "set", "show")
                        : Stream.of("on"))
                        .filter(e -> e.startsWith(args[0])).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
