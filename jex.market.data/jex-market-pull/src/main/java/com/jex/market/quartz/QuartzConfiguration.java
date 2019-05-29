package com.jex.market.quartz;


import com.jex.market.dao.enums.ExchangeEnum;
import com.jex.market.dto.JobDataInfoDTO;
import com.jex.market.job.PriceJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Configuration
public class QuartzConfiguration {
    /**
     *  任务调度器
     */
    public static Scheduler wapischeduler= null;

    public static Map<String, JobDataInfoDTO> jobDetailMap = new HashMap<>();

    @Bean
    public void getJobs(){
        JobDetail jobPrice = newJob(PriceJob.class) //定义Job类为HelloQuartz类，这是真正的执行逻辑所在
                .withIdentity(ExchangeEnum.binance.getDesc(), PriceJob.class.getSimpleName()) //定义name/group
                .usingJobData(ExchangeEnum.binance.getDesc(), PriceJob.class.getSimpleName()) //定义属性
                .build();
        String namePrice = ExchangeEnum.binance.getDesc()+PriceJob.class.getSimpleName();
        Trigger trigger = getTrigger();
        JobDataInfoDTO jobDataInfoDTO = new JobDataInfoDTO();

        jobDataInfoDTO.setJob(jobPrice);
        jobDataInfoDTO.setTrigger(trigger);

       jobDetailMap.put(namePrice,jobDataInfoDTO);
    }


    public Trigger getTrigger(){
        Trigger trigger = newTrigger().withIdentity("trigger1", "group1") //定义name/group
                // .startNow()//一旦加入scheduler，立即生效
                .startAt(new Date())//设置触发开始的时间
                .withSchedule(simpleSchedule() //使用SimpleTrigger
                        .withIntervalInSeconds(1) //每隔一秒执行一次
                        .repeatForever()) //一直执行，奔腾到老不停歇
                .build();
        return trigger;
    }

    @Bean(name="wapi")
    public void CronJobTrigger() {
        try {

            wapischeduler = StdSchedulerFactory.getDefaultScheduler();
            getJobs();
            for(Map.Entry<String, JobDataInfoDTO> map: jobDetailMap.entrySet()){
                //加入这个调度
                wapischeduler.scheduleJob(map.getValue().getJob(), map.getValue().getTrigger());
            }
            //启动之
            wapischeduler.start();

        }catch (Exception ex){

        }

    }
}
