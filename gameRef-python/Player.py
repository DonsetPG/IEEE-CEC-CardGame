import random
import math
import copy
import numpy as np

## ----------------

from Parameters import get_args

## ----------------

class Player:

    def __init__(self,hp,mana,rcid,rune,hand,board,id):
        self.hp = hp
        self.mana = mana
        self.rcid = rcid 
        self.rune = rune 
        self.hand = hand 
        self.board = board 
        self.id = id 

