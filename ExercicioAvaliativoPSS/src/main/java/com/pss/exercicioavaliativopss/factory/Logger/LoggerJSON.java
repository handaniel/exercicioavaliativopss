package com.pss.exercicioavaliativopss.factory.Logger;

import com.pss.exercicioavaliativopss.model.Log;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


import org.json.simple.JSONObject;

public class LoggerJSON implements InterfaceLogger {

    private File file;
    private final String path = "logs/log.json";

    public LoggerJSON() {
        criarArquivo();
    }

    @Override
    public void criarArquivo() {
        File diretorio = new File("logs/");

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar arquivo! " + e.getMessage());
        }
    }

    @Override
    public void logUsuarioCRUD(Log log) {
        HashMap<String, Object> hashObject = new HashMap<>();

        hashObject.put("OPERACAO", log.getOperacao());
        hashObject.put("NOME", log.getNome());
        hashObject.put("DATA", log.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hashObject.put("HORA", log.getHora().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        hashObject.put("USUARIO", log.getUsername());

        JSONObject jsonObject = new JSONObject(hashObject);

        try {
            FileWriter writer = new FileWriter(file, true);

            writer.write(jsonObject.toJSONString() + ",\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar log! " + e.getMessage());
        }
    }

    @Override
    public void logFalha(Log log) {
        HashMap<String, Object> hashObject = new HashMap<>();

        hashObject.put("EXCESSAO", log.getExcecao());
        hashObject.put("OPERACAO", log.getExcecao());
        hashObject.put("NOME", log.getNome());
        hashObject.put("DATA", log.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        hashObject.put("HORA", log.getHora().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        hashObject.put("USUARIO", log.getUsername());

        JSONObject jsonObject = new JSONObject(hashObject);

        try {
            FileWriter writer = new FileWriter(file, true);

            writer.write(jsonObject.toJSONString() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar log: " + e.getMessage());
        }
    }

}
