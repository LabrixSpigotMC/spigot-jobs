package net.nothing.jobs.utils.jobs.task.list;

import net.nothing.jobs.JobsBootstrap;
import net.nothing.jobs.utils.jobs.Job;
import net.nothing.jobs.utils.jobs.JobManager;
import net.nothing.jobs.utils.jobs.JobMode;
import net.nothing.jobs.utils.jobs.cached.CachedJob;
import net.nothing.jobs.utils.jobs.task.Task;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class RemoveBlockTask implements Task {

    private final JobManager jobManager;
    private final Material breakable;

    public RemoveBlockTask(final Material breakable, final Job job){
        this.jobManager = JobsBootstrap.getInstance().getJobManager();
        this.breakable = breakable;

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR)
            public void handle(final BlockBreakEvent event){
                final Player player = event.getPlayer();
                final Block block = event.getBlock();

                if(jobManager.getCachedJobFromPlayer(player) == null) return;
                if(jobManager.getCachedJobFromPlayer(player).getJobMode().equals(getJobMode())){
                    if(block.getType() != breakable) return;
                    final CachedJob cachedJob = jobManager.getCachedJobFromPlayer(player);
                    cachedJob.addOneSuccess();

                    if(cachedJob.getCurrentSuccess() == job.getRequiredNumberForTheReward()){
                        job.onReward(player);
                        cachedJob.setCurrentSuccess(0);
                    }
                }
            }
        }, JobsBootstrap.getInstance());
    }

    @Override
    public JobMode getJobMode() {
        return JobMode.REMOVE_BLOCKS;
    }

    public Material getBreakable() {
        return breakable;
    }
}
