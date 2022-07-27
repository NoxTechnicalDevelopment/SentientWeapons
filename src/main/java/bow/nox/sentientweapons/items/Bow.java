package bow.nox.sentientweapons.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;

import java.util.List;
import java.util.ArrayList;

public class Bow {

    public static ItemStack SentientBow;

    public static void init() {createSentientBow();}

    private static void createSentientBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();

        meta.setDisplayName("§6Sentient Bow");
        lore.add("");
        lore.add("§6Item Ability: Roll Out §e§lLEFT CLICK");
        lore.add("§7Roll this weapon on the floor");
        lore.add("§7to create a sentient bow.");

        meta.setLore(lore);
        meta.addEnchant(Enchantment.OXYGEN, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        SentientBow = item;
    }
}
