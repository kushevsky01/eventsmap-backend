package com.example.eventsmap.service;

import com.example.eventsmap.Exceptions.ErrorResponse;
import com.example.eventsmap.dto.UserDTO;
import com.example.eventsmap.model.User;
import com.example.eventsmap.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервис с операциями над сущностью User
 */
@Service
public class UserService {

    /**
     * Репозиторий пользователей
     */
    private final UserRepository userRepository;

    /**
     * Маппер для конвертирования сущности
     */
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Инициализация полей класса
     */
    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Ищет всех пользователей
     * @return найденных пользователей
     */
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Ищет пользователя по id
     * @param id id пользователя
     * @return найденного пользователя
     */
    public UserDTO getUserById(long id) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Создает пользователя
     * @param userDTO пользователь, которого нужно создать
     * @return созданного пользователя
     */
    @Transactional
    public UserDTO createUser(UserDTO userDTO){
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("ROLE_USER");
        user.setActive(true);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    /**
     * Обновляет пользователя
     * @param id id пользователя
     * @param userDetails пользователь со значениями,на которые нужно поменять
     * @return измененный пользователь
     */
    @Transactional
    public UserDTO updateUser(long id, UserDTO userDetails) {

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        User user = optionalUser.get();
//        user.setUsername(userDetails.getUsername());
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    /**
     * Удаляет пользователя
     * @param id id пользователя
     */
    @Transactional
    public void deleteUser(long id) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        userRepository.deleteById(id);
    }

    public UserDTO getByUserName(String userName) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
        return modelMapper.map(user, UserDTO.class);
    }

    public void updatePassword(String newPassword) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
