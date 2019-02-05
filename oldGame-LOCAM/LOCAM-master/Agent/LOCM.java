



// The code is an absolute mess, and most of the function are actually never used. 

// _______________________________________________________________________________________________________________ // 

/*
* allpossibilities de summon bugggg;
*/

/*
* checker qu'on ne fait pas plusieurs fois le même calcul
*/

/*
* 
* et comprendre pq des scores atteignent les 7k ???
* verifier que greenitem fonctionne ok;
* rajouter une Solution qui attack toujours full l'ennemi; 
* ne pas compter dans le score les créatures.getDefense() <=0;
* bails chelou avec summon : ne summon pas tout; Il faudrait vérifier que tout fonctionne bien; 
* améliorer le bot ennemie 
* remplacer ScoreBis par scoreBoard si le tempo obtient de meilleur résultat 
*
*/

/*
* retry la fonction tempo;
* faire tourner en local 
* réfléchir à des stratégies en fonction de si on est Joueur 1 ou Joueur 2 : draft/summon/attack. 
*


java -jar -Dleague.level=4 legends-of-code-and-magic-1.0.4.jar -p1 "testBot1.jar" -p2 "testBotOld.jar" -l ./logs/game1.json





*
*
*/





import java.util.Vector;
import java.util.*;
import java.io.*;
import java.math.*;
import java.util.concurrent.ThreadLocalRandom;

class Card{

	private int idCard;
	private int id;
	private int location;
	private int CardType;
	private int cost;
	private int attack;
	private int defense;
	private int oldDefense;
	private int oldestDefense;
	private String abilities;
	private String oldAbilities;
	private int hpChange;
	private int hpChangeOpponent;
	private int CardDraw;
	private int placeDraft;
	private boolean used = false;
	private boolean guard = false;
	private boolean ward = false;
	private boolean lethal = false;
	private boolean charge = false;
	private boolean drain = false;
	private boolean breaktrough = false;
	private boolean buffGuard = false;
	private boolean buffWard = false;
	private boolean buffLethal = false;
	private boolean buffCharge = false;
	private boolean buffDrain = false;
	private boolean buffBreaktrough = false;
	private boolean wasWard = false;
	private boolean wasWardOldest = false;



	public Card(int idCard,int id,int location,int CardType,int cost,int attack,int defense,String abilities,int hpChange,int hpChangeOpponent,int CardDraw,int placeDraft){
		this.idCard = idCard;
		this.id = id;
		this.location = location;
		this.CardType = CardType;
		this.cost = cost;
		this.attack = attack;
		this.defense = defense;
		this.oldDefense = defense;
		this.oldestDefense = defense;
		this.abilities = abilities;
		this.oldAbilities = abilities;
		this.hpChange = hpChange;
		this.hpChangeOpponent = hpChangeOpponent;
		this.CardDraw = CardDraw;
		this.placeDraft = placeDraft;
		this.used = false;
		for (int i = 0;i<abilities.length();i++){
			if (abilities.charAt(i) == 'G') {this.guard = true;}
			else if (abilities.charAt(i) == 'L') {this.lethal = true;}
			else if (abilities.charAt(i) == 'W') {this.ward = true;}
			else if (abilities.charAt(i) == 'C') {this.charge = true;}
			else if (abilities.charAt(i) == 'D') {this.drain = true;}
			else if (abilities.charAt(i) == 'B') {this.breaktrough = true;}
		}
	}

	public Card(int hp){
		this.idCard = -1;
		this.id = -1;
		this.location = 100;
		this.CardType = 100;
		this.cost = 1;
		this.attack = 0;
		this.defense = hp;
		this.oldestDefense = hp;
		this.oldDefense = hp;
		this.abilities = "------";
		this.oldAbilities = "------";
		this.hpChange = 0;
		this.hpChangeOpponent = 0;
		this.CardDraw = 0;
		this.placeDraft = 0;
	}

	public Card(Card c){
		this.idCard = c.idCard;
		this.id = c.id;
		this.location = c.location;
		this.CardType = c.CardType;
		this.cost = c.cost;
		this.attack = -c.attack;
		this.defense = -c.defense;
		this.abilities = c.abilities;
		this.abilities = c.oldAbilities;
		this.hpChange = c.hpChange;
		this.hpChangeOpponent = c.hpChangeOpponent;
		this.CardDraw = c.CardDraw;
		this.placeDraft = c.placeDraft;
		this.used = false;
		for (int i = 0;i<c.abilities.length();i++){
			if (c.abilities.charAt(i) == 'G') {this.guard = true;}
			else if (c.abilities.charAt(i) == 'L') {this.lethal = true;}
			else if (c.abilities.charAt(i) == 'W') {this.ward = true;}
			else if (c.abilities.charAt(i) == 'C') {this.charge = true;}
			else if (c.abilities.charAt(i) == 'D') {this.drain = true;}
			else if (c.abilities.charAt(i) == 'B') {this.breaktrough = true;}
		}
	}

	public Card(Card c,int a){
		this.idCard = c.idCard;
		this.id = c.id;
		this.location = c.location;
		this.CardType = c.CardType;
		this.cost = c.cost;
		this.attack = c.attack;
		this.defense = c.defense;
		this.abilities = c.abilities;
		this.abilities = c.oldAbilities;
		this.hpChange = c.hpChange;
		this.hpChangeOpponent = c.hpChangeOpponent;
		this.CardDraw = c.CardDraw;
		this.placeDraft = c.placeDraft;
		this.used = false;
		for (int i = 0;i<c.abilities.length();i++){
			if (c.abilities.charAt(i) == 'G') {this.guard = true;}
			else if (c.abilities.charAt(i) == 'L') {this.lethal = true;}
			else if (c.abilities.charAt(i) == 'W') {this.ward = true;}
			else if (c.abilities.charAt(i) == 'C') {this.charge = true;}
			else if (c.abilities.charAt(i) == 'D') {this.drain = true;}
			else if (c.abilities.charAt(i) == 'B') {this.breaktrough = true;}
		}
	}

	public int getCardDraw(){
		return this.CardDraw;
	}


	public int score(){
		return this.getCost();
	}

	public void setUsed(boolean b){
		this.used = b;
	}

	public boolean getUsed(){
		return this.used;
	}

	public double scoreTest(int n,int mineHp,int opponentHp){
		double score = 0;
		score += 2*(double)this.defense + 5*(double)this.attack;

		if (opponentHp <= this.hpChangeOpponent && n == 0){
			score += 1000;
		}

		if (this.guard && mineHp < 20 && n == 0){
			score += 1.5*(double)this.defense;
		}
		if (this.CardDraw > 0 && mineHp < 15 && n == 0){
			score += 4;
		}
		if (this.breaktrough){
			score += ((double)this.attack - 1.0)*0.9;
		}
		if (this.drain){
			score += 0.7*(double)this.attack;
		}
		if (this.lethal){
			score += 20.0 - (double)this.attack;
		}
		if (this.ward){
			score += (double)this.attack;
			score += 4;
		}
		if (this.ward && this.lethal){
			score += 20.0 - (double)this.attack;
		}

		if (mineHp < 10 && this.guard && n == 0){
			score += 3*(double)this.defense;
		}
		if (opponentHp < 10 && this.breaktrough && n == 0){
			score += (double)this.attack;
		}
		if (opponentHp < 10 && this.drain && n == 1){
			score += (double)this.attack;
		}


		return score;
		
	}

	public double scoreBis(int hp,int hpOpponent,int nbrCards,int nbrCardsOpponent,int PlayerNumber,int TOUR){
    

    double score;

		if (this.getCardType() == 2){
			this.attack = - this.attack;
			this.defense = - this.defense;
		}


		double score1x = (double)100*(this.cost+0.1)*((double)this.attack*2 + (double)this.defense);
		double score2x = (double)100*(this.cost+0.1)*((double)this.attack + 2*(double)this.defense);
		double score3x = (double)100*(this.cost+0.1)*((double)this.attack + (double)this.defense);
		double score1y = (double)100*(this.attack*2 + (double)this.defense)/(double)(this.cost+0.1);
		double score2y = (double)100*(this.attack + 2*(double)this.defense)/(double)(this.cost+0.1);
		double score3y = (double)100*(this.attack + (double)this.defense)/(double)(this.cost+0.1);

    if (this.guard){
      score1x += 10;
      score2x += 10;
      score3x += 10;
      score1y += 10;
      score2y += 10;
      score3y += 10;
    }
    if (this.ward){
      score1x += 50;
      score2x += 50;
      score3x += 50;
      score1y += 50;
      score2y += 50;
      score3y += 50;
    }
    if (this.lethal){
      score1x += 1000*this.defense;
      score2x += 1000*this.defense;
      score3x += 1000*this.defense;
      score1y += 1000*this.defense;
      score2y += 1000*this.defense;
      score3y += 1000*this.defense;
    }
    if (this.drain){
      score1x += 10;
      score2x += 10;
      score3x += 10;
      score1y += 10;
      score2y += 10;
      score3y += 10;
    }
    if (this.charge){
      score1x += 10;
      score2x += 10;
      score3x += 10;
      score1y += 10;
      score2y += 10;
      score3y += 10;
    }
    if (this.breaktrough){
      score1x += 10;
      score2x += 10;
      score3x += 10;
      score1y += 10;
      score2y += 10;
      score3y += 10;
    }
		//cas des y //
		if (nbrCards*2 <= nbrCardsOpponent && nbrCardsOpponent >=4){
			if (hp > 20){
				if (hpOpponent > 20){
					if (this.guard){score3y += 100;}
					if (this.lethal){score3y += 100;}
					if (this.ward){score3y += 100;}
					score = score3y;
				} else if(hpOpponent > 10){
					if (this.ward){score1y += 100;}
					if (this.lethal){score1y += 100;}
					score = score1y;
				} else {
					if (this.charge){score1y += 100;}
					if (this.lethal){score1y += 100;}
					if (this.breaktrough){score1y += 50;}
					score = score1y;
				}
		}	else if(hp > 10){
				if (hpOpponent > 20){
					if (this.guard){score2y += 200;}
					if (this.drain){score2y += 50;}
					if (this.ward){score2y += 50;}
					if (this.lethal){score2y += 200;}

					score = score2y;
				} else if(hpOpponent > 10){
					if (this.guard){score3y += 200;}
					if (this.drain){score3y += 50;}
					if (this.ward){score3y += 50;}
					if (this.lethal){score3y += 200;}

					score = score3y;
				} else {
					if (this.charge){score1y += 100;}
					if (this.lethal){score1y += 100;}
					if (this.breaktrough){score1y += 50;}
					score = score1y;
				}
			}else {

				if (hpOpponent > 20){
					if (this.guard){score2y += 1000;}
					if (this.drain){score2y += 50;}
					if (this.ward){score2y += 50;}
					score = score2y;
				} else if(hpOpponent > 10){
					if (this.guard){score2y += 1000;}
					if (this.drain){score2y += 50;}
					if (this.ward){score2y += 50;}
					score = score2y;
				} else {
					if (this.charge){score3y += 1000;}
					if (this.guard){score3y += 200;}
					score = score3y;
				}

			}
		} else {
			if (hp > 20){
				if (hpOpponent > 23){
					if (this.guard){score3x += 100;}
					if (this.lethal && this.cost <4){
						if (PlayerNumber == 1){score3x += 100;}
						else {score3x += 100;}
						
					}
					if (this.ward){score3x += 100;}
					score = score3x;
				} else if(hpOpponent > 10){
					if (this.ward){score1x += 100;}
					if (this.lethal){score1x += 100;}
					score = score1x;
				} else {
					if (this.charge){score1x += 100;}
					if (this.lethal){score1x += 25;}
					if (this.breaktrough){score1x += 50;}
					score = score1x;
				}
		}	else if(hp > 10){
				if (hpOpponent > 20){
					if (this.guard){score2x += 100;}
					if (this.drain){score2x += 50;}
					if (this.ward){score2x += 50;}
					score = score2x;
				} else if(hpOpponent > 10){
					if (this.guard){score3x += 100;}
					if (this.drain){score3x += 50;}
					if (this.ward){score3x += 50;}
					score = score3x;
				} else {
					if (this.charge){score1x += 100;}
					if (this.lethal){score1x += 25;}
					if (this.breaktrough){score1x += 50;}
					score = score1x;
				}
			}else {

				if (hpOpponent > 20){
					if (this.guard){score2x += 1000;}
					if (this.drain){score2x += 50;}
					if (this.ward){score2x += 50;}
					score = score2x;
				} else if(hpOpponent > 10){
					if (this.guard){score2x += 1000;}
					if (this.drain){score2x += 50;}
					if (this.ward){score2x += 50;}
					score = score2x;
				} else {
					if (this.charge){score3x += 100;}
					if (this.guard){score3x += 1000;}
					if (this.ward){score3x += 200;}

					score = score3x;
				}

			}
		}

		if (this.getCardType() == 2){
			if (this.getCardType() == 2){
				this.attack = - this.attack;
				this.defense = - this.defense;
			}
			return Math.sqrt(-score);
		} else {
			//////System.err.println("la carte "+this.getId()+" de score -> "+score);
			return score;
		}
	}

	public void printPICK(){
		String answer = "PICK"+" "+Integer.toString(this.getPlace());
		System.out.println(answer);

	}


	public String printSUMMON(){
		String answer = "SUMMON"+" "+Integer.toString(this.getId())+";";
		//System.out.println(answer);
		return answer;
	}

	public String printATTACK(Card c){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+Integer.toString(c.getId())+";";
		//System.out.println(answer);
		return answer;
	}

  public String printATTACKBIS(Card c){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+Integer.toString(c.getId())+";";
		//System.out.println(answer);
		return answer;
	}

	public String printATTACK(){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+"-1"+";";
		//System.out.println(answer);
		return answer;
	}

	public String printUSE(Card c){
		String answer = "USE"+" "+Integer.toString(c.getId())+" "+Integer.toString(this.getId())+";";
		//System.out.println(answer);
		return answer;
	}

