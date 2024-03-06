package br.com.inatel.auditoria.informacao;

import br.com.inatel.utils.Regex;
import java.util.regex.Pattern;

public class TotalPortadorasBandas extends Regex {
    Pattern pattern = Pattern.compile("earfcndl\\n=+\\n(EUtranCell.*DD=\\w{6} \\d+ *\\n)+");

    public String[] info(String arquivoLog) {
        int index;
        int total = 1;
        String[] resultadoPortadorasBandas = new String[2];

        String[] portadorasBandas = procurarPorInformacao(arquivoLog, pattern)
                .replaceAll("earfcndl\\n=+\\n|EUtranCell.*DD=| {2,}", "")
                .replaceAll(" ", ":")
                .split("\n");

        if(portadorasBandas[0].equals("INFORMAÇAO_NAO_ENCONTRADA")) {
            System.out.println("PORTADORAS: INFORMAÇAO_NAO_ENCONTRADA");
            resultadoPortadorasBandas[0] = "INFORMAÇAO_NAO_ENCONTRADA";
            resultadoPortadorasBandas[1] = "INFORMAÇAO_NAO_ENCONTRADA";
            return resultadoPortadorasBandas;
        }

        trocarNomePortadoras(portadorasBandas);



        StringBuilder[] sbPortadorasBandas = new StringBuilder[2];
        sbPortadorasBandas[0] = new StringBuilder();
        sbPortadorasBandas[1] = new StringBuilder();

        for(int i = portadorasBandas.length - 1; i > 0; i--) {
            if(portadorasBandas[i - 1].equals(portadorasBandas[i])) {
                portadorasBandas[i] = "null";
            } else {
                total++;
            }
        }

        sbPortadorasBandas[0]
                .append(total)
                .append("(");
        sbPortadorasBandas[1]
                .append(total)
                .append("(");

        for(int i = portadorasBandas.length - 1; i >= 0 ; i--) {
            index = portadorasBandas[i].indexOf(":");
            if (index != -1) {
                sbPortadorasBandas[0]
                        .append(portadorasBandas[i], 0, index);
                sbPortadorasBandas[1]
                        .append(portadorasBandas[i], index + 1, portadorasBandas[i].length());

                if(i != 0) {
                    sbPortadorasBandas[0]
                            .append("/");
                    sbPortadorasBandas[1]
                            .append("/");
                }
            }
        }
        sbPortadorasBandas[0]
                .append(")");
        sbPortadorasBandas[1]
                .append(")");

        resultadoPortadorasBandas[0] = sbPortadorasBandas[0].toString();
        resultadoPortadorasBandas[1] = sbPortadorasBandas[1].toString();

        System.out.println("PORTADORAS: OK");

        return resultadoPortadorasBandas;
    }
}
