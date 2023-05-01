package com.example.eventsmap.service;

import com.example.eventsmap.dto.EventsDTO;
import com.example.eventsmap.dto.UserDTO;
import com.example.eventsmap.model.City;
import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.User;
import com.example.eventsmap.repository.CityRepository;
import com.example.eventsmap.repository.EventsRatingRepository;
import com.example.eventsmap.repository.EventsRepository;
import com.example.eventsmap.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Сервис с операциями над сущностью Events
 */
@Service
@Transactional(readOnly = true)
public class EventsService {

    /**
     * Репозиторий мероприятий
     */
    private final EventsRepository eventsRepository;

    private final EventsRatingRepository eventsRatingRepository;

    /**
     * Репозиторий городов
     */
    private final CityRepository cityRepository;

    private final UserRepository userRepository;

    /**
     * Маппер для конвертирования сущности
     */
    private final ModelMapper modelMapper;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public EventsService(EventsRepository eventsRepository, EventsRatingRepository eventsRatingRepository, CityRepository cityRepository, UserRepository userRepository, ModelMapper modelMapper1) {
        this.eventsRepository = eventsRepository;
        this.eventsRatingRepository = eventsRatingRepository;
        this.cityRepository = cityRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper1;
    }

    /**
     * Ищет ввсе мероприятия
     * @return найденные мероприятия
     */
    public List<EventsDTO> getAllEvents() {
        List<Events> events = eventsRepository.findAll();
        return events
                .stream()
                .map(event -> modelMapper.map(event, EventsDTO.class))
                .collect(Collectors.toList());
    }

    public List<EventsDTO> getUserEvents(UserDTO userDTO){

        User user = modelMapper.map(userDTO, User.class);

        List<Events> events = eventsRepository.findByUser(user);
        events.sort(new Comparator<Events>() {
            @Override
            public int compare(Events o1, Events o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return events
                .stream()
                .map(event -> modelMapper.map(event, EventsDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Ищет мероприятия по id
     * @param id id мероприятия
     * @return найденное меропритяие
     */
    public EventsDTO getEventById(long id) {
        Optional<Events> event = Optional.ofNullable(eventsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return modelMapper.map(event, EventsDTO.class);
    }

    /**
     * Создает мероприятия
     * @param eventDTO мероприятие, которое нужно создать
     * @return созданное мероприятие
     */
    @Transactional
    public EventsDTO createEvent(EventsDTO eventDTO) {

        Events event = modelMapper.map(eventDTO, Events.class);

        Optional<City> optionalCity = Optional.ofNullable(cityRepository.findByName(eventDTO.getCityName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        City city = optionalCity.get();
        event.setUser(user.get());
        event.setCity(city);
        event.setCreatedTime(new Date());
        event.setUpdateTime(new Date());
        return modelMapper.map(eventsRepository.save(event), EventsDTO.class);
    }

    /**
     * Обновляет меропритие
     * @param id id мероприятия
     * @param eventDetails мероприятие со значениями, на которые нужно поменят
     * @return обновленное мероприятие
     */
    @Transactional
    public EventsDTO updateEvent(long id, EventsDTO eventDetails) {
        Optional<Events> optionalEvent = Optional.ofNullable(eventsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        Events event = optionalEvent.get();
        event.setTitle(eventDetails.getTitle());
        event.setDate(eventDetails.getDate());
        event.setUpdateTime(new Date());
        event.setDescription(eventDetails.getDescription());
        return modelMapper.map(eventsRepository.save(event), EventsDTO.class);
    }

    /**
     * Удаляет мероприятие
     * @param id id мероприятия
     */
    @Transactional
    public void deleteEvent(long id) {

        Optional<Events> event = Optional.ofNullable(eventsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        event.get().removeUsers();

        eventsRatingRepository.deleteAllByEvents(event.get());

        eventsRepository.deleteById(id);
    }

    @Transactional
    public List<EventsDTO> getEventsByCoordinates(double latitude, double longitude){
        List<Events> events = eventsRepository.findByLatitudeAndLongitude(latitude, longitude);
        return events
                .stream()
                .map(event -> modelMapper.map(event, EventsDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void setFavouriteEvent(long eventId) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        Optional<Events> optionalEvent = Optional.ofNullable(eventsRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        List<Events> events = user.get().getUserEvents();

        if (!events.contains(optionalEvent.get())){
            user.get().addEvents(optionalEvent.get());
            userRepository.saveAndFlush(user.get());
        }
    }

    @Transactional
    public void deleteFavouriteEvent(long eventId) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        Optional<Events> optionalEvent = Optional.ofNullable(eventsRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        user.get().removeEvent(optionalEvent.get());
        userRepository.saveAndFlush(user.get());
    }

    @Transactional
    public List<EventsDTO> getFavouriteEvents() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));


        List<Events> events = user.get().getUserEvents();
        events.sort(new Comparator<Events>() {
            @Override
            public int compare(Events o1, Events o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return events
                .stream()
                .map(event -> modelMapper.map(event, EventsDTO.class))
                .collect(Collectors.toList());
  }

    public List<EventsDTO> getSearchedEvents(String subString) {
        List<Events> events = eventsRepository.findByTitleContaining(subString);

        events.sort(new Comparator<Events>() {
            @Override
            public int compare(Events o1, Events o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return events
                .stream()
                .map(event -> modelMapper.map(event, EventsDTO.class))
                .collect(Collectors.toList());
    }

}
