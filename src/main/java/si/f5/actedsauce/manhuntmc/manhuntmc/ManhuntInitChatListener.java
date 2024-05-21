package si.f5.actedsauce.manhuntmc.manhuntmc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ManhuntInitChatListener implements Listener {
    @EventHandler
    public void TeamAssignOnChatEvent(AsyncPlayerChatEvent apce)
    {
        if(ManhuntMC.instance().ManhuntInitializing())
        {
            if(apce.getMessage().equalsIgnoreCase("runner"))
            {
                ManhuntMC.instance().JoinRunnerTeam(apce.getPlayer());
                apce.setCancelled(true);
            } else if (apce.getMessage().equalsIgnoreCase("hunter"))
            {
                ManhuntMC.instance().JoinHunterTeam(apce.getPlayer());
                apce.setCancelled(true);
            }
        }
    }
}
