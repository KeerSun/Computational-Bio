/* Keer Sun, Taylor Burke
Project 2
CS313 
resources:
*/

import java.util.*;
import java.io.*; 
import javafx.util.Pair; 


public class Matrix {
    private String seq;
    private String highestProbHex;
    private double highestProb;
    private ArrayList<Pair<String,Double>> notByChance;
    private double CGcontent;

    public Matrix(String fileName){
        this.seq = SequenceOps.sequenceFromFastaFile(fileName);
        this.highestProbHex = "";
        this.highestProb = 0;
        this.CGcontent = SequenceOps.GC_content(this.seq);
        this.notByChance = new ArrayList<Pair<String,Double>>();
        TATAprobs();
    }



    private double getProbabilityChance(char c) {
        double output = 0;
        double CGprob = this.CGcontent/2;
        double ATprob = (1-this.CGcontent)/2;
        if (c == 'C' || c == 'G') {
            output = CGprob;
        } else if (c == 'A' || c == 'T') {
            output = ATprob;
        } else {
            System.out.println("Error"); 
        }  
        return output; 
        
    }

    public double ProbabilityChance(String hex) {
        double output = 1;
        for (int i=0; i<6; i++) {
            double probability =  getProbabilityChance(hex.charAt(i));
            output = output * probability;
        }
        return output;
    }

    public HashMap<Character,double[]> hashTATA() {
        HashMap<Character,double[]> map = new HashMap<Character,double[]>(4);
        map.put('A', new double[]{0.04, .90, 0, .95, .66, .97});
        map.put('C', new double[]{.10, .01, 0, 0, .01, 0});
        map.put('G', new double[]{.03, .01, 0, 0, .01, .03});
        map.put('T', new double[]{.83, .08, 1, .05, .32, 0});
        return map;
    }

    private double ProbabilityTATA(String s) {
        HashMap<Character,double[]> map = new HashMap<Character,double[]>(hashTATA());
        double output = 1;
        for (int i = 0; i<6; i++) {
            output *= map.get(s.charAt(i))[i];
        }
        return output;
    }

    private void TATAprobs() {
        double probabilityLog = 0;
        double probability = 0;
        for (int i = 0; i<=this.seq.length()-6; i++) {
           String hex = this.seq.substring(i, i+6);
           probabilityLog = Math.log(ProbabilityTATA(hex)/ProbabilityChance(hex));
           probability = ProbabilityTATA(hex)/ProbabilityChance(hex);
           if (probabilityLog > 0) {
               if (probability > this.highestProb) this.highestProb = probability;
               Pair<String,Double> p =  new Pair<String,Double>(hex,probabilityLog);
               this.notByChance.add(p);
           } else {
               continue;
           }
        }
    }

    public String toString() {
        String s = "Hexamer with highest TATA probability:" + "\n" + this.highestProbHex;
        s += "\n" + "TATA probablity:" + this.highestProb;
        s += "\n" + "All hexamer more likely to correspond to TATA box than by chance:";
        for (Pair<String,Double> pair : this.notByChance) {
            s += "\n" + pair.toString();
        }
        return s;
    }

    public static void main(String args[]) {
        Matrix m = new Matrix(args[0]);
        System.out.println(m.toString());
    }
}