	public String printUSE(){
		String answer = "USE"+" "+Integer.toString(this.getId())+" "+"-1;";
		//System.out.println(answer);
		return answer;
	}


	public int getHpChange(){
		return this.hpChange;
	}

	public int getHpChangeOpponent(){
		return this.hpChangeOpponent;
	}



	public int getId(){
		return this.id;
	}

	public int getCardNumber(){
		return this.idCard;
	}


    public int getPlace(){
		return this.placeDraft;
	}

	public int getLocation(){
		return this.location;
	}

	public int getCardType(){
		return this.CardType;
	}

	public int getCost(){
		return this.cost;
	}

	public int getAttack(){
		return this.attack;
	}

	public int getDefense(){
		return this.defense;
	}

	public int getOldDefense(){
		return this.oldDefense;
	}

	public int getOldestDefense(){
		return this.oldestDefense;
	}

	public void setOldestDefense(int n){
		this.oldestDefense = n;
	}

	public void reset(){
		this.defense = this.oldestDefense;
		this.oldDefense = this.oldestDefense;
		if (this.wasWard == true ){
			this.ward = true;
			this.wasWard = false;
		} else if (this.wasWardOldest == true){
			this.ward = true;
			this.wasWard = false;
			this.wasWardOldest = false;
		}
	}

	public void upgradeLife(){
		this.oldDefense = this.defense;
	}

	public void resetSmall(){
		this.defense = this.oldDefense;
		
		if (this.wasWard == true ){
			this.ward = true;
			this.wasWard = false;
		}
	}

	public void update(){
		this.oldDefense = this.defense;
		if (this.wasWard == true){
			this.ward = false;
			this.wasWard = false;
			this.wasWardOldest = true;
		}
	}

	public void setOldDefense(int n){
		this.oldDefense = n;
	}

	public boolean getGuard() {
		return this.guard;
	}

	public boolean getWard() {
		return this.ward;
	}

	public boolean getLethal() {
		return this.lethal;
	}

	public boolean getWasWard() {
		return this.wasWard;
	}

	public boolean getDrain() {
		return this.drain;
	}

	public boolean getCharge() {
		return this.charge;
	}

	public boolean getBreaktrough() {
		return this.breaktrough;
	}

	public String getAbilities(){
		return this.abilities;
	}


	public void setAttack(int attack){
		this.attack = attack;
	}

	public void setDefense(int defense){
		this.defense = defense;
	}

	public void counterAttack(Card c,Joueur me,Joueur other){
		if (c.getWard() && this.getAttack() >0){
			c.ward = false;
			c.wasWard = true;
			this.abilities.replace('W', '-');
		}
		else{
			if (this.getLethal()){c.defense = 0;}
			else {c.defense -= this.getAttack();}
	}

	}

	public void deCounterAttack(Card c,Joueur me,Joueur other){
		if (c.getWasWard() && this.getAttack()>0){
			c.ward = true;
			c.wasWard = false;
			this.abilities = this.oldAbilities;
		}
		else{
			if (this.getLethal()){c.defense = c.oldDefense;}

			else {c.defense = c.oldDefense;}
	}
	}


	public void getAttackBy(Card c,Joueur me,Joueur other,Card opponent,Card mine){

		



		if (this.getWard() && c.getAttack()>0){
			this.ward = false;
			this.wasWard = true;
			this.abilities.replace('W', '-');
		}
		else{

			if (c.getDrain() && c.getLethal()) {
				mine.defense +=c.getAttack();
				if (this.getId() == -1){this.defense -= c.getAttack();}
				else {this.defense = 0;}
				
			}
			else if (c.getLethal()){
				if (this.getId() == -1){this.defense -= c.getAttack();}
				else {this.defense = 0;}
			}
			else if (c.getDrain()){
				mine.defense +=c.getAttack();
				this.defense -= c.getAttack();
			}
			else if (c.getBreaktrough()){
				this.defense -= c.getAttack();
				if (this.defense < 0 && this.defense + c.getAttack() >0){opponent.defense += this.defense;}
			}
			else {this.defense -= c.getAttack();}
	}

}

	public void getDeattackBy(Card c,Joueur me,Joueur other,Card opponent,Card mine){

		if (this.getWasWard() && c.getAttack() >0){
			this.ward = true;
			this.wasWard = false;
			this.abilities = this.oldAbilities;
      		this.defense = this.oldDefense;
		}
		else{
			if (c.getDrain()){
				mine.defense -= c.attack;
			}
			else if (c.getBreaktrough()){
				
				if (this.defense < 0 && this.defense + c.getAttack() >0){opponent.defense -= this.defense;}
			}
			this.defense = this.oldDefense;
			this.abilities = this.oldAbilities;
			

			}


}

	public void getBuffByGreenCard(Card c){
		this.defense += c.getDefense();
		this.attack += c.getAttack();
		if (c.getGuard()){
			if (!this.guard){this.buffGuard = true;}
			this.guard = true;
		}
		if (c.getWard()){
			if (!this.ward){this.buffWard = true;}
			this.ward = true;
		}
		if (c.getDrain()){
			if (!this.drain){this.buffDrain = true;}
			this.drain = true;
		}
		if (c.getLethal()){
			if (!this.lethal){this.buffLethal = true;}
			this.lethal = true;
		}
		if (c.getCharge()){
			if (!this.charge){this.buffCharge = true;}
			this.charge = true;
		}
		if (c.getBreaktrough()){
			if (!this.breaktrough){this.buffBreaktrough = true;}
			this.breaktrough = true;
		}
	}

	public void getDebuffByGreenCard(Card c){
		this.defense -= c.getDefense();
		this.attack -= c.getAttack();
		if (c.getGuard()){
			if (this.buffGuard){this.guard = false;}
			this.buffGuard = false;
		}
		if (c.getWard()){
			if (this.buffWard){this.ward = false;}
			this.buffWard = false;
		}
		if (c.getLethal()){
			if (this.buffLethal){this.lethal = false;}
			this.buffLethal = false;
		}
		if (c.getDrain()){
			if (this.buffDrain){this.drain = false;}
			this.buffDrain = false;
		}
		if (c.getCharge()){
			if (this.buffCharge){this.charge = false;}
			this.buffCharge = false;
		}
		if (c.getBreaktrough()){
			if (this.buffBreaktrough){this.breaktrough = false;}
			this.buffBreaktrough = false;
		}
	}

	public void getBuffByGreenCardBis(Card c){
		this.oldDefense += c.getDefense();
		this.defense += c.getDefense();
		this.attack += c.getAttack();
		if (c.getGuard()){
			if (!this.guard){this.buffGuard = true;}
			this.guard = true;
			
		}
		if (c.getWard()){
			if (!this.ward){this.buffWard = true;}
			this.ward = true;
		}
		if (c.getDrain()){
			if (!this.drain){this.buffDrain = true;}
			this.drain = true;
		}
		if (c.getLethal()){
			if (!this.lethal){this.buffLethal = true;}
			this.lethal = true;
		}
		if (c.getCharge()){
			if (!this.charge){this.buffCharge = true;}
			this.charge = true;
		}
		if (c.getBreaktrough()){
			if (!this.breaktrough){this.buffBreaktrough = true;}
			this.breaktrough = true;
		}
	}

	public void getBuffByRedCard(Card c){
		if (this.ward == false){
			this.defense += c.getDefense();
			this.attack += c.getAttack();
		}
		
		if (c.getGuard()){
			if (this.guard){this.buffGuard = true;}
			this.guard = false;
		}
		if (c.getWard() || (this.ward && c.getDefense() != 0)){
			if (this.ward){this.buffWard = true;}
			this.ward = false;
		}
		if (c.getDrain()){
			if (this.drain){this.buffDrain = true;}
			this.drain = false;
		}
		if (c.getLethal()){
			if (this.lethal){this.buffLethal = true;}
			this.lethal = false;
		}
		if (c.getCharge()){
			if (this.charge){this.buffCharge = true;}
			this.charge = false;
		}
		if (c.getBreaktrough()){
			if (this.breaktrough){this.buffBreaktrough = true;}
			this.breaktrough = false;
		}
	}

	public void getDebuffByRedCard(Card c){
		if (this.buffWard == false){
			this.defense -= c.getDefense();
			this.attack -= c.getAttack();
		}
		if (c.getGuard()){
			if (this.buffGuard){this.guard = true;}
			this.buffGuard = false;
		}
		if (c.getWard() || (this.buffWard && c.defense != 0)){
			if (this.buffWard){this.ward = true;}
			this.buffWard = false;
		}
		if (c.getLethal()){
			if (this.buffLethal){this.lethal = true;}
			this.buffLethal = false;
		}
		if (c.getDrain()){
			if (this.buffDrain){this.drain = true;}
			this.buffDrain = false;
		}
		if (c.getCharge()){
			if (this.buffCharge){this.charge = true;}
			this.buffCharge = false;
		}
		if (c.getBreaktrough()){
			if (this.buffBreaktrough){this.breaktrough = true;}
			this.buffBreaktrough = false;
		}
	}



	public void getAttackByBlueCard(Card c){
		this.defense += c.getDefense();
		this.attack += c.getAttack();
		if (c.getGuard()){this.guard = false;}
		if (c.getWard()){this.ward = false;}
		if (c.getDrain()){this.guard = false;}
		if (c.getLethal()){this.guard = false;}
		if (c.getCharge()){this.guard = false;}
		if (c.getBreaktrough()){this.guard = false;}
	}

}


class Hand{

	private int amountOfCard;
	private Vector<Card> Hand;
	private int amountOfItem;

	public Hand(Vector<Card> h ) {
		this.Hand = h;
		this.amountOfCard = h.size();
		this.amountOfItem = 0;
		for (int i = 0;i<h.size();i++) {
			if (h.elementAt(i).getCardType() >0){this.amountOfItem++;}
		}
	}

	public int getItems(){
		return this.amountOfItem;
	}

	public boolean equalWithPermutation(Vector<Card> vA,Vector<Card> vB){
		
		if (vA.size() != vB.size()){
			return false;
		} else {
			for (int i = 0 ; i < vA.size() ; i++){
				Card test = vB.elementAt(i);
				if (test.getCardType() > 0){return false;}
				if (!vA.contains(test)){return false;}
				
			}
		}
		return true;
	}

	public boolean insideNoMatterpermutation(Vector<Vector<Card>> possible,Vector<Card> vB){
		for (int i = 0 ; i<possible.size() ; i++){
			Vector<Card> vA = possible.elementAt(i);
			boolean eq = this.equalWithPermutation(vA,vB);
			if (eq == true){
				return true;
			}
		}
		return false;
	}

	public Vector<Card> summonPossible(int mana,int nbrCarte,Joueur me){
		Vector<Card> possible = new Vector<Card>();
		Vector<Card> h = this.Hand;
		for (int i = 0 ; i < h.size() && nbrCarte < 6 ; i++){
			if (h.elementAt(i).getCost() <= mana){
				possible.addElement(h.elementAt(i));
			}
		}
		return possible;
	}

	public Card CardRandomPossible(int mana,int nbrCarte,Joueur me){
		Vector<Card> possible = this.summonPossible(mana, nbrCarte, me);
		if (possible.size() > 0){
			int randomIndice = ThreadLocalRandom.current().nextInt(0,possible.size());
			return possible.elementAt(randomIndice);
		} else {
			return new Card(0);
		}
		
	}

	public Vector<Card> randomSummon(int mana,int nbrCarte,Joueur me){
		Vector<Card> Solution = new Vector<Card>();
		int m = mana;
		int compteur = nbrCarte;
		Card toSummon;
		do {
			toSummon = this.CardRandomPossible(m, compteur, me);
			if (toSummon.getId() > -1){
				Solution.addElement(toSummon);
				m -= toSummon.getCost();
				if (toSummon.getCardType() == 0){compteur++;}
				me.getHand().remove(toSummon);
			}
		} while (toSummon.getId()>-1 && compteur <= 6 && m >= 0);

		for (int i = 0 ; i < Solution.size();i++){
			if (Solution.elementAt(i).getId()>-1){me.getHand().add(Solution.elementAt(i));}
		}
		return Solution;
	}

	public Vector<Vector<Card>> allPosibilitesWithoutItem(int mana,int nbrCarte,Joueur me,Joueur other) {


		int min = 13;
		
		Vector<Card> h = this.Hand;
		Card minCost = new Card(0);
		
		Vector<Card> possible = new Vector<Card>();
		for (int i = 0;i<h.size();i++) {
			


			if (h.elementAt(i).getCost() <= mana) {
				possible.addElement(h.elementAt(i));
				if (h.elementAt(i).getCost() <= min){
					min = h.elementAt(i).getCost();
					minCost = h.elementAt(i);
				}
			    //////System.err.print("Cartes possibles :");
			    //////System.err.print(h.elementAt(i).getId());
			    //////System.err.println();
			    }
		}

    Vector<Card> possible2 = new Vector<Card>();
		for (int i = h.size()-1;i>=0;i--) {
			if (h.elementAt(i).getCost() <= mana) {
			    possible2.addElement(h.elementAt(i));
			    //////System.err.print("Cartes possibles :");
			    //////System.err.print(h.elementAt(i).getId());
			    //////System.err.println();
			    }
		}

		Vector<Vector<Card>> renvoie = new Vector<Vector<Card>>();
		int n = possible.size();
		for (int i = 0;i<Math.pow(2,n);i++){
			int compteur = nbrCarte;
			int m = 0;
			Vector<Card> answer = new Vector<Card>();
			
			for (int j = 0;j<n;j++){
				if ((int)(i/(int)(Math.pow(2,n-1-j)))%2 == 0){
					if (possible.elementAt(j).getCardType() == 0|| possible.elementAt(j).getCardType() == 3 || (possible.elementAt(j).getCardType() == 1 && compteur>0) || (possible.elementAt(j).getCardType() == 2 && other.getBoard().size()>1)){
						answer.addElement(possible.elementAt(j));
						m += possible.elementAt(j).getCost();
						if (possible.elementAt(j).getCardType() == 0){compteur++;}
					}
				}
			}
			if (compteur <= 6 && m <= mana){
				if (possible.size() >0 && answer.size() > 0 && !renvoie.contains(answer) && !this.insideNoMatterpermutation(renvoie,answer)){
					 renvoie.addElement(answer);
					
				}
				
			}
		}

    for (int i = 0;i<Math.pow(2,n);i++){
			int compteur = nbrCarte;
			int m = 0;
			Vector<Card> answer = new Vector<Card>();
			
			for (int j = 0;j<n;j++){
				if ((int)(i/(int)(Math.pow(2,n-1-j)))%2 == 0){
					if (possible2.elementAt(j).getCardType() == 0|| possible2.elementAt(j).getCardType() == 3 || (possible2.elementAt(j).getCardType() == 1 && compteur>0) || (possible2.elementAt(j).getCardType() == 2 && other.getBoard().size()>1)){
						answer.addElement(possible2.elementAt(j));
						m += possible2.elementAt(j).getCost();
						if (possible2.elementAt(j).getCardType() == 0){compteur++;}
					}
				}
			}
			if (compteur <= 6 && m <= mana && !renvoie.contains(answer) && answer.size() > 0 && !this.insideNoMatterpermutation(renvoie,answer)){
				 renvoie.addElement(answer);
				
			}
	}
		return renvoie;
	}
}

