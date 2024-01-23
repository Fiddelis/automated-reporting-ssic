package br.com.inatel.auditoria.informacao;

import br.com.inatel.auditoria.utils.Regex;

import java.util.regex.Pattern;

public class Vizinhos extends Regex {
    Pattern pattern = Pattern.compile("pv \\$NR_OF_REL\\s+(.+\\n)*");

    public String info(String logFile, String[] portadorasRequisitadas) {
        int index;
        String[] vizinhos = procurarPorInformacao(logFile, pattern)
                .replaceAll("] = ", ":")
                .replaceAll("((pv )?\\$NR_OF_REL(\\[|\\n*))|\\n{2,}| +", "")
                .split("\n");

        StringBuilder resultadoVizinhos = new StringBuilder();
        String[] vizinhosComparacao = vizinhos.clone();
        String portadora;

        trocarNomePortadoras(vizinhosComparacao);

        for(int i = 0; i < vizinhos.length; i++) {
            index = vizinhosComparacao[i].indexOf(":");

            if(index != -1) {
                portadora = vizinhosComparacao[i].substring(0, index);

                for(String portadoraRequisitada : portadorasRequisitadas) {
                    if(portadora.equals(portadoraRequisitada)) {
                        resultadoVizinhos.append(vizinhos[i]).append("\n");
                    }
                }
            }
        }
        System.out.println("Vizinhos:\n" + resultadoVizinhos);
        return resultadoVizinhos.toString();
    }
}
