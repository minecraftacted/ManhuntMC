package si.f5.actedsauce.manhuntmc.manhuntmc;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VictoryJudge implements Listener {
    @EventHandler
    public void PortalEvent(PlayerPortalEvent ppe)
    {
        if(ManhuntMC.instance().getHunterTeam().hasPlayer(ppe.getPlayer()))
        {
            ppe.getPlayer().sendMessage(ManhuntMC.LINE+ ChatColor.RED +"\n鬼はネザーに行けません\n"+ChatColor.RESET+ ManhuntMC.LINE);
            ppe.setCancelled(true);
        }
        else if(ManhuntMC.instance().getRunnerTeam().hasPlayer(ppe.getPlayer()))
        {
            if(ManhuntMC.instance().getMainLoop().Running.get())
            {
                Bukkit.broadcastMessage(ManhuntMC.instance().getRunnerTeam().getPrefix()+ChatColor.RESET+ppe.getPlayer().getName()+"がネザーゲートに入った");
                RunnerWin();
                ManhuntMC.instance().getRunnerTeam().removePlayer(ppe.getPlayer());
                ppe.setCancelled(true);
                ppe.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
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
            if(ManhuntMC.instance().getMainLoop().Running.get())
            {
                if(ManhuntMC.instance().getRunnerTeam().hasPlayer(pde.getEntity()))
                {
                    ManhuntMC.instance().getRunnerTeam().removePlayer(pde.getEntity());
                    ManhuntMC.instance().getDeadRunnerTeam().addPlayer(pde.getEntity());
                    Bukkit.broadcastMessage(ManhuntMC.instance().getRunnerTeam().getPrefix()+pde.getEntity().getName()+ChatColor.RESET+"は死んでしまった！ 残り逃走者は"+ ManhuntMC.instance().getRunnerTeam().getPlayers().size()+"人");
                    pde.getEntity().setGameMode(GameMode.SPECTATOR);
                    if(ManhuntMC.instance().getRunnerTeam().getPlayers().size()==0)
                    {
                        HunterWin();
                    }
                }
                if(ManhuntMC.instance().getHunterTeam().hasPlayer(pde.getEntity()))
                {
                    ManhuntMC.instance().getHunterTeam().removePlayer(pde.getEntity());
                    ManhuntMC.instance().getDeadHunterTeam().addPlayer(pde.getEntity());
                    Bukkit.broadcastMessage(ManhuntMC.instance().getHunterTeam().getPrefix()+pde.getEntity().getName()+ChatColor.RESET+"は死んでしまった！ 残り鬼は"+ ManhuntMC.instance().getHunterTeam().getPlayers().size()+"人");
                    pde.getEntity().setGameMode(GameMode.SPECTATOR);
                    if(ManhuntMC.instance().getHunterTeam().getPlayers().size()==0)
                    {
                        RunnerWin();
                    }
                }
            }
        }
        if(e instanceof PlayerQuitEvent)
        {
            PlayerQuitEvent pqe=(PlayerQuitEvent) e;
            if(ManhuntMC.instance().getMainLoop().Running.get())
            {
                if(ManhuntMC.instance().getRunnerTeam().hasPlayer(pqe.getPlayer()))
                {
                    ManhuntMC.instance().getRunnerTeam().removePlayer(pqe.getPlayer());
                    ManhuntMC.instance().getDeadRunnerTeam().addPlayer(pqe.getPlayer());
                    pqe.setQuitMessage(ChatColor.YELLOW+pqe.getPlayer().getName()+"は戦いに耐えられずLANケーブルを拳で切断した!");
                    Bukkit.broadcastMessage(ManhuntMC.instance().getRunnerTeam().getPrefix()+pqe.getPlayer().getName()+ChatColor.RESET+"は死んでしまった！(ということにしておこう) 残り逃走者は"+ ManhuntMC.instance().getRunnerTeam().getPlayers().size()+"人");
                    pqe.getPlayer().setGameMode(GameMode.SPECTATOR);
                    if(ManhuntMC.instance().getRunnerTeam().getPlayers().size()==0)
                    {
                        HunterWin();
                    }
                }
                if(ManhuntMC.instance().getHunterTeam().hasPlayer(pqe.getPlayer()))
                {
                    ManhuntMC.instance().getHunterTeam().removePlayer(pqe.getPlayer());
                    ManhuntMC.instance().getDeadHunterTeam().addPlayer(pqe.getPlayer());
                    pqe.setQuitMessage(pqe.getPlayer().getName()+"は戦いに耐えられずLANケーブルを拳で切断した!");
                    Bukkit.broadcastMessage(ManhuntMC.instance().getHunterTeam().getPrefix()+pqe.getPlayer().getName()+ChatColor.RESET+"は死んでしまった！(ということにしておこう) 残り鬼は"+ ManhuntMC.instance().getHunterTeam().getPlayers().size()+"人");
                    pqe.getPlayer().setGameMode(GameMode.SPECTATOR);
                    if(ManhuntMC.instance().getHunterTeam().getPlayers().size()==0)
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
        Bukkit.broadcastMessage(ManhuntMC.instance().getRunnerTeam().getPrefix()+"の勝利!");
        for(OfflinePlayer offlineRunner: ManhuntMC.instance().getRunnerTeam().getPlayers())
        {
            if(offlineRunner instanceof Player)
            {
                Player runner=(Player) offlineRunner;
                runner.playSound(runner, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER,1,1);
                runner.sendTitle(ChatColor.YELLOW+"勝利!",null,10,80,10);
            }
        }
        for(OfflinePlayer offlineHunter: ManhuntMC.instance().getHunterTeam().getPlayers())
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
        for(OfflinePlayer offlineHunter: ManhuntMC.instance().getDeadHunterTeam().getPlayers())
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
        for(OfflinePlayer offlinePlayer: ManhuntMC.instance().getRunnerTeam().getPlayers())
        {
            ManhuntMC.instance().getRunnerTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer: ManhuntMC.instance().getHunterTeam().getPlayers())
        {
            ManhuntMC.instance().getHunterTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer: ManhuntMC.instance().getDeadRunnerTeam().getPlayers())
        {
            ManhuntMC.instance().getDeadRunnerTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer: ManhuntMC.instance().getDeadHunterTeam().getPlayers())
        {
            ManhuntMC.instance().getDeadHunterTeam().removePlayer(offlinePlayer);
        }
        ManhuntMC.instance().getMainLoop().stopReq.set(true);

    }
    private void HunterWin()
    {
        Bukkit.broadcastMessage(ManhuntMC.instance().getHunterTeam().getPrefix()+"の勝利!");
        for(OfflinePlayer offlineHunter: ManhuntMC.instance().getHunterTeam().getPlayers())
        {
            if(offlineHunter instanceof Player)
            {
                Player hunter=(Player) offlineHunter;
                hunter.playSound(hunter, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER,1,1);
                hunter.sendTitle(ChatColor.YELLOW+"勝利!",null,10,80,10);
            }
        }
        for(OfflinePlayer offlineHunter: ManhuntMC.instance().getDeadHunterTeam().getPlayers())
        {
            if(offlineHunter instanceof Player)
            {
                Player hunter=(Player) offlineHunter;
                hunter.playSound(hunter, Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER,1,1);
                hunter.sendTitle(ChatColor.YELLOW+"勝利!",null,10,80,10);
            }
        }
        for(OfflinePlayer offlineRunner: ManhuntMC.instance().getRunnerTeam().getPlayers())
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
        for(OfflinePlayer offlineRunner: ManhuntMC.instance().getDeadRunnerTeam().getPlayers())
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
        for(OfflinePlayer offlinePlayer: ManhuntMC.instance().getRunnerTeam().getPlayers())
        {
            ManhuntMC.instance().getRunnerTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer: ManhuntMC.instance().getHunterTeam().getPlayers())
        {
            ManhuntMC.instance().getHunterTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer: ManhuntMC.instance().getDeadRunnerTeam().getPlayers())
        {
            ManhuntMC.instance().getDeadRunnerTeam().removePlayer(offlinePlayer);
        }
        for(OfflinePlayer offlinePlayer: ManhuntMC.instance().getDeadHunterTeam().getPlayers())
        {
            ManhuntMC.instance().getDeadHunterTeam().removePlayer(offlinePlayer);
        }
        ManhuntMC.instance().getMainLoop().stopReq.set(true);
    }
}