class Deck{

	int amountOfCard;
	Vector<Card> Deck;
	int[] manaAmount;
	int amountOfItem;
	int[] scoreCard2 = {73
		,69
		,87
		,72
		,80
		,79
		,127
		,83
		,90
		,62
		,86
		,86
		,73
		,54
		,79
		,66
		,79
		,93
		,92
		,58
		,80
		,60
		,95
		,60
		,76
		,85
		,69
		,91
		,100
		,69
		,59
		,101
		,94
		,77
		,53
		,68
		,86
		,75
		,68
		,68
		,84
		,52
		,52
		,93
		,70
		,55
		,76
		,122
		,123
		,99
		,112
		,97
		,111
		,96
		,37
		,70
		,52
		,69
		,67
		,46
		,68
		,61
		,55
		,99
		,127
		,106
		,98
		,124
		,111
		,79
		,77
		,74
		,77
		,72
		,83
		,50
		,75
		,36
		,80
		,100
		,77
		,88
		,84
		,113
		,96
		,77
		,92
		,91
		,70
		,82
		,70
		,30
		,78
		,69
		,88
		,80
		,86
		,77
		,88
		,72
		,70
		,61
		,81
		,81
		,74
		,80
		,45
		,52
		,76
		,13
		,70
		,70
		,33
		,91
		,94
		,114
		,38
		,59
		,52
		,54
		,87
		,62
		,65
		,43
		,68
		,60
		,74
		,80
		,73
		,54
		,47
		,48
		,86
		,73
		,80
		,47
		,80
		,35
		,99
		,4
		,77
		,52
		,16
		,85
		,86
		,72
		,95
		,94
		,78
		,85
		,121
		,89
		,10
		,9
		,86
		,5
		,86
		,80
		,82
		,4
		};
		
	   
	
	
	//{81,69,87,81,89,89,127,83,92,62,86,78,77,54,79,66,79,93,76,58,79,60,86,60,79,85,69,91,99,70,59,97,94,71,53,68,86,75,72,68,79,52,52,88,55,47,76,122,111,99,112,102,111,97,37,70,52,69,61,46,68,61,55,99,127,106,98,124,111,79,71,74,70,57,79,50,63,36,66,90,72,83,84,113,96,89,92,91,67,64,81,30,78,70,86,87,86,77,88,72,67,61,81,75,68,70,45,52,76,13,69,59,33,64,73,114,38,59,52,54,76,62,44,43,58,60,64,58,68,54,47,48,74,63,64,47,71,35,99,4,80,52,16,85,87,68,92,94,75,85,121,83,10,9,0,5,9,0,5,4};

//	{18, 96, 87, 81, 89, 10, 129, 83, 92, 62, 123, 78, 42, 54, 79, 46, 79, 95, 50, 105, 115, 69, 19, 70, 88, 85, 54, 100, 100, 70, 59, 99, 30, 24, 65, 80, 4, 4, 77, 127, 100, 41, 52, 88, 55, 10, 56, 71, 41, 99, 112, 102, 28, 97, 22, 70, 52, 53, 55, 46, 68, 61, 71, 83, 65, 74, 84, 98, 111, 79, 82, 74, 70, 57, 71, 50, 63, 36, 66, 78, 72, 116, 100, 119, 96, 89, 92, 91, 67, 64, 91, 71, 81, 70, 94, 87, 35, 77, 88, 72, 67, 61, 81, 75, 68, 90, 45, 52, 76, 13, 21, 59, 33, 64, 73,114,38,59,52,54,76,62,44,43,58,60,64,58,68,54,47,48,74,63,64,47,71,35,99,4,80,52,16,85,87,68,92,94,75,85,121,83,10,9,0,5,9,0,5,4};

	int[] scoreCard = {73
		,69
		,87
		,72
		,80
		,79
		,127
		,83
		,90
		,62
		,86
		,86
		,73
		,54
		,79
		,66
		,79
		,93
		,92
		,58
		,80
		,60
		,95
		,60
		,76
		,85
		,69
		,91
		,100
		,69
		,59
		,101
		,94
		,77
		,53
		,68
		,86
		,75
		,68
		,68
		,84
		,52
		,52
		,93
		,70
		,55
		,76
		,122
		,123
		,99
		,112
		,97
		,111
		,96
		,37
		,70
		,52
		,69
		,67
		,46
		,68
		,61
		,55
		,99
		,127
		,106
		,98
		,124
		,111
		,79
		,77
		,74
		,77
		,72
		,83
		,50
		,75
		,36
		,80
		,100
		,77
		,88
		,84
		,113
		,96
		,77
		,92
		,91
		,70
		,82
		,70
		,30
		,78
		,69
		,88
		,80
		,86
		,77
		,88
		,72
		,70
		,61
		,81
		,81
		,74
		,80
		,45
		,52
		,76
		,13
		,70
		,70
		,33
		,91
		,94
		,114
		,38
		,59
		,52
		,54
		,87
		,62
		,65
		,43
		,68
		,60
		,74
		,80
		,73
		,54
		,47
		,48
		,86
		,73
		,80
		,47
		,80
		,35
		,99
		,4
		,77
		,52
		,16
		,85
		,86
		,72
		,95
		,94
		,78
		,85
		,121
		,89
		,10
		,9
		,86
		,5
		,86
		,80
		,82
		,4
		};
		
	   
	//{81,69,87,81,89,89,127,83,92,62,86,78,77,54,79,66,79,93,76,58,79,60,86,60,79,85,69,91,99,70,59,97,94,71,53,68,86,75,72,68,79,52,52,88,55,47,76,122,111,99,112,102,111,97,37,70,52,69,61,46,68,61,55,99,127,106,98,124,111,79,71,74,70,57,79,50,63,36,66,90,72,83,84,113,96,89,92,91,67,64,81,30,78,70,86,87,86,77,88,72,67,61,81,75,68,70,45,52,76,13,69,59,33,64,73,114,38,59,52,54,76,62,44,43,58,60,64,58,68,54,47,48,74,63,64,47,71,35,99,4,80,52,16,85,87,68,92,94,75,85,121,83,10,9,0,5,9,0,5,4};






	public Deck(){
		this.amountOfCard = 0;
		this.Deck = new Vector<Card>();
		this.manaAmount = new int[13];
		for (int i = 0;i<13;i++){
			manaAmount[i] = 0;
		}
		this.amountOfItem = 0;



	}

	public int getAmountOfCard(){
		return this.amountOfCard;
	}

	public Vector<Card> getDeck(){
		return this.Deck;
	}

	public int[] getManaAmount(){
		return this.manaAmount;
	}

	public int getAmountOfItem(){
		return this.amountOfItem;
	}

	public void incrCard(Card c){
		this.amountOfCard++;
		this.Deck.addElement(c);
		if (c.getCardType() > 0){this.amountOfItem++;}
		else{
			this.manaAmount[c.getCost()]++;
		}

	}

	public void decrCard(Card c){
		this.amountOfCard--;
		this.Deck.removeElementAt(this.Deck.size()-1);
		if (c.getCardType() > 0){this.amountOfItem--;}
		else{
			this.manaAmount[c.getCost()]--;
		}

	}

	public int scoreCardInDraft(Card c,int n){
		int indx = c.getCardNumber();
		int score;
		if (n == 1){
			score = this.scoreCard[indx-1];
		}
		else {
			score =  this.scoreCard2[indx-1];
		}
		if (this.amountOfItem > 10){
			score -= 1000;
		}
		if (this.manaAmount[9]+this.manaAmount[10]+this.manaAmount[11]+this.manaAmount[12]>4){
			score -= 1000;
		}
		return score;
	}

	public int scoreDeck(){
		int score = 0;
		if (this.amountOfItem <=0){score += this.amountOfItem;}
		else {score -= 100000000;}

		int cat0 = this.manaAmount[0];
		int cat1 = this.manaAmount[1];
		int cat2 = this.manaAmount[2];
		int cat3 = this.manaAmount[3];
		int cat4 = this.manaAmount[4];
		int cat5 = this.manaAmount[5];
		int cat6 = this.manaAmount[6];
		int cat7 = this.manaAmount[7];
		int cat8 = this.manaAmount[8];
		int cat9 = this.manaAmount[9];
		int cat12 = this.manaAmount[12];

		if (cat0 <=1){score += cat0;}
		else {score -= 100000000;}

		if (cat1 <= 2){score += cat1;}
		else {score -= 100000000;}

		if (cat2 <= 10){score += cat2;}
		else {score -= 100000000;}

		if (cat3 <= 7){score += cat3;}
		else {score -= 100000000;}

		if (cat4 <= 3){score += cat4;}
		else {score -= 100000000;}

		if (cat5 <= 3){score += cat5;}
		else {score -= 100000000;}

		if (cat6 <= 2){score += cat6;}
		else {score -= 100000000;}

		if (cat7 <= 1){score += cat7;}
		else {score -= 100000000;}

		if (cat8 <= 1){score += cat8;}
		else {score -= 100000000;}

		if (cat9 <= 0){score += cat9;}
		else {score -= 100000000;}

		if (cat12 <= 0){score += cat12;}
		else {score -= 100000000;}


		score *= this.amountOfCard;

		Card c = this.Deck.elementAt(this.Deck.size()-1);
		/*if (c.getGuard()){score += 50;}
		if (c.getWard()){score += 25;}
		if (c.getDrain()){score += 50;}
		if (c.getLethal()){score += 1000;}
		if (c.getCharge()){score += 25;}
		if (c.getBreaktrough()){score += 10;}*/
		score -= c.getCost();  //c.scoreBis(30,30,3,3);//Math.abs(c.getAttack())*Math.abs(c.getDefense());

		return score;

	}

}

class Joueur{

	private int hp;
	private int mana;
	private int remainingCardsInDeck;
	private int rune;
	private Vector<Card> board;
	private Vector<Card> Hand;


	public Joueur(int hp,int mana,int rcid,int rune,Vector<Card> Hand, Vector<Card> board){
		this.hp = hp;
		this.mana = mana;
		this.remainingCardsInDeck = rcid;
		this.rune = rune;
		this.board = board;
		this.Hand = Hand;
	}

  public boolean validAnswerBis(Vector<Vector<Card>> a,Joueur opponent,Card other,Card mine){
	  	int kill = 0;
		boolean answer = true;
		Vector<Card> opponentBoard = new Vector<Card>();
		Vector <Card> b = opponent.getBoard();
		Vector<Card> gInBoard = opponent.getGuardsInBoard();
		Vector<Card> attacked = new Vector<Card>();
		int let = 0;
		int amountOfLethal = this.getLethalsInBoard().size();
		//for (int i = 0;i<b.size();i++){opponentBoard.addElement(b.elementAt(i));}
		for (int i = 0;i<a.size();i++){
			Card attacker = a.elementAt(i).elementAt(0);
			Card defender = a.elementAt(i).elementAt(1);
			int n = gInBoard.size();
			if (attacker.getLethal() == true){let++;}
			//if (attacker.getLethal() == true && defender.getWard() == true){answer = false;}
			if (attacker.getLethal() == true && attacked.contains(defender) == true && defender.getWasWard() == false ){answer = false;}
			//if (attacker.getLethal() == true && defender.getId() == -1 && b.size()>kill){answer = false;}
			attacked.add(defender);
			if (n > 0){
				if (defender.getGuard() == false){
					answer = false;
					defender.getAttackBy(attacker,this,opponent,other,mine);
					if (defender.getDefense()<= 0){kill++;}
				}
				else {
          			if (defender.getDefense()<=0){answer = false;}
					defender.getAttackBy(attacker,this,opponent,other,mine);
					if (defender.getDefense()<= 0){kill++;}
					if (defender.getDefense() <= 0){
						gInBoard.removeElement(defender);
					}
				}
			} else {
				if (defender.getGuard()){answer = false;}
				else if (defender.getDefense() <= 0 && defender.getId() > -1){answer = false;}

				defender.getAttackBy(attacker,this,opponent,other,mine);
				if (defender.getDefense()<= 0){kill++;}
			}
		}
		//if (amountOfLethal > let){answer = false;}
		//if (let == 0 && kill == 0 && b.size() > 1){answer = false;}
		for (int i = 0 ; i < this.getBoard().size() ; i++ ){
			this.getBoard().elementAt(i).resetSmall();
		}
		for (int i = 0 ; i < opponent.getBoard().size() ; i++ ){
			opponent.getBoard().elementAt(i).resetSmall();
		}
		other.resetSmall();
		mine.resetSmall();
		return answer;
	}

