package com.rpg;

import com.rpg.controller.GameController;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== GENERATEUR DE PERSONNAGES RPG ===");
        GameController controller = new GameController();
        controller.start();
    }
}
