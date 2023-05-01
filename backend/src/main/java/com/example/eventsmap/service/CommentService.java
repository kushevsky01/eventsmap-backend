package com.example.eventsmap.service;

import com.example.eventsmap.dto.CommentDTO;
import com.example.eventsmap.model.Comment;
import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.User;
import com.example.eventsmap.repository.CommentRepository;
import com.example.eventsmap.repository.EventsRepository;
import com.example.eventsmap.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис с операциями над сущностью Comment
 */
@Transactional(readOnly = true)
@Service
public class CommentService {

    /**
     * Репозиторий комментариев
     */
    private final CommentRepository commentRepository;

    /**
     * Репозиторий мероприятий
     */
    private final EventsRepository eventsRepository;

    private final UserRepository userRepository;

    /**
     * Маппер для конвертирования сущности
     */
    private final ModelMapper modelMapper;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public CommentService(CommentRepository commentRepository, EventsRepository eventsRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.eventsRepository = eventsRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Ищет комментарии мероприятия
     * @param eventId id мероприятия
     * @return комментарии мероприятия
     */
    public List<CommentDTO> getAllComments(long eventId) {
        Optional<Events> optionalEvent = Optional.ofNullable(eventsRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        List<Comment> comments = commentRepository.findByEvents(optionalEvent.get());
        return comments
                .stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Ищет комментарий мероприятия по id
     * @param commentId id комметария
     * @param eventsId id мероприятия
     * @return найденный комментарий
     */
    public CommentDTO getCommentById(long commentId, long eventsId) {
        Events event = eventsRepository.findById(eventsId).get();
        Optional<Comment> comment = Optional.ofNullable(commentRepository.findByIdAndEvents(commentId, event)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        return modelMapper.map(comment, CommentDTO.class);
    }

    /**
     * Создает комментарий для мероприятия
     * @param commentDTO комментарий, который нужно создать
     * @param eventId id мероприятия
     * @return созданный комментарий
     */
    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO, long eventId) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        Optional<User> user = Optional.ofNullable(userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        Optional<Events> event = Optional.ofNullable(eventsRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        comment.setUserId(commentDTO.getUserId());
        comment.setUsername(user.get().getName());
        comment.setEvents(event.get());
        comment.setCreatedTime(new Date());
        return modelMapper.map(commentRepository.save(comment), CommentDTO.class);
    }

    /**
     * Обноляет комментарий мероприятия
     * @param commentId id комментарий
     * @param eventId id ероприятия
     * @param commentDetails комментарий со значениями, на которые нужно поменять
     * @return обноленный комментарий
     */
    @Transactional
    public CommentDTO updateComment(long commentId, long eventId, CommentDTO commentDetails) {
        Events event = eventsRepository.findById(eventId).get();
        Optional<Comment> optionalComment = Optional.ofNullable(commentRepository.findByIdAndEvents(commentId, event)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        Comment comment = optionalComment.get();
        comment.setText(commentDetails.getText());
        comment.setCreatedTime(new Date());
        return modelMapper.map(commentRepository.save(comment), CommentDTO.class);
    }

    /**
     * Удаляет комментарий мероприятия
     * @param commentId id комментария
     * @param eventId id мероприятия
     */
    @Transactional
    public void deleteComment(long commentId, long eventId) {
        Events event = eventsRepository.findById(eventId).get();
        Optional<Comment> optionalComment = Optional.ofNullable(commentRepository.findByIdAndEvents(commentId, event)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        commentRepository.deleteById(commentId);
    }
}
