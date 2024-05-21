
package si.f5.actedsauce.manhuntmc.manhuntmc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ManhuntCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args) {
        if(command.getName().equalsIgnoreCase("manhunt"))
        {
                if(ManhuntMC.instance().getMainLoop().Running.get())
                {
                    ManhuntMC.instance().getMainLoop().stopReq.set(true);
                    return true;
                }
            if(args.length==0)
            {
                ManhuntMC.instance().newMainLoop();
                ManhuntMC.instance().getMainLoop().SetCount(1800);
                ManhuntMC.instance().getMainLoop().runTaskTimer(PluginMain.getPlugin(),0,20);
                return true;
            }
            else if(args.length==1)
            {
                try
                {
                    ManhuntMC.instance().newMainLoop();
                    ManhuntMC.instance().getMainLoop().SetCount(Integer.parseInt(args[0]));
                    ManhuntMC.instance().getMainLoop().runTaskTimer(PluginMain.getPlugin(),0,20);
                    return true;
                }
                catch (NumberFormatException e)
                {
                    return false;
                }
            }

        }
        return false;
    }

    public void onDisable()
    {
        ManhuntMC.instance().getMainLoop().onDisable();
    }
}
