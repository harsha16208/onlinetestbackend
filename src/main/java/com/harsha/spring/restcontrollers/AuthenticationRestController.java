package com.harsha.spring.restcontrollers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.harsha.spring.vo.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import com.harsha.spring.util.*;
import com.harsha.spring.services.*;


@RestController
public class AuthenticationRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtRequest)throws BadCredentialsException {
        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        }catch(Exception e){
            throw new BadCredentialsException("incorrect username and password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());

        String jwtToken = jwtUtil.generateToken(userDetails);
        
        SimpleGrantedAuthority adminAuthority = new SimpleGrantedAuthority("ADMIN");
        
        if (userDetails.getAuthorities().contains(adminAuthority)) {
			Map<String, Object> details = new HashMap<>();
			details.put("jwtToken", jwtToken);
			details.put("role", "ADMIN");
			details.put("username", userDetails.getUsername());
			return ResponseEntity.ok(details);
		}
        
        JwtResponse jwtResponse = authenticationService.getJwtResponse(jwtToken, userDetails);

        return ResponseEntity.ok(jwtResponse);
    }
}
