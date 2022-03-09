package com.pss.exercicioavaliativopss.factory.Logger;

import com.pss.exercicioavaliativopss.model.Log;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
            JAXBContext jaxbContext = JAXBContext.newInstance(Log.class);

            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();

            marshaller.marshal(log, sw);

        } catch (JAXBException e) {
            throw new RuntimeException("Erro ao gravar Log!" + e.getMessage());
        }
    }

    @Override
    public void logFalha(Log log) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Log.class);

            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();

            marshaller.marshal(log, sw);

        } catch (JAXBException e) {
            throw new RuntimeException("Erro ao gravar Log!" + e.getMessage());
        }
    }

}
