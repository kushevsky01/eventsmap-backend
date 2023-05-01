package com.example.eventsmap.controller;

import com.example.eventsmap.dto.EventsDTO;
import com.example.eventsmap.dto.EventsRatingDTO;
import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.EventsRating;
import com.example.eventsmap.repository.EventsRepository;
import com.example.eventsmap.service.EventsRatingService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class EventsRatingController {

    private final EventsRatingService eventsRatingService;
    /**
     * Маппер для конвертирования сущности
     */
    private final ModelMapper modelMapper;

    private final EventsRepository eventsRepository;

    public EventsRatingController(EventsRatingService eventsRatingService, ModelMapper modelMapper, EventsRepository eventsRepository) {
        this.eventsRatingService = eventsRatingService;
        this.modelMapper = modelMapper;
        this.eventsRepository = eventsRepository;
    }

    @PostMapping
    public ResponseEntity<Object> setRating(@RequestBody EventsRatingDTO rating){
        eventsRatingService.setRating(rating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{eventsId}")
    public ResponseEntity<Object> getRating(@PathVariable(name = "eventsId") Long eventsId){
        List<EventsRating> events = eventsRatingService.getRatingList(eventsId);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

//    @PutMapping
//    public ResponseEntity<Object> updateUserEventRating(@RequestBody EventsRatingDTO rating){
//        eventsRatingService.updateRating(rating);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
