package com.pss.exercicioavaliativopss.model;

import java.time.LocalDate;

public class Usuario extends UsuarioModel {

    private boolean autorizado;

    public Usuario(int id, String nome, String username, String senha, LocalDate dataCadastro, boolean autorizado) {
        super(id, nome, username, senha, dataCadastro);
        setAutorizado(autorizado);
    }

    public Usuario(String nome, String username, String senha, LocalDate dataCadastro, boolean autorizado) {
        super(-1, nome, username, senha, dataCadastro);
        setAutorizado(autorizado);
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }

}
