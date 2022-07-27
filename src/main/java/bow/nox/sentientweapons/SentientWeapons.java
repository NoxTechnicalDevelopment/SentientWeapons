package bow.nox.sentientweapons;

import bow.nox.sentientweapons.abilities.BowAbility;
import org.bukkit.plugin.java.JavaPlugin;

import bow.nox.sentientweapons.items.Bow;
import bow.nox.sentientweapons.commands.GiveBow;

import java.util.Objects;

public final class SentientWeapons extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bow.init();

        Objects.requireNonNull(this.getCommand("transformbow")).setExecutor(new GiveBow());

        enableItems();
    }

    private void enableItems() {
        // Register all weapons
        getServer().getPluginManager().registerEvents(new BowAbility(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
