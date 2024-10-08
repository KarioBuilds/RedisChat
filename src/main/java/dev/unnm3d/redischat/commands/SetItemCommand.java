package dev.unnm3d.redischat.commands;

import dev.unnm3d.redischat.RedisChat;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class SetItemCommand implements CommandExecutor, TabCompleter {
    private RedisChat plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            plugin.getComponentProvider().sendMessage(sender, plugin.messages.noConsole);
            return true;
        }

        if (args.length == 0) {
            plugin.getComponentProvider().sendMessage(sender, plugin.messages.missing_arguments);
            return true;
        }
        plugin.guiSettings.setIngredient(args[0], player.getInventory().getItemInMainHand());
        plugin.saveGuiSettings();
        plugin.getComponentProvider().sendMessage(sender, plugin.messages.itemSet);

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.stream(plugin.guiSettings.getClass().getDeclaredFields())
                    .filter(field -> field.getType().equals(ItemStack.class))
                    .map(Field::getName)
                    .filter(name -> name.startsWith(args[0]))
                    .toList();
        }
        return List.of();
    }
}
