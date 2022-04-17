package com.harsha.spring.config;

import com.harsha.spring.filter.jwtFilter;
import com.harsha.spring.services.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private MyUserDetailsService userDetailsService;
    
    @Autowired
    private jwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/authenticate").permitAll()
        .antMatchers("/register/*").permitAll()
        .antMatchers("/forgotpassword").permitAll()
        .antMatchers("/verifyotp").permitAll()
        .antMatchers("/verify").permitAll()
        .antMatchers("/resetpassword").permitAll()
        .antMatchers("/generateotp").permitAll()
        .antMatchers("/createexam/*").hasAuthority("ORG")
        .antMatchers("/createexam/{\\d+}/{\\d+}/postquestions").hasAuthority("ORG")
        .antMatchers("/applyforexam/{\\d+}/{\\d+}/{\\d+}").hasAuthority("CANDIDATE")
        .antMatchers("/test/{\\d+}/{\\d+}/{\\d+}").hasAuthority("CANDIDATE")
        .antMatchers("/test/{\\d+}/{\\d+}/{\\d+}/submit").hasAuthority("CANDIDATE")
        .antMatchers("/trigger/{\\d+}").hasAuthority("ORG")
        .antMatchers("/{\\d+}/filter").hasAuthority("ORG")
        .antMatchers("/{\\d+}/{\\d+}/filter/trigger").hasAuthority("ORG")
        .antMatchers("/getexamdetails/{\\d+}").hasAuthority("ORG")
        .antMatchers("/getexamdetails/{\\d+}/{\\d+}").hasAuthority("ORG")
        .antMatchers("/gettopicdetails/{\\d+}").hasAuthority("ORG")
        .antMatchers("/gethomedetails/{\\d+}").hasAuthority("ORG")
        .antMatchers("/getresults/{\\d+}").hasAuthority("ORG")
        .antMatchers("/examdetails/{\\d+}").hasAnyAuthority("ORG", "CANDIDATE")
        .antMatchers("/getexamsapplieddetails").hasAuthority("CANDIDATE")
        .antMatchers("/getcurrentexamdetails").hasAuthority("CANDIDATE")
        .antMatchers("/getresults").hasAuthority("CANDIDATE")
        .antMatchers("/update").hasAuthority("CANDIDATE")
        .antMatchers("/changePassword").hasAnyAuthority("ORG", "CANDIDATE")
        .antMatchers("/getalldetails").hasAuthority("ADMIN")
        .antMatchers("/grantorrevokeaccess").hasAuthority("ADMIN")
        .antMatchers("/deleteuser").hasAuthority("ADMIN")
        .anyRequest().authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception{
		return super.authenticationManagerBean();
	}

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
