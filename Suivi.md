## Semaine du 6/11/2023

#### Nathan DESMEE

- Ajout de la fonctionnalité de modifier la largeur et la hauteur du labyrinthe dans la view
- Ajout de la fonctionnalité de choisir si il y a des déplacements diagonaux ou non dans la view
- Ajout de la fonctionnalité de choisir si il y a du brouillard ou non dans la view
- Modification du système de notification à la view pour se concentrer sur une seule méthode de notification plutôt que plusieurs
- Mise en place du système de brouillard dans la view du monstre
- Corrections de bugs liés au brouillard du monstre dans la view et dans le modèle

### Théo Lenglart

- Ajout du brouillard pour le monstre
- Corrections de bug pour la méthode canMove du monstre
- Modification de la classe de test du monstre


### Armand SADY  

- Implémentation de l'algorithme de Prim afin de créer des labyrinthes aléatoires  
- Refactoring des fonctionnalités de création de labyrinthe vers la classe Maze


### Valentin THUILLIER

- Aide pour l'implémentation de l'agorithm de Prim
- Ajout des Hunter/Monster Random
- Modification de l'UML et de l'analyse

## Break

```agsl
Vu notre avance sur les dates de rendu du projet nous 
n'avons pas eu besoin de travailler sur le projet entre deux
```

## Semaine du 4/12/2023

### Nathan DESMEE

- Correction du bug qui fait que les cases sont vues en brouillards après avoir été vues par le monstre et par le hunter
- Ajout d'un nouveau ModelMain qui permet de jouer avec un monstre en intelligence artificielle
- Correction d'un bug d'affichage lié au javafx
- Rassemblement des ModelMain en un seul pour décide de jouer avec des IA ou non
- Ajout d'options de paramétrage pour jouer avec des IA

### Valentin THUILLIER

- Introduction de l'algorithme A* pour IAMonster
### Armand SADY

- Correction bug de taille de la maze
- Changement de l'affichage si on joue contre une IA ou contre un Humain
- Ajout du paramètre "Generation / Import" pour choisir le labyrinthe
- Refactoring de la classe Maze -> MazeFactory & correction des bugs que cela a engendré

### Théo Lenglart

- Implémentation d'une IA random pour le Monster
- Implémentation d'une IA random pour le Hunter mais lorsqu'il trouve une cellule ou le monstre est passé, réduit sa portée de tir à (tour actuel - tour où le monstre est passé)
