package model;

import java.util.List;

import Exception.IllegalRuleException;
import enums.TileEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 20, 2022
 */
class CompanyTile
	extends AbstractTile
{

	/**
	 * <p>
	 * </p>
	 * @param boardPosition 
	 */
	public CompanyTile( final Long value, final Integer multiplier, Integer boardPosition )
	{
		setBoardPosition(boardPosition);
		setMultiplier( multiplier );
		setCanPurchase( true );
		setGorup( null );
		setOwner( null );
		setSpecialProperty( TileEnum.NONE );
		setValue( value );
	}

	public CompanyTile (CompanyTile company)
	{
		setBoardPosition(company.getBoardPosition());
		setValue(company.getValue());
		setMultiplier( multiplier );
		setGorup( company.getGorup() );
		setCanPurchase( company.getCanPurchase() );
		setSpecialProperty( company.getSpecialProperty() );
		setOwner( company.getOwner() );
	}

	public void buyCompany( final Player player )
		throws IllegalRuleException
	{
		if ( null != getOwner() )
		{
			throw new IllegalRuleException( "Propriedade já tem dono!" );
		}
		player.loseMoney( getValue() );
		setOwner( player );
		setCanPurchase( false );
		Game.setHasBought(true);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the multiplier.
	 * @see #multiplier
	 */
	private Integer getMultiplier()
	{
		return this.multiplier;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param multiplier
	 *            The multiplier to set.
	 * @see #multiplier
	 */
	private void setMultiplier( final Integer multiplier )
	{
		this.multiplier = multiplier;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param player
	 * @see model.AbstractTile#tileRule(model.Player)
	 */
	@Override
	public void tileRule( final Integer playerId )
	{
		Player player = Game.getPlayerList().get(playerId);
		if ( !player.equals( getOwner() ) && getOwner() != null)
		{
			List<Integer> dice = Dice.simpleRoll();
			dice.stream().forEach(dado -> System.out.printf("%d, ", dado));
			final Integer rollTotal = dice.stream().mapToInt( r -> r ).sum();
			player.loseMoney( Long.valueOf( rollTotal * getMultiplier() ) );
			this.getOwner().loseMoney(- Long.valueOf( rollTotal * getMultiplier() ));
		}
	}

	private Integer multiplier;

    public CharSequence genSaveString() {
        return String.format("dono %d, aVenda %b",getOwner() == null? -1: getOwner().getColor().getIndex(), getCanPurchase());
    }

}
