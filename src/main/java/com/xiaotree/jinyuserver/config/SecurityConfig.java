package com.xiaotree.jinyuserver.config;


import com.xiaotree.jinyuserver.handler.*;
import com.xiaotree.jinyuserver.service.impl.AutoUserDetailsService;
import com.xiaotree.jinyuserver.util.TokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig {
    private final AutoUserDetailsService autoUserDetailsService;
    private final TokenUtil tokenUtil;

    public SecurityConfig(AutoUserDetailsService autoUserDetailsService, TokenUtil tokenUtil) {
        this.autoUserDetailsService = autoUserDetailsService;
        this.tokenUtil = tokenUtil;
    }

    /**
     * Security filter chain: [
     * DisableEncodeUrlFilter
     * WebAsyncManagerIntegrationFilter
     * SecurityContextHolderFilter
     * HeaderWriterFilter
     * CorsFilter
     * LogoutFilter
     * UsernamePasswordAuthenticationFilter
     * ConcurrentSessionFilter
     * RequestCacheAwareFilter
     * SecurityContextHolderAwareRequestFilter
     * RememberMeAuthenticationFilter
     * SessionManagementFilter
     * ExceptionTranslationFilter
     * AuthorizationFilter
     * ]
     */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(cors->cors.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/resources/**", "/signup", "/common/**").permitAll()
                        .requestMatchers("/users/*").hasAnyRole("admin", "user")
                        .requestMatchers("/system/*").hasRole("admin")
                        .anyRequest().authenticated()
                )
                .authenticationProvider(addAuthenticationProvider())
                .sessionManagement(session -> session.maximumSessions(3))
                .requestCache(cache -> cache.requestCache(new NullRequestCache()))
                .anonymous(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .rememberMe(me -> me.rememberMeServices(autoUserDetailsService))
                .formLogin(login -> login
                        .successHandler(new LoginSuccessHandlerImp())
                        .failureHandler(new LoginFailureHandler()))
                .logout(logout -> logout.logoutSuccessHandler(new LogoutSuccessHandlerImp()))
                .exceptionHandling(ex -> ex.accessDeniedHandler(new MyAccessDeniedHandler())
                                           .authenticationEntryPoint(new AuthenticationEntryPointImpl()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    public JwtAuthenticationProvider addAuthenticationProvider() {
        return new JwtAuthenticationProvider(tokenUtil);
    }
}
