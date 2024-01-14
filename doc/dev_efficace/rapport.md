# Développement efficace SAE3.02 G1

Pour réaliser ces algorithmes, nous avons utlisé un tableau à deux dimensions de **Cell** représentant le maze qui, lors de son initialisation, est un tableau de cellule vide rempli au fur et à mesure de la partie pour le hunter ou un tableau rempli avec les bonnes cellules pour le monster.

L'usage d'un tableau à deux dimensions fut un choix logique, car il permet un accès direct à la case correspondante à la coordonnée voulue, ce qui en améliore considérablement la rapidité d'exécution du programme. D'autre part, nous aurions pu utiliser une liste de **Cell** mais cela aurait considérablement réduit l'efficacité programme car il aurait fallu parcourir toute la liste jusqu'à atteindre la cellule voulue.

Nous avons utilisé cette structure de données pour les classes **IAHunterRandom.java**, **IAHunter.java**, **IAMonster.java** et **DFSMonster.java**.

## IA Hunter

### 1. Aléatoire
L'algorithme utilisé est personnel. Il se trouve dans la classe **IAHunterRandom.java** et voici le pseudo-code associé :

```
Initialisation de la variable coord (ICoordinate) qui sera retournée à la fin
Initialisation des variables row et col (int)
faire
    row = une valeur aléatoire compris entre 0 et la taille maximale d'une ligne du tableau
    col = une valeur aléatoire compris entre 0 et la taille maximale d'une colonne du tableau
    coord = instanciation d'une nouvelle Coordinate de paramètre row et col
tant que coord est dans le tableau, et que la cellule à la coordonnée de coord est un mur, donc déjà découverte
retourne coord
```

### 2. Aléatoire amélioré

L'algorithme utilisé est personnel. Il se trouve dans la classe **IAHunter.java** et voici le pseudo-code associé :

Algorithme principal :
```
Initialisation de la variable coord (ICoordinate) qui sera retournée à la fin
si lastPositionMonster n'est pas null
    around (liste de cellule) = around(lastPositionMonster.getCoord())
    faire
        coord = un élément aléatoire retiré de around
    tant que la cellule à la coordonnée de coord est un mur, donc déjà découverte
sinon
    faire
        coord = nouvelle instance de Coordinate de paramètre, entier aléatoire compris entre 0 et taille maximale d'une ligne, et entier aléatoire compris entre 0 et taille maximale d'une colonne
    tant que la cellule à la coordonnée de coord est un mur, donc déjà découverte
retourne coord
```
La méthode around prend en paramètre une coordonnée et, en fonction de la portée qui est égale au tour actuel - le dernier tour où le monstre a été trouvé (= toutes les cellules où le monstre pourrait se trouver), retourne une liste de **Cell** autour de la coordonnée d'une portée définie précédemment.

## IA Monster

### 1. Algorithme A*

### 2. Algorithme DFS

## Génération du labyrinthe

### 1. Algorithme de Prim

Pour la génération du labyrinthe, nous avons utilisé l'algorithme de Prim.  
Il réalise un labyrinthe parfait, c'est-à-dire qu'il n'y a qu'un seul chemin entre deux points du labyrinthe.
Pour cela, il prend un labyrinthe rempli de murs et créé un tableau de boolean de même taille afin de savoir quelles cellules sont déjà explorées, et prend comme cellule initiale une cellule aléatoire.  
Pour créer le labyrinthe, on calcule la "frontière" de la cellule actuelle, autrement dit, les cellules adjacentes à deux de distances qui ne sont pas encore explorées.  
On choisit ensuite une cellule de la "frontière" aléatoirement, on la marque comme explorée et on la relie à la cellule actuelle. En supprimant les murs entre les deux cellules (et sur les cellules).
On répète ensuite l'opération avec une nouvelle cellule de la frontière jusqu'à ce que la frontière soit vide, et donc qu'on ait exploré toutes les cellules du labyrinthe.  
Pour rendre le labyrinthe plus jouable, après la génération d'un labyrinthe aléatoire, on supprime des murs aléatoires en vérifiant qu'ils ne génèrent pas de chemin isolé.  
De cette manière, les labyrinthes générés sont toujours jouables, et les algorithmes de recherche de chemin marchent dans tous les cas.

Cet algorithme est implémenté dans la classe **MazeFactory.java**, avec cette manière de faire, nous pouvons créer un labyrinthe de n'importe quel taille, qu'il soit carré ou rectangulaire, même si nous avons limité la génération à un labyrinthe de taille 16*16 car au-delà, le labyrinthe est trop grand pour être affiché sur l'écran.