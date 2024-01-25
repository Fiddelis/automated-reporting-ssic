package br.com.inatel.auditoria.informacao;

import br.com.inatel.auditoria.utils.Regex;

import java.util.regex.Pattern;

public class LatLong extends Regex {
    Pattern pattern = Pattern.compile("(EUtranCell.*DD=\\w{6} -?\\d+ -?\\d+\\n)+");

    public String info(String logFile) {
        StringBuilder resultadoLatLong = new StringBuilder();

        String[] latLong = procurarPorInformacao(logFile, pattern)
                .replaceAll("EUtranCell.*DD=\\w+ +", "")
                .split("\\n");

        resultadoLatLong.append(latLong[0]);

        for(int i = 1; i < latLong.length; i++) {
            if(!latLong[0].equals(latLong[i])) {
                resultadoLatLong.append(latLong[i]).append(" ");
                System.out.println(latLong[i]);
            }
        }

        System.out.println("LatLong: OK");

        return resultadoLatLong.toString();
    }
}
