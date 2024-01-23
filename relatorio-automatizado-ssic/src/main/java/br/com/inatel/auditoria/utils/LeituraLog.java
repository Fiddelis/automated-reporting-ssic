package br.com.inatel.auditoria.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeituraLog {
    public String read(String file) {
        StringBuilder fileContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(file), 8192)) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String content = fileContent.toString();
        return content;
    }

}
