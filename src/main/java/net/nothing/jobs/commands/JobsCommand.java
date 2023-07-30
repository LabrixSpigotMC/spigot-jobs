package net.nothing.jobs.commands;

import net.nothing.jobs.JobsBootstrap;
import net.nothing.jobs.utils.jobs.Job;
import net.nothing.jobs.utils.jobs.cached.CachedJob;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JobsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            return true;

        final Player player = (Player) sender;

        switch (args.length){
            case 0:
                if(args[0].equalsIgnoreCase("setup")){

                }

                player.sendMessage(JobsBootstrap.PREFIX + "§7Usage: ");
                player.sendMessage(JobsBootstrap.PREFIX + "§e/jobs list");
                player.sendMessage(JobsBootstrap.PREFIX + "§e/jobs join <name>");
                player.sendMessage(JobsBootstrap.PREFIX + "§e/jobs leave ");
                player.sendMessage(JobsBootstrap.PREFIX + "§e/jobs info");
                break;
            case 1:
                if(args[0].equalsIgnoreCase("list")){
                    for (Job job : JobsBootstrap.getInstance().getJobManager().getJobs()) {
                        player.sendMessage(JobsBootstrap.PREFIX + "§7> §a" + job.getName() + ":");
                        player.sendMessage(JobsBootstrap.PREFIX + "§7§o" + job.getDescription() + "");
                    }
                    return true;
                }
                if(args[0].equalsIgnoreCase("info")){
                    if(JobsBootstrap.getInstance().getJobManager().getCachedJobFromPlayer(player) == null){
                        player.sendMessage(JobsBootstrap.PREFIX + "§c§oDu hast aktuell keine Arbeit.");
                        return true;
                    }

                    final CachedJob cachedJob = JobsBootstrap.getInstance().getJobManager().getCachedJobFromPlayer(player);

                    player.sendMessage(JobsBootstrap.PREFIX + "§7Aktueller Stand: " + cachedJob.getCurrentSuccess());
                    player.sendMessage(JobsBootstrap.PREFIX + "§7Gestartet mit: " + cachedJob.getStartingSuccess());
                    player.sendMessage(JobsBootstrap.PREFIX + "§7Benötigt: : " + cachedJob.getJob().getRequiredNumberForTheReward());
                    player.sendMessage(JobsBootstrap.PREFIX + "§7Job-Typ: " + cachedJob.getJobMode());
                    player.sendMessage(JobsBootstrap.PREFIX + "§7Job: " + cachedJob.getJob().getInGameName());
                    player.sendMessage(JobsBootstrap.PREFIX + "§7Job Beschreibung: " + cachedJob.getJob().getDescription());
                }

                if(args[0].equalsIgnoreCase("leave")){
                    if(JobsBootstrap.getInstance().getJobManager().getCachedJobFromPlayer(player) == null){
                        player.sendMessage(JobsBootstrap.PREFIX + "§c§oDu hast aktuell keine Arbeit.");
                        return true;
                    }
                    final Job job = JobsBootstrap.getInstance().getJobManager().getCachedJobFromPlayer(player).getJob();
                    job.onDisableJob(player);
                }
                break;
            case 2:
                if(args[0].equalsIgnoreCase("join")){
                    if(JobsBootstrap.getInstance().getJobManager().getJob(args[1]) == null){
                        player.sendMessage(JobsBootstrap.PREFIX + "§c§oDiesen Job gibt es nicht");
                        return true;
                    }
                    final Job job = JobsBootstrap.getInstance().getJobManager().getJob(args[1]);
                    job.onActivateJob(player);
                    return true;
                }
                break;
        }

        return false;
    }
}
