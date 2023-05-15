package cs1501_p2;

import java.util.ArrayList;
import java.util.HashMap;

public class UserHistory implements Dict    {

    //constants

    //The char that goes at the end of a valid word
    private final char wordEnd = '*';

    //This will be for the by-char search
    String nextChar = "";

    //The root of the DLB & the current node
    //cur will be used for traversal :p
    public DLBNode root;
    DLBNode cur;
    DLBNode traverse;

    //counter variable for count method
    private int counter;

    //Using hash map for frequencies
    HashMap<String, Integer> frequencies = new HashMap<String, Integer>();

	public void add(String key){
        //Add a new word to the dictionary
        //Takes in the key word to be added

        cur = root;

        //THIS IS WHAT IS BEING CHANGED FOR USERHISTORY
        //CHECK TO SEE IF THE KEY IS ALREADY INSERTED
        //IF YES, ADD 1 TO IT'S COUNTER

        if (contains(key) == true) {
            frequencies.put(key, ((frequencies.get(key) + 1)));
            return;
        }

        else if (contains(key) == false) {
            frequencies.put(key, 1);
        }

        cur = root;

        //Adding wordEnd so we know this is a valid word
        key += wordEnd;

        //Specifically for adding the first part of the DLB
        if (root == null)  {
            DLBNode newNode = new DLBNode(key.charAt(0));
            root = newNode;
            cur = root;

            for (int i=1; i < key.length(); i++)    {
                DLBNode newNodee = new DLBNode(key.charAt(i));
                cur.setDown(newNodee);
                cur = cur.getDown();
            }
        }

        else {
            //For loop that adds each char one-at-a-time
            for (int i=0; i < key.length(); i++){
                char character = key.charAt(i);

                //I'm separating the first char from the rest
                //I want to avoid having a null ref!
                if (i == 0)  {
                    if (cur.getLet() == character)   {
                        continue;
                    }
                    while (cur.getRight() != null)  {
                        cur = cur.getRight();
                        if (cur.getLet() == character)  {
                            break;
                        }
                    }
                    if (cur.getLet() == character)  {
                        continue;
                    }
                    else if (cur.getRight() == null)    {
                        DLBNode newNode = new DLBNode(character);
                        cur.setRight(newNode);
                        cur = cur.getRight();
                        continue;
                    }
                }

                if (i + 1 == key.length())  {
                    DLBNode newNode = new DLBNode(key.charAt(i));
                    cur.setDown(newNode);
                    counter += 1;
                    return;
                }

                //If there's nothing below, just straight up add it and move down
                if (cur.getDown() == null)  {
                    DLBNode newNode = new DLBNode(character);
                    cur.setDown(newNode);
                    cur = cur.getDown();
                    continue;
                }

                cur = cur.getDown();

                if (cur.getLet() == character)  {
                    continue;
                }

                else if (cur.getRight() == null)  {
                    DLBNode newNode = new DLBNode(character);
                    cur.setRight(newNode);
                    cur = cur.getRight();
                    continue;
                }

                while (cur.getRight() != null)  {
                    cur = cur.getRight();
                    if (cur.getLet() == character)  {
                        break;
                    }

                    else if (cur.getRight() == null)    {
                        DLBNode newNode = new DLBNode(character);
                        cur.setRight(newNode);
                        cur = cur.getRight();
                        break;
                    }
                }
            }
        }
        //Add 1 to counter because word has been successfully added
        counter += 1;
    }
    
	public boolean contains(String key){
        //Checks if the dictionary contains a word
        //Takes in the key word to be searched for
        //Returns true if there, false if not

        cur = root;

        //If there's nothing in the DLB, return FALSE
        if (root == null){
            return false;
        }

        //For loop that checks for each char one-at-a-time
        for(int i=0; i < key.length(); i++){
            char character = key.charAt(i);

            if (cur != null ) {

                //If we're already on the right character
                if (cur.getLet() == character)  {

                    //If there's nothing below this node, return FALSE
                    if (cur.getDown() == null)  {
                        return false; //false
                    }

                    //If the down character is *, return TRUE
                    else if (cur.getDown().getLet() == wordEnd && ((i + 1) == key.length())) {
                        return true;
                    }

                    //If there's something below the node, traverse down and CONTINUE
                    cur = cur.getDown();

                    //If there's something downright, then GO
                    if (cur.getLet() == wordEnd && cur.getRight() != null) {
                        cur = cur.getRight();
                    }

                    continue;
                }

                else if (cur.getRight() == null)    {
                    return false; //false
                    //dict is failing here
                }

                //Traverse the DLB...
                while (cur.getRight() != null)  {
                    cur = cur.getRight();

                    //If we found our guy, set cur to him and continue
                    if(cur.getLet() == character)   {

                        //If there's nothing below this node, return FALSE
                        if (cur.getDown() == null)
                            return false;

                        //If the down character is *, return TRUE
                        else if (cur.getDown().getLet() == wordEnd && ((i + 1) == key.length()))
                            return true;

                        //If there's something below the node, traverse down and CONTINUE
                        cur = cur.getDown();

                        //If there's something downright, then GO
                        if (cur.getLet() == wordEnd && cur.getRight() != null) {
                            cur = cur.getRight();
                        }

                        break;
                    }

                    else if (cur.getRight() == null)    {
                        return false;
                    }
                }
            }
        }
        return false;
    }

