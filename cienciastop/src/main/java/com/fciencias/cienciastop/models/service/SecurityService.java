package com.fciencias.cienciastop.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fciencias.cienciastop.models.entity.Usuario;

@Service("securityService")
public class SecurityService {

    @Autowired
    private IUsuarioService userService;

    // Logger logger = LoggerFactory.getLogger(SecurityService.class);
    Authentication authentication;

    public boolean hasUser(Long id) {

        Usuario user = this.userService.buscarUsuarioPorNoCT(id);
        this.authentication = SecurityContextHolder.getContext().getAuthentication();

        return Long.parseLong(authentication.getName()) == user.getNoCT();
    }
}