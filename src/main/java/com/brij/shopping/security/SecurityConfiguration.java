package com.brij.shopping.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user1").password("user1").roles("USER")
//                .and()
//                .withUser("user2").password("user2").roles("USER")
//                .and()
//                .withUser("admin").password("admin").roles("ADMIN")
//                .and()
//                .;
//    }

//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
////                .authorizeRequests()
////                .antMatchers("/carts").hasRole("ADMIN")
////                .and()
//                .formLogin()
//               // .loginPage("/login")
//
//                .permitAll() ;
//    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

    }

     @Bean
    public UserDetailsService userDetailsService()  {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user1").password("user1").roles("USER").build());
        manager.createUser(users.username("user2").password("user2").roles("USER").build());
        manager.createUser(users.username("admin").password("admin").roles("ADMIN").build());
         return manager;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////                .authorizeRequests().antMatchers("/carts").hasRole("ADMIN")
////                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .formLogin();
//    }

}