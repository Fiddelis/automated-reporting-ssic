package br.com.inatel.auditoria.informacao;

import br.com.inatel.utils.Regex;
import java.util.regex.Pattern;

public class LatLong extends Regex {
    Pattern pattern = Pattern.compile("(EUtranCell.*DD=\\w{6} -?\\d+ +-?\\d+\\n)+");

    public String info(String logFile) {
        StringBuilder resultadoLatLong = new StringBuilder();
        String[] latLongAlterar;
        String[] latLong = procurarPorInformacao(logFile, pattern)
                .replaceAll("EUtranCell.*DD=\\w+ +", "")
                .split("\\n");

        if(latLong[0].equals("INFORMAÇAO_NAO_ENCONTRADA")) {
            System.out.println("LatLong: INFORMAÇAO_NAO_ENCONTRADA");
            return "INFORMAÇAO_NAO_ENCONTRADA";
        }

        latLongAlterar = latLong[0].split(" +");

        resultadoLatLong.append(Double.parseDouble(latLongAlterar[0])/1000000).append("/");
        resultadoLatLong.append(Double.parseDouble(latLongAlterar[1])/1000000);

        System.out.println("LatLong: OK");

        return resultadoLatLong.toString();
    }
}
