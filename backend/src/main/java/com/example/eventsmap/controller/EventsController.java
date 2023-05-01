package com.example.eventsmap.controller;

import com.example.eventsmap.dto.EventsDTO;
import com.example.eventsmap.dto.UserDTO;
import com.example.eventsmap.model.User;
import com.example.eventsmap.service.EventsService;
import com.example.eventsmap.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 *  Rest контроллер для операций над сущностью Events
 */
@RestController
@RequestMapping("/events")
public class EventsController {

    /**
     * Сервис с операциями над сущностью Events
     */
    private final EventsService eventsService;

    private final UserService userService;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public EventsController(EventsService eventsService, UserService userService) {
        this.eventsService = eventsService;
        this.userService = userService;
    }

    /**
     * Запрос на поиск всех мероприятий
     * @return все мероприятия
     */
    @GetMapping
    public List<EventsDTO> getAllEvents() {
        return eventsService.getAllEvents();
    }

    @GetMapping("/userevents")
    public List<EventsDTO> getUserEvents(){
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userService.getByUserName(currentUserName);
        return eventsService.getUserEvents(user);
    }

    /**
     * Запрос на поиск мероприятия по id
     * @param eventId id мероприятия
     * @return найденное мероприятие в формате JSON
     */
    @GetMapping("/{eventId}")
    public ResponseEntity<EventsDTO> getEventById(@PathVariable(name = "eventId") long eventId){
        EventsDTO event = eventsService.getEventById(eventId);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    /**
     * Запрос на создание мероприятия
     * @parm eventDTO тело запроса
     * @return созданное мероприятие в формате JSON
     */
    @PostMapping
    public ResponseEntity<EventsDTO> createEvent(@RequestBody EventsDTO eventDTO){
        EventsDTO event = eventsService.createEvent(eventDTO);
        System.out.println(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    /**
     * Запрос на обноление данных мероприятия
     * @param eventId id пероприятия
     * @param eventDTO тело запроса
     * @return обноленное мероприятие в формате JSON
     */
    @PutMapping("/{eventId}")
    public ResponseEntity<EventsDTO> updateEvent(@PathVariable(name = "eventId") long eventId, @RequestBody EventsDTO eventDTO){
        EventsDTO event = eventsService.updateEvent(eventId, eventDTO);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    /**
     * Запрос на удаление мероприятия
     * @param eventId id мероприятия
     * @return статус запроса
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<HttpStatus> deleteEvent(@PathVariable(name = "eventId") long eventId){
        eventsService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{latitude}/{longitude}")
    public List<EventsDTO> getEventsByCoordinates(@PathVariable(name = "latitude") double latitude, @PathVariable(name = "longitude") double longitude){
        return eventsService.getEventsByCoordinates(latitude, longitude);
    }

    @GetMapping("/favourite")
    public List<EventsDTO> getFavoutiteEvents(){
        List<EventsDTO> events = eventsService.getFavouriteEvents();
        return events;
    }

    @PostMapping("/favourite/{eventId}")
    public ResponseEntity<EventsDTO> setFavouriteEvent(@PathVariable(name="eventId") long eventId){
        eventsService.setFavouriteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/favourite/{eventId}")
    public ResponseEntity<EventsDTO> deleteFavouriteEvent(@PathVariable(name="eventId") long eventId){
        eventsService.deleteFavouriteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search/{subString}")
    public List<EventsDTO> getSearchedEvents(@PathVariable(name = "subString") String subString ){

        return eventsService.getSearchedEvents(subString);
    }

}
