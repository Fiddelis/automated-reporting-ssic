package br.com.inatel.auditoria.informacao;

import br.com.inatel.utils.Regex;
import java.util.regex.Pattern;

public class Banda extends Regex {
    Pattern pattern = Pattern.compile("(EUtranCell.*DD=\\w{6} \\d+ {6,}\\d+ *\\n)+");
    
    public String info(String arquivoLog) {
        int index;
        int quantidadeBandas = 0;
        StringBuilder resultadoBanda = new StringBuilder();

        String[] banda = procurarPorInformacao(arquivoLog, pattern)
                .replaceAll("EUtranCell.*DD=|000", "")
                .replaceAll(" +\n", "\n")
                .replaceAll(" {2,}","/")
                .replaceAll(" ", ":")
                .split("\\n");

        if(banda[0].equals("INFORMAÇAO_NAO_ENCONTRADA")) {
            System.out.println("Banda: INFORMAÇAO_NAO_ENCONTRADA");
            return "INFORMAÇAO_NAO_ENCONTRADA";
        }

        trocarNomePortadoras(banda);
        
        for(int i = 1; i < banda.length; i++) {
            if(banda[i].equals(banda[i-1])) {
                banda[i-1] = "null";
            }
        }

        for (int i = banda.length - 1; i >= 0; i--) {
            index = banda[i].indexOf(":");
            if(index != -1) {
                resultadoBanda
                        .append(banda[i], index + 1,banda[i].length())
                        .append(" MHz (")
                        .append(banda[i], 0, index)
                        .append(")");
                if(i != 2) {
                    resultadoBanda.append(", ");
                }

                quantidadeBandas++;
                if(quantidadeBandas == 4) {
                    resultadoBanda.append("\n");
                }
            }
        }

        System.out.println("Banda: OK");
        return resultadoBanda.toString();
    }
}
