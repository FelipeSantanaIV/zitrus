package br.com.zitrus.model;

import javax.persistence.*;

@Entity
@Table(name = "procedimentos")
public class Procedimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public Procedimento(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Procedimento() {

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
