package bow.nox.sentientweapons.commands;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import bow.nox.sentientweapons.items.Sword;

public class GiveSword implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player && command.getName().equalsIgnoreCase("GiveSentientSword")) {
            Player player = (Player) sender;

            player.getInventory().addItem(Sword.SentientSword);
            return true;

        } else {
            sender.sendMessage("Only players can use that command.");
            return true;
        }
    }
}
