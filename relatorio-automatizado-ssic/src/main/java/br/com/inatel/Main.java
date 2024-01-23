package br.com.inatel;

import br.com.inatel.auditoria.Relatorio;
import br.com.inatel.auditoria.utils.LeituraLog;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LeituraLog leituraLog = new LeituraLog();
        Relatorio relatorio = new Relatorio();
        char escolha;

        String[] nomeSite;
        String[] portadorasRequisitadas;
        String pasta;

        System.out.print("Nome do Site: ");
        nomeSite = sc.nextLine().split(" ");

        System.out.print("Portadoras Requisitadas: ");
        portadorasRequisitadas = sc.nextLine().split(" ");

        System.out.print("Pasta: ");
        pasta = sc.nextLine();

        relatorio.gerarScript(nomeSite, pasta);
        System.out.println();

        System.out.println("Feito? (s/n): ");

        escolha = sc.nextLine().charAt(0);
        switch (escolha) {
            case 's' -> {
                for (String s : nomeSite) {
                    relatorio.gerarInformacao(s, leituraLog.read(pasta +  "\\"+ s + ".log"), portadorasRequisitadas, pasta);
                    System.out.println();
                }
            }
            case 'n' -> System.out.println("Bye!");
        }
    }
}