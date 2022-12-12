package me.arrhioui;

import me.arrhioui.entity.Compte;
import me.arrhioui.service.IMetier;
import me.arrhioui.service.MetierImpl;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        new Application().start();
    }
    private void start() {
        System.out.println("Starting Application .....");
        IMetier metier=new MetierImpl();
        Scanner clavier=new Scanner(System.in);
        System.out.print("Code:");Long code=clavier.nextLong();
        System.out.print("Solde:");double solde=clavier.nextDouble();
        metier.addCompte(new Compte(code,solde));
        while (true){
            try {
                System.out.println("=========================================");
                System.out.println(metier.getCompte(code).toString());
                System.out.print("Type Opération:");String type=clavier.next();
                if(type.equals("q")) break;
                System.out.print("Montant:");double montant=clavier.nextDouble();
                if(type.equalsIgnoreCase("v")) metier.verser(code,montant);
                else if(type.equalsIgnoreCase("r")) metier.retirer(code,montant);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
