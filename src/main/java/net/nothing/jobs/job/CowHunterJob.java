package net.nothing.jobs.job;

import net.nothing.jobs.JobsBootstrap;
import net.nothing.jobs.utils.jobs.Job;
import net.nothing.jobs.utils.jobs.JobMode;
import net.nothing.jobs.utils.jobs.task.Task;
import net.nothing.jobs.utils.jobs.task.list.HunterTask;
import net.nothing.jobs.utils.jobs.task.list.RemoveBlockTask;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CowHunterJob extends Job {

    private Configuration configuration;

    @Override
    public void onLoad() {
        configuration = JobsBootstrap.getInstance().getConfig();
        this.getTask();
    }

    @Override
    public void onActivateJob(Player player) {
        super.onActivateJob(player);
    }

    @Override
    public void onDisableJob(Player player) {
        super.onDisableJob(player);
    }

    @Override
    public Integer getRequiredNumberForTheReward() {
        return configuration.getInt("jobs." + getName() + ".requiredNumberForTheReward");
    }

    @Override
    public void onReward(final Player player) {
        player.getInventory().addItem(new ItemStack(Material.valueOf(configuration.getString("jobs." + getName() + ".reward_material"))));
        player.sendMessage(JobsBootstrap.PREFIX + "§aDu hast eine Belohnung für deine Arbeit bekommen! §e(1x " + Material.valueOf(configuration.getString("jobs." + getName() + ".reward_material")).name() +")");
    }

    @Override
    public String getInGameName() {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configuration.getString("jobs." + getName() + ".name")));
    }

    @Override
    public String getName() {
        return "CowHunter";
    }

    @Override
    public String getDescription() {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(configuration.getString("jobs." + getName() + ".description")));
    }

    @Override
    public Material getIcon() {
        return Material.valueOf(configuration.getString("jobs." + getName() + ".icon"));
    }

    @Override
    public JobMode getJobMode() {
        return JobMode.FARMS_ANIMAL;
    }

    @Override
    public Task getTask() {
        return new HunterTask(this, EntityType.COW);
    }
}
