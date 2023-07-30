package net.nothing.jobs.listeners;

import net.nothing.jobs.JobsBootstrap;
import net.nothing.jobs.utils.jobs.JobManager;
import net.nothing.jobs.utils.jobs.cached.CachedJob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void handle(final PlayerQuitEvent event){
        final Player player = event.getPlayer();
        final JobManager jobManager = JobsBootstrap.getInstance().getJobManager();

        if(jobManager.getCachedJobFromPlayer(player) == null)
            return;
        final CachedJob cachedJob = jobManager.getCachedJobFromPlayer(player);
        if(cachedJob.getCurrentSuccess() != cachedJob.getStartingSuccess())
            jobManager.buildSqlStatsForPlayer(cachedJob, player.getUniqueId());
    }
}
