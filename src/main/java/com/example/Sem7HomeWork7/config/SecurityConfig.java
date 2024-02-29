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
                .password("password1")
                .roles("USER")
                .build());
//        InMemoryUserDetailsManager manager2 = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("password2")
                .roles("ADMIN")
                .build());
        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
               .antMatchers("/private-data").hasAnyRole("ADMIN")
               .antMatchers("/public-data").authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/login");

//                .antMatchers("/private-data").hasAnyRole("ADMIN")
//                .antMatchers("/public-data").hasAnyRole("USER")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin(login -> login
//                        .defaultSuccessUrl("/")
//                        .permitAll())
//                .logout(logout -> logout
//                        .logoutSuccessUrl("/"));
   }
}
