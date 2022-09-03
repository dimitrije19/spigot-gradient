package me.obito.gradient.listeners;

import me.obito.gradient.GradientImplementation;
import me.obito.gradient.GradientPresets;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.ArrayList;

public class GradListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        try{
            File customConfigFile;
            FileConfiguration customConfig;
            customConfigFile = new File(Bukkit.getPluginManager().getPlugin("Gradient").getDataFolder(), "/players/" + e.getPlayer().getUniqueId() + ".yml");
            if (!customConfigFile.exists()) {
                customConfigFile.getParentFile().mkdirs();

                try {
                    customConfigFile.createNewFile();
                    customConfig = new YamlConfiguration();
                    customConfig.load(customConfigFile);
                    customConfig.set("Player Name", e.getPlayer().getName());
                    customConfig.set("GradientEnabled", false);
                    customConfig.set("GradientStart", "#ffffff");
                    customConfig.set("GradientEnd", "#ffffff");
                    customConfig.set("GradientPreset", "none");
                    customConfig.save(customConfigFile);
                } catch (Exception e1){
                    e.getPlayer().sendMessage("NERADI2");
                }
                //Bukkit.getPluginManager().getPlugin("ChromiumCore").saveResource(e.getPlayer().getUniqueId() + ".yml", false);
            }
        } catch (Exception e1){

        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){

        File customConfigFile;
        customConfigFile = new File(Bukkit.getPluginManager().getPlugin("Gradient").getDataFolder(), "/players/" + e.getPlayer().getUniqueId() + ".yml");
        FileConfiguration customConfig;
        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (Exception e2){
            //TODO fix
            e.getPlayer().sendMessage("NERADI");
        }

        if(customConfig.getBoolean("GradientEnabled") ){

            if(customConfig.getString("GradientPreset").equals("none")){
                ArrayList<String> colors = new ArrayList<>();
                try {

                    colors.add(customConfig.getString("GradientStart"));
                    colors.add(customConfig.getString("GradientEnd"));
                    GradientImplementation g = new GradientImplementation(colors);
                    e.setMessage(g.gradientMessage(e.getMessage(), "", false));

                } catch (Exception e1) {

                    e.setMessage(e.getMessage());

                }
            } else {
                try {

                    GradientImplementation g = GradientPresets.getGradient(customConfig.getString("GradientPreset"));
                    e.setMessage(g.gradientMessage(e.getMessage(), "", false));

                } catch (Exception e1) {

                    e.setMessage(e.getMessage());

                }
            }




        } else {

            String messageDefault = e.getMessage();
            String messageColored = (ChatColor.translateAlternateColorCodes('&', messageDefault));
            String messageFinal = messageColored;
            for(Player p : Bukkit.getOnlinePlayers()){
                if(messageDefault.toLowerCase().contains(p.getName().toLowerCase())){
                    messageFinal = messageColored.replace(p.getName(), (ChatColor.translateAlternateColorCodes('&', p.getName())));
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                }
            }
            e.setMessage(messageFinal);
        }



    }

}
