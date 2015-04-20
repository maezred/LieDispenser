package net.moltendorf.Bukkit.LieDispenser;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dispenser;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Created by moltendorf on 14/09/10.
 *
 * @author moltendorf
 */
public class Listeners implements Listener {

	final protected Plugin          plugin;
	final protected BukkitScheduler scheduler;

	protected Listeners(final Plugin instance) {
		plugin = instance;
		scheduler = plugin.getServer().getScheduler();
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void BlockDispenseEventListener(final BlockDispenseEvent event) {
		final ItemStack item = event.getItem();

		if (item.getType() != Material.CAKE) {
			return;
		}

		final Block      block      = event.getBlock();
		final BlockState blockState = block.getState();
		final Material   type       = blockState.getType();

		if (type != Material.DISPENSER) {
			return;
		}

		final MaterialData blockStateData = blockState.getData();
		final Dispenser    dispenser      = (Dispenser)blockStateData;
		final BlockFace    facing         = dispenser.getFacing();

		Block relative = block.getRelative(facing);

		if (relative.getType() != Material.AIR) {
			relative = relative.getRelative(facing);
		}

		if (relative.getType() == Material.AIR) {
			relative.setType(Material.CAKE_BLOCK); // PLACE A CAKE!

			scheduler.scheduleSyncDelayedTask(plugin, () -> ((InventoryHolder)blockState).getInventory().removeItem(item));
		}

		event.setCancelled(true);
	}
}
