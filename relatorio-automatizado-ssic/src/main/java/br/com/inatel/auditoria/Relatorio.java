package br.com.inatel.auditoria;

import br.com.inatel.auditoria.informacao.*;
import br.com.inatel.utils.Regex;

public class Relatorio extends Regex {
    static Vizinhos logVizinhos = new Vizinhos();
    static LatLong logLatLong = new LatLong();
    static Mimo logMimo = new Mimo();
    static Tac logTac = new Tac();
    static Banda logBanda = new Banda();
    static Cgid logCgid = new Cgid();
    static Pci logPci = new Pci();
    static TotalPortadorasBandas logTotalPortadorasBandas = new TotalPortadorasBandas();

    public static void gerarScript(String[] site, String pasta) {
        System.out.println("██████████ SCRIPT ██████████");

        for (int i = 0; site[i] != null; i++) {
            System.out.println();
            System.out.println("amos " + site[i]);
            System.out.println("lt all\n" +
                    "unset $CELL96\n" +
                    "unset $NR_OF_REL\n" +
                    "hget ^EUtranCell.*DD= EUtranCell.*DDId\n" +
                    "for $mo in hget_group\n" +
                    "get $mo EUtranCell.*DDId > $CELL\n" +
                    "lpr $CELL.*CellRelation=\n" +
                    "$NR_OF_REL[$CELL] = $nr_of_mos\n" +
                    "Done\n" +
                    "@L " + pasta + "\\ferramentas\\logs\\" + site[i] + ".log\n" +
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
                    "@L-\n" +
                    "exit");
        }
    }

    public static void gerarInformacao(String nomeSite, String arquivoLog, String[] portadorasRequisitadas, String pasta) {

        System.out.println("██████████ EXCEL ██████████");
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
    }
}
