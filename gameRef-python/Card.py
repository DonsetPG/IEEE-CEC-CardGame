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

    def display(self):
        bar = " ---------- "
        blank  = " |         | "
        print(bar)
        print(blank)
        print(" |   "+str(self.cost)+"     | ")
        print(blank)
        print(" | "+str(self.attack)+"    "+str(self.defense)+"  | ")
        print(blank)
        print(" | "+self.abilities+"  | ")
        print(blank)
        print(bar)

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

    def AttackPlayer(self,player2):
        player2.hp -= self.attack
        lastRuneAlive = player2.rune[0]
        while player2.hp < lastRuneAlive:
            player2.rune.pop(0) 
            player2.cardToDraw += 1
            lastRuneAlive = player2.rune[0]

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

    def BuffGreen(self,greenCard,player1):
        if greenCard.cost <= player1.mana:
            player1.mana -= greenCard.cost
            self.attack += greenCard.attack 
            self.defense += greenCard.defense
            greenCard.attack = 0
            greenCard.defense = 0
            if greenCard.ward:
                self.ward = True
                greenCard.ward = False 
            if greenCard.guard:
                self.guard = True
                greenCard.guard = False 
            if greenCard.lethal:
                self.lethal = True
                greenCard.lethal = False 
            if greenCard.drain:
                self.drain = True
                greenCard.drain = False 
            if greenCard.charge:
                self.charge = True
                greenCard.charge = False 
            if greenCard.breaktrough:
                self.breaktrough = True
                greenCard.breaktrough = False 

    def BuffBlueOpponent(self,player1,player2):
        if self.cost <= player1.mana:
            player1.mana -= self.cost
            player1.hp += self.hpChange
            player2.hp += self.hpChangeOpponent

    def BuffBlueCard(self,player1,card,player2):
        if self.cost <= player1.mana:
            player1.mana -= self.cost
            player1.hp += self.hpChange
            card.defense += self.defense
            player2.hp += self.hpChangeOpponent


    def buffRed(self,redCard,player1):
        if redCard.cost <= player1.mana:
            player1.mana -= redCard.cost
            redCard.attack = 0
            redCard.defense = 0
            if redCard.ward:
                self.ward = False
                redCard.ward = False 
            if redCard.guard:
                self.guard = False
                redCard.guard = False 
            if redCard.lethal:
                self.lethal = False
                redCard.lethal = False 
            if redCard.drain:
                self.drain = False
                redCard.drain = False 
            if redCard.charge:
                self.charge = False
                redCard.charge = False 
            if redCard.breaktrough:
                self.breaktrough = False
                redCard.breaktrough = False 

        self.attack -= redCard.attack 
        if self.ward and redCard.attack > 0:
            self.ward = False
        else:
            self.defense -= redCard.defense
        if self.defense < 0:
            self.alive = False

    
                
                

	
    