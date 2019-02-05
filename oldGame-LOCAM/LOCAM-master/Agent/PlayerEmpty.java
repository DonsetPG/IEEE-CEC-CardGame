
/*
* l'ennemi ne prend pas en compte les cartes juste posées car je ne les considère pas dans mon board;
* revoir le summon... (Pas de guard alors que sinon je meurs);
* si board full : summon après attack;
* hardcoder toutes les possibilités en fonction du nombre boardME/boardOPPONENT;
* simplifier la fonction d'évaluation??;
* allpossibilities de summon bugggg;
*Mugu-Mugu 12:12PM
ant colony optimization
* améliorer la vitesse du random avec des permutations des qu'une Solution1 est valide?
* ou en ajoutant un tableau qui maj les Solution1s déjà testées;
*
*/

/*
* ne pas considerer sa carte player quand on calcul ses attacks;
* mettre à jour son board avec les cartes qui ont moins de 0 pdv;
* Careful avec les timeouts!!;
* dans attack et counter attack : remplacer opponent Joueur1 par la carte opponent;
* checker qu'on ne fait pas plusieurs fois le même calcul
* closestIA joue controle??
*
*il faut debug le depth1;
* il faut etudier les Deck1 des bons et réflechir à comment bosser dessus;
* rajouter mes pdv dans le tableau score pour prendre en compte le drain;
*
*
*/







import java.util.Vector;
import java.util.*;
import java.io.*;
import java.math.*;
import java.util.concurrent.ThreadLocalRandom;

class Card1{

	private int idCard1;
	private int id;
	private int location;
	private int Card1Type;
	private int cost;
	private int attack;
	private int defense;
	private int oldDefense;
	private String abilities;
	private String oldAbilities;
	private int hpChange;
	private int hpChangeOpponent;
	private int Card1Draw;
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



