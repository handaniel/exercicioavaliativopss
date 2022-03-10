package com.pss.exercicioavaliativopss.model;

import java.time.LocalDate;

public class Notificacao {

    private int id;
    private int destinatario;
    private int remetente;
    private String mensagem;
    private boolean lida;
    private LocalDate dataEnvio;

    public Notificacao(int id, int destinatario, int remetente, String mensagem, boolean lida, LocalDate dataEnvio) {
        this.setId(id);
        this.setDestinatario(destinatario);
        this.setRemetente(remetente);
        this.setMensagem(mensagem);
        this.setLida(lida);
        this.setDataEnvio(dataEnvio);
    }

    public Notificacao(int destinatario, int remetente, String mensagem, boolean lida, LocalDate dataEnvio) {
        this(-1, destinatario, remetente, mensagem, lida, dataEnvio);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(int destinatario) {
        this.destinatario = destinatario;
    }

    public int getRemetente() {
        return remetente;
    }

    public void setRemetente(int remetente) {
        this.remetente = remetente;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        if (mensagem != null && !mensagem.isBlank() && !mensagem.isEmpty()) {
            this.mensagem = mensagem;
        } else {
            throw new RuntimeException("Mensagem não pode ser vazia ou nula!");
        }
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

}
