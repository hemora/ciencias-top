package com.fciencias.cienciastop.models.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fciencias.cienciastop.models.dao.IUsuarioDao;
import com.fciencias.cienciastop.models.entity.JwtRequest;
import com.fciencias.cienciastop.models.entity.JwtResponse;
import com.fciencias.cienciastop.models.entity.Usuario;
import com.fciencias.cienciastop.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();

        authenticate(userName, userPassword);

        final UserDetails userDetails = loadUserByUsername(userName);

        String newGeneratedToken = jwtUtil.generateToken(userDetails);

        Usuario usuario = usuarioDao.encontrarPorNoCT(Long.parseLong(userName));

        return new JwtResponse(usuario, newGeneratedToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.encontrarPorNoCT(Long.parseLong(username));

        if (usuario != null) {
            return new User(
                Long.toString(usuario.getNoCT()) ,
                usuario.getContrasenya(),
                getAuthoriities(usuario)
            );
        } else {
            throw new UsernameNotFoundException("Número Cuenta inválido");
        }
    }

    private Set<SimpleGrantedAuthority> getAuthoriities(Usuario usuario) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + usuario.getRol()));
        return authorities;
    }
    
    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch(DisabledException e) {
            throw new Exception("Usuario no habilitado.");
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales erróneas");
        }
    }
}
