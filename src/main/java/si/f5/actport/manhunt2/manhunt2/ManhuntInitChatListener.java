package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static si.f5.actport.manhunt2.manhunt2.Manhunt2.LINE;

public class ManhuntInitChatListener implements Listener {
    @EventHandler
    public void TeamAssignOnChatEvent(AsyncPlayerChatEvent apce)
    {
        if(Manhunt2.instance().ManhuntInitializing())
        {
            if(apce.getMessage().equalsIgnoreCase("runner"))
            {
                Manhunt2.instance().RemovePlayerFromHunter(apce.getPlayer());
                Manhunt2.instance().AddPlayerToRunner(apce.getPlayer());
                apce.getPlayer().sendMessage(LINE+ ChatColor.AQUA+"\n逃走者に設定しました。\n"+ChatColor.RESET+LINE);
                apce.getPlayer().setScoreboard(Manhunt2.instance().board());
                apce.getPlayer().playSound(apce.getPlayer(), Sound.BLOCK_NOTE_BLOCK_HARP,2,1);
                apce.getPlayer().playSound(apce.getPlayer(), Sound.ENTITY_VEX_HURT,2,1);
                apce.getPlayer().playSound(apce.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM,2,1);
                apce.getPlayer().spawnParticle(Particle.REDSTONE,apce.getPlayer().getLocation(),240,0.1,2,0.1,20,new Particle.DustOptions(Color.fromRGB(39, 163, 245),1));
                apce.setCancelled(true);
            } else if (apce.getMessage().equalsIgnoreCase("hunter"))
            {
                Manhunt2.instance().RemovePlayerFromRunner(apce.getPlayer());
                Manhunt2.instance().AddPlayerToHunter(apce.getPlayer());
                apce.getPlayer().sendMessage(LINE+ ChatColor.RED+"\n鬼に設定しました。\n"+ChatColor.RESET+LINE);
                apce.getPlayer().setScoreboard(Manhunt2.instance().board());
                apce.getPlayer().playSound(apce.getPlayer(), Sound.BLOCK_NOTE_BLOCK_HARP,2,1);
                apce.getPlayer().playSound(apce.getPlayer(), Sound.ENTITY_VEX_HURT,2,1);
                apce.getPlayer().playSound(apce.getPlayer(), Sound.BLOCK_NOTE_BLOCK_BASEDRUM,2,1);
                apce.getPlayer().spawnParticle(Particle.REDSTONE,apce.getPlayer().getLocation(),240,0.1,2,0.1,20,new Particle.DustOptions(Color.fromRGB(255, 64, 64),1));
                apce.setCancelled(true);
            }
        }
    }
}
