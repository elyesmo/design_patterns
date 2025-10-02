package com.rpg.view;

import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Creer un personnage");
        System.out.println("2. Ajouter des capacites");
        System.out.println("3. Afficher personnages");
        System.out.println("4. Creer une equipe");
        System.out.println("5. Combat");
        System.out.println("6. Sauvegarder");
        System.out.println("7. Charger");
        System.out.println("0. Quitter");
        System.out.print("Choix (0-7): ");
    }

    public String getUserInput() {
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public String askInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public int askIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt + ": ");
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrez un nombre valide");
            }
        }
    }
}
