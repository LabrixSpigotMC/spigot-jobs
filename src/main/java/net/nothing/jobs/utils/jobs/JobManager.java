package net.nothing.jobs.utils.jobs;

import net.nothing.jobs.utils.jobs.cached.CachedJob;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface JobManager {

    void registerJob(final Job job);
    void unregisterJob(final Job job);

    void createPlayer(final UUID uuid, final Job job);
    void deletePlayer(final UUID uuid);

    void buildSqlStatsForPlayer(final CachedJob cachedJob, final UUID uuid);

    Boolean playerExists(final UUID uuid);

    CachedJob createCachedJob(final UUID uuid);
    Job getJob(final String jobName);
    CachedJob getCachedJobFromPlayer(final Player player);

    List<Job> getJobs();
    Map<UUID, CachedJob> getCachedJobMap();
}
