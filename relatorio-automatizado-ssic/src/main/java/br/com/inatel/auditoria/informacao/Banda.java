package br.com.inatel.auditoria.informacao;

import br.com.inatel.auditoria.utils.Regex;

import java.util.regex.Pattern;

public class Banda extends Regex {
    Pattern pattern = Pattern.compile("(EUtranCell.*DD=\\w{6} \\d+ {6,}\\d+ *\\n)+");
    
    public String info(String arquivoLog) {
        int index;
        StringBuilder resultadoBanda = new StringBuilder();

        String[] banda = procurarPorInformacao(arquivoLog, pattern)
                .replaceAll("EUtranCell.*DD=|000", "")
                .replaceAll(" +\n", "\n")
                .replaceAll(" {2,}","/")
                .replaceAll(" ", ":")
                .split("\\n");

        trocarNomePortadoras(banda);
        
        for(int i = 1; i < banda.length; i++) {
            if(banda[i].equals(banda[i-1])) {
                banda[i-1] = "null";
            }
        }

        for (String b : banda) {
            index = b.indexOf(":");
            if(index != -1) {
                resultadoBanda
                        .append(b, index + 1,b.length())
                        .append(" MHz (")
                        .append(b, 0, index)
                        .append(") ");
            }
        }

        System.out.println("BANDA:\n" + resultadoBanda);
        return resultadoBanda.toString();
    }
}
