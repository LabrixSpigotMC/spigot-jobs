package net.nothing.jobs.utils.jobs.cached;

import net.nothing.jobs.utils.jobs.Job;
import net.nothing.jobs.utils.jobs.JobMode;

import java.util.UUID;

public class CachedJob {

    private Job job;
    private JobMode jobMode;
    private UUID uuidFromPlayer;

    private int currentSuccess;
    private int startingSuccess;

    public CachedJob(Job job, JobMode jobMode, UUID uuidFromPlayer, int currentSuccess, int startingSuccess) {
        this.job = job;
        this.jobMode = jobMode;
        this.uuidFromPlayer = uuidFromPlayer;
        this.currentSuccess = currentSuccess;
    }

    public void addOneSuccess(){
        setCurrentSuccess((getCurrentSuccess() + 1));
    }

    public void setStartingSuccess(int startingSuccess) {
        this.startingSuccess = startingSuccess;
    }

    public int getStartingSuccess() {
        return startingSuccess;
    }

    public void setCurrentSuccess(int currentSuccess) {
        this.currentSuccess = currentSuccess;
    }

    public int getCurrentSuccess() {
        return currentSuccess;
    }

    public Job getJob() {
        return job;
    }

    public JobMode getJobMode() {
        return jobMode;
    }

    public UUID getUuidFromPlayer() {
        return uuidFromPlayer;
    }
}
