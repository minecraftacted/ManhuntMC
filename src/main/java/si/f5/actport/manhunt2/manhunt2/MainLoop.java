
package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainLoop extends BukkitRunnable{
    private int maxCount;
    private int nowCount;
    private static final int waitSecondAtStart=15;
    private static final int tick=20;
    public AtomicBoolean stopReq=new AtomicBoolean(false);
    public AtomicBoolean Running=new AtomicBoolean(false);
    FootprintsGen footprintsGen=new FootprintsGen();
    private BossBarManager bossBarManager;
    private Map<Player,Location> huntersInitalPosition;
    private final Sound[] musicDisks={Sound.MUSIC_DISC_BLOCKS,Sound.MUSIC_DISC_CAT, Sound.MUSIC_DISC_CHIRP,Sound.MUSIC_DISC_FAR,Sound.MUSIC_DISC_MALL,Sound.MUSIC_DISC_MELLOHI,Sound.MUSIC_DISC_OTHERSIDE,Sound.MUSIC_DISC_PIGSTEP,Sound.MUSIC_DISC_STAL,Sound.MUSIC_DISC_STRAD,Sound.MUSIC_DISC_WAIT,Sound.MUSIC_DISC_WARD};

    public void SetCount(int cnt)
    {
        maxCount=cnt;
        nowCount=cnt;
    }

    @Override
    public void run()
    {
        Running.set(true);
        if(nowCount<=0)
        {
            stopReq.set(false);
            Running.set(false);
            ((PluginMain)PluginMain.getPlugin()).getVictoryJudge().TimeUp();
            for(Team team: Manhunt2.instance().board().getTeams())
            {
                for(OfflinePlayer offlinePlayer: team.getPlayers())
                {
                    if(offlinePlayer instanceof Player)
                    {
                        Player player=(Player) offlinePlayer;
                        player.stopSound(SoundCategory.BLOCKS);
                    }
                }
            }
            bossBarManager.Destroy();
            Bukkit.broadcastMessage("終了");
            cancel();
            return;
        }
        if(stopReq.get())
        {

            stopReq.set(false);
            Running.set(false);

            for(Team team: Manhunt2.instance().board().getTeams())
            {
                for(OfflinePlayer offlinePlayer: team.getPlayers())
                {
                    if(offlinePlayer instanceof Player)
                    {
                        Player player=(Player) offlinePlayer;
                        player.stopSound(SoundCategory.BLOCKS);
                    }
                }
            }
            bossBarManager.Destroy();
            Bukkit.broadcastMessage("終了");
            cancel();
            return;
        }
        if(nowCount==maxCount)
        {

            AtStart();
            bossBarManager=new BossBarManager();
        }
        if(nowCount>maxCount-waitSecondAtStart)
        {
            UpTo30secAfterStart();
        }
        if(nowCount==(maxCount-waitSecondAtStart))
        {
            WhenTheHunterIsOpen();
        }
        if(nowCount==300)
        {
            for(OfflinePlayer offlinePlayer:Manhunt2.instance().board().getTeam("runner").getPlayers())
            {
                if(offlinePlayer instanceof Player)
                {
                    Player runner = (Player) offlinePlayer;
                    runner.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING,5*60*20,10));
                }
            }
        }
        if(!(nowCount>maxCount-waitSecondAtStart))
        {
            footprintsGen.Gen();
        }
        Manhunt2.instance().getWorldBorder().setCenter(Bukkit.getWorlds().get(0).getSpawnLocation());
        Manhunt2.instance().getWorldBorder().setSize(4096);
        bossBarManager.UpdateBossBar(maxCount,nowCount);
        nowCount--;
    }

    private void WhenTheHunterIsOpen() {
        for(Team team:Manhunt2.instance().board().getTeams())
        {
            for(OfflinePlayer offlinePlayer:team.getPlayers())
            {
                if(offlinePlayer instanceof Player)
                {
                    Player player=(Player) offlinePlayer;
                    if(team.getName().equals("hunter"))
                    {
                        Bukkit.dispatchCommand(player,"supportmanhuntcompass");
                    }
                    player.stopSound(SoundCategory.BLOCKS);
                }
            }
        }
    }

    private void UpTo30secAfterStart() {
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().board().getTeam("hunter").getPlayers())
        {
            if(offlinePlayer instanceof Player)
            {
                Player player=(Player) offlinePlayer;
                player.teleport(huntersInitalPosition.get(player));
                player.getInventory().clear();
            }
        }
    }

    public void onDisable()
    {
        bossBarManager.onDisable();
    }
    private void AtStart()
    {
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getDeadHunterTeam().getPlayers())
        {
            if(offlinePlayer instanceof Player)
            {
                Player player=(Player) offlinePlayer;
                Manhunt2.instance().getDeadHunterTeam().removePlayer(player);
            }
        }
        for(OfflinePlayer offlinePlayer:Manhunt2.instance().getDeadRunnerTeam().getPlayers())
        {
            if(offlinePlayer instanceof Player)
            {
                Player player=(Player) offlinePlayer;
                Manhunt2.instance().getDeadRunnerTeam().removePlayer(player);
            }
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"advancement revoke @a everything");
        huntersInitalPosition=new HashMap<>();
        for(Team team:Manhunt2.instance().board().getTeams())
        {
            for(OfflinePlayer offlinePlayer:team.getPlayers())
            {
                if(offlinePlayer instanceof Player)
                {
                    Player player=(Player)offlinePlayer;
                    player.setWorldBorder(Manhunt2.instance().getWorldBorder());
                    spread(player);
                    Random random=new Random();
                    player.playSound(player,musicDisks[random.nextInt(12)],SoundCategory.BLOCKS, 1F, 1.25F);
                    player.getInventory().clear();
                    player.setLevel(0);
                    player.setExp(0);
                    for(PotionEffect effect: player.getActivePotionEffects())
                    {
                        player.removePotionEffect(effect.getType());
                    }
                    player.sendMessage(Manhunt2.LINE+ChatColor.UNDERLINE+ChatColor.BOLD+"\n開始\n"
                            +ChatColor.RESET+Manhunt2.instance().getHunterTeam().getPrefix()+"\n");
                    for(OfflinePlayer offlineHunter: Manhunt2.instance().getHunterTeam().getPlayers())
                    {
                        if(offlineHunter instanceof Player)
                        {
                            Player hunter=(Player) offlineHunter;
                            player.sendMessage(hunter.displayName());
                        }
                    }
                    player.sendMessage("\n");
                    player.sendMessage(Manhunt2.instance().getRunnerTeam().getPrefix()+"\n");
                    for(OfflinePlayer offlineRunner: Manhunt2.instance().getRunnerTeam().getPlayers())
                    {
                        if(offlineRunner instanceof Player)
                        {
                            Player runner=(Player) offlineRunner;
                            player.sendMessage(runner.displayName());
                        }
                    }
                    player.sendMessage(Manhunt2.LINE);
                    player.sendMessage(Manhunt2.LINE);
                    if(team.getName().equals("hunter"))
                    {
                        player.sendMessage(ChatColor.RED+"あなたは鬼だ。");
                        player.sendMessage("空へと響く高い音色に、ふと目を落とした。羅針盤は遠い彼方を指していた。\n愚か者に制裁を加えなければならない。\n何故か? それは与えられた義務だからだ。" +
                                "\n\n勝利条件:\n    逃走者が全滅する\n    時間切れになる\n敗北条件:\n    全滅する\n    エンダードラゴンが倒される");
                    }
                    if(team.getName().equals("runner"))
                    {
                        player.sendMessage(ChatColor.AQUA+"あなたは逃走者だ。");
                        player.sendMessage("ある日突然、希望の光は現れた。我々は隙を突いて脱出した。\nどんな代償を払おうとも、我々は帰還しなければならない。\n何故か? それは与えられた義務だからだ。"+
                                "\n\n勝利条件:\n    鬼が全滅する\n    エンダードラゴンを倒す\n敗北条件:\n    全滅する\n    時間切れになる");
                    }
                    player.sendMessage(Manhunt2.LINE);
                    if(team.getName().equals("hunter"))
                    {
                        player.setGameMode(GameMode.SURVIVAL);
                        huntersInitalPosition.put(player,player.getLocation());
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,waitSecondAtStart*tick,1,false,false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,waitSecondAtStart*tick,255,false,false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,waitSecondAtStart*tick,128,false,false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING,waitSecondAtStart*tick,255,false,false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,waitSecondAtStart*tick,255,false,false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,waitSecondAtStart*tick,255,false,false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,waitSecondAtStart*tick,255,false,false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,waitSecondAtStart*tick,255,false,false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,waitSecondAtStart*tick,255,false,false));
                    }
                    if(team.getName().equals("runner"))
                    {
                        player.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                        player.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
                        player.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
                        player.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
                        player.getInventory().addItem(new ItemStack(Material.BREAD,4));
                        player.getInventory().addItem(new ItemStack(Material.DIRT,32));
                        player.setGameMode(GameMode.SURVIVAL);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,tick,255));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,tick,255));
                    }
                }
            }
        }
    }
    private void spread(Player player)
    {
        Random random=new Random();
        World world=player.getWorld();
        Location defaultSpawn =world.getSpawnLocation();
        int x=random.nextInt(50)-25+(int)defaultSpawn.getX();
        int z=random.nextInt(50)-25+(int)defaultSpawn.getZ();
        int y=top(world,x,z)+1;
        Location loc =new Location(world,x+0.5,y,z+0.5);
        player.teleport(loc);
    }
    private int top(World world, int x, int z)throws IllegalStateException
    {
        for(int y=320;y>=-64;y--)
        {
            if(world.getBlockAt(x,y,z).getType().isAir())
            {
                if(y==-64)
                {
                    world.getBlockAt(x,y,z).setType(Material.STONE);
                    return y;
                }
                else
                {
                    continue;
                }
            }
            else if (world.getBlockAt(x,y,z).getType().isSolid()) {
                return y;
            }
            else if(world.getBlockAt(x,y,z).isLiquid())
            {
                world.getBlockAt(x,y,z).setType(Material.STONE);
                return y;
            }
        }
        throw new IllegalStateException();
    }
}