	public Card1(int idCard1,int id,int location,int Card1Type,int cost,int attack,int defense,String abilities,int hpChange,int hpChangeOpponent,int Card1Draw,int placeDraft){
		this.idCard1 = idCard1;
		this.id = id;
		this.location = location;
		this.Card1Type = Card1Type;
		this.cost = cost;
		this.attack = attack;
		this.defense = defense;
		this.oldDefense = defense;
		this.abilities = abilities;
		this.oldAbilities = abilities;
		this.hpChange = hpChange;
		this.hpChangeOpponent = hpChangeOpponent;
		this.Card1Draw = Card1Draw;
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

	public Card1(int hp){
		this.idCard1 = -1;
		this.id = -1;
		this.location = 100;
		this.Card1Type = 100;
		this.cost = 1;
		this.attack = 0;
		this.defense = hp;
		this.oldDefense = hp;
		this.abilities = "------";
		this.oldAbilities = "------";
		this.hpChange = 0;
		this.hpChangeOpponent = 0;
		this.Card1Draw = 0;
		this.placeDraft = 0;
	}

	public Card1(Card1 c){
		this.idCard1 = c.idCard1;
		this.id = c.id;
		this.location = c.location;
		this.Card1Type = c.Card1Type;
		this.cost = c.cost;
		this.attack = -c.attack;
		this.defense = -c.defense;
		this.abilities = c.abilities;
		this.abilities = c.oldAbilities;
		this.hpChange = c.hpChange;
		this.hpChangeOpponent = c.hpChangeOpponent;
		this.Card1Draw = c.Card1Draw;
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

	public Card1(Card1 c,int a){
		this.idCard1 = c.idCard1;
		this.id = c.id;
		this.location = c.location;
		this.Card1Type = c.Card1Type;
		this.cost = c.cost;
		this.attack = c.attack;
		this.defense = c.defense;
		this.abilities = c.abilities;
		this.abilities = c.oldAbilities;
		this.hpChange = c.hpChange;
		this.hpChangeOpponent = c.hpChangeOpponent;
		this.Card1Draw = c.Card1Draw;
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


	public int score(){
		return this.getCost();
	}

	public void setUsed(boolean b){
		this.used = b;
	}

	public boolean getUsed(){
		return this.used;
	}

	public double scoreBis(int hp,int hpOpponent,int nbrCard1s,int nbrCard1sOpponent){
    

    double score;

		if (this.getCard1Type() == 2){
			this.attack = - this.attack;
			this.defense = - this.defense;
		}


		double score1x = (double)100*(this.cost+0.1)*((double)this.attack*2 + (double)this.defense);
		double score2x = (double)100*(this.cost+0.1)*((double)this.attack + 2*(double)this.defense);
		double score3x = (double)100*(this.cost+0.1)*((double)this.attack + (double)this.defense);
		double score1y = ((double)100*this.attack*2 + (double)this.defense)/(double)(this.cost+0.1);
		double score2y = ((double)100*this.attack + 2*(double)this.defense)/(double)(this.cost+0.1);
		double score3y = ((double)100*this.attack + (double)this.defense)/(double)(this.cost+0.1);
    if (this.guard){
      score1x += 10;
      score2x += 10;
      score3x += 10;
      score1y += 10;
      score2y += 10;
      score3y += 10;
    }
    if (this.ward){
      score1x += 10;
      score2x += 10;
      score3x += 10;
      score1y += 10;
      score2y += 10;
      score3y += 10;
    }
    if (this.lethal){
      score1x += 10;
      score2x += 10;
      score3x += 10;
      score1y += 10;
      score2y += 10;
      score3y += 10;
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
		if (nbrCard1s*2 <= nbrCard1sOpponent){
			if (hp > 20){
				if (hpOpponent > 20){
					if (this.guard){score3y += 100;}
					if (this.lethal){score3y += 100;}

					score = score3y;
				} else if(hpOpponent > 10){
					if (this.ward){score1y += 100;}
					if (this.lethal){score1y += 100;}
					score = score1y;
				} else {
					if (this.charge){score1y += 100;}
					if (this.lethal){score1y += 25;}
					if (this.breaktrough){score1y += 50;}
					score = score1y;
				}
		}	else if(hp > 10){
				if (hpOpponent > 20){
					if (this.guard){score2y += 200;}
					if (this.drain){score2y += 50;}
					if (this.ward){score2y += 50;}
					score = score2y;
				} else if(hpOpponent > 10){
					if (this.guard){score3y += 200;}
					if (this.drain){score3y += 50;}
					if (this.ward){score3y += 50;}
					score = score3y;
				} else {
					if (this.charge){score1y += 100;}
					if (this.lethal){score1y += 25;}
					if (this.breaktrough){score1y += 50;}
					score = score1y;
				}
			}else {

				if (hpOpponent > 20){
					if (this.guard){score2y += 200;}
					if (this.drain){score2y += 50;}
					if (this.ward){score2y += 50;}
					score = score2y;
				} else if(hpOpponent > 10){
					if (this.guard){score2y += 200;}
					if (this.drain){score2y += 50;}
					if (this.ward){score2y += 50;}
					score = score2y;
				} else {
					if (this.charge){score3y += 100;}
					if (this.guard){score3y += 200;}
					score = score3y;
				}

			}
		} else {
			if (hp > 20){
				if (hpOpponent > 20){
					if (this.guard){score3x += 200;}
					if (this.lethal){score3x += 100;}
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
					if (this.guard){score2x += 100;}
					if (this.drain){score2x += 50;}
					if (this.ward){score2x += 50;}
					score = score2x;
				} else if(hpOpponent > 10){
					if (this.guard){score2x += 100;}
					if (this.drain){score2x += 50;}
					if (this.ward){score2x += 50;}
					score = score2x;
				} else {
					if (this.charge){score3x += 100;}
					if (this.guard){score3x += 100;}
					score = score3x;
				}

			}
		}

		if (this.getCard1Type() == 2){
			if (this.getCard1Type() == 2){
				this.attack = - this.attack;
				this.defense = - this.defense;
			}
			return Math.sqrt(-score);
		} else {return score;}
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

	public String printATTACK(Card1 c){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+Integer.toString(c.getId())+";";
		//System.out.println(answer);
		return answer;
	}

  public String printATTACKBIS(Card1 c){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+Integer.toString(c.getId())+";";
		//System.out.println(answer);
		return answer;
	}

	public String printATTACK(){
		String answer = "ATTACK"+" "+Integer.toString(this.getId())+" "+"-1"+";";
		//System.out.println(answer);
		return answer;
	}

	public String printUSE(Card1 c){
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


    public int getPlace(){
		return this.placeDraft;
	}

	public int getLocation(){
		return this.location;
	}

	public int getCard1Type(){
		return this.Card1Type;
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

	public void counterAttack(Card1 c,Joueur1 me,Joueur1 other){
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

	public void deCounterAttack(Card1 c,Joueur1 me,Joueur1 other){
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


	public void getAttackBy(Card1 c,Joueur1 me,Joueur1 other,Card1 opponent,Card1 mine){

		



		if (this.getWard()){
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

	public void getDeattackBy(Card1 c,Joueur1 me,Joueur1 other,Card1 opponent,Card1 mine){

		if (this.getWasWard()){
			this.ward = true;
			this.wasWard = false;
			this.abilities = this.oldAbilities;
      		this.defense = this.oldDefense;
		}
		else{

			this.defense = this.oldDefense;
			this.abilities = this.oldAbilities;

			}


}

	public void getBuffByGreenCard1(Card1 c){
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

	public void getDebuffByGreenCard1(Card1 c){
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

	public void getBuffByGreenCard1Bis(Card1 c){
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

	public void getBuffByRedCard1(Card1 c){
		this.defense += c.getDefense();
		this.attack += c.getAttack();
		if (c.getGuard()){
			if (this.guard){this.buffGuard = true;}
			this.guard = false;
		}
		if (c.getWard()){
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

	public void getDebuffByRedCard1(Card1 c){
		this.defense -= c.getDefense();
		this.attack -= c.getAttack();
		if (c.getGuard()){
			if (this.buffGuard){this.guard = true;}
			this.buffGuard = false;
		}
		if (c.getWard()){
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



	public void getAttackByBlueCard1(Card1 c){
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


class Hand1{

	private int amountOfCard1;
	private Vector<Card1> Hand1;
	private int amountOfItem;

	public Hand1(Vector<Card1> h ) {
		this.Hand1 = h;
		this.amountOfCard1 = h.size();
		this.amountOfItem = 0;
		for (int i = 0;i<h.size();i++) {
			if (h.elementAt(i).getCard1Type() >0){this.amountOfItem++;}
		}
	}

	public int getItems(){
		return this.amountOfItem;
	}

	public Vector<Vector<Card1>> allPosibilitesWithoutItem(int mana,int nbrCarte,Joueur1 me,Joueur1 other) {



		Vector<Card1> h = this.Hand1;
		Vector<Card1> possible = new Vector<Card1>();
		for (int i = 0;i<h.size();i++) {
			


			if (h.elementAt(i).getCost() <= mana) {
			    possible.addElement(h.elementAt(i));
			    //System.err.print("Cartes possibles :");
			    //System.err.print(h.elementAt(i).getId());
			    //System.err.println();
			    }
		}

    Vector<Card1> possible2 = new Vector<Card1>();
		for (int i = h.size()-1;i>=0;i--) {
			if (h.elementAt(i).getCost() <= mana) {
			    possible2.addElement(h.elementAt(i));
			    //System.err.print("Cartes possibles :");
			    //System.err.print(h.elementAt(i).getId());
			    //System.err.println();
			    }
		}

		Vector<Vector<Card1>> renvoie = new Vector<Vector<Card1>>();
		int n = possible.size();
		for (int i = 0;i<Math.pow(2,n);i++){
			int compteur = nbrCarte;
			int m = 0;
			Vector<Card1> answer = new Vector<Card1>();
			
			for (int j = 0;j<n;j++){
				if ((int)(i/(int)(Math.pow(2,n-1-j)))%2 == 0){
					if (possible.elementAt(j).getCard1Type() == 0|| possible.elementAt(j).getCard1Type() == 3 || (possible.elementAt(j).getCard1Type() == 1 && compteur>0) || (possible.elementAt(j).getCard1Type() == 2 && other.getBoard().size()>1)){
						answer.addElement(possible.elementAt(j));
						m += possible.elementAt(j).getCost();
						if (possible.elementAt(j).getCard1Type() == 0){compteur++;}
					}
				}
			}
			if (compteur <= 6 && m <= mana){renvoie.addElement(answer);}
		}

    for (int i = 0;i<Math.pow(2,n);i++){
			int compteur = nbrCarte;
			int m = 0;
			Vector<Card1> answer = new Vector<Card1>();
			
			for (int j = 0;j<n;j++){
				if ((int)(i/(int)(Math.pow(2,n-1-j)))%2 == 0){
					if (possible2.elementAt(j).getCard1Type() == 0|| possible2.elementAt(j).getCard1Type() == 3 || (possible2.elementAt(j).getCard1Type() == 1 && compteur>0) || (possible2.elementAt(j).getCard1Type() == 2 && other.getBoard().size()>1)){
						answer.addElement(possible2.elementAt(j));
						m += possible2.elementAt(j).getCost();
						if (possible2.elementAt(j).getCard1Type() == 0){compteur++;}
					}
				}
			}
			if (compteur <= 6 && m <= mana){renvoie.addElement(answer);}
		}
		return renvoie;
	}
}

class Deck1{

	int amountOfCard1;
	Vector<Card1> Deck1;
	int[] manaAmount;
	int amountOfItem;


	public Deck1(){
		this.amountOfCard1 = 0;
		this.Deck1 = new Vector<Card1>();
		this.manaAmount = new int[13];
		for (int i = 0;i<13;i++){
			manaAmount[i] = 0;
		}
		this.amountOfItem = 0;



	}

	public int getAmountOfCard1(){
		return this.amountOfCard1;
	}

	public Vector<Card1> getDeck1(){
		return this.Deck1;
	}

	public int[] getManaAmount(){
		return this.manaAmount;
	}

	public int getAmountOfItem(){
		return this.amountOfItem;
	}

	public void incrCard1(Card1 c){
		this.amountOfCard1++;
		this.Deck1.addElement(c);
		if (c.getCard1Type() > 0){this.amountOfItem++;}
		else{
			this.manaAmount[c.getCost()]++;
		}

	}

	public void decrCard1(Card1 c){
		this.amountOfCard1--;
		this.Deck1.removeElementAt(this.Deck1.size()-1);
		if (c.getCard1Type() > 0){this.amountOfItem--;}
		else{
			this.manaAmount[c.getCost()]--;
		}

	}

	public int scoreDeck1(){
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


		score *= this.amountOfCard1;

		Card1 c = this.Deck1.elementAt(this.Deck1.size()-1);
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


class Solution1{
	int taille;
	int tailleOpponent;
	Vector<Vector<Integer>> genome;

	public Solution1(int taille, int tailleOpponent){
		this.taille = taille;
		this.tailleOpponent = tailleOpponent;
		this.genome = new Vector<Vector<Integer>>();
	}

	public void afficher(Joueur1 me, Joueur1 opponent){
		System.err.println("La Solution1 consiste en :");
		for (int i = 0;i<this.genome.size();i++){
			Vector<Integer> att = this.genome.elementAt(i);
			Card1 attacker = me.getBoard().elementAt(att.elementAt(0));
			Card1 defender = opponent.getBoard().elementAt(att.elementAt(1));
			System.err.print("ATTACK "+attacker.getId() + " " + defender.getId()+ "; ");
			System.err.println("");
		}
	}

	public int getTaille(){
		return this.taille;
	}

	public int getTailleOpponent(){
		return this.tailleOpponent;
	}

	public Vector<Vector<Integer>> getGenome(){
		return this.genome;
	}

	public void setGenome(Vector<Vector<Integer>> genome){
		this.genome = genome;
	}

	public void incrGenome(Vector<Integer> gene){
		this.genome.addElement(gene);
	}

	public void decrGenome(){
		this.genome.removeElementAt(this.genome.size()-1);
	}

	public Boolean valideSolution1(Joueur1 me, Joueur1 opponent,Card1 other,Card1 mine){
		Vector<Vector<Card1>> transcript = new Vector<Vector<Card1>>();
		for (int i = 0;i<this.genome.size();i++){
			Vector<Card1> att = new Vector<Card1>();
			att.addElement(me.getBoard().elementAt(this.genome.elementAt(i).elementAt(0)));
			att.addElement(opponent.getBoard().elementAt(this.genome.elementAt(i).elementAt(1)));
			transcript.addElement(att);
		}

		return me.validAnswerBis(transcript,opponent,other,mine);
	}
// Il faut peut être ne prendre qu'une permutation sur deux;
	public Vector<Solution1> permutationSolution1(Joueur1 me, Joueur1 other){
		int n = this.genome.size();
		Vector<Vector<Integer>> indice = me.indicePermutation(n);
		Vector<Solution1> population = new Vector<Solution1>();

		for (int i = 0;i<indice.size();i+=2){
			Solution1 sol = new Solution1(n,other.getBoard().size());
			for (int j = 0;j<n;j++){
				Vector<Integer> s = new Vector<Integer>();
				s.addElement(indice.elementAt(i).elementAt(j));
				s.addElement(this.genome.elementAt(j).elementAt(1));
				sol.incrGenome(s);
			}
			population.addElement(sol);
		}

		return population;

	}



}




class Joueur1{

	private int hp;
	private int mana;
	private int remainingCard1sInDeck1;
	private int rune;
	private Vector<Card1> board;
	private Vector<Card1> Hand1;


	public Joueur1(int hp,int mana,int rcid,int rune,Vector<Card1> Hand1, Vector<Card1> board){
		this.hp = hp;
		this.mana = mana;
		this.remainingCard1sInDeck1 = rcid;
		this.rune = rune;
		this.board = board;
		this.Hand1 = Hand1;
	}

  public boolean validAnswerBis(Vector<Vector<Card1>> a,Joueur1 opponent,Card1 other,Card1 mine){
		boolean answer = true;
		Vector<Card1> opponentBoard = new Vector<Card1>();
		Vector <Card1> b = opponent.getBoard();
		Vector<Card1> gInBoard = opponent.getGuardsInBoard();
		//for (int i = 0;i<b.size();i++){opponentBoard.addElement(b.elementAt(i));}
		for (int i = 0;i<a.size();i++){
			Card1 attacker = a.elementAt(i).elementAt(0);
			Card1 defender = a.elementAt(i).elementAt(1);
			int n = gInBoard.size();

			if (n > 0){
				if (defender.getGuard() == false){
					answer = false;
					defender.getAttackBy(attacker,this,opponent,other,mine);
				}
				else {
					/*System.err.println("defense avant attaque ="+defender.getDefense());
					defender.getAttackBy(attacker,this,opponent);
					System.err.println("defense après attaque ="+defender.getDefense());
					defender.getDeattackBy(attacker,this,opponent);
					System.err.println("defense après Deattaque ="+defender.getDefense());
					defender.getAttackBy(attacker,this,opponent);
					*/
          if (defender.getDefense()<=0){answer = false;}
					defender.getAttackBy(attacker,this,opponent,other,mine);
					if (defender.getDefense() <= 0){
						gInBoard.removeElement(defender);
					}
				}
			} else {
				if (defender.getGuard()){answer = false;}
				else if (defender.getDefense() <= 0 && defender.getId() > -1){answer = false;}

				defender.getAttackBy(attacker,this,opponent,other,mine);
			}
		}
		for (int i = a.size()-1 ;i>=0;i--){
			Card1 attacker = a.elementAt(i).elementAt(0);
			Card1 defender = a.elementAt(i).elementAt(1);
			defender.getDeattackBy(attacker,this,opponent,other,mine);
		}
		return answer;
	}



  public void setHand1(Vector<Card1> Hand1){
		this.Hand1 = Hand1;
	}


	public void setBoard(Vector<Card1> board){
		this.Hand1 = board;
	}



	public int getHp(){
		return this.hp;
	}

	public int getRemainingCard1sInDeck1(){
		return this.remainingCard1sInDeck1;
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

	public Vector<Card1> getBoard(){
		return this.board;
	}

	public Vector<Card1> getHand1(){
		return this.Hand1;
	}

	public void getAttackByCard1(Card1 c){
		this.hp -= c.getAttack();
	}

	public void getBuffByBlueCard1(Card1 c){
		this.hp += c.getHpChange();
	}

	public void getAttackByBlueCard1(Card1 c){
		this.hp += c.getHpChangeOpponent();
	}

	public double attackAverage(){
		double avg = 0.0;
		Vector<Card1> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			avg += (double) b.elementAt(i).getAttack();
		}
		return (double) (avg)/((double)b.size());
	}

	public double bestDefense(){
		double best = 0.0;
		Vector<Card1> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if ((double)b.elementAt(i).getDefense() > best){
				best = b.elementAt(i).getDefense();
			}
		}
		return (double) best;
	}

	public Vector<Card1> getGuardsInBoard(){
		Vector<Card1> g = new Vector<Card1>();
		Vector<Card1> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getGuard()){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card1> getWardsInBoard(){
		Vector<Card1> g = new Vector<Card1>();
		Vector<Card1> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getWard() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card1> getDrainsInBoard(){
		Vector<Card1> g = new Vector<Card1>();
		Vector<Card1> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getDrain() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card1> getChargesInBoard(){
		Vector<Card1> g = new Vector<Card1>();
		Vector<Card1> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getCharge() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card1> getBreaktroughsInBoard(){
		Vector<Card1> g = new Vector<Card1>();
		Vector<Card1> b = this.getBoard();
		for (int i = 0;i<b.size();i++){
			if (b.elementAt(i).getBreaktrough() && b.elementAt(i).getDefense() > 0){
				g.addElement(b.elementAt(i));
			}
		}
		return g;
	}

	public Vector<Card1> getLethalsInBoard(){
		Vector<Card1> g = new Vector<Card1>();
		Vector<Card1> b = this.getBoard();
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
      	Vector<Integer> Solution1 = new Vector<Integer>();
      	for (int j = 0;j<arr.length;j++) {
      		Solution1.addElement(arr[j]);
      	}
      	perm.addElement(Solution1);

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

  public Vector<Vector<Integer>> produitCartesienBis(Vector<Integer> board,Vector<Integer> opponentBoard){

    Vector<Vector<Integer>> renvoie = new Vector<Vector<Integer>>();
    Vector<Integer> b = board;
    for (int i = 0;i<b.size();i++){
      for (int j = 0;j<opponentBoard.size();j++){
        Vector<Integer>  n = new Vector<Integer>();
        n.addElement(i);
        n.addElement(j);
        renvoie.addElement(n);
      }
    }

    return renvoie;

  }

  public Vector<Vector<Vector<Integer>>> subVector(Vector<Vector<Integer>> produitCart, int p){
    Vector<Vector<Vector<Integer>>> renvoie = new Vector<Vector<Vector<Integer>>>();
    int n = produitCart.size();
    long endTime = System.nanoTime();


    //System.err.println("n vaut :  " +n);
    //System.err.println("donc 2**n vaut :  " +Math.pow(2,n));

    for (int i = 0;i<Math.pow(2,n);i++){
      endTime = System.nanoTime();
      //if ((endTime - startTime)/1000000 > 70){return renvoie;}
      Vector<Vector<Integer>> reponse = new Vector<Vector<Integer>>();
      for (int j = 0;j<n;j++){
        endTime = System.nanoTime();
        //if ((endTime - startTime)/1000000 > 70){return renvoie;}
        if ((int)(i/(int)(Math.pow(2,n-1-j)))%2 == 0){
          reponse.addElement(produitCart.elementAt(j));
        }
      }
      if (reponse.size() <= p){
        renvoie.addElement(reponse);
      }
    }
    return renvoie;
  }


  public Vector<Vector<Vector<Integer>>> calculAllPossibilites(Vector<Integer> me,Vector<Integer> opponent){
  	long startTime2 = System.nanoTime();
  	int p = this.getBoard().size();
  	Vector<Vector<Vector<Integer>>> renvoie = new Vector<Vector<Vector<Integer>>>();
  	Vector<Vector<Integer>> permutation = indicePermutation(me.size());
  	//Vector<Vector<Card1>> permutationOther = permutationVector(opponent.getBoard());
  	long endTime = System.nanoTime();
  	//System.err.println("les 2 permutations se calculent en " + (endTime - startTime2) + " ns");
  	//System.err.println("c'est-à-dire en " + (endTime - startTime2)/1000000 + " ms");

  	for (int i = 0;i<permutation.size();i++){
  		/*for (int k = 0;k<permutationOther.size();k++){
  			Vector<Vector<Card1>> solu = produitCartesienBis(permutation.elementAt(i),permutationOther.elementAt(k));
  			if (this.validAnswerBis(solu,opponent)){renvoie.addElement(solu);}

  		}*/
  		Vector<Integer> per = permutation.elementAt(i);
  		Vector<Vector<Vector<Integer>>> test = subVector(this.produitCartesienBis(per,opponent),p);

  		for (int j = 0;j<test.size();j++){
  			renvoie.addElement(test.elementAt(j));
  		}
    }
  	//}

  	return renvoie;
  }


}

class Turn1{

	private int tour;
	private Joueur1 me;
	private Joueur1 other;


	public Turn1(int tour,Joueur1 me, Joueur1 other){
		this.tour = tour;
		this.me = me;
		this.other = other;
	}


  public void incrTurn1(){
		this.tour++;
	}

	public void setTurn1(int Turn1){
		this.tour = Turn1;
	}

	public int getTurn1(){
		return this.tour;
	}

	public Joueur1 getMe(){
		return this.me;
	}

	public Joueur1 getOther(){
		return this.other;
	}

	public void afficher(){
		System.err.println("Le tour est constitué de ");
		Vector<Card1> myBoard = this.me.getBoard();
		Vector<Card1> hisBoard = this.other.getBoard();

		System.err.println("Mon board ");
		for (int i = 0;i<myBoard.size();i++){
			Card1 c = myBoard.elementAt(i);
			System.err.println("Carte "+c.getId()+" d'attaque "+c.getAttack()+" de defense "+c.getDefense()+" d'abilities "+c.getAbilities());
		}


		System.err.println("Son board ");
		for (int i = 0;i<hisBoard.size();i++){
			Card1 c = hisBoard.elementAt(i);
			System.err.println("Carte "+c.getId()+" d'attaque "+c.getAttack()+" de defense "+c.getDefense()+" d'abilities "+c.getAbilities());
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
      	Vector<Integer> Solution1 = new Vector<Integer>();
      	for (int j = 0;j<arr.length;j++) {
      		Solution1.addElement(arr[j]);
      	}
      	perm.addElement(Solution1);

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

  public Vector<Vector<Integer>> produitCartesienBis(Vector<Integer> board,Vector<Integer> opponentBoard){
    //long startTime = System.nanoTime();
    Vector<Vector<Integer>> renvoie = new Vector<Vector<Integer>>();
    Vector<Integer> b = board;
    for (int i = 0;i<b.size();i++){
      for (int j = 0;j<opponentBoard.size();j++){
        Vector<Integer>  n = new Vector<Integer>();
        n.addElement(i);
        n.addElement(j);
        renvoie.addElement(n);
      }
    }
    //long endTime = System.nanoTime();
    //System.err.println("le produitCartesien se calculent en " + (endTime - startTime) + " ns");
    //System.err.println("c'est-à-dire en " + (endTime - startTime)/1000000 + " ms");
    return renvoie;

  }

  public Vector<Vector<Vector<Integer>>> subVector(Vector<Vector<Integer>> produitCart, int p){
    Vector<Vector<Vector<Integer>>> renvoie = new Vector<Vector<Vector<Integer>>>();
    int n = produitCart.size();
    //long endTime = System.nanoTime();
    long startTime = System.nanoTime();

    //System.err.println("n vaut :  " +n);
    //System.err.println("donc 2**n vaut :  " +Math.pow(2,n));

    for (int i = 0;i<Math.pow(2,n);i++){

      //if ((endTime - startTime)/1000000 > 70){return renvoie;}
      Vector<Vector<Integer>> reponse = new Vector<Vector<Integer>>();
      /*Vector<Integer> toBeDone = new Vector<Integer>();
      for (int k = 0;k<p;k++){
        toBeDone.addElement(k);
      }
      */



      for (int j = 0;j<n;j++){

        //if ((endTime - startTime)/1000000 > 70){return renvoie;}
        if ((int)(i/(int)(Math.pow(2,n-1-j)))%2 == 0){
          reponse.addElement(produitCart.elementAt(j));
          //if (toBeDone.contains(produitCart.elementAt(j).elementAt(0)) == false){test = false;}
          //toBeDone.removeElement(produitCart.elementAt(j).elementAt(0));
          /*if (reponse.size() > p){test = false;}

        }*/

        }
      }
        if (reponse.size() == p && this.validAnswer(reponse) == true){renvoie.addElement(reponse);}



    }

    long endTime = System.nanoTime();
    System.err.println("le subVector se calculent en " + (endTime - startTime) + " ns");
    System.err.println("c'est-à-dire en " + (endTime - startTime)/1000000 + " ms");
return renvoie;
}

public boolean validAnswer(Vector<Vector<Integer>> a){
		int n = a.size();
		Vector<Integer> done = new Vector<Integer>();
		for (int i = 0;i<n;i++){
			if (done.indexOf(a.elementAt(i).elementAt(0)) > -1){
				return false;
			} else {
				done.addElement(a.elementAt(i).elementAt(0));
			}
		}
		return true;
	}

  public Vector<Vector<Vector<Integer>>> calculAllPossibilites(Vector<Integer> me,Vector<Integer> opponent){
  	int p = me.size();
  	Vector<Vector<Vector<Integer>>> renvoie = new Vector<Vector<Vector<Integer>>>();
  	Vector<Vector<Integer>> permutation = indicePermutation(me.size());
  	//Vector<Vector<Integer>> permutationOther = permutationVector(opponent.getBoard());


  	for (int i = 0;i<permutation.size();i++){
  		/*for (int k = 0;k<permutationOther.size();k++){
  			Vector<Vector<Integer>> solu = produitCartesienBis(permutation.elementAt(i),permutationOther.elementAt(k));
  			if (this.validAnswerBis(solu,opponent)){renvoie.addElement(solu);}

  		}*/
  		Vector<Integer> per = permutation.elementAt(i);

  		Vector<Vector<Vector<Integer>>> test = subVector(this.produitCartesienBis(per,opponent),p);


  		for (int j = 0;j<test.size();j++){
  			renvoie.addElement(test.elementAt(j));
  		}
    }
  	//}

  	return renvoie;
  }









  public double getMyTempo(){
		if (this.other.getBoard().elementAt(0).getDefense() <= 0){return (double)100000.0;}
		Vector<Card1> g = this.other.getGuardsInBoard();
		double lifeOpponent = 0;//(double)this.other.getHp();
		for (int i = 0;i<this.other.getBoard().size();i++){
			if (this.other.getBoard().elementAt(i).getId() == -1){lifeOpponent = (double)this.other.getBoard().elementAt(i).getDefense();}
		}

		double score;
		for (int i = 0;i<g.size();i++){
			lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());
		}
		for (int i = 1;i<this.other.getBoard().size();i++){
			lifeOpponent += (double) Math.max(0,this.other.getBoard().elementAt(i).getDefense());
		}
		Vector<Card1> b = this.me.getBoard();
		double myAttack = (double)0;
		for (int i = 0;i<b.size();i++){
			myAttack += (double) Math.max(0,b.elementAt(i).getAttack());
		}
		double bestDefense = this.other.bestDefense();
		double avgAttack = this.me.attackAverage();
		Vector<Card1> w = this.other.getWardsInBoard();
		Vector<Card1> l = this.me.getLethalsInBoard();

		lifeOpponent += (double)w.size()*avgAttack;
		lifeOpponent -= (double)l.size()*(bestDefense+5);
		Vector<Card1> c = this.me.getChargesInBoard();
		for (int i = 0;i<c.size();i++){
			lifeOpponent -= (double) c.elementAt(i).getAttack();
		}
		Vector<Card1> b1 = this.me.getBreaktroughsInBoard();
		myAttack += (double) b1.size();
		Vector<Card1> d = this.me.getDrainsInBoard();
		double tempTempo = (double)(lifeOpponent+0.1)/(myAttack+0.1);
		for (int i = 0;i<d.size();i++){
			myAttack += (tempTempo/(double)2.0) * (double)d.elementAt(i).getAttack();
		}

		score = (double)(lifeOpponent+0.1)/(myAttack+0.1);
		return (double) (2.0)/(score);
	}

	public double getHisTempo(){
		if (this.me.getHp() <= 0){return (double)100000.0;}
		Vector<Card1> g = this.me.getGuardsInBoard();
		double lifeOpponent = (double)this.me.getHp();
		double score;
		for (int i = 0;i<g.size();i++){
			lifeOpponent += (double) Math.max(0,g.elementAt(i).getDefense());
		}
		for (int i = 1;i<this.me.getBoard().size();i++){
			lifeOpponent += (double) Math.max(0,this.me.getBoard().elementAt(i).getDefense());
		}
		Vector<Card1> b = this.other.getBoard();
		double myAttack = (double)0;
		for (int i = 0;i<b.size();i++){
			myAttack += (double) Math.max(0,b.elementAt(i).getAttack());
		}
		double bestDefense = this.me.bestDefense();
		double avgAttack = this.other.attackAverage();
		Vector<Card1> w = this.me.getWardsInBoard();
		Vector<Card1> l = this.other.getLethalsInBoard();

		lifeOpponent += (double)w.size()*avgAttack;
		lifeOpponent -= (double)l.size()*(bestDefense+5);
		Vector<Card1> c = this.other.getChargesInBoard();
		for (int i = 0;i<c.size();i++){
			lifeOpponent -= (double) c.elementAt(i).getAttack();
		}
		Vector<Card1> b1 = this.other.getBreaktroughsInBoard();
		myAttack += (double) b1.size();
		Vector<Card1> d = this.other.getDrainsInBoard();
		double tempTempo = (double)(lifeOpponent+0.1)/(myAttack+0.1);
		for (int i = 0;i<d.size();i++){
			myAttack += (tempTempo/(double)2.0) * (double)d.elementAt(i).getAttack();
		}

		score = (double)(lifeOpponent+0.1)/(myAttack+0.1);
		return (double) (2.0)/(score);
	}


	public double getMyBoardScore(Card1 opponent,Card1 mine){
	    
		int hp = mine.getDefense();
		int hpOpponent = opponent.getDefense();
		if (hpOpponent <= 0){return (double)100000.0;}

		int nbrCard1s = this.me.getBoard().size();
		int nbrCard1sOpponent = this.other.getBoard().size();
		double score = 0;
    //System.err.println("nbr mes Card1s : "+nbrCard1s);
		for (int i = 0;i<nbrCard1s;i++){
      if (this.me.getBoard().elementAt(i).getAttack() == 0 && this.me.getBoard().elementAt(i).getGuard() == false){
				score -= 300;
			}
			score += this.me.getBoard().elementAt(i).scoreBis(hp,hpOpponent,nbrCard1s,nbrCard1sOpponent);
		}

		return score;
	}

  public double getMyBoardScoreDummie(Card1 opponent,Card1 mine){
	   if (opponent.getDefense() <= 0){return (double)100000.0;}

		int hp = this.me.getHp();
		int hpOpponent = this.other.getHp();
		int nbrCard1s = this.me.getBoard().size();
		int nbrCard1sOpponent = this.other.getBoard().size();
		double score = mine.getDefense() - opponent.getDefense();
    //System.err.println("nbr mes Card1s : "+nbrCard1s);
		for (int i = 0;i<nbrCard1s;i++){
      if (this.me.getBoard().elementAt(i).getAttack() == 0 && this.me.getBoard().elementAt(i).getId() > -1 && this.me.getBoard().elementAt(i).getGuard() == false){
				score -= 300;
			}
		}
    for (int i = 0;i<this.me.getBoard().size();i++){
      if (this.me.getBoard().elementAt(i).getDefense()>0){score +=(double)this.me.getBoard().elementAt(i).getDefense()/10.0;}
      if (this.me.getBoard().elementAt(i).getDefense()>0 && this.me.getBoard().elementAt(i).getGuard()){score +=(double)this.me.getBoard().elementAt(i).getDefense()/10.0;}
//(double)this.me.getBoard().elementAt(i).getDefense();}
    }



		return score;
	}

  public double getHisBoardScoreDummie(){
	    if (this.me.getHp() <= 0){return (double)100000.0;}
		int hp = this.me.getHp();
		int hpOpponent = this.other.getHp();
		int nbrCard1s = this.me.getBoard().size();
		int nbrCard1sOpponent = this.other.getBoard().size();
		double score = 0;
    //System.err.println("nbr ses Card1s : "+nbrCard1sOpponent);
    for (int i = 1;i<nbrCard1sOpponent;i++){
      if (this.other.getBoard().elementAt(i).getAttack() == 0 && this.other.getBoard().elementAt(i).getId() > -1 && this.other.getBoard().elementAt(i).getGuard() == false){
				score -= 300;
			}
    }

    for (int i = 0;i<this.other.getBoard().size();i++){
      if (this.other.getBoard().elementAt(i).getDefense()>0){score +=(double)this.other.getBoard().elementAt(i).getDefense()/10.0;}
      if (this.other.getBoard().elementAt(i).getDefense()>0 && this.other.getBoard().elementAt(i).getGuard()){score +=(double)this.other.getBoard().elementAt(i).getDefense()/10.0;}
      // (double)this.other.getBoard().elementAt(i).getDefense();}
    }

		return score;
	}

	public double getHisBoardScore(Card1 opponent,Card1 mine){
	    
		int hp = mine.getDefense();
		if (hp <= 0){return (double)100000.0;}
		int hpOpponent = opponent.getDefense();
		int nbrCard1s = this.me.getBoard().size();
		int nbrCard1sOpponent = this.other.getBoard().size();
		double score = 0;
    //System.err.println("nbr ses Card1s : "+nbrCard1sOpponent);

		for (int i = 1;i<nbrCard1sOpponent;i++){
      if (this.other.getBoard().elementAt(i).getAttack() == 0 && this.other.getBoard().elementAt(i).getGuard() == false){
				score -= 300;
			}
			score += this.other.getBoard().elementAt(i).scoreBis(hpOpponent,hp,nbrCard1sOpponent,nbrCard1s);
		}
		
		return score;
	}

  public double getTurn1ScoreDummie(Card1 opponent,Card1 mine){
		double myScore = this.getMyBoardScoreDummie(opponent,mine);//this.getMyTempo(); //+ this.getMyBoardScore();
		double hisScore =this.getHisBoardScoreDummie();//this.getHisTempo(); //+ this.getHisBoardScore();
		return myScore - hisScore;
	}



	public double getTurn1ScoreSummon(Card1 opponent,Card1 mine){
		double myScore = this.getMyBoardScore(opponent,mine);
		double hisScore = this.getHisBoardScore(opponent,mine);
		return myScore - hisScore;
	}

  public double getTurn1Score(Card1 opponent,Card1 mine){
		double myScore =  this.getMyBoardScore(opponent,mine);
		double hisScore = this.getHisBoardScore(opponent,mine);
		return myScore - hisScore;
	}

  public Card1 bestUseOfGreenCard1(Card1 greenItem,Card1 opponent,Card1 mine){
		Vector<Card1> b = this.me.getBoard();
		int bestAnswer = 0;
		double bestScore = -10000.0;

		for (int i = 0;i<b.size();i++){

			b.elementAt(i).getBuffByGreenCard1(greenItem);
			double tempScore = this.getTurn1Score(opponent,mine);
			if (tempScore >= bestScore){
				bestScore = tempScore;
				bestAnswer =i;
			}

			b.elementAt(i).getDebuffByGreenCard1(greenItem);

		}

		return b.elementAt(bestAnswer);

	}

	public Card1 bestUseOfRedCard1(Card1 redItem,Card1 opponent,Card1 mine){
		Vector<Card1> b = this.other.getBoard();
		Card1 him = b.elementAt(0);
		b.removeElementAt(0);
		int bestAnswer = 0;
		double bestScore = -10000.0;

		for (int i = 0;i<b.size();i++){

			b.elementAt(i).getBuffByRedCard1(redItem);
			double tempScore = this.getTurn1Score(opponent,mine);
			if (tempScore >= bestScore){
				bestScore = tempScore;
				bestAnswer =i;
			}

			b.elementAt(i).getDebuffByRedCard1(redItem);

		}

		Card1 answer =  b.elementAt(bestAnswer);
		b.add(0,him);
		return answer;

	}

  public int calculBestSummon(Vector<Vector<Card1>> possible,Card1 opponent,Card1 mine){
		double bestScore = -10000.0;
		int bestAnswer = 0;




		if (possible.size()>0){
		System.err.println("");
		for (int i = 0;i<possible.size();i++){
			double score = 0;
			Vector<Card1> summoning = possible.elementAt(i);

			for (int j = 0;j<summoning.size();j++){

				Card1 c = summoning.elementAt(j);
				System.err.print("SUMMON "+c.getId());
				if (c.getCard1Type() < 2){this.me.getBoard().addElement(c);}
				else if (c.getCard1Type() == 2){
					Card1 reverseC = new Card1(c);
					this.me.getBoard().addElement(reverseC);
				} else {
					Card1 reverseC = new Card1(c);
					this.me.getBoard().addElement(reverseC);
					this.me.setHp(this.me.getHp()+reverseC.getHpChange());
					this.other.setHp(this.me.getHp()+reverseC.getHpChangeOpponent());
				}

			}
			score = this.getTurn1ScoreSummon(opponent,mine);
			System.err.println("de score :"+score);

			if (score>bestScore){
				bestScore = score;
				bestAnswer = i;
			}
			//System.err.print("mon tempo ");
		 //System.err.print(this.getMyTempo());
			//System.err.println();

			for (int j = 0;j<summoning.size();j++){
				Card1 c = summoning.elementAt(j);
				if (c.getCard1Type() < 2){this.me.getBoard().removeElement(c);}
				else if (c.getCard1Type() == 2){
					Card1 reverseC = new Card1(c);
					this.me.getBoard().removeElement(reverseC);
				} else {
					Card1 reverseC = new Card1(c);
					this.me.getBoard().removeElement(reverseC);
					this.me.setHp(this.me.getHp()-reverseC.getHpChange());
					this.other.setHp(this.me.getHp()-reverseC.getHpChangeOpponent());
				}

			}
		}
	}
	return bestAnswer;
	}

  public Vector<Vector<Card1>> generateRandomAttack(int compteur,long startTime){


	int n = this.me.getBoard().size();
	Vector<Integer> indice = new Vector<Integer>();
		for (int i = 0;i<n;i++){
			indice.addElement(i);
		}
	Vector<Vector<Card1>> renvoie = new Vector<Vector<Card1>>();
	while (indice.size() > 0){
		int randomNum = ThreadLocalRandom.current().nextInt(0, indice.size());

		int indiceAttack = indice.elementAt(randomNum);
		int indiceDef = (int)ThreadLocalRandom.current().nextInt(0, this.other.getBoard().size());


		Vector<Card1> att = new Vector<Card1>();
		att.addElement(this.me.getBoard().elementAt(indiceAttack));
		att.addElement(this.other.getBoard().elementAt(indiceDef));
		renvoie.addElement(att);
		indice.removeElement(indiceAttack);
	}
	/*
	System.err.println("la Solution1 consiste en :");
	for (int i = 0;i<renvoie.size();i++){
		System.err.println("ATTACK "+renvoie.elementAt(i).elementAt(0).getId()+ " "+renvoie.elementAt(i).elementAt(1).getId()+"; ");
	}
	System.err.println("est elle valide ? "+this.me.validAnswerBis(renvoie,this.other));
	*/
	return renvoie;



}

public double scoreSolution1(Solution1 Solution1,Card1 opponent,Card1 mine){
		double score;
		for (int i = 0;i<Solution1.genome.size();i++){
		    //System.err.println(t);
			Card1 attacker = this.other.getBoard().elementAt(Solution1.genome.elementAt(i).elementAt(0));
			Card1 defender = this.me.getBoard().elementAt(Solution1.genome.elementAt(i).elementAt(1));
			defender.getAttackBy(attacker,this.other,this.me,mine,opponent);
			defender.counterAttack(attacker,this.other,this.me);
		}
		score = this.getTurn1ScoreDummie(opponent,mine);
		for (int i = 0;i<Solution1.genome.size();i++){
			Card1 attacker = this.other.getBoard().elementAt(Solution1.genome.elementAt(i).elementAt(0));
			Card1 defender = this.me.getBoard().elementAt(Solution1.genome.elementAt(i).elementAt(1));
			defender.getDeattackBy(attacker,this.other,this.me,mine,opponent);
			defender.deCounterAttack(attacker,this.other,this.me);
		}

		return score;

	}


public Solution1 bestAttackCard1ByCard1(Card1 opponent,Card1 mine){
    //this.other.getBoard().removeElement(opponent);
		int n = this.other.getBoard().size();
		int p = this.me.getBoard().size();
		Solution1 Solution1 = new Solution1(n,p);
		Vector<Integer> toBeDone = new Vector<Integer>();
		for (int i = 0;i<n;i++){
			toBeDone.addElement(i);
		}

		while (toBeDone.size() > 0){
			Vector<Integer> best = new Vector<Integer>();
			best.addElement(toBeDone.elementAt(0));
			best.addElement(0);
			double bestScore = -10000.0;

			for (int i = 0;i<toBeDone.size();i++){
				for (int j = 0;j<this.me.getBoard().size();j++){
					Vector<Integer> test = new Vector<Integer>();
					test.addElement(toBeDone.elementAt(i));
					test.addElement(j);
          //Card1 c = this.me.getBoard().elementAt(j);
          //System.err.println("La carte  "+c.getId()+" de defense :"+c.getDefense());

					Solution1.incrGenome(test);
					if (Solution1.valideSolution1(this.other,this.me,mine,opponent) && this.scoreSolution1(Solution1,opponent,mine) < bestScore){
						bestScore  = this.scoreSolution1(Solution1,opponent,mine);
						best = test;
					}
					Solution1.decrGenome();
				}
			}

			Solution1.incrGenome(best);
			toBeDone.removeElement(best.elementAt(0));

		}
    //this.other.getBoard().add(0,opponent);
		return Solution1;
	}

public Vector<Vector<Vector<Card1>>> attack2(Card1 opponent,Card1 mine){
		Solution1 Solution1 = this.bestAttackCard1ByCard1(opponent,mine);
		Vector<Solution1> population = Solution1.permutationSolution1(this.other,this.me);

		//System.err.println(population.size());
		Vector<Vector<Vector<Card1>>> possibles = new Vector<Vector<Vector<Card1>>>();
		for (int i = 0;i<population.size();i++){
			Solution1 sol = population.elementAt(i);
			Vector<Vector<Card1>> attack = new Vector<Vector<Card1>>();
			for (int j = 0;j<sol.getGenome().size();j++){
				Vector<Card1> att = new Vector<Card1>();
        //System.err.println(this.me.getBoard().size());
        //System.err.println(sol.getGenome().size());
				Card1 attacker = this.other.getBoard().elementAt(sol.getGenome().elementAt(j).elementAt(0));
				Card1 defender = this.me.getBoard().elementAt(sol.getGenome().elementAt(j).elementAt(1));
				att.addElement(attacker);
				att.addElement(defender);
				attack.addElement(att);
			}
			possibles.addElement(attack);
		}
		return possibles;
	}

  public Solution1 attackBasicSolution1(Card1 opponent,Card1 mine){
		Vector<Vector<Integer>> renvoie = new Vector<Vector<Integer>>();
    //this.other.getBoard().removeElement(opponent);
		Vector<Card1> b = this.other.getBoard();
		Vector<Card1> bOpponent = this.me.getBoard();
		Vector<Card1> g = this.me.getGuardsInBoard();
    Vector<Vector<Card1>> toRefresh = new Vector<Vector<Card1>>();
		for (int i = 0;i<b.size();i++){
			if (!b.elementAt(i).getUsed()){
			if (g.size() == 0){

				Vector<Integer> att = new Vector<Integer>();
				att.addElement(i);
				att.addElement(0);
				renvoie.addElement(att);
			}
			else {

				Card1 c = g.firstElement();
        //System.err.println("La carte "+c.getId()+" de defense :"+c.getDefense());
				c.getAttackBy(b.elementAt(i),this.other,this.me,mine,opponent);
        //System.err.println("La carte  après att "+c.getId()+" de defense :"+c.getDefense());
				if (c.getDefense() <= 0){
          g.removeElementAt(0);
          c.getDeattackBy(b.elementAt(i),this.other,this.me,mine,opponent);
          //System.err.println("La carte après deAtt directe "+c.getId()+" de defense :"+c.getDefense());
        }
        else {
          Vector<Card1> refresh = new Vector<Card1>();
          refresh.add(b.elementAt(i));
          refresh.add(c);
        }
				Vector<Integer> att = new Vector<Integer>();
				att.addElement(i);
				att.addElement(this.me.getBoard().indexOf(c));
				renvoie.addElement(att);
			}
		}
	}

  for (int i = 0;i<toRefresh.size();i++){
    Card1 attacker = toRefresh.elementAt(i).elementAt(0);
    Card1 defender = toRefresh.elementAt(i).elementAt(1);
    defender.getDeattackBy(attacker,this.other,this.me,mine,opponent);
    //System.err.println("La carte après deAtt refresh "+defender.getId()+" de defense :"+defender.getDefense());

  }





		Solution1 sol = new Solution1(this.other.getBoard().size(),this.me.getBoard().size());
		sol.setGenome(renvoie);
    //this.other.getBoard().add(0,opponent);
		return sol;
	}

	public Vector<Vector<Vector<Card1>>> attack3(Card1 opponent,Card1 mine){
		Solution1 Solution1 = this.attackBasicSolution1(opponent,mine);
		Vector<Solution1> population = Solution1.permutationSolution1(this.other,this.me);

		//System.err.println(population.size());
		Vector<Vector<Vector<Card1>>> possibles = new Vector<Vector<Vector<Card1>>>();
		for (int i = 0;i<population.size();i++){
			Solution1 sol = population.elementAt(i);
			Vector<Vector<Card1>> attack = new Vector<Vector<Card1>>();
			for (int j = 0;j<sol.getGenome().size();j++){
				Vector<Card1> att = new Vector<Card1>();
				Card1 attacker = this.other.getBoard().elementAt(sol.getGenome().elementAt(j).elementAt(0));
				Card1 defender = this.me.getBoard().elementAt(sol.getGenome().elementAt(j).elementAt(1));
				att.addElement(attacker);
				att.addElement(defender);
				attack.addElement(att);
			}
			possibles.addElement(attack);
		}
		return possibles;
	}



public Vector<Vector<Card1>> bestAttackOpponent(Card1 opponent,Card1 mine){
    //Card1 opponent = this.other.getBoard().elementAt(0);
    //System.err.println("on vire de son board la Card1e d'Id :"+this.other.getBoard().elementAt(0).getId());
    //this.other.getBoard().removeElementAt(0);
		Vector<Vector<Vector<Card1>>> possibles2 = this.attack2(opponent,mine);
		Vector<Vector<Vector<Card1>>> possibles3 = this.attack3(opponent,mine);
    //this.other.getBoard().add(0,opponent);
		Vector<Vector<Vector<Card1>>> possibles = new Vector<Vector<Vector<Card1>>>();
		possibles.addAll(possibles2);
		possibles.addAll(possibles3);

		//System.err.println(possibles.size());
		int bestAnswer = 0;
		double bestScore = 1000.0;
		//long startTime = System.nanoTime();
		/*
		while (((endTime - startTime)/1000000) < 110){
				endTime = System.nanoTime();
				}
		*/

		for (int i = 0;i<possibles.size();i++){

			//System.err.println(i);
			//System.err.println(possibles.size());
			Vector<Vector<Card1>> test = possibles.elementAt(i);
			for (int j = 0;j<test.size();j++){
				Card1 attacker = test.elementAt(j).elementAt(0);
				Card1 defender = test.elementAt(j).elementAt(1);
				defender.getAttackBy(attacker,this.other,this.me,mine,opponent);
				defender.counterAttack(attacker,this.other,this.me);
			}
			double tempScore = this.getTurn1ScoreDummie(opponent,mine);
			if (tempScore < bestScore){
				bestScore = tempScore;
				bestAnswer = i;
			}
			for (int j = 0;j<test.size();j++){
				Card1 attacker = test.elementAt(j).elementAt(0);
				Card1 defender = test.elementAt(j).elementAt(1);
				defender.getDeattackBy(attacker,this.other,this.me,mine,opponent);
				defender.deCounterAttack(attacker,this.other,this.me);
			}

		}
		//long endTime = System.nanoTime();
		//System.err.println("la boucle se calcul en " + (endTime - startTime) + " ns");
		//System.err.println("c'est-à-dire en " + (endTime - startTime)/1000000 + " ms");
		return possibles.elementAt(bestAnswer);
	}








public Vector<Vector<Card1>> bestAttackRandom(long startTime,Card1 mine){
	int compteur = 0;
	int compteurGlobal = 0;
	Vector<Vector<Card1>> bestAnswer = new Vector<Vector<Card1>>();
	double bestScore = -1000000.0;
  double bestScoreBis = -1000000.0;
	Card1 opponent = this.other.getBoard().elementAt(0);
	//long startTime = System.nanoTime();
	long endTime = System.nanoTime();
	while (((endTime - startTime)/1000000) < 90){
		compteurGlobal++;



			Vector<Vector<Card1>> attaque = this.generateRandomAttack(compteur,startTime);
      //System.err.println("la Solution1 est de taille :"+attaque.size());
			/*System.err.println("la Solution1 consiste en :");
			for (int i = 0;i<attaque.size();i++){
				System.err.println("ATTACK "+attaque.elementAt(i).elementAt(0).getId()+ " "+attaque.elementAt(i).elementAt(1).getId()+"; ");
			}
			System.err.println("est elle valide ? "+this.me.validAnswerBis(attaque,this.other));
			*/




			if (this.me.validAnswerBis(attaque,this.other,opponent,mine) == true){
				compteur++;

				for (int j = 0;j<attaque.size();j++){
					Card1 attacker = attaque.elementAt(j).elementAt(0);
					Card1 defender = attaque.elementAt(j).elementAt(1);
					defender.getAttackBy(attacker,this.me,this.other,opponent,mine);
					defender.counterAttack(attacker,this.me,this.other);
					
					
				}
				double tempScoreBis = this.getTurn1ScoreDummie(opponent,mine);
				double tempScore = this.getTurn1Score(opponent,mine);
				
				if (tempScore > bestScore){
					/*for (int j = 0;j<attaque.size();j++){
						Card1 attacker = attaque.elementAt(j).elementAt(0);
						Card1 defender = attaque.elementAt(j).elementAt(1);
						System.err.println("ATTACK "+attacker.getId()+" "+defender.getId());
						
					}
					this.afficher();*/
          			bestScoreBis = tempScoreBis;
					bestScore = tempScore;
					bestAnswer = attaque;
				}

        		else if ((int)tempScore == (int)bestScore && tempScoreBis > bestScoreBis){
          			bestScoreBis = tempScoreBis;
					bestAnswer = attaque;
        		}




				for (int j = attaque.size()-1;j>=0;j--){
					Card1 attacker = attaque.elementAt(j).elementAt(0);
					Card1 defender = attaque.elementAt(j).elementAt(1);
					defender.getDeattackBy(attacker,this.me,this.other,opponent,mine);
					defender.deCounterAttack(attacker,this.me,this.other);
				/*	System.err.println("DEATTACK "+attacker.getId()+" "+defender.getId());
					System.err.println("attacker "+attacker.getDefense());
					System.err.println("defender "+defender.getDefense());*/
				}
			}
			endTime = System.nanoTime();
	}
			/*System.err.println("nombre de test :"+compteur);
			System.err.println("nombre de test total :"+compteurGlobal);*/
	//System.err.println(bestAnswer);
	return bestAnswer;
}

public Vector<Vector<Card1>> bestAttackRandomDepth1(long startTime,Vector<Card1> summonWithoutCharge,Card1 mine){
	int compteur = 0;
	int compteurGlobal = 0;
	Vector<Vector<Card1>> bestAnswer = new Vector<Vector<Card1>>();
  Vector<Vector<Card1>> bestAnswerOpponent = new Vector<Vector<Card1>>();
  Card1 opponent = this.other.getBoard().elementAt(0);
	double bestScore = -1000000.0;
  double bestScoreBis = -1000000.0;
	//long startTime = System.nanoTime();
  //System.err.println("mon board : "+this.me.getBoard().size());
  //System.err.println("his board : "+this.other.getBoard().size());

	long endTime = System.nanoTime();
	while (((endTime - startTime)/1000000) < 40){
		compteurGlobal++;



			Vector<Vector<Card1>> attaque = this.generateRandomAttack(compteur,startTime);
			/*System.err.println("la Solution1 consiste en :");
			for (int i = 0;i<attaque.size();i++){
				System.err.println("ATTACK "+attaque.elementAt(i).elementAt(0).getId()+ " "+attaque.elementAt(i).elementAt(1).getId()+"; ");
			}
			System.err.println("est elle valide ? "+this.me.validAnswerBis(attaque,this.other));
			*/




			if (this.me.validAnswerBis(attaque,this.other,opponent,mine) == true){
				compteur++;

        Vector<Card1> myDead = new Vector<Card1>();
        Vector<Card1> hisDead = new Vector<Card1>();

        //System.err.println("mon board avant mes att: "+this.me.getBoard().size());
        //System.err.println("his board avant mes att: "+this.other.getBoard().size());

				for (int j = 0;j<attaque.size();j++){
					Card1 attacker = attaque.elementAt(j).elementAt(0);
					Card1 defender = attaque.elementAt(j).elementAt(1);
					defender.getAttackBy(attacker,this.me,this.other,opponent,mine);
					defender.counterAttack(attacker,this.me,this.other);

          if (defender.getDefense() <=0){
            hisDead.addElement(defender);
            this.other.getBoard().removeElement(defender);
          }
          if (attacker.getDefense() <=0){
            myDead.addElement(attacker);
            this.me.getBoard().removeElement(attacker);
          }

          /*System.err.println("ME ATTACK "+attacker.getId()+" "+defender.getId());
					System.err.println("attacker "+attacker.getDefense());
					System.err.println("defender "+defender.getDefense());*/

				}

        /*System.err.println("mon board après mes att: "+this.me.getBoard().size());
        System.err.println("his board après mes att: "+this.other.getBoard().size());*/


        for (int me = 0;me<summonWithoutCharge.size();me++){
            //System.err.println("On rajoute l'id "+summonWithoutCharge.elementAt(me).getId()+" de defense :"+summonWithoutCharge.elementAt(me).getDefense());
                this.me.getBoard().add(summonWithoutCharge.elementAt(me));
        }

        /*System.err.println("mon board après rajouts des autres: "+this.me.getBoard().size());
        System.err.println("his board après rajouts des autres: "+this.other.getBoard().size());*/



        this.other.getBoard().removeElement(opponent);
        Vector<Vector<Card1>> hisNextAttack = this.bestAttackOpponent(opponent,mine);
        this.other.getBoard().add(0,opponent);
        for (int j = 0;j<hisNextAttack.size();j++){
					Card1 attacker = hisNextAttack.elementAt(j).elementAt(0);
					Card1 defender = hisNextAttack.elementAt(j).elementAt(1);
          /*System.err.println(" HIM : ATTACK "+attacker.getId()+" "+defender.getId());
          System.err.println("attacker before "+attacker.getDefense());
          System.err.println("defender before "+defender.getDefense());*/
					defender.getAttackBy(attacker,this.other,this.me,mine,opponent);
					defender.counterAttack(attacker,this.other,this.me);

				/*	System.err.println("attacker after "+attacker.getDefense());
					System.err.println("defender after "+defender.getDefense());*/


          if (defender.getDefense() <=0){
            myDead.addElement(defender);
            this.me.getBoard().removeElement(defender);
          }
          if (attacker.getDefense() <=0){
            hisDead.addElement(attacker);
            this.other.getBoard().removeElement(attacker);
          }
				}

        //System.err.println("mon board : "+this.me.getBoard().size());
        //System.err.println("his board : "+this.other.getBoard().size());
      /*  System.err.println("mon board après ses att: "+this.me.getBoard().size());
        System.err.println("his board après ses att: "+this.other.getBoard().size());*/

				double tempScoreBis = this.getTurn1ScoreDummie(opponent,mine);
        	double tempScore = this.getTurn1Score(opponent,mine);
				if (tempScore > bestScore){
          bestScoreBis = tempScoreBis;
					bestScore = tempScore;
					bestAnswer = attaque;
          bestAnswerOpponent = hisNextAttack;
          //System.err.println("compteur :"+compteur);
          System.err.println("SCORE :"+bestScore);
				}

        else if (tempScore == bestScore && tempScoreBis > bestScoreBis){
          bestScoreBis = tempScoreBis;
          //System.err.println("compteur :"+compteur);
          bestAnswerOpponent = hisNextAttack;
					bestAnswer = attaque;
          System.err.println("SCORE :"+bestScore);
          System.err.println("SCORE tempo :"+bestScoreBis);

        }

      /*  System.err.println("mon board après score: "+this.me.getBoard().size());
        System.err.println("his board après score: "+this.other.getBoard().size());*/



        for (int j = hisNextAttack.size()-1;j>=0;j--){
					Card1 attacker = hisNextAttack.elementAt(j).elementAt(0);
					Card1 defender = hisNextAttack.elementAt(j).elementAt(1);
          if (defender.getDefense() <=0){
            myDead.removeElement(defender);
            this.me.getBoard().addElement(defender);
          }
          if (attacker.getDefense() <=0){
            hisDead.removeElement(attacker);
            this.other.getBoard().addElement(attacker);
          }
					defender.getDeattackBy(attacker,this.other,this.me,mine,opponent);
					defender.deCounterAttack(attacker,this.other,this.me);

				}

      /*  System.err.println("mon board après ses Deatt: "+this.me.getBoard().size());
        System.err.println("his board après ses Deatt: "+this.other.getBoard().size());*/


        for (int me = 0;me<summonWithoutCharge.size();me++){
                this.me.getBoard().removeElement(summonWithoutCharge.elementAt(me));
        }
      /*  System.err.println("mon board après removal autres: "+this.me.getBoard().size());
        System.err.println("his board après removal autres: "+this.other.getBoard().size());*/

				for (int j = attaque.size()-1;j>=0;j--){
					Card1 attacker = attaque.elementAt(j).elementAt(0);
					Card1 defender = attaque.elementAt(j).elementAt(1);
          if (defender.getDefense() <=0){
            hisDead.removeElement(defender);
            this.other.getBoard().addElement(defender);
          }
          if (attacker.getDefense() <=0){
            //System.err.println("attacker reAdd :"+attacker.getId());
            myDead.removeElement(attacker);
            this.me.getBoard().addElement(attacker);
          }
					defender.getDeattackBy(attacker,this.me,this.other,opponent,mine);
					defender.deCounterAttack(attacker,this.me,this.other);

				}
      /*  System.err.println("mon board après mes Deatt: "+this.me.getBoard().size());
        System.err.println("his board après mes Deatt: "+this.other.getBoard().size());*/
			}
			endTime = System.nanoTime();
			}
			System.err.println("nombre de test :"+compteur);
			System.err.println("nombre de test total :"+compteurGlobal);
	/*System.err.println("His answer :");
  for (int k = 0;k<bestAnswerOpponent.size();k++){
    System.err.println("ATTACK "+bestAnswerOpponent.elementAt(k).elementAt(0).getId()+" "+bestAnswerOpponent.elementAt(k).elementAt(1).getId());
  }*/
	return bestAnswer;
}

public String summonPossibilities(String a,Vector<Card1> summonWithoutCharge,Card1 opponent,Card1 mine){

		Hand1 Hand1 = new Hand1(this.me.getHand1());
		int mana = this.me.getMana();

		if (1 == 0){




		    }
		else {

			Vector<Vector<Card1>> possible = Hand1.allPosibilitesWithoutItem(mana,this.me.getBoard().size(),this.me,this.other);
			if (possible.size() >0){
			double bestScore = -100000000.0;
			int bestAnswer = 0;






			for (int i = 0;i<possible.size();i++){
				double score = 0;
				Vector<Card1> summoning = possible.elementAt(i);
        //System.err.println("");
				Vector<Card1> bestUseGreen = new Vector<Card1>();
				Vector<Card1> bestUseRed = new Vector<Card1>();


				for (int j = 0;j<summoning.size();j++){
					Card1 c = summoning.elementAt(j);
          			//System.err.print("GO FOR "+c.getId()+" ");
					if (c.getCard1Type() < 1){this.me.getBoard().addElement(c);}
					else if (c.getCard1Type() == 1 && this.me.getBoard().size()>0){
						Card1 bestUse = bestUseOfGreenCard1(c, opponent, mine);
						bestUseGreen.addElement(bestUse);
						bestUse.getBuffByGreenCard1(c);
						//this.other.getBoard().addElement(c);
					}
					else if (c.getCard1Type() == 2 && this.other.getBoard().size()>1){
						Card1 bestUse = bestUseOfRedCard1(c, opponent, mine);
						bestUseRed.addElement(bestUse);
						bestUse.getBuffByRedCard1(c);
						//this.other.getBoard().addElement(c);
					} else {
						//Card1 reverseC = new Card1(c);
						//this.me.getBoard().addElement(reverseC);
						this.me.setHp(this.me.getHp()+c.getHpChange());
						this.other.setHp(this.me.getHp()+c.getHpChangeOpponent());
					}

				}
				score = this.getTurn1ScoreSummon(opponent,mine);
				//System.err.print(" de score : "+score+" ");
				//System.err.println();

				if (score>=bestScore){
					bestScore = score;
					bestAnswer = i;
					//System.err.print("best score :"+bestScore);
			  		//System.err.println();
				}
				//System.err.print("mon tempo ");
			 

				for (int j = summoning.size()-1;j>=0;j--){
					Card1 c = summoning.elementAt(j);
					if (c.getCard1Type() < 1){this.me.getBoard().removeElement(c);}
					else if (c.getCard1Type() == 1 && this.me.getBoard().size()>0){
						Card1 bestUse = bestUseGreen.firstElement();
						bestUseGreen.remove(0);
						bestUse.getDebuffByGreenCard1(c);
						//this.other.getBoard().addElement(c);
					}
					else if (c.getCard1Type() == 2 && this.other.getBoard().size()>1){
						Card1 bestUse = bestUseRed.firstElement();
						bestUseRed.remove(0);
						bestUse.getDebuffByRedCard1(c);
						//this.other.getBoard().addElement(c);
					} else {
						//Card1 reverseC = new Card1(c);
						//this.me.getBoard().removeElement(reverseC);
						this.me.setHp(this.me.getHp()-c.getHpChange());
						this.other.setHp(this.me.getHp()-c.getHpChangeOpponent());
					}

				}
			}


			Vector<Card1> items = new Vector<Card1>();
			Vector<Card1> finalAnswer = possible.elementAt(bestAnswer);
			for (int i = 0;i<finalAnswer.size();i++){
				Card1 c = finalAnswer.elementAt(i);
				if (c.getCard1Type() == 0){
					a += c.printSUMMON();


					this.me.getHand1().removeElement(c);
					if (c.getCharge()){this.me.getBoard().addElement(c);}
					  else {
						summonWithoutCharge.addElement(c);
						this.me.getBoard().addElement(c);}
				} else if (c.getCard1Type() == 3){
					a += c.printUSE();
				} else {
					items.addElement(c);
				}


			}
			//il reste à s'occuper des items rouges et verts
			if (items.size() > 0){
				Vector<Card1> myBoard = this.me.getBoard();
				Vector<Card1> hisBoard = this.other.getBoard();
				for (int k = 0; k < items.size();k++){
					Card1 objet = items.elementAt(k);
					//System.err.println(myBoard.size());
					if (objet.getCard1Type() == 1 && myBoard.size()+summonWithoutCharge.size() >0){
						Card1 bestUse = this.bestUseOfGreenCard1(objet,opponent,mine);
						this.me.getHand1().removeElement(objet);
						bestUse.getBuffByGreenCard1Bis(objet);
						

						
						a += bestUse.printUSE(objet);
					} else if(objet.getCard1Type() == 2 && hisBoard.size() >1) {
						Card1 bestUse = this.bestUseOfRedCard1(objet,opponent,mine);
						//System.err.println("before :" + bestUse.getDefense());
						bestUse.getBuffByRedCard1(objet);
						//System.err.println("after :"+ bestUse.getDefense());
						this.me.getHand1().removeElement(objet);
						a += bestUse.printUSE(objet);
					}

				}
			}




		}
		}
		for (int p = 0;p<summonWithoutCharge.size();p++){
			this.me.getBoard().removeElement(summonWithoutCharge.elementAt(p));
		}
		return a;

	}








	public void pickDraft(Deck1 d){
		if (this.getTurn1() < 30){
			int score = -100000;

			Vector<Card1> h = this.me.getHand1();
			Card1 best = h.elementAt(2);

			int score1;
			int score2;
			int score3;

			d.incrCard1(h.elementAt(0));
			score1 = d.scoreDeck1();
			d.decrCard1(h.elementAt(0));

			d.incrCard1(h.elementAt(1));
			score2 = d.scoreDeck1();
			d.decrCard1(h.elementAt(1));

			d.incrCard1(h.elementAt(2));
			score3 = d.scoreDeck1();
			d.decrCard1(h.elementAt(2));

			if (score1 >= score){
				best = h.elementAt(0);
				score = score1;
			}

			if (score2 >= score){
				best = h.elementAt(1);
				score = score2;
			}

			if (score3 >= score){
				best = h.elementAt(2);
				score = score3;
			}

            System.err.println(best);
			d.incrCard1(best);
			best.printPICK();

		}

	}

  public String attackBasicBis(String a,long startTime,Vector<Card1> summonWithoutCharge,Card1 mine){
		//Vector<Vector<Card1>> answer = this.bestAttackRandom(startTime,mine);
		//Vector<Vector<Card1>> answer = this.bestAttack2();
		//Vector<Vector<Card1>> answer = this.bestAttack(startTime);
    	Vector<Vector<Card1>> answer = this.bestAttackRandomDepth1(startTime,summonWithoutCharge,mine);

		for (int i = 0;i<answer.size();i++){
			a += answer.elementAt(i).elementAt(0).printATTACK(answer.elementAt(i).elementAt(1));
      /*Card1 attacker = answer.elementAt(i).elementAt(0);
      Card1 defender = answer.elementAt(i).elementAt(1);

      if (defender.getWard()){}
      else if (attacker.getLethal()){defender.setDefense(0);}
      else {defender.setDefense(defender.getDefense()-attacker.getAttack());}

      if (attacker.getWard()){}
      else if (defender.getLethal()){attacker.setDefense(0);}
      else {attacker.setDefense(attacker.getDefense()-defender.getAttack());}

      System.err.println("attacker :"+attacker.getId()+" attack : "+attacker.getAttack()+" de defense "+attacker.getDefense());
      System.err.println("defender :"+defender.getId()+" attack : "+defender.getAttack()+" de defense "+defender.getDefense());


      if (attacker.getDefense()<=0){this.me.getBoard().removeElement(attacker);}
      if (defender.getDefense()<=0){this.other.getBoard().removeElement(defender);}
      */
		}
		return a;
	}



}

class PlayerEmpty{

public static void main(String args[]) {
		int TOUR = 0;
		Deck1 d = new Deck1();

    /*
    long s = System.nanoTime();
    Vector<Vector<Vector<Integer>>> test = fakeT.calculAllPossibilites(meINDICE,opponentINDICE);
    long e = System.nanoTime();
    */






		Scanner in = new Scanner(System.in);

		// game loop
		while (true) {


				int Joueur1Health = in.nextInt();
				int Joueur1Mana = in.nextInt();
				int Joueur1Deck1 = in.nextInt();
				int Joueur1Rune = in.nextInt();
				//Joueur1 Joueur11 = new Joueur1(Joueur1Health,Joueur1Mana,Joueur1Deck1,Joueur1Rune);
				int Joueur1HealthOpponent = in.nextInt();
				int Joueur1ManaOpponent = in.nextInt();
				int Joueur1Deck1Opponent = in.nextInt();
				int Joueur1RuneOpponent = in.nextInt();
				//Joueur1 Joueur12 = new Joueur1(Joueur1HealthOpponent,Joueur1ManaOpponent,Joueur1Deck1Opponent,Joueur1RuneOpponent);
				int opponentHand1 = in.nextInt();
				int Card1Count = in.nextInt();
				Vector<Card1> board = new Vector();
				Vector<Card1> Hand1 = new Vector();
				Vector<Card1> boardOpponent = new Vector();
        		Vector<Card1> summonWithoutCharge = new Vector();
				Card1 opponent = new Card1(Joueur1HealthOpponent);
        		Card1 mine = new Card1(Joueur1Health);
				boardOpponent.addElement(opponent);
        		summonWithoutCharge.addElement(mine);
				for (int i = 0; i < Card1Count; i++) {
						int Card1Number = in.nextInt();
						int instanceId = in.nextInt();
						int location = in.nextInt();
						int Card1Type = in.nextInt();
						int cost = in.nextInt();
						int attack = in.nextInt();
						int defense = in.nextInt();
						String abilities = in.next();
						int myHealthChange = in.nextInt();
						int opponentHealthChange = in.nextInt();
						int Card1Draw = in.nextInt();
						Card1 c = new Card1(Card1Number,instanceId,location,Card1Type,cost,attack,defense,abilities,myHealthChange,opponentHealthChange,Card1Draw,i);
						if (location == 0){
							if (!c.getCharge()) {c.setUsed(true);}
							Hand1.add(c);
						}
						else if (location == 1 && c.getAttack() > 0){
							board.add(c);
						}
            else if (location == 1){
            	summonWithoutCharge.add(c);
            }
            else if (location == -1 && (c.getAttack() >0 || c.getGuard())){
							boardOpponent.add(c);

						}
				}
				long startTime = System.nanoTime();
				Vector<Card1> fake = new Vector<Card1>();
				Joueur1 Joueur11 = new Joueur1(Joueur1Health,Joueur1Mana,Joueur1Deck1,Joueur1Rune,Hand1,board);
				Joueur1 Joueur12 = new Joueur1(Joueur1HealthOpponent,Joueur1ManaOpponent,Joueur1Deck1Opponent,Joueur1RuneOpponent,fake,boardOpponent);
				//Joueur1 Joueur12 = new Joueur1(Joueur1HealthOpponent,Joueur1ManaOpponent,Joueur1Deck1Opponent,Joueur1RuneOpponent,fake,hisGuards);

				//Joueur11.setHand1(Hand1);
				//Joueur11.setBoard(board);
				//Joueur12.setBoard(boardOpponent);
				Turn1 tour = new Turn1(TOUR,Joueur11,Joueur12);




        /*if (TOUR == 0){
            System.err.println("le vecteur se calcul en " + (e-s) + " ns");
            System.err.println("c'est-à-dire en " + (e-s)/1000000 + " ms");
            System.err.println(test);
        }
        */


				if (TOUR <30){tour.pickDraft(d);}
				else{
				    String answer = "PASS;";
            	String hisAnswer = "";
				    answer = tour.summonPossibilities(answer,summonWithoutCharge,opponent,mine);
					//	if (tour.getOther().getBoard().size() >1){System.err.println(" test his life :" + tour.getOther().getBoard().elementAt(1).getDefense());}




						if (tour.getMe().getBoard().size()>0){


							answer = tour.attackBasicBis(answer,startTime,summonWithoutCharge,mine);
						}
            /*
            long hisStartTime = System.nanoTime();
            boardOpponent.removeElement(opponent);
            if (tour.getOther().getBoard().size()>0){
              for (int me = 0;me<summonWithoutCharge.size();me++){
                board.add(summonWithoutCharge.elementAt(me));
                System.err.println(summonWithoutCharge.elementAt(me).getId());
              }
              hisAnswer = tour.attackBasicBisOpponent(hisAnswer,startTime);
              for (int me = 0;me<summonWithoutCharge.size();me++){
                board.removeElement(summonWithoutCharge.elementAt(me));
              }
            }
            boardOpponent.add(0,opponent);
            long hisEndTime = System.nanoTime();
            System.err.println("son move se calcul en " + (hisEndTime - hisStartTime) + " ns");
            System.err.println("c'est-à-dire en " + (hisEndTime - hisStartTime)/1000000 + " ms");
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
            //for (int t = 0;t<tour.getOther().getBoard().size();t++){  System.err.println(" "+tour.getOther().getBoard().elementAt(t).getId());}

            System.err.println("la boucle de jeux total se calcul en " + (endTime - startTime) + " ns");
            System.err.println("c'est-à-dire en " + (endTime - startTime)/1000000 + " ms");
						System.out.println(answer);
				}





				// Write an action using System.out.println()
				// To debug: System.err.println("Debug messages...");

				TOUR++;
		}
}
}
