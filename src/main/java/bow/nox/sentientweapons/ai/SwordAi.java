package bow.nox.sentientweapons.ai;

import org.bukkit.entity.*;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import bow.nox.sentientweapons.SentientWeapons;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

public class SwordAi {

    static List<LivingEntity> lungedAt = new ArrayList<>();

    public static void summonSentientSword(Player player, Location preLocation, SentientWeapons plugin) {
        Location location = preLocation.subtract(0, 0.5, 0);

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        as.setVisible(false);//TODO: Bug where armor stand is visible for 1 tick. Maybe spawn it somewhere else?
        as.setGravity(false);
        as.setArms(true);
        as.setSmall(false);
        as.setMarker(false);
        as.getEquipment().setItemInMainHand(new ItemStack(Material.NETHERITE_SWORD));
        as.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(0), Math.toRadians(0)));
        startAiSword(as, player, plugin);
    }

    private static void startAiSword(ArmorStand as, Player player, SentientWeapons plugin) {

        new BukkitRunnable() {

            public void run() {

                if(as.isDead()) { cancel(); }

                for(Entity entity : as.getNearbyEntities(5, 3, 5)) {
                    if(!(entity instanceof Player) && entity instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity) entity;

                        float yaw = (float) Math.toDegrees(Math.atan2(livingentity.getLocation().getZ() - as.getLocation().getZ(), livingentity.getLocation().getX() - as.getLocation().getX())) + 90;
                        as.setRotation(yaw, 0);

                        if(!lungedAt.contains(livingentity)) {
                            lungedAt.add(livingentity);

                            new BukkitRunnable() {

                                final Location origin = as.getLocation();

                                public void run() {

                                    Location livingentityLoc = livingentity.getLocation();

                                    if (!livingentity.isDead() && Objects.requireNonNull(origin.getWorld()).getNearbyEntities(origin, 5, 3, 5).contains(livingentity) && !livingentity.isInvisible()) {
                                        if (!livingentity.isInvulnerable()) {
                                            float yawAdjustment = livingentityLoc.getYaw() + 90;

                                            if(yawAdjustment < 0) yawAdjustment += 360;

                                            double newX = Math.cos(Math.toRadians(yawAdjustment));
                                            double newZ = Math.sin(Math.toRadians(yawAdjustment));

                                            Location behindEntity = new Location(livingentity.getWorld(), livingentityLoc.getX() - newX, livingentityLoc.getY(), livingentityLoc.getZ() - newZ, livingentityLoc.getYaw(), livingentityLoc.getPitch());

                                            livingentity.damage(8, as);
                                            as.teleport(behindEntity);
                                        }
                                    } else {
                                        if (!as.isDead()) {
                                            as.teleport(origin);
                                        }
                                        lungedAt.remove(livingentity);
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 5L, 20L);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}
