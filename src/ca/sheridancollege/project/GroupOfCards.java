/**
 * SYST 17796 Project Winter 2019 Base code.
 * Students can modify and extend to implement their game.
 * Add your name as a modifier and the date!
 */
package ca.sheridancollege.project;

import com.sun.jdi.Value;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A concrete class that represents any grouping of cards for a Game.
 * HINT, you might want to subclass this more than once.
 * The group of cards has a maximum size attribute which is flexible for reuse.
 * @author dancye
 */
public class GroupOfCards 
{
   
    //The group of cards, stored in an ArrayList
    private ArrayList<Cards> deckOfCards;//represents a deck of cards
    private int size;//the size of the grouping

    public GroupOfCards(int givenSize)
    {
        size = givenSize;
    }
    
    /**
     * A method that will get the group of cards as an ArrayList
     */
     
    public GroupOfCards()
    {
    deckOfCards = new ArrayList<Cards>();//represents deck of cards
    for(int i=0; i<4; i++)
    {
        for(int j=1; j<=13; j++)
        {
            deckOfCards.add(new Cards(i,j));
        }
    }
    }
    
    public void shuffle()
    {
        Collections.shuffle(deckOfCards);
    }

    /**
     * @return the size of the group of cards
     */
    public int getSize() {
        return size;
    }

    /**
     * @param givenSize the max size for the group of cards
     */
    public void setSize(int givenSize) {
        size = givenSize;
    }
    public Cards drawCard()//draw card from deck
    {
        return deckOfCards.remove(0);
    }
    
}//end class
