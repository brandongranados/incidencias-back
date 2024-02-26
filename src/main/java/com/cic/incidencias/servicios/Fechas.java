package com.cic.incidencias.servicios;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    public Map<String, Object> getExtraeFechasFromIniFin(String fechini, String fechfin) throws Exception
    {
        Map<String, Object> resp = new HashMap<String, Object>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate inicio = LocalDate.parse(fechini, formatter);
        LocalDate fin = LocalDate.parse(fechfin, formatter);

        fin = fin.plusDays(1);

        int contador = 1;
        LocalDate fechaActual = inicio;
    
        while (!fechaActual.isAfter(fin)) 
        {
            String temp = fechaActual.format(formatter);

            resp.put(""+contador, this.getDiaMesAno(temp, false));
            fechaActual = fechaActual.plusDays(1);
            contador++;
        }

        return resp;
    }

    public Map<String, String> getDiaMesAno(String fecha, boolean bool) throws Exception
    {
        Map<String, String> sal = new HashMap<String, String>();

        if( bool )
        {
            ZoneId zonaHorariaCDMX = ZoneId.of("America/Mexico_City");
            ZonedDateTime fechaDeHoyCDMX = ZonedDateTime.now(zonaHorariaCDMX);
            LocalDate fechaDeHoy = fechaDeHoyCDMX.toLocalDate();
            DateTimeFormatter formatoMes = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "ES"));

            sal.put("dia", String.format("%02d", fechaDeHoy.getDayOfMonth()));
            sal.put("mes", fechaDeHoy.format(formatoMes));
            sal.put("ano", String.valueOf(fechaDeHoy.getYear()));
        }
        else
        {
            String descompone[] = fecha.split("/");

            sal.put("dia", descompone[0]);
            sal.put("mes", descompone[1]);
            sal.put("ano", descompone[2]);
        }

        return sal;
    }
}
