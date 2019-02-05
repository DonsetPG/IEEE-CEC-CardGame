/*
* allpossibilities de summon bugggg;
*/

/*
* checker qu'on ne fait pas plusieurs fois le même calcul
*
*
*/

/*
*
* il faut réussir à faire tourner le jeux en local;
* 
* et comprendre pq des scores atteignent les 7k ???
* verifier que greenitem fonctionne ok;
* rajouter une SolutionBOT4 qui attack toujours full l'ennemi; 
* ne pas compter dans le score les créatures.getDefense() <=0;
* bails chelou avec summon : ne summon pas tout; Il faudrait vérifier que tout fonctionne bien; 
* améliorer le bot ennemie 
* remplacer ScoreBis par scoreBoard si le tempo obtient de meilleur résultat 
*
*/

/*
* retry la fonction tempo;
* faire tourner en local 
* réfléchir à des stratégies en fonction de si on est JoueurBOT4 1 ou JoueurBOT4 2 : draft/summon/attack. 
*
*
*
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

class CardBOT4{

	private int idCardBOT4;
	private int id;
	private int location;
	private int CardBOT4Type;
	private int cost;
	private int attack;
	private int defense;
	private int oldDefense;
	private int oldestDefense;
	private String abilities;
	private String oldAbilities;
	private int hpChange;
	private int hpChangeOpponent;
	private int CardBOT4Draw;
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



	public CardBOT4(int idCardBOT4,int id,int location,int CardBOT4Type,int cost,int attack,int defense,String abilities,int hpChange,int hpChangeOpponent,int CardBOT4Draw,int placeDraft){
		this.idCardBOT4 = idCardBOT4;
		this.id = id;
		this.location = location;
		this.CardBOT4Type = CardBOT4Type;
		this.cost = cost;
		this.attack = attack;
		this.defense = defense;
		this.oldDefense = defense;
		this.oldestDefense = defense;
		this.abilities = abilities;
		this.oldAbilities = abilities;
		this.hpChange = hpChange;
		this.hpChangeOpponent = hpChangeOpponent;
		this.CardBOT4Draw = CardBOT4Draw;
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

	public CardBOT4(int hp){
		this.idCardBOT4 = -1;
		this.id = -1;
		this.location = 100;
		this.CardBOT4Type = 100;
		this.cost = 1;
		this.attack = 0;
		this.defense = hp;
		this.oldestDefense = hp;
		this.oldDefense = hp;
		this.abilities = "------";
		this.oldAbilities = "------";
		this.hpChange = 0;
		this.hpChangeOpponent = 0;
		this.CardBOT4Draw = 0;
		this.placeDraft = 0;
	}

	public CardBOT4(CardBOT4 c){
		this.idCardBOT4 = c.idCardBOT4;
		this.id = c.id;
		this.location = c.location;
		this.CardBOT4Type = c.CardBOT4Type;
		this.cost = c.cost;
		this.attack = -c.attack;
		this.defense = -c.defense;
		this.abilities = c.abilities;
		this.abilities = c.oldAbilities;
		this.hpChange = c.hpChange;
		this.hpChangeOpponent = c.hpChangeOpponent;
		this.CardBOT4Draw = c.CardBOT4Draw;
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

	public CardBOT4(CardBOT4 c,int a){
		this.idCardBOT4 = c.idCardBOT4;
		this.id = c.id;
		this.location = c.location;
		this.CardBOT4Type = c.CardBOT4Type;
		this.cost = c.cost;
		this.attack = c.attack;
		this.defense = c.defense;
		this.abilities = c.abilities;
		this.abilities = c.oldAbilities;
		this.hpChange = c.hpChange;
		this.hpChangeOpponent = c.hpChangeOpponent;
		this.CardBOT4Draw = c.CardBOT4Draw;
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

	public int getCardBOT4Draw(){
		return this.CardBOT4Draw;
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
		score += Math.pow((double)this.defense,2) + Math.pow((double)this.attack,3);
		if (this.guard){
			score += (double)this.defense;
		}
		if (this.breaktrough){
			score += ((double)this.attack - 1.0)*0.9;
		}
		if (this.drain){
			score += 0.7*(double)this.attack;
		}
		if (this.lethal){
			score += 12.0 - (double)this.attack;
		}
		if (this.ward){
			score += (double)this.attack;
		}
		if (this.ward && this.lethal){
			score += (double)this.attack;
		}

		if (mineHp < 10 && this.guard && n == 0){
			score += (double)this.defense;
		}
		if (opponentHp < 10 && this.drain && n == 1){
			score += (double)this.attack;
		}


		return score;
		
	}

	public double scoreBis(int hp,int hpOpponent,int nbrCardBOT4s,int nbrCardBOT4sOpponent,int PlayerBOT4Number,int TOUR){
    

    double score;

		if (this.getCardBOT4Type() == 2){
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
		if (nbrCardBOT4s*2 <= nbrCardBOT4sOpponent && nbrCardBOT4sOpponent >=4){
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
						if (PlayerBOT4Number == 1){score3x += 100;}
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

		if (this.getCardBOT4Type() == 2){
			if (this.getCardBOT4Type() == 2){
				this.attack = - this.attack;
				this.defense = - this.defense;
			}
			return Math.sqrt(-score);
		} else {
			////System.err.println("la carte "+this.getId()+" de score -> "+score);
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

	public String printATTACK(CardBOT4 c){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+Integer.toString(c.getId())+";";
		//System.out.println(answer);
		return answer;
	}

  public String printATTACKBIS(CardBOT4 c){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+Integer.toString(c.getId())+";";
		//System.out.println(answer);
		return answer;
	}

	public String printATTACK(){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+"-1"+";";
		//System.out.println(answer);
		return answer;
	}

	public String printUSE(CardBOT4 c){
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

	public int getCardBOT4Number(){
		return this.idCardBOT4;
	}


    public int getPlace(){
		return this.placeDraft;
	}

	public int getLocation(){
		return this.location;
	}

	public int getCardBOT4Type(){
		return this.CardBOT4Type;
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

	public void counterAttack(CardBOT4 c,JoueurBOT4 me,JoueurBOT4 other){
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

	public void deCounterAttack(CardBOT4 c,JoueurBOT4 me,JoueurBOT4 other){
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


	public void getAttackBy(CardBOT4 c,JoueurBOT4 me,JoueurBOT4 other,CardBOT4 opponent,CardBOT4 mine){

		



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

	public void getDeattackBy(CardBOT4 c,JoueurBOT4 me,JoueurBOT4 other,CardBOT4 opponent,CardBOT4 mine){

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

	public void getBuffByGreenCardBOT4(CardBOT4 c){
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

	public void getDebuffByGreenCardBOT4(CardBOT4 c){
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

	public void getBuffByGreenCardBOT4Bis(CardBOT4 c){
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

	public void getBuffByRedCardBOT4(CardBOT4 c){
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

	public void getDebuffByRedCardBOT4(CardBOT4 c){
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



	public void getAttackByBlueCardBOT4(CardBOT4 c){
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


class HandBOT4{

	private int amountOfCardBOT4;
	private Vector<CardBOT4> HandBOT4;
	private int amountOfItem;

	public HandBOT4(Vector<CardBOT4> h ) {
		this.HandBOT4 = h;
		this.amountOfCardBOT4 = h.size();
		this.amountOfItem = 0;
		for (int i = 0;i<h.size();i++) {
			if (h.elementAt(i).getCardBOT4Type() >0){this.amountOfItem++;}
		}
	}

	public int getItems(){
		return this.amountOfItem;
	}

	public boolean equalWithPermutation(Vector<CardBOT4> vA,Vector<CardBOT4> vB){
		
		if (vA.size() != vB.size()){
			return false;
		} else {
			for (int i = 0 ; i < vA.size() ; i++){
				CardBOT4 test = vB.elementAt(i);
				if (test.getCardBOT4Type() > 0){return false;}
				if (!vA.contains(test)){return false;}
				
			}
		}
		return true;
	}

	public boolean insideNoMatterpermutation(Vector<Vector<CardBOT4>> possible,Vector<CardBOT4> vB){
		for (int i = 0 ; i<possible.size() ; i++){
			Vector<CardBOT4> vA = possible.elementAt(i);
			boolean eq = this.equalWithPermutation(vA,vB);
			if (eq == true){
				return true;
			}
		}
		return false;
	}

	public Vector<CardBOT4> summonPossible(int mana,int nbrCarte,JoueurBOT4 me){
		Vector<CardBOT4> possible = new Vector<CardBOT4>();
		Vector<CardBOT4> h = this.HandBOT4;
		for (int i = 0 ; i < h.size() && nbrCarte < 6 ; i++){
			if (h.elementAt(i).getCost() <= mana){
				possible.addElement(h.elementAt(i));
			}
		}
		return possible;
	}

	public CardBOT4 CardBOT4RandomPossible(int mana,int nbrCarte,JoueurBOT4 me){
		Vector<CardBOT4> possible = this.summonPossible(mana, nbrCarte, me);
		if (possible.size() > 0){
			int randomIndice = ThreadLocalRandom.current().nextInt(0,possible.size());
			return possible.elementAt(randomIndice);
		} else {
			return new CardBOT4(0);
		}
		
	}

	public Vector<CardBOT4> randomSummon(int mana,int nbrCarte,JoueurBOT4 me){
		Vector<CardBOT4> SolutionBOT4 = new Vector<CardBOT4>();
		int m = mana;
		int compteur = nbrCarte;
		CardBOT4 toSummon;
		do {
			toSummon = this.CardBOT4RandomPossible(m, compteur, me);
			if (toSummon.getId() > -1){
				SolutionBOT4.addElement(toSummon);
				m -= toSummon.getCost();
				if (toSummon.getCardBOT4Type() == 0){compteur++;}
				me.getHandBOT4().remove(toSummon);
			}
		} while (toSummon.getId()>-1 && compteur <= 6 && m >= 0);

		for (int i = 0 ; i < SolutionBOT4.size();i++){
			if (SolutionBOT4.elementAt(i).getId()>-1){me.getHandBOT4().add(SolutionBOT4.elementAt(i));}
		}
		return SolutionBOT4;
	}

	public Vector<Vector<CardBOT4>> allPosibilitesWithoutItem(int mana,int nbrCarte,JoueurBOT4 me,JoueurBOT4 other) {


		int min = 13;
		
		Vector<CardBOT4> h = this.HandBOT4;
		CardBOT4 minCost = new CardBOT4(0);
		
		Vector<CardBOT4> possible = new Vector<CardBOT4>();
		for (int i = 0;i<h.size();i++) {
			


			if (h.elementAt(i).getCost() <= mana) {
				possible.addElement(h.elementAt(i));
				if (h.elementAt(i).getCost() <= min){
					min = h.elementAt(i).getCost();
					minCost = h.elementAt(i);
				}
			    ////System.err.print("Cartes possibles :");
			    ////System.err.print(h.elementAt(i).getId());
			    ////System.err.println();
			    }
		}

    Vector<CardBOT4> possible2 = new Vector<CardBOT4>();
		for (int i = h.size()-1;i>=0;i--) {
			if (h.elementAt(i).getCost() <= mana) {
			    possible2.addElement(h.elementAt(i));
			    ////System.err.print("Cartes possibles :");
			    ////System.err.print(h.elementAt(i).getId());
			    ////System.err.println();
			    }
		}

		Vector<Vector<CardBOT4>> renvoie = new Vector<Vector<CardBOT4>>();
		int n = possible.size();
		for (int i = 0;i<Math.pow(2,n);i++){
			int compteur = nbrCarte;
			int m = 0;
			Vector<CardBOT4> answer = new Vector<CardBOT4>();
			
			for (int j = 0;j<n;j++){
				if ((int)(i/(int)(Math.pow(2,n-1-j)))%2 == 0){
					if (possible.elementAt(j).getCardBOT4Type() == 0|| possible.elementAt(j).getCardBOT4Type() == 3 || (possible.elementAt(j).getCardBOT4Type() == 1 && compteur>0) || (possible.elementAt(j).getCardBOT4Type() == 2 && other.getBoard().size()>1)){
						answer.addElement(possible.elementAt(j));
						m += possible.elementAt(j).getCost();
						if (possible.elementAt(j).getCardBOT4Type() == 0){compteur++;}
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
			Vector<CardBOT4> answer = new Vector<CardBOT4>();
			
			for (int j = 0;j<n;j++){
				if ((int)(i/(int)(Math.pow(2,n-1-j)))%2 == 0){
					if (possible2.elementAt(j).getCardBOT4Type() == 0|| possible2.elementAt(j).getCardBOT4Type() == 3 || (possible2.elementAt(j).getCardBOT4Type() == 1 && compteur>0) || (possible2.elementAt(j).getCardBOT4Type() == 2 && other.getBoard().size()>1)){
						answer.addElement(possible2.elementAt(j));
						m += possible2.elementAt(j).getCost();
						if (possible2.elementAt(j).getCardBOT4Type() == 0){compteur++;}
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

class DeckBOT4{

	int amountOfCardBOT4;
	Vector<CardBOT4> DeckBOT4;
	int[] manaAmount;
	int amountOfItem;
	int[] scoreCardBOT42 = {73
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

	int[] scoreCardBOT4 = {73
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






	public DeckBOT4(){
		this.amountOfCardBOT4 = 0;
		this.DeckBOT4 = new Vector<CardBOT4>();
		this.manaAmount = new int[13];
		for (int i = 0;i<13;i++){
			manaAmount[i] = 0;
		}
		this.amountOfItem = 0;



	}

	public int getAmountOfCardBOT4(){
		return this.amountOfCardBOT4;
	}

	public Vector<CardBOT4> getDeckBOT4(){
		return this.DeckBOT4;
	}

	public int[] getManaAmount(){
		return this.manaAmount;
	}

	public int getAmountOfItem(){
		return this.amountOfItem;
	}

	public void incrCardBOT4(CardBOT4 c){
		this.amountOfCardBOT4++;
		this.DeckBOT4.addElement(c);
		if (c.getCardBOT4Type() > 0){this.amountOfItem++;}
		else{
			this.manaAmount[c.getCost()]++;
		}

	}

	public void decrCardBOT4(CardBOT4 c){
		this.amountOfCardBOT4--;
		this.DeckBOT4.removeElementAt(this.DeckBOT4.size()-1);
		if (c.getCardBOT4Type() > 0){this.amountOfItem--;}
		else{
			this.manaAmount[c.getCost()]--;
		}

	}

	public int scoreCardBOT4InDraft(CardBOT4 c,int n){
		int indx = c.getCardBOT4Number();
		int score;
		if (n == 1){
			score = this.scoreCardBOT4[indx-1];
		}
		else {
			score =  this.scoreCardBOT42[indx-1];
		}
		if (this.amountOfItem > 10){
			score -= 1000;
		}
		if (this.manaAmount[9]+this.manaAmount[10]+this.manaAmount[11]+this.manaAmount[12]>4){
			score -= 1000;
		}
		return score;
	}

	public int scoreDeckBOT4(){
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


		score *= this.amountOfCardBOT4;

		CardBOT4 c = this.DeckBOT4.elementAt(this.DeckBOT4.size()-1);
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

class JoueurBOT4{

	private int hp;
	private int mana;
	private int remainingCardBOT4sInDeckBOT4;
	private int rune;
	private Vector<CardBOT4> board;
	private Vector<CardBOT4> HandBOT4;


	public JoueurBOT4(int hp,int mana,int rcid,int rune,Vector<CardBOT4> HandBOT4, Vector<CardBOT4> board){
		this.hp = hp;
		this.mana = mana;
		this.remainingCardBOT4sInDeckBOT4 = rcid;
		this.rune = rune;
		this.board = board;
		this.HandBOT4 = HandBOT4;
	}

  public boolean validAnswerBis(Vector<Vector<CardBOT4>> a,JoueurBOT4 opponent,CardBOT4 other,CardBOT4 mine){
	  	int kill = 0;
		boolean answer = true;
		Vector<CardBOT4> opponentBoard = new Vector<CardBOT4>();
		Vector <CardBOT4> b = opponent.getBoard();
		Vector<CardBOT4> gInBoard = opponent.getGuardsInBoard();
		Vector<CardBOT4> attacked = new Vector<CardBOT4>();
		int let = 0;
		int amountOfLethal = this.getLethalsInBoard().size();
		//for (int i = 0;i<b.size();i++){opponentBoard.addElement(b.elementAt(i));}
		for (int i = 0;i<a.size();i++){
			CardBOT4 attacker = a.elementAt(i).elementAt(0);
			CardBOT4 defender = a.elementAt(i).elementAt(1);
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

	public boolean validAnswerBisOpponent(Vector<Vector<CardBOT4>> a,JoueurBOT4 opponent,CardBOT4 other,CardBOT4 mine){
		int kill = 0;
	  boolean answer = true;
	  Vector<CardBOT4> opponentBoard = new Vector<CardBOT4>();
	  Vector <CardBOT4> b = opponent.getBoard();
	  Vector<CardBOT4> gInBoard = opponent.getGuardsInBoard();
	  Vector<CardBOT4> attacked = new Vector<CardBOT4>();
	  int let = 0;
	  int amountOfLethal = this.getLethalsInBoard().size();
	  //for (int i = 0;i<b.size();i++){opponentBoard.addElement(b.elementAt(i));}
	  for (int i = 0;i<a.size();i++){
		  CardBOT4 attacker = a.elementAt(i).elementAt(0);
		  CardBOT4 defender = a.elementAt(i).elementAt(1);
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
		  CardBOT4 attacker = a.elementAt(i).elementAt(0);
		  CardBOT4 defender = a.elementAt(i).elementAt(1);
		  defender.getDeattackBy(attacker,this,opponent,other,mine);
	  }
	  return answer;
  }

  public void setHandBOT4(Vector<CardBOT4> HandBOT4){
		this.HandBOT4 = HandBOT4;
	}


	public void setBoard(Vector<CardBOT4> board){
		this.HandBOT4 = board;
	}

	public void clean(){
		for (int i =1;i<this.getBoard().size();i++ ){
			CardBOT4 c = this.getBoard().elementAt(i);
			if (c.getId() == -1){
				this.getBoard().remove(c);
			}
		}
	}

	public Vector<CardBOT4> boardOrderByAttack(){
		Vector<CardBOT4> order = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getBoard();
		while (b.size()>0){
			int bestScore = -1;
			CardBOT4 best = b.elementAt(0);
			for (int i = 0;i<b.size();i++){
				int score = 0;
				CardBOT4 c = b.elementAt(i);
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

	

	public Vector<CardBOT4> getGuardsInBoardOrderByDefense(){
		Vector<CardBOT4> order = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getGuardsInBoard();
		while (b.size()>0){
			int bestScore = -1000000;
			CardBOT4 best = b.elementAt(0);
			for (int i = 0;i<b.size();i++){
				int score = 0;
				CardBOT4 c = b.elementAt(i);
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

	public Vector<CardBOT4> boardOrderByDefense(){
		Vector<CardBOT4> order = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getBoard();
		while (b.size()>0){
			int bestScore = -1000000;
			CardBOT4 best = b.elementAt(0);
			for (int i = 0;i<b.size();i++){
				int score = 0;
				CardBOT4 c = b.elementAt(i);
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

	public int getRemainingCardBOT4sInDeckBOT4(){
		return this.remainingCardBOT4sInDeckBOT4;
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

	public Vector<CardBOT4> getBoard(){
		return this.board;
	}

	public Vector<CardBOT4> getHandBOT4(){
		return this.HandBOT4;
	}

	public void getAttackByCardBOT4(CardBOT4 c){
		this.hp -= c.getAttack();
	}

	public void getBuffByBlueCardBOT4(CardBOT4 c){
		this.hp += c.getHpChange();
	}

	public void getAttackByBlueCardBOT4(CardBOT4 c){
		this.hp += c.getHpChangeOpponent();
	}

	public double attackAverage(){
		double avg = 0.0;
		Vector<CardBOT4> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			avg += (double) b.elementAt(i).getAttack();
		}
		return (double) (avg)/((double)b.size());
	}

	public double bestDefense(){
		double best = 0.0;
		Vector<CardBOT4> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if ((double)b.elementAt(i).getDefense() > best){
				best = b.elementAt(i).getDefense();
			}
		}
		return (double) best;
	}

	public Vector<CardBOT4> getGuardsInBoard(){
		Vector<CardBOT4> g = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getGuard()){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<CardBOT4> getWardsInBoard(){
		Vector<CardBOT4> g = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getWard() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<CardBOT4> getDrainsInBoard(){
		Vector<CardBOT4> g = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getDrain() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<CardBOT4> getChargesInBoard(){
		Vector<CardBOT4> g = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getCharge() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<CardBOT4> getBreaktroughsInBoard(){
		Vector<CardBOT4> g = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getBreaktrough() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<CardBOT4> getLethalsInBoard(){
		Vector<CardBOT4> g = new Vector<CardBOT4>();
		Vector<CardBOT4> b = this.getBoard();
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
      	Vector<Integer> SolutionBOT4 = new Vector<Integer>();
      	for (int j = 0;j<arr.length;j++) {
      		SolutionBOT4.addElement(arr[j]);
      	}
      	perm.addElement(SolutionBOT4);

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

class TurnBOT4{

	private int tour;
	private JoueurBOT4 me;
	private JoueurBOT4 other;


	public TurnBOT4(int tour,JoueurBOT4 me, JoueurBOT4 other){
		this.tour = tour;
		this.me = me;
		this.other = other;
	}


  public void incrTurnBOT4(){
		this.tour++;
	}

	public void setTurnBOT4(int TurnBOT4){
		this.tour = TurnBOT4;
	}

	public int getTurnBOT4(){
		return this.tour;
	}

	public JoueurBOT4 getMe(){
		return this.me;
	}

	public JoueurBOT4 getOther(){
		return this.other;
	}

	public void afficher(){
		//System.err.println("Le tour est constitué de ");
		Vector<CardBOT4> myBoard = this.me.getBoard();
		Vector<CardBOT4> hisBoard = this.other.getBoard();

		//System.err.println("Mon board ");
		for (int i = 0;i<myBoard.size();i++){
			CardBOT4 c = myBoard.elementAt(i);
			//System.err.println("Carte "+c.getId()+" d'attaque "+c.getAttack()+" de defense "+c.getDefense()+" d'abilities "+c.getAbilities());
		}


		//System.err.println("Son board ");
		for (int i = 0;i<hisBoard.size();i++){
			CardBOT4 c = hisBoard.elementAt(i);
			//System.err.println("Carte "+c.getId()+" d'attaque "+c.getAttack()+" de defense "+c.getDefense()+" d'abilities "+c.getAbilities());
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
      	Vector<Integer> SolutionBOT4 = new Vector<Integer>();
      	for (int j = 0;j<arr.length;j++) {
      		SolutionBOT4.addElement(arr[j]);
      	}
      	perm.addElement(SolutionBOT4);

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


  public double getMyTempo(CardBOT4 opponent,CardBOT4 mine){
	if (opponent.getDefense() <= 0){return (double)100000.0;}
	Vector<CardBOT4> g = this.other.getBoard();
	double lifeOpponent = opponent.getDefense();//(double)this.other.getHp();
	

	double score;
	for (int i = 0;i<g.size();i++){
		if (g.elementAt(i).getId()>-1){lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());}
		if (g.elementAt(i).getGuard() == true){lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());}

	}
	
	Vector<CardBOT4> b = this.me.getBoard();
	double myAttack = (double)0;
	for (int i = 0;i<b.size();i++){
		if (b.elementAt(i).getDefense()>0) {myAttack += (double) Math.max(0,b.elementAt(i).getAttack());}
	}
	double bestDefense = this.other.bestDefense();
	double avgAttack = this.me.attackAverage();
	Vector<CardBOT4> w = this.other.getWardsInBoard();
	Vector<CardBOT4> l = this.me.getLethalsInBoard();

	lifeOpponent += (double)w.size()*avgAttack;
	//lifeOpponent -= (double)l.size()*(bestDefense);
	
	Vector<CardBOT4> b1 = this.me.getBreaktroughsInBoard();
	myAttack += (double) b1.size();
	Vector<CardBOT4> d = this.me.getDrainsInBoard();
	double tempTempo = (double)(lifeOpponent+0.1)/(myAttack+0.1);
	for (int i = 0;i<d.size();i++){
		myAttack += (tempTempo/(double)2.0) * (double)d.elementAt(i).getAttack();
	}

	score = (double)(lifeOpponent+0.1)/(myAttack+0.1);
	return (double) (2.0)/(score);
}

public double getHisTempo(CardBOT4 opponent,CardBOT4 mine){
	if (mine.getDefense() <= 0){return (double)10000.0;}
	Vector<CardBOT4> g = this.me.getBoard();
	double lifeOpponent = mine.getDefense();
	double score;
	for (int i = 0;i<g.size();i++){
		if (g.elementAt(i).getId()>-1){lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());}
		if (g.elementAt(i).getGuard() == true){lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());}

	}
	
	Vector<CardBOT4> b = this.other.getBoard();
	double myAttack = (double)0;
	for (int i = 0;i<b.size();i++){
		if (b.elementAt(i).getDefense()>0) {myAttack += (double) Math.max(0,b.elementAt(i).getAttack());}
	}
	double bestDefense = this.me.bestDefense();
	double avgAttack = this.other.attackAverage();
	Vector<CardBOT4> w = this.me.getWardsInBoard();
	Vector<CardBOT4> l = this.other.getLethalsInBoard();

	lifeOpponent += (double)w.size()*avgAttack;
	lifeOpponent -= (double)l.size()*(bestDefense);
	
	Vector<CardBOT4> b1 = this.other.getBreaktroughsInBoard();
	myAttack += (double) b1.size();
	Vector<CardBOT4> d = this.other.getDrainsInBoard();
	double tempTempo = (double)(lifeOpponent+0.1)/(myAttack+0.1);
	for (int i = 0;i<d.size();i++){
		myAttack += (tempTempo/(double)2.0) * (double)d.elementAt(i).getAttack();
	}

	score = (double)(lifeOpponent+0.1)/(myAttack+0.1);
	return (double) (2.0)/(score);
}

public double getTurnBOT4Score(CardBOT4 opponent,CardBOT4 mine){
	double myScore =  this.getMyTempo(opponent, mine);//this.getMyBoardScore(opponent,mine);
	double hisScore = this.getHisTempo(opponent, mine); //this.getHisBoardScore(opponent,mine);	
	int deltaHandBOT4 = this.me.getHandBOT4().size() - this.other.getHandBOT4().size() - 1;
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
	
	if (deltaHandBOT4 + deltaBoard - runeDropped < 0){return myScore - hisScore-50;}
	else {return myScore - hisScore;}
	
}

  
	public double getMyBoardScore(CardBOT4 opponent,CardBOT4 mine,int PlayerBOT4Number,int TOUR){
	    
		int hp = mine.getDefense();
		int hpOpponent = opponent.getDefense();
		if (hpOpponent <= 0){return (double)100000.0;}

		int nbrCardBOT4s = this.me.getBoard().size();
		int nbrCardBOT4sOpponent = this.other.getBoard().size();
		double score = (double)hp;
    ////System.err.println("nbr mes CardBOT4s : "+nbrCardBOT4s);
		for (int i = 0;i<nbrCardBOT4s;i++){
      if (this.me.getBoard().elementAt(i).getAttack() == 0 && this.me.getBoard().elementAt(i).getGuard() == false){
				score -= 300;
			}
			if(this.me.getBoard().elementAt(i).getId()>-1 && this.me.getBoard().elementAt(i).getDefense() > 0){ score += this.me.getBoard().elementAt(i).scoreBis(hp,hpOpponent,nbrCardBOT4s,nbrCardBOT4sOpponent,PlayerBOT4Number,TOUR);}
		}

		return score;
	}

  
	public double getHisBoardScore(CardBOT4 opponent,CardBOT4 mine,int PlayerBOT4Number,int TOUR){
	    
		int hp = mine.getDefense();
		if (hp <= 0){return (double)100000.0;}
		int hpOpponent = opponent.getDefense();
		int nbrCardBOT4s = this.me.getBoard().size();
		int nbrCardBOT4sOpponent = this.other.getBoard().size();
		double score = (double)hpOpponent;
    ////System.err.println("nbr ses CardBOT4s : "+nbrCardBOT4sOpponent);

		for (int i = 1;i<nbrCardBOT4sOpponent;i++){
      		if (this.other.getBoard().elementAt(i).getAttack() == 0 && this.other.getBoard().elementAt(i).getGuard() == false){
				score -= 300.0;
			}
			if (this.other.getBoard().elementAt(i).getId()>-1 && this.other.getBoard().elementAt(i).getDefense() > 0){score += (double)this.other.getBoard().elementAt(i).scoreBis(hpOpponent,hp,nbrCardBOT4sOpponent,nbrCardBOT4s,PlayerBOT4Number,TOUR);}
			
		}
		
		return score;
	}


	public double getTurnBOT4ScoreSummon(CardBOT4 opponent,CardBOT4 mine,int PlayerBOT4Number,int TOUR){
		double myScore = this.getMyBoardScore(opponent,mine,PlayerBOT4Number,TOUR);
		double hisScore =0;// this.getHisBoardScore(opponent,mine,PlayerBOT4Number,TOUR);
		return myScore - hisScore;
	}

	
  public CardBOT4 bestUseOfGreenCardBOT4(CardBOT4 greenItem,CardBOT4 opponent,CardBOT4 mine,Vector<CardBOT4> summonWithoutCharge){
		
		Vector<CardBOT4> b = new Vector<CardBOT4>();
  		b.addAll(this.me.getBoard());
		b.addAll(summonWithoutCharge);
		CardBOT4 bestAnswer = new CardBOT4(0);
		if (b.size() > 0){
			int indiceUse = ThreadLocalRandom.current().nextInt(0,b.size());
			bestAnswer = b.elementAt(indiceUse);
		}
		
		boolean isItReally = true;
		int bestScore = -1;

		
		

		return bestAnswer;
	}

	public CardBOT4 bestUseOfRedCardBOT4(CardBOT4 redItem,CardBOT4 opponent,CardBOT4 mine){
		Vector<CardBOT4> b = this.other.getBoard();
		if (redItem.getCardBOT4Number() == 142 || redItem.getCardBOT4Number() == 149){
			Vector<CardBOT4> possible = new Vector<CardBOT4>();
			for (int i = 0 ; i < b.size() ; i++){
				if (b.elementAt(i).getGuard() || b.elementAt(i).getWard() || b.elementAt(i).getLethal()){
					possible.add(b.elementAt(i));
				}
			}
			b = possible;
		} else if (redItem.getCardBOT4Number() == 143){
			Vector<CardBOT4> possible = new Vector<CardBOT4>();
			for (int i = 0 ; i < b.size() ; i++){
				if (b.elementAt(i).getGuard()){
					possible.add(b.elementAt(i));
				}
			}
			b = possible;
		}
		CardBOT4 bestAnswer = new CardBOT4(0);
		if (b.size() > 0){
			int indiceUse = ThreadLocalRandom.current().nextInt(0,b.size());
			bestAnswer = b.elementAt(indiceUse);
		}
		/*boolean isItReally = false;
		boolean changeAbilities = !(redItem.getAbilities().equals("------"));
		int bestScore = -1;*/
		////System.err.println(changeAbilities);

		/*if (redItem.getCardBOT4Number() == 151){
			bestScore = 6;
			for (int i = 0;i<b.size();i++){
				CardBOT4 c = b.elementAt(i);
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
				CardBOT4 c = b.elementAt(i);
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
				CardBOT4 c = b.elementAt(i);
				//score = c.getAttack();
				//if (c.getAttack() >= bestScore && c.getWard() == false){

				if (c.getAttack() >= bestScore && c.getWard() == false && c.getDefense() <= -redItem.getDefense() && c.getDefense() >= -redItem.getDefense() - 2){
					bestScore = c.getAttack();
					bestAnswer =c;
				}
				else if ( c.getWard() == true && (redItem.getDefense() == -1 ||redItem.getDefense() == -2)  ){
					bestAnswer =c;
					bestScore = 99;
				}


			}

		}*/
		

		return bestAnswer;



	}

  public Vector<Vector<CardBOT4>> generateRandomAttack(int compteur,long startTime,CardBOT4 opponent, CardBOT4 mine){


	int n = this.me.getBoard().size();
	Vector<CardBOT4> hisBoard = new Vector<CardBOT4>();
	Vector<CardBOT4> hisGuard = new Vector<CardBOT4>();
    
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

	Vector<Integer> indice = new Vector<Integer>();
		for (int i = 0;i<n;i++){
			indice.addElement(i);
		}
	Vector<Vector<CardBOT4>> renvoie = new Vector<Vector<CardBOT4>>();
	
		while (indice.size() > n - sizeAttack){
			int randomNum = ThreadLocalRandom.current().nextInt(0, indice.size());
			int indiceAttack = indice.elementAt(randomNum);
			CardBOT4 attacker = this.me.getBoard().elementAt(indiceAttack);
			int indiceDef;
			CardBOT4 defender;

			if (hisGuard.size() > 0){
				indiceDef = (int)ThreadLocalRandom.current().nextInt(0, hisGuard.size());
				defender = hisGuard.elementAt(indiceDef);
			}
			else {
				indiceDef = (int)ThreadLocalRandom.current().nextInt(0, hisBoard.size());
				defender = hisBoard.elementAt(indiceDef);
			}

			defender.getAttackBy(attacker,this.me,this.other,opponent,mine);

			if (defender.getDefense() <= 0 && defender.getId() > -1){
				if (defender.getGuard() == true){
					hisGuard.remove(defender);
					hisBoard.remove(defender);
				} else {
					hisBoard.remove(defender);
				}
			}

			Vector<CardBOT4> att = new Vector<CardBOT4>();
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

public Vector<Vector<CardBOT4>> generateRandomAttackOpponent(int compteur,long startTime,CardBOT4 opponent, CardBOT4 mine){


	int n = this.other.getBoard().size();
	Vector<CardBOT4> hisBoard = new Vector<CardBOT4>();
	Vector<CardBOT4> hisGuard = new Vector<CardBOT4>();
    
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
	Vector<Vector<CardBOT4>> renvoie = new Vector<Vector<CardBOT4>>();
	
		while (indice.size() > n - sizeAttack){
			int randomNum = ThreadLocalRandom.current().nextInt(0, indice.size());
			int indiceAttack = indice.elementAt(randomNum);
			CardBOT4 attacker = this.other.getBoard().elementAt(indiceAttack);
			int indiceDef;
			CardBOT4 defender;

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

			Vector<CardBOT4> att = new Vector<CardBOT4>();
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


public Vector<Vector<CardBOT4>> bestRandomAttackOpponent(CardBOT4 opponent,CardBOT4 mine){
	long startTimeOpponent = System.nanoTime();
	int iteration = 0;
	int testValide = 0;
	//double bestScore = -100000.0;
	//double bestScoreSecond = -100000.0;
	//int bestScoreBis = - 100000;
	int bestScore = -100000;
	/*for (int m = 0 ; m < this.other.getBoard().size();m++){
		CardBOT4 c = this.other.getBoard().elementAt(m);
		if (c.getDefense() >0 && c.getId() > -1){
			//scoreBis++;
			bestScore++;
			bestScore += c.getDefense() + c.getAttack();
		}
	}
	
	for (int m = 0 ; m < this.me.getBoard().size();m++){
		CardBOT4 c = this.me.getBoard().elementAt(m);
		if (c.getDefense() >0 && c.getId() > -1){
			//scoreBis--;
			bestScore--;
			bestScore -= (c.getDefense() + c.getAttack());
			
			
		}
	}*/
	////System.err.println("init best score : "+bestScore);
	double bestScoreBis = -100000.0;
	
	Vector<Vector<CardBOT4>> bestAnswer = new Vector<Vector<CardBOT4>>();
	long endTimeOpponent = System.nanoTime();
	Vector<Vector<Vector<CardBOT4>>> tested = new Vector<Vector<Vector<CardBOT4>>>();
	////System.err.println("mon board est de taille : "+ this.me.getBoard().size());
	while ((endTimeOpponent - startTimeOpponent) <300000 && iteration < 10){
	    ////System.err.println("iterations : "+iteration+" et le temps :"+(endTimeOpponent - startTimeOpponent));
		iteration++;
		/*if (this.me.getBoard().size() == 0){
			//System.err.println(mine.getDefense());
		}*/
		Vector<Vector<CardBOT4>> attaque = this.generateRandomAttackOpponent(0,startTimeOpponent,opponent,mine);
		
		//if (!(tested.contains(attaque)) && this.other.validAnswerBis(attaque,this.me,mine,opponent)){
		if (!(tested.contains(attaque))){
			Vector<CardBOT4> myDead = new Vector<CardBOT4>();
			Vector<CardBOT4> hisDead = new Vector<CardBOT4>();
			tested.addElement(attaque);
			testValide++;
			this.hisAttack(attaque,opponent,mine,myDead,hisDead);

		/* this.me.getBoard().removeElement(mine);
        Vector<Vector<CardBOT4>> hisNextAttack = this.attackDirecte(opponent,mine);
        this.me.getBoard().add(0,mine);
        for (int j = 0;j<hisNextAttack.size();j++){
					CardBOT4 attacker = hisNextAttack.elementAt(j).elementAt(0);
					CardBOT4 defender = hisNextAttack.elementAt(j).elementAt(1);
         
					if (defender.getGuard() == false){defender.getAttackBy(attacker,this.me,this.other,opponent,mine);}
					
					defender.counterAttack(attacker,this.me,this.other);

					//System.err.println("attacker after "+attacker.getDefense());
					//System.err.println("defender after "+defender.getDefense());


          
		}*/

			////System.err.println("end attack");
			//int scoreBis = 0;
			int score = 0;
			
		for (int m = 0 ; m < this.me.getBoard().size();m++){
			CardBOT4 c = this.me.getBoard().elementAt(m);
			if (c.getDefense() >0 && c.getId() > -1){
				//scoreBis++;
				score--;
				score -= c.scoreTest(1,opponent.getDefense(),mine.getDefense());
			}
		}
		for (int m = 0 ; m < this.other.getBoard().size();m++){
			CardBOT4 c = this.other.getBoard().elementAt(m);
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
			////System.err.println("best Score : "+bestScore+" score actuel : "+score);
			
			//double tempScoreSecond = getTurnBOT4Score(opponent,mine);//this.getTurnBOT4Score(opponent, mine);
			//double tempScoreSecond = mine.getDefense() - opponent.getDefense();
			//double scoreBis = this.getTurnBOT4Score(opponent, mine);
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
				CardBOT4 attacker = hisNextAttack.elementAt(j).elementAt(0);
				CardBOT4 defender = hisNextAttack.elementAt(j).elementAt(1);
	  
				defender.getDeattackBy(attacker,this.me,this.other,opponent,mine);
				defender.deCounterAttack(attacker,this.me,this.other);

			}*/
			
			for (int i = attaque.size()-1;i >= 0;i--){
				CardBOT4 attacker = attaque.elementAt(i).elementAt(0);
				CardBOT4 defender = attaque.elementAt(i).elementAt(1);
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
	    ////System.err.println("nombre de test  --> "+iteration+ "en "+(endTimeOpponent - startTimeOpponent)+ "ns");
		//System.err.println("nombre de test valide --> "+testValide);
	opponent.resetSmall();
	mine.resetSmall();
///System.err.println("opponent realise : "+iteration);
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

	public Vector<Vector<CardBOT4>> attackBasicSolutionBOT4Direct(CardBOT4 opponent,CardBOT4 mine){
		Vector<Vector<CardBOT4>> renvoie = new Vector<Vector<CardBOT4>>();
		Vector<CardBOT4> b = this.other.boardOrderByAttack();
		Vector<CardBOT4> bOpponent = this.me.getBoard();
		Vector<CardBOT4> g = this.me.getGuardsInBoard();

		for (int i = 0;i<b.size();i++){
			if (!b.elementAt(i).getUsed() && b.elementAt(i).getId()>-1){
			if (g.size() == 0){

				Vector<CardBOT4> att = new Vector<CardBOT4>();
				att.addElement(b.elementAt(i));
				att.addElement(mine);
				renvoie.addElement(att);
			}
			else {

				CardBOT4 c = g.firstElement();

				c.getAttackBy(b.elementAt(i),this.other,this.me,mine,opponent);
				if (c.getDefense() <= 0){g.removeElementAt(0);}
				Vector<CardBOT4> att = new Vector<CardBOT4>();
				att.addElement(b.elementAt(i));
				att.addElement(c);
				renvoie.addElement(att);
			}
		}
	}
		for (int i = 0 ; i < renvoie.size() ; i++){
			CardBOT4 attacker = renvoie.elementAt(i).elementAt(0);
			CardBOT4 defender = renvoie.elementAt(i).elementAt(1);
			attacker.resetSmall();
			defender.resetSmall();

		}
		return renvoie;
	}

	public Vector<Vector<CardBOT4>> attackDirecte(CardBOT4 opponent,CardBOT4 mine){
		Vector<Vector<CardBOT4>> renvoie = new Vector<Vector<CardBOT4>>();
		Vector<CardBOT4> b = this.me.boardOrderByAttack();
		Vector<CardBOT4> bOpponent = this.other.getBoard();
		Vector<CardBOT4> g = this.other.getGuardsInBoard();
		Vector<CardBOT4> attacked = new Vector<CardBOT4>();
		for (int i = 0;i<b.size();i++){
			
			
			if (g.size() == 0){

				Vector<CardBOT4> att = new Vector<CardBOT4>();
				att.addElement(b.elementAt(i));
				att.addElement(opponent);
				renvoie.addElement(att);
			}
			else {

				CardBOT4 c = g.firstElement();

				c.getAttackBy(b.elementAt(i),this.me,this.other,opponent,mine);
				attacked.add(c);
				if (c.getDefense() <= 0){g.removeElementAt(0);}
				Vector<CardBOT4> att = new Vector<CardBOT4>();
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
public void updateItem(Vector<CardBOT4> tempUseGreenItem,Vector<CardBOT4> tempUseRedItem){
	for (int i = 0 ; i < tempUseGreenItem.size() ; i++){
		tempUseGreenItem.elementAt(i).upgradeLife();
	}
	for (int i = 0 ; i < tempUseRedItem.size() ; i++){
		tempUseRedItem.elementAt(i).upgradeLife();
	}
}
public void summonThings(Vector<CardBOT4> summoning,Vector<CardBOT4> summonWithoutCharge,CardBOT4 opponent, CardBOT4 mine,Vector<CardBOT4> tempGreenItem,Vector<CardBOT4> tempRedItem,Vector<CardBOT4> tempUseGreenItem,Vector<CardBOT4> tempUseRedItem){
	
	if (summoning.size() > 0){
		//System.err.print("summoning : ");
		for (int j = 0;j<summoning.size();j++){
			CardBOT4 c = summoning.elementAt(j);
			
			//System.err.print(" ; "+c.getId()+" ; ");
			if (c.getCardBOT4Type() < 1){
				if (c.getCharge() == true){this.me.getBoard().addElement(c);}
				else {summonWithoutCharge.addElement(c);}
				opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setDefense(mine.getDefense() + c.getHpChange());
				opponent.setOldDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setOldDefense(mine.getDefense() + c.getHpChange());
				////System.err.print("Go FOR "+c.getId()+" ; ");
				
				
			}
			else if (c.getCardBOT4Type() == 1 && this.me.getBoard().size()>0){
				CardBOT4 bestUse = this.bestUseOfGreenCardBOT4(c, opponent, mine,summonWithoutCharge);
				if (bestUse.getId() >-1){
					
					tempUseGreenItem.addElement(bestUse);
					tempGreenItem.addElement(c);
					bestUse.getBuffByGreenCardBOT4(c);
					////System.err.print("USE"+" "+c.getId()+" "+bestUse.getId()+" ; ");
				}
				
			}
			else if (c.getCardBOT4Type() == 2 && this.other.getBoard().size()>1){
				CardBOT4 bestUse = bestUseOfRedCardBOT4(c, opponent, mine);
				if (bestUse.getId()>-1){
					
					tempUseRedItem.addElement(bestUse);
					tempRedItem.addElement(c);
					////System.err.println("USE"+" "+c.getId()+" "+bestUse.getId()+" ; ");
					bestUse.getBuffByRedCardBOT4(c);
					if (bestUse.getDefense() <= 0){
						this.other.getBoard().remove(bestUse);
					}
					opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
					mine.setDefense(mine.getDefense() + c.getHpChange());
					opponent.setOldDefense(opponent.getDefense() +c.getHpChangeOpponent());
					mine.setOldDefense(mine.getDefense() + c.getHpChange());
				} 
				
			} else if(c.getCardBOT4Type() == 3) {
				
				////System.err.println("USE"+" "+c.getId()+" ; ");
				opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setDefense(mine.getDefense() + c.getHpChange());
				opponent.setOldDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setOldDefense(mine.getDefense() + c.getHpChange());
				
			}

		}
	}
}

public void unSummonThings(Vector<CardBOT4> summoning,Vector<CardBOT4> summonWithoutCharge,CardBOT4 opponent, CardBOT4 mine,Vector<CardBOT4> tempGreenItem,Vector<CardBOT4> tempRedItem,Vector<CardBOT4> tempUseGreenItem,Vector<CardBOT4> tempUseRedItem){
	for (int j = summoning.size()-1;j>=0;j--){
		CardBOT4 c = summoning.elementAt(j);
		if (c.getCardBOT4Type() < 1){
			if (c.getCharge() == true){this.me.getBoard().removeElement(c);}
			else {summonWithoutCharge.removeElement(c);}
		
		} else if(c.getCardBOT4Type() == 3) {
			//CardBOT4 reverseC = new CardBOT4(c);
			//this.me.getBoard().removeElement(reverseC);
			opponent.setDefense(opponent.getDefense() -c.getHpChangeOpponent());
				mine.setDefense(mine.getDefense() - c.getHpChange());
		}

	}
	for (int m = 0;m<tempGreenItem.size();m++){
		CardBOT4 objet = tempGreenItem.elementAt(m);
		CardBOT4 bestUse = tempUseGreenItem.elementAt(m);
		bestUse.getDebuffByGreenCardBOT4(objet);
	}

	for (int m = 0 ; m<tempRedItem.size();m++){
		CardBOT4 objet = tempRedItem.elementAt(m);
		CardBOT4 bestUse = tempUseRedItem.elementAt(m);
		if (bestUse.getDefense()<=0 && !this.other.getBoard().contains(bestUse)){
			this.other.getBoard().add(bestUse);
		}
		bestUse.getDebuffByRedCardBOT4(objet);		
	}
}

public void myAttack(Vector<Vector<CardBOT4>> attaque,CardBOT4 opponent,CardBOT4 mine,Vector<CardBOT4> myDead,Vector<CardBOT4> hisDead){
		//System.err.println("");
		//System.err.print("Mon attaque ");
		for (int j = 0;j<attaque.size();j++){
			CardBOT4 attacker = attaque.elementAt(j).elementAt(0);
			CardBOT4 defender = attaque.elementAt(j).elementAt(1);
			//System.err.print(attacker.getId()+" "+defender.getId()+" ; ");
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

public void hisAttack(Vector<Vector<CardBOT4>> hisNextAttack,CardBOT4 opponent,CardBOT4 mine,Vector<CardBOT4> myDead,Vector<CardBOT4> hisDead){
		//System.err.println("");
		////System.err.print("Son attaque ");
        for (int j = 0;j<hisNextAttack.size();j++){
					CardBOT4 attacker = hisNextAttack.elementAt(j).elementAt(0);
					CardBOT4 defender = hisNextAttack.elementAt(j).elementAt(1);
					////System.err.print(attacker.getId()+" "+defender.getId()+" ; ");
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

public void update(Vector<CardBOT4> summonWithoutCharge){
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

public void downgrade(Vector<CardBOT4> summonWithoutCharge,Vector<CardBOT4> myDead,Vector<CardBOT4> hisDead){
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

public void resetFull(Vector<CardBOT4> summonWithoutCharge){
	for (int m = 0 ; m < this.me.getBoard().size();m++){
		CardBOT4 c = this.me.getBoard().elementAt(m);
		c.reset();
	}
	for (int m = 0 ; m < this.me.getHandBOT4().size();m++){
		CardBOT4 c = this.me.getHandBOT4().elementAt(m);
		c.reset();
	}
	for (int m = 0 ; m < summonWithoutCharge.size();m++){
		CardBOT4 c = summonWithoutCharge.elementAt(m);
		c.reset();
	}
	for (int m = 0 ; m < this.other.getBoard().size();m++){
		CardBOT4 c = this.other.getBoard().elementAt(m);
		c.reset();
	}
	this.me.clean();
	this.other.clean();
}


public String bestAttackRandomDepthSummon(String a,long startTime,Vector<CardBOT4> summonWithoutCharge,CardBOT4 mine){
	int mana = this.me.getMana();
	HandBOT4 HandBOT4 = new HandBOT4(this.me.getHandBOT4());
	int nbrCarte = this.me.getBoard().size();
	int compteur = 0;
	int compteurGlobal = 0;
	Vector<CardBOT4> bestSummon = new Vector<CardBOT4>();
	Vector<Vector<CardBOT4>> bestAnswer = new Vector<Vector<CardBOT4>>();
  	CardBOT4 opponent = this.other.getBoard().elementAt(0);
	//double bestScore = -10000000.0;//this.getTurnBOT4Score(opponent,mine);
	//double bestScoreSecond = -1000000.0;
	//int bestScoreBis = - 100000;  
	int bestScoreBis = -10000;
	//init initScore = -1000000;////this.getTurnBOT4Score(opponent,mine);
	int bestScore = -100;
	double bestScoreSecond = - 100000;  
	Vector<CardBOT4> bestGreenItem = new Vector<CardBOT4>();
	Vector<CardBOT4> bestRedItem = new Vector<CardBOT4>();
	Vector<CardBOT4> bestUseGreenItem = new Vector<CardBOT4>();
	Vector<CardBOT4> bestUseRedItem = new Vector<CardBOT4>();
	long endTime = System.nanoTime();
	

	while ((endTime - startTime) <95*1000000){
		Vector<CardBOT4> summoning = HandBOT4.randomSummon(mana, nbrCarte,this.me);
		Vector<CardBOT4> tempGreenItem = new Vector<CardBOT4>();
		Vector<CardBOT4> tempRedItem = new Vector<CardBOT4>();
		Vector<CardBOT4> tempUseGreenItem = new Vector<CardBOT4>();
		Vector<CardBOT4> tempUseRedItem = new Vector<CardBOT4>();
		Vector<CardBOT4> myDead = new Vector<CardBOT4>();
		Vector<CardBOT4> hisDead = new Vector<CardBOT4>();
		compteurGlobal++;
		
		this.summonThings(summoning,summonWithoutCharge,opponent,mine,tempGreenItem,tempRedItem,tempUseGreenItem,tempUseRedItem);
		////System.err.println("mes hp : "+mine.getDefense()+" ; "+mine.getOldDefense());
		this.updateItem(tempUseGreenItem,tempUseRedItem);
		
		Vector<Vector<CardBOT4>> attaque = new Vector<Vector<CardBOT4>>();
		boolean test;
		
		/*do {
			/*if (this.other.getBoard().size() == 0){
				//System.err.println(opponent.getDefense());
			}
			//compteurtestForMe++;
			
			endTimeAttack = System.nanoTime();
			test = this.me.validAnswerBis(attaque,this.other,opponent,mine);
		} while((endTimeAttack-startTimeAttack)<500000 && !test);

		if (test == false){
			attaque = this.attackDirecte(opponent, mine);
		} else {compteur++;}*/
		//System.err.println(" nbr de try needed : "+compteurtestForMe);
		attaque = this.generateRandomAttack(compteur, startTime, opponent, mine);
		//compteur++;
		this.myAttack(attaque,opponent,mine,myDead,hisDead);
		this.update(summonWithoutCharge);
		//double testScore = this.getTurnBOT4Score(opponent,mine);
//testScore >= bestScore
		if (true){
		compteur++;
		
		this.other.getBoard().removeElement(opponent);
		if (this.me.getBoard().contains(mine) == false){this.me.getBoard().add(mine);}
        Vector<Vector<CardBOT4>> hisNextAttack =this.bestRandomAttackOpponent(opponent,mine);
		this.other.getBoard().add(0,opponent);
		

		this.hisAttack(hisNextAttack,opponent,mine,myDead,hisDead);
		//System.err.println("his attack :");
		
		
		int tempScore = 0;
		for (int m = 0 ; m < this.me.getBoard().size();m++){
			CardBOT4 c = this.me.getBoard().elementAt(m);
			if (c.getDefense() >0 && c.getId() > -1){
				tempScore+=c.scoreTest(0,mine.getDefense(),opponent.getDefense());
			}
		}
		for (int m = 0 ; m < this.other.getBoard().size();m++){
			CardBOT4 c = this.other.getBoard().elementAt(m);
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
		/*int runeDropped = 0;
		int oldHp = opponent.getOldDefense();
		int hp = opponent.getDefense();

		if (oldHp > 25 && hp <= 25){runeDropped++;}
		if (oldHp > 20 && hp <= 20){runeDropped++;}
		if (oldHp > 15 && hp <= 15){runeDropped++;}
		if (oldHp > 10 && hp <= 10){runeDropped++;}
		if (oldHp > 5 && hp <= 5){runeDropped++;}
		tempScore += drawThanksOfSummon + (myHandBOT4Size - numberOfSummoned) - hisHandBOT4Size - runeDropped;*/
		//double tempScoreSecond = mine.getDefense() - opponent.getDefense();
		//double tempScore = this.getTurnBOT4Score(opponent,mine);
		double tempScoreSecond = getTurnBOT4Score(opponent,mine);//this.getTurnBOT4Score(opponent,mine);
		int tempScoreBis = mine.getDefense() - opponent.getDefense();
		//System.err.println("de score "+tempScore+" de deltaHp "+tempScoreBis);
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
			
			Vector<CardBOT4> items = new Vector<CardBOT4>();
			Vector<CardBOT4> finalAnswer = bestSummon;
			for (int i = 0;i<finalAnswer.size();i++){
				CardBOT4 c = finalAnswer.elementAt(i);
				if (c.getCardBOT4Type() == 0){
					a += c.printSUMMON();
					this.me.setMana(this.me.getMana()-c.getCost());
					if (c.getHpChange() != 0){
						mine.setDefense(mine.getDefense()+c.getHpChange());
						mine.setOldDefense(mine.getOldDefense()+c.getHpChange());
					}

					if (c.getHpChangeOpponent() != 0){
						opponent.setOldDefense(opponent.getOldDefense()+c.getHpChangeOpponent());
					}


					this.me.getHandBOT4().removeElement(c);
					if (c.getCharge()){this.me.getBoard().addElement(c);}
					  else {
						summonWithoutCharge.addElement(c);
						this.me.getBoard().addElement(c);}
				} else if (c.getCardBOT4Type() == 3){
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
				CardBOT4 objet = bestGreenItem.elementAt(m);
				CardBOT4 bestUse = bestUseGreenItem.elementAt(m);
				this.me.getHandBOT4().removeElement(objet);
				bestUse.getBuffByGreenCardBOT4Bis(objet);
				a += bestUse.printUSE(objet);
				this.me.setMana(this.me.getMana()-objet.getCost());
			}
			for (int m = 0 ; m<bestRedItem.size();m++){
				CardBOT4 objet = bestRedItem.elementAt(m);
				CardBOT4 bestUse = bestUseRedItem.elementAt(m);
				bestUse.getBuffByRedCardBOT4(objet);
				this.me.getHandBOT4().removeElement(objet);
				a += bestUse.printUSE(objet);
				this.me.setMana(this.me.getMana()-objet.getCost());
			}

			
			endTime = System.nanoTime();


			for (int i = 0;i<bestAnswer.size();i++){
				CardBOT4 attacker = bestAnswer.elementAt(i).elementAt(0);
				CardBOT4 defender = bestAnswer.elementAt(i).elementAt(1);
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
	System.err.println("nombre de test summon tried : "+compteurGlobal+" de valide : "+compteur);

	return a;


}

public String summonPossibilities(String a,Vector<CardBOT4> summonWithoutCharge,CardBOT4 opponent,CardBOT4 mine,int PlayerBOT4Number,int TOUR){

	HandBOT4 HandBOT4 = new HandBOT4(this.me.getHandBOT4());
	int mana = this.me.getMana();
	

		Vector<Vector<CardBOT4>> possible = HandBOT4.allPosibilitesWithoutItem(mana,this.me.getBoard().size(),this.me,this.other);
		if (possible.size() >0){
		double bestScore = -100000000.0;
		int bestScoreSecond = -1;
		Vector<CardBOT4> bestSummon = new Vector<CardBOT4>();
			Vector<CardBOT4> bestGreenItem = new Vector<CardBOT4>();
	Vector<CardBOT4> bestRedItem = new Vector<CardBOT4>();
	Vector<CardBOT4> bestUseGreenItem = new Vector<CardBOT4>();
	Vector<CardBOT4> bestUseRedItem = new Vector<CardBOT4>();

		for (int i = 0;i<possible.size();i++){
			double score = 0;
			Vector<CardBOT4> summoning = possible.elementAt(i);
	////System.err.println("");
			int manaUsed = 0;

			Vector<CardBOT4> bestUseGreen = new Vector<CardBOT4>();
			Vector<CardBOT4> bestUseRed = new Vector<CardBOT4>();
			Vector<CardBOT4> tempGreenItem = new Vector<CardBOT4>();
	Vector<CardBOT4> tempRedItem = new Vector<CardBOT4>();
	Vector<CardBOT4> tempUseGreenItem = new Vector<CardBOT4>();
	Vector<CardBOT4> tempUseRedItem = new Vector<CardBOT4>();
	
	
	if (summoning.size() > 0){
	for (int j = 0;j<summoning.size();j++){
		CardBOT4 c = summoning.elementAt(j);
		
		////System.err.println("Go FOR (full liste) "+c.getId()+" ; ");
		if (c.getCardBOT4Type() < 1){
			manaUsed += c.getCost();
			if (c.getCharge() == true){this.me.getBoard().addElement(c);}
			else {summonWithoutCharge.addElement(c);}
			////System.err.print("Go FOR "+c.getId()+" ; ");
			
			
		}
		else if (c.getCardBOT4Type() == 1 && this.me.getBoard().size()+summonWithoutCharge.size()>0){
			CardBOT4 bestUse = this.bestUseOfGreenCardBOT4(c, opponent, mine,summonWithoutCharge);
			if (bestUse.getId() >-1){
				manaUsed += c.getCost();
				tempUseGreenItem.addElement(bestUse);
				tempGreenItem.addElement(c);
				bestUseGreen.addElement(bestUse);
				bestUse.getBuffByGreenCardBOT4(c);
				////System.err.print("USE"+" "+c.getId()+" "+bestUse.getId()+" ; ");
			}
			else {bestUseGreen.addElement(bestUse);}
		}
		else if (c.getCardBOT4Type() == 2 && this.other.getBoard().size()>1){
			CardBOT4 bestUse = bestUseOfRedCardBOT4(c, opponent, mine);
			if (bestUse.getId()>-1){
				manaUsed += c.getCost();
				tempUseRedItem.addElement(bestUse);
				tempRedItem.addElement(c);
				////System.err.println("USE"+" "+c.getId()+" "+bestUse.getId()+" ; ");
				bestUseRed.addElement(bestUse);
				bestUse.getBuffByRedCardBOT4(c);
				if (bestUse.getDefense() <= 0){
					this.other.getBoard().remove(bestUse);
				}
				opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
				mine.setDefense(mine.getDefense() + c.getHpChange());
				
			} else {bestUseRed.addElement(bestUse);}
			
		} else if(c.getCardBOT4Type() == 3) {
			manaUsed += c.getCost();
			////System.err.println("USE"+" "+c.getId()+" ; ");
			opponent.setDefense(opponent.getDefense() +c.getHpChangeOpponent());
			mine.setDefense(mine.getDefense() + c.getHpChange());
		}

	}
}
for (int me = 0;me<summonWithoutCharge.size();me++){
	this.me.getBoard().add(summonWithoutCharge.elementAt(me));
}


			score = getTurnBOT4ScoreSummon(opponent,mine,1,30);//this.getTurnBOT4Score(opponent,mine);
			int scoreSecond  = manaUsed;
			////System.err.println(score);
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
				////System.err.print("best score :"+bestScore);
				  //System.err.println();
			}
			else if (this.areEqual(score,bestScore) == true && scoreSecond > bestScoreSecond){
				bestScoreSecond = scoreSecond;
				bestSummon = summoning;
				bestGreenItem = tempGreenItem;
				bestRedItem = tempRedItem;
				bestUseGreenItem = tempUseGreenItem;
				bestUseRedItem = tempUseRedItem;
			//	//System.err.print("best score :"+bestScore+" de scoreSecond : "+scoreSecond);
				  //System.err.println();
			}
			////System.err.print("mon tempo ");
			for (int j = summoning.size()-1;j>=0;j--){
				CardBOT4 c = summoning.elementAt(j);
				if (c.getCardBOT4Type() < 1){
					if (c.getCharge() == true){this.me.getBoard().removeElement(c);}
					else {summonWithoutCharge.removeElement(c);}
				
				} else if(c.getCardBOT4Type() == 3) {
					//CardBOT4 reverseC = new CardBOT4(c);
					//this.me.getBoard().removeElement(reverseC);
					opponent.setDefense(opponent.getDefense() -c.getHpChangeOpponent());
						mine.setDefense(mine.getDefense() - c.getHpChange());
				}
	
			}
			for (int m = 0;m<tempGreenItem.size();m++){
				CardBOT4 objet = tempGreenItem.elementAt(m);
				CardBOT4 bestUse = tempUseGreenItem.elementAt(m);
				bestUse.getDebuffByGreenCardBOT4(objet);
			}
	
			for (int m = 0 ; m<tempRedItem.size();m++){
				CardBOT4 objet = tempRedItem.elementAt(m);
				CardBOT4 bestUse = tempUseRedItem.elementAt(m);
				if (bestUse.getDefense()<=0 && !this.other.getBoard().contains(bestUse)){
					this.other.getBoard().add(bestUse);
				}
				bestUse.getDebuffByRedCardBOT4(objet);		
			}
			
			opponent.resetSmall();
			mine.resetSmall();
			this.other.clean();
		}

		Vector<CardBOT4> items = new Vector<CardBOT4>();
		Vector<CardBOT4> finalAnswer = bestSummon;
		for (int i = 0;i<finalAnswer.size();i++){
			CardBOT4 c = finalAnswer.elementAt(i);
			if (c.getCardBOT4Type() == 0){
				a += c.printSUMMON();
				this.me.setMana(this.me.getMana()-c.getCost());
				if (c.getHpChange() != 0){
					mine.setDefense(mine.getDefense()+c.getHpChange());
					mine.setOldDefense(mine.getOldDefense()+c.getHpChange());
				}

				if (c.getHpChangeOpponent() != 0){
					opponent.setOldDefense(opponent.getOldDefense()+c.getHpChangeOpponent());
				}


				this.me.getHandBOT4().removeElement(c);
				if (c.getCharge()){this.me.getBoard().addElement(c);}
				  else {
					summonWithoutCharge.addElement(c);
					this.me.getBoard().addElement(c);}
			} else if (c.getCardBOT4Type() == 3){
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
			CardBOT4 objet = bestGreenItem.elementAt(m);
			CardBOT4 bestUse = bestUseGreenItem.elementAt(m);
			this.me.getHandBOT4().removeElement(objet);
			bestUse.getBuffByGreenCardBOT4Bis(objet);
			a += bestUse.printUSE(objet);
			this.me.setMana(this.me.getMana()-objet.getCost());
		}
		for (int m = 0 ; m<bestRedItem.size();m++){
			CardBOT4 objet = bestRedItem.elementAt(m);
			CardBOT4 bestUse = bestUseRedItem.elementAt(m);
			bestUse.getBuffByRedCardBOT4(objet);
			if (bestUse.getDefense() <= 0){
				this.other.getBoard().remove(bestUse);
			}
			this.me.getHandBOT4().removeElement(objet);
			a += bestUse.printUSE(objet);
			this.me.setMana(this.me.getMana()-objet.getCost());
		}

		}
	for (int p = 0;p<summonWithoutCharge.size();p++){
		this.me.getBoard().removeElement(summonWithoutCharge.elementAt(p));
	}
	return a;

}



	public void pickDraft(DeckBOT4 d,int PlayerBOT4Number){
		if (this.getTurnBOT4() < 30){
			int score = -100000;
			int best = 0;

			Vector<CardBOT4> h = this.me.getHandBOT4();
			d.incrCardBOT4(h.elementAt(0));
			int score0 = d.scoreCardBOT4InDraft(h.elementAt(0),PlayerBOT4Number);
			d.decrCardBOT4(h.elementAt(0));
			d.incrCardBOT4(h.elementAt(1));
			int score1 = d.scoreCardBOT4InDraft(h.elementAt(1),PlayerBOT4Number);
			d.decrCardBOT4(h.elementAt(1));
			d.incrCardBOT4(h.elementAt(2));
			int score2 = d.scoreCardBOT4InDraft(h.elementAt(2),PlayerBOT4Number);
			d.decrCardBOT4(h.elementAt(2));

			//System.err.println(score0 +" "+score1+" "+score2);

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

			CardBOT4 bestCardBOT4 = h.elementAt(best);

            
			d.incrCardBOT4(bestCardBOT4);
			bestCardBOT4.printPICK();

		}

	}


  public String attackBasicBis(String a,long startTime,Vector<CardBOT4> summonWithoutCharge,CardBOT4 mine){
		CardBOT4 opponent = this.other.getBoard().elementAt(0);
		//Vector<Vector<CardBOT4>> answer = this.bestAttackRandom(startTime,summonWithoutCharge,mine);
		//Vector<Vector<CardBOT4>> answer =  bestRandomAttack(opponent,mine,summonWithoutCharge,0);

		a = bestAttackRandomDepthSummon(a,startTime,summonWithoutCharge,mine);
		/*for (int i = 0;i<answer.size();i++){
			a += answer.elementAt(i).elementAt(0).printATTACK(answer.elementAt(i).elementAt(1));
      
		}*/
		return a;
	}



}

class PlayerBOT4{

public static void main(String args[]) {
		int TOUR = 0;
		DeckBOT4 d = new DeckBOT4();
		int PlayerBOT4Number = 1;

		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {


				int JoueurBOT4Health = in.nextInt();
				int JoueurBOT4Mana = in.nextInt();
				int JoueurBOT4DeckBOT4 = in.nextInt();
				int JoueurBOT4Rune = in.nextInt();
				//JoueurBOT4 JoueurBOT41 = new JoueurBOT4(JoueurBOT4Health,JoueurBOT4Mana,JoueurBOT4DeckBOT4,JoueurBOT4Rune);
				int JoueurBOT4HealthOpponent = in.nextInt();
				int JoueurBOT4ManaOpponent = in.nextInt();
				int JoueurBOT4DeckBOT4Opponent = in.nextInt();
				int JoueurBOT4RuneOpponent = in.nextInt();
				//JoueurBOT4 JoueurBOT42 = new JoueurBOT4(JoueurBOT4HealthOpponent,JoueurBOT4ManaOpponent,JoueurBOT4DeckBOT4Opponent,JoueurBOT4RuneOpponent);
				int opponentHandBOT4 = in.nextInt();
				Vector<CardBOT4> fake = new Vector<CardBOT4>();
				for (int o = 0;o<opponentHandBOT4;o++){
					CardBOT4 lol = new CardBOT4(0);
					fake.add(lol);
				}
				int CardBOT4Count = in.nextInt();
				Vector<CardBOT4> board = new Vector();
				Vector<CardBOT4> HandBOT4 = new Vector();
				Vector<CardBOT4> boardOpponent = new Vector();
        		Vector<CardBOT4> summonWithoutCharge = new Vector();
				CardBOT4 opponent = new CardBOT4(JoueurBOT4HealthOpponent);
        		CardBOT4 mine = new CardBOT4(JoueurBOT4Health);
				boardOpponent.addElement(opponent);
				summonWithoutCharge.addElement(mine);
				long startTime = System.nanoTime();
				for (int i = 0; i < CardBOT4Count; i++) {
						int CardBOT4Number = in.nextInt();
						

						int instanceId = in.nextInt();
						int location = in.nextInt();
						int CardBOT4Type = in.nextInt();
				
						int cost = in.nextInt();
						int attack = in.nextInt();
						int defense = in.nextInt();
						if (CardBOT4Type == 3 && defense != 0){
							CardBOT4Type = 2;
						}
						String abilities = in.next();
						int myHealthChange = in.nextInt();
						int opponentHealthChange = in.nextInt();
						int CardBOT4Draw = in.nextInt();
						CardBOT4 c = new CardBOT4(CardBOT4Number,instanceId,location,CardBOT4Type,cost,attack,defense,abilities,myHealthChange,opponentHealthChange,CardBOT4Draw,i);
						if (location == 0){
							if (!c.getCharge()) {c.setUsed(true);}
							HandBOT4.add(c);
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
				JoueurBOT4 JoueurBOT41 = new JoueurBOT4(JoueurBOT4Health,JoueurBOT4Mana,JoueurBOT4DeckBOT4,JoueurBOT4Rune,HandBOT4,board);
				JoueurBOT4 JoueurBOT42 = new JoueurBOT4(JoueurBOT4HealthOpponent,JoueurBOT4ManaOpponent,JoueurBOT4DeckBOT4Opponent,JoueurBOT4RuneOpponent,fake,boardOpponent);
				//JoueurBOT4 JoueurBOT42 = new JoueurBOT4(JoueurBOT4HealthOpponent,JoueurBOT4ManaOpponent,JoueurBOT4DeckBOT4Opponent,JoueurBOT4RuneOpponent,fake,hisGuards);

				//JoueurBOT41.setHandBOT4(HandBOT4);
				//JoueurBOT41.setBoard(board);
				//JoueurBOT42.setBoard(boardOpponent);
				TurnBOT4 tour = new TurnBOT4(TOUR,JoueurBOT41,JoueurBOT42);




        /*if (TOUR == 0){
            //System.err.println("le vecteur se calcul en " + (e-s) + " ns");
            //System.err.println("c'est-à-dire en " + (e-s)/1000000 + " ms");
            //System.err.println(test);
        }
        */

				if (TOUR == 0){
					
					if (JoueurBOT4DeckBOT4Opponent == 0){PlayerBOT4Number = 1;}
					if (JoueurBOT4DeckBOT4Opponent == 1){PlayerBOT4Number = 2;}
					//System.err.println(PlayerBOT4Number);
				}
				if (TOUR <30){tour.pickDraft(d,PlayerBOT4Number);}
				else{
				    String answer = "PASS;";
            	String hisAnswer = "";
					//	if (tour.getOther().getBoard().size() >1){//System.err.println(" test his life :" + tour.getOther().getBoard().elementAt(1).getDefense());}
					

						if (tour.getMe().getBoard().size()>-1){

							//answer = tour.summonPossibilities(answer,summonWithoutCharge,opponent,mine,PlayerBOT4Number,TOUR);

							answer = tour.attackBasicBis(answer,startTime,summonWithoutCharge,mine);
							//answer = tour.summonPossibilities(answer,summonWithoutCharge,opponent,mine,PlayerBOT4Number,TOUR);
							//answer = tour.attackBasic(answer,mine);
							//answer = tour.attackBasicHeuristique(answer,mine);
							for (int m = 0;m<tour.getMe().getBoard().size();m++){
								answer+="ATTACK"+" "+tour.getMe().getBoard().elementAt(m).getId()+" "+"-1;";
							
							}
							answer = tour.summonPossibilities(answer,summonWithoutCharge,opponent,mine,PlayerBOT4Number,TOUR);

						}

            /*
            long hisStartTime = System.nanoTime();
            boardOpponent.removeElement(opponent);
            if (tour.getOther().getBoard().size()>0){
              for (int me = 0;me<summonWithoutCharge.size();me++){
                board.add(summonWithoutCharge.elementAt(me));
                //System.err.println(summonWithoutCharge.elementAt(me).getId());
              }
              hisAnswer = tour.attackBasicBisOpponent(hisAnswer,startTime);
              for (int me = 0;me<summonWithoutCharge.size();me++){
                board.removeElement(summonWithoutCharge.elementAt(me));
              }
            }
            boardOpponent.add(0,opponent);
            long hisEndTime = System.nanoTime();
            //System.err.println("son move se calcul en " + (hisEndTime - hisStartTime) + " ns");
            //System.err.println("c'est-à-dire en " + (hisEndTime - hisStartTime)/1000000 + " ms");
						//answer = tour.attackBasic(answer);
						/*for (int i = 0;i<tour.getMe().getBoard().size();i++){
							answer += "ATTACK "+tour.getMe().getBoard().elementAt(i).getId()+" "+"-1;";
						}*/





				    long endTime = System.nanoTime();
						/*



				    while (((endTime - startTime)/1000000) < 110){
				        endTime = System.nanoTime();
				        }
						*/
            //for (int t = 0;t<tour.getOther().getBoard().size();t++){  //System.err.println(" "+tour.getOther().getBoard().elementAt(t).getId());}

            System.err.println("timer1 :  " + (endTime - startTime) + " ns");
            System.err.println("c'est-à-dire en " + (endTime - startTime)/1000000 + " ms");
						System.out.println(answer);
				}





				// Write an action using System.out.println()
				// To debug: //System.err.println("Debug messages...");

				TOUR++;
		}
}
}
