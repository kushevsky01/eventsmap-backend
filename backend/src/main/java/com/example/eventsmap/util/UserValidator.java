package com.example.eventsmap.util;

import com.example.eventsmap.Exceptions.ErrorResponse;
import com.example.eventsmap.model.User;
import com.example.eventsmap.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserValidator implements Validator {
    /**
     * Поиск в базе данных
     */
    private final UserRepository userRepository;

    /**
     * Класс для шифрования пароля
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Инициализая поля
     */
    @Autowired
    public UserValidator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Проверка на поддержку класса валидатором
     *
     * @param clazz проверяемый класс
     * @return значение true, если он соответвсвует Person
     */
    @Override
    public boolean supports(Class<?> clazz) {

        System.out.println("hi");
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User person = (User) target;

        if (userRepository.findByUsername(person.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "Пользователь с таким логином зарегистрирован");
        }

        if (userRepository.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Пользователь с таким email зарегистрирован");
        }
    }

    public List<ErrorResponse> checkUserNameAndEmail(String username, String email) {
        List<ErrorResponse> errorsResponses = new ArrayList<>();
        if (userRepository.findByUsername(username).isPresent()) {
            ErrorResponse error = new ErrorResponse("username", "Пользователь с таким логином зарегистрирован");
            errorsResponses.add(error);
        }

        if (userRepository.findByEmail(email).isPresent()) {
            ErrorResponse error = new ErrorResponse("email", "Пользователь с таким email зарегистрирован");
            errorsResponses.add(error);
        }

        return errorsResponses;
    }


    public void checkPassword(String username, String password) {

        Optional<User> user = Optional.ofNullable(userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден")));
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неправельный пароль");
        }

    }

    public List<ErrorResponse> checkUserEmail(String email, long userId) {
        List<ErrorResponse> errorsResponses = new ArrayList<>();
        User userWithId = userRepository.findById(userId).get();
        Optional<User> userWithEmail = userRepository.findByEmail(email);
        if (userWithEmail.isPresent()) {
            if (!(userWithEmail.get().equals(userWithId))) {
                ErrorResponse error = new ErrorResponse("email", "Пользователь с таким email зарегистрирован");
                errorsResponses.add(error);
            }
        }

        return errorsResponses;
    }

    public List<ErrorResponse> checkPassword(String password) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ErrorResponse> errorsResponses = new ArrayList<>();

        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(currentUserName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            ErrorResponse error = new ErrorResponse("oldPassword", "Неправельный пароль");
            errorsResponses.add(error);
        }
        return errorsResponses;
    }

}
