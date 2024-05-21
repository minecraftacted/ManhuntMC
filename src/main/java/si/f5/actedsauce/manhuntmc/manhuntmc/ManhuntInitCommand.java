
package si.f5.actedsauce.manhuntmc.manhuntmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static si.f5.actedsauce.manhuntmc.manhuntmc.ManhuntMC.LINE;

public class ManhuntInitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args) {
        if(command.getName().equalsIgnoreCase("manhuntinit"))
        {
            if(ManhuntMC.instance().ManhuntInitializing())
            {
                Bukkit.broadcastMessage(LINE+"\nチーム分けを終了。\n"+ LINE);
                ManhuntMC.instance().ManhuntInitializing(false);
            }
            else
            {
                Bukkit.broadcastMessage
                (LINE+"\nチーム分けを開始。"+"\n"+ ChatColor.RED+"鬼になる場合は「hunter」、"+ChatColor.AQUA+"逃走者になる場合は「runner」"+ChatColor.RESET+"とチャットに打ち込んでください。自動でチーム分けします。"+"\n"+"⚠コマンドではなく、チャットに直接打ち込んでください。\n"+ LINE);
                ManhuntMC.instance().ManhuntInitializing(true);
            }
            return true;
        }
        return false;
    }

    public void onDisable()
    {

    }
}
