package si.f5.actedsauce.manhuntmc.manhuntmc;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class Compass {
    private HashMap<OfflinePlayer, Location> playersLocations;
    private HashMap<Player,Player> playersTarget;
    public static ItemStack compass;
    Compass()
    {
        playersLocations=new HashMap<>();
        playersTarget=new HashMap<>();
        compass=new ItemStack(Material.COMPASS,1);
        ItemMeta meta=compass.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"羅針盤");
        ArrayList lore=new ArrayList();
        lore.add("愚か者のもとへ導く。");
        lore.add("これも与えられた義務なのだろう。");
        lore.add("使用法:");
        lore.add("  定期的に位置が更新される。");
        lore.add("  チャットにプレイヤーの名前を書くことで対象を変更できる。");
        meta.setLore(lore);
        compass.setItemMeta(meta);
    }
    public void GiveCompass(Player player)
    {
        player.getInventory().addItem(compass);
    }
    public boolean DoPlayerHaveCompass(Player player)
    {
        return player.getInventory().contains(compass);
    }
    public void UpdatePlayersLocations()
    {
        for(Player player: Bukkit.getOnlinePlayers())
        {
            if(player.getInventory().contains(compass))
            {
                player.sendMessage(ManhuntMC.LINE+ ChatColor.GREEN +"\n位置情報が更新された!\n"+ChatColor.RESET+ ManhuntMC.LINE);
                player.playSound(player,Sound.BLOCK_BEACON_ACTIVATE,SoundCategory.MASTER,1,2);
            }
            else
            {
                player.sendMessage(ManhuntMC.LINE+ ChatColor.GRAY +"\n鬼に位置情報が送信された...\n"+ChatColor.RESET+ ManhuntMC.LINE);
                player.playSound(player,Sound.BLOCK_BEACON_ACTIVATE,SoundCategory.MASTER,1,1);
            }
        }
        for(OfflinePlayer offlinePlayer: Bukkit.getOfflinePlayers())
        {
            if(offlinePlayer.isOnline())
            {
                if(((Player)offlinePlayer).getGameMode()== GameMode.SURVIVAL)
                {
                    playersLocations.put(offlinePlayer,((Player)offlinePlayer).getLocation());
                }
                else
                {
                    playersLocations.remove(offlinePlayer);
            }
            }else
            {
                playersLocations.remove(offlinePlayer);
            }
        }
        for(Player player:playersTarget.keySet())
        {
            SetTarget(player,playersTarget.get(player));
        }
    }
    public boolean SetTarget(Player hunter,Player target)
    {
        if(playersLocations.containsKey(target)&&DoPlayerHaveCompass(hunter))
        {
            hunter.setCompassTarget(playersLocations.get(target));
            playersTarget.put(hunter,target);
            return true;
        }
        return false;
    }
}
