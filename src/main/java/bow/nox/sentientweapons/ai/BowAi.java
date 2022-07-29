package bow.nox.sentientweapons.ai;

import org.bukkit.entity.*;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import bow.nox.sentientweapons.SentientWeapons;

import java.util.List;
import java.util.ArrayList;

public class BowAi {

    static List<LivingEntity> shotAt = new ArrayList<>();

    public static void summonSentientBow(Player player, Location preLocation, SentientWeapons plugin) {
        Location location = preLocation.subtract(0, 0.5, 0);

        ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);

        as.setVisible(false);//temp //TODO: Bug where armor stand is visible for 1 tick. Maybe spawn it somewhere else?
        as.setGravity(false);
        as.setArms(true);
        as.setSmall(false);
        as.setMarker(false);
        as.getEquipment().setItemInMainHand(new ItemStack(Material.BOW));
        as.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(0), Math.toRadians(0)));
        startAi(as, player, plugin);
    }

    private static void startAi(ArmorStand as, Player player, SentientWeapons plugin) {

        new BukkitRunnable() {

            public void run() {

                //double pitch = Math.toDegrees(Math.acos(player.getLocation().getY() - as.getLocation().getY() / distanceY)) - 90.0D;
                //as.setRightArmPose(new EulerAngle(pitch, Math.toRadians(0), Math.toRadians(0)));

                if(as.isDead()) { cancel(); }

                for(Entity entity : as.getNearbyEntities(5, 5, 5)) {
                    if(!(entity instanceof Player) && entity instanceof LivingEntity) {
                        LivingEntity livingentity = (LivingEntity) entity;

                        float yaw = (float) Math.toDegrees(Math.atan2(livingentity.getLocation().getZ() - as.getLocation().getZ(), livingentity.getLocation().getX() - as.getLocation().getX())) + 90;
                        as.setRotation(yaw, 0);

                        if(!shotAt.contains(livingentity)) {
                            shotAt.add(livingentity);

                            new BukkitRunnable() {

                                public void run() {

                                    Arrow arrow = as.getWorld().spawn(as.getLocation().add(0, 0.8, 0), Arrow.class);
                                    arrow.setShooter(as);

                                    if (!arrow.isOnGround() && !arrow.isDead() && !livingentity.isDead() && as.getNearbyEntities(5, 5, 5).contains(livingentity)) {
                                        if (!livingentity.isInvulnerable()) {
                                            arrow.setVelocity((livingentity.getEyeLocation().subtract(arrow.getLocation())).toVector().multiply(0.4));
                                        }
                                    } else {
                                        if (!arrow.isDead()) {
                                            arrow.remove();
                                        }
                                        shotAt.remove(livingentity);
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 20L);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }
}
