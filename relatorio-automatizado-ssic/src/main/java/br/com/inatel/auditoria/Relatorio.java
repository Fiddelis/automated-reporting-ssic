package br.com.inatel.auditoria;

import br.com.inatel.auditoria.informacao.*;
import br.com.inatel.auditoria.utils.Regex;

public class Relatorio extends Regex {
    Vizinhos logVizinhos = new Vizinhos();
    LatLong logLatLong = new LatLong();
    Mimo logMimo = new Mimo();
    Tac logTac = new Tac();
    Banda logBanda = new Banda();
    Cgid logCgid = new Cgid();
    Pci logPci = new Pci();
    TotalPortadorasBandas logTotalPortadorasBandas = new TotalPortadorasBandas();

    public void gerarScript(String[] site, String pasta) {
        for (int i = 0; site[i] != null; i++) {
            System.out.println();
            if (i == 0) {
                System.out.println("amos " + site[i]);
            } else {
                System.out.println("l amos " + site[i]);
            }
            System.out.println("@L " + pasta + "\\ferramentas\\logs\\" + site[i] + ".log\n" +
                    "lt all\n" +
                    "unset $CELL96\n" +
                    "unset $NR_OF_REL\n" +
                    "hget ^EUtranCell.*DD= EUtranCell.*DDId\n" +
                    "for $mo in hget_group\n" +
                    "get $mo EUtranCell.*DDId > $CELL\n" +
                    "lpr $CELL.*CellRelation=\n" +
                    "$NR_OF_REL[$CELL] = $nr_of_mos\n" +
                    "Done\n" +
                    "pv $NR_OF_REL \n" +
                    "hget SectorCarrier= noOfTxAntennas|noOfrxAntennas\n" +
                    "hget . latitude|longitude\n" +
                    "get ^(N|EU).*Cell tac$\n" +
                    "hget . dlChannelBandwidth|ulChannelBandwidt\n" +
                    "get enodeBfunction=1 eNodeBPlmnId  \n" +
                    "get enodeBfunction=1 enbid > $enodeB_ID_Value\n" +
                    "hget EUtranCellFDD=.*. ^CellId\n" +
                    "hget EUtranCell.*DD= physicalLayerCellId$\n" +
                    "hget . earfcndl\n" +
                    "@L-");
        }
    }

    public void gerarInformacao(String nomeSite, String arquivoLog, String[] portadorasRequisitadas, String pasta) {

        System.out.println("SITE: " + nomeSite);
        String vizinhos = logVizinhos.info(arquivoLog, portadorasRequisitadas);
        String latLong = logLatLong.info(arquivoLog);
        String mimo = logMimo.info(arquivoLog, portadorasRequisitadas);
        String tac = logTac.info(arquivoLog);
        String banda = logBanda.info(arquivoLog);
        String cgid = logCgid.info(arquivoLog);
        String pci = logPci.info(arquivoLog, portadorasRequisitadas);
        String[] totalPortadorasBandas = logTotalPortadorasBandas.info(arquivoLog);

        Excel excel = new Excel();

        excel.criarAuditoria(nomeSite, portadorasRequisitadas, totalPortadorasBandas, cgid, vizinhos, tac, pci, mimo, latLong, banda, pasta);
        System.out.println("-------------------");
    }
}
