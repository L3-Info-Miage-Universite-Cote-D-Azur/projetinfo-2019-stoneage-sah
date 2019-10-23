# projetinfo-2019-stoneage-sah
projetinfo-2019-stoneage-sah created by GitHub Classroom

Première Itération:
Nous souhaitons commencer le développement du jeu en programmant les entités suivantes:
-1 joueur.
-1 figurine.
-2 zones (celle de chasse ainsi qu'une zone de ressource quelconque).
-1 dé équilibré allant de 1 à 6.
-le début d'un inventaire pour le joueur, contenant les ressources et la nourriture.
-une phase de jeu obligeant le joueur à poser la figurine dans une zone.
-la gestion des ressources recoltées.
-une "fin de jeu" après l'obtention de la ressource.

Seconde Itération:
Nous souhaitons continuer le développement en introduisant les fonctionnalités suivantes en plus des précédentes:
-Implémentation des 5 figurines de début de partie.
-Implémentation des zones de récolte des ressources manquantes.
-Implémentation de la création de dés, ainsi, à chaque fois qu'un nombre de figurine sera placée dans une zone, dans la phase de recolte, les dés seront génerés selon le nombre de figurines.
-Ajout d'une zone autre que celles des ressources, la cabane à reproduction, ainsi que ses contraintes
-Ajout d'un maximum de figurines pour le joueur (10).

Le déroulement de la phase de jeu reste le même, le joueur est forcé de poser toutes ses figurines (pas forcement toutes dans la même zone), puis vient la phase de récolte.
La fin de jeu reste la même, le jeu termine après la récolte des ressources.

Troisième Itération:
-Ajout des outils et du système allant avec, permettant de les utiliser dans la phase de récolte.
-Ajout de la cabane afin de craft les outils ainsi que ses contraintes (1 seule figurine).
-Ajout d'un système de tour complet, imposant au joueur de poser toutes les figurines (pas forcément toutes à la fois), puis vient la phase de récolte, le joueur peut désormais utiliser les outils. 
Après cela, le joueur doit dépenser de la nourriture pour chaque figurine utilisée avant de finaliser son tour, si il n'a pas assez de nourriture il peut utiliser des ressources, enfin , si il 
n'a pas assez de ressources, rien de spécial, il ne perd pas de points car ils ne sont pas encore dans le jeu.
La partie fini désormais après 5 tours de jeu, pas vraiment de gagnant.

Quatrième Itération:
Le début de partie change enfin, on sélectionne désormais le nombre de joueurs, allant de 2 a 4, celui-ci ne changera plus.
-On gère le marqueur "premier joueur", celui-ci change de joueur après chaque tour.
-On commence à gérer les différentes IA, pour l'instant, le joueur 1 sera concentré sur la nourriture, le joueur 2 sur une ressource, le joueur 3 sur une ressource différente, et le joueur 4 encore sur une autre ressouce.
La fin de partie reste la même.

Cinquième Itération:
-Ajout d'un système de score rudimentaire (les joueurs ont des points avec les bâtiments, et perdent des points lorsqu'ils ne peuvent pas nourrir les figurines).
-Ajout du système de batiments coûtant des ressources afin d'obtenir des points en fonction des ressources dépensées.
-Affinement des AI (Nous ne savons pas encore ce qu'on ajoutera).
Changement de la fin de partie, la partie fini toujours après 5 tours, mais il y a désormais un classement en fonction des points.

Sixième Itération:
-Ajout des cartes civilisation ainsi que du système les concernant.
-Ajout d'un calcul des scores en fin de partie, avant de désigner un gagnant, celui-ci prend en compte les points des cartes civilisation.
-Ajout des conditions de fin de parties présentes dans le vrai jeu.
-Affinement des AI ( Toujours pas d'idée).
Le gagnant est le joueur ayant le plus de points après calcul du score.

Dernière Itération et rendu:
-Avancement au maximum des AI.
-Réalisation des statistiques sur 500 parties (exemple: quelle AI gagne le plus souvent, quelle AI s'adapte le plus aux autres...).
