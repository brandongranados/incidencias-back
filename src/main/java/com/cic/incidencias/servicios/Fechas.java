package com.cic.incidencias.servicios;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

@Service
public class Fechas {

    public String getFechaCompletaCDMX()
    {
        
        LocalDate fechaLocalCDMX = LocalDate.now(ZoneId.of("America/Mexico_City"));

        return fechaLocalCDMX.format(DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy"));
    }

    public String getAnoCreacion()
    {
        return  String.valueOf(Year.now().getValue());
    }

    public String getFechaHoraUTC()
    {
        ZonedDateTime fechaHoraUTC = ZonedDateTime.now(java.time.ZoneOffset.UTC);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return fechaHoraUTC.format(formatter);
    }
}