	public boolean validAnswerBisOpponent(Vector<Vector<Card>> a,Joueur opponent,Card other,Card mine){
		int kill = 0;
	  boolean answer = true;
	  Vector<Card> opponentBoard = new Vector<Card>();
	  Vector <Card> b = opponent.getBoard();
	  Vector<Card> gInBoard = opponent.getGuardsInBoard();
	  Vector<Card> attacked = new Vector<Card>();
	  int let = 0;
	  int amountOfLethal = this.getLethalsInBoard().size();
	  //for (int i = 0;i<b.size();i++){opponentBoard.addElement(b.elementAt(i));}
	  for (int i = 0;i<a.size();i++){
		  Card attacker = a.elementAt(i).elementAt(0);
		  Card defender = a.elementAt(i).elementAt(1);
		  int n = gInBoard.size();
		  if (attacker.getLethal() == true){let++;}
		 // if (attacker.getLethal() == true && defender.getWard() == true){answer = false;}
		  if (attacker.getLethal() == true && attacked.contains(defender) == true && defender.getWasWard() == false ){answer = false;}
		  //if (attacker.getLethal() == true && defender.getId() == -1 && b.size()>kill){answer = false;}
		  attacked.add(defender);
		  if (n > 0){
			  if (defender.getGuard() == false){
				  answer = false;
				  defender.getAttackBy(attacker,this,opponent,other,mine);
				  if (defender.getDefense()<= 0){kill++;}
			  }
			  else {
					if (defender.getDefense()<=0){answer = false;}
				  defender.getAttackBy(attacker,this,opponent,other,mine);
				  if (defender.getDefense()<= 0){kill++;}
				  if (defender.getDefense() <= 0){
					  gInBoard.removeElement(defender);
				  }
			  }
		  } else {
			  if (defender.getGuard()){answer = false;}
			  else if (defender.getDefense() <= 0 && defender.getId() > -1){answer = false;}

			  defender.getAttackBy(attacker,this,opponent,other,mine);
			  if (defender.getDefense()<= 0){kill++;}
		  }
	  }
	  //if (amountOfLethal > let){answer = false;}
	  //if (let == 0 && kill == 0 && b.size() > 1){answer = false;}
	  for (int i = a.size()-1 ;i>=0;i--){
		  Card attacker = a.elementAt(i).elementAt(0);
		  Card defender = a.elementAt(i).elementAt(1);
		  defender.getDeattackBy(attacker,this,opponent,other,mine);
	  }
	  return answer;
  }

  public void setHand(Vector<Card> Hand){
		this.Hand = Hand;
	}


	public void setBoard(Vector<Card> board){
		this.Hand = board;
	}

	public void clean(){
		for (int i =1;i<this.getBoard().size();i++ ){
			Card c = this.getBoard().elementAt(i);
			if (c.getId() == -1){
				this.getBoard().remove(c);
			}
		}
	}

	public Vector<Card> boardOrderByAttack(){
		Vector<Card> order = new Vector<Card>();
		Vector<Card> b = this.getBoard();
		while (b.size()>0){
			int bestScore = -1;
			Card best = b.elementAt(0);
			for (int i = 0;i<b.size();i++){
				int score = 0;
				Card c = b.elementAt(i);
				if (c.getLethal() == true){
					score += 99;
				}
				score += c.getAttack();

				if (score>=bestScore){
					bestScore = score;
					best = c;
				}
			}
			b.remove(best);
			order.add(best);
		}
		for (int i = 0;i<order.size();i++){
			this.getBoard().add(order.elementAt(i));
		}
		return order;
	}

	public boolean hasEmperorNightmareInBoard(){
		for (int i = 0 ; i < this.board.size() ; i++){
			if (this.board.elementAt(i).getCardNumber() == 116){
				return true;
			}
		}
		return false;
	}

	public boolean hasEmperorNightmareInHand(){
		for (int i = 0 ; i < this.Hand.size() ; i++){
			if (this.Hand.elementAt(i).getCardNumber() == 116){
				return true;
			}
		}
		return false;
	}

	public void removeEmperorFromHand(){
		for (int i = 0 ; i < this.Hand.size() ; i++){
			if (this.Hand.elementAt(i).getCardNumber() == 116){
				this.Hand.remove(this.Hand.elementAt(i));
			}
		}
		
	}
	

	public Vector<Card> getGuardsInBoardOrderByDefense(){
		Vector<Card> order = new Vector<Card>();
		Vector<Card> b = this.getGuardsInBoard();
		while (b.size()>0){
			int bestScore = -1000000;
			Card best = b.elementAt(0);
			for (int i = 0;i<b.size();i++){
				int score = 0;
				Card c = b.elementAt(i);
				if (c.getWard() == true){
					score -= 99;
				}
				score += c.getDefense();

				if (score>=bestScore){
					bestScore = score;
					best = c;
				}
			}
			b.remove(best);
			order.add(best);
		}
		
		return order;
	}

	public Vector<Card> boardOrderByDefense(){
		Vector<Card> order = new Vector<Card>();
		Vector<Card> b = this.getBoard();
		while (b.size()>0){
			int bestScore = -1000000;
			Card best = b.elementAt(0);
			for (int i = 0;i<b.size();i++){
				int score = 0;
				Card c = b.elementAt(i);
				if (c.getWard() == true){
					score -= 99;
				}
				score += c.getDefense();

				if (score>=bestScore){
					bestScore = score;
					best = c;
				}
			}
			b.remove(best);
			order.add(best);
		}
		for (int i = 0;i<order.size();i++){
			this.getBoard().add(order.elementAt(i));
		}
		return order;
	}





	public int getHp(){
		return this.hp;
	}

	public int getRemainingCardsInDeck(){
		return this.remainingCardsInDeck;
	}

	public int getMana(){
		return this.mana;
	}

	public void setMana(int mana){
		this.mana = mana;
	}

	public void setHp(int hp){
		this.hp = hp;
	}

	public Vector<Card> getBoard(){
		return this.board;
	}

	public Vector<Card> getHand(){
		return this.Hand;
	}

	public void getAttackByCard(Card c){
		this.hp -= c.getAttack();
	}

	public void getBuffByBlueCard(Card c){
		this.hp += c.getHpChange();
	}

	public void getAttackByBlueCard(Card c){
		this.hp += c.getHpChangeOpponent();
	}

	public double attackAverage(){
		double avg = 0.0;
		Vector<Card> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			avg += (double) b.elementAt(i).getAttack();
		}
		return (double) (avg)/((double)b.size());
	}

	public double bestDefense(){
		double best = 0.0;
		Vector<Card> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if ((double)b.elementAt(i).getDefense() > best){
				best = b.elementAt(i).getDefense();
			}
		}
		return (double) best;
	}

	public Vector<Card> getGuardsInBoard(){
		Vector<Card> g = new Vector<Card>();
		Vector<Card> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getGuard()){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card> getWardsInBoard(){
		Vector<Card> g = new Vector<Card>();
		Vector<Card> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getWard() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card> getDrainsInBoard(){
		Vector<Card> g = new Vector<Card>();
		Vector<Card> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getDrain() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card> getChargesInBoard(){
		Vector<Card> g = new Vector<Card>();
		Vector<Card> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getCharge() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card> getBreaktroughsInBoard(){
		Vector<Card> g = new Vector<Card>();
		Vector<Card> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getBreaktrough() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card> getLethalsInBoard(){
		Vector<Card> g = new Vector<Card>();
		Vector<Card> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getLethal() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

  public static Vector<Vector<Integer>> permute(int[] arr){

  	Vector<Vector<Integer>> p = new Vector<Vector<Integer>>();
      permuteHelper(arr, 0,p);
      return p;
  }

  private static void permuteHelper(int[] arr, int index, Vector<Vector<Integer>> perm){
      if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute
          //System.out.println(Arrays.toString(arr));
          //Print the array
      	Vector<Integer> Solution = new Vector<Integer>();
      	for (int j = 0;j<arr.length;j++) {
      		Solution.addElement(arr[j]);
      	}
      	perm.addElement(Solution);

      }

      for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]

          //Swap the elements at indices index and i
          int t = arr[index];
          arr[index] = arr[i];
          arr[i] = t;

          //Recurse on the sub array arr[index+1...end]
          permuteHelper(arr, index+1,perm);

          //Swap the elements back
          t = arr[index];
          arr[index] = arr[i];
          arr[i] = t;
      }
  }

  public Vector<Vector<Integer>> indicePermutation(int n){
  	int[] arr = new int[n];
  	for (int i =0;i<n;i++) {
  		arr[i]=i;
  	}
  	Vector<Vector<Integer>> indice = permute(arr);
  	return indice;
  }

}

class Turn{

	private int tour;
	private Joueur me;
	private Joueur other;


	public Turn(int tour,Joueur me, Joueur other){
		this.tour = tour;
		this.me = me;
		this.other = other;
	}


  public void incrTurn(){
		this.tour++;
	}

	public void setTurn(int Turn){
		this.tour = Turn;
	}

	public int getTurn(){
		return this.tour;
	}

	public Joueur getMe(){
		return this.me;
	}

	public Joueur getOther(){
		return this.other;
	}

	public void afficher(){
		////System.err.println("Le tour est constitué de ");
		Vector<Card> myBoard = this.me.getBoard();
		Vector<Card> hisBoard = this.other.getBoard();

		////System.err.println("Mon board ");
		for (int i = 0;i<myBoard.size();i++){
			Card c = myBoard.elementAt(i);
			////System.err.println("Carte "+c.getId()+" d'attaque "+c.getAttack()+" de defense "+c.getDefense()+" d'abilities "+c.getAbilities());
		}


		////System.err.println("Son board ");
		for (int i = 0;i<hisBoard.size();i++){
			Card c = hisBoard.elementAt(i);
			////System.err.println("Carte "+c.getId()+" d'attaque "+c.getAttack()+" de defense "+c.getDefense()+" d'abilities "+c.getAbilities());
		}
	}


  public static Vector<Vector<Integer>> permute(int[] arr){

  	Vector<Vector<Integer>> p = new Vector<Vector<Integer>>();
      permuteHelper(arr, 0,p);
      return p;
  }

  private static void permuteHelper(int[] arr, int index, Vector<Vector<Integer>> perm){
      if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute
          //System.out.println(Arrays.toString(arr));
          //Print the array
      	Vector<Integer> Solution = new Vector<Integer>();
      	for (int j = 0;j<arr.length;j++) {
      		Solution.addElement(arr[j]);
      	}
      	perm.addElement(Solution);

      }

      for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]

          //Swap the elements at indices index and i
          int t = arr[index];
          arr[index] = arr[i];
          arr[i] = t;

          //Recurse on the sub array arr[index+1...end]
          permuteHelper(arr, index+1,perm);

          //Swap the elements back
          t = arr[index];
          arr[index] = arr[i];
          arr[i] = t;
      }
  }

  public Vector<Vector<Integer>> indicePermutation(int n){
  	int[] arr = new int[n];
  	for (int i =0;i<n;i++) {
  		arr[i]=i;
  	}
  	Vector<Vector<Integer>> indice = permute(arr);
  	return indice;
  }


  public double getMyTempo(Card opponent,Card mine){
	if (opponent.getDefense() <= 0){return (double)100000.0;}
	Vector<Card> g = this.other.getBoard();
	double lifeOpponent = opponent.getDefense();//(double)this.other.getHp();
	

	double score;
	for (int i = 0;i<g.size();i++){
		if (g.elementAt(i).getId()>-1){lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());}
		if (g.elementAt(i).getGuard() == true){lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());}

	}
	
	Vector<Card> b = this.me.getBoard();
	double myAttack = (double)0;
	for (int i = 0;i<b.size();i++){
		if (b.elementAt(i).getDefense()>0) {myAttack += (double) Math.max(0,b.elementAt(i).getAttack());}
	}
	double bestDefense = this.other.bestDefense();
	double avgAttack = this.me.attackAverage();
	Vector<Card> w = this.other.getWardsInBoard();
	Vector<Card> l = this.me.getLethalsInBoard();

	lifeOpponent += (double)w.size()*avgAttack;
	//lifeOpponent -= (double)l.size()*(bestDefense);
	
	Vector<Card> b1 = this.me.getBreaktroughsInBoard();
	myAttack += (double) b1.size();
	Vector<Card> d = this.me.getDrainsInBoard();
	double tempTempo = (double)(lifeOpponent+0.1)/(myAttack+0.1);
	for (int i = 0;i<d.size();i++){
		myAttack += (tempTempo/(double)2.0) * (double)d.elementAt(i).getAttack();
	}

	score = (double)(lifeOpponent+0.1)/(myAttack+0.1);
	return (double) (2.0)/(score);
}

public double getHisTempo(Card opponent,Card mine){
	if (mine.getDefense() <= 0){return (double)10000.0;}
	Vector<Card> g = this.me.getBoard();
	double lifeOpponent = mine.getDefense();
	double score;
	for (int i = 0;i<g.size();i++){
		if (g.elementAt(i).getId()>-1){lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());}
		if (g.elementAt(i).getGuard() == true){lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());}

	}
	
	Vector<Card> b = this.other.getBoard();
	double myAttack = (double)0;
	for (int i = 0;i<b.size();i++){
		if (b.elementAt(i).getDefense()>0) {myAttack += (double) Math.max(0,b.elementAt(i).getAttack());}
	}
	double bestDefense = this.me.bestDefense();
	double avgAttack = this.other.attackAverage();
	Vector<Card> w = this.me.getWardsInBoard();
	Vector<Card> l = this.other.getLethalsInBoard();

	lifeOpponent += (double)w.size()*avgAttack;
	lifeOpponent -= (double)l.size()*(bestDefense);
	
	Vector<Card> b1 = this.other.getBreaktroughsInBoard();
	myAttack += (double) b1.size();
	Vector<Card> d = this.other.getDrainsInBoard();
	double tempTempo = (double)(lifeOpponent+0.1)/(myAttack+0.1);
	for (int i = 0;i<d.size();i++){
		myAttack += (tempTempo/(double)2.0) * (double)d.elementAt(i).getAttack();
	}

	score = (double)(lifeOpponent+0.1)/(myAttack+0.1);
	return (double) (2.0)/(score);
}

