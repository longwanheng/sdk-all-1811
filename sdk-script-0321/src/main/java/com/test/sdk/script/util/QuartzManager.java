package com.test.sdk.script.util;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzManager {
    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private static final String GROUP_NAME="game_script";
    public static void addJob(String scriptName, Class jobClass, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            // 任务名，任务组，任务执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(scriptName, GROUP_NAME).build();
            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(scriptName, GROUP_NAME);
            triggerBuilder.startNow();
            // 触发器时间设定
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();
            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    /**
     * @Description: 修改一个任务的触发时间
     *
     * @param cron   时间设置，参考quartz说明文档
     */
    public static void modifyJobTime(String scriptName, String cron) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(scriptName, GROUP_NAME);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(scriptName, GROUP_NAME);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */
                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                //Class<? extends Job> jobClass = jobDetail.getJobClass();
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @Description: 移除一个任务
     *
     */
    public static void removeJob(String scriptName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(scriptName, GROUP_NAME);
            sched.pauseTrigger(triggerKey);// 停止触发器
            sched.unscheduleJob(triggerKey);// 移除触发器
            sched.deleteJob(JobKey.jobKey(scriptName, GROUP_NAME));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void startJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public static void shutdownJobs() {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void pauseJob(String jobName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, GROUP_NAME);
            // sched.pauseJob(jobName, JOB_GROUP_NAME);
            sched.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    public static void resumeJob(String jobName) {
        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, GROUP_NAME);
            // sched.resumeJob(jobName, JOB_GROUP_NAME);
            sched.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }
    public static Integer getJobStatus(String jobName) {

        try {
            Scheduler sched = schedulerFactory.getScheduler();
            JobKey jobKey = JobKey.jobKey(jobName, GROUP_NAME);
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, GROUP_NAME);
            // return sched.getTriggerState(jobName, TRIGGER_GROUP_NAME);
            Trigger.TriggerState state = sched.getTriggerState(triggerKey);
            return state.ordinal();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        int unknowStatus = -7;
        return unknowStatus;
    }
}