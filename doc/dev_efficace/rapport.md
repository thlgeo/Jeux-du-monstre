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