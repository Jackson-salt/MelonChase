package me.jacksoneng.melonChase;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    public static Server server = Bukkit.getServer();
    
    @Override
    public void onEnable()
    {
        server.getPluginManager().registerEvents(new MelonChase(), this);
        
        server.getConsoleSender().sendMessage("MelonChase enabled");
    }
    
    @Override
    public void onDisable()
    {
        server.getConsoleSender().sendMessage("MelonChase disabled");
    }
}
