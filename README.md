# IEEE-CEC-CardGame

C'est le concours numéro 9 de cette page : http://cec2019.org/programs/competitions.html#cec-07 

Lien du jeux en particulier : https://github.com/acatai/Strategy-Card-Game-AI-Competition

Lien pour tester le code : https://www.codingame.com/contribute/view/162759566f5a132f64b4de78ed637a2f309a

Le lien du jeux offre aussi le referee en java, utile pour les simulations. 

La deadline est le 19 Mai 2019. 

# _____________________________________________________________________________________________________________________

# Principe du jeu : 

Globalement le jeu se déroule en 2 phases: une phase de draft, puis une phase de gameplay. 

## Phase de draft: 

Le jeu est composé de 160 cartes (disponibles ici : https://files.codingame.com/legends-of-code-and-magic/index_v3.html) 

La majorité sont des cartes créatures, mais on trouve aussi des cartes objets. Elles possèdent toutes une attaque, une défense, et un cout en mana. En plus, certaines possèdes des capacités spéciales (cf règles du jeu). 

Le draft se décompose en 30 tours (pour constituer un deck de 30 cartes). À chaque tour, le jeu propose aux deux joueurs 3 cartes, tirées aléatoirement parmi les 160 cartes de bases. Chacun des joueurs récupère alors une des cartes de son choix. En revanche, il n'est pas possible de connaître la carte récupérée par l'adversaire. 
On marche alors comme ça pendant 30 tours, puis la phase de gameplay peut commencer. 

## Phase de gameplay : 

Le jeu se déroule plus ou moins comme les autres jeux de cartes (Magic, Heartstone, etc). On commence avec 1 de Mana, et on en gagne un de plus à chaque tour. On pose alors des cartes, et on se tape avec l'autre. (Cf règles du jeu pour plus de détails, ou regarder des replays de game pour bien comprendre). On trouve quelques particularités dans ce jeu: le plateau se compose de deux couloirs, le joueur 2 commence avec du mana en plus, et finalement il existe un système de runes qui relie la vie restante et nombre de cartes à piocher. 

# _____________________________________________________________________________________________________________________

# Plan d'attaque : 

## Actuellement : 

On possède un code qui fonctionne de la manière suivante : 

Pour le draft : prends à chaque fois la carte la plus forte des trois, en suivant un tableau où chaque carte possède son propre score. 

Pour le gameplay : Pour chaque tour, on simule qu'elles sont les meilleures cartes à poser. Puis, on simule toutes les combinaisons d'attaques possibles, on garde les 5 meilleurs, avec ces 5 meilleurs ; on simule les combinaisons possibles de l'adversaire, et on garde enfin notre meilleure combinaison après avoir simulé les coups de l'adversaire. 

## Ce qu'il faudrait faire : 

##

Draft phase : Les scores marchent bien, mais y'a globalement moyen de faire beaucoup mieux : prendre en compte les interactions possibles entre les cartes, prendre en compte ce qui a déjà été tiré, prendre en compte avec des statistiques ce que l'adversaire a pu choisir, etc. Pas mal de méthodes possible aussi : machine learning, reinforcement learning, monte-carlo et algo génétique. Globalement le principe va être de simuler des milliards et des milliards de parties pour comprendre quelles sont les meilleures cartes, comment elles fonctionnent entre elles, etc. 

## 

Gameplay : 

Le but ici serait de réaliser à faire fonctionner du Reinforcement learning sur un jeu qui : n'est pas à information parfaite (on ne connaît pas les cartes de l'autre), n'est pas vraiment un zéro sum game (on a beau avoir plus de vie que l'autre, les cartes comptent pour beaucoup), n'est pas un environnement déjà travaillé, demande à la fois des stratégies sur le long terme et d'autre beaucoup plus rapide. Bref c'est un sujet complexe mais très intéressant. Réussir à mettre en place du reinforcement learning performent dessus est ultra stylé. 
On pourrait d'ailleurs en profiter pour aussi recoder un interface du jeu (facile on a le github du jeu), pour pouvoir nous tester nous même contre l'AI.






