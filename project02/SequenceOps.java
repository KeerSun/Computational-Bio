/* Keer Sun
Pset0
CS313 
resources:
https://www.w3schools.com/java/java_user_input.asp
https://zhanglab.ccmb.med.umich.edu/FASTA/
https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
https://www.geeksforgeeks.org/collections-shuffle-java-examples/
https://docs.oracle.com/javase/8/docs/api/java/util/Hashtable.html
https://javarevisited.blogspot.com/2016/10/how-to-split-string-in-java-by-whitespace-or-tabs.html
*/
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class SequenceOps{

    public static String sequenceFromFastaFile(String fileName) {
        boolean firstLine = true;
        StringBuilder output = new StringBuilder(); 
        String line = "";
        File s = new File(fileName);
        try {
            Scanner file = new Scanner(s);
            while (file.hasNextLine()) {
                line = file.nextLine();
                if (firstLine){
                    firstLine = false;
                } else {
                    output.append(line);
                }
            }
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found");
        }
        return output.toString();
    }

    public static String sequenceToFastaFormat(String s) {
       String output = "";
       int counter = 0;
       for (int i = 0; i < s.length(); i++) {
            if (counter < 60) {
                output += s.charAt(i);
                counter++;
            } else {
                output += "\n";
                counter = 0;
            }
       } 
       return output;
    }


    public static String reverse(String s){
        String output = "";
        for (int i = (s.length() - 1); i >= 0; i--){
            output += s.charAt(i);
        }
        return output;
    }

    public static String complement(String s) {
        String output = "";
            for (int i = 0; i < s.length(); i++){
                if (s.charAt(i) == 'A') {
                    output = output + 'T';
                } else if (s.charAt(i) == 'T') {
                    output = output + 'A';
                } else if (s.charAt(i) == 'C') {
                    output = output + 'G';
                } else {
                    output = output + 'C';
                }
            } 
            return output;
     } 
    

    public static String reverseComplement(String s){
        String output = "";
        return complement(reverse(s));
    }

    // Human 22 - %47.916576954659507
    // Ecoli - %50.47480343799055
    public static double GC_content(String s){
        int GC = 0;
        double total = 0;
        double output = 0;
        for (int i = 0; i < s.length(); i++){
            if (s.charAt(i) == 'G' || s.charAt(i) == 'C') {
                GC++;
                total++;
            } else {
                total++;
            }
        }
        output = GC/total;
        return output;
    }

// /* Download the file yeastGenome.txt. This file contains the entire yeast genome in FASTA format.
// Execute your GC_content method on the yeast genome and verify that the GC content of the yeast genome is 38%. 
// In a comment above your GC_content method, indicate the GC content of the Escherichia coli genome and the GC content of human chromosome 22. */

    public static String randomPermutation(String s) {
        LinkedList<Character> ll = new LinkedList<Character>(Help.stringToLinkedList(s));
        String output = "";
        Collections.shuffle(ll);
        output = Help.linkedListToString(ll);
        return output;

    }


    public static String randomSequence(int length, double GC_content) {
        double AT_content = 1 - GC_content;
        double numb_AT = length * AT_content;
        double numb_GC = length * GC_content;
        LinkedList<Character> ll = new LinkedList<Character>();
        String output = "";
        for (int i = 0; i < numb_AT; i++ ){
            if (Math.random() < .5){
                ll.add('T');
            } else {
                ll.add('A');
            }
        }
        for (int i = 0; i < numb_GC; i++ ){
            if (Math.random() < .5){
                ll.add('G');
            } else {
                ll.add('C');
            }
        }
        Collections.shuffle(ll);
        output = Help.linkedListToString(ll);
        return output;
    }

    public static String randomSampling(String s) {
        int length = s.length();
        double GC_content = GC_content(s);
        return randomSequence(length, GC_content);
    }

    public static char translateCodon(String s) {
        HashMap<String, String> map = new HashMap<String, String>(Help.codonMap());
        String str = map.get(s);
        char output = str.charAt(0);
        return output;
     }
        
    public static String translateORF(String s){
        String codon = "";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i<s.length();i+=3) {
            codon = String.valueOf(s.charAt(i)) + String.valueOf(s.charAt(i+1)) + String.valueOf(s.charAt(i+2));
            output.append(translateCodon(codon));
        }
        return output.toString();
    }

    public static void main(String[] args) {
    }
}