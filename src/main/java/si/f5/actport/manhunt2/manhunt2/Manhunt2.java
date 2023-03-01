package si.f5.actport.manhunt2.manhunt2;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.checkerframework.checker.units.qual.C;

public class Manhunt2 {
    private static final Manhunt2 instance=new Manhunt2();
    public static Manhunt2 instance()
    {
        return instance;
    }
    public static final String LINE="===================================================";
    private final ScoreboardManager manager;
    private final Scoreboard board;
    private final Team hunterTeam;
    private final Team runnerTeam;
    private final Team deadHunterTeam;
    private final Team deadRunnerTeam;
    private boolean ManhuntInitializing =false;
    private Compass compass;
    private WorldBorder worldBorder=Bukkit.createWorldBorder();
    private MainLoop mainLoop=new MainLoop();
    private Manhunt2()
    {
        manager= Bukkit.getScoreboardManager();
        board=manager.getNewScoreboard();
        hunterTeam=board.registerNewTeam("hunter");
        hunterTeam.setPrefix(ChatColor.RED+"[鬼]");
        hunterTeam.setDisplayName("鬼");
        hunterTeam.color(NamedTextColor.RED);
        hunterTeam.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.ALWAYS);
        hunterTeam.setAllowFriendlyFire(false);
        runnerTeam=board.registerNewTeam("runner");
        runnerTeam.setPrefix(ChatColor.AQUA+"[逃走者]");
        runnerTeam.setDisplayName("逃走者");
        runnerTeam.color(NamedTextColor.AQUA);
        runnerTeam.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.ALWAYS);
        runnerTeam.setAllowFriendlyFire(false);
        deadHunterTeam=board.registerNewTeam("deadhunter");
        deadHunterTeam.setPrefix(ChatColor.GRAY+"[死亡済み]");
        deadHunterTeam.setSuffix(ChatColor.DARK_RED+"[鬼]");
        deadHunterTeam.setDisplayName("観戦鬼");
        deadHunterTeam.color(NamedTextColor.GRAY);
        deadHunterTeam.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.FOR_OTHER_TEAMS);
        deadHunterTeam.setAllowFriendlyFire(false);
        deadRunnerTeam=board.registerNewTeam("deadrunner");
        deadRunnerTeam.setPrefix(ChatColor.GRAY+"[死亡済み]");
        deadRunnerTeam.setSuffix(ChatColor.DARK_AQUA+"[逃走者]");
        deadRunnerTeam.setDisplayName("観戦逃走者");
        deadRunnerTeam.color(NamedTextColor.GRAY);
        deadRunnerTeam.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.FOR_OTHER_TEAMS);
        deadRunnerTeam.setAllowFriendlyFire(false);
    }


    public void onDisable()
    {
    }

    public boolean ManhuntInitializing()
    {
        return ManhuntInitializing;
    }
    public void ManhuntInitializing(boolean manhunt_initializing)
    {
        ManhuntInitializing=manhunt_initializing;
    }
    public void AddPlayerToHunter(Player player)
    {
        hunterTeam.addPlayer(player);
    }
    public void RemovePlayerFromHunter(Player player)
    {
        hunterTeam.removePlayer(player);
    }
    public void AddPlayerToRunner(Player player)
    {
        runnerTeam.addPlayer(player);
    }
    public void RemovePlayerFromRunner(Player player)
    {
        runnerTeam.removePlayer(player);
    }
    public Scoreboard board()
    {
        return board;
    }
    public Team getHunterTeam()
    {
        return hunterTeam;
    }
    public Team getRunnerTeam()
    {
        return runnerTeam;
    }
    public MainLoop getMainLoop()
    {
        return mainLoop;
    }
    public void newMainLoop()
    {
        mainLoop=new MainLoop();
    }
    public void newCompass()
    {
        compass=new Compass();
    }
    public Compass getCompass()
    {
        return compass;
    }
    public WorldBorder getWorldBorder()
    {
        return worldBorder;
    }
    public void JoinHunterTeam(Player player)
    {
        Manhunt2.instance().RemovePlayerFromRunner(player);
        Manhunt2.instance().AddPlayerToHunter(player);
        player.sendMessage(LINE+ ChatColor.RED+"\n鬼に設定しました。\n"+ChatColor.RESET+LINE);
        player.setScoreboard(Manhunt2.instance().board());
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP,2,1);
        player.playSound(player, Sound.ENTITY_VEX_HURT,2,1);
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASEDRUM,2,1);
        player.spawnParticle(Particle.REDSTONE,player.getLocation(),240,0.1,2,0.1,20,new Particle.DustOptions(Color.fromRGB(255, 64, 64),1));
    }
    public void JoinRunnerTeam(Player player)
    {
        Manhunt2.instance().RemovePlayerFromHunter(player);
        Manhunt2.instance().AddPlayerToRunner(player);
        player.sendMessage(LINE+ ChatColor.AQUA+"\n逃走者に設定しました。\n"+ChatColor.RESET+LINE);
        player.setScoreboard(Manhunt2.instance().board());
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP,2,1);
        player.playSound(player, Sound.ENTITY_VEX_HURT,2,1);
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASEDRUM,2,1);
        player.spawnParticle(Particle.REDSTONE,player.getLocation(),240,0.1,2,0.1,20,new Particle.DustOptions(Color.fromRGB(39, 163, 245),1));
    }

    public Team getDeadHunterTeam()
    {
        return deadHunterTeam;
    }

    public Team getDeadRunnerTeam()
    {
        return deadRunnerTeam;
    }
}

