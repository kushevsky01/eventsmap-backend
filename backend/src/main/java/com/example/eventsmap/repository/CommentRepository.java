package com.example.eventsmap.repository;

import com.example.eventsmap.model.Comment;
import com.example.eventsmap.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Ищет комментарии мероприятия
     * @param events мероприятие
     * @return список комметанриев мероприятия
     */
    List<Comment> findByEvents(Events events);

    /**
     * Ищет комметарий мероприятия по id
     * @param id комментария
     * @param events мероприятие
     * @return мероприятие
     */
    Optional<Comment> findByIdAndEvents(Long id, Events events);
}
