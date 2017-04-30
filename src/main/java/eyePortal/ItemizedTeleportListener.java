package eyePortal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Inventory;

public class ItemizedTeleportListener implements Listener{
	@EventHandler
	public void OnTeleport (PlayerTeleportEvent event){
		if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL){	
			int num = Main.cfg.getInt("quantity");
			Inventory invo = event.getPlayer().getInventory();
			if (invo.containsAtLeast(Main.it, num)){
				invo.removeItem(Main.it);
				if (Main.replace){
					invo.addItem(Main.rit);
				}
			}else{
				event.setCancelled(true);
			}
		}
	}
}
