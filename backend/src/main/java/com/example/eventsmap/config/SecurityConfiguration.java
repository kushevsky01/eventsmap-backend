package com.example.eventsmap.config;

import com.example.eventsmap.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {


    /**
     * Сервис загрузки пользователя
     */
    private final PersonDetailsService personDetailsService;


    /**
     * Инициализация поля
     */
    @Autowired
    public SecurityConfiguration(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    /**
     * Данный метод выполняет следующие функции
     * Настройка аутентификации
     * Настройка авторизации
     * Настройка формы для входа
     * Удаляет сессии и куки
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(personDetailsService).passwordEncoder(passwordEncoder());

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
                .antMatchers("/user/**").hasAnyRole("ROLE_USER")
                .antMatchers("/**").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/lgout")
                .logoutSuccessUrl("/");

        return http.build();
    }

    /**
     * Шифровальщик паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