	public boolean containsPrefix(String pre){
        //Checks if a String is a valid prefix to other words
        //Takes in prefix to be searced for
        //Returns true if prefix, false if not

        cur = root;

        //For loop that checks for each char one-at-a-time
        for(int i=0; i < pre.length(); i++){
            char character = pre.charAt(i);

            //If there's nothing in the DLB, return FALSE
            if (cur == null)
                return false;

            if (cur != null ) {

                //If we're already on the right character
                if (cur.getLet() == character)  {

                    //If for loop is about to break, return TRUE
                    if ((i + 1) == pre.length())    {
                        if (cur.getDown() != null && cur.getDown().getLet() == wordEnd && cur.getDown().getRight() == null) {
                            return false;
                        }
                        return true;
                    }

                    //If there's something below the node, traverse down and CONTINUE
                    else if (cur.getDown() != null)  {
                        cur = cur.getDown();
                        continue;
                    }
                }

                //Traverse the DLB...
                while (cur.getRight() != null)  {
                    cur = cur.getRight();

                    //If we found our guy...
                    if(cur.getLet() == character)   {

                        //If for loop is about to break, return TRUE
                        if ((i + 1) == pre.length())    {
                            if (cur.getDown() != null && cur.getDown().getLet() == wordEnd && cur.getDown().getRight() == null) {
                                return false;
                            }
                            return true;
                        }

                        //If there's something below the node, traverse down and BREAK
                        else if (cur.getDown() != null)  {
                            cur = cur.getDown();
                            break;
                        }
                    }

                    //If the loop is about to break without finding our guy
                    //just return false because he aint there
                    else if (cur.getRight() == null)    {
                        return false;
                    }
                }
            }
        }
        return false;
    }

	public int searchByChar(char next){
        //Search for a word one character at a time
        //Takes in next char to search for
        //Returns int based on the current by-char search
            //-1: not valid word or prefix
            //0: valid prefix, but not a valid word
            //1: valid word, but not a valid prefix to other words
            //2: both a valid word a prefix to other words

        //changing next to a string so it can be used in other methods
        if (nextChar == null)   {
            String nextCharr = "";
            nextChar = nextCharr;
        }
        
        nextChar += next;

        if (nextChar.length() == 1) {
            cur = root;
            traverse = root;
        }

        cur = traverse;

        int returnInt = -1;

        //If there's nothing here, just leave
        //If not prefix and not word, return -1
        if (cur == null || (containsPrefix(nextChar) == false && contains(nextChar) == false))  {
            returnInt = -1;
        }

        //If prefix and not word, return 0
        else if (containsPrefix(nextChar) == true && contains(nextChar) == false)    {
            returnInt = 0;
        }
        
        //If not prefix and word, return 1
        else if (containsPrefix(nextChar) == false && contains(nextChar) == true)    {
            returnInt = 1;
        }
        
        //If prefix and word, return 2
        else if (containsPrefix(nextChar) == true && contains(nextChar) == true) {
            returnInt = 2;
        }

        traverse = cur;
        return returnInt;
    }

	public void resetByChar(){
        //Resets the state of the current by-char search

        //set traverse back to root so we're back to the base starting point
        traverse = root;

        //set nextChar to null
        nextChar = "";
    }

