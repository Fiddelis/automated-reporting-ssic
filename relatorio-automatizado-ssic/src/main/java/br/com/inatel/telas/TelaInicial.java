package br.com.inatel.telas;

import br.com.inatel.auditoria.Relatorio;
import br.com.inatel.relatorio.Qgis;

import java.util.Scanner;

import static br.com.inatel.utils.ManipulacaoDeArquivos.*;

public class TelaInicial {
    public static void exibir(String pasta) {
        Scanner sc = new Scanner(System.in);

        int escolha;
        int quantidade;
        char[] siteSeisSetores = new char[20];
        String[] site = new String[20];
        String[] portadorasRequisitadas = new String[20];
        String[] cidade = new String[20];
        String conteudoLog;

        System.out.println("1) Completo");
        System.out.println("2) Auditoria");
        System.out.println("3) QGIS");
        System.out.print("-> ");
        escolha = sc.nextInt();

        System.out.print("Quantidade de sites: ");
        quantidade = sc.nextInt();
        sc.nextLine();

        for(int i = 0; i < quantidade; i++) {
            System.out.print("Nome do Site: ");
            site[i] = sc.nextLine();

            System.out.print("Site 6 setores? (s/n): ");
            siteSeisSetores[i] = sc.nextLine().toLowerCase().charAt(0);

            System.out.print("Portadoras Requisitadas (700 1800 1800C 2100 2300 2600 2600Q): ");
            portadorasRequisitadas[i] = sc.nextLine();

            System.out.print("Cidade: ");
            cidade[i] = sc.nextLine();

            System.out.println();
            criarPasta(pasta + "\\relatorios", site[i]);
            System.out.println();
        }

        switch (escolha) {
            case 1 -> {
                Relatorio.gerarScript(site, pasta);
                sc.nextLine();

                for(int i = 0; i < quantidade; i++) {
                    conteudoLog = leituraArquivoLog(pasta +  "\\ferramentas\\logs\\" + site[i] + ".log");
                    Relatorio.gerarInformacao(site[i], conteudoLog, portadorasRequisitadas[i].split(" "), pasta);
                    Qgis.gerarRelatorio(portadorasRequisitadas[i].split(" "), site[i], cidade[i], siteSeisSetores[i], pasta);
                    copiarPowerPoint(pasta + "\\relatorios", site[i], portadorasRequisitadas[i]);
                }
            }
            case 2 -> {
                Relatorio.gerarScript(site, pasta);
                sc.nextLine();
                for(int i = 0; i < quantidade; i++) {
                    conteudoLog = leituraArquivoLog(pasta + "\\ferramentas\\logs\\" + site[i] + ".log");
                    Relatorio.gerarInformacao(site[i], conteudoLog, portadorasRequisitadas[i].split(" "), pasta);
                }
            }
            case 3-> {
                for(int i = 0; i < quantidade; i++) {
                    Qgis.gerarRelatorio(portadorasRequisitadas[i].split(" "), site[i], cidade[i], siteSeisSetores[i], pasta);
                    copiarPowerPoint(pasta + "\\relatorios", site[i], portadorasRequisitadas[i]);
                }
            }
        }

    }
}
