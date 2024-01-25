package br.com.inatel.auditoria.informacao;

import br.com.inatel.auditoria.utils.Regex;

import java.util.regex.Pattern;

public class Pci extends Regex {
    Pattern pattern = Pattern.compile("physicalLayerCellId\\n=+\\n(EUtranCell.*DD=\\w{6} \\d+ *\\n)+");

    public String info(String arquivoLog, String[] portadorasRequisitadas) {
        int index;
        String[] pci = procurarPorInformacao(arquivoLog, pattern)
                .replaceAll("physicalLayerCellId\\n=+\\n|EUtranCell.*DD=| {2,}", "")
                .replaceAll(" ", ":")
                .replaceAll(" *\\n(?!$)", "\n")
                .split("\n");

        String portadoraPci;
        StringBuilder resultadoPci = new StringBuilder();

        trocarNomePortadoras(pci);

        for (int i = 0; i < pci.length; i++) {
            index = pci[i].indexOf(":");
            if (index != -1) {
                portadoraPci = pci[i].substring(0, index);

                for (String portadoraRequisitada : portadorasRequisitadas)
                    if (portadoraPci.equals(portadoraRequisitada)) {
                        resultadoPci.append(pci[i], index + 1, pci[i].length());
                            resultadoPci.append("/");
                    }
            }
        }
        resultadoPci.deleteCharAt(resultadoPci.length()-1);

        System.out.println("PCI: OK");
        return resultadoPci.toString();
    }
}
