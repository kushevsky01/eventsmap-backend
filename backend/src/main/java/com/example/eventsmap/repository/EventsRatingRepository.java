package com.example.eventsmap.repository;

import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.EventsRating;
import com.example.eventsmap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface EventsRatingRepository extends JpaRepository<EventsRating, Long> {

    EventsRating findByUserIdAndEventsId (Long userId, Long eventsId);

    @Query("select NEW com.example.eventsmap.repository.EventsRatingAvg (e.id.eventsId, avg(e.rating)) from EventsRating AS e group by e.id.eventsId ")
    List<EventsRatingAvg> findEvents();

    @Query("select e.id.eventsId, avg(e.rating) from EventsRating AS e group by e.id.eventsId ")
    List<List<?>> findEventsByEvent();

    void deleteAllByEvents(Events events);
}

