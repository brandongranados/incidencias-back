package com.cic.incidencias.configSpringBoot;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cic.incidencias.datosAjax.Login;
import com.cic.incidencias.llaves.*;

public class AutenticacionPlataforma extends UsernamePasswordAuthenticationFilter{
    private AuthenticationManager manager;
    private Gson obj;

    public AutenticacionPlataforma(AuthenticationManager manager)
    {
        this.manager = manager;
        obj = new Gson();
    }
    protected void successfulAuthentication(HttpServletRequest request, 
                                            HttpServletResponse response, 
                                            FilterChain chain,
                                            Authentication authResult) 
    {

        String usuario = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername();

        try {
            Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
            Claims claim = Jwts.claims();
            
            String token = null;
            HashMap<String, String> resp = new HashMap<String, String>();

            claim.put("permisos", obj.toJson(roles));
            token = Jwts.builder()
                        .setClaims(claim)
                        .setSubject(usuario)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                        .signWith(General.LLAVE_SECRETA)
                        .compact();
                        
            resp.put("token", token);
            resp.put("usuario", usuario);
            resp.put("rol", obj.toJson(roles));

            response.getWriter().write(obj.toJson(resp));
            response.addHeader("Authorization", "Bearer "+token);
            response.setStatus(200);
            response.setContentType("application/json");
        } catch (Exception e) {
            Map<String, String> resp = new HashMap<>();
            resp.put("ERROR", "Fallo la autenticacion intentalo de nuevo"); 
            response.setStatus(401);
            response.setContentType("application/json");
        }

    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, 
                                                HttpServletResponse response,
                                                AuthenticationException failed) 
    { 
        try {
            response.setStatus(401);
            response.setContentType("application/json");
        } catch (Exception e) {
            response.setStatus(401);
            response.setContentType("application/json");
        }
    }
    
    public Authentication attemptAuthentication(HttpServletRequest request, 
                                                HttpServletResponse response)
    {
        Login us = null;
        UsernamePasswordAuthenticationToken respFiltro = null;

        try {
            us = new ObjectMapper().
                        readValue(request.getInputStream(), 
                        Login.class);
            respFiltro = new UsernamePasswordAuthenticationToken(us.getUsuario(), 
                                                                us.getContrasena());
        } catch (Exception e) {
            unsuccessfulAuthentication(request, response, null);
            return null;
        }
        return manager.authenticate(respFiltro);
    } 
}