public double getTurnScore(Card opponent,Card mine){
	double myScore =  this.getMyTempo(opponent, mine);//this.getMyBoardScore(opponent,mine);
	double hisScore = this.getHisTempo(opponent, mine); //this.getHisBoardScore(opponent,mine);	
	int deltaHand = this.me.getHand().size() - this.other.getHand().size() - 1;
	int deltaBoard = 0;
	for (int i = 0;i<this.me.getBoard().size();i++){
		if (this.me.getBoard().elementAt(i).getDefense()>0){
			deltaBoard++;
		}
	}
	for (int i = 0;i<this.other.getBoard().size();i++){
		if (this.other.getBoard().elementAt(i).getDefense()>0){
			deltaBoard--;
		}
	}

	int runeDropped = 0;
	int oldHp = opponent.getOldDefense();
	int hp = opponent.getDefense();

	if (oldHp > 25 && hp <= 25){runeDropped++;}
	if (oldHp > 20 && hp <= 20){runeDropped++;}
	if (oldHp > 15 && hp <= 15){runeDropped++;}
	if (oldHp > 10 && hp <= 10){runeDropped++;}
	if (oldHp > 5 && hp <= 5){runeDropped++;}
	
	if (deltaHand + deltaBoard - runeDropped < 0){return myScore - hisScore-50;}
	else {return myScore - hisScore;}
	
}

  
	public double getMyBoardScore(Card opponent,Card mine,int PlayerNumber,int TOUR){
	    
		int hp = mine.getDefense();
		int hpOpponent = opponent.getDefense();
		if (hpOpponent <= 0){return (double)100000.0;}

		int nbrCards = this.me.getBoard().size();
		int nbrCardsOpponent = this.other.getBoard().size();
		double score = (double)hp;
    //////System.err.println("nbr mes Cards : "+nbrCards);
		for (int i = 0;i<nbrCards;i++){
      if (this.me.getBoard().elementAt(i).getAttack() == 0 && this.me.getBoard().elementAt(i).getGuard() == false){
				score -= 300;
			}
			if(this.me.getBoard().elementAt(i).getId()>-1 && this.me.getBoard().elementAt(i).getDefense() > 0){ score += this.me.getBoard().elementAt(i).scoreBis(hp,hpOpponent,nbrCards,nbrCardsOpponent,PlayerNumber,TOUR);}
		}

		return score;
	}

  
	public double getHisBoardScore(Card opponent,Card mine,int PlayerNumber,int TOUR){
	    
		int hp = mine.getDefense();
		if (hp <= 0){return (double)100000.0;}
		int hpOpponent = opponent.getDefense();
		int nbrCards = this.me.getBoard().size();
		int nbrCardsOpponent = this.other.getBoard().size();
		double score = (double)hpOpponent;
    //////System.err.println("nbr ses Cards : "+nbrCardsOpponent);

		for (int i = 1;i<nbrCardsOpponent;i++){
      		if (this.other.getBoard().elementAt(i).getAttack() == 0 && this.other.getBoard().elementAt(i).getGuard() == false){
				score -= 300.0;
			}
			if (this.other.getBoard().elementAt(i).getId()>-1 && this.other.getBoard().elementAt(i).getDefense() > 0){score += (double)this.other.getBoard().elementAt(i).scoreBis(hpOpponent,hp,nbrCardsOpponent,nbrCards,PlayerNumber,TOUR);}
			
		}
		
		return score;
	}


	public double getTurnScoreSummon(Card opponent,Card mine,int PlayerNumber,int TOUR){
		double myScore = this.getMyBoardScore(opponent,mine,PlayerNumber,TOUR);
		double hisScore =0;// this.getHisBoardScore(opponent,mine,PlayerNumber,TOUR);
		return myScore - hisScore;
	}

	public Card bestUseOfGreenCard1(Card greenItem,Card opponent,Card mine,Vector<Card> summonWithoutCharge){
		
		Vector<Card> b = new Vector<Card>();
  		b.addAll(this.me.getBoard());
  		b.addAll(summonWithoutCharge);
		Card bestAnswer = new Card(0);
		boolean isItReally = true;
		int bestScore = -1;

		if (greenItem.getDefense()>=greenItem.getAttack()){
			for (int i = 0;i<b.size();i++){
				Card c = b.elementAt(i);
				if (c.getAttack()>=bestScore  && c.getId() != -1){
					if (greenItem.getGuard() && c.getGuard()){
						isItReally = false;
					}

					if (greenItem.getCharge() && c.getCharge()){
						isItReally = false;
					}

					if (greenItem.getDrain() && c.getDrain()){
						isItReally = false;
					}

					if (greenItem.getBreaktrough() && c.getBreaktrough()){
						isItReally = false;
					}

					if (greenItem.getWard() && c.getWard()){
						isItReally = false;
					}

					if (greenItem.getLethal() && c.getLethal()){
						isItReally = false;
					}

					if (isItReally){
						bestAnswer = c;
						bestScore = c.getAttack();
					}

				}
			}



		} else {

			for (int i = 0;i<b.size();i++){
				Card c = b.elementAt(i);
				if (c.getDefense()>=bestScore && c.getId() != -1){
					if (greenItem.getGuard() && c.getGuard()){
						isItReally = false;
					}

					if (greenItem.getCharge() && c.getCharge()){
						isItReally = false;
					}

					if (greenItem.getDrain() && c.getDrain()){
						isItReally = false;
					}

					if (greenItem.getBreaktrough() && c.getBreaktrough()){
						isItReally = false;
					}

					if (greenItem.getWard() && c.getWard()){
						isItReally = false;
					}

					if (greenItem.getLethal() && c.getLethal()){
						isItReally = false;
					}

					if (isItReally){
						bestAnswer = c;
						bestScore = c.getDefense();}

				}
			}

		}
		

		return bestAnswer;



	}

	public Card bestUseOfRedCard1(Card redItem,Card opponent,Card mine){
		Vector<Card> b = this.other.getBoard();
		Card bestAnswer = new Card(0);
		boolean isItReally = false;
		boolean changeAbilities = !(redItem.getAbilities().equals("------"));
		int bestScore = -1;
		////System.err.println(changeAbilities);

		if (redItem.getCardNumber() == 151){
			bestScore = 6;
			for (int i = 0;i<b.size();i++){
				Card c = b.elementAt(i);
				int scoreC = c.getDefense();
				if (c.getLethal() == true){
					scoreC += 99;
				}
				if (scoreC>=bestScore){
					if (redItem.getGuard() && c.getGuard()){
						isItReally = true;
					}

					if (redItem.getWard() && c.getWard()){
						isItReally = true;
					}

					if (redItem.getLethal() && c.getLethal()){
						isItReally = true;
					}

				
					if (isItReally){
						bestAnswer = c;
						bestScore = scoreC;
					}

				}
			}
		}

		else if (changeAbilities == true){
			for (int i = 0;i<b.size();i++){
				Card c = b.elementAt(i);
				int scoreC = c.getAttack();
				if (c.getLethal() == true){
					scoreC += 99;
				}
				if (scoreC>=bestScore){
					if (redItem.getGuard() && c.getGuard()){
						isItReally = true;
					}

					if (redItem.getWard() && c.getWard()){
						isItReally = true;
					}

					if (redItem.getLethal() && c.getLethal()){
						isItReally = true;
					}

				
					if (isItReally){
						bestAnswer = c;
						bestScore = scoreC;
					}

				}
			}



		} else {

			for (int i = 0;i<b.size();i++){
				Card c = b.elementAt(i);
				//score = c.getAttack();
				if (c.getAttack() >= bestScore && c.getWard() == false){

				//if (c.getAttack() >= bestScore && c.getWard() == false && c.getDefense() <= -redItem.getDefense() && c.getDefense() >= -redItem.getDefense() - 2){
					bestScore = c.getAttack();
					bestAnswer =c;
				}
				else if ( c.getWard() == true && (redItem.getDefense() == -1 ||redItem.getDefense() == -2)  ){
					bestAnswer =c;
					bestScore = 99;
				}


			}

		}
		

		return bestAnswer;



	}






	
  public Card bestUseOfGreenCard(Card greenItem,Card opponent,Card mine,Vector<Card> summonWithoutCharge){
		
		Vector<Card> b = new Vector<Card>();
  		b.addAll(this.me.getBoard());
		b.addAll(summonWithoutCharge);
		Card bestAnswer = new Card(0);
		if (b.size() > 0){
			int indiceUse = ThreadLocalRandom.current().nextInt(0,b.size());
			bestAnswer = b.elementAt(indiceUse);
		}
		
		boolean isItReally = true;
		int bestScore = -1;

		
		

		return bestAnswer;
	}

	public Card bestUseOfRedCard(Card redItem,Card opponent,Card mine){
		Vector<Card> b = this.other.getBoard();
		if (redItem.getCardNumber() == 142 || redItem.getCardNumber() == 149){
			Vector<Card> possible = new Vector<Card>();
			for (int i = 0 ; i < b.size() ; i++){
				if (b.elementAt(i).getGuard() || b.elementAt(i).getWard() || b.elementAt(i).getLethal()){
					possible.add(b.elementAt(i));
				}
			}
			b = possible;
		} else if (redItem.getCardNumber() == 143){
			Vector<Card> possible = new Vector<Card>();
			for (int i = 0 ; i < b.size() ; i++){
				if (b.elementAt(i).getGuard()){
					possible.add(b.elementAt(i));
				}
			}
			b = possible;
		}
		Card bestAnswer = new Card(0);
		if (b.size() > 0){
			int indiceUse = ThreadLocalRandom.current().nextInt(0,b.size());
			bestAnswer = b.elementAt(indiceUse);
		}
		
		return bestAnswer;

	}

	public Vector<Vector<Card>> generateRandomAttack(int compteur,long startTime,Card opponent, Card mine){


		int n = this.me.getBoard().size();
		Vector<Card> hisBoard = new Vector<Card>();
		Vector<Card> hisGuard = new Vector<Card>();
		
		int sizeAttack = n;
		if (n>0){
			sizeAttack = ThreadLocalRandom.current().nextInt(1,n+1);
		}
		for (int i = 0 ; i < this.other.getBoard().size() ; i++){
			hisBoard.addElement(this.other.getBoard().elementAt(i));
			if (this.other.getBoard().elementAt(i).getGuard() == true){
				hisGuard.addElement(this.other.getBoard().elementAt(i));
			}
		}
	
		Vector<Card> damaged = new Vector<Card>();
	
	
		Vector<Integer> indice = new Vector<Integer>();
			for (int i = 0;i<n;i++){
				indice.addElement(i);
			}
		Vector<Vector<Card>> renvoie = new Vector<Vector<Card>>();
		
			while (indice.size() > n - sizeAttack){
				int randomNum = ThreadLocalRandom.current().nextInt(0, indice.size());
				int indiceAttack = indice.elementAt(randomNum);
				Card attacker = this.me.getBoard().elementAt(indiceAttack);
				int indiceDef;
				Card defender;
	
				if (damaged.size() > 0){
					indiceDef = (int)ThreadLocalRandom.current().nextInt(0, damaged.size());
					defender = damaged.elementAt(indiceDef);
				}
				else if (hisGuard.size() > 0){
					indiceDef = (int)ThreadLocalRandom.current().nextInt(0, hisGuard.size());
					defender = hisGuard.elementAt(indiceDef);
				}
				else {
					indiceDef = (int)ThreadLocalRandom.current().nextInt(0, hisBoard.size());
					defender = hisBoard.elementAt(indiceDef);
				}
	
				defender.getAttackBy(attacker,this.me,this.other,opponent,mine);
	
				if (defender.getDefense() <= 0 && defender.getId() > -1){
					if (damaged.contains(defender)){
						damaged.remove(defender);
					}
					if (defender.getGuard() == true){
						hisGuard.remove(defender);
						hisBoard.remove(defender);
					} else {
						hisBoard.remove(defender);
					}
				} else if (defender.getWasWard() == false && defender.getId() > -1 ){
					if (!damaged.contains(defender)){damaged.add(defender);}
					
				}
	
				Vector<Card> att = new Vector<Card>();
				att.addElement(attacker);
				att.addElement(defender);
				renvoie.addElement(att);
				indice.removeElement(indiceAttack);
			}
		for (int i = 0 ; i < this.other.getBoard().size() ; i++){
			this.other.getBoard().elementAt(i).resetSmall();
		}
		return renvoie;
	}

public Vector<Vector<Card>> generateRandomAttackOpponent(int compteur,long startTime,Card opponent, Card mine){


	int n = this.other.getBoard().size();
	Vector<Card> hisBoard = new Vector<Card>();
	Vector<Card> hisGuard = new Vector<Card>();
    
    int sizeAttack = n;
    if (n>0){
	    sizeAttack = ThreadLocalRandom.current().nextInt(1,n+1);
    }
	for (int i = 0 ; i < this.me.getBoard().size() ; i++){
		hisBoard.addElement(this.me.getBoard().elementAt(i));
		if (this.me.getBoard().elementAt(i).getGuard() == true){
			hisGuard.addElement(this.me.getBoard().elementAt(i));
		}
	}

	Vector<Integer> indice = new Vector<Integer>();
		for (int i = 0;i<n;i++){
			indice.addElement(i);
		}
	Vector<Vector<Card>> renvoie = new Vector<Vector<Card>>();
	
		while (indice.size() > n - sizeAttack){
			int randomNum = ThreadLocalRandom.current().nextInt(0, indice.size());
			int indiceAttack = indice.elementAt(randomNum);
			Card attacker = this.other.getBoard().elementAt(indiceAttack);
			int indiceDef;
			Card defender;

			if (hisGuard.size() > 0){
				indiceDef = (int)ThreadLocalRandom.current().nextInt(0, hisGuard.size());
				defender = hisGuard.elementAt(indiceDef);
			}
			else {
 				indiceDef = (int)ThreadLocalRandom.current().nextInt(0, hisBoard.size());

				defender = hisBoard.elementAt(indiceDef);
			}

			defender.getAttackBy(attacker,this.other,this.me,mine,opponent);

			if (defender.getDefense() <= 0 && defender.getId() > -1){
				if (defender.getGuard() == true){
					hisGuard.remove(defender);
					hisBoard.remove(defender);
				} else {
					hisBoard.remove(defender);
				}
			}

			Vector<Card> att = new Vector<Card>();
			att.addElement(attacker);
			att.addElement(defender);
			renvoie.addElement(att);
			indice.removeElement(indiceAttack);
		}
	for (int i = 0 ; i < this.me.getBoard().size() ; i++){
		this.me.getBoard().elementAt(i).resetSmall();
	}
	return renvoie;
}


