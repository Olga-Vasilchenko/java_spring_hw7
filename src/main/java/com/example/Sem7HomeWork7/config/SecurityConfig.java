package com.example.Sem7HomeWork7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SCryptPasswordEncoder sCryptPasswordEncoder(){
        return new SCryptPasswordEncoder();
    }

    /**
     * Бин UserDetailsService с добавленными пользователями
     * @return добавленного пользователя
     */
    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build());
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build());
        return manager;
    }

    /**
     * Конфигурация защиты Spring Security
     * @param http the {@Link HttpSecurity} to modify
     * @throws Exception исключение безопасности
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/private-data").hasAnyRole("ADMIN")
                .antMatchers("/public-data").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
//                .permitAll()
//                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/public-data")
//                .failureUrl("/login?error")
                .and()
                .logout()
                .logoutSuccessUrl("/login");
//                .and()
//                .exceptionHandling();
//                .accessDeniedPage("/access-denied");
    }
}

//                .antMatchers("/private-data").hasAnyRole("ADMIN")
//                .antMatchers("/public-data").hasAnyRole("USER")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin(login -> login
//                        .defaultSuccessUrl("/")
//                        .permitAll())
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/"));

