package dev.codingbear.asm;

import dev.codingbear.asm.command.AsmCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    public static Main getMain() {
        return (Main) Bukkit.getPluginManager().getPlugin("ArmorStandModel");
    }

    public static File getPath(String path) {
        File target = getMain().getDataFolder().toPath().resolve(path).toFile();
        if (!target.exists()) {
            target.mkdirs();
        }
        return target;
    }

    @Override
    public void onEnable() {
        new AsmCommand().command().register();
        Bukkit.getPluginManager().registerEvents(new Events(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
