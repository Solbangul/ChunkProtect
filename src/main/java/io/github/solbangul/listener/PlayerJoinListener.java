package io.github.solbangul.listener;

import io.github.solbangul.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener implements Listener {

    private final FileConfiguration config;
    private final JavaPlugin plugin;

    public PlayerJoinListener(Main main) {
        this.config = main.getConfig();
        this.plugin = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        config.set(player.getUniqueId() + ".name", player.getName());
        plugin.saveConfig();
    }
}
