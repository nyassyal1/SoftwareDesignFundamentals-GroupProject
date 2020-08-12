/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sheridancollege.project;
import java.util.*;
/**
 *
 * @author Ravneet
 */
public class BlackjackTester extends Game{
    
private static int cash;//cash the user bets with
private static int bet;//how much the user wants to bet
private static int AceCounter;//how many aces are in the user's hand
private static ArrayList<Cards> hand;//represents the user's hand
private static int handvalue;//the value of the user's hand
private static String name;//name of the user

public BlackjackTester(String name)
{
    super(name);
}
public static void main(String[] args){
    BlackjackTester bj=new BlackjackTester("name");

    System.out.println("What is your name?");
    Scanner scan = new Scanner(System.in);
    name = scan.nextLine();
    System.out.println("Hello, "+name+", lets play BlackJack!");
    System.out.println("How much cash do you want to start with?");
    Scanner money = new Scanner(System.in);
    cash = money.nextInt();
    System.out.println("You start with cash: "+cash);
    while(cash>0){
        GroupOfCards deck = new GroupOfCards();//initialize deck, dealer, hands, and set the bet.
        deck.shuffle();
        AceCounter=0;
        Dealer dealer = new Dealer(deck);
        List<Cards> hand = new ArrayList<>();
        hand.add(deck.drawCard());
        hand.add(deck.drawCard());
        System.out.println("How much would you like to bet?");
        bet=bj.Bet(cash);
        System.out.println("Cash:"+(cash-bet));
        System.out.println("Money on the table:"+bet);
        System.out.println("Here is your hand: ");
        System.out.println(hand);
        int handvalue = bj.calcHandValue(hand);
        System.out.println("The dealer is showing: ");
        dealer.showFirstCard();
        if(bj.hasBlackJack(handvalue) && dealer.hasBlackJack())//check if both the user and dealer have blackjack.
        {
            bj.Push();
        }
        else if(bj.hasBlackJack(handvalue))//check if the user has blackjack.
        {
            System.out.println("You have BlackJack!");
            System.out.println("You win 2x your money back!");
            cash=cash+bet;
            bj.declareWin();
        }
        else if(dealer.hasBlackJack())//check if the dealer has blackjack.
        {
            System.out.println("Here is the dealer's hand:");
            dealer.showHand();
            bj.Lose();
        }
        else
        {
            if(2*bet<cash)//check if the user can double down.
            {
                System.out.println("Would you like to double down?");//allows the user to double down.
                Scanner doubledown = new Scanner(System.in);
                String doubled = doubledown.nextLine();
                while(!bj.isyesorno(doubled))
                {
                    System.out.println("Please enter yes or no.");
                    doubled = doubledown.nextLine();
                }
                if(doubled.equals("yes"))
                {
                    System.out.println("You have opted to double down!");
                    bet=2*bet;
                    System.out.println("Cash:"+(cash-bet));
                    System.out.println("Money on the table:"+bet);
                }
            }
            System.out.println("Would you like to hit or stand?");//ask if the user will hit or stand
            Scanner hitorstand = new Scanner(System.in);
            String hitter = hitorstand.nextLine();
            while(!bj.isHitorStand(hitter))
            {
                System.out.println("Please enter 'hit' or 'stand'.");
                hitter = hitorstand.nextLine();
            }
            while(hitter.equals("hit"))//hits the user as many times as he or she pleases.
            {
                bj.Hit(deck, hand);
                System.out.println("Your hand is now:");
                System.out.println(hand);
                handvalue = bj.calcHandValue(hand);
                if(bj.checkBust(handvalue))//checks if the user busted
                {
                    bj.Lose();
                    break;
                }
                if(handvalue<=21 && hand.size()==5)//checks for a five card trick.
                {
                    bj.fivecardtrick();
                    break;
                }
                System.out.println("Would you like to hit or stand?");
                hitter = hitorstand.nextLine();
            }
            if(hitter.equals("stand"))//lets the user stand.
            {
                int dealerhand = dealer.takeTurn(deck);//takes the turn for the dealer.
                System.out.println("");
                System.out.println("Here is the dealer's hand:");
                dealer.showHand();
                if(dealerhand>21)//if the dealer busted, user wins.
                {
                    bj.declareWin();
                }
                else
                {
                    int you = 21-handvalue;//check who is closer to 21 and determine winner
                    int deal = 21-dealerhand;
                    if(you==deal)
                    {
                        bj.Push();
                    }
                    if(you<deal)
                    {
                        bj.declareWin();
                    }
                    if(deal<you)
                    {
                        bj.Lose();
                    }
                }
            }
        }
    System.out.println("Would you like to play again?");//ask if the user wants to keep going
    Scanner yesorno = new Scanner(System.in);
    String answer = yesorno.nextLine();
    while(!bj.isyesorno(answer))
            {
                System.out.println("Please answer yes or no.");
                answer = yesorno.nextLine();
            }
    if(answer.equals("no"))
    {
        break;
    }
}
    System.out.println("Your cash is: "+cash);//if user doesn't want to play or runs out of cash, either congratulates them on their winnings or lets them know
    if(cash==0)
    {
        System.out.println("You ran out of cash!");
    }
    else
    {
        System.out.println("Enjoy your winnings, "+name+"!");
    }
}
/*
 * Checks if the user has blackjack.
 */
public  boolean hasBlackJack(int handValue)
{
    if(handValue==21)
    {
        return true;
    }
    return false;
}
/*
 * Calculates the value of a player's hand.
 */
public  int calcHandValue(List<Cards> hand)
{
    Cards[] aHand = new Cards[]{};
    aHand = hand.toArray(aHand);
    int handvalue=0;
    for(int i=0; i<aHand.length; i++)
    {
        handvalue += aHand[i].getValue();
        if(aHand[i].getValue()==11)
        {
            AceCounter++;
        }
        while(AceCounter>0 && handvalue>21)
        {
            handvalue-=10;
            AceCounter--;
        }
    }
    return handvalue;
}
/*
 * Asks the user how much he or she would like to bet.
 */
public int Bet(int cash)
{
    Scanner sc=new Scanner(System.in);
    int bet=sc.nextInt();
    while(bet>cash)
    {
        System.out.println("You cannot bet more cash than you have!");
        System.out.println("How much would you like to bet?");
        bet=sc.nextInt();
    }
    return bet;
}
/*
 * Called if the user wins.
 */
public void declareWin()
{
    System.out.println("Congratulations, you win!");
    cash=cash+bet;
    System.out.println("Cash: "+cash);
}
/*
 * Called if the user loses.
 */
public void Lose()
{
    System.out.println("Sorry, you lose!");
    cash=cash-bet;
    System.out.println("Cash: "+cash);
}
/*
 * Called if the user pushes
 */
public void Push()
{
    System.out.println("It's a push!");
    System.out.println("You get your money back.");
    System.out.println("Cash: "+cash);
}
/*
 * Adds a card to user's hand and calculates the value of that hand. Aces are taken into account.
 */
public void Hit(GroupOfCards deck, List<Cards> hand)
{
    hand.add(deck.drawCard());
    Cards[] aHand = new Cards[]{};
    aHand = hand.toArray(aHand);
    handvalue = 0;
    for(int i=0; i<aHand.length; i++)
    {
        handvalue += aHand[i].getValue();
        if(aHand[i].getValue()==11)
        {
            AceCounter++;
        }
        while(AceCounter>0 && handvalue>21)
        {
            handvalue-=10;
            AceCounter--;
        }
    }
}
/*
 * Determines if a user has input hit or stand.
 */
public boolean isHitorStand(String hitter)
{
    if(hitter.equals("hit") || hitter.equals("stand"))
    {
        return true;
    }
    return false;
}
/*
 * Determines if a user has busted.
 */
public boolean checkBust(int handvalue)
{
    if(handvalue>21)
    {
        System.out.println("You have busted!");
        return true;
    }
    return false;
}
/*
 * Determines if a user has input yes or no.
 */
public boolean isyesorno(String answer)
{
    if(answer.equals("yes") || answer.equals("no"))
    {
        return true;
    }
    return false;
}
/*
 * Called if the user has a five card trick.
 */
public void fivecardtrick()
{
    System.out.println("You have achieved a five card trick!");
    declareWin();
}

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void declareWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
