import random
import math
import copy
import numpy as np

## ----------------

from Parameters import get_args

## ----------------

class Card:

    def __init__(self,idCard,id,location,cardType,cost,attack,defense,abilities,hpChange,hpChangeOpponent,cardDraw):
        self.alive = True
        self.idCard = idCard
        self.id = id
        self.location = location
        self.cardType = cardType
        self.cost = cost 
        self.attack = attack 
        self.defense = defense 
        self.abilities = abilities 
        self.hpChange = hpChange
        self.hpChangeOpponent = hpChangeOpponent
        self.cardDraw = cardDraw
        self.guard = (abilities[3] == 'G')
        self.ward = (abilities[5] == 'W')
        self.lethal = (abilities[4] == 'L')
        self.charge = (abilities[1] == 'C')
        self.drain = (abilities[2] == 'D')
        self.breaktrough = (abilities[0] == 'B')

    def Attack(self,cardOpponent,player1,player2):
        if self.attack > 0:
            if cardOpponent.ward:
                cardOpponent.ward = False 
            else:
                if self.lethal:
                    cardOpponent.defense = 0
                    if self.drain:
                        player1.hp += self.attack
                else:
                    if self.drain:
                        player1.hp += max(cardOpponent.defense - self.attack,cardOpponent.defense)
                    cardOpponent.defense -= self.attack
            if cardOpponent.defense < 0:
                cardOpponent.alive = False 
                if self.breaktrough:
                    player2.hp += cardOpponent.defense

    def CounterAttack(self,cardOpponent):
        if cardOpponent.attack > 0:
            if self.ward:
                self.ward = False 
            else:
                if cardOpponent.lethal:
                    self.defense = 0
                else:
                    self.defense -= cardOpponent.attack 
    
    def Trade(self,cardOpponent,player1,player2):
        self.Attack(cardOpponent,player1,player2)
        self.CounterAttack(cardOpponent)

    
                
                

	
    