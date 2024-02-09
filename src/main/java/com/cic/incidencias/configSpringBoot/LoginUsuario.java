package com.cic.incidencias.configSpringBoot;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cic.incidencias.datos.RepoRolImp;
import com.cic.incidencias.datos.usuarioBD;


@Service
public class LoginUsuario implements UserDetailsService {
    @Autowired
    private RepoRolImp rol;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {

        Optional<usuarioBD> usuarioLogin = rol.usuarioFindNombre(usuario);
        ArrayList<GrantedAuthority> aut =new ArrayList<>();
        usuarioBD usBD = null;

        if( !usuarioLogin.isPresent() )
            return null;
        else
            usBD = usuarioLogin.get();

        aut.add(new SimpleGrantedAuthority(usBD.getNombreRol()));

        return new User(usuario, usBD.getContrasena(), 
        true, true, true,
         true, aut);
    }
    
}