public Vector<Vector<Card>> bestRandomAttackOpponent(Card opponent,Card mine){
	long startTimeOpponent = System.nanoTime();
	int iteration = 0;
	int testValide = 0;
	//double bestScore = -100000.0;
	//double bestScoreSecond = -100000.0;
	//int bestScoreBis = - 100000;
	int bestScore = -100000;
	
	//////System.err.println("init best score : "+bestScore);
	double bestScoreBis = -100000.0;
	
	Vector<Vector<Card>> bestAnswer = new Vector<Vector<Card>>();
	long endTimeOpponent = System.nanoTime();
	Vector<Vector<Vector<Card>>> tested = new Vector<Vector<Vector<Card>>>();
	//////System.err.println("mon board est de taille : "+ this.me.getBoard().size());
	while ((endTimeOpponent - startTimeOpponent) <300000 && iteration < 10){
	    //////System.err.println("iterations : "+iteration+" et le temps :"+(endTimeOpponent - startTimeOpponent));
		iteration++;
		/*if (this.me.getBoard().size() == 0){
			////System.err.println(mine.getDefense());
		}*/
		Vector<Vector<Card>> attaque = this.generateRandomAttackOpponent(0,startTimeOpponent,opponent,mine);
		
		//if (!(tested.contains(attaque)) && this.other.validAnswerBis(attaque,this.me,mine,opponent)){
		if (!(tested.contains(attaque))){
			Vector<Card> myDead = new Vector<Card>();
			Vector<Card> hisDead = new Vector<Card>();
			tested.addElement(attaque);
			testValide++;
			this.hisAttack(attaque,opponent,mine,myDead,hisDead);

		
			int score = 0;
			
		for (int m = 0 ; m < this.me.getBoard().size();m++){
			Card c = this.me.getBoard().elementAt(m);
			if (c.getDefense() >0 && c.getId() > -1){
				//scoreBis++;
				score--;
				score -= c.scoreTest(1,opponent.getDefense(),mine.getDefense());
			}
		}
		for (int m = 0 ; m < this.other.getBoard().size();m++){
			Card c = this.other.getBoard().elementAt(m);
			if (c.getDefense() >0 && c.getId() > -1){
				//scoreBis--;
				score++;
				score += c.scoreTest(0,opponent.getDefense(),mine.getDefense());
				
			}
		}
		if (opponent.getDefense()<=0){
			score += 10000;
		}
		if (mine.getDefense()<=0){
			score -= 10000;
		}
			//////System.err.println("best Score : "+bestScore+" score actuel : "+score);
			
			//double tempScoreSecond = getTurnScore(opponent,mine);//this.getTurnScore(opponent, mine);
			//double tempScoreSecond = mine.getDefense() - opponent.getDefense();
			//double scoreBis = this.getTurnScore(opponent, mine);
			if (score > bestScore){
				bestScore = score;
				bestAnswer = attaque;
			}
			else if (this.areEqual(score,bestScore)){
				double scoreBis = opponent.getDefense() - mine.getDefense();
				if (scoreBis > bestScoreBis){bestScoreBis = scoreBis;
					bestAnswer = attaque;}
				

			}
			
			opponent.resetSmall();
			mine.resetSmall();
			
			/*for (int j = hisNextAttack.size()-1;j>=0;j--){
				Card attacker = hisNextAttack.elementAt(j).elementAt(0);
				Card defender = hisNextAttack.elementAt(j).elementAt(1);
	  
				defender.getDeattackBy(attacker,this.me,this.other,opponent,mine);
				defender.deCounterAttack(attacker,this.me,this.other);

			}*/
			
			for (int i = attaque.size()-1;i >= 0;i--){
				Card attacker = attaque.elementAt(i).elementAt(0);
				Card defender = attaque.elementAt(i).elementAt(1);
				if (defender.getDefense() <= 0 && defender.getId() > -1){
					this.me.getBoard().add(defender);
				}
				if (attacker.getDefense() <= 0 && attacker.getId() > -1){
					this.other.getBoard().add(attacker);
				}
				defender.getDeattackBy(attacker,this.other,this.me,mine,opponent);
				defender.deCounterAttack(attacker,this.other,this.me);
			}
		}
		endTimeOpponent = System.nanoTime();
		//System.gc();
	}
	    //////System.err.println("nombre de test  --> "+iteration+ "en "+(endTimeOpponent - startTimeOpponent)+ "ns");
		////System.err.println("nombre de test valide --> "+testValide);
	opponent.resetSmall();
	mine.resetSmall();
/////System.err.println("opponent realise : "+iteration);
	return bestAnswer;
}




public boolean areEqual(double a,double b){
	if (a < b){
		if (b-a<0.0000001){
			return true;
		} else {
			return false;
		}
	} else {
		if (a-b<0.0000001){
			return true;
		} else {
			return false;
		}
	}
}

	public Vector<Vector<Card>> attackBasicSolutionDirect(Card opponent,Card mine){
		Vector<Vector<Card>> renvoie = new Vector<Vector<Card>>();
		Vector<Card> b = this.other.boardOrderByAttack();
		Vector<Card> bOpponent = this.me.getBoard();
		Vector<Card> g = this.me.getGuardsInBoard();

		for (int i = 0;i<b.size();i++){
			if (!b.elementAt(i).getUsed() && b.elementAt(i).getId()>-1){
			if (g.size() == 0){

				Vector<Card> att = new Vector<Card>();
				att.addElement(b.elementAt(i));
				att.addElement(mine);
				renvoie.addElement(att);
			}
			else {

				Card c = g.firstElement();

				c.getAttackBy(b.elementAt(i),this.other,this.me,mine,opponent);
				if (c.getDefense() <= 0){g.removeElementAt(0);}
				Vector<Card> att = new Vector<Card>();
				att.addElement(b.elementAt(i));
				att.addElement(c);
				renvoie.addElement(att);
			}
		}
	}
		for (int i = 0 ; i < renvoie.size() ; i++){
			Card attacker = renvoie.elementAt(i).elementAt(0);
			Card defender = renvoie.elementAt(i).elementAt(1);
			attacker.resetSmall();
			defender.resetSmall();

		}
		return renvoie;
	}

	public Vector<Vector<Card>> attackDirecte(Card opponent,Card mine){
		Vector<Vector<Card>> renvoie = new Vector<Vector<Card>>();
		Vector<Card> b = this.me.boardOrderByAttack();
		Vector<Card> bOpponent = this.other.getBoard();
		Vector<Card> g = this.other.getGuardsInBoard();
		Vector<Card> attacked = new Vector<Card>();
		for (int i = 0;i<b.size();i++){
			
			
			if (g.size() == 0){

				Vector<Card> att = new Vector<Card>();
				att.addElement(b.elementAt(i));
				att.addElement(opponent);
				renvoie.addElement(att);
			}
			else {

				Card c = g.firstElement();

				c.getAttackBy(b.elementAt(i),this.me,this.other,opponent,mine);
				attacked.add(c);
				if (c.getDefense() <= 0){g.removeElementAt(0);}
				Vector<Card> att = new Vector<Card>();
				att.addElement(b.elementAt(i));
				att.addElement(c);
				renvoie.addElement(att);
			}
		
	}
	for (int i = 0 ; i < attacked.size() ; i++){
		attacked.elementAt(i).resetSmall();
	}
		return renvoie;
	}
public void updateItem(Vector<Card> tempUseGreenItem,Vector<Card> tempUseRedItem){
	for (int i = 0 ; i < tempUseGreenItem.size() ; i++){
		tempUseGreenItem.elementAt(i).upgradeLife();
	}
	for (int i = 0 ; i < tempUseRedItem.size() ; i++){
		tempUseRedItem.elementAt(i).upgradeLife();
	}
}
public void summonThings(Vector<Card> summoning,Vector<Card> summonWithoutCharge,Card opponent, Card mine,Vector<Card> tempGreenItem,Vector<Card> tempRedItem,Vector<Card> tempUseGreenItem,Vector<Card> tempUseRedItem){
	
	if (summoning.size() > 0){
		////System.err.print("summoning : ");
		for (int j = 0;j<summoning.size();j++){
			Card c = summoning.elementAt(j);
			
			////System.err.print(" ; "+c.getId()+" ; ");
			if (c.getCardType() < 1){
				if (c.getCharge() == true){this.me.getBoard().addElement(c);}
				else {summonWithoutCharge.addElement(c);}
				opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setDefense(mine.getDefense() + c.getHpChange());
				opponent.setOldDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setOldDefense(mine.getDefense() + c.getHpChange());
				//////System.err.print("Go FOR "+c.getId()+" ; ");
				
				
			}
			else if (c.getCardType() == 1 && this.me.getBoard().size()>0){
				Card bestUse = this.bestUseOfGreenCard(c, opponent, mine,summonWithoutCharge);
				if (bestUse.getId() >-1){
					
					tempUseGreenItem.addElement(bestUse);
					tempGreenItem.addElement(c);
					bestUse.getBuffByGreenCard(c);
					//////System.err.print("USE"+" "+c.getId()+" "+bestUse.getId()+" ; ");
				}
				
			}
			else if (c.getCardType() == 2 && this.other.getBoard().size()>1){
				Card bestUse = bestUseOfRedCard(c, opponent, mine);
				if (bestUse.getId()>-1){
					
					tempUseRedItem.addElement(bestUse);
					tempRedItem.addElement(c);
					//////System.err.println("USE"+" "+c.getId()+" "+bestUse.getId()+" ; ");
					bestUse.getBuffByRedCard(c);
					if (bestUse.getDefense() <= 0){
						this.other.getBoard().remove(bestUse);
					}
					opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
					mine.setDefense(mine.getDefense() + c.getHpChange());
					opponent.setOldDefense(opponent.getDefense() +c.getHpChangeOpponent());
					mine.setOldDefense(mine.getDefense() + c.getHpChange());
				} 
				
			} else if(c.getCardType() == 3) {
				
				//////System.err.println("USE"+" "+c.getId()+" ; ");
				opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setDefense(mine.getDefense() + c.getHpChange());
				opponent.setOldDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setOldDefense(mine.getDefense() + c.getHpChange());
				
			}

		}
	}
}

public void unSummonThings(Vector<Card> summoning,Vector<Card> summonWithoutCharge,Card opponent, Card mine,Vector<Card> tempGreenItem,Vector<Card> tempRedItem,Vector<Card> tempUseGreenItem,Vector<Card> tempUseRedItem){
	for (int j = summoning.size()-1;j>=0;j--){
		Card c = summoning.elementAt(j);
		if (c.getCardType() < 1){
			if (c.getCharge() == true){this.me.getBoard().removeElement(c);}
			else {summonWithoutCharge.removeElement(c);}
		
		} else if(c.getCardType() == 3) {
			//Card reverseC = new Card(c);
			//this.me.getBoard().removeElement(reverseC);
			opponent.setDefense(opponent.getDefense() -c.getHpChangeOpponent());
				mine.setDefense(mine.getDefense() - c.getHpChange());
		}

	}
	for (int m = 0;m<tempGreenItem.size();m++){
		Card objet = tempGreenItem.elementAt(m);
		Card bestUse = tempUseGreenItem.elementAt(m);
		bestUse.getDebuffByGreenCard(objet);
	}

	for (int m = 0 ; m<tempRedItem.size();m++){
		Card objet = tempRedItem.elementAt(m);
		Card bestUse = tempUseRedItem.elementAt(m);
		if (bestUse.getDefense()<=0 && !this.other.getBoard().contains(bestUse)){
			this.other.getBoard().add(bestUse);
		}
		bestUse.getDebuffByRedCard(objet);		
	}
}

public void myAttack(Vector<Vector<Card>> attaque,Card opponent,Card mine,Vector<Card> myDead,Vector<Card> hisDead){
		////System.err.println("");
		////System.err.print("Mon attaque ");
		for (int j = 0;j<attaque.size();j++){
			Card attacker = attaque.elementAt(j).elementAt(0);
			Card defender = attaque.elementAt(j).elementAt(1);
			////System.err.print(attacker.getId()+" "+defender.getId()+" ; ");
			defender.getAttackBy(attacker,this.me,this.other,opponent,mine);
			defender.counterAttack(attacker,this.me,this.other);
  			if (defender.getDefense() <=0 && defender.getId() > -1){
				this.other.getBoard().removeElement(defender);
				hisDead.add(defender);
  			}
  			if (attacker.getDefense() <=0 && attacker.getId() > -1){
				this.me.getBoard().removeElement(attacker);
				myDead.add(attacker);
  			}
		}
}

public void hisAttack(Vector<Vector<Card>> hisNextAttack,Card opponent,Card mine,Vector<Card> myDead,Vector<Card> hisDead){
		////System.err.println("");
		//////System.err.print("Son attaque ");
        for (int j = 0;j<hisNextAttack.size();j++){
					Card attacker = hisNextAttack.elementAt(j).elementAt(0);
					Card defender = hisNextAttack.elementAt(j).elementAt(1);
					//////System.err.print(attacker.getId()+" "+defender.getId()+" ; ");
					defender.getAttackBy(attacker,this.me,this.other,opponent,mine);
					defender.counterAttack(attacker,this.me,this.other);
					  if (defender.getDefense() <=0 && defender.getId() > -1){
						this.me.getBoard().removeElement(defender);
						myDead.add(defender);
					  }
					  if (attacker.getDefense() <=0 && attacker.getId() > -1){
						this.other.getBoard().removeElement(attacker);
						hisDead.add(attacker);
					  }

				}
}

