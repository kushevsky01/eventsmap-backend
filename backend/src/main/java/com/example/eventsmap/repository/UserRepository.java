package com.example.eventsmap.repository;

import com.example.eventsmap.model.Events;
import com.example.eventsmap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Ищет пользователя по имени
     * @param name имя пользователя
     * @return найденный пользователь
     */
   Optional<User> findByName(String name);

    /**
     * Ищет пользователя по логину
     * @param username логин пользователя
     * @return найденный пользователь
     */
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    /**
     * Ищет пользователя по email
     * @param email имя пользователя
     * @return найденный пользователь
     */
    Optional<User> findByEmail(String email);

    List<User> findByUserEventsContains(Events event);

}
