package br.com.inatel;

import br.com.inatel.auditoria.Relatorio;
import br.com.inatel.auditoria.utils.LeituraLog;
import br.com.inatel.relatorio.Qgis;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LeituraLog leituraLog = new LeituraLog();
        Relatorio relatorio = new Relatorio();
        String[] site = new String[20];
        String[] portadorasRequisitadas = new String[20];
        String[] cidades = new String[20];
        char[] siteSeisSetores = new char[20];
        char escolha = 's';
        int i = 0;

        System.out.print("Pasta do arquivo JAR: ");
        String pasta = sc.nextLine();
        while(escolha == 's') {
            System.out.print("Nome do Site: ");
            site[i] = sc.nextLine();

            System.out.print("Site 6 setores? (s/n): ");
            siteSeisSetores[i] = sc.nextLine().charAt(0);

            System.out.print("Portadoras Requisitadas (700 1800 1800C 2100 2300 2600 2600Q): ");
            portadorasRequisitadas[i] = sc.nextLine();

            System.out.print("Cidade: ");
            cidades[i] = sc.nextLine();

            System.out.print("Mais site? (s/n): ");
            escolha = sc.nextLine().charAt(0);

            i++;
        }

        relatorio.gerarScript(site, pasta);

        System.out.println();
        System.out.print("Feito? (s/n): ");

        escolha = sc.nextLine().charAt(0);
        switch (escolha) {
            case 's' -> {
                Qgis qgis = new Qgis();

                for (i = 0; site[i] != null; i++) {
                    qgis.gerarRelatorio(portadorasRequisitadas[i].split(" "), site[i], cidades[i], siteSeisSetores[i], pasta);

                    relatorio.gerarInformacao(site[i], leituraLog.read(pasta +  "\\ferramentas\\logs\\" + site[i] + ".log"), portadorasRequisitadas[i].split(" "), pasta);

                    System.out.println();
                }
            }
            case 'n' -> System.out.println("Bye!");
        }
    }
}