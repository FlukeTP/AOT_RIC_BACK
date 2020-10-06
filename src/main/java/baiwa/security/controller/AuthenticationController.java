package baiwa.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import baiwa.security.config.TokenProvider;
import baiwa.security.domain.AuthToken;
import baiwa.security.domain.LoginUser;
 

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
    	System.out.println(" In  AuthenticationController.generate-token user:"+loginUser.getUsername()+":"+ bcryptEncoder.encode(loginUser.getPassword()));
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUsername(),
                         loginUser.getPassword() 
                )
        );
        
     
        SecurityContextHolder.getContext().setAuthentication(authentication);
       // final String token = jwtTokenUtil.generateToken(authentication);
        AuthToken returnToken = jwtTokenUtil.generateToken2(authentication);
        System.out.println(" In  AuthenticationController.generate-token token:"+returnToken.getToken());
        return ResponseEntity.ok(returnToken);
    }

}
