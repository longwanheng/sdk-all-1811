package com.test.sdk.script.job;

import com.test.sdk.data.service.GameDataService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DailyRegistStat implements Job {


    private static GameDataService gameDataService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("开始统计");
        // gameDataService.doStatRegistData();
    }
    @Autowired
    public  void setGameDataService(GameDataService gameDataService) {
        DailyRegistStat.gameDataService = gameDataService;
    }
}
