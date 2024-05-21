package si.f5.actedsauce.manhuntmc;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListenerForChangeCompassTarget implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent apce)
    {
        if(Manhunt2.instance().getCompass().DoPlayerHaveCompass(apce.getPlayer()) &&  Manhunt2.instance().getCompass().SetTarget(apce.getPlayer(),Bukkit.getPlayer(apce.getMessage())))
        {
            apce.getPlayer().playSound(apce.getPlayer(), Sound.ITEM_CROSSBOW_LOADING_END, SoundCategory.MASTER,1f,0.1f);
            apce.getPlayer().sendMessage("ターゲットを"+apce.getMessage()+"に変更した。");
            apce.setCancelled(true);
        }
    }
}
