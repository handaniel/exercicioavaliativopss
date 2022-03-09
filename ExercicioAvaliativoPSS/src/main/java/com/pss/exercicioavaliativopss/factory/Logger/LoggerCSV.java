package com.pss.exercicioavaliativopss.factory.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import com.opencsv.CSVWriter;
import com.pss.exercicioavaliativopss.model.Log;

public class LoggerCSV implements InterfaceLogger {

    private File file;
    private final String path = "logs/log.csv";

    public LoggerCSV() {
        criarArquivo();
    }

    @Override
    public void criarArquivo() {
        File diretorio = new File("logs/");

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        file = new File(path);

        if (!file.exists()) {

            try {

                CSVWriter writer = new CSVWriter(
                        new FileWriter(file),
                        ';',
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);

                String cabecalho[] = {"OPERAÇÃO", "NOME", "DATA", "HORA", "USUARIO", "FALHA"};

                writer.writeNext(cabecalho);
                writer.close();

            } catch (IOException e) {
                throw new RuntimeException("Erro ao criar arquivo de Log!" + e.getMessage());
            }

        }

    }

    @Override
    public void logUsuarioCRUD(Log log) {
        try {
            String linha[] = {
                log.getOperacao(),
                log.getNome(),
                log.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                log.getHora().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                log.getUsername(),
                "-"
            };

            CSVWriter writer = new CSVWriter(
                    new FileWriter(file),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            writer.writeNext(linha);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar log! " + e.getMessage());
        }
    }

    @Override
    public void logFalha(Log log) {
        try {
            String linha[] = {
                log.getOperacao(),
                log.getNome(),
                log.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                log.getHora().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                log.getUsername(),
                log.getExcecao()
            };

            CSVWriter writer = new CSVWriter(
                    new FileWriter(file),
                    ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            writer.writeNext(linha);
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar log! " + e.getMessage());
        }
    }

}
