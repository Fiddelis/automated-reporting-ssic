package br.com.inatel.auditoria.informacao;

import br.com.inatel.utils.Regex;
import java.util.regex.Pattern;

public class Cgid extends Regex {
    Pattern patternMcc = Pattern.compile("mcc = \\d+");
    Pattern patternMnc = Pattern.compile("mnc = \\d+");
    Pattern patternEnbid = Pattern.compile("eNBId +\\d+");
    Pattern patternCellid = Pattern.compile("(cellId\\n=+)\\n(EUtranCell.*DD=\\w{6} \\d+ *\\n)+");

    public String info(String arquivoLog) {
        String mcc = procurarPorInformacao(arquivoLog, patternMcc)
                .replaceAll("mcc = ", "");

        String mnc = procurarPorInformacao(arquivoLog, patternMnc)
                .replaceAll("mnc = ", "");

        String enbid = procurarPorInformacao(arquivoLog, patternEnbid)
                .replaceAll("eNBId +", "");

        String[] cellId = procurarPorInformacao(arquivoLog, patternCellid)
                .replaceAll("(cellId\\n=+\\n|EUtranCell.*DD=\\w+ | {2,})", "")
                .split("\n");

        if(mcc.equals("INFORMAÇAO_NAO_ENCONTRADA") || mnc.equals("INFORMAÇAO_NAO_ENCONTRADA") || enbid.equals("INFORMAÇAO_NAO_ENCONTRADA")) {
            System.out.println("CGID: INFORMAÇAO_NAO_ENCONTRADA");
            return "INFORMAÇÃO NÃO ENCONTRADA";
        }

        StringBuilder resCellId = new StringBuilder();

        for(int i = 0; i < cellId.length; i++) {
            resCellId.append(cellId[i]);

            if(i != cellId.length - 1) {
                if(Integer.parseInt(cellId[i + 1]) - Integer.parseInt(cellId[i]) != 1) {
                    resCellId.append("-");
                } else {
                    resCellId.append("/");
                }
            }
        }

        String[] manipularCellId = resCellId.toString().split("-");

        resCellId.delete(0, resCellId.length());

        for(int i = manipularCellId.length - 1; i >= 0; i--) {
            resCellId.append(manipularCellId[i]);
            if(i != 0) {
                resCellId.append("-");
            }
        }


        System.out.println("CGID: OK");
        return mcc + "-" + mnc + "-" + enbid + "-(" + resCellId + ")";
    }
}

