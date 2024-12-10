package com.ns.solve.Config;

import com.ns.solve.Repository.MembershipRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/problem/**").hasRole("MANAGER")
                        .requestMatchers("/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }


    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public UserDetailsService dbUserDetailsService(MembershipRepository membershipRepository) {
        return account -> membershipRepository.findByAccount(account)
                .map(member -> User.withUsername(member.getName())
                        .password(member.getPassword())
                        .roles(member.getRoleName())
                        .build()
                ).orElseThrow(() -> new UsernameNotFoundException("Account not found: " + account));
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService inMemoryUserDetailsService,  UserDetailsService dbUserDetailsService, PasswordEncoder passwordEncoder) {
        // 다중 인증 처리를 위한 AuthenticationManager

        DaoAuthenticationProvider inMemoryAuthProvider = new DaoAuthenticationProvider();
        inMemoryAuthProvider.setUserDetailsService(inMemoryUserDetailsService);
        inMemoryAuthProvider.setPasswordEncoder(passwordEncoder);

        DaoAuthenticationProvider dbAuthProvider = new DaoAuthenticationProvider();
        dbAuthProvider.setUserDetailsService(dbUserDetailsService);
        dbAuthProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager authManager = new ProviderManager(inMemoryAuthProvider, dbAuthProvider);
        return authManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}




