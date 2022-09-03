package me.obito.gradient;

import me.obito.gradient.listeners.GradListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gradient extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new GradListener(), this);
        System.out.println(ChatColor.GREEN + "PLUGIN ON");
    }

    @Override
    public void onDisable() {
        System.out.println(ChatColor.RED + "PLUGIN OFF");
    }
}
