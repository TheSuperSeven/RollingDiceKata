package src;

import Random.RNG;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Roll {

    public enum RollType {
        NORMAL,
        ADVANTAGE,
        DISADVANTAGE
    }

    // Attributes

    private int diceValue;
    private int nbRoll;
    private int modifier;

    public Roll(String formula) {
        int counterBadCarac = 0;
        String[] badCarac = formula.split("[^[d0123456789+-]]", 2); //C'est une très mauvaise idée pour régler ce problème. Dommage !
        for(String c : badCarac)
            counterBadCarac++;
        if(counterBadCarac > 1) {
            this.diceValue = -1;
        }else {


            int counter = 0;
            String[] arrOfStr = formula.split("[d+-]", 0);
            for (String a : arrOfStr) {
                if (counter == 0) {
                    if (a == "") {
                        this.nbRoll = 1;
                    } else if (parseInt(a) > 0) {
                        this.nbRoll = parseInt(a);
                    } else {
                        this.nbRoll = -1;
                    }
                }

                if (counter == 1) {
                    if (a == "") {
                        this.diceValue = -1;
                    } else if (parseInt(a) > 0) {
                        this.diceValue = parseInt(a);
                    } else {
                        this.diceValue = -1;
                    }
                }

                if (counter == 2) {
                    if (a == "") {
                        this.modifier = 0;
                    } else if (parseInt(a) > 0) {
                        this.modifier = parseInt(a);
                    }
                    int counterMinus = 0;
                    String[] list = formula.split("-", 0);
                    for (String b : list)
                        counterMinus++;
                    if (counterMinus > 1)
                        this.modifier = parseInt(a) * -1;
                }
                counter += 1;
            }
        }
    }

    public Roll(int diceValue, int nbRoll, int modifier) {
        this.nbRoll = nbRoll;
        this.diceValue = diceValue;
        this.modifier = modifier;
    }

    public int makeRoll(RollType rollType) {
        int score = 0;
        int inter;
        switch(rollType){
            case NORMAL:
                for (int i = 0 ; i < nbRoll ; i++) {
                    score += RNG.random(diceValue);
                }
                score += modifier;
                if(score < 0) {
                    score = 0;
                }
                if(diceValue <= 0 || nbRoll <= 0){
                    return -1;
                }
                return score;

            case ADVANTAGE:
                for(int j = 0 ; j < 2 ; j++) {
                    inter = 0;
                    for (int i = 0; i < nbRoll; i++) {
                        inter += RNG.random(diceValue);
                    }
                    inter += modifier;
                    if (inter < 0) {
                        inter = 0;
                    }
                    if (diceValue <= 0 || nbRoll <= 0) {
                        return -1;
                    }
                    if(inter > score){
                        score = inter;
                    }
                }
                return score;
            case DISADVANTAGE:
                for(int j = 0 ; j < 2 ; j++) {
                    score = 0;
                    for (int i = 0; i < nbRoll; i++) {
                        score += RNG.random(diceValue);
                    }
                    score += modifier;
                    if (score < 0) {
                        score = 0;
                    }
                    inter = score;
                    if (diceValue <= 0 || nbRoll <= 0) {
                        return -1;
                    }
                    if(inter < score) {
                        score = inter;
                    }
                }
                return score;
        }
        return -1;
    }

}
