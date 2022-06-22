package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 20, 2022
 */
class Dice
{

	private static Dice dice;

	private static Integer diceOne;

	final private static Integer diceSize = 6;

	private static Integer diceTwo;

	private static List<Integer> die;

	/**
	 * <p>
	 * </p>
	 */
	public Dice()
	{
		diceOne = 0;
		diceTwo = 0;
		die = new ArrayList<Integer>();
	}

	// Get last dice roll
	public static List<Integer> getDie()
	{
		return die;
	}

	public static Dice getInstance()
	{
		if ( null == dice )
		{
			dice = new Dice();
		}
		return dice;
	}

	private static Integer getRoll()
	{
		final Random roll = new Random();
		return 1 + roll.nextInt( diceSize - 1 );
	}

	public static void setDice( final Integer diceOne, final Integer diceTwo )
	{
		Dice.diceOne = diceOne;
		Dice.diceTwo = diceTwo;
	}

	// Set last dice roll
	public void setDie( final List<Integer> die )
	{
		die.forEach(result -> Dice.die.add(result));
	}

	public static ArrayList<Integer> simpleRoll()
	{
		final ArrayList<Integer> dice = new ArrayList<Integer>();

		if ( diceOne.equals( 0 ) && diceTwo.equals( 0 ) )
		{
			diceOne = getRoll();
			diceTwo = getRoll();
		}
		dice.add( diceOne );
		dice.add( diceTwo );

		diceOne = diceTwo = 0;
		getInstance().setDie( dice );
		return dice;
	}

	public ArrayList<Integer> moveRoll()
	{
		final ArrayList<Integer> roll = simpleRoll();
		while ( roll.get( roll.size() - 1 ).equals( roll.get( roll.size() - 2 ) ) && ( roll.size() < 7 ) )
		{
			roll.addAll( simpleRoll() );
		}
		setDie( roll );
		return roll;
	}
}
