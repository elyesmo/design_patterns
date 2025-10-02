# 🎮 Générateur de Personnages RPG - Édition Ultime

> 🌟 **Version Améliorée** avec affichage coloré, combats d'équipes, stratégies IA, niveaux et statistiques détaillées !

Application Java complète de création et gestion de personnages RPG intégrant **10 design patterns**, un système de combat avancé avec stratégies, et une interface console immersive.

---

## 📋 Table des matières

- [🎯 Aperçu Rapide](#-aperçu-rapide)
- [✨ Fonctionnalités](#-fonctionnalités)
- [🎨 Design Patterns](#-design-patterns-implémentés)
- [🚀 Installation](#-installation)
- [📖 Guide d'Utilisation](#-guide-dutilisation)
- [🏗️ Architecture](#-architecture)
- [📊 Captures d'Écran](#-captures-décran)

---

## 🎯 Aperçu Rapide

Cette application démontre l'utilisation professionnelle de **10 design patterns** dans un contexte de jeu RPG:

```
🎮 GAMEPLAY                  🎨 AFFICHAGE
├─ Création personnages      ├─ Interface colorée (ANSI)
├─ Équipes hiérarchiques     ├─ Barres de vie visuelles
├─ Combat au tour par tour   ├─ Tableaux formatés
├─ Stratégies IA             ├─ Animations de combat
├─ Système de niveaux        └─ Statistiques détaillées
└─ Historique rejouable
```

---

## ✨ Fonctionnalités

### 🧙 Gestion des Personnages

- ✅ **Création avec Builder** - Construction fluide avec validation
- ✅ **Capacités dynamiques** (Decorator)
  - Invisibilité (+5 Agilité)
  - Télépathie (+5 Intelligence)
  - Super Force (+8 Force)
  - Régénération (+50 PV)
  - Pouvoir du Feu (+3 Force/Intelligence)
- ✅ **Système de niveaux** - Montée en niveau tous les 100 XP
- ✅ **Persistance DAO** - Sauvegarde/chargement automatique

### 👥 Hiérarchie d'Équipes (Pattern Composite)

```
🛡 Armée "Alliance du Nord" (370 points de puissance)
  ├─ 👥 Équipe "Les Guerriers" (185 points)
  │   ├─ ⚔ Aragorn [Niv.1] (Force:26 Agilité:15)
  │   ├─ ⚔ Legolas [Niv.1] (Force:14 Agilité:20)
  │   └─ ⚔ Gimli [Niv.1] (Force:20 Agilité:10)
  └─ 👥 Équipe "Les Mages" (185 points)
      ├─ ⚔ Gandalf [Niv.1] (Intelligence:20)
      └─ ⚔ Saruman [Niv.1] (Intelligence:18)
```

### ⚔️ Système de Combat Multi-Niveaux

#### 🎯 Combat Simple
Affrontement rapide entre deux personnages

#### 🎮 Combat Avancé
- Tour par tour avec choix d'actions
- 3 types d'actions: Attaquer / Défendre / Utiliser capacité
- Gestion dynamique des PV avec barres visuelles

#### 🛡 Combat d'Équipes (NOUVEAU!)
- **Pattern Strategy** - 3 stratégies de combat IA:
  - **⚔ Agressive** - Attaque constamment, capacités offensives
  - **🛡 Défensive** - Privilégie survie et régénération
  - **⚖ Équilibrée** - S'adapte dynamiquement à la situation
- Affichage en temps réel de l'état des équipes
- Statistiques détaillées post-combat
- Système d'expérience pour les survivants

### 📊 Affichage Amélioré

#### Barres de Vie Colorées
```
❤  Santé: ████████████████░░░░ 80/100 PV (80%)
```

#### Interface avec Couleurs ANSI
- 🟢 Vert pour les succès
- 🔴 Rouge pour les erreurs
- 🟡 Jaune pour les avertissements
- 🔵 Cyan pour l'information
- Tableaux et bordures décoratives

#### Statistiques de Combat
```
📊 STATISTIQUES DU COMBAT
  Durée du combat: 45 secondes
  Total d'actions: 24

  Actions par combattant:
    • Aragorn            : 8 actions (33%)
    • Legolas            : 7 actions (29%)
    • Gimli              : 6 actions (25%)
    • Gandalf            : 3 actions (13%)

  K.O. (ordre):
    1. Gimli
    2. Legolas
```

### 🎯 Observation et Logging (Pattern Observer)

- ✅ **Journal de combat** horodaté
- ✅ Notifications automatiques des changements d'équipes
- ✅ Historique complet rejouable

---

## 🎨 Design Patterns Implémentés

| # | Pattern | Localisation | Usage | Difficulté |
|---|---------|-------------|-------|-----------|
| 1 | **Builder** | `builder/CharacterBuilder` | Construction fluide de personnages | ⭐⭐ |
| 2 | **Singleton** | `singleton/GameSettings` | Configuration globale unique | ⭐ |
| 3 | **DAO** | `dao/FileCharacterDAO` | Persistance des données | ⭐⭐ |
| 4 | **Decorator** | `decorator/AbilityDecorator` | Ajout dynamique de capacités | ⭐⭐⭐ |
| 5 | **Chain of Responsibility** | `validator/*` | Validation en chaîne | ⭐⭐⭐ |
| 6 | **Command** | `command/*` | Actions de combat | ⭐⭐ |
| 7 | **Composite** | `composite/*` | Hiérarchie équipes/armées | ⭐⭐⭐⭐ |
| 8 | **Observer** | `observer/*` | Notifications d'événements | ⭐⭐⭐ |
| 9 | **Strategy** | `strategy/*` | Stratégies de combat IA | ⭐⭐⭐ |
| 10 | **MVC** | `model/`, `view/`, `controller/` | Architecture globale | ⭐⭐⭐⭐ |

### 📁 Structure Complète du Projet

```
src/main/java/com/rpg/
├── 🏗️ builder/
│   └── CharacterBuilder.java          # Construction fluide
├── ⚔️ command/
│   ├── Command.java                   # Interface
│   ├── AttackCommand.java             # Attaque
│   ├── DefendCommand.java             # Défense
│   ├── UseAbilityCommand.java         # Capacités
│   └── CommandHistory.java            # Historique
├── 🎯 combat/
│   ├── TeamBattle.java                # Combats d'équipes
│   └── CombatStatistics.java          # Stats détaillées
├── 🌳 composite/
│   ├── TeamComponent.java             # Interface Composite
│   ├── CharacterLeaf.java             # Feuille (personnage)
│   └── TeamComposite.java             # Nœud (équipe/armée)
├── 🎮 controller/
│   └── GameController.java            # Contrôleur MVC
├── 💾 dao/
│   ├── CharacterDAO.java              # Interface
│   └── FileCharacterDAO.java          # Implémentation
├── 🎨 decorator/
│   └── AbilityDecorator.java          # Capacités spéciales
├── 📦 model/
│   ├── Character.java                 # Modèle personnage
│   └── Team.java                      # Ancien modèle
├── 👀 observer/
│   ├── TeamObserver.java              # Interface Observer
│   └── CombatLogger.java              # Logger concret
├── 🎲 strategy/
│   ├── CombatStrategy.java            # Interface Strategy
│   ├── AggressiveStrategy.java        # IA Agressive
│   ├── DefensiveStrategy.java         # IA Défensive
│   └── BalancedStrategy.java          # IA Équilibrée
├── 🔧 singleton/
│   └── GameSettings.java              # Configuration
├── 🎨 util/
│   └── DisplayUtil.java               # Utilitaires d'affichage
├── ✅ validator/
│   ├── CharacterValidator.java        # Base chaîne
│   ├── NameValidator.java             # Validation nom
│   ├── StatsValidator.java            # Validation stats
│   └── AbilitiesValidator.java        # Validation capacités
├── 🖥️ view/
│   └── ConsoleView.java               # Interface console
└── 🚀 Main.java                       # Point d'entrée
```

---

## 🚀 Installation

### Prérequis
- ☕ Java JDK 8 ou supérieur
- 💻 Terminal supportant les couleurs ANSI (Windows 10+, Linux, macOS)

### Compilation & Exécution

#### Option 1: IDE (Recommandé)
```bash
1. Ouvrir le projet dans IntelliJ IDEA / Eclipse / NetBeans
2. Build → Build Project
3. Run → Run 'Main'
```

#### Option 2: Ligne de commande
```bash
# Compiler
cd src/main/java
javac -encoding UTF-8 com/rpg/**/*.java

# Exécuter
java com.rpg.Main
```

#### Option 3: Avec script de compilation
```bash
# Windows
compile.bat
run.bat

# Linux/macOS
chmod +x compile.sh run.sh
./compile.sh
./run.sh
```

---

## 📖 Guide d'Utilisation

### Scénario 1: Créer une équipe et la faire combattre

```
1️⃣ Créer 3 personnages
   → Menu: 1
   → Exemple: Warrior (Force:18, Agilité:12, Intelligence:8)
   
2️⃣ Ajouter des capacités
   → Menu: 2
   → Warrior → Super Force
   
3️⃣ Créer une équipe
   → Menu: 4
   → Nom: "Team Alpha"
   → Indices: 0 1 2
   
4️⃣ Créer une seconde équipe
   → Menu: 4
   → Nom: "Team Beta"
   → Indices: 3 4
   
5️⃣ Lancer un combat d'équipes
   → Menu: 9
   → Équipe 1: 0 (Team Alpha)
   → Équipe 2: 1 (Team Beta)
   → Stratégie 1: Agressive
   → Stratégie 2: Défensive
```

### Scénario 2: Combat avancé avec stratégie

```
1️⃣ Créer 2 personnages puissants
   → Ajoutez plusieurs capacités
   
2️⃣ Lancer combat avancé
   → Menu: 8
   → Choisissez vos actions à chaque tour:
     - Attaque pour infliger des dégâts
     - Défense quand PV bas
     - Capacités pour effets spéciaux
```

### Scénario 3: Créer une armée hiérarchique

```
1️⃣ Créer 6+ personnages
2️⃣ Créer 2 équipes de 3 personnages
3️⃣ Menu: 5 → Créer une armée
4️⃣ Menu: 6 → Voir la hiérarchie complète
```

---

## 🏗️ Architecture

### Flux MVC

```mermaid
┌──────────────┐         ┌──────────────┐         ┌──────────────┐
│              │◄────────│              │◄────────│              │
│     View     │  Input  │  Controller  │  Query  │    Model     │
│   Console    │────────►│     Game     │────────►│  Character   │
│              │  Output │              │  Update │  Team        │
└──────────────┘         └──────────────┘         └──────────────┘
                                │
                                │
                         ┌──────┴──────┐
                         │             │
                    ┌────▼───┐    ┌────▼─────┐
                    │Validator│   │ Commands │
                    │ Chain   │   │ History  │
                    └─────────┘   └──────────┘
```

### Pattern Strategy en Action

```java
// Choix dynamique de stratégie
CombatStrategy strategy = switch(choice) {
    case 1 -> new AggressiveStrategy();
    case 2 -> new DefensiveStrategy();
    case 3 -> new BalancedStrategy();
};

// Exécution intelligente
Command action = strategy.chooseAction(attacker, defender);
action.execute();
```

### Pattern Composite Hiérarchique

```
TeamComponent (Interface)
    ├── CharacterLeaf
    │   └── Character (modèle)
    └── TeamComposite
        ├── Type: "TEAM"
        └── Type: "ARMY"
            └── Contient d'autres TeamComposite ou CharacterLeaf
```

---

## 📊 Captures d'Écran

### Interface Principale

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
    9. ⚔ Combat d'équipes (avec stratégies)
   10. Afficher historique des actions
   11. Rejouer l'historique
   12. Afficher journal de combat

  SYSTÈME:
   13. Sauvegarder
   14. Charger
   15. Paramètres du jeu
    0. Quitter
```

### Combat d'Équipes

```
╔══════════════════════════════════════════════════════════════════════╗
║                    ⚔ COMBAT D'ÉQUIPES ⚔                              ║
╠══════════════════════════════════════════════════════════════════════╣
  Team Alpha [3 combattants]
    Stratégie: ⚔ AGRESSIVE
    Attaque constamment, utilise les capacités offensives

               🆚 CONTRE 🆚

  Team Beta [2 combattants]
    Stratégie: 🛡 DÉFENSIVE
    Se défend souvent, régénère quand nécessaire
╚══════════════════════════════════════════════════════════════════════╝

═══ ROUND 1 ═══

📊 Team Alpha
  1. Warrior [Niv.1] ████████████████████ 100/100 PV (100%)
  2. Mage [Niv.1] ███████████████████░ 95/100 PV (95%)
  3. Ranger [Niv.1] ████████████████████ 100/100 PV (100%)

  → Warrior agit:
Warrior utilise Super Force sur Enemy1!
Coup surpuissant! Enemy1 perd 52 PV
Enemy1 a maintenant 48 PV
```

---

## 🎯 Validation des User Stories

| Epic | US | Description | Statut | Patterns |
|------|-----|-------------|---------|----------|
| **1** | 1.1 | Création personnages | ✅ | Builder |
| **1** | 1.2 | Capacités dynamiques | ✅ | Decorator |
| **1** | 1.3 | Persistance | ✅ | DAO |
| **1** | 1.4 | Groupes hiérarchisés | ✅ | Composite |
| **2** | 2.1 | Règles centralisées | ✅ | Singleton |
| **2** | 2.2 | Commandes de jeu | ✅ | Command |
| **2** | 2.3 | Validation en chaîne | ✅ | Chain |
| **3** | 3.1 | Interface MVC | ✅ | MVC |
| **3** | 3.2 | Visualisation | ✅ | MVC |
| **3** | 3.3 | Notifications | ✅ | Observer |
| **4** | 4.1 | Combat personnages | ✅ | Command |
| **4** | 4.2 | Observation combat | ✅ | Observer |
| **4** | 4.3 | Historique rejouable | ✅ | Command |
| **BONUS** | **5.1** | **Combat d'équipes** | ✅ | **Composite + Strategy** |
| **BONUS** | **5.2** | **Affichage amélioré** | ✅ | **Util** |
| **BONUS** | **5.3** | **Système de niveaux** | ✅ | **Model** |

---

## 🌟 Points Forts du Projet

### 🎯 Design Patterns
- ✅ **10 patterns** parfaitement intégrés
- ✅ Code modulaire et extensible
- ✅ Respect des principes SOLID

### 🎮 Gameplay
- ✅ Combat d'équipes innovant
- ✅ 3 stratégies IA différentes
- ✅ Système de progression (niveaux)
- ✅ Actions variées (attaque, défense, capacités)

### 🎨 Expérience Utilisateur
- ✅ Interface colorée et moderne
- ✅ Barres de vie visuelles
- ✅ Statistiques détaillées
- ✅ Feedback immédiat

### 📚 Qualité du Code
- ✅ Architecture claire
- ✅ Commentaires JavaDoc
- ✅ Gestion des erreurs
- ✅ Code maintenable

---

## 🔮 Améliorations Futures

### Gameplay
- [ ] Mode tournoi avec classement
- [ ] Équipement et inventaire
- [ ] Quêtes et missions
- [ ] Système de crafting

### Technique
- [ ] Interface graphique Swing/JavaFX
- [ ] Sauvegarde équipes/armées
- [ ] Base de données SQL (DAO étendu)
- [ ] Multijoueur réseau

### IA
- [ ] Stratégies adaptatives (Machine Learning)
- [ ] Comportements personnalisés par classe
- [ ] Analyse prédictive des actions

---

## 📄 Licence & Auteur

**Projet Éducatif** - Démonstration des Design Patterns en Java  
📚 Pour apprentissage et portfolio  
⭐ N'hésitez pas à forker et améliorer !

---

## 🙏 Remerciements

Ce projet utilise uniquement la bibliothèque standard Java et démontre comment créer une application riche sans dépendances externes.

**Patterns de référence**: Gang of Four (GoF) Design Patterns  
**Inspiration**: Jeux RPG classiques (Final Fantasy, D&D)

---

<div align="center">

### ⭐ Si ce projet vous a plu, n'hésitez pas à lui donner une étoile! ⭐

Made with ❤️ and ☕ 

</div> 