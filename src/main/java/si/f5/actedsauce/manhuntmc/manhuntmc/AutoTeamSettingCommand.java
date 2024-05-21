package si.f5.actedsauce.manhuntmc.manhuntmc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class AutoTeamSettingCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label,String[] args)
    {
        if(command.getName().equalsIgnoreCase("autoteamsetting"))
        {
            if(ManhuntMC.instance().getMainLoop().Running.get())
            {
                sender.sendMessage("ゲームが実行中です");
                return false;
            }
            if(args.length>=2)
            {
                sender.sendMessage("引数が多すぎます");
                return false;
            }
            if(args.length==0)
            {
                sender.sendMessage("総プレイヤーの半分を鬼にします");
                RandomTeamSetting(Bukkit.getOnlinePlayers().size()/2);
                return true;
            }
            try {
                if (Integer.parseInt(args[0]) > Bukkit.getOnlinePlayers().size()) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
            RandomTeamSetting(Integer.parseInt(args[0]));
            return true;
        }
        return false;
    }
    void RandomTeamSetting(int numOfHunter)
    {
        Random random=new Random();
        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for(Player player:players)
        {
            ManhuntMC.instance().RemovePlayerFromRunner(player);
            ManhuntMC.instance().RemovePlayerFromHunter(player);
        }
        while(ManhuntMC.instance().getHunterTeam().getSize()<numOfHunter)//ハンターの数が目標より小さい間
        {
            var playerIndex=random.nextInt(players.size());
            ManhuntMC.instance().JoinHunterTeam(players.get(playerIndex));
        }
        for(Player player:players)
        {
            if(!ManhuntMC.instance().getHunterTeam().hasPlayer(player))
            {
                ManhuntMC.instance().JoinRunnerTeam(player);
            }
        }
    }
}
