package com.pss.exercicioavaliativopss.factory.Logger;

import com.pss.exercicioavaliativopss.model.Log;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LoggerXML implements InterfaceLogger {

    private File file;
    private final String path = "logs/log.xml";

    public LoggerXML() {
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
        try {
            XStream stream = new XStream();
            stream.alias("UsuarioCRUD", Log.class);

            FileWriter writer = new FileWriter(file, true);
            writer.write(stream.toXML(log) + "\n");
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar Log!" + e.getMessage());
        }
    }

    @Override
    public void logFalha(Log log) {
        try {
            XStream stream = new XStream();
            stream.alias("Falha", Log.class);

            FileWriter writer = new FileWriter(file, true);
            writer.write(stream.toXML(log) + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar Log!" + e.getMessage());
        }
    }

}
