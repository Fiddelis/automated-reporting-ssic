package br.com.inatel.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ManipulacaoDeArquivos {

    public static String leituraArquivoLog(String arquivo) {
        StringBuilder conteudo = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo), 8192)) {
            String line;
            while ((line = br.readLine()) != null) {
                conteudo.append(line).append("\n");
            }
        } catch (IOException e) {
            System.out.println("LOG: LOG_NAO_ENCONTRADO");
        }

        return conteudo.toString();
    }


    public static void editarTabelaSeisSetores(String site, String cidade, String uf, String pasta) {
        Row linha;
        Cell celula;

        String caminhoArquivo = pasta + "\\ferramentas\\templates\\SRB_VIVO_six_sectors_allfreq\\SSV_Tables.xlsx";

        try (FileInputStream fileIn = new FileInputStream(caminhoArquivo);
             Workbook workbook = new XSSFWorkbook(fileIn)) {
            Sheet sheet = workbook.getSheetAt(1);

            linha = sheet.getRow(0);
            celula = linha.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            celula.setCellValue(site);

            linha = sheet.getRow(1);
            celula = linha.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            celula.setCellValue(cidade);

            linha = sheet.getRow(2);
            celula = linha.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            celula.setCellValue(uf);

            try (FileOutputStream fileOut = new FileOutputStream(caminhoArquivo)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            System.out.println("Arquivo 'SSV_Tables.xlsx' não encontrado ou ocorreu um erro ao manipular o arquivo.");
            e.printStackTrace();
        }
    }

    public static void editarTabela(String portadora, String site, String cidade, String uf, String pasta) {
        Row linha;
        Cell celula;

        String caminhoArquivo = pasta + "\\ferramentas\\templates\\01 - SRB_Template\\" + portadora + " Mhz\\SSV_Tables.xlsx";

        try (FileInputStream fileIn = new FileInputStream(caminhoArquivo);
             Workbook workbook = new XSSFWorkbook(fileIn)) {
            Sheet sheet = workbook.getSheetAt(1);

            linha = sheet.getRow(0);
            celula = linha.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            celula.setCellValue(site);

            linha = sheet.getRow(1);
            celula = linha.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            celula.setCellValue(cidade);

            linha = sheet.getRow(2);
            celula = linha.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            celula.setCellValue(uf);

            try (FileOutputStream fileOut = new FileOutputStream(caminhoArquivo)) {
                workbook.write(fileOut);
            }
        } catch (IOException e) {
            System.out.println("Arquivo 'SSV_Tables.xlsx' não encontrado ou ocorreu um erro ao manipular o arquivo.");
            e.printStackTrace();
        }
    }

    public static String criarPasta(String pasta, String nomePasta) {
        String caminhoDaPasta = pasta + "\\" + nomePasta;

        Path path = Paths.get(caminhoDaPasta);

        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                System.out.println("Pasta criada com sucesso!");
            } catch (Exception e) {
                System.out.println("Falha ao criar a pasta: " + e.getMessage());
            }
        } else {
            System.out.println("A pasta já existe.");
        }

        return caminhoDaPasta;
    }

    public static void extrairArquivoCompactado(String caminhoArquivoZip, String pastaDestino) {
        byte[] buffer = new byte[1024];

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(caminhoArquivoZip))) {

            Path pastaPath = Paths.get(pastaDestino);

            try {
                Files.walk(pastaPath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);

                System.out.println("Pasta excluída com sucesso.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Falha ao excluir a pasta.");
            }

            File destino = new File(pastaDestino);
            destino.mkdirs();

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String caminhoCompletoDestino = pastaDestino + File.separator + zipEntry.getName();
                File novoArquivo = new File(caminhoCompletoDestino);

                if (zipEntry.isDirectory()) {
                    novoArquivo.mkdirs();
                } else {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(novoArquivo)) {
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }

            System.out.println("Arquivo ZIP extraído com sucesso.");

        } catch (IOException e) {
            System.out.println("ERRO AO EXTRAIR ARQUIVO");
        }
    }

    public static void copiarPowerPoint(String pastaDestino, String nomeSite, String portadoras) {
        String origem = "C:\\Users\\lucas.ferreira\\Desktop\\Relatorio_Automatizado_SSIC_1.0\\ferramentas\\SINGLE SITE VERIFICATION_DL_SSV.pptx";
        String destino = pastaDestino + "\\" + nomeSite + "\\SINGLE SITE VERIFICATION_DL_SSV_VIVO_" + nomeSite + "_" + portadoras.replaceAll(" ", "_");

        try {
            Path origemPath = Paths.get(origem);
            Path destinoPath = Paths.get(destino + ".pptx");

            Files.copy(origemPath, destinoPath);

            System.out.println("PowerPoint copiado com sucesso!\n");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao copiar o arquivo");
        }
    }
}
