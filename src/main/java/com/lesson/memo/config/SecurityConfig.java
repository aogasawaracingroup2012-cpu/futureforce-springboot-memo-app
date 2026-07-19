package com.lesson.memo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.lesson.memo.security.AdminDetailService;

@Configuration
public class SecurityConfig {

	private final AdminDetailService adminDetailService;

	public SecurityConfig(AdminDetailService adminDetailService) {
		this.adminDetailService = adminDetailService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
	            .requestMatchers("/admin/signin", "/admin/signup").permitAll()
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/admin/signin")              // ログイン画面のURL
	            .loginProcessingUrl("/admin/signin")     // ログイン処理のURL（POST先）
	            .defaultSuccessUrl("/memo", true) // ログイン成功時の遷移先
	            .failureUrl("/admin/signin?error")       // ログイン失敗時
	            .usernameParameter("email")       // ログインIDは email
	            .passwordParameter("password")    // パスワード
	        )
	        .logout(logout -> logout
	            .logoutUrl("/admin/logout")
	            .logoutSuccessUrl("/admin/signin?logout")
	            .permitAll()
	        )
	        .userDetailsService(adminDetailService);

	    return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}