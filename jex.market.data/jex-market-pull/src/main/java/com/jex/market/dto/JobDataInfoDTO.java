package com.jex.market.dto;


import lombok.Data;
import org.quartz.JobDetail;
import org.quartz.Trigger;

@Data
public class JobDataInfoDTO {
    private JobDetail job;
    private Trigger trigger;

}
