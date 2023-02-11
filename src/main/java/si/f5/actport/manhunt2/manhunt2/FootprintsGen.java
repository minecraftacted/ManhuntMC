package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class FootprintsGen {
    public void Gen()
    {
        for(Team team:Manhunt2.instance().board().getTeams())
        {
            for(OfflinePlayer offlinePlayer:team.getPlayers())
            {
                if(offlinePlayer instanceof Player)
                {
                    Player player=(Player)offlinePlayer;
                    new PersistentParticles(13,player.getLocation()).runTaskTimer(PluginMain.getPlugin(),0,20);
                }
            }
        }
    }
}
