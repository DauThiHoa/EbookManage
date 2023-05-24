package com.ManageBookStore.ManageBookStore.config;

import com.ManageBookStore.ManageBookStore.service.Impl.CustomerDetailsService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan("com.ManageBookStore.ManageBookStore.service")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerDetailsService customerDetailsService;

    public SecurityConfig(CustomerDetailsService customerDetails) {
        this.customerDetailsService = customerDetails;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/customer/changepassword").authenticated() // Trang yeu cau xac thuc
                .and()
                .formLogin()
//                .loginPage("/customer/login")
                .defaultSuccessUrl("/customer/changepassword")
                .and()
                .logout()
                .logoutSuccessUrl("/customer/logout") // customer/logout
                .and()
                .csrf().disable();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customerDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}