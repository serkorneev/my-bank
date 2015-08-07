package com.griddynamics.mybank.controller.api;

import com.griddynamics.mybank.controller.api.dto.LoginDto;
import com.griddynamics.mybank.controller.api.dto.TokenDto;
import com.griddynamics.mybank.security.rest.TokenUtils;
import com.griddynamics.mybank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergey Korneev
 */
@RestController
public class AuthenticationController {
    @Autowired
    private UserDetailsService userService;

    @Autowired
    private CardService service;

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    public TokenDto authenticate(@RequestBody LoginDto loginDto) {
        Authentication token = new UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password);
        Authentication authentication = this.authManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = this.userService.loadUserByUsername(loginDto.username);
        TokenDto dto = new TokenDto();
        dto.token = TokenUtils.createToken(userDetails);
        service.init();

        return dto;
    }
}
