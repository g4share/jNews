package com.g4share.daemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import java.util.Collections;
import java.util.Map;

/**
 * User: gm
 */

@Configuration
@EnableScheduling
@PropertySource("classpath:conf/daemon.properties")
@ComponentScan(basePackages =  { "com.g4share" })
public class AppConfig implements SchedulingConfigurer {

    @Autowired
    private Processor processor;

    private @Value("${daemon.cron.expression:3/10 * * * * ?}")  String cronExpression;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        Map<Runnable, Trigger> map = Collections.<Runnable, Trigger> singletonMap(new Runnable() {
            public void run() {
                processor.process();
            }
        }, new CronTrigger(cronExpression));

        taskRegistrar.setTriggerTasks(map);
    }
}