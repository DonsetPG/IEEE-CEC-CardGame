## TODO : 

- implémentation de R2D2 

## DONE : 

- implémentation DQL 

## Remarques : 

Sur l'action space variable : On le garde fixe, et de taille maximal. On applique ensuite un masque de possibilité sur le vecteur 
d'output, qu'on renormalise ensuite si ce sont des probabilités. On peut alors passer outre le fait que certaines actions ne soient
pas toujours disponibles. 
Pour les rewards, on renvoie toujours 0 sauf à la fin (1/0/-1). 
Pour les actions : on peut considérer des compositions d'actions élémentaires plutôt que des actions compliquées. Par exemple, 
plutôt que SUMMON 2 ou 2 ATTACK 1 on peut considérer les suites d'actions SUMMON puis 2 ou alors ATTACK puis 2 puis 1. 
