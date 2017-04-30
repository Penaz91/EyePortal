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
			String id2 = Main.cfg.getString("replaceblock");
			Material mat = Material.matchMaterial(id);
			Material mat2 = Material.matchMaterial(id2);
			int num = Main.cfg.getInt("quantity");
			int num2 = Main.cfg.getInt("replacequantity");
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
				if (Main.replace){
					invo.addItem();
				}
			}else{
				//event.getPlayer().sendMessage("You need " + num + " of " + mat.toString());
				event.setCancelled(true);
			}
			if (Main.replace){
				invo.addItem(new ItemStack(mat2, num2));
			}
		}
	}
}
