package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VictoryJudge implements Listener {
    @EventHandler
    public void onEnderDragonDeath(EntityDeathEvent ede)
    {
        if(!(ede.getEntity() instanceof EnderDragon))
        {
            return;
        }
        if(!(Manhunt2.instance().getMainLoop().Running.get()))
        {
            return;
        }
        Bukkit.broadcastMessage(Manhunt2.LINE+"\nエンダードラゴンが倒された.....\n"+Manhunt2.LINE);
        RunnerWin();
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent pde)
    {
    onPlayerDeathAndPlayerLeave(pde);
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent pqe)
    {
        onPlayerDeathAndPlayerLeave(pqe);
    }
    private void onPlayerDeathAndPlayerLeave(Event e)
    {
        if(e instanceof PlayerDeathEvent)
        {
            PlayerDeathEvent pde=(PlayerDeathEvent) e;
            if(Manhunt2.instance().getMainLoop().Running.get())
            {
                if(Manhunt2.instance().getRunnerTeam().hasPlayer(pde.getPlayer()))
                {
                    Manhunt2.instance().getRunnerTeam().removePlayer(pde.getPlayer());
                    Manhunt2.instance().getDeadRunnerTeam().addPlayer(pde.getPlayer());
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
                    Manhunt2.instance().getDeadHunterTeam().addPlayer(pde.getPlayer());
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
        if(e instanceof PlayerQuitEvent)
        {
            PlayerQuitEvent pqe=(PlayerQuitEvent) e;
            if(Manhunt2.instance().getMainLoop().Running.get())
            {
                if(Manhunt2.instance().getRunnerTeam().hasPlayer(pqe.getPlayer()))
                {
                    Manhunt2.instance().getRunnerTeam().removePlayer(pqe.getPlayer());
                    Manhunt2.instance().getDeadRunnerTeam().addPlayer(pqe.getPlayer());
                    pqe.setQuitMessage(ChatColor.YELLOW+pqe.getPlayer().getName()+"は戦いに耐えられずLANケーブルを拳で切断した!");
                    Bukkit.broadcastMessage(Manhunt2.instance().getRunnerTeam().getPrefix()+pqe.getPlayer().getName()+ChatColor.RESET+"は死んでしまった！(ということにしておこう) 残り逃走者は"+Manhunt2.instance().getRunnerTeam().getPlayers().size()+"人");
                    pqe.getPlayer().setGameMode(GameMode.SPECTATOR);
                    if(Manhunt2.instance().getRunnerTeam().getPlayers().size()==0)
                    {
                        HunterWin();
                    }
                }
                if(Manhunt2.instance().getHunterTeam().hasPlayer(pqe.getPlayer()))
                {
                    Manhunt2.instance().getHunterTeam().removePlayer(pqe.getPlayer());
                    Manhunt2.instance().getDeadHunterTeam().addPlayer(pqe.getPlayer());
                    pqe.setQuitMessage(pqe.getPlayer().getName()+"は戦いに耐えられずLANケーブルを拳で切断した!");
                    Bukkit.broadcastMessage(Manhunt2.instance().getHunterTeam().getPrefix()+pqe.getPlayer().getName()+ChatColor.RESET+"は死んでしまった！(ということにしておこう) 残り鬼は"+Manhunt2.instance().getHunterTeam().getPlayers().size()+"人");
                    pqe.getPlayer().setGameMode(GameMode.SPECTATOR);
                    if(Manhunt2.instance().getHunterTeam().getPlayers().size()==0)
                    {
                        RunnerWin();
                    }
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
        for(OfflinePlayer offlineHunter:Manhunt2.instance().getDeadHunterTeam().getPlayers())
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
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getRunnerTeam().getPlayers())
        {
            Manhunt2.instance().getRunnerTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getHunterTeam().getPlayers())
        {
            Manhunt2.instance().getHunterTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getDeadRunnerTeam().getPlayers())
        {
            Manhunt2.instance().getDeadRunnerTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getDeadHunterTeam().getPlayers())
        {
            Manhunt2.instance().getDeadHunterTeam().removePlayer(offlinePlayer);
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
        for(OfflinePlayer offlineHunter:Manhunt2.instance().getDeadHunterTeam().getPlayers())
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
        for(OfflinePlayer offlineRunner:Manhunt2.instance().getDeadRunnerTeam().getPlayers())
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
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getRunnerTeam().getPlayers())
        {
            Manhunt2.instance().getRunnerTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getHunterTeam().getPlayers())
        {
            Manhunt2.instance().getHunterTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getDeadRunnerTeam().getPlayers())
        {
            Manhunt2.instance().getDeadRunnerTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getDeadHunterTeam().getPlayers())
        {
            Manhunt2.instance().getDeadHunterTeam().removePlayer(offlinePlayer);
        }
        Manhunt2.instance().getMainLoop().stopReq.set(true);
    }
}
