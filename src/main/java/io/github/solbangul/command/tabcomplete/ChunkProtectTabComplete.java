package io.github.solbangul.command.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkProtectTabComplete implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();

            completions.add("생성");
            completions.add("제거");
            completions.add("리로드");
            completions.add("관리자모드");
            completions.add("강제제거");

            return completions;
        }
        return Collections.emptyList();
    }
}
