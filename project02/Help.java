/* Keer Sun
Pset0
CS313 
resources:
https://www.w3schools.com/java/java_user_input.asp
https://zhanglab.ccmb.med.umich.edu/FASTA/
*/

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class Help{

    public static LinkedList<Character> stringToLinkedList(String s) {
        LinkedList<Character> output = new LinkedList<Character>();
        for (int i = 0; i < s.length(); i++) {
            output.add(s.charAt(i));
        }
        return output;
    }

    public static String linkedListToString(LinkedList<Character> ll) {
        String output = "";
        for (int i = 0; i < ll.size(); i++) {
            output += ll.get(i);
        }
        return output;
    }

    public static HashMap<String, String> codonMap(){
        HashMap<String, String> map = new HashMap<String, String>();
        boolean firstLine = true;
        String line = "";
        String codon = "";
        String AAC = "";
        File s = new File("translation.txt");

        try {
            Scanner file = new Scanner(s);
            while (file.hasNextLine()) { 
                line = file.nextLine();
                if (firstLine) {
                    firstLine = false;
                } else {
                    String[] parts = line.split("\\s+");
                    codon = parts[0];
                    AAC = parts[1];
                    map.put(codon, AAC);
                }
            }
         file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
        return map;
    }


    public static void main(String[] args) {
        System.out.println(codonMap());
    }
}

