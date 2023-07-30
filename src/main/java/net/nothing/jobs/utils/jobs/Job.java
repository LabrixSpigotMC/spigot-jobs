package net.nothing.jobs.utils.jobs;

import net.nothing.jobs.JobsBootstrap;
import net.nothing.jobs.utils.jobs.task.Task;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Job implements Listener {

    /**
     * Executed as soon as a job is loaded
     */
    public void onLoad(){}

    /**
     * Executed as soon as a job is unloaded
     */
    public void onUnload(){}

    /**
     * Executed as soon as a player activates the job
     */
    public void onActivateJob(final Player player){
        player.sendMessage("try...");

        if(!JobsBootstrap.getInstance().getJobManager().playerExists(player.getUniqueId())) {
            JobsBootstrap.getInstance().getJobManager().createPlayer(player.getUniqueId(), this);
            JobsBootstrap.getInstance().getJobManager().createCachedJob(player.getUniqueId());
        }else
            player.sendMessage(JobsBootstrap.PREFIX + "§c§oVerlasse erst dein aktuellen Job um ein neuen anzufangen");
    }

    /**
     * Executed as soon as a player disable the job
     */
    public void onDisableJob(final Player player){
        player.sendMessage("try...");

        if(JobsBootstrap.getInstance().getJobManager().playerExists(player.getUniqueId())) {
            JobsBootstrap.getInstance().getJobManager().deletePlayer(player.getUniqueId());
            if(JobsBootstrap.getInstance().getJobManager().getCachedJobFromPlayer(player) != null)
                JobsBootstrap.getInstance().getJobManager().getCachedJobMap().remove(player.getUniqueId());
        }
    }

    /**
     * Get required number for the reward
     * @return required number
     */
    public abstract Integer getRequiredNumberForTheReward();
    /**
     * When a player has successfully completed the task and now receives the reward
     */
    public abstract void onReward(final Player player);
    /**
     * The name of the job
     * @return addressed job
     */
    public abstract String getName();
    /**
     * The ingame name of the job
     * @return addressed job
     */
    public abstract String getInGameName();
    /**
     * The description of the job
     * @return addressed description
     */
    public abstract String getDescription();
    /**
     * The material/icon from the job
     * @return addressed material/icon
     */
    public abstract Material getIcon();
    /**
     * Returns the job mode
     * @return addressed job mode
     */
    public abstract JobMode getJobMode();

    public abstract Task getTask();

}
