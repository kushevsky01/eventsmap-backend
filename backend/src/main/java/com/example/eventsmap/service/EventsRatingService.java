package com.example.eventsmap.service;

import com.example.eventsmap.dto.EventsRatingDTO;
import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.EventsRating;
import com.example.eventsmap.model.User;
import com.example.eventsmap.repository.EventsRatingAvg;
import com.example.eventsmap.repository.EventsRatingRepository;
import com.example.eventsmap.repository.EventsRepository;
import com.example.eventsmap.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class EventsRatingService {

    /**
     * Репозиторий мероприятий
     */
    private final EventsRepository eventsRepository;


    private final UserRepository userRepository;

    /**
     * Маппер для конвертирования сущности
     */
    private final ModelMapper modelMapper;

    private final EventsRatingRepository eventsRatingRepository;

    public EventsRatingService(EventsRepository eventsRepository, UserRepository userRepository, ModelMapper modelMapper, EventsRatingRepository eventsRatingRepository) {
        this.eventsRepository = eventsRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.eventsRatingRepository = eventsRatingRepository;
    }


    @Transactional
    public void setRating(EventsRatingDTO rating) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        Optional<Events> optionalEvent = Optional.ofNullable(eventsRepository.findById(rating.getEventsId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        EventsRating eventsRating = modelMapper.map(rating, EventsRating.class);

        List<EventsRating> eventsRatingList = user.get().getRatingList();
        System.out.println(eventsRatingList);
        System.out.println(optionalEvent);
        System.out.println(eventsRepository.findAll());
//        boolean userB = user.get().getId().equals(rating.getUserId());

//        if (!eventsRatingList.contains(eventsRating) ) {
            eventsRatingRepository.save(eventsRating);
//        }
    }

    public List<EventsRating> getRatingList(Long eventsId) {

        Optional<Events> optionalEvent = Optional.ofNullable(eventsRepository.findById(eventsId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        List<EventsRating> events = optionalEvent.get().getRatingList();

        return events;
    }

    @Transactional
    public void updateAllRatings(List<EventsRatingAvg> events){
        for(EventsRatingAvg event: events){
            Optional<Events> optionalEvent =  eventsRepository.findById(event.getId());
            optionalEvent.get().setRating(event.getRating());
            eventsRepository.save(optionalEvent.get());
        }
    }

}
