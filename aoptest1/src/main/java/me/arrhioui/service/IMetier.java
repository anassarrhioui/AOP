package me.arrhioui.service;

import me.arrhioui.entity.Compte;

public interface IMetier {
    void addCompte(Compte cp);
    void verser(Long code,double mt);
    void retirer(Long code,double mt);
    Compte getCompte(Long code);
}
