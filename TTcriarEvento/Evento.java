package com.example.tasktide.Objetos;

public class Evento {
    private int id; // ID do usuário no banco de dados
    private String nomeEvento;
    private String tipoEvento;
    private String horasComplementares;
    private String modalidade;

    public Evento() {
        // Construtor vazio necessário para o SQLite
    }

    public Evento(String nomeEvento, String tipoEvento, String horasComplementares, String modalidade) {
        this.nomeEvento = nomeEvento;
        this.tipoEvento = tipoEvento;
        this.horasComplementares = horasComplementares;
        this.modalidade = modalidade;
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getHorasComplementares() {
        return horasComplementares;
    }

    public void setHorasComplementares(String horasComplementares) {
        this.horasComplementares = horasComplementares;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

}
