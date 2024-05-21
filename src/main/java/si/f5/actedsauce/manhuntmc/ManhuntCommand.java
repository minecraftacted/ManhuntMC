
package si.f5.actedsauce.manhuntmc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ManhuntCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("manhunt"))
        {
                if(Manhunt2.instance().getMainLoop().Running.get())
                {
                    Manhunt2.instance().getMainLoop().stopReq.set(true);
                    return true;
                }
            if(args.length==0)
            {
                Manhunt2.instance().newMainLoop();
                Manhunt2.instance().getMainLoop().SetCount(1800);
                Manhunt2.instance().getMainLoop().runTaskTimer(PluginMain.getPlugin(),0,20);
                return true;
            }
            else if(args.length==1)
            {
                try
                {
                    Manhunt2.instance().newMainLoop();
                    Manhunt2.instance().getMainLoop().SetCount(Integer.parseInt(args[0]));
                    Manhunt2.instance().getMainLoop().runTaskTimer(PluginMain.getPlugin(),0,20);
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
        Manhunt2.instance().getMainLoop().onDisable();
    }
}
