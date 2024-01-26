package io.github.solbangul.listener;

import io.github.solbangul.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final FileConfiguration config;

    public BlockBreakListener(Main main) {
        this.config = main.getConfig();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (config.contains(player.getUniqueId() + ".mode")) return;
        long chunkKey = event.getBlock().getChunk().getChunkKey();
        String chunkPlayer = "찾을 수 없음";
        boolean isChunkOwnedByAnyPlayerOnline = false;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (config.getLongList(onlinePlayer.getUniqueId() + ".chunk").contains(chunkKey)) {
                isChunkOwnedByAnyPlayerOnline = true;
                chunkPlayer = onlinePlayer.getName();
                break;
            }
        }
        boolean isChunkOwnedByAnyPlayerOffline = false;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (config.getLongList(offlinePlayer.getUniqueId() + ".chunk").contains(chunkKey)) {
                isChunkOwnedByAnyPlayerOffline = true;
                break;
            }
        }
        if (isChunkOwnedByAnyPlayerOnline | isChunkOwnedByAnyPlayerOffline) {
            if (config.getLongList(player.getUniqueId() + ".chunk").contains(chunkKey)) {
                return;
            }
            event.setCancelled(true);
            player.sendMessage("§c다른 플레이어가 소유하고 있는 청크입니다.\n" + chunkPlayer);
        }
    }
}
