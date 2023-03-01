package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.scheduler.BukkitRunnable;

public class FootPrint extends BukkitRunnable {
    final int duration;
    final Location loc;
    int count;
    public FootPrint(int duration, Location loc)
    {
        this.duration=duration;
        this.loc=loc;
        count=duration;
    }
    @Override
    public void run() {
        if(
                count==0||
                (loc.getWorld().getBlockAt((int) loc.getX(), (int) ((double)loc.getY()-1), (int) loc.getZ()).getType().isAir())||
                (loc.getWorld().getBlockAt((int) loc.getX(), (int) ((double)loc.getY()-1), (int) loc.getZ()).isLiquid())
        )
        {
            cancel();
            return;
        }
        loc.getWorld().spawnParticle(Particle.REDSTONE,loc,25,0.2,0.2,0.2,20,new Particle.DustOptions(Color.fromRGB(200, 200, 200),1));
        count--;
    }
}
