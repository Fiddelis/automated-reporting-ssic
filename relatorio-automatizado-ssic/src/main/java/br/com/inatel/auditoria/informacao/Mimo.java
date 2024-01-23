package br.com.inatel.auditoria.informacao;

import br.com.inatel.auditoria.utils.Regex;

import java.util.regex.Pattern;

public class Mimo extends Regex {
    Pattern pattern = Pattern.compile("(SectorCarrier=\\w{6} *\\d *\\d *\\n)+");

    public String info(String arquivoLog, String[] portadorasRequisitadas) {
        StringBuilder resultadoMimo = new StringBuilder();
        String portadoraMimo;
        int index;
        String[] mimo = procurarPorInformacao(arquivoLog, pattern)
                .replaceAll("SectorCarrier=", "")
                .replaceAll(" +\n", "\n")
                .replaceAll(" {2,}", "x")
                .replaceAll(" ", ":")
                .split("\\n");

        trocarNomePortadoras(mimo);

        for(int i = 1; i < mimo.length; i++) {
            if(mimo[i].equals(mimo[i-1])) {
                mimo[i-1] = "null";
            }
        }


        for (String m : mimo) {
            index = m.indexOf(":");
            if(index == -1) continue;
            portadoraMimo = m.substring(0, index);

            for (String portadoraRequisitada : portadorasRequisitadas)
                if (portadoraMimo.equals(portadoraRequisitada)) {
                    resultadoMimo
                            .append("MIMO ")
                            .append(m.substring(index + 1))
                            .append(" ");
                }
        }

        System.out.println("MIMO:\n" + resultadoMimo);
        return resultadoMimo.toString();
    }

}
