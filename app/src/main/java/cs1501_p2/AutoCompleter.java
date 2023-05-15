package cs1501_p2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AutoCompleter implements AutoComplete_Inter    {

    String uhist;
    String dict;
    DLB dlb;
    UserHistory uh;

    HashMap<String, Integer> frequencies = new HashMap<String, Integer>();
    
    public ArrayList<String> nextChar(char next){
        //produce up to 5 suggestions based on the current word the user has
        //entered.  These suggestions should be pulled first from the user history 
        //dictionary then from the initial dictionary.  Any words pulled from user
        //history should be ordered by frequency of use.  Any words pulled from
        //the iniial dictionary should be in ascending order by their character
        //value ("ASCIIbetical" order)

        //takes in next char the user just entered
        //returns array list of up to 5 words prefixed by cur
        
        dlb.searchByChar(next);

        ArrayList<String> uhSuggestions = new ArrayList<String>(5);
        ArrayList<String> dlbSuggestions = new ArrayList<String>(5);
        
        if (uh.count() != 0)    {
            uh.searchByChar(next);
            uhSuggestions = uh.suggest();
        }

        if (uhSuggestions.size() != 5)    {
            dlbSuggestions = dlb.suggest();
        }

        int i = 0;

        while (uhSuggestions.size() != 5 && i < dlbSuggestions.size())   {
            if (uhSuggestions.contains(dlbSuggestions.get(i)) != true)  {
                uhSuggestions.add(dlbSuggestions.get(i));
            }
            i++;
        }
        return uhSuggestions;
    }

    public void finishWord(String cur){
        //process the user having selected the current word
        //takes in cur String representing the text the user has entered so far
        uh.add(cur);
        if (frequencies.containsKey(cur) == true){
            frequencies.put(cur, frequencies.get(cur) + 1);
        }
        else   {
            frequencies.put(cur, 1);
        }
        uh.resetByChar();
        dlb.resetByChar();
    }

    public void saveUserHistory(String fname){
        //Save the state of the user history to a file
        //takes in fname String filename to write history state to

        try {
            FileWriter saveuh = new FileWriter(new File(fname));

            ArrayList<String> uhTraverse = new ArrayList<String>();
            uhTraverse = uh.traverse();

            for (int i = 0; i < uhTraverse.size(); i++) {
                int j = frequencies.get(uhTraverse.get(i));
                for (int k = 0; k < j; k++) {
                    saveuh.write(uhTraverse.get(i) + "\n");
                }
            }
            saveuh.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    //constructors
    AutoCompleter(String dict, String uhist)    {
        this.dict = dict;
        this.uhist = uhist;
        DLB dlb = new DLB();
        try (Scanner dlbs = new Scanner(new File(dict))) {
            while (dlbs.hasNext()) {
                dlb.add(dlbs.nextLine());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.dlb = dlb; 
        UserHistory uh = new UserHistory();
        try (Scanner uhs = new Scanner(new File(uhist))) {
            while (uhs.hasNext()) {
                System.out.println("adding 1");
                uh.add(uhs.nextLine());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.uh = uh;
    }

    AutoCompleter(String dict)  {
        this.dict = dict;
        DLB dlb = new DLB();
        try (Scanner dlbs = new Scanner(new File(dict))) {
            while (dlbs.hasNext()) {
                dlb.add(dlbs.nextLine());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.dlb = dlb;
        UserHistory uh = new UserHistory();
        if (uh.count() != 0){
        try (Scanner uhs = new Scanner(new File(uhist))) {
            while (uhs.hasNext()) {
                uh.add(uhs.nextLine());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        }
        this.uh = uh;
    }
}