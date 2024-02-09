package com.cic.incidencias.servicios;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.cic.incidencias.datos.RepoDatUsuario;


import jakarta.mail.internet.MimeMessage;

@Service
public class EnviarCorreo {

    static int ESPERA_AUTORIZACION = 1;

    @Autowired
    private JavaMailSender correo;
    @Autowired
    private RepoDatUsuario datos;
    @Value("${correo.asunto}")
    private String asunto;
    @Value("${spring.mail.username}")
    private String desde;
    @Value("${correo.obligatorio}")
    private String obligatorioCorreo;
    @Value("${correo.html.AUTORIZAR}")
    private String recursoHTMLAutiriza;

    public void enviarCorreo(String usuario, String tipo, int msm)
    {
        List<Map<String, Object>> profLista = datos.datosUusario(usuario);
        Map<String, Object> prof = profLista.get(0);
        String correoE = (String)prof.get("correo_electronico");
        String cuerpo = this.setTipoMsm(msm);

        cuerpo = cuerpo.replaceAll("#tipo#", tipo);

        try {
            MimeMessage msmConstruido = correo.createMimeMessage();
            MimeMessageHelper help = new MimeMessageHelper(msmConstruido, true);

            help.setFrom(desde);
            help.setTo(correoE);
            help.setCc(obligatorioCorreo);
            help.setSubject(asunto);
            help.setText(cuerpo, true);

            correo.send(msmConstruido);
        } catch (Exception e) {}
    }

    private String setTipoMsm(int msm)
    {
        String salida = "";
        try {
            switch (msm) {
                case 3:
                    File archivo = new File(recursoHTMLAutiriza);
                    byte crudo[] = FileCopyUtils.copyToByteArray(archivo);
                    salida = new String(crudo, StandardCharsets.UTF_8);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {}
        return salida;
    }
}
