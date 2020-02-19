package com.jex.take.data.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "trendyml")

public class Trendyml {
    private String from;
    private String index;
    private List<String> exchange;
}