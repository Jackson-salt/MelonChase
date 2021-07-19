package me.jacksoneng.melonChase;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new MelonChase(), this);
        getServer().getConsoleSender().sendMessage("MelonChase enabled");
    }

    @Override
    public void onDisable()
    {
        getServer().getConsoleSender().sendMessage("MelonChase disabled");
    }
}
