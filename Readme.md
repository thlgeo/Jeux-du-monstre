# Commande pour lancer l'application

```bash
java --module-path <chemin vers le dossier lib de javafx> --add-modules javafx.controls,javafx.fxml -jar G1_SAE3A.jar
```

# Règles du jeu
Le jeu se déroule en tour par tour, alternant entre un chasseur et un monstre, dans un labyrinthe  
dont seulement le monstre connait les informations.  
### Règles Monstre :
Le monstre peut se déplacer sur une case autour de lui.  
Le monstre ne peut pas se déplacer hors des limites du labyrinthe, et ne peut pas se déplacer à plus d'une case de distance.  
Le monstre ne peut pas se déplacer sur un mur (représenté par une case entièrement noire).  
### Règles Chasseur :
Le chasseur voit un terrain vide, et doit tirer sur le monstre.  
Pour cela, il peut tirer sur n'importe quel case du jeu, et apprend si la case touchée est :  

- Un mur, dans tel cas le chasseur sait dorénavant que cette case est un mur.  
- un endroit vide, dans tel cas le chasseur sait dorénavant que cette case est vide.
- Un endroit où le monstre est déjà allé, dans tel cas, le chasseur voit à quel tour le monstre a occupé cette case.

S'il tire sur la sortie du labyrinthe, il n'apprendra pas que cette case est la sortie du labyrinthe.
# But du jeu
Le but du jeu pour les deux joueurs est de gagner, mais ils gagnent tout-deux de manière différente.  
- Le chasseur gagne en tirant sur la case où le monstre se trouve actuellement.
- Le monstre gagne en atteignant la sortie du labyrinthe sans se faire tirer dessus par le chasseur.