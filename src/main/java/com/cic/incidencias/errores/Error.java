package com.cic.incidencias.errores;

public class Error {
    private int codigo;

    public void setCodigo(int codigo){   this.codigo = codigo;   }

    public String getMsmCodigo()
    {
        String salida = "";

        switch (codigo) {
            case 2:
                salida = "La hora de inicio capturada  no puede ser menor a las 6 am.";
                break;
            case 3:
                salida = "La hora final capturada  no puede ser mayor a las 10 pm.";
                break;
            case 4:
                salida = "Ya cuenta con 2 incidencias capturadas en la quincena que está corriendo.";
                break;
            case 5:
                salida = "Solo se puede capturar incidencias de la quincena que está corriendo.";
                break;
            case 6:
                salida = "El día capturado no pertenece a un día con clases asignadas.";
                break;
            case 7:
                salida = "La incidencia que intenta capturar ya fue capturada";
                break;
            case 8:
                salida = "Cotejar con su horario que su incidencia no se traslape con más de un horario de clases que es lo permitido.";
                break;
            case 9:
                salida = "Las observaciones y la incidencia no coinciden";
                break;
            case 10:
                salida = "El horario en el que desea reponer horas ya fue capturado";
                break;
            case 11:
                salida = "Al menos una de las compensaciones capturada está errónea";
                break;
            case 12:
                salida = "El tiempo que debe cubrir es distinto al que cubrió";
                break;
            case 13:
                salida = "Solo se pueden solicitar 10 días económicos al año";
                break;
            case 14:
                salida = "Solo se pueden solicitar 3 días económicos consecutivos";
                break;
            case 15:
                salida = "Tipo de documento no permitido.";
                break;
            case 16:
                salida = "No fue posible procesar su documento Excel";
                break;
            default:
                salida = "Error interno contactar al jefe del departamento";
                break;
        }

        return salida;
    }
}
