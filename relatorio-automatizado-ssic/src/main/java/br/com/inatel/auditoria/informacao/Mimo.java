package br.com.inatel.auditoria.informacao;

import br.com.inatel.auditoria.utils.Regex;

import java.util.regex.Pattern;

public class Mimo extends Regex {
    Pattern pattern = Pattern.compile("(SectorCarrier=\\w{6} *\\d *\\d *\\n)+");

    public String info(String arquivoLog, String[] portadorasRequisitadas) {
        StringBuilder resultadoMimo = new StringBuilder();
        int index;
        String mimoAnterior = "null";

        String[] mimo = procurarPorInformacao(arquivoLog, pattern)
                .replaceAll("SectorCarrier=", "")
                .replaceAll(" +\n", "\n")
                .replaceAll(" {2,}", "x")
                .replaceAll(" ", ":")
                .split("\\n");

        trocarNomePortadoras(mimo);

        for (int i = 0; i < mimo.length; i++) {
            index = mimo[i].indexOf(":");
            if (index == -1) continue;

            for(int j = 0; j < portadorasRequisitadas.length; j++) {
                if(portadorasRequisitadas[j].equals(mimo[j].substring(0, index)) && !mimoAnterior.equals(mimo[j].substring(index + 1))) {
                    resultadoMimo
                            .append("MIMO (")
                            .append(mimo[i].substring(index + 1))
                            .append(")\n");
                    mimoAnterior = mimo[i].substring(index + 1);
                }
            }
        }

        System.out.println("MIMO: OK");
        return resultadoMimo.toString();
    }

}
