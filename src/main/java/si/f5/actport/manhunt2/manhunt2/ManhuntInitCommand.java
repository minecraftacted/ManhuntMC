
package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static si.f5.actport.manhunt2.manhunt2.Manhunt2.LINE;

public class ManhuntInitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("manhuntinit"))
        {
            if(Manhunt2.instance().ManhuntInitializing())
            {
                Bukkit.broadcastMessage(LINE+"\nチーム分けを終了。\n"+ LINE);
                Manhunt2.instance().ManhuntInitializing(false);
            }
            else
            {
                Bukkit.broadcastMessage
                (LINE+"\nチーム分けを開始。"+"\n"+ ChatColor.RED+"鬼になる場合は「hunter」、"+ChatColor.AQUA+"逃走者になる場合は「runner」"+ChatColor.RESET+"とチャットに打ち込んでください。自動でチーム分けします。"+"\n"+"⚠コマンドではなく、チャットに直接打ち込んでください。\n"+ LINE);
                Manhunt2.instance().ManhuntInitializing(true);
            }
            return true;
        }
        return false;
    }

    public void onDisable()
    {

    }
}
