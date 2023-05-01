package com.example.eventsmap.security;

import com.example.eventsmap.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Обертка над сущностью, для работы не напрямую с Person
 * Стандартные методы
 */
public class PersonDetails implements UserDetails {

    /**
     * Данные пользователя
     */
    private final User user;

    /**
     * Инициализация поля
     */
    public PersonDetails(User user) {
        this.user = user;
    }

    /**
     * @return возвращает полномочия, предоставленные пользователю
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
    }

    /**
     * @return возвращает пароль, используемый для аутентификации пользователя
     */
    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * @return возвращает имя пользователя, используемое для аутентификации пользователя
     */
    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /**
     * Указывает, истек ли срок действия учетной записи пользователя. Учетная запись с истекшим сроком действия не может быть аутентифицирована.
     * @return учетная запись пользователя действительна
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Указывает, заблокирован или разблокирован пользователь. Заблокированный пользователь не может быть аутентифицирован.
     * @return пользователь не заблокирован
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Указывает, истек ли срок действия учетных данных пользователя
     * @return учетные данные пользователя действительны
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Указывает, включен или отключен пользователь
     * @return пользователь включен
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

