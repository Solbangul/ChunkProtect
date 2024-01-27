package io.github.solbangul.listener;

import io.github.solbangul.Main;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener {

    private final FileConfiguration config;

    public InteractListener(Main main) {
        this.config = main.getConfig();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (config.contains(player.getUniqueId() + ".mode")) return;
        if (event.getClickedBlock() == null) {
            return;
        }
        long chunkKey = event.getClickedBlock().getChunk().getChunkKey();
        String chunkPlayer = "찾을 수 없음";
        boolean isChunkOwnedByAnyPlayerOnline = false;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (config.getLongList(onlinePlayer.getUniqueId() + ".chunk").contains(chunkKey)) {
                if (config.getStringList(onlinePlayer.getUniqueId() + ".whitelist").contains(player.getName())) {
                    break;
                }
                isChunkOwnedByAnyPlayerOnline = true;
                chunkPlayer = onlinePlayer.getName();
                break;
            }
        }
        boolean isChunkOwnedByAnyPlayerOffline = false;
        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (config.getLongList(offlinePlayer.getUniqueId() + ".chunk").contains(chunkKey)) {
                if (config.getStringList(offlinePlayer.getUniqueId() + ".whitelist").contains(player.getName())) {
                    break;
                }
                isChunkOwnedByAnyPlayerOffline = true;
                chunkPlayer = config.getString(offlinePlayer.getUniqueId() + ".name");
                break;
            }
        }
        if (isChunkOwnedByAnyPlayerOnline | isChunkOwnedByAnyPlayerOffline) {
            if (config.getLongList(player.getUniqueId() + ".chunk").contains(chunkKey)) {
                return;
            }
            event.setCancelled(true);
            player.sendActionBar(Component.text("§c해당 청크는 보호를 받는 중입니다. " + chunkPlayer));
        }
    }
}
