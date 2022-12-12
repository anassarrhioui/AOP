package me.arrhioui.entity;

public class Compte {

    public Compte() {
    }

    public Compte(Long code, double solde) {
        this.code = code;
        this.solde = solde;
    }

    private Long code;
    private double solde;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "code=" + code +
                ", solde=" + solde +
                '}';
    }
}
