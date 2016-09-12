package eyePortal;

import java.io.File;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
	public static Configuration cfg = null;
	@Override
	public void onEnable(){
		File f = getDataFolder();
		if (!f.exists()){
			f.mkdir();
			saveResource("config.yml", false);
		}
		cfg = this.getConfig();
		getServer().getPluginManager().registerEvents(new TeleportListener(), this);
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
		}
		return false;}
}
