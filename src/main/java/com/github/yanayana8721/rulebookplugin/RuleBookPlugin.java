package com.github.yanayana8721.rulebookplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class RuleBookPlugin extends JavaPlugin implements Listener {

    public boolean setting = false;
    public ItemStack RuleBook ;

    @Override
    public void onEnable() {
        this.getCommand("rulebook").setExecutor(this);
        this.getCommand("rulebook").setTabCompleter(new TabComp());
        Bukkit.getPluginManager().registerEvents(this, this);
        getServer().getLogger().info(ChatColor.AQUA+"RuleBookPlugin by yanaaaaa");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
        if (cmd.getName().equals("rulebook")) {
            if (!(sender.isOp())) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:このコマンドの実行にはOP権限が必要だよ~！");
            } else if (args.length > 1) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が多いよ~！");
            } else if (args.length < 1) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が少ないよ~！");
            } else {
                if(args[0].equals("on")||args[0].equals("off")){
                    if(args[0].equals("on")){
                        if(RuleBook == null){
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:RuleBookがセットされていません。記述した本を持って/rulebook setコマンドを実行してください！");
                        }else {
                            setting = true;
                            sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:ログイン時の本のオープンを"+args[0]+"にしました！");
                        }
                    } else{
                        setting = false;
                        sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:ログイン時の本のオープンを"+args[0]+"にしました！");
                    }

                }else if(args[0].equals("set")){
                    Player p = (Player) sender;
                    if(p.getItemInHand().getType() == null){
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:記述した本を持ってコマンドを実行してください！");
                    } else if(p.getItemInHand().getType() == Material.WRITTEN_BOOK){
                        RuleBook = p.getItemInHand();
                        sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:RuleBookを設定できました！");
                    }else{
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:記述した本を持ってコマンドを実行してください！");
                    }
                }else if(args[0].equals("show")){
                    Player p = (Player) sender;
                    if(RuleBook == null){
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:RuleBookがセットされていません。記述した本を持って/rulebook setコマンドを実行してください！");
                    }else {
                        p.openBook(RuleBook);
                    }
                }else{
                    sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が違うよ~！");
                }
            }
        }
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if(RuleBook == null){
        }else {
            if (setting == true) {
                p.openBook(RuleBook);
            }
        }
    }

}
