package bow.nox.sentientweapons;

import bow.nox.sentientweapons.abilities.SwordAbility;
import bow.nox.sentientweapons.commands.GiveSword;
import org.bukkit.plugin.java.JavaPlugin;
import bow.nox.sentientweapons.items.Sword;

import bow.nox.sentientweapons.items.Bow;
import bow.nox.sentientweapons.commands.GiveBow;
import bow.nox.sentientweapons.abilities.BowAbility;

import java.util.Objects;

public final class SentientWeapons extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bow.init();
        Sword.init();

        Objects.requireNonNull(this.getCommand("givesentientbow")).setExecutor(new GiveBow());
        Objects.requireNonNull(this.getCommand("givesentientsword")).setExecutor(new GiveSword());

        enableItems();
    }

    private void enableItems() {
        // Register all weapons
        getServer().getPluginManager().registerEvents(new BowAbility(this), this);
        getServer().getPluginManager().registerEvents(new SwordAbility(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
