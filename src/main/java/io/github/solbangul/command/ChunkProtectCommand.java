package io.github.solbangul.command;

import io.github.solbangul.Main;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class ChunkProtectCommand implements CommandExecutor {

    private final FileConfiguration config;
    private final JavaPlugin plugin;

    public ChunkProtectCommand(Main main) {
        this.config = main.getConfig();
        this.plugin = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (args.length == 1) {
            switch (args[0]) {
                case "생성" -> {
                    long chunk = player.getChunk().getChunkKey();
                    for (Player allPlayer : Bukkit.getOnlinePlayers()) {
                        if (config.getLongList(allPlayer.getUniqueId() + ".chunk").contains(chunk)) {
                            player.sendMessage("§c해당 청크는 다른 플레이어가 가지고 있습니다.");
                            return false;
                        }
                    }
                    if (config.getInt("max-chunk-count") == config.getLongList(player.getUniqueId() + ".chunk").size()) {
                        player.sendMessage("§c당신은 이미 최대 10 청크를 보유하고 있습니다.");
                        return false;
                    }
                    if (config.getLongList(player.getUniqueId() + ".chunk").contains(chunk)) {
                        player.sendMessage("§c이미 당신이 가지고 있는 구역입니다.");
                        return false;
                    }
                    List<Long> playerChunk = config.getLongList(player.getUniqueId() + ".chunk");
                    playerChunk.add(chunk);
                    config.set(player.getUniqueId() + ".chunk", playerChunk);
                    config.set(player.getUniqueId() + ".name", player.getName());
                    plugin.saveConfig();

                    player.sendMessage("§a해당 청크를 다른 플레이어로부터 보호합니다.");
                    return false;
                }
                case "제거" -> {
                    long chunk = player.getChunk().getChunkKey();
                    if (!config.getLongList(player.getUniqueId() + ".chunk").contains(chunk)) {
                        player.sendMessage("§c해당 청크를 가지고 있지 않습니다.");
                        return false;
                    }
                    List<Long> playerChunk = config.getLongList(player.getUniqueId() + ".chunk");
                    playerChunk.remove(chunk);
                    config.set(player.getUniqueId() + ".chunk", playerChunk);
                    plugin.saveConfig();

                    player.sendMessage("§a해당 청크를 보호를 제거했습니다.");
                    return false;
                }
                case "리로드" -> {
                    if (!player.isOp()) {
                        player.sendMessage("§c당신은 관리자가 아닙니다.");
                        return false;
                    }
                    plugin.reloadConfig();
                    plugin.saveConfig();
                    player.sendMessage("§aConfig를 리로드했습니다.");
                }
                case "강제제거" -> {
                    if (!player.isOp()) {
                        player.sendMessage("§c당신은 관리자가 아닙니다.");
                        return false;
                    }
                    long chunk = player.getChunk().getChunkKey();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (config.getLongList(onlinePlayer.getUniqueId() + ".chunk").contains(chunk)) {
                            List<Long> playerChunk = config.getLongList(onlinePlayer.getUniqueId() + ".chunk");
                            playerChunk.remove(chunk);
                            config.set(onlinePlayer.getUniqueId() + ".chunk", playerChunk);
                            break;
                        }
                    }
                    for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                        if (config.getLongList(offlinePlayer.getUniqueId() + ".chunk").contains(chunk)) {
                            List<Long> playerChunk = config.getLongList(offlinePlayer.getUniqueId() + ".chunk");
                            playerChunk.remove(chunk);
                            config.set(offlinePlayer.getUniqueId() + ".chunk", playerChunk);
                            break;
                        }
                    }
                    plugin.saveConfig();

                    player.sendMessage("§a해당 청크를 보호를 제거했습니다.");
                    return false;
                }
                case "관리자모드" -> {
                    if (!player.isOp()) {
                        player.sendMessage("§c당신은 관리자가 아닙니다.");
                        return false;
                    }
                    if (config.contains(player.getUniqueId() + ".mode")) {
                        config.set(player.getUniqueId() + ".mode", null);
                        plugin.saveConfig();

                        player.sendMessage("§a관리자 모드를 종료했습니다.");
                    } else {
                        config.set(player.getUniqueId() + ".mode", "op");
                        plugin.saveConfig();

                        player.sendMessage("§a관리자 모드를 시작했습니다.");
                    }
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equals("화이트리스트")) {
                switch (args[1]) {
                    case "추가" -> {
                        if (Bukkit.getPlayer(args[2]) == null) {
                            player.sendMessage("§c존재하지 않는 플레이어입니다.");
                            return false;
                        }
                        Player target_player = Bukkit.getPlayer(args[2]);
                        if (config.getStringList(player.getUniqueId() + ".whitelist").contains(target_player.getName())) {
                            player.sendMessage("§c이미 화이트리스트입니다.");
                            return false;
                        }
                        List<String> playerWhitelist = config.getStringList(player.getUniqueId() + ".whitelist");
                        playerWhitelist.add(target_player.getName());
                        config.set(player.getUniqueId() + ".whitelist", playerWhitelist);
                        plugin.saveConfig();

                        player.sendMessage("§a청크를 " + target_player.getName() + "과 공유합니다.");
                        return false;
                    }
                    case "삭제" -> {
                        if (Bukkit.getPlayer(args[2]) == null) {
                            player.sendMessage("§c존재하지 않는 플레이어입니다.");
                            return false;
                        }
                        Player target_player = Bukkit.getPlayer(args[2]);
                        if (!config.getStringList(player.getUniqueId() + ".whitelist").contains(target_player.getName())) {
                            player.sendMessage("§c화이트리스트가 아닙니다.");
                            return false;
                        }
                        List<String> playerWhitelist = config.getStringList(player.getUniqueId() + ".whitelist");
                        playerWhitelist.remove(target_player.getName());
                        config.set(player.getUniqueId() + ".whitelist", playerWhitelist);
                        plugin.saveConfig();

                        player.sendMessage("§a청크를 " + target_player.getName() + "과 공유를 삭제합니다.");
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