	public ArrayList<String> suggest(){
        //Suggests up to 5 words **BASED ON FREQUENCY** fron the current
        //by-char search.  Ordering depends on FREQUENCY
        //Returns array list of up to 5 words that are prefixed by current by-char search

        ArrayList<String> suggestions = new ArrayList<String>();

        ArrayList<String> actualSuggestions = new ArrayList<String>(5);
        suggestions = suggestRecursive(suggestions, nextChar, traverse);

        //Keeps track of the most frequent word & it's frequency
        String freqWord = "";
        int freqNum = 0;
        int suggestionsSize = suggestions.size();

        //Suggestions contains all POSSIBLE words to be suggested
        //Will now traverse to find the HIGHEST value and add that to suggestions
        while (actualSuggestions.size() != 5)   {
            if (actualSuggestions.size() == suggestionsSize) {
                break;
            }
            
            for (int i = 0; i < suggestions.size(); i++)    {
            
                if (frequencies.get(suggestions.get(i)) > freqNum) {

                    freqWord = suggestions.get(i);
                    freqNum = frequencies.get(freqWord);
                }
            }

            //Remove this word from suggestions so we don't get it again
            suggestions.remove(freqWord);
            
            //Add word to the actual suggestions
            actualSuggestions.add(freqWord);

            //resetting my variables
            freqNum = 0;
            freqWord = "";

        }
        return actualSuggestions;
    }

    private ArrayList<String> suggestRecursive(ArrayList<String> suggestions, String nextChar, DLBNode traverse){

        //System.out.println("I'm going in B)");

        //There's at least SOMETHING that we can return...

        //System.out.println("entering if 1 with: " + nextChar);
        //If the by-char search is a prefix OR word...
        if (containsPrefix(nextChar) == true || contains(nextChar) == true)   {

            //System.out.println("entering if 2 with: " + nextChar);
            //If the node is on a complete word, add it to suggestions
            if (traverse.getDown().getLet() == '*') {
                nextChar += traverse.getLet();
                suggestions.add(suggestions.size(), nextChar);
            }

            //System.out.println("entering if 3 with: " + nextChar);
            //If there's something below the prefix AND it's not *
            if (traverse.getDown() != null && traverse.getDown().getLet() != '*'){

                //Add that char to nextChar, change traverse, and recurse
                nextChar += traverse.getLet();
                suggestRecursive(suggestions, nextChar, (traverse.getDown()));
            }

            //System.out.println("entering if 4 with: " + nextChar);
            //If there's something to the right of this node, move right!
            if (traverse.getRight() != null)  {
                //Go to that down right node and recurse
                nextChar = nextChar.substring(0, nextChar.length() - 1);
                suggestRecursive(suggestions, nextChar, (traverse.getRight()));
            }

            //System.out.println("entering if 5 with: " + nextChar);
            //If there's a '*' below this node AND something to the right of '*', move to that new node
            if (traverse.getDown() != null && traverse.getDown().getLet() == wordEnd && traverse.getDown().getRight() != null)  {
                suggestRecursive(suggestions, nextChar, (traverse.getDown().getRight()));
            }
        }

        //System.out.println(nextChar);
        //return the populated suggestion array list
        return suggestions;
    }

	public ArrayList<String> traverse(){
        //Lists all the words currently stored in the dictionary
        //Returns an array list of all valid words in the dictionary

        if (root == null)   {
            return null;
        }

        cur = root;

        ArrayList<String> traversal = new ArrayList<String>();

        String word = "";

        return (traverseRecursive(traversal, cur, root, word));
    }

    private ArrayList<String> traverseRecursive(ArrayList<String> traversal, DLBNode cur, DLBNode root, String word){
        //doing an in-order traversal
        //I think this'll be easier tbh, because I can look for *'s first

        if (cur.getDown() != null)  {

            //If the down is not a *, just move down, add char, and recurse
            if (cur.getDown().getLet() != '*')  {
                word += cur.getLet();
                traverseRecursive(traversal, cur.getDown(), root, word);
            }
            
            //If the down is a *, add that word to the array list
            if (cur.getDown().getLet() == '*')  {
                word += cur.getLet();
                traversal.add(word);

                //then check to see if that word is a prefix
                if (cur.getDown().getRight() != null)   {
                    traverseRecursive(traversal, cur.getDown().getRight(), root, word);
                }
            }
        }

        //We should only get to this point in the recursion if
        //We have to travel right before going down again

        if (cur.getRight() != null) {
            //sub the char we were at
            word = word.substring(0, word.length() - 1);
            traverseRecursive(traversal, cur.getRight(), root, word);
        }

        //return the array list we have so far
        return traversal;
    }

	public int count(){
        //Count the number of words in the dictionary
        //Returns number of DISTINCT words in the dictionary

        return counter;
    }  
}

