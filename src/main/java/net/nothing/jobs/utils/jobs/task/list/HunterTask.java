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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class HunterTask implements Task {

    private final JobManager jobManager;
    private final EntityType entityType;

    public HunterTask(final Job job, final EntityType entityType){
        this.jobManager = JobsBootstrap.getInstance().getJobManager();
        this.entityType = entityType;

        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler(priority = EventPriority.MONITOR)
            public void handle(final EntityDeathEvent event){
                if(event.getEntity().getKiller() == null)
                    return;

                final Player player = event.getEntity().getKiller();
                final Entity entity = event.getEntity();

                if(!entity.getType().equals(entityType))
                    return;

                if(jobManager.getCachedJobFromPlayer(player) == null) return;
                if(jobManager.getCachedJobFromPlayer(player).getJobMode().equals(getJobMode())){
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

    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public JobMode getJobMode() {
        return JobMode.FARMS_ANIMAL;
    }
}
