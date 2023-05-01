package com.example.eventsmap.config;

import com.example.eventsmap.model.Events;
import com.example.eventsmap.repository.EventsRepository;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
@EnableAsync
public class SchedulerConfiguration {
    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withTableName("shedlock")
                        .withColumnNames(new JdbcTemplateLockProvider.ColumnNames("name", "lock_until", "locked_at", "locked_by"))
                        .withJdbcTemplate(new JdbcTemplate(dataSource))
//                        .withLockedByValue("my-value")
//                        .usingDbTime()
                        .build());
    }


}
