/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sheridancollege.project;

/**
 *
 * @author Ravneet
 */
public class Cards extends Card{
    private int rank,suit,values;
    
    public Cards(int suit,int values)
    {
        this.rank=values;
        this.suit=suit;
    }
    public int getValue()
    {
        if(rank>10)
        {
            values=10;
        }
        else if(rank==1)
        {
            values=11;
        }
        else
        {
            values=rank;
        }
        return values;
    }
public String toString()
{
    return Values.values()[rank]+" of "+Suit.values()[suit];
}
}
