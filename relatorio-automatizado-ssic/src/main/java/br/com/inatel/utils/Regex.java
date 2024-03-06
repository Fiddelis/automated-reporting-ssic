package br.com.inatel.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public String procurarPorInformacao(String texto, Pattern pattern) {
        Matcher matcher = pattern.matcher(texto);
        if(matcher.find()) {
            return matcher.group();
        }
        return "INFORMAÇAO_NAO_ENCONTRADA";
    }

    public void trocarNomePortadoras(String[] portadoras) {
        for (int i = 0; i < portadoras.length; i++) {
            String p = portadoras[i];

            char firstChar = p.charAt(0);
            int index = p.indexOf(":");

            switch (firstChar) {
                case 'Z' -> portadoras[i] = "700" + p.substring(index);
                case 'V' -> portadoras[i] = "1800" + p.substring(index);
                case 'U' -> portadoras[i] = "2100" + p.substring(index);
                case 'O', 'L' -> portadoras[i] = "2300" + p.substring(index);
                case 'T' -> portadoras[i] = "2600" + p.substring(index);
                case 'C' -> portadoras[i] = "1800C" + p.substring(index);
                case 'Q' -> portadoras[i] = "2600Q" + p.substring(index);
            }
        }
    }
}
