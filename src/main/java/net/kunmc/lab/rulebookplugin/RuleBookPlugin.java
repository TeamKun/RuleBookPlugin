package net.kunmc.lab.rulebookplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RuleBookPlugin extends JavaPlugin implements Listener , TabCompleter {

    public List<ItemStack> Books = new ArrayList<>();
    public ItemStack RuleBook ;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        setBooks(Books);
        if(getConfig().getItemStack("JoinBook").getType() == Material.WRITTEN_BOOK){
            RuleBook = config.getItemStack("JoinBook");
        }
        saveConfig();
        this.getCommand("rulebook").setExecutor(this);
        this.getCommand("rulebook").setTabCompleter(this);
        Bukkit.getPluginManager().registerEvents(this, this);
        getServer().getLogger().info(ChatColor.AQUA+"RuleBookPlugin by yanaaaaa");
    }

    public void setBooks(List<ItemStack> s){
        FileConfiguration config = getConfig();
        reloadConfig();
        saveDefaultConfig();
        List<ItemStack> i;
        i = (List<ItemStack>) config.get("BookList");
        if (i==null){
            s = new ArrayList<>();
        }else {
            for (int n = 0; n < i.size(); n++) {
                if (i.get(n).getType() == Material.WRITTEN_BOOK) {
                        s.add(i.get(n));
                }
            }
        }
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
        if (cmd.getName().equals("rulebook")) {
            if (!(sender.isOp())) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:このコマンドの実行にはOP権限が必要だよ~！");
            } else if (args.length > 3) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が多いよ~！コマンド一覧の確認:/rulebook help");
            } else if (args.length < 1) {
                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が少ないよ~！コマンド一覧の確認:/rulebook help");
            } else {
                if (args[0].equals("add")) {
                    if (args.length != 1) {
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook add ");
                    } else {
                        Player p = (Player) sender;
                        if (p.getItemInHand().getType() == null) {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:記述した本を持ってコマンドを実行してください！");
                        } else if (p.getItemInHand().getType() == Material.WRITTEN_BOOK) {
                            if (Books.contains(p.getItemInHand())) {
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに同じ本が存在します!");
                            } else {
                                Books.add(p.getItemInHand());
                                sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:リストに本を追加しました！");
                            }
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:記述した本を持ってコマンドを実行してください！");
                        }
                    }
                } else if (args[0].equals("list")) {
                    if (args.length != 1) {
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook list ");
                    } else {
                        if (Books.size() == 0) {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに設定されている本はありません");
                        } else {
                            List<String> BookName = new ArrayList<>();
                            for (int i = 0; i < Books.size(); i++) {
                                BookMeta book = (BookMeta) Books.get(i).getItemMeta();
                                BookName.add(book.getTitle());
                            }
                            sender.sendMessage(ChatColor.GREEN + "==========本の一覧===========");
                            sender.sendMessage(ChatColor.AQUA + String.valueOf(BookName));
                        }
                    }
                } else if (args[0].equals("new")) {
                    if (args.length != 1) {
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook new ");
                    } else {
                        Player p = (Player) sender;
                        ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                        p.getLocation().getWorld().dropItem(p.getLocation(), item);
                        sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]" + p.getName() + "に未記入の本を与えました！");
                    }
                } else if (args[0].equals("remove")) {
                    if (args.length != 2) {
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook remove <本のタイトル> ");
                    } else {
                        List<String> BookName = new ArrayList<>();
                        for (int i = 0; i < Books.size(); i++) {
                            BookMeta book = (BookMeta) Books.get(i).getItemMeta();
                            BookName.add(book.getTitle());
                        }
                        if (BookName.contains(args[1])) {
                            int n = BookName.indexOf(args[1]);
                            Books.remove(n);
                            sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:リストから" + args[1] + "を削除しました！");
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに" + args[1] + "は存在しません！");
                        }

                    }
                } else if (args[0].equals("show")) {
                    if (args.length != 3) {
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook show <本のタイトル> <対象プレイヤー> ");
                    } else {
                        List<Entity> player = Bukkit.selectEntities(sender, args[2]);
                        List<String> BookName = new ArrayList<>();
                        for (int i = 0; i < Books.size(); i++) {
                            BookMeta book = (BookMeta) Books.get(i).getItemMeta();
                            BookName.add(book.getTitle());
                        }
                        if (player.size() <= 0) {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:対象のプレイヤーは存在しません！");
                        } else {
                            if (BookName.contains(args[1])) {
                                int n = BookName.indexOf(args[1]);
                                for (int j = 0; j < player.size(); j++) {
                                    if (player.get(j) instanceof Player) {
                                        Player p = (Player) player.get(j);
                                        p.openBook(Books.get(n));
                                    }
                                }
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに" + args[1] + "は存在しません！");
                            }
                        }
                    }
                } else if (args[0].equals("give")) {
                    if (args.length != 3) {
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook give <本のタイトル> <対象プレイヤー> ");
                    } else {
                        List<Entity> player = Bukkit.selectEntities(sender, args[2]);
                        List<String> BookName = new ArrayList<>();
                        for (int i = 0; i < Books.size(); i++) {
                            BookMeta book = (BookMeta) Books.get(i).getItemMeta();
                            BookName.add(book.getTitle());
                        }
                        if (player.size() <= 0) {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:対象のプレイヤーは存在しません！");
                        } else {
                            if (BookName.contains(args[1])) {
                                int n = BookName.indexOf(args[1]);
                                for (int j = 0; j < player.size(); j++) {
                                    if (player.get(j) instanceof Player) {
                                        Player p = (Player) player.get(j);
                                        ItemStack item = Books.get(n);
                                        p.getLocation().getWorld().dropItem(p.getLocation(), item);
                                    }
                                }
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに" + args[1] + "は存在しません！");
                            }
                        }
                    }
                }else if(args[0].equals("on-join")){
                    if(args.length>=2){
                    if(args[1].equals("get")){
                        if(args.length!=2){
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook on-join get ");
                        }else {
                            if(RuleBook==null||RuleBook.getType()!=Material.WRITTEN_BOOK) {
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:本は登録されていません");
                            }else{
                                Player p = (Player) sender;
                                p.openBook(RuleBook);
                            }
                        }
                    }else if(args[1].equals(("remove"))){
                        if(args.length==2){
                            ItemStack item = new ItemStack(Material.STICK);
                            RuleBook = item;
                            sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]JoinBookに設定された本を削除しました！");
                        }else {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook on-join remove ");
                        }
                    }else if(args[1].equals("set")) {
                        if (args.length == 3) {
                            List<String> BookName = new ArrayList<>();
                            for (int i = 0; i < Books.size(); i++) {
                                BookMeta book = (BookMeta) Books.get(i).getItemMeta();
                                BookName.add(book.getTitle());
                            }
                            if (BookName.contains(args[2])) {
                                int n = BookName.indexOf(args[2]);
                                RuleBook = Books.get(n);
                                sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]:RuleBookを設定できました！");
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:リストに" + args[2] + "は存在しません！");
                            }
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook on-join set ");
                        }
                    }
                    }else{
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook on-join get ");
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook on-join remove ");
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook on-join set ");
                    }
                }else if(args[0].equals("config")){
                    if(args.length>=2){
                    if(args[1].equals("reload")){
                        if(args.length==2){
                            reloadConfig();
                            saveDefaultConfig();
                            Books = new ArrayList<>();
                            setBooks(Books);
                            if (getConfig().getItemStack("JoinBook").getType() == Material.WRITTEN_BOOK) {
                                RuleBook = getConfig().getItemStack("JoinBook");
                            }
                            sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]Configをリロードしました！");
                        }else {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook config reload ");
                        }
                    }else if(args[1].equals("save")) {
                        if (args.length == 2) {
                            ConfigSetting();
                            sender.sendMessage(ChatColor.GREEN + "[RuleBookPlugin]Configに現在の設定を保存しました！");
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook config save ");
                        }
                    }
                }else{
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook config reload ");
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook config save ");
                    }
                }else if(args[0].equals("help")) {
                    if (args.length != 1) {
                        sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:コマンドの形式:/rulebook help");
                    } else {
                        sender.sendMessage("------------------コマンド一覧------------------");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook help");
                        sender.sendMessage("・RuleBookPluginのコマンド一覧の表示");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook add");
                        sender.sendMessage("・右手に持っている本をリストに登録する");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook list");
                        sender.sendMessage("・リストに登録された本の表示");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook new");
                        sender.sendMessage("・記入されていない本をコマンド実行者に与える");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook remove <本のタイトル> ");
                        sender.sendMessage("・指定した本をリストから削除する");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook show <本のタイトル> <対象プレイヤー>");
                        sender.sendMessage("・指定した本を対象プレイヤーに表示する");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook give <本のタイトル> <対象プレイヤー>");
                        sender.sendMessage("・指定した本を対象プレイヤーに与える");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook on-join set <本のタイトル>");
                        sender.sendMessage("・指定した本をログイン時に表示する本に設定する");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook on-join get");
                        sender.sendMessage("・ログイン時に表示する本をコマンド実行者に表示する");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook on-join remove");
                        sender.sendMessage("・ログイン時に表示する本をを削除する");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook config reload");
                        sender.sendMessage("・コンフィグをから設定をリロードする");
                        sender.sendMessage(ChatColor.GOLD + "/rulebook config save");
                        sender.sendMessage("・コンフィグに現在の設定を保存する");
                    }
                }else{
                    sender.sendMessage(ChatColor.YELLOW + "[RuleBookPlugin]:引数が異なります！コマンド一覧の確認:/rulebook help");
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
            if (RuleBook.getType() == Material.WRITTEN_BOOK) {
                p.openBook(RuleBook);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String Label, String[] args){
        if (cmd.getName().equals("rulebook")) {
            if(args.length==1){
                return Stream.of("on-join","config","help","add","list","new","remove","show","give").filter(e -> e.startsWith(args[0])).collect(Collectors.toList());
            }if(args.length==2&&args[0].equals("on-join")){
                return Stream.of("set","get","remove").filter(e -> e.startsWith(args[1])).collect(Collectors.toList());
            }if(args.length==2&&args[0].equals("config")){
                return Stream.of("reload","save").filter(e -> e.startsWith(args[1])).collect(Collectors.toList());
            }if(args.length==2&&(args[0].equals("remove")||args[0].equals("give")||args[0].equals("show"))){
                List<String> BookName = new ArrayList<>();
                for (ItemStack itemStack : Books) {
                    BookMeta book = (BookMeta) itemStack.getItemMeta();
                    BookName.add(book.getTitle());
                }
                return BookName;
            }if(args.length==3&&(args[0].equals("give")||args[0].equals("show"))){
                List<String> PlayerName = new ArrayList<>();
                getServer().getOnlinePlayers().forEach(player ->{
                    PlayerName.add(player.getName());
                });
                PlayerName.add("@a");
                PlayerName.add("@p");
                PlayerName.add("@r");
                PlayerName.add("@s");
                PlayerName.add("@e");
                return PlayerName;
            }if(args.length==3&&args[0].equals("on-join")&&args[1].equals("set")){
                List<String> BookName = new ArrayList<>();
                for (ItemStack itemStack : Books) {
                    BookMeta book = (BookMeta) itemStack.getItemMeta();
                    BookName.add(book.getTitle());
                }
                return BookName;
            }
        }
        return null;
    }

    @Override
    public void onDisable() {
       ConfigSetting();
    }

    public  void  ConfigSetting(){
        FileConfiguration config = getConfig();
        ItemStack M = new ItemStack(Material.STICK);
        if(RuleBook != null &&RuleBook.getType() == Material.WRITTEN_BOOK){
            config.set("JoinBook",RuleBook);
        }else{
            config.set("JoinBook",M);
        }
        config.set("BookList",Books);
        saveConfig();
    }
}
