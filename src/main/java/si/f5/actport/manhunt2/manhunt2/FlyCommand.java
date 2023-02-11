package si.f5.actport.manhunt2.manhunt2;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
    {
        if(command.getName().equalsIgnoreCase("fly"))
        {
            if(sender instanceof Player)
            {
                Player player =(Player) sender;
                if(player.isFlying())
                {
                    player.setFlying(false);
                }
                else
                {
                    player.setFlying(true);
                }
                return true;
            }
            return false;
        }
        return false;
    }
}
