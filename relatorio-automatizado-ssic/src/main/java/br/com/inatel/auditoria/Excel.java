package br.com.inatel.auditoria;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Excel {
    public void criarAuditoria(String nomeSite, String[] portadorasRequisitadas, String[] totalPortadorasBandas, String cgid, String vizinhos, String tac, String pci, String mimo, String latLong, String banda, String pasta) {

        StringBuilder novasPortadoras = new StringBuilder();
        Row linha;
        Cell celula;

        for (int i = 0; i < portadorasRequisitadas.length; i++) {
            novasPortadoras.append(portadorasRequisitadas[i]);
            if (i < portadorasRequisitadas.length - 1) {
                novasPortadoras.append("/");
            }
        }

        int totalCelulas = 1;

        int indice = cgid.indexOf("/");
        while (indice != -1) {
            totalCelulas++;
            indice = cgid.indexOf("/", indice + 1);
        }

        try (FileInputStream fileIn = new FileInputStream(pasta + "\\ferramentas\\auditoria.xlsx");
             Workbook workbook = new XSSFWorkbook(fileIn)) {
            Sheet sheet = workbook.getSheetAt(0);

            linha = sheet.getRow(1);
            celula = linha.getCell(1);
            celula.setCellValue(nomeSite);

            linha = sheet.getRow(3);
            celula = linha.getCell(1);
            celula.setCellValue(totalPortadorasBandas[0]);

            linha = sheet.getRow(4);
            celula = linha.getCell(1);
            celula.setCellValue(totalPortadorasBandas[1]);

            linha = sheet.getRow(5);
            celula = linha.getCell(1);
            celula.setCellValue(totalCelulas);

            linha = sheet.getRow(6);
            celula = linha.getCell(1);
            celula.setCellValue(latLong);

            linha = sheet.getRow(7);
            celula = linha.getCell(1);
            celula.setCellValue("Novas Portadoras: " + novasPortadoras + " MHz");

            linha = sheet.getRow(1);
            celula = linha.getCell(3);
            celula.setCellValue(cgid);

            linha = sheet.getRow(2);
            celula = linha.getCell(3);
            celula.setCellValue(mimo);

            linha = sheet.getRow(4);
            celula = linha.getCell(3);
            celula.setCellValue(tac);

            linha = sheet.getRow(4);
            celula = linha.getCell(3);
            celula.setCellValue(tac);

            linha = sheet.getRow(6);
            celula = linha.getCell(3);
            celula.setCellValue(banda);

            linha = sheet.getRow(1);
            celula = linha.getCell(5);
            celula.setCellValue(pci);

            linha = sheet.getRow(2);
            celula = linha.getCell(5);
            celula.setCellValue(vizinhos);

            try (FileOutputStream fileOut = new FileOutputStream(pasta + "\\relatorios\\" + nomeSite + "\\auditoria.xlsx")) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            System.out.println("Arquivo 'auditoria.xlsx' não encontrado");
        }
    }


}
