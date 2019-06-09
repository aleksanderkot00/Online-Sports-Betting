package com.github.aleksanderkot00.onlinesportsbetting.config;

import com.github.aleksanderkot00.onlinesportsbetting.repository.UserRepository;
import com.github.aleksanderkot00.onlinesportsbetting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers( "/bets", "/users", "/h2-console/**").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        // Database authentication
////        auth.
////                jdbcAuthentication()
////                .usersByUsernameQuery(usersQuery)
////                .authoritiesByUsernameQuery(rolesQuery)
////                .dataSource(dataSource)
////                .passwordEncoder(passwordEncoder());
//
//        // In memory authentication
//        auth.
//                inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser(adminUsername).password(adminPassword).roles("ADMIN");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
