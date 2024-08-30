package com.taskify.springboot.todoWebApp.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class JwtSecurityConfiguration {
	//LDAP or Database 
//	InMemoryUserDetailsManager
//	InMemoryUserDetailsManager(UserDetails... users)
	
//	@Bean 
//	public InMemoryUserDetailsManager createUserDetailsManager() {
//		UserDetails userDetails1 = createNewUser("name1", "dummy1");
//		UserDetails userDetails2 = createNewUser("name2", "dummy2"); 
//		return new InMemoryUserDetailsManager(userDetails1,userDetails2); 
//	}
//
//	private UserDetails createNewUser(String username, String password) {
//		Function<String, String> passwordEncoder = 
//				input -> passwordEncoder().encode(input);
//		UserDetails userDetails = User.builder()
//									.passwordEncoder(passwordEncoder)
//									.username(username)
//									.password(password)
//									.roles("USER","ADMIN")
//									.build();
//		return userDetails;
//	}
//	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(); 
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.csrf(AbstractHttpConfigurer :: disable) 
				.sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests( request -> { request.requestMatchers("/","/authenticate","/actuator","/actuator/*").permitAll();
										request.requestMatchers("/h2-console/**").permitAll(); 
										request.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest().authenticated();
									 })
				.oauth2ResourceServer( oauth2 ->  oauth2.jwt(Customizer.withDefaults()))
				.exceptionHandling( (ex) ->  ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()).accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
				.httpBasic(Customizer.withDefaults())
				.headers( header -> header.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
				.build();
	}
	
	@Bean 
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		var authenticationProvider = new DaoAuthenticationProvider(); 
		authenticationProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(authenticationProvider); 
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("manish").password("root").authorities("read").roles("USER").build(); 
		return new InMemoryUserDetailsManager(user); 
	}
	
	@Bean
	public JWKSource<SecurityContext> jwkSource(){
		JWKSet jwkSet = new JWKSet(rsaKey()); 
		return (((jwkSelector,SecurityContext) -> jwkSelector.select(jwkSet))); 
	}
	
	@Bean
	JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource); 
	}
	
	@Bean
	JwtDecoder jwtDecoder() throws JOSEException {
		return NimbusJwtDecoder.withPublicKey(rsaKey().toRSAPublicKey()).build();  
	}
	
	@Bean
	public RSAKey rsaKey() {
		KeyPair keyPair = keyPair(); 
		return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic()).privateKey((RSAPrivateKey) keyPair.getPrivate()).keyID(UUID.randomUUID().toString()).build(); 
	}
	
	@Bean
	public KeyPair keyPair() {
		try {
			var keyPairGenerator = KeyPairGenerator.getInstance("RSA"); 
			keyPairGenerator.initialize(2048);
			return keyPairGenerator.generateKeyPair(); 
		} catch (Exception e) {
			throw new IllegalStateException("Unable to generate an RSA Key Pair",e);
		}
	}
	
}
