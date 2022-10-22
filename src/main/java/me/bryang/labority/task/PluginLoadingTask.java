package me.bryang.labority.task;

import me.bryang.labority.PluginCore;
import me.bryang.labority.data.JobData;
import me.bryang.labority.data.PlayerData;
import me.bryang.labority.loader.DataLoader;
import me.bryang.labority.manager.file.FileDataManager;
import me.bryang.labority.manager.file.FileManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PluginLoadingTask {

    private final FileDataManager playersFile;
    private final DataLoader dataLoader;

    public PluginLoadingTask(PluginCore pluginCore){

        this.playersFile = pluginCore.getFilesLoader().getPlayersFile();
        this.dataLoader = pluginCore.getDataLoader();

    }

    public void loadTask(){

        for (String stringToUUID : playersFile.getPlayersKeys()){
            UUID playerUniqueId = UUID.fromString(stringToUUID);

            dataLoader.createPlayerJob(playerUniqueId);

            PlayerData playerData = dataLoader.getPlayerJob(playerUniqueId);

            for (String jobName : playersFile.getJobsKeys(playerUniqueId)){

                JobData jobData = new JobData(jobName);

                jobData.setLevel(playersFile.getJobData(playerUniqueId).getInt(".list-jobs." + jobName + ".xp"));
                jobData.setLevel(playersFile.getJobData(playerUniqueId).getInt(".list-jobs." + jobName + ".level"));

                playerData.addJobData(jobName, jobData);
            }
        }
    }
}