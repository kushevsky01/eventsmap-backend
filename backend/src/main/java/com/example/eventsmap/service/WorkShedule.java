package com.example.eventsmap.service;

import com.example.eventsmap.model.Events;
import com.example.eventsmap.repository.EventsRatingAvg;
import com.example.eventsmap.repository.EventsRatingRepository;
import com.example.eventsmap.repository.EventsRepository;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class WorkShedule {

    private final EventsRepository eventsRepository;

    private final EventsService eventsService;

    private final EventsRatingService eventsRatingService;

    private final EventsRatingRepository eventsRatingRepository;

    public WorkShedule(EventsRepository eventsRepository, EventsService eventsService, EventsRatingService eventsRatingService, EventsRatingRepository eventsRatingRepository) {
        this.eventsRepository = eventsRepository;
        this.eventsService = eventsService;
        this.eventsRatingService = eventsRatingService;
        this.eventsRatingRepository = eventsRatingRepository;
    }

    @Transactional
    @Async
    @Scheduled(cron = "0 0 0 * * *")
    @SchedulerLock(name = "deleteEvents", lockAtLeastFor = "5m", lockAtMostFor = "10m")
    public void deleteEvents(){
        LocalDate now = LocalDate.now();
        LocalDate lastWeekStart = now.minusDays(7);
        Date date = Date.from(lastWeekStart.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Events> events =  eventsRepository.findByDateLessThan(date);
        for(Events event : events){
            eventsService.deleteEvent(event.getId());
        }
    }

    @Transactional
    @Async
    @Scheduled(cron = "0 30 0 * * *")
    @SchedulerLock(name = "setRating", lockAtLeastFor = "15m", lockAtMostFor = "20m")
    public void setRating(){
        List<EventsRatingAvg> events = eventsRatingRepository.findEvents();
        eventsRatingService.updateAllRatings(events);
    }
}
