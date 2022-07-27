package bow.nox.sentientweapons.commands;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import bow.nox.sentientweapons.items.Bow;
import org.bukkit.command.CommandExecutor;

public class GiveBow implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player && command.getName().equalsIgnoreCase("TransformBow")) {
            Player player = (Player) sender;

            player.getInventory().addItem(Bow.SentientBow);
            return true;

        } else {
            sender.sendMessage("Only players can use that command.");
            return true;
        }
    }
}
