import random
import math
import copy
import numpy as np

## ----------------

from Parameters import get_args

## ----------------

class Player:

    def __init__(self,hp,mana,rcid,rune,hand,board,id,deck):
        self.hp = hp
        self.turn = 0
        self.mana = mana
        self.rcid = rcid 
        self.rune = rune 
        self.hand = hand 
        self.board = board 
        self.deck = deck
        self.id = id 
        self.avantage = False
        self.cardToDraw = 1

    def display(self):
        bar = " ---------- "
        blank  = " |         | "
        print(bar)
        print(blank)
        print(" | "+str(self.id)+" - "+str(self.hp)+"  | ")
        print(blank)
        print(" | Mana "+str(self.mana)+"  | ")
        print(blank)
        print(bar)

    def StartTurnMana(self):
        if self.turn == 0:
            self.mana = 1  
        else:
            self.mana += 1

    def EndTurnMana(self):
        self.mana = self.turn
        self.turn += 1

    def Summon(self,cards,player2):
        #cards est un tableau d'objet Card, on le lit du début à la fin, si on a plus de mana avant la fin, on stop la lecture 
        indx = 0
        while self.mana > 0 and len(cards) > indx:
            card = cards[indx]
            indx += 1
            if card.cost <= self.mana:
                self.mana -= card.cost
                self.AddToBoard(card,player2)
                self.hand.remove(card)

    def AddToBoard(self,card,player2):
        self.board.append(card)
        if card.hpChange > 0:
            self.hp += card.hpChange
        if card.hpChangeOpponent < 0:
            player2.hp == card.hpChangeOpponent
        if card.cardDraw > 0:
            self.cardToDraw += card.cardToDraw

    def ClearBoard(self):
        toKill = []
        for card in self.board:
            if card.alive == False:
                toKill.append(card)
        for card in toKill:
            self.board.remove(card)
        
    def EndTurn(self):
        self.ClearBoard()
        if self.hp < 0 or len(self.deck) == 0 :
            return True 
        else:
            return False

    def Draw(self):
        indxToDraw = []
        for i in range(self.cardToDraw):
            indx = random.randint(0,len(self.deck))
            while indx in indxToDraw:
                indx = random.randint(0,len(self.deck))
            indxToDraw.append(indx)
        compteur = 0
        for indx in indxToDraw:
            self.hand.append(self.deck[indx-compteur])
            self.deck.pop(indx-compteur)
            self.rcid -= 1
            compteur += 1
        self.cardToDraw = 1




