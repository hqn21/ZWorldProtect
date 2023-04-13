package nukkit.ZWorldProtect;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.block.ItemFrameDropItemEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.EventHandler;

import java.io.File;
import java.util.Objects;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.Config;

public class ZWorldProtect extends PluginBase implements Listener {

	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getLogger().info(TextFormat.LIGHT_PURPLE + "世界保護插件 [ZeroK製作]");
		File worlds = new File(this.getServer().getDataPath() + "/worlds");
		if (worlds.exists()) {
			for (String world : Objects.requireNonNull(worlds.list())) {
				if (!this.getServer().isLevelLoaded(world)) {
					this.getServer().loadLevel(world);
					Config config = new Config(this.getDataFolder() + "/" + world + ".yml", Config.YAML);
					if (config.get("status") == null) {
						config.set("status", "openw");
						config.save();
					}
				}
			}
		}
	}

	public void sendHelp(CommandSender sender) {
		sender.sendMessage("§8[§cX§8]§f 您輸入的格式貌似錯誤, 請使用 §e/zw help §f查詢正確用法");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (cmd.getName().equalsIgnoreCase("zw")) {
			if (args.length < 1) {
				this.sendHelp(sender);
				return true;
			}
			switch (args[0]) {
			case "help":
				sender.sendMessage(
						"<世界保護指令列表>\n/zw help: 得到幫助列表\n/zw open <世界名>: 將一個世界解除保護\n/zw protect <世界名>: 將一個世界設置保護(管理員可建設 )\n/zw lock <世界名>: 將一個世界設置保護(管理員不可建設)");
				return true;
			case "open":
				if (args.length != 2) {
					this.sendHelp(sender);
					return true;
				}
				if (this.getServer().loadLevel(args[1])) {
					Config config = new Config(this.getDataFolder() + "/" + args[1] + ".yml", Config.YAML);
					config.set("status", "openw");
					config.save();
					this.getServer().broadcastMessage("§8[§e!§8]§f 世界§e " + args[1] + " §f已解除保護(OPEN)");
					return true;
				} else {
					sender.sendMessage("§8[§cX§8]§f 此世界不存在");
					return true;
				}
			case "protect":
				if (args.length != 2) {
					this.sendHelp(sender);
					return true;
				}
				if (this.getServer().loadLevel(args[1])) {
					Config config = new Config(this.getDataFolder() + "/" + args[1] + ".yml", Config.YAML);
					config.set("status", "protect");
					config.save();
					this.getServer().broadcastMessage("§8[§e!§8]§f 世界§e " + args[1] + " §f已開啟保護(PROTECT)");
					return true;
				} else {
					sender.sendMessage("§8[§cX§8]§f 此世界不存在");
					return true;
				}
			case "lock":
				if (args.length != 2) {
					this.sendHelp(sender);
					return true;
				}
				if (this.getServer().loadLevel(args[1])) {
					Config config = new Config(this.getDataFolder() + "/" + args[1] + ".yml", Config.YAML);
					config.set("status", "lock");
					config.save();
					this.getServer().broadcastMessage("§8[§e!§8]§f 世界§e " + args[1] + " §f已開啟保護(LOCK)");
					return true;
				} else {
					sender.sendMessage("§8[§cX§8]§f 此世界不存在");
					return true;
				}
			}
		}
		return true;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		String world = event.getBlock().getLevel().getName();
		Config config = new Config(this.getDataFolder() + "/" + world + ".yml", Config.YAML);
		Player player = event.getPlayer();
		if (!event.isCancelled()) {
			if (config.getString("status").length() == 4) {
				player.sendMessage("§8[§cX§8]§f 此世界已被保護");
				event.setCancelled();
			} else if (config.getString("status").length() == 7 && !player.isOp()) {
				player.sendMessage("§8[§cX§8]§f 此世界已被保護");
				event.setCancelled();
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		String world = event.getBlock().getLevel().getName();
		Config config = new Config(this.getDataFolder() + "/" + world + ".yml", Config.YAML);
		Player player = event.getPlayer();
		if (!event.isCancelled()) {
			if (config.getString("status").length() == 4) {
				player.sendMessage("§8[§cX§8]§f 此世界已被保護");
				event.setCancelled();
			} else if (config.getString("status").length() == 7 && !player.isOp()) {
				player.sendMessage("§8[§cX§8]§f 此世界已被保護");
				event.setCancelled();
			}
		}
	}

	@EventHandler
	public void onItemFrameDropItem(ItemFrameDropItemEvent event) {
		String world = event.getPlayer().getLevel().getName();
		Config config = new Config(this.getDataFolder() + "/" + world + ".yml", Config.YAML);
		Player player = event.getPlayer();
		if (!event.isCancelled()) {
			if (config.getString("status").length() == 4) {
				player.sendMessage("§8[§cX§8]§f 此世界已被保護");
				event.setCancelled();
			} else if (config.getString("status").length() == 7 && !player.isOp()) {
				player.sendMessage("§8[§cX§8]§f 此世界已被保護");
				event.setCancelled();
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		String world = event.getPlayer().getLevel().getName();
		Config config = new Config(this.getDataFolder() + "/" + world + ".yml", Config.YAML);
		Player player = event.getPlayer();
		if (!event.isCancelled()) {
			if (event.getItem() == null) {
				if (config.getString("status").length() == 4) {
					player.sendMessage("§8[§cX§8]§f 此世界已被保護");
					event.setCancelled();
				} else if (config.getString("status").length() == 7 && !player.isOp()) {
					player.sendMessage("§8[§cX§8]§f 此世界已被保護");
					event.setCancelled();
				}
			} else if (config.getString("status").length() == 4) {
				player.sendMessage("§8[§cX§8]§f 此世界已被保護");
				event.setCancelled();
			} else if (config.getString("status").length() == 7 && !player.isOp()) {
				player.sendMessage("§8[§cX§8]§f 此世界已被保護");
				event.setCancelled();
			}
		}
	}
}
