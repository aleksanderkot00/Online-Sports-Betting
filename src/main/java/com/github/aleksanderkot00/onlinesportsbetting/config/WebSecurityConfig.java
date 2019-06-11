package com.github.aleksanderkot00.onlinesportsbetting.config;

import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers( "/","/home").permitAll()
                .antMatchers( HttpMethod.GET,"/events/**", "/bets/**").permitAll()
                .antMatchers( HttpMethod.POST,"/users").permitAll()
                .antMatchers( "/users/*").hasAnyRole("USER","ADMIN")
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder makePasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
