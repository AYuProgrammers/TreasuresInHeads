package pl.AYuPro.TreasInHead;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class tih extends JavaPlugin implements Listener {
	
	Random rand = new Random();
	List<Integer> rareitems;
	List<Integer> normalitems;
	List<Integer> veryrareitems;
	int raritynormal;
	int rarityrare;
	int rarityveryrare;
	int rarity;
	
	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		Bukkit.getServer().getLogger().info("Treasures In Heads by AYuPro Loaded");
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		
		rareitems = this.getConfig().getIntegerList("rare");
		veryrareitems = this.getConfig().getIntegerList("veryrare");
		normalitems = this.getConfig().getIntegerList("normal");
		
		raritynormal = this.getConfig().getInt("rarity.normal");
		rarityrare = this.getConfig().getInt("rarity.rare");
		rarityveryrare = this.getConfig().getInt("rarity.veryrare");

	}
	
	public void onDisable() {
		Bukkit.getServer().getLogger().info("Treasures In Heads by AYuPro Unloaded");
	}
	
	@EventHandler
	private void onHeadUse(PlayerInteractEvent e){
		Player pl = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR){
			ItemStack item = pl.getItemInHand();
			if (item.getType() == Material.SKULL_ITEM){
				swap(pl, item);
			}
		}
	}
		
	@SuppressWarnings("deprecation")
	private void swap(Player pl, ItemStack itstack){
		
		Inventory plinv = pl.getInventory();
		plinv.removeItem(new ItemStack(itstack.getType(), 1));
		rarity = rand.nextInt(rarityveryrare) + 1;
			if (rarity < raritynormal){
				pl.getInventory().addItem(new ItemStack(Material.getMaterial(normalitems.get(rand.nextInt(normalitems.size())))));   
			} else if (rarity < rarityrare){
				pl.getInventory().addItem(new ItemStack(Material.getMaterial(rareitems.get(rand.nextInt(rareitems.size())))));
			} else {
				pl.getInventory().addItem(new ItemStack(Material.getMaterial(veryrareitems.get(rand.nextInt(veryrareitems.size())))));
			}
			pl.updateInventory();
			pl.sendMessage(ChatColor.BLUE + "[Treasures in Heads]"+ ChatColor.GOLD + " Вы получили вознаграждение за голову, удостоверьтесь что для его получения у вас было место в инвентаре.");
	}
	}
