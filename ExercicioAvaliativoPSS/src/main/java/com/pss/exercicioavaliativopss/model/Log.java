package com.pss.exercicioavaliativopss.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Log {

    private String operacao;
    private String nome;
    private String username;
    private String excecao;
    private LocalDate data;
    private LocalTime hora;

    public Log(String operacao, String nome, String username, String excecao) {
        this.operacao = operacao;
        this.nome = nome;
        this.username = username;
        this.excecao = excecao;
        this.data = LocalDate.now();
        this.hora = LocalTime.now();
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExcecao() {
        return excecao;
    }

    public void setExcecao(String excecao) {
        this.excecao = excecao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

}
