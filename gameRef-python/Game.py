import random
import math
import copy
import numpy as np

## ----------------

from Parameters import get_args
from Rules import AttackValide
from draftPhase import Draft
from Player import Player
from Card import Card

## ----------------

args = get_args()

# Pour le moment, on se limite à un choix de carte random pdt le draft 

def displayFullCards(cards):
    bar = " ---------- "
    blank  = " |        | "
    n = len(cards)

    BAR = n*bar 
    BLANK = n*blank 

    print(BAR)
    print(BLANK)

    cost = " | cost "
    for card in cards:
        cost += str(card.cost)+" |  | cost "
    
    print(cost)
    print(BLANK)

    attack =  " |"

    for card in cards:
        attack += "a:"+str(card.attack)+" d:"+str(card.defense)+" |  |"

    print(attack)
    print(BLANK)

    abilities =  " | "

    for card in cards:
        abilities += card.abilities+" |  | "

    print(abilities)
    print(BLANK)
    print(BAR)




def phaseDraft(player1,player2):
    nbTour = args.nbTourDraft 

    for i in range(nbTour):

        cards = Draft() 
        indx1 = random.randint(0,2)
        card1 = copy.deepcopy(cards[indx1])
        card2 = copy.deepcopy(cards[indx1])

        card1.id = 2*i
        card2.id = 2*i+1
        card1.location = 1 
        card2.location = 2 

        player1.deck.append(card1)
        player2.deck.append(card2)

def initGame():

    lifeStart1 = args.lifeStart 
    nbTour1 = args.nbTourDraft 
    rune1 = args.rune 

    lifeStart2 = copy.deepcopy(lifeStart1)
    nbTour2 = copy.deepcopy(nbTour1)
    rune2 = copy.deepcopy(rune1)

    player1 = Player(lifeStart1,0,nbTour1,rune1,[],[],1,[])
    player2 = Player(lifeStart2,0,nbTour2,rune2,[],[],2,[])

    cardPlayer1 = Card(-1,-1,1,'P',0,0,lifeStart1,'------',0,0,0)
    cardPlayer2 = Card(-2,-1,2,'P',0,0,lifeStart2,'------',0,0,0)

    return player1,player2,cardPlayer1,cardPlayer2 

class Game:

    def __init__(self):
        player1,player2,cardPlayer1,cardPlayer2 = initGame()

        self.player1 = player1 
        self.player2 = player2 
        self.cardPlayer1 = cardPlayer1
        self.cardPlayer2 = cardPlayer2 

        phaseDraft(self.player1,self.player2)

        self.done = False 
        self.toPlay = 1 
        self.oneWin = False 
        self.twoWin = False 

    def display(self):
        bar = " ---------- "
        blank  = " |         | "
        print(bar)
        print(blank)
        print(" | turn "+str(self.player1.turn)+" | ")
        print(blank)
        self.player2.display()
        print(bar)
        print(" |  HAND  | ")
        displayFullCards(self.player2.hand)
        print(bar)
        print("|  BOARD  | ")
        displayFullCards(self.player2.board)
        print(bar)
        print(blank)
        print(bar)
        print("|  BOARD  | ")
        displayFullCards(self.player1.board)
        print(bar)
        print(" |  HAND  | ")
        displayFullCards(self.player1.hand)
        print(bar)
        self.player1.display()
        print(blank)
        print(bar)

    def playAttack(self,actions,idPlayer):

        if self.toPlay == 1:
            if idPlayer == 1:
                AttackValide(self.player1,self.player2,actions)
            self.player2.Draw()
            self.toPlay = 2

        else:
            if idPlayer == 2:
                AttackValide(self.player2,self.player1,actions)
            self.player1.Draw() 
            self.toPlay = 1

        self.twoWin = self.player1.EndTurn()
        self.oneWin = self.player2.EndTurn()  
        self.player1.EndTurnMana()
        self.player2.EndTurnMana() 

    def playSummon(self,actions,idPlayer):

        if self.toPlay == 1: 
            if idPlayer == 1:
                self.player1.StartTurnMana()
                self.player1.Summon(actions,self.player2)

        else:
            if idPlayer == 2:
                self.player2.StartTurnMana()
                self.player2.Summon(actions,self.player1)

    def Turn(self,actionsSummon,actionBattle,idPlayer):

        self.playSummon(actionsSummon,idPlayer)
        self.playAttack(actionBattle,idPlayer)
        self.done = (self.oneWin or self.twoWin)




game = Game() 
for _ in range(30):
    game.display() 
    if len(game.player1.hand) > 0:
        summon = [game.player1.hand[0]]
    else:
        summon = []

    attack = []
    for card in game.player1.board:
        attack.append([card,game.cardPlayer2])

    game.Turn(summon,attack,1)




    


 
