package com.cic.incidencias.llaves;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class General {
    public final static Key LLAVE_SECRETA = Keys.secretKeyFor(SignatureAlgorithm.HS512);
}
