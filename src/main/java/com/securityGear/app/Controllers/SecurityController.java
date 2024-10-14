package com.securityGear.app.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/security")
public class SecurityController {

    @GetMapping("/securitySessionId")
    public String getHttpServletObject(HttpServletRequest request){
        return "this is your sessionId: " + request.getSession().getId();
    }


    @GetMapping("/getCsrfToken")
    public CsrfToken getCsrfToken(HttpServletRequest request){
//      get special attribute named = _csrf
        return (CsrfToken) request.getAttribute("_csrf");
    }





}
