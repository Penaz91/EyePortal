package eyePortal;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
	public static Configuration cfg = null;
	public static ItemStack it = null;
	@Override
	public void onEnable(){
		File f = getDataFolder();
		if (!f.exists()){
			f.mkdir();
			saveResource("config.yml", false);
		}
		cfg = this.getConfig();
		if (cfg.getString("block").equalsIgnoreCase("###ITEM###")){
			File customfile = new File(getDataFolder(),"item.yml");
			FileConfiguration customyml=YamlConfiguration.loadConfiguration(customfile);
			it=customyml.getItemStack("item");
			getServer().getPluginManager().registerEvents(new ItemizedTeleportListener(), this);
		}else{
			getServer().getPluginManager().registerEvents(new TeleportListener(), this);
		}
		getLogger().info("Plugin loaded");
	}
	@Override
	public void onDisable(){
		HandlerList.unregisterAll(this);
		getLogger().info("Plugin Disabled");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("epreload")){
			cfg = this.getConfig();
			getLogger().info("Configuration reloaded");
			sender.sendMessage("Configuration reloaded");
			return true;
		}else{
			if (cmd.getName().equalsIgnoreCase("epsetitem")){
				if (sender instanceof Player){
					ItemStack i=((Player) sender).getInventory().getItemInMainHand();
					File customfile = new File(getDataFolder(),"item.yml");
					FileConfiguration customyml=YamlConfiguration.loadConfiguration(customfile);
					Map<String, Object> map=customyml.getValues(false);
					if (map.get("item")==null){
						customyml.createSection("item");
						customyml.set("item",i);
					}else{
						customyml.set("item", i);
					}
					try {
						customyml.save(customfile);
					} catch (IOException e) {
						sender.sendMessage("Unable to save item, item.yml not found");
					}
				}else{
					sender.sendMessage("This command can be executed only by a player");
				}
				return true;
			}else{
				if (cmd.getName().equalsIgnoreCase("epgetitem")){
					PlayerInventory pi = ((Player) sender).getInventory();
					if (pi.firstEmpty()==-1) {
						sender.sendMessage("You need at least 1 free space in your inventory");
					}else{
						File customfile = new File(getDataFolder(),"item.yml");
						FileConfiguration customyml=YamlConfiguration.loadConfiguration(customfile);
						ItemStack i=customyml.getItemStack("item");
						pi.addItem(i);
					}
					return true;
				}
			}
		}
		return false;}
}
