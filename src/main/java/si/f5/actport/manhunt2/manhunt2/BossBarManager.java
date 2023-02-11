package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarManager {
    BossBar bossbar;
    public BossBarManager()
    {
        bossbar= Bukkit.createBossBar("残り時間: ", BarColor.RED, BarStyle.SOLID);
        DisplayForAllPlayer();
    }
    private void DisplayForAllPlayer()
    {
        for(World world:Bukkit.getWorlds())
        {
            for(Player player:world.getPlayers())
            {
                bossbar.addPlayer(player);
            }
        }
    }
    public void UpdateBossBar(int max, int now)
    {
        DisplayForAllPlayer();
        bossbar.setProgress((double)now/(double)max);
        bossbar.setTitle("残り: "+now/60+"分"+now%60+"秒");
    }
    public void Destroy()
    {
        bossbar.removeAll();
    }

    public void onDisable()
    {
        Destroy();
    }
}
