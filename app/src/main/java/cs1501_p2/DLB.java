package cs1501_p2;

import java.util.ArrayList;

public class DLB implements Dict    {

    //constants

    //The char that goes at the end of a valid word
    private final char wordEnd = '*';

    //This will be our running count variable
    private int counter;

    //This will be for the by-char search
    String nextChar;

    //Special constant for suggestRecursive, keeps track of what level in the DLB we're on
    private int suggestRecursiveLevel = 1;

    //The root of the DLB & the current node
    //cur will be used for traversal :p
    public DLBNode root;
    DLBNode cur;
    DLBNode traverse;

    //THIS METHOD IS WORKING !!!!
    public void add(String key){
        //adds a new word to the dictionary
        //takes in keyword to be added to dictionary

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

    //THIS METHOD WORKS!!
    public boolean contains(String key){
        //checks if the dictionary has this word
        //takes in the keyword that'll be searched for
        //returns true if found, and false if not found

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

    //THIS METHOD IS WORKING!!!
    public boolean containsPrefix(String pre){
        //checks if a string is a valid prefix for a word in the dictionary
        //takes in pre Prefix to use in the search
        //returns true if Prefix is valid, and false if it's not

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
        //searchs for a word one character at a time
        //next Next character to search for
        //returns an int depending on the result
            //-1: not a valid word or prefix
            //0: valid prefix, but not a valid word
            //1: valid word, but not a valid prefix to any other words
            //2: both valid word and a valid prefix to other words

        //This is all dependent on cur NOT changing between calls
        //unless searchByChar is reset()
        //God I Hope This Works.

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

        //for (int i = 0; i < this.count(); i++)  {
            //System.out.println(traverse().get(i));
        //}

        return returnInt;
    }

    public void resetByChar(){
        //Resets the state of the current by-character search

        //set traverse back to root so we're back to the base starting point
        traverse = root;

        //set nextChar to null
        nextChar = "";

        //set level counter back to 1
        suggestRecursiveLevel = 1;
    }

    /*

    public ArrayList<String> suggest(){
        //Suggest up to 5 words from the dictionary based on the current
        //by-character search.  Ordering should depend on the implementation.
        //returns a list of up to 5 words that are prefixed by
        //the current by-character search

        ArrayList<String> suggestions = new ArrayList<String>(5);

        //Go to & return the recursive helper function

        return (suggestRecursive(suggestions, nextChar, traverse));
    }

    private ArrayList<String> suggestRecursive(ArrayList<String> suggestions, String nextChar, DLBNode traverse){
        //Ok so here's how I want to implement this:
         //Check if the DLB dictionary actually has something in it
        //Check that the string/char is a prefix/word/whatever
        //Traverse to where the last char of the prefix is (aka node traverse)
        //travel straight down until we hit a word
            //Each travel, add that node's char to nextChar
        //Add that word to the array list
        //return when there's 5 words in the array list

        //Check to make sure the DLB has something in it
        if (traverse == null)
            return null;

        //If our array list is full, return what we have
        if (suggestions.size() == 5)
            return suggestions;

        //There's at least SOMETHING that we can return...

        //If the by-char search is a prefix OR word...
        if (containsPrefix(nextChar) == true || contains(nextChar) == true)   {

            //If the node is on a complete word, add it to suggestions
            if (traverse.getDown().getLet() == '*' && suggestions.size() < 5) {
                nextChar += traverse.getLet();
                if (contains(nextChar) == true)    {
                suggestions.add(suggestions.size(), nextChar);
                }
            }

            //If there's something below the prefix AND it's not *
            if (traverse.getDown() != null && traverse.getDown().getLet() != '*'){

                //Add that char to nextChar, change traverse, and recurse
                nextChar += traverse.getLet();
                suggestRecursive(suggestions, nextChar, (traverse.getDown()));
            }

            //If there's something to the right of this node, move right!
            if (traverse.getRight() != null)  {
                //Go to that down right node and recurse
                nextChar = nextChar.substring(0, nextChar.length() - 1);
                suggestRecursive(suggestions, nextChar, (traverse.getRight()));
            }

            //If there's a '*' below this node AND something to the right of '*', move to that new node
            if (traverse.getDown() != null && traverse.getDown().getLet() == wordEnd && traverse.getDown().getRight() != null)  {
                suggestRecursive(suggestions, nextChar, (traverse.getDown().getRight()));
            }
        }
        //return the populated suggestion array list
        return suggestions;
    }

    */

   public ArrayList<String> suggest(){
        //Suggest up to 5 words from the dictionary based on the current
        //by-character search.  Ordering should depend on the implementation.
        //returns a list of up to 5 words that are prefixed by
        //the current by-character search

        //Check to make sure the DLB has something in it
        if (traverse == null)
            return null;

        ArrayList<String> suggestions = new ArrayList<String>(5);

        //Go to & return the recursive helper function

        return (suggestRecursive(suggestions, nextChar, traverse, suggestRecursiveLevel));
    }

    private ArrayList<String> suggestRecursive(ArrayList<String> suggestions, String nextChar, DLBNode traverse, int suggestRecursiveLevel){

        //If our array list is full, return what we have
        if (suggestions.size() == 5)
            return suggestions;

        //There's at least SOMETHING that we can return...

        System.out.println("Current node letter is " + traverse.getLet());

        //System.out.println("Checking whether " + nextChar + " is a prefix or word");

        //If the by-char search is a prefix OR word...
        if (containsPrefix(nextChar) == true || contains(nextChar) == true)   {

            //System.out.println(nextChar + " is a prefix or word");

            //Special case -- if we're entering the recursive function on the first "level" of the DLB
            if (suggestRecursiveLevel == 1 && nextChar.length() == 1) {

                suggestRecursiveLevel += 1;
                
                //If the down is not a *, just move down and recurse

                //System.out.println("About to enter special if 1, traverse.getDown.getLet is " + traverse.getDown().getLet());

                if (traverse.getDown().getLet() != '*')  {

                    //System.out.println(nextChar + " entered special if 1, traversing down");

                    suggestRecursive(suggestions, nextChar, (traverse.getDown()), suggestRecursiveLevel);
                }

                //If the down is a * and suggestions isnt full, add that word to suggestions
                if (traverse.getDown().getLet() == '*' && suggestions.size() < 5) {

                    //System.out.println(nextChar + " entered special if 2, adding to suggestions");

                    if (contains(nextChar) == true)    {
                        suggestions.add(suggestions.size(), nextChar);
                    }

                    //then check to see if that word is a prefix. If so, traverse down right
                    if (traverse.getDown().getRight() != null)   {

                        //System.out.println(nextChar + " entered special if 3, traversing down and right");

                        suggestRecursive(suggestions, nextChar, traverse.getDown().getRight(), suggestRecursiveLevel);
                    }
                }
            }

            else {

                //If the down is not a *, just move down, add nextChar, and recurse
                if (traverse.getDown().getLet() != '*')  {

                    //System.out.println(nextChar + " entered if 1, adding " + traverse.getLet() + " and traversing down");

                    nextChar += traverse.getLet();
                    suggestRecursive(suggestions, nextChar, (traverse.getDown()), suggestRecursiveLevel);
                }
                
                //If the down is a * and suggestions isnt full, add that word to suggestions
                if (traverse.getDown().getLet() == '*' && suggestions.size() < 5) {

                    //System.out.println(nextChar + " entered if 2, adding " + traverse.getLet() + " and adding to suggestions");

                    nextChar += traverse.getLet();
                    if (contains(nextChar) == true)    {
                        suggestions.add(suggestions.size(), nextChar);
                    }

                    //then check to see if that word is a prefix. If so, traverse down right
                    if (traverse.getDown().getRight() != null)   {

                        //System.out.println(nextChar + " entered if 3, traversing down and right");

                        suggestRecursive(suggestions, nextChar, traverse.getDown().getRight(), suggestRecursiveLevel);
                    }
                }

                //We should only get to this point in the recursion if
                //We have to travel right before going down again

                if (traverse.getRight() != null) {
                    //sub the char we were at

                    //System.out.println(nextChar + " entered if 4, subtracting " + traverse.getLet() + " from " + nextChar + " and traversing right");

                    nextChar = nextChar.substring(0, nextChar.length() - 1);
                    suggestRecursive(suggestions, nextChar, traverse.getRight(), suggestRecursiveLevel);
                }
            }
        }

        suggestRecursiveLevel = 2;

        //return the populated suggestion array list
        return suggestions;
    }


    public ArrayList<String> traverse(){
        //List of all the words currently stored in the dictionary
        //returns a list of all valid words in the dictionary

        if (root == null)   {
            return null;
        }

        cur = root;

        ArrayList<String> traversal = new ArrayList<String>();

        String word = "";

        return(traverseRecursive(traversal, cur, root, word));
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

        //return the array list
        return traversal;
    }

    public int count(){
        //it just a count lol
        return counter;
    }
}