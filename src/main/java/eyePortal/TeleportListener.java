package eyePortal;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TeleportListener implements Listener{
	@EventHandler
	public void OnTeleport (PlayerTeleportEvent event){
		if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL){	
			String id = Main.cfg.getString("block");
			Material mat = Material.matchMaterial(id);
			int num = Main.cfg.getInt("quantity");
			Inventory invo = event.getPlayer().getInventory();
			if (invo.contains(mat)){
				int slot = invo.first(mat);
				ItemStack item = invo.getItem(slot);
				int amt = item.getAmount();
				if (amt == num){
					invo.clear(slot);
				}else{
					if (amt < num){
						event.setCancelled(true);
					}else{
					item.setAmount(amt-num);
					invo.setItem(slot, item);
					}
				}
			}else{
				//event.getPlayer().sendMessage("You need " + num + " of " + mat.toString());
				event.setCancelled(true);
			}
		}
	}
}
