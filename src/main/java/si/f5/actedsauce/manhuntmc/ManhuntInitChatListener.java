package si.f5.actedsauce.manhuntmc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ManhuntInitChatListener implements Listener {
    @EventHandler
    public void TeamAssignOnChatEvent(AsyncPlayerChatEvent apce)
    {
        if(Manhunt2.instance().ManhuntInitializing())
        {
            if(apce.getMessage().equalsIgnoreCase("runner"))
            {
                Manhunt2.instance().JoinRunnerTeam(apce.getPlayer());
                apce.setCancelled(true);
            } else if (apce.getMessage().equalsIgnoreCase("hunter"))
            {
                Manhunt2.instance().JoinHunterTeam(apce.getPlayer());
                apce.setCancelled(true);
            }
        }
    }
}
