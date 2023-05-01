package com.example.eventsmap.service;

import com.example.eventsmap.model.User;
import com.example.eventsmap.repository.UserRepository;
import com.example.eventsmap.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
public class PersonDetailsService implements UserDetailsService {

    /**
     * Поиск в базе данных
     */
    private final UserRepository userRepository;

    /**
     * Инициализация поля
     */
    @Autowired
    public PersonDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загрузка пользователя
     * @param usernameOrEmail логин пользователя
     * @return объект UserDetails, который содержит некоторые методы для описания информации о пользователе
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail){
        Optional<User> user = Optional.ofNullable(userRepository.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail )
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден")));
        return new PersonDetails(user.get());
    }
}
