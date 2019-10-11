/* Keer Sun, Taylor Burke
Project 2
CS313 
resources:
*/

import java.io.*; 
import java.util.*; 

public class ORF_Finder {
    private String seq;
    private int num_ORFs;
    private int num_ORFs_300nt;
    private int num_ORFs_distribution;
    private int num_ORFs_kozak;
    private int num_ORFs_encoding;

    public ORF_Finder(String fileName){
        this.seq = SequenceOps.sequenceFromFastaFile(fileName).replace('T', 'U');
        this.num_ORFs = 0;
        this.num_ORFs_300nt = 0;
        this.num_ORFs_distribution = 0;
        this.num_ORFs_kozak = 0;
        this.num_ORFs_encoding = 0;
        num_ORF();
    }


    // helper methods for larger than 300
    public static int largerThan300(ArrayList<Integer> ls) {
        int count = 0;
        for (int i = ls.size()-1; i >= 0; i--) {
            if (ls.get(i) >= 300) {
                count = ls.size();
            } else {
                ls.remove(i);
            }
        }
        return count;
    }
            
    public static ArrayList <Integer> allAdd3(ArrayList <Integer> ls) {
        for (int i = 0; i < ls.size(); i++) {
            ls.set(i, ls.get(i)+3);
        }
        return ls;
    }

    public void num_ORF(){
        int startsEncountered = 0;
        boolean inORF = false;

        ArrayList <Integer> lengthCount = new ArrayList <Integer> (100);
        int numStarts = 0;

        for (int i = 0; i < 3; i++){
            for (int startIndex = i; (seq.length()-startIndex) >= 3; startIndex += 3){
                
                String codon = seq.substring(startIndex, startIndex + 3);
                char representation = SequenceOps.translateCodon(codon);

                if (codon.equals("AUG") && !inORF){
                    inORF = true;
                    startsEncountered++;
                    numStarts++;
                    lengthCount.clear();
                    lengthCount.add(3);
                } else if (representation == '*' && inORF){
                    inORF = false;
                    lengthCount = allAdd3(lengthCount);
                    this.num_ORFs += startsEncountered;
                    startsEncountered = 0;
                    this.num_ORFs_300nt += largerThan300(lengthCount);
                } else if (codon.equals("AUG") && inORF){
                    startsEncountered++;
                    numStarts++;
                    lengthCount = allAdd3(lengthCount);
                } else {
                    //when you encounter a stop OUTSIDE of an ORF, you do not count it
                    lengthCount = allAdd3(lengthCount);
                }

            }
        }
    }

    public int getNumORFs(){
        return this.num_ORFs;
    }

    public int getNumORF300nt(){
        return this.num_ORFs_300nt;
    }

    public static String helper(String s){
        String leftEdge = "*****  ";
        String rightEdge = "  *****";
        StringBuilder testing = new StringBuilder();
        int n = leftEdge.length()*2 + s.length();
        StringBuilder topBottom = new StringBuilder();
        for (int i = 0; i<n ; i++) {
            topBottom.append("*");
        }
        testing.append(topBottom);
        testing.append("\n");
        testing.append(leftEdge);
        testing.append(s);
        testing.append(rightEdge);
        testing.append("\n");
        testing.append(topBottom);
        return testing.toString();
    }


    public static void main(String[] args){
        ORF_Finder test = new ORF_Finder(args[0]);

        System.out.println(helper("Genomic Sequence Read-In From Fasta Formatted File"));
        System.out.println("Number of ORFs is:" + "\t" + test.getNumORFs());
        System.out.println("Number of ORFs at least 300nt:" + "\t" + test.getNumORF300nt());

        

        String test2Name = "Random Sequnce With Same Length And GC-Content as Sequence Read-in From File";
    }
}