import random
import math
import copy
import numpy as np

## ----------------

from Parameters import get_args
from cardsExtractor import getCards

## ----------------

fulldata = getCards()
N = len(fulldata)

def Draft():
    for i in range(30):
        indx1 = random.randint(0,N)
        indx2 = random.randint(0,N)
        while indx1 == indx2:
            indx2 = random.randint(0,N)
        indx3 =  random.randint(0,N)
        while indx3 == indx1 or indx3 == indx2:
            indx3 = random.randint(0,N)

        return [fulldata[indx1],fulldata[indx2],fulldata[indx3]]
    
