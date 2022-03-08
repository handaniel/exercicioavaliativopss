package com.pss.exercicioavaliativopss.factory.Logger;

import com.pss.exercicioavaliativopss.model.Log;

public interface InterfaceLogger {

    public void criarArquivo();

    public void logUsuarioCRUD(Log log);

    public void logFalha(Log log);

}
