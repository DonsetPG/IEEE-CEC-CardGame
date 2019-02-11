
import random
import math
import copy
import numpy as np

## ----------------

from Parameters import get_args

## ----------------

#Gestion des guards, on regarde de gauche à droite 

def Guards(player2):
    for card in player2.board:
        if card.defense > 0 and card.guard:
            return True 
    return False

def AttackValide(player1,player2,trades):

    """
    Attaque par le player1 sur le player2
    trades : [..... [card1,card2] .......], la carte 1 attaque la carte 2
    Si une carte attaque directement l'auter joueur, on le représentera par une carte d'id := -1

    ____

    On gère aussi les objets, etc...
    """

    for trade in trades:

        card1 = trade[0]
        card2 = trade[1]
        testGuard = Guards(player2)

        if (testGuard == False) or (testGuard == True and card2.guard == True) or (card1.type != 'C'):
            if card2.id == -1:
                if card1.type == 'B':
                    card1.BuffBlueOpponent(player1,player2)
                elif card1.type == 'C':
                    card1.AttackPlayer(player2)

            else:
                if card1.type == 'B':
                    card1.BuffBlueCard(player1,card2,player2)
                elif card1.type == 'G':
                    card1.BuffGreen(card2,player1)
                elif card1.type == 'R':
                    card1.BuffRed(card2,player1)
                else:
                    card1.Trade(card2,player1,player2)


