package dev.anhcraft.serverbenchmark;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ServerBenchmark extends JavaPlugin {
    public BukkitTask scheduler;

    @Override
    public void onEnable() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new Command(this));
    }

}
