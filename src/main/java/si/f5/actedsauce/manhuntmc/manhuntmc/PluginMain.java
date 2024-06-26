package si.f5.actedsauce.manhuntmc.manhuntmc;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PluginMain extends JavaPlugin {
private static Plugin plugin;
private ManhuntMC manhuntMC;
private ManhuntInitCommand manhuntInitCommand;
private ManhuntCommand manhuntCommand;
private VictoryJudge victoryJudge;

    @Override
    public void onEnable() {
        plugin=this;
        manhuntInitCommand=new ManhuntInitCommand();
        manhuntCommand=new ManhuntCommand();
        victoryJudge=new VictoryJudge();
        // Plugin startup logic
        getCommand("manhuntinit").setExecutor(manhuntInitCommand);
        getCommand("manhunt").setExecutor(manhuntCommand);
        getCommand("autoteamsetting").setExecutor(new AutoTeamSettingCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new ManhuntInitChatListener(),this);
        Bukkit.getServer().getPluginManager().registerEvents(victoryJudge,this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatListenerForChangeCompassTarget(),this);
        manhuntMC = ManhuntMC.instance();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        manhuntInitCommand.onDisable();
        manhuntCommand.onDisable();
        manhuntMC.onDisable();
    }

    public static Plugin getPlugin() {
        return plugin;
    }
    public VictoryJudge getVictoryJudge()
    {
        return victoryJudge;
    }
}
