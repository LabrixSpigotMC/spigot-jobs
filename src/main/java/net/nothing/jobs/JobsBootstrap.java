package net.nothing.jobs;

import net.nothing.jobs.commands.JobsCommand;
import net.nothing.jobs.job.CowHunterJob;
import net.nothing.jobs.job.StoneCollectorJob;
import net.nothing.jobs.listeners.PlayerJoinListener;
import net.nothing.jobs.listeners.PlayerQuitListener;
import net.nothing.jobs.utils.jobs.JobManager;
import net.nothing.jobs.utils.jobs.SimpleJobManager;
import net.nothing.jobs.utils.jobs.cached.CachedJob;
import net.nothing.jobs.utils.mysql.MySQL;
import net.nothing.jobs.utils.mysql.SimpleMySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class JobsBootstrap extends JavaPlugin {

    private static JobsBootstrap instance;
    public static final String PREFIX = getInstance().getPrefix();

    private MySQL mySQL;
    private JobManager jobManager;

    /**
     * Plugin startup logic
     */
    @Override
    public void onEnable() {
        saveDefaultConfig();
        successSQL();
        init();
        register();
    }


    /**
     * Plugin disable logic
     */
    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final JobManager jobManager = JobsBootstrap.getInstance().getJobManager();
            if(jobManager.getCachedJobFromPlayer(player) == null)
                return;
            final CachedJob cachedJob = jobManager.getCachedJobFromPlayer(player);
            if(cachedJob.getCurrentSuccess() != cachedJob.getStartingSuccess())
                jobManager.buildSqlStatsForPlayer(cachedJob, player.getUniqueId());
        });
    }

    /**
     * Initialize something
     */
    private void init(){
        instance = this;
        jobManager = new SimpleJobManager(this.mySQL);
    }

    /**
     * Creates a connection to the database
     */
    private void successSQL(){
        this.mySQL = new SimpleMySQL(getConfig().getString("mysql.host"), getConfig().getString("mysql.database"), getConfig().getString("mysql.user"), getConfig().getString("mysql.password"), getConfig().getInt("mysql.port"), false);
        this.mySQL.update("CREATE TABLE IF NOT EXISTS Jobs(UUID varchar(64), JOB_NAME varchar(64), CURRENT_SUCCESS int);");
    }

    /**
     * Register events, commands & jobs
     */
    private void register(){
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);

        getJobManager().registerJob(new StoneCollectorJob());
        getJobManager().registerJob(new CowHunterJob());

        getCommand("jobs").setExecutor(new JobsCommand());
    }

    /**
     * Returns the instance
     * @return addressed instance
     */
    public static JobsBootstrap getInstance() {
        return instance;
    }

    /**
     * Returns the MySQL
     * @return addressed sql
     */
    public MySQL getMySQL() {
        return mySQL;
    }

    /**
     * Returns the job manager
     * @return addressed job manager
     */
    public JobManager getJobManager() {
        return jobManager;
    }

    /**
     * Returns the prefix
     * @return resolved
     */
    public String getPrefix(){
        return getConfig().getString("prefix");
    }
}
