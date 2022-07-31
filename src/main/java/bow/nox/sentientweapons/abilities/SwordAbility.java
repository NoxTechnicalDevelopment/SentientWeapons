package bow.nox.sentientweapons.abilities;

import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.EulerAngle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.player.PlayerInteractEvent;

import bow.nox.sentientweapons.SentientWeapons;

import static bow.nox.sentientweapons.ai.SwordAi.summonSentientSword;

public class SwordAbility implements Listener {

    SentientWeapons plugin;

    public SwordAbility(SentientWeapons plugin) { this.plugin = plugin; }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            Player player = e.getPlayer();

            if(player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().getLore().contains("§7to create a sentient sword.")) {
                ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0,0.5, 0), EntityType.ARMOR_STAND);

                as.setVisible(false); //TODO: Bug where armor stand is visible for 1 tick. Maybe spawn it somewhere else and mot to player?
                as.setGravity(false);
                as.setArms(true);
                as.setSmall(true);
                as.setMarker(true);
                as.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
                as.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(0), Math.toRadians(0)));

                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                Location dest = player.getLocation().add(player.getLocation().getDirection().multiply(10));
                Vector vector = dest.subtract(player.getLocation()).toVector();

                vector.setY(0);

                new BukkitRunnable() {

                    final int distance = 8;
                    int i = 0;

                    public void run() {

                        EulerAngle rot = as.getRightArmPose();
                        EulerAngle rotnew = rot.add(20, 0, 0);

                        as.setRightArmPose(rotnew);
                        as.teleport(as.getLocation().add(vector.normalize()));

                        if (as.getTargetBlockExact(1) != null && !as.getTargetBlockExact(1).isPassable()) {
                            if(!as.isDead()){
                                summonSentientSword(player, as.getLocation(), plugin);
                                as.remove();
                                cancel();
                            }
                        }

                        if(i > distance) {
                            if(!as.isDead()){
                                summonSentientSword(player, as.getLocation(), plugin);
                                as.remove();
                                cancel();
                            }
                        }

                        i++;
                    }
                }.runTaskTimer(plugin, 0L, 1L);

                e.setCancelled(true);
            }
        }
    }
}
