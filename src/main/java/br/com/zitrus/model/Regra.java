package br.com.zitrus.model;

import br.com.zitrus.enums.SexoEnum;

import javax.persistence.*;

@Entity
public class Regra {

    @Id
    private Long id;
    @ManyToOne
    private Procedimento procedimento;
    private int idade;
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    public Regra(Long id, Procedimento procedimento, int idade, SexoEnum sexo) {
        this.id = id;
        this.procedimento = procedimento;
        this.idade = idade;
        this.sexo = sexo;
    }

    public Regra() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }
}
