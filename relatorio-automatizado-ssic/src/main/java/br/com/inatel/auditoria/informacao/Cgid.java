package br.com.inatel.auditoria.informacao;

import br.com.inatel.auditoria.utils.Regex;

import java.util.regex.Pattern;

public class Cgid extends Regex {
    Pattern patternMcc = Pattern.compile("mcc = \\d+");
    Pattern patternMnc = Pattern.compile("mnc = \\d+");
    Pattern patternEnbid = Pattern.compile("eNBId +\\d+");
    Pattern patternCellid = Pattern.compile("(cellId\\n=+)\\n(EUtranCell.*DD=\\w{6} \\d+ *\\n)*");

    public String info(String arquivoLog) {
        String mcc = procurarPorInformacao(arquivoLog, patternMcc)
                .replaceAll("mcc = ", "");

        String mnc = procurarPorInformacao(arquivoLog, patternMnc)
                .replaceAll("mnc = ", "");

        String enbid = procurarPorInformacao(arquivoLog, patternEnbid)
                .replaceAll("eNBId +", "");

        String cellId = procurarPorInformacao(arquivoLog, patternCellid)
                .replaceAll("(cellId\\n=+\\n)|(EUtranCell.*DD=\\w+ +| +)", "")
                .replaceAll("\\n(?!$)", "/")
                .replaceAll("\\n","");

        System.out.println("CGID: OK");
        return mcc + "-" + mnc + "-" + enbid + "-(" + cellId + ")";
    }
}
