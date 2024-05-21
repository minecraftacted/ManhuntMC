package si.f5.actedsauce.manhuntmc;

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
        int h,m,s;
        h=now/3600;
        m=(now/60)%60;
        s=now%60;
        bossbar.setTitle
        (
            "残り: "+(h>=10?"":"0")+h+"時間"+
            (m>=10?"":"0")+m+"分"+
            (s>=10?"":"0")+s+"秒"
        );
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
