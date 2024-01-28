package io.github.solbangul;

import io.github.solbangul.command.ChunkProtectCommand;
import io.github.solbangul.command.tabcomplete.ChunkProtectTabComplete;
import io.github.solbangul.listener.BlockBreakListener;
import io.github.solbangul.listener.BlockPlaceListener;
import io.github.solbangul.listener.InteractListener;
import io.github.solbangul.listener.PlayerJoinListener;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerEvents(
            new BlockBreakListener(instance),
            new BlockPlaceListener(instance),
            new InteractListener(instance),
            new PlayerJoinListener(instance)
        );

        getCommand("청크보호").setExecutor(new ChunkProtectCommand(instance));
        getCommand("청크보호").setTabCompleter(new ChunkProtectTabComplete(instance));
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, instance);
        }
    }
}