package com.example.eventsmap.controller;

import com.example.eventsmap.dto.CommentDTO;
import com.example.eventsmap.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest контроллер для операций над сущностью Comment
 */
@RestController
@RequestMapping("events/{eventId}/comments")
public class CommentController {

    /**
     * Сервис с операциями над сущностью Comment
     */
    private final CommentService commentService;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Запрос на поиск всех комментариев мероприятия
     * @param eventId id мероприятия
     * @return все комментарии
     */
    @GetMapping
    public List<CommentDTO> getAll(@PathVariable(name = "eventId") long eventId) {

        return commentService.getAllComments(eventId);
    }

    /**
     * Запрос на поиск комментария мероприятия по id
     * @param commentId id комментария
     * @param eventsId id мероприятия
     * @return найденный комментарий в формате JSON
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(name = "commentId") long commentId, @PathVariable(name = "eventId") long eventsId) {
        CommentDTO comment = commentService.getCommentById(commentId, eventsId);

        return new ResponseEntity<>(comment, HttpStatus.OK) ;
    }

    /**
     * Запрос на саздание комментария мероприятия
     * @param commentDTO тело запроса
     * @param eventId id мероприятия
     * @return сазданный комментарий в формате JSON
     */
    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "eventId") long eventId,
                                    @RequestBody CommentDTO commentDTO) {

        CommentDTO comment = commentService.createComment(commentDTO, eventId);

        return new ResponseEntity<>(comment, HttpStatus.CREATED );
    }

    /**
     * Запрос на обновление данных комментария мероприятия
     * @param commentId id комментария
     * @param eventId id ероприятия
     * @param commentDTO тело запроса
     * @return обновленный комментарий в формате JSON
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "commentId") long commentId, @PathVariable(name = "eventId") long eventId, @RequestBody CommentDTO commentDTO){
        CommentDTO comment = commentService.updateComment(commentId, eventId, commentDTO);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    /**
     * Запрос на удаление комментария мероприятия
     * @param commentId id комментария
     * @param eventId id мероприятия
     * @return статус запроса
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable(name = "commentId") long commentId,@PathVariable(name = "eventId") long eventId){
        commentService.deleteComment(commentId, eventId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