public void update(Vector<Card> summonWithoutCharge){
	for (int me = 0;me<summonWithoutCharge.size();me++){
		this.me.getBoard().add(summonWithoutCharge.elementAt(me));
	}

	for (int me = 0 ; me < this.me.getBoard().size() ; me++){
		this.me.getBoard().elementAt(me).update();
	}
	for (int me = 0 ; me < this.other.getBoard().size() ; me++){
		this.other.getBoard().elementAt(me).update();
	}
}

public void downgrade(Vector<Card> summonWithoutCharge,Vector<Card> myDead,Vector<Card> hisDead){
	for (int i = 0 ; i < myDead.size() ; i++){
		this.me.getBoard().add(myDead.elementAt(i));
	}
	myDead.clear();
	for (int i = 0 ; i < hisDead.size() ; i++){
		this.other.getBoard().add(hisDead.elementAt(i));
	}
	hisDead.clear();
	
	for (int i = 0 ; i < summonWithoutCharge.size() ; i++){
		this.me.getBoard().remove(summonWithoutCharge.elementAt(i));
	}
}

public void resetFull(Vector<Card> summonWithoutCharge){
	for (int m = 0 ; m < this.me.getBoard().size();m++){
		Card c = this.me.getBoard().elementAt(m);
		c.reset();
	}
	for (int m = 0 ; m < this.me.getHand().size();m++){
		Card c = this.me.getHand().elementAt(m);
		c.reset();
	}
	for (int m = 0 ; m < summonWithoutCharge.size();m++){
		Card c = summonWithoutCharge.elementAt(m);
		c.reset();
	}
	for (int m = 0 ; m < this.other.getBoard().size();m++){
		Card c = this.other.getBoard().elementAt(m);
		c.reset();
	}
	this.me.clean();
	this.other.clean();
}


public String bestAttackRandomDepthSummon(String a,long startTime,Vector<Card> summonWithoutCharge,Card mine){
	int mana = this.me.getMana();
	Hand Hand = new Hand(this.me.getHand());
	int nbrCarte = this.me.getBoard().size();
	int compteur = 0;
	int compteurGlobal = 0;
	Vector<Card> bestSummon = new Vector<Card>();
	Vector<Vector<Card>> bestAnswer = new Vector<Vector<Card>>();
  	Card opponent = this.other.getBoard().elementAt(0);
	//double bestScore = -10000000.0;//this.getTurnScore(opponent,mine);
	//double bestScoreSecond = -1000000.0;
	//int bestScoreBis = - 100000;  
	int bestScoreBis = -10000;
	//init initScore = -1000000;////this.getTurnScore(opponent,mine);
	int bestScore = -100;
	double bestScoreSecond = - 100000;  
	Vector<Card> bestGreenItem = new Vector<Card>();
	Vector<Card> bestRedItem = new Vector<Card>();
	Vector<Card> bestUseGreenItem = new Vector<Card>();
	Vector<Card> bestUseRedItem = new Vector<Card>();
	long endTime = System.nanoTime();
	

	while ((endTime - startTime) <95*1000000){
		Vector<Card> summoning = Hand.randomSummon(mana, nbrCarte,this.me);
		Vector<Card> tempGreenItem = new Vector<Card>();
		Vector<Card> tempRedItem = new Vector<Card>();
		Vector<Card> tempUseGreenItem = new Vector<Card>();
		Vector<Card> tempUseRedItem = new Vector<Card>();
		Vector<Card> myDead = new Vector<Card>();
		Vector<Card> hisDead = new Vector<Card>();
		Vector<Card> hugeSummon = new Vector<Card>();

		for (int i = 0 ; i < summoning.size() ; i++){
			if (summoning.elementAt(i).getCost() >=7 && summoning.elementAt(i).getCardType() == 0 && summoning.elementAt(i).getCharge() == false){
				hugeSummon.add(summoning.elementAt(i));
			}
		}
		compteurGlobal++;
		
		this.summonThings(summoning,summonWithoutCharge,opponent,mine,tempGreenItem,tempRedItem,tempUseGreenItem,tempUseRedItem);
		//////System.err.println("mes hp : "+mine.getDefense()+" ; "+mine.getOldDefense());
		this.updateItem(tempUseGreenItem,tempUseRedItem);
		
		Vector<Vector<Card>> attaque = new Vector<Vector<Card>>();
		boolean test;
		
		attaque = this.generateRandomAttack(compteur, startTime, opponent, mine);
		//compteur++;
		this.myAttack(attaque,opponent,mine,myDead,hisDead);
		this.update(summonWithoutCharge);
		//double testScore = this.getTurnScore(opponent,mine);
//testScore >= bestScore
		if (true){
		compteur++;
		
		this.other.getBoard().removeElement(opponent);
		if (this.me.getBoard().contains(mine) == false){this.me.getBoard().add(mine);}
        Vector<Vector<Card>> hisNextAttack =this.bestRandomAttackOpponent(opponent,mine);
		this.other.getBoard().add(0,opponent);
		

		this.hisAttack(hisNextAttack,opponent,mine,myDead,hisDead);
		////System.err.println("his attack :");
		
		
		int testHugeSummon = 0;
		int tempScore = 0;
		for (int m = 0 ; m < this.me.getBoard().size();m++){
			Card c = this.me.getBoard().elementAt(m);
			
			if (c.getDefense() >0 && c.getId() > -1){
				tempScore+=c.scoreTest(0,mine.getDefense(),opponent.getDefense());
				if (hugeSummon.contains(c)){testHugeSummon++;}
			}
		}
		for (int m = 0 ; m < this.other.getBoard().size();m++){
			Card c = this.other.getBoard().elementAt(m);
			if (c.getDefense() >0 && c.getId() > -1){
				//tempScoreBis--;
				tempScore-= c.scoreTest(1,mine.getDefense(),opponent.getDefense());
				
			}
		}
		
		if (opponent.getDefense() <= 0){
			tempScore = 10000;
		}
		if (mine.getDefense() <= 0){
			tempScore = -10000;
		}
		if (testHugeSummon < hugeSummon.size()){tempScore -= 1000;}
		
		double tempScoreSecond = getTurnScore(opponent,mine);//this.getTurnScore(opponent,mine);
		int tempScoreBis = mine.getDefense() - opponent.getDefense();
		////System.err.println("de score "+tempScore+" de deltaHp "+tempScoreBis);
		//this.afficher();

		if (tempScore > bestScore){
			bestScoreBis = tempScoreBis;
		  	bestScore = tempScore;
			bestAnswer = attaque;	
			bestSummon = summoning;	
			bestGreenItem = tempGreenItem;
			bestRedItem = tempRedItem;
			bestUseGreenItem = tempUseGreenItem;
			bestUseRedItem = tempUseRedItem;
	  	}

		else if (this.areEqual(tempScore,bestScore) && tempScoreBis > bestScoreBis){
			bestScoreBis = tempScoreBis;
			bestAnswer = attaque;
			bestSummon = summoning;	
			bestGreenItem = tempGreenItem;
			bestRedItem = tempRedItem;
			bestUseGreenItem = tempUseGreenItem;
			bestUseRedItem = tempUseRedItem;

		}
		else if (this.areEqual(tempScore,bestScore) && this.areEqual(tempScoreBis,bestScoreBis) && tempScoreSecond > bestScoreSecond){
			bestScoreSecond = tempScoreSecond;
			bestAnswer = attaque;
			bestSummon = summoning;	
			bestGreenItem = tempGreenItem;
			bestRedItem = tempRedItem;
			bestUseGreenItem = tempUseGreenItem;
			bestUseRedItem = tempUseRedItem;
		}

	}

		this.downgrade(summonWithoutCharge,myDead,hisDead);

		this.unSummonThings(summoning,summonWithoutCharge,opponent,mine,tempGreenItem,tempRedItem,tempUseGreenItem,tempUseRedItem);

		this.resetFull(summonWithoutCharge);
		endTime = System.nanoTime();


	}
			
			Vector<Card> items = new Vector<Card>();
			Vector<Card> finalAnswer = bestSummon;
			for (int i = 0;i<finalAnswer.size();i++){
				Card c = finalAnswer.elementAt(i);
				if (c.getCardType() == 0){
					a += c.printSUMMON();
					this.me.setMana(this.me.getMana()-c.getCost());
					if (c.getHpChange() != 0){
						mine.setDefense(mine.getDefense()+c.getHpChange());
						mine.setOldDefense(mine.getOldDefense()+c.getHpChange());
					}

					if (c.getHpChangeOpponent() != 0){
						opponent.setOldDefense(opponent.getOldDefense()+c.getHpChangeOpponent());
					}


					this.me.getHand().removeElement(c);
					if (c.getCharge()){this.me.getBoard().addElement(c);}
					  else {
						summonWithoutCharge.addElement(c);
						this.me.getBoard().addElement(c);}
				} else if (c.getCardType() == 3){
					a += c.printUSE();
					this.me.setMana(this.me.getMana()-c.getCost());
					if (c.getHpChange() != 0){
						mine.setDefense(mine.getDefense()+c.getHpChange());
						mine.setOldDefense(mine.getOldDefense()+c.getHpChange());
					}

					if (c.getHpChangeOpponent() != 0){
						opponent.setDefense(opponent.getDefense()+c.getHpChangeOpponent());

						opponent.setOldDefense(opponent.getOldDefense()+c.getHpChangeOpponent());
					}
				} else {
					items.addElement(c);
				}


			}
			endTime = System.nanoTime();

			

			//il reste à s'occuper des items rouges et verts

			for (int m = 0;m<bestGreenItem.size();m++){
				Card objet = bestGreenItem.elementAt(m);
				Card bestUse = bestUseGreenItem.elementAt(m);
				this.me.getHand().removeElement(objet);
				bestUse.getBuffByGreenCardBis(objet);
				a += bestUse.printUSE(objet);
				this.me.setMana(this.me.getMana()-objet.getCost());
			}
			for (int m = 0 ; m<bestRedItem.size();m++){
				Card objet = bestRedItem.elementAt(m);
				Card bestUse = bestUseRedItem.elementAt(m);
				bestUse.getBuffByRedCard(objet);
				this.me.getHand().removeElement(objet);
				a += bestUse.printUSE(objet);
				this.me.setMana(this.me.getMana()-objet.getCost());
			}

			
			endTime = System.nanoTime();


			for (int i = 0;i<bestAnswer.size();i++){
				Card attacker = bestAnswer.elementAt(i).elementAt(0);
				Card defender = bestAnswer.elementAt(i).elementAt(1);
				defender.getAttackBy(attacker,this.me,this.other,opponent,mine);
			defender.counterAttack(attacker,this.me,this.other);
  			if (defender.getDefense() <=0){
				this.other.getBoard().removeElement(defender);
  			}
  			if (attacker.getDefense() <=0){
				this.me.getBoard().removeElement(attacker);
  			}
				a += bestAnswer.elementAt(i).elementAt(0).printATTACK(bestAnswer.elementAt(i).elementAt(1));
		  
			}
	//System.err.println("nombre de test summon tried : "+compteurGlobal+" de valide : "+compteur);

	return a;


}

