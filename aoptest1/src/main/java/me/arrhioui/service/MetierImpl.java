package me.arrhioui.service;

import me.arrhioui.entity.Compte;

import java.util.HashMap;
import java.util.Map;

public class MetierImpl implements IMetier {
    private Map<Long, Compte> comptes=new HashMap<>();
    @Override
    public void addCompte(Compte cp) {
        comptes.put(cp.getCode(),cp);
    }
    @Override
    public void verser(Long code, double mt) {
        Compte cp=comptes.get(code);
        cp.setSolde(cp.getSolde()+mt);
    }
    @Override
    public void retirer(Long code, double mt) {
        Compte cp=comptes.get(code);
        cp.setSolde(cp.getSolde()-mt);
    }
    @Override
    public Compte getCompte(Long code) {
        return comptes.get(code);
    }
}
