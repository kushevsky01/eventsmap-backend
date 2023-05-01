package com.example.eventsmap.controller;

import com.example.eventsmap.dto.UserDTO;
import com.example.eventsmap.service.PersonDetailsService;
import com.example.eventsmap.service.UserService;
import com.example.eventsmap.Exceptions.ErrorResponse;
import com.example.eventsmap.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
public class LoginAndRegistrationController {

    /**
     * Валидатор, который проверяет введенные поля пользоватлем
     */
    private final UserValidator userValidator;

    /**
     * Маппер для конвертации сущностей
     */
    private final ModelMapper modelMapper;

    /**
     * Сервис с логикой CRUD операций над сущностью Person
     */
    private final UserService userService;

    /**
     * Сервис для регистрации пользователя
     */

    /**
     * Сервис для Spring Security, загружает пользователя
     */
    private final PersonDetailsService personDetailsService;



    /**
     * Инициализация полей класса
     */
    @Autowired
    public LoginAndRegistrationController(UserValidator userValidator, ModelMapper modelMapper, UserService userService, PersonDetailsService personDetailsService) {
        this.userValidator = userValidator;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.personDetailsService = personDetailsService;
    }

    /**
     * Метод для авторизации пользователя
     * @param userDTO, логин или mail и пароль пользователя
     * @return если пользователь успешно авторизирован статус 200, в противном случае 403
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginPage(@RequestBody UserDTO userDTO){
        userValidator.checkPassword(userDTO.getUsername(), userDTO.getPassword());
        UserDetails userDetails = personDetailsService.loadUserByUsername(userDTO.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) ;
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userService.getByUserName(currentUserName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Метод для получения авторизированного пользователя
     * @return авторизированного пользователя
     */
    @GetMapping("/whoami")
    public ResponseEntity<UserDTO> whoAmI() {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDTO user = userService.getByUserName(currentUserName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Метод, отвечающий за регистрацию нового пользователя
     * @param userDTO сущности Person для запроса
     * @return DTO сущности Person для ответа с кодом 201
     */
    @PostMapping("/registration")
    public ResponseEntity<Object> performRegistration(@RequestBody @Valid UserDTO userDTO) {

        List<ErrorResponse> errors = userValidator.checkUserNameAndEmail(userDTO.getUsername(), userDTO.getEmail());

        if (!errors.isEmpty()){
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        UserDTO user = userService.createUser(userDTO);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Метод, отвечающий за выход пользователя
     */
    @PostMapping ("/logout")
    public void logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}
