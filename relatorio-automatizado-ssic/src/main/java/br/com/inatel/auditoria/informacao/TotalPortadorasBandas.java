package br.com.inatel.auditoria.informacao;

import br.com.inatel.auditoria.utils.Regex;

import java.util.regex.Pattern;

public class TotalPortadorasBandas extends Regex {
    Pattern pattern = Pattern.compile("earfcndl\\n=+\\n(EUtranCell.*DD=\\w{6} \\d+ *\\n)+");

    public String[] info(String arquivoLog) {
        int index;
        int total = 1;
        StringBuilder resultadoPortadoras = new StringBuilder();
        StringBuilder resultadoBandas = new StringBuilder();
        String[] portadorasBandas = procurarPorInformacao(arquivoLog, pattern)
                .replaceAll("earfcndl\\n=+\\n|EUtranCell.*DD=| {2,}", "")
                .replaceAll(" ", ":")
                .split("\n");

        trocarNomePortadoras(portadorasBandas);

        String[] resultadoPortadorasBandas = new String[2];

        StringBuilder[] sbPortadorasBandas = new StringBuilder[2];
        sbPortadorasBandas[0] = new StringBuilder();
        sbPortadorasBandas[1] = new StringBuilder();

        for(int i = 1; i < portadorasBandas.length; i++) {
            if(portadorasBandas[i - 1].equals(portadorasBandas[i])) {
                portadorasBandas[i - 1] = "null";
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

        for(int i = 0; i < portadorasBandas.length; i++) {
            index = portadorasBandas[i].indexOf(":");
            if (index != -1) {
                sbPortadorasBandas[0]
                        .append(portadorasBandas[i], 0, index);
                sbPortadorasBandas[1]
                        .append(portadorasBandas[i], index + 1, portadorasBandas[i].length());

                if(i != portadorasBandas.length - 1) {
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

        System.out.println("PORTADORAS:");
        for(int i = 0; i < 2; i++) {
            resultadoPortadorasBandas[i] = sbPortadorasBandas[i].toString();
            System.out.println(resultadoPortadorasBandas[i]);
        }

        return resultadoPortadorasBandas;
    }
}
