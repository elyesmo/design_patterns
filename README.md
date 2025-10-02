# 🎮 Générateur de Personnages RPG - Version Étendue

Application Java complète de création et gestion de personnages RPG avec système de combat avancé, intégrant 9 design patterns.

## 📋 Table des matières

- [Fonctionnalités](#-fonctionnalités)
- [Design Patterns](#-design-patterns-implémentés)
- [Structure du projet](#-structure-du-projet)
- [Installation](#-installation)
- [Utilisation](#-utilisation)
- [Architecture](#-architecture)

## ✨ Fonctionnalités

### 🧙 Gestion des personnages
- ✅ Création de personnages avec caractéristiques personnalisables (Force, Agilité, Intelligence)
- ✅ Ajout dynamique de capacités spéciales (Invisibilité, Télépathie, Super Force, Régénération, Pouvoir du Feu)
- ✅ Validation automatique des personnages (nom unique, points de stats, nombre de capacités)
- ✅ Sauvegarde et chargement depuis fichier

### 👥 Gestion des équipes (Pattern Composite)
- ✅ Création d'équipes de personnages
- ✅ Création d'armées composées de plusieurs équipes
- ✅ Hiérarchie complète : Personnage → Équipe → Armée
- ✅ Affichage hiérarchique avec puissance totale
- ✅ Limites configurables (personnages par équipe, équipes par armée)

### ⚔️ Système de combat
- ✅ **Combat simple** : Affrontement direct entre deux personnages
- ✅ **Combat avancé** : Combat au tour par tour avec actions multiples
  - Attaque
  - Défense
  - Utilisation de capacités spéciales
- ✅ Journal de combat automatique avec timestamps
- ✅ Historique des actions rejouable

### 🎯 Actions de combat (Pattern Command)
- `AttackCommand` : Attaque standard infligeant des dégâts
- `DefendCommand` : Position défensive avec bonus de PV
- `UseAbilityCommand` : Utilisation de capacités spéciales

### 📊 Observation et logging (Pattern Observer)
- ✅ Notifications automatiques lors des modifications d'équipes
- ✅ Journal de combat détaillé avec horodatage
- ✅ Historique rejouable de toutes les actions

### ⚙️ Configuration
- ✅ Paramètres globaux modifiables en temps réel
- ✅ Points de stats maximum
- ✅ Nombre max de personnages par équipe
- ✅ Nombre max de capacités par personnage
- ✅ Nombre max d'équipes par armée

## 🎨 Design Patterns implémentés

| Pattern | Localisation | Description |
|---------|-------------|-------------|
| **Builder** | `builder/CharacterBuilder` | Construction fluide de personnages avec paramètres optionnels |
| **Singleton** | `singleton/GameSettings` | Instance unique de configuration globale du jeu |
| **DAO** | `dao/FileCharacterDAO` | Abstraction de la persistance des personnages |
| **Decorator** | `decorator/AbilityDecorator` | Ajout dynamique de capacités aux personnages |
| **Chain of Responsibility** | `validator/*` | Validation en chaîne (nom → stats → capacités) |
| **Command** | `command/*` | Encapsulation des actions (attaque, défense, capacités) |
| **Composite** | `composite/*` | Hiérarchie Personnage/Équipe/Armée |
| **Observer** | `observer/*` | Notification des changements d'équipes et événements de combat |
| **MVC** | `model/`, `view/`, `controller/` | Séparation des préoccupations |

## 📁 Structure du projet

```
src/main/java/com/rpg/
├── builder/
│   └── CharacterBuilder.java          # Pattern Builder
├── command/
│   ├── Command.java                   # Interface Command
│   ├── AttackCommand.java             # Commande d'attaque
│   ├── DefendCommand.java             # Commande de défense
│   ├── UseAbilityCommand.java         # Commande d'utilisation de capacité
│   └── CommandHistory.java            # Historique rejouable
├── composite/
│   ├── TeamComponent.java             # Interface Composite
│   ├── CharacterLeaf.java             # Feuille (personnage)
│   └── TeamComposite.java             # Composite (équipe/armée)
├── controller/
│   └── GameController.java            # Contrôleur MVC
├── dao/
│   ├── CharacterDAO.java              # Interface DAO
│   └── FileCharacterDAO.java          # Implémentation DAO
├── decorator/
│   └── AbilityDecorator.java          # Pattern Decorator
├── model/
│   ├── Character.java                 # Modèle personnage
│   └── Team.java                      # Ancien modèle équipe
├── observer/
│   ├── TeamObserver.java              # Interface Observer
│   └── CombatLogger.java              # Observateur de combat
├── singleton/
│   └── GameSettings.java              # Pattern Singleton
├── validator/
│   ├── CharacterValidator.java        # Base Chain of Responsibility
│   ├── NameValidator.java             # Validation du nom
│   ├── StatsValidator.java            # Validation des stats
│   └── AbilitiesValidator.java        # Validation des capacités
├── view/
│   └── ConsoleView.java               # Vue console
└── Main.java                          # Point d'entrée
```

## 🚀 Installation

### Prérequis
- Java JDK 8 ou supérieur
- IDE Java (IntelliJ IDEA, Eclipse, etc.) ou ligne de commande

### Compilation

```bash
# Depuis le répertoire racine
cd src/main/java
javac com/rpg/**/*.java
```

### Exécution

```bash
java com.rpg.Main
```

## 📖 Utilisation

### Menu principal

```
╔═══════════════════════════════════════╗
║   MENU PRINCIPAL - RPG GENERATOR      ║
╚═══════════════════════════════════════╝
  PERSONNAGES:
    1. Créer un personnage
    2. Ajouter des capacités
    3. Afficher personnages

  ÉQUIPES & ARMÉES:
    4. Créer une équipe
    5. Créer une armée
    6. Afficher équipes/armées

  COMBAT:
    7. Combat simple
    8. Combat avancé (actions multiples)
    9. Afficher historique des actions
   10. Rejouer l'historique
   11. Afficher journal de combat

  SYSTÈME:
   12. Sauvegarder
   13. Charger
   14. Paramètres du jeu
    0. Quitter
```

### Exemple d'utilisation

#### 1. Créer des personnages

```
1. Créer un personnage
Nom: Aragorn
Force: 18
Agilité: 15
Intelligence: 12
✅ Personnage créé: Aragorn (Force:18 Agilité:15 Intelligence:12 100 PV)
```

#### 2. Ajouter des capacités

```
2. Ajouter des capacités
Index du personnage: 0
📚 Capacités disponibles:
  1. Invisibilité (+5 Agilité)
  2. Télépathie (+5 Intelligence)
  3. Super Force (+8 Force)
  4. Régénération (+50 PV)
  5. Pouvoir du Feu (+3 Force, +3 Intelligence)
Choix: 3
✅ Capacité ajoutée
```

#### 3. Créer une équipe

```
4. Créer une équipe
Nom de l'équipe: Les Guerriers
Entrez les indices: 0 1 2
✅ Équipe créée: Équipe Les Guerriers (3 membres, Puissance: 185)
```

#### 4. Créer une armée

```
5. Créer une armée
Nom de l'armée: Alliance du Nord
Entrez les indices des équipes: 0 1
✅ Armée créée: Armée Alliance du Nord (6 membres, Puissance: 370)
```

#### 5. Affichage hiérarchique

```
6. Afficher équipes/armées

👥 ÉQUIPES:
  👥 Les Guerriers (TEAM) - Puissance totale: 185 - Membres: 3
    ⚔ Aragorn (Force:26 Agilité:15 Intelligence:12 100 PV) - Puissance: 68
    ⚔ Legolas (Force:14 Agilité:20 Intelligence:16 150 PV) - Puissance: 65
    ⚔ Gimli (Force:20 Agilité:10 Intelligence:8 100 PV) - Puissance: 52

🛡 ARMÉES:
  🛡 Alliance du Nord (ARMY) - Puissance totale: 370 - Membres: 6
    👥 Les Guerriers (TEAM) - Puissance: 185
      ⚔ Aragorn - Puissance: 68
      ⚔ Legolas - Puissance: 65
      ⚔ Gimli - Puissance: 52
    👥 Les Mages (TEAM) - Puissance: 185
      ⚔ Gandalf - Puissance: 95
      ⚔ Saruman - Puissance: 90
```

#### 6. Combat avancé

```
8. Combat avancé
Personnage 1: 0
Personnage 2: 1

--- ROUND 1 ---
Aragorn: 100 PV
Legolas: 150 PV

Aragorn - Actions:
  1. Attaquer
  2. Défendre
  3. Utiliser une capacité
Choix: 3

Capacités de Aragorn:
  1. Super Force
Choix capacité: 1
Aragorn utilise Super Force sur Legolas!
Coup surpuissant! Legolas perd 52 PV
Legolas a maintenant 98 PV
```

## 🏗️ Architecture

### Flux de données (MVC)

```
┌──────────┐        ┌──────────────┐        ┌─────────┐
│   View   │───────▶│  Controller  │───────▶│  Model  │
│ Console  │◀───────│     Game     │◀───────│Character│
└──────────┘        └──────────────┘        └─────────┘
                           │
                           │
                    ┌──────┴──────┐
                    │             │
              ┌─────▼────┐  ┌────▼─────┐
              │ Validator│  │ Commands │
              │  Chain   │  │ History  │
              └──────────┘  └──────────┘
```

### Hiérarchie Composite

```
TeamComponent (interface)
    │
    ├── CharacterLeaf (personnage individuel)
    │
    └── TeamComposite
            ├── Type: TEAM (équipe de personnages)
            └── Type: ARMY (armée d'équipes)
```

### Pattern Observer

```
TeamComposite (Observable)
    │
    └── notifie
         │
         └── CombatLogger (Observer)
                 │
                 └── enregistre tous les événements
```

## 🎯 Améliorations par rapport au MVP initial

### ✅ Gestion des équipes (Composite)
- Hiérarchie complète avec armées
- Affichage en arborescence
- Calcul automatique de la puissance totale
- Support de structures illimitées

### ✅ Système d'observation (Observer)
- Notifications automatiques des changements
- Journal de combat horodaté
- Traçabilité complète des événements

### ✅ Historique des commandes
- Enregistrement de toutes les actions
- Possibilité de rejouer l'historique
- Affichage formaté des actions

### ✅ Combat avancé
- Tour par tour avec choix d'actions
- 3 types d'actions (attaquer, défendre, capacités)
- Gestion dynamique des PV
- Détection automatique du vainqueur

### ✅ Configuration dynamique
- Modification des paramètres en temps réel
- Validation automatique selon les règles
- Paramètres persistants durant la session

### ✅ Interface améliorée
- Menu structuré et intuitif
- Emojis pour meilleure lisibilité
- Messages de confirmation/erreur clairs
- Affichage hiérarchique avec indentation

## 📝 Validation des User Stories

| US | Description | Statut |
|----|-------------|--------|
| US 1.1 | Création personnage (Builder) | ✅ |
| US 1.2 | Capacités dynamiques (Decorator) | ✅ |
| US 1.3 | Persistance (DAO) | ✅ |
| US 1.4 | Groupes hiérarchisés (Composite) | ✅ |
| US 2.1 | Règles centralisées (Singleton) | ✅ |
| US 2.2 | Commandes de jeu (Command) | ✅ |
| US 2.3 | Validation en chaîne (Chain) | ✅ |
| US 3.1 | Interface MVC | ✅ |
| US 3.2 | Visualisation et tri | ✅ |
| US 3.3 | Notifications (Observer) | ✅ |
| US 4.1 | Combat entre personnages | ✅ |
| US 4.2 | Observation du combat | ✅ |
| US 4.3 | Historique rejouable | ✅ |

## 🔮 Améliorations futures possibles

- [ ] Persistance des équipes et armées
- [ ] Stratégies de combat différentes (Strategy pattern)
- [ ] Interface graphique Swing
- [ ] Statistiques de combat
- [ ] Système d'expérience et de niveau
- [ ] Équipement et inventaire
- [ ] Effets de statut (poison, paralysie, etc.)
- [ ] IA pour les combats automatiques

## 📄 Licence

Projet éducatif - Design Patterns en Java

## 👥 Auteur

Projet de démonstration des Design Patterns Java 