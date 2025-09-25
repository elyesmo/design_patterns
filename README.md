# RPG Character Generator

Ce projet implémente un générateur de personnages pour jeu de rôle utilisant les design patterns suivants :

## Design Patterns utilisés

- **Builder Pattern** : Construction de personnages étape par étape
- **Decorator Pattern** : Ajout dynamique de capacités spéciales
- **Singleton Pattern** : Configuration globale du jeu
- **DAO Pattern** : Persistance des personnages
- **Collections** : Gestion des groupes de personnages
- **Généricité** : DAO générique

## Structure du projet

```
src/main/java/rpg/
├── builder/
│   └── CharacterBuilder.java
├── core/
│   ├── Character.java
│   └── Party.java
├── decorator/
│   ├── CharacterDecorator.java
│   ├── Invisibility.java
│   ├── FireResistance.java
│   └── Telepathy.java
├── dao/
│   ├── DAO.java
│   └── CharacterDAO.java
├── settings/
│   └── GameSettings.java
└── main/
    └── Main.java
```

## Compilation et exécution

```bash
javac -cp src src/main/java/rpg/main/Main.java
java -cp src rpg.main.Main
```

## Fonctionnalités

- Création de personnages avec attributs (force, agilité, intelligence)
- Ajout de capacités spéciales (invisibilité, résistance au feu, télépathie)
- Validation des règles du jeu
- Sauvegarde et récupération des personnages
- Gestion d'équipes de personnages
- Tri par puissance ou nom
- Simulation de combats
