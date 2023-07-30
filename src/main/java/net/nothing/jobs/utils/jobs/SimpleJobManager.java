package net.nothing.jobs.utils.jobs;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.nothing.jobs.JobsBootstrap;
import net.nothing.jobs.utils.jobs.cached.CachedJob;
import net.nothing.jobs.utils.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SimpleJobManager implements JobManager {

    private final List<Job> jobs;
    private final Map<UUID, CachedJob> cachedJobMap;

    private final MySQL mySQL;

    public SimpleJobManager(final MySQL mySQL){
        this.mySQL = mySQL;

        this.jobs = Lists.newLinkedList();
        this.cachedJobMap = Maps.newConcurrentMap();
    }

    @Override
    public void registerJob(Job job) {
        Bukkit.getPluginManager().registerEvents(job, JobsBootstrap.getInstance());

        jobs.add(job);
        job.onLoad();
    }

    @Override
    public void unregisterJob(Job job) {
        HandlerList.unregisterAll(job);

        assert jobs.contains(job);
        jobs.remove(job);

        job.onUnload();
    }

    @Override
    public CachedJob createCachedJob(UUID uuid) {
        final ResultSet resultSet = mySQL.query("SELECT * FROM Jobs WHERE UUID='" + uuid + "';");
        try {
            if(resultSet.next()){
                final Job job = getJob(resultSet.getString("JOB_NAME"));
                final CachedJob cachedJob = new CachedJob(job, job.getJobMode(), uuid, resultSet.getInt("CURRENT_SUCCESS"), resultSet.getInt("CURRENT_SUCCESS"));

                cachedJobMap.put(uuid, cachedJob);
                resultSet.close();

                return cachedJob;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createPlayer(UUID uuid, Job job) {
        mySQL.update("INSERT INTO Jobs(UUID, JOB_NAME, CURRENT_SUCCESS) VALUES ('" + uuid + "', '" + job.getName() + "', '0');");
    }

    @Override
    public void deletePlayer(UUID uuid) {
        if(playerExists(uuid))
            mySQL.update("DELETE FROM Jobs WHERE UUID='" + uuid + "'");
    }

    @Override
    public Boolean playerExists(UUID uuid) {
        final ResultSet resultSet = mySQL.query("SELECT * FROM Jobs WHERE UUID='" + uuid + "'");
        try {
            if(resultSet.next() && resultSet.getString("UUID") != null) {
                resultSet.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void buildSqlStatsForPlayer(CachedJob cachedJob, UUID uuid) {
        mySQL.update("UPDATE Jobs SET JOB_NAME='" + cachedJob.getJob().getName() + "' WHERE UUID='" + uuid + "';");
        mySQL.update("UPDATE Jobs SET CURRENT_SUCCESS='" + cachedJob.getCurrentSuccess() + "' WHERE UUID='" + uuid + "';");
    }

    @Override
    public Job getJob(String jobName) {
        for (Job job : jobs)
            if(job.getName().equalsIgnoreCase(jobName))
                return job;
        return null;
    }

    @Override
    public CachedJob getCachedJobFromPlayer(Player player) {
        if(cachedJobMap.get(player.getUniqueId()) == null)
            return null;
        return cachedJobMap.get(player.getUniqueId());
    }

    @Override
    public List<Job> getJobs() {
        return this.jobs;
    }

    @Override
    public Map<UUID, CachedJob> getCachedJobMap() {
        return cachedJobMap;
    }
}
