package dev.anhcraft.serverbenchmark;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandAlias("bench")
public class Command extends BaseCommand {
    private final ServerBenchmark serverBenchmark;

    public Command(ServerBenchmark serverBenchmark) {
        this.serverBenchmark = serverBenchmark;
    }

    @Subcommand("entity start")
    @Description("Starts an entity benchmark")
    @CommandPermission("tron.tron.vn")
    public void entityStart(Player p, EntityType type, int amount, int seconds, int threshold) {
        Location loc = p.getLocation();
        serverBenchmark.scheduler = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < amount; i++) {
                    Entity e = loc.getWorld().spawnEntity(loc, type);
                    e.setInvulnerable(true);
                }
                sendMessage(p, String.format("Total: %s entities; TPS: %.2f", loc.getWorld().getEntityCount(), Bukkit.getTPS()[0]));
                if (Bukkit.getTPS()[0] < threshold) {
                    sendMessage(p, "TPS too low, stopping benchmark");
                    entityEnd(p);
                }
            }
        }.runTaskTimer(serverBenchmark, 0, seconds * 20L);
    }

    private void sendMessage(Player p, String s) {
        p.sendMessage(s);
        Bukkit.getConsoleSender().sendMessage(s);
    }

    @Subcommand("entity end")
    @Description("Ends the entity benchmark")
    @CommandPermission("tron.tron.vn")
    public void entityEnd(CommandSender sender) {
        if (serverBenchmark.scheduler == null) {
            sender.sendMessage("No entity benchmark running");
            return;
        }
        serverBenchmark.scheduler.cancel();
        serverBenchmark.scheduler = null;
        sender.sendMessage("Benchmark ended");
    }
}
