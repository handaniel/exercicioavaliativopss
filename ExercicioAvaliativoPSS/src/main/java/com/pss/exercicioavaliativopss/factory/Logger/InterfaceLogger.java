package com.pss.exercicioavaliativopss.factory.Logger;

import com.pss.exercicioavaliativopss.model.UsuarioModel;

public interface InterfaceLogger {

    public void criarArquivo();

    public void logUsuarioCRUD(UsuarioModel usuario, String operacao);

    public void logFalha(UsuarioModel usuario, String operacao, String excessao);

}
