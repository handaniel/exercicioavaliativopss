package com.pss.exercicioavaliativopss.model;

import com.pss.senha.validacao.ValidadorSenha;
import java.time.LocalDate;
import java.util.List;

public abstract class UsuarioModel {

    private int id;
    private String nome;
    private String username;
    private String senha;
    private LocalDate dataCadastro;

    public UsuarioModel(int id, String nome, String username, String senha, LocalDate dataCadastro) {
        this.setId(id);
        this.setNome(nome);
        this.setUsername(username);
        this.setSenha(senha);
        this.setDataCadastro(dataCadastro);
    }

    public UsuarioModel(String nome, String username, String senha, LocalDate dataCadastro) {
        this.setId(-1);
        this.setNome(nome);
        this.setUsername(username);
        this.setSenha(senha);
        this.setDataCadastro(dataCadastro);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome != null && !nome.isEmpty() && !nome.isBlank()) {
            this.nome = nome;
        } else {
            throw new RuntimeException("Nome inválido!");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username != null && !username.isBlank() && !username.isEmpty()) {
            this.username = username;
        } else {
            throw new RuntimeException("Nome de usuário inválido!");
        }
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws RuntimeException {
        ValidadorSenha validador = new ValidadorSenha();
        List<String> lista = validador.validar(senha);
        if (lista.isEmpty()) {
            this.senha = senha;
        } else {
            String erro = "Senha inválida!\n";
            for (String s : lista) {
                erro = erro + s + "\n";
            }
            throw new RuntimeException(erro);
        }
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        if (dataCadastro != null) {
            this.dataCadastro = dataCadastro;
        } else {
            throw new RuntimeException("Data de cadastro inválida!");
        }
    }

}
