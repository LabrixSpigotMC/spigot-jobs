package net.nothing.jobs.listeners;

import net.nothing.jobs.JobsBootstrap;
import net.nothing.jobs.utils.jobs.JobManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void handle(final PlayerJoinEvent event){
        final Player player = event.getPlayer();
        final JobManager jobManager = JobsBootstrap.getInstance().getJobManager();

        jobManager.createCachedJob(player.getUniqueId());
    }
}
