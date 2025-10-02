package com.rpg.view;

import java.util.Scanner;

public class ConsoleView {
    private Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║      MENU PRINCIPAL - RPG GENERATOR   ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("  PERSONNAGES:");
        System.out.println("    1. Créer un personnage");
        System.out.println("    2. Ajouter des capacités");
        System.out.println("    3. Afficher personnages");
        System.out.println("\n  ÉQUIPES & ARMÉES:");
        System.out.println("    4. Créer une équipe");
        System.out.println("    5. Créer une armée");
        System.out.println("    6. Afficher équipes/armées");
        System.out.println("\n  COMBAT:");
        System.out.println("    7. Combat simple");
        System.out.println("    8. Combat avancé (actions multiples)");
        System.out.println("    9. Afficher historique des actions");
        System.out.println("   10. Rejouer l'historique");
        System.out.println("   11. Afficher journal de combat");
        System.out.println("\n  SYSTÈME:");
        System.out.println("   12. Sauvegarder");
        System.out.println("   13. Charger");
        System.out.println("   14. Paramètres du jeu");
        System.out.println("    0. Quitter");
        System.out.print("\nChoix: ");
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
