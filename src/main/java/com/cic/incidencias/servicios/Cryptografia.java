package com.cic.incidencias.servicios;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cic.incidencias.configSpringBoot.Configuracion;

@Service
public class Cryptografia {
    
    @Autowired
    private Configuracion bouncy;
    
    public String crearSHA512(String texto)
    {
        String salida = "";
        try {
            MessageDigest digesto = MessageDigest.getInstance("SHA-512", bouncy.bouncyCastleProvider());
            byte[] hashBytes = digesto.digest(texto.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashHex = new StringBuilder();
            for( byte b : hashBytes )
                hashHex.append(String.format("%02x", b));
            
            salida = hashHex.toString();
        } catch (Exception e) {
            e.printStackTrace();
            salida = "";
        }

        return salida;
    }
}
