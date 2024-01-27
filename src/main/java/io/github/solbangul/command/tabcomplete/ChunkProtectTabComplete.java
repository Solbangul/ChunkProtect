package io.github.solbangul.command.tabcomplete;

import io.github.solbangul.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkProtectTabComplete implements TabCompleter {

    private final FileConfiguration config;

    public ChunkProtectTabComplete(Main main) {
        this.config = main.getConfig();
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return Collections.emptyList();
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();

            completions.add("생성");
            completions.add("제거");
            completions.add("리로드");
            completions.add("관리자모드");
            completions.add("강제제거");
            completions.add("화이트리스트");

            return completions;
        }
        if (args.length == 2) {
            if (args[0].equals("화이트리스트")) {
                List<String> completions = new ArrayList<>();

                completions.add("추가");
                completions.add("삭제");

                return completions;
            }
        }
        if (args.length == 3) {
            switch (args[1]) {
                case "추가" -> {
                    List<String> completions = new ArrayList<>();

                    for (Player playerAll : Bukkit.getOnlinePlayers()) {
                        if (playerAll.getName().startsWith(args[2])) {
                            completions.add(playerAll.getName());
                        } else {
                            completions.add(playerAll.getName());
                        }
                    }

                    return completions;
                }
                case "삭제" -> {
                    return config.getStringList(player.getUniqueId() + ".whitelist");
                }
            }
        }
        return Collections.emptyList();
    }
}
