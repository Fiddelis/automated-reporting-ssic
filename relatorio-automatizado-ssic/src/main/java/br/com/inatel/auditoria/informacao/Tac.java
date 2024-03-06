package br.com.inatel.auditoria.informacao;

import br.com.inatel.utils.Regex;
import java.util.regex.Pattern;

public class Tac extends Regex {
    Pattern pattern = Pattern.compile("(EUtranCell.*DD=\\w{6} +tac +\\d+\\n)+");

    public String info(String logFile) {
        String[] tac = procurarPorInformacao(logFile, pattern)
                .replaceAll("EUtranCell.*DD=\\w+ +tac +", "")
                .split("\\n");

        if(tac[0].equals("INFORMAÇAO_NAO_ENCONTRADA")) {
            System.out.println("TAC: INFORMAÇAO_NAO_ENCONTRADA");
            return "INFORMAÇAO_NAO_ENCONTRADA";
        }

        StringBuilder resultadoTac = new StringBuilder();

        resultadoTac.append(tac[0]);

        for(int i = 1; i < tac.length; i++) {
            if(!tac[0].equals(tac[i])) {
                resultadoTac.append(tac[i]);
            }
        }
        System.out.println("TAC: OK");
        return resultadoTac.toString();
    }
}