public String summonPossibilities(String a,Vector<Card> summonWithoutCharge,Card opponent,Card mine,int PlayerNumber,int TOUR){

	Hand Hand = new Hand(this.me.getHand());
	int mana = this.me.getMana();
	

		Vector<Vector<Card>> possible = Hand.allPosibilitesWithoutItem(mana,this.me.getBoard().size(),this.me,this.other);
		if (possible.size() >0){
		double bestScore = -100000000.0;
		int bestScoreSecond = -1;
		Vector<Card> bestSummon = new Vector<Card>();
			Vector<Card> bestGreenItem = new Vector<Card>();
	Vector<Card> bestRedItem = new Vector<Card>();
	Vector<Card> bestUseGreenItem = new Vector<Card>();
	Vector<Card> bestUseRedItem = new Vector<Card>();

		for (int i = 0;i<possible.size();i++){
			double score = 0;
			Vector<Card> summoning = possible.elementAt(i);
	//////System.err.println("");
			int manaUsed = 0;

			Vector<Card> bestUseGreen = new Vector<Card>();
			Vector<Card> bestUseRed = new Vector<Card>();
			Vector<Card> tempGreenItem = new Vector<Card>();
	Vector<Card> tempRedItem = new Vector<Card>();
	Vector<Card> tempUseGreenItem = new Vector<Card>();
	Vector<Card> tempUseRedItem = new Vector<Card>();
	
	
	if (summoning.size() > 0){
	for (int j = 0;j<summoning.size();j++){
		Card c = summoning.elementAt(j);
		
		//////System.err.println("Go FOR (full liste) "+c.getId()+" ; ");
		if (c.getCardType() < 1){
			manaUsed += c.getCost();
			if (c.getCharge() == true){this.me.getBoard().addElement(c);}
			else {summonWithoutCharge.addElement(c);}
			//////System.err.print("Go FOR "+c.getId()+" ; ");
			
			
		}
		else if (c.getCardType() == 1 && this.me.getBoard().size()+summonWithoutCharge.size()>0){
			Card bestUse = this.bestUseOfGreenCard1(c, opponent, mine,summonWithoutCharge);
			if (bestUse.getId() >-1){
				manaUsed += c.getCost();
				tempUseGreenItem.addElement(bestUse);
				tempGreenItem.addElement(c);
				bestUseGreen.addElement(bestUse);
				bestUse.getBuffByGreenCard(c);
				//////System.err.print("USE"+" "+c.getId()+" "+bestUse.getId()+" ; ");
			}
			else {bestUseGreen.addElement(bestUse);}
		}
		else if (c.getCardType() == 2 && this.other.getBoard().size()>1){
			Card bestUse = bestUseOfRedCard1(c, opponent, mine);
			if (bestUse.getId()>-1){
				manaUsed += c.getCost();
				tempUseRedItem.addElement(bestUse);
				tempRedItem.addElement(c);
				//////System.err.println("USE"+" "+c.getId()+" "+bestUse.getId()+" ; ");
				bestUseRed.addElement(bestUse);
				bestUse.getBuffByRedCard(c);
				if (bestUse.getDefense() <= 0){
					this.other.getBoard().remove(bestUse);
				}
				opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setDefense(mine.getDefense() + c.getHpChange());
				
			} else {bestUseRed.addElement(bestUse);}
			
		} else if(c.getCardType() == 3) {
			manaUsed += c.getCost();
			//////System.err.println("USE"+" "+c.getId()+" ; ");
			opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
			mine.setDefense(mine.getDefense() + c.getHpChange());
		}

	}
}
for (int me = 0;me<summonWithoutCharge.size();me++){
	this.me.getBoard().add(summonWithoutCharge.elementAt(me));
}


			score = getTurnScoreSummon(opponent,mine,1,30);//this.getTurnScore(opponent,mine);
			int scoreSecond  = manaUsed;
			//////System.err.println(score);
			for (int me = 0;me<summonWithoutCharge.size();me++){
				this.me.getBoard().removeElement(summonWithoutCharge.elementAt(me));
			}


			if (score>bestScore){
				bestScore = score;
				bestScoreSecond = scoreSecond;
				bestSummon = summoning;
				bestGreenItem = tempGreenItem;
				bestRedItem = tempRedItem;
				bestUseGreenItem = tempUseGreenItem;
				bestUseRedItem = tempUseRedItem;
				//////System.err.print("best score :"+bestScore);
				  ////System.err.println();
			}
			else if (this.areEqual(score,bestScore) == true && scoreSecond > bestScoreSecond){
				bestScoreSecond = scoreSecond;
				bestSummon = summoning;
				bestGreenItem = tempGreenItem;
				bestRedItem = tempRedItem;
				bestUseGreenItem = tempUseGreenItem;
				bestUseRedItem = tempUseRedItem;
			//	////System.err.print("best score :"+bestScore+" de scoreSecond : "+scoreSecond);
				  ////System.err.println();
			}
			//////System.err.print("mon tempo ");
			for (int j = summoning.size()-1;j>=0;j--){
				Card c = summoning.elementAt(j);
				if (c.getCardType() < 1){
					if (c.getCharge() == true){this.me.getBoard().removeElement(c);}
					else {summonWithoutCharge.removeElement(c);}
				
				} else if(c.getCardType() == 3) {
					//Card reverseC = new Card(c);
					//this.me.getBoard().removeElement(reverseC);
					opponent.setDefense(opponent.getDefense() -c.getHpChangeOpponent());
						mine.setDefense(mine.getDefense() - c.getHpChange());
				}
	
			}
			for (int m = 0;m<tempGreenItem.size();m++){
				Card objet = tempGreenItem.elementAt(m);
				Card bestUse = tempUseGreenItem.elementAt(m);
				bestUse.getDebuffByGreenCard(objet);
			}
	
			for (int m = 0 ; m<tempRedItem.size();m++){
				Card objet = tempRedItem.elementAt(m);
				Card bestUse = tempUseRedItem.elementAt(m);
				if (bestUse.getDefense()<=0 && !this.other.getBoard().contains(bestUse)){
					this.other.getBoard().add(bestUse);
				}
				bestUse.getDebuffByRedCard(objet);		
			}
			
			opponent.resetSmall();
			mine.resetSmall();
			this.other.clean();
		}

		Vector<Card> items = new Vector<Card>();
		Vector<Card> finalAnswer = bestSummon;
		for (int i = 0;i<finalAnswer.size();i++){
			Card c = finalAnswer.elementAt(i);
			if (c.getCardType() == 0){
				a += c.printSUMMON();
				this.me.setMana(this.me.getMana()-c.getCost());
				if (c.getHpChange() != 0){
					mine.setDefense(mine.getDefense()+c.getHpChange());
					mine.setOldDefense(mine.getOldDefense()+c.getHpChange());
				}

				if (c.getHpChangeOpponent() != 0){
					opponent.setOldDefense(opponent.getOldDefense()+c.getHpChangeOpponent());
				}


				this.me.getHand().removeElement(c);
				if (c.getCharge()){this.me.getBoard().addElement(c);}
				  else {
					summonWithoutCharge.addElement(c);
					this.me.getBoard().addElement(c);}
			} else if (c.getCardType() == 3){
				a += c.printUSE();
				this.me.setMana(this.me.getMana()-c.getCost());
				if (c.getHpChange() != 0){
					mine.setDefense(mine.getDefense()+c.getHpChange());
					mine.setOldDefense(mine.getOldDefense()+c.getHpChange());
				}

				if (c.getHpChangeOpponent() != 0){
					opponent.setDefense(opponent.getDefense()+c.getHpChangeOpponent());

					opponent.setOldDefense(opponent.getOldDefense()+c.getHpChangeOpponent());
				}
			} else {
				items.addElement(c);
			}


		}
		
		//il reste à s'occuper des items rouges et verts

		for (int m = 0;m<bestGreenItem.size();m++){
			Card objet = bestGreenItem.elementAt(m);
			Card bestUse = bestUseGreenItem.elementAt(m);
			this.me.getHand().removeElement(objet);
			bestUse.getBuffByGreenCardBis(objet);
			a += bestUse.printUSE(objet);
			this.me.setMana(this.me.getMana()-objet.getCost());
		}
		for (int m = 0 ; m<bestRedItem.size();m++){
			Card objet = bestRedItem.elementAt(m);
			Card bestUse = bestUseRedItem.elementAt(m);
			bestUse.getBuffByRedCard(objet);
			if (bestUse.getDefense() <= 0){
				this.other.getBoard().remove(bestUse);
			}
			this.me.getHand().removeElement(objet);
			a += bestUse.printUSE(objet);
			this.me.setMana(this.me.getMana()-objet.getCost());
		}

		}
	for (int p = 0;p<summonWithoutCharge.size();p++){
		this.me.getBoard().removeElement(summonWithoutCharge.elementAt(p));
	}
	return a;

}



	public void pickDraft(Deck d,int PlayerNumber){
		if (this.getTurn() < 30){
			int score = -100000;
			int best = 0;

			Vector<Card> h = this.me.getHand();
			d.incrCard(h.elementAt(0));
			int score0 = d.scoreCardInDraft(h.elementAt(0),PlayerNumber);
			d.decrCard(h.elementAt(0));
			d.incrCard(h.elementAt(1));
			int score1 = d.scoreCardInDraft(h.elementAt(1),PlayerNumber);
			d.decrCard(h.elementAt(1));
			d.incrCard(h.elementAt(2));
			int score2 = d.scoreCardInDraft(h.elementAt(2),PlayerNumber);
			d.decrCard(h.elementAt(2));

			////System.err.println(score0 +" "+score1+" "+score2);

			if (score0 > score){
				score = score0;
				best = 0;
			}

			if (score1 > score){
				score = score1;
				best = 1;
			}

			if (score2 > score){
				score = score2;
				best = 2;
			}

			Card bestCard = h.elementAt(best);

            
			d.incrCard(bestCard);
			bestCard.printPICK();

		}

	}


  public String attackBasicBis(String a,long startTime,Vector<Card> summonWithoutCharge,Card mine){
		Card opponent = this.other.getBoard().elementAt(0);
		//Vector<Vector<Card>> answer = this.bestAttackRandom(startTime,summonWithoutCharge,mine);
		//Vector<Vector<Card>> answer =  bestRandomAttack(opponent,mine,summonWithoutCharge,0);

		a = bestAttackRandomDepthSummon(a,startTime,summonWithoutCharge,mine);
		/*for (int i = 0;i<answer.size();i++){
			a += answer.elementAt(i).elementAt(0).printATTACK(answer.elementAt(i).elementAt(1));
      
		}*/
		return a;
	}



}

class Player{

public static void main(String args[]) {
		int TOUR = 0;
		Deck d = new Deck();
		int PlayerNumber = 1;

		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {


				int JoueurHealth = in.nextInt();
				int JoueurMana = in.nextInt();
				int JoueurDeck = in.nextInt();
				int JoueurRune = in.nextInt();
				int JoueurDraw = in.nextInt();
				//Joueur Joueur1 = new Joueur(JoueurHealth,JoueurMana,JoueurDeck,JoueurRune);
				int JoueurHealthOpponent = in.nextInt();
				int JoueurManaOpponent = in.nextInt();
				int JoueurDeckOpponent = in.nextInt();
				int JoueurRuneOpponent = in.nextInt();
				int JoueurDrawOpponent = in.nextInt();

				//Joueur Joueur2 = new Joueur(JoueurHealthOpponent,JoueurManaOpponent,JoueurDeckOpponent,JoueurRuneOpponent);
				int opponentHand = in.nextInt();
				Vector<Card> fake = new Vector<Card>();
				for (int o = 0;o<opponentHand;o++){
					Card lol = new Card(0);
					fake.add(lol);
				}

            	int opponentActions = in.nextInt();
            	if (in.hasNextLine()) {
                in.nextLine();
            	}
            	for (int i = 0; i < opponentActions; i++) {
                	String cardNumberAndAction = in.nextLine();
           	 	}


				int CardCount = in.nextInt();
				Vector<Card> board = new Vector();
				Vector<Card> Hand = new Vector();
				Vector<Card> boardOpponent = new Vector();
        		Vector<Card> summonWithoutCharge = new Vector();
				Card opponent = new Card(JoueurHealthOpponent);
        		Card mine = new Card(JoueurHealth);
				boardOpponent.addElement(opponent);
				summonWithoutCharge.addElement(mine);
				long startTime = System.nanoTime();
				for (int i = 0; i < CardCount; i++) {
						int CardNumber = in.nextInt();
						

						int instanceId = in.nextInt();
						int location = in.nextInt();
						int CardType = in.nextInt();
				
						int cost = in.nextInt();
						int attack = in.nextInt();
						int defense = in.nextInt();
						if (CardType == 3 && defense != 0){
							CardType = 2;
						}
						String abilities = in.next();
						int myHealthChange = in.nextInt();
						int opponentHealthChange = in.nextInt();
						int CardDraw = in.nextInt();
						Card c = new Card(CardNumber,instanceId,location,CardType,cost,attack,defense,abilities,myHealthChange,opponentHealthChange,CardDraw,i);
						if (location == 0){
							if (!c.getCharge()) {c.setUsed(true);}
							Hand.add(c);
						}
						else if (location == 1 && c.getAttack() > 0){
							board.add(c);
						}
						else if (location == 1 && c.getAttack() == 0 && c.getGuard() == false){
							board.add(c);
						}
            else if (location == 1){
            	summonWithoutCharge.add(c);
            }
            else if (location == -1 && (c.getAttack() >0 || c.getGuard())){
							boardOpponent.add(c);

						}
				}
				Joueur Joueur1 = new Joueur(JoueurHealth,JoueurMana,JoueurDeck,JoueurRune,Hand,board);
				Joueur Joueur2 = new Joueur(JoueurHealthOpponent,JoueurManaOpponent,JoueurDeckOpponent,JoueurRuneOpponent,fake,boardOpponent);
				//Joueur Joueur2 = new Joueur(JoueurHealthOpponent,JoueurManaOpponent,JoueurDeckOpponent,JoueurRuneOpponent,fake,hisGuards);

				//Joueur1.setHand(Hand);
				//Joueur1.setBoard(board);
				//Joueur2.setBoard(boardOpponent);
				Turn tour = new Turn(TOUR,Joueur1,Joueur2);




        /*if (TOUR == 0){
            ////System.err.println("le vecteur se calcul en " + (e-s) + " ns");
            ////System.err.println("c'est-à-dire en " + (e-s)/1000000 + " ms");
            ////System.err.println(test);
        }
        */

				if (TOUR == 0){
					
					if (JoueurDeckOpponent == 0){PlayerNumber = 1;}
					if (JoueurDeckOpponent == 1){PlayerNumber = 2;}
					////System.err.println(PlayerNumber);
				}
				if (TOUR <30){tour.pickDraft(d,PlayerNumber);}
				else{
				    String answer = "PASS;";
				String hisAnswer = "";
				int amountOfCrea = 0;
				int manaToUse = 0;
				for (int i = 0 ; i < tour.getMe().getHand().size() ; i++){
					if (tour.getMe().getHand().elementAt(i).getCardType() == 0){
						amountOfCrea++;
						manaToUse +=  tour.getMe().getHand().elementAt(i).getCost();
					}
				}
				if (TOUR > 40 && amountOfCrea >= 3 && manaToUse >= 7 && tour.getOther().hasEmperorNightmareInBoard() == false){
					if (tour.getMe().hasEmperorNightmareInHand() == true){
						tour.getMe().removeEmperorFromHand();
					}
				}
					//	if (tour.getOther().getBoard().size() >1){////System.err.println(" test his life :" + tour.getOther().getBoard().elementAt(1).getDefense());}
					

						if (tour.getMe().getBoard().size()>-1){

							//answer = tour.summonPossibilities(answer,summonWithoutCharge,opponent,mine,PlayerNumber,TOUR);

							answer = tour.attackBasicBis(answer,startTime,summonWithoutCharge,mine);
							//answer = tour.summonPossibilities(answer,summonWithoutCharge,opponent,mine,PlayerNumber,TOUR);
							//answer = tour.attackBasic(answer,mine);
							//answer = tour.attackBasicHeuristique(answer,mine);
							Vector<Vector<Card>>  fakeAttack = tour.attackDirecte(opponent,mine);
							for (int i = 0;i<fakeAttack.size();i++){
			answer += fakeAttack.elementAt(i).elementAt(0).printATTACK(fakeAttack.elementAt(i).elementAt(1));
      
		}


							for (int m = 0;m<tour.getMe().getBoard().size();m++){
								answer+="ATTACK"+" "+tour.getMe().getBoard().elementAt(m).getId()+" "+"-1;";
							
							}
							answer = tour.summonPossibilities(answer,summonWithoutCharge,opponent,mine,PlayerNumber,TOUR);

						}

          





				    long endTime = System.nanoTime();
						
						System.out.println(answer);
				}




				TOUR++;
		}
	}
}
