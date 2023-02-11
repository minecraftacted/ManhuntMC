package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class VictoryJudge implements Listener {
    @EventHandler
    public void PortalEvent(PlayerPortalEvent ppe)
    {
        if(Manhunt2.instance().getHunterTeam().hasPlayer(ppe.getPlayer()))
        {
            ppe.getPlayer().sendMessage(Manhunt2.LINE+ ChatColor.RED +"\n鬼はネザーに行けません\n"+ChatColor.RESET+Manhunt2.LINE);
            ppe.setCancelled(true);
        }
        else if(Manhunt2.instance().getRunnerTeam().hasPlayer(ppe.getPlayer()))
        {
            if(Manhunt2.instance().getMainLoop().Running.get())
            {
                Bukkit.broadcastMessage(Manhunt2.instance().getRunnerTeam().getPrefix()+ChatColor.RESET+ppe.getPlayer().getName()+"がネザーゲートに入った");
                if(Manhunt2.instance().getRunnerTeam().getPlayers().size()==1)
                {
                    RunnerWin();
                }
                Manhunt2.instance().getRunnerTeam().removePlayer(ppe.getPlayer());
                ppe.setCancelled(true);
                ppe.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent pde)
    {
        if(Manhunt2.instance().getMainLoop().Running.get())
        {
            if(Manhunt2.instance().getRunnerTeam().hasPlayer(pde.getPlayer()))
            {
                Manhunt2.instance().getRunnerTeam().removePlayer(pde.getPlayer());
                Bukkit.broadcastMessage(Manhunt2.instance().getRunnerTeam().getPrefix()+pde.getPlayer().getName()+ChatColor.RESET+"は死んでしまった！ 残り逃走者は"+Manhunt2.instance().getRunnerTeam().getPlayers().size()+"人");
                pde.setCancelled(true);
                pde.getPlayer().setGameMode(GameMode.SPECTATOR);
                if(Manhunt2.instance().getRunnerTeam().getPlayers().size()==0)
                {
                    HunterWin();
                }
            }
            if(Manhunt2.instance().getHunterTeam().hasPlayer(pde.getPlayer()))
            {
                Manhunt2.instance().getHunterTeam().removePlayer(pde.getPlayer());
                Bukkit.broadcastMessage(Manhunt2.instance().getHunterTeam().getPrefix()+pde.getPlayer().getName()+ChatColor.RESET+"は死んでしまった！ 残り鬼は"+Manhunt2.instance().getHunterTeam().getPlayers().size()+"人");
                pde.setCancelled(true);
                pde.getPlayer().setGameMode(GameMode.SPECTATOR);
                if(Manhunt2.instance().getHunterTeam().getPlayers().size()==0)
                {
                    RunnerWin();
                }
            }
        }
    }
    public void TimeUp()
    {
        HunterWin();
    }
    private void RunnerWin()
    {
        Bukkit.broadcastMessage(Manhunt2.instance().getRunnerTeam().getPrefix()+"の勝利!");
        for(OfflinePlayer offlineRunner:Manhunt2.instance().getRunnerTeam().getPlayers())
        {
            if(offlineRunner instanceof Player)
            {
                Player runner=(Player) offlineRunner;
                runner.playSound(runner, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER,1,1);
                runner.sendTitle(ChatColor.YELLOW+"勝利!",null,10,80,10);
            }
        }
        for(OfflinePlayer offlineHunter:Manhunt2.instance().getHunterTeam().getPlayers())
        {
            if(offlineHunter instanceof Player)
            {
                Player hunter=(Player) offlineHunter;
                for(int i=0;i<5;i++)
                {
                    hunter.playSound(hunter,Sound.AMBIENT_CAVE,SoundCategory.MASTER,(float)1,(float)1);
                }
                hunter.sendTitle(ChatColor.RED+"敗北",null,10,80,10);
            }
        }
        Manhunt2.instance().getMainLoop().stopReq.set(true);
    }
    private void HunterWin()
    {
        Bukkit.broadcastMessage(Manhunt2.instance().getHunterTeam().getPrefix()+"の勝利!");
        for(OfflinePlayer offlineHunter:Manhunt2.instance().getHunterTeam().getPlayers())
        {
            if(offlineHunter instanceof Player)
            {
                Player hunter=(Player) offlineHunter;
                hunter.playSound(hunter, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER,1,1);
                hunter.sendTitle(ChatColor.YELLOW+"勝利!",null,10,80,10);
            }
        }
        for(OfflinePlayer offlineRunner:Manhunt2.instance().getRunnerTeam().getPlayers())
        {
            if(offlineRunner instanceof Player)
            {
                Player runner=(Player) offlineRunner;
                for(int i=0;i<5;i++)
                {
                    runner.playSound(runner,Sound.AMBIENT_CAVE,SoundCategory.MASTER,(float)1,(float)1);
                }
                runner.sendTitle(ChatColor.RED+"敗北",null,10,80,10);
            }
        }

        Manhunt2.instance().getMainLoop().stopReq.set(true);
    }
}
