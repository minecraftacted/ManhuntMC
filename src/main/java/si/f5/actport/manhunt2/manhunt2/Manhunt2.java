package si.f5.actport.manhunt2.manhunt2;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

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
    private boolean ManhuntInitializing =false;
    private WorldBorder worldBorder=Bukkit.createWorldBorder();
    private MainLoop mainLoop=new MainLoop();
    private Manhunt2()
    {
        manager= Bukkit.getScoreboardManager();
        board=manager.getNewScoreboard();
        hunterTeam=board.registerNewTeam("鬼");
        hunterTeam.setPrefix(ChatColor.RED+"[鬼]");
        hunterTeam.setDisplayName("鬼");
        hunterTeam.color(NamedTextColor.RED);
        hunterTeam.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.FOR_OTHER_TEAMS);
        hunterTeam.setAllowFriendlyFire(false);
        runnerTeam=board.registerNewTeam("逃走者");
        runnerTeam.setPrefix(ChatColor.AQUA+"[逃走者]");
        runnerTeam.setDisplayName("逃走者");
        runnerTeam.color(NamedTextColor.AQUA);
        runnerTeam.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.FOR_OTHER_TEAMS);
        runnerTeam.setAllowFriendlyFire(false);
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
    public WorldBorder getWorldBorder()
    {
        return worldBorder;
    }

}

