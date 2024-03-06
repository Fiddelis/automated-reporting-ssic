package br.com.inatel.auditoria.informacao;

import br.com.inatel.utils.Regex;
import java.util.regex.Pattern;

public class Mimo extends Regex {
    Pattern pattern = Pattern.compile("(SectorCarrier=\\w{6} *\\d *\\d *\\n)+");

    public String info(String arquivoLog, String[] portadorasRequisitadas) {
        StringBuilder resultadoMimo = new StringBuilder();
        String portadora;
        int index;

        String[] mimo = procurarPorInformacao(arquivoLog, pattern)
                .replaceAll("SectorCarrier=", "")
                .replaceAll(" +\n", "\n")
                .replaceAll(" {2,}", "x")
                .replaceAll(" ", ":")
                .split("\\n");

        if(mimo[0].equals("INFORMAÇAO_NAO_ENCONTRADA")) {
            System.out.println("MIMO: INFORMAÇAO_NAO_ENCONTRADA");
            return "INFORMAÇAO_NAO_ENCONTRADA";
        }

        trocarNomePortadoras(mimo);

        for(int i = 1; i < mimo.length; i++) {
            if(mimo[i].equals(mimo[i-1])) {
                mimo[i - 1] = "null";
            }
        }

        for(int i = 0; i < mimo.length; i++) {
            for(int j = 0; j < portadorasRequisitadas.length; j++) {
                index = mimo[i].indexOf(":");

                if(index != -1) {
                    portadora = mimo[i].substring(0,index);

                    if(portadora.equals(portadorasRequisitadas[j])) {
                        resultadoMimo
                                .append("MIMO (")
                                .append(mimo[i].substring(index + 1))
                                .append("): ")
                                .append(mimo[i].substring(0, index))
                                .append(" ");
                    }
                }
            }
        }

        System.out.println("MIMO: OK");
        return resultadoMimo.toString();
    }

}
