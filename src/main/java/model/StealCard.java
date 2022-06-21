package model;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 17, 2022
 */
public class StealCard
	extends AbstractCard
{

	StealCard(Integer id, String link, Long money)
	{
		setId(id);
		setLuck(true);
		setLink(link);
		setMoney(money);
	}
	/**
	 * <p>
	 * </p>
	 *
	 * @param player
	 * @see model.AbstractCard#cardRule(model.Player)
	 */
	@Override
	public
	void cardRule( final Player player )
	{
		Game.getPlayerList().stream().forEach( p -> {
			if ( p.equals( player ) )
			{
				changeMoney( getGainOrLoss(), p );
			}
			else
			{
				changeMoney( getGainOrLoss() * ( -1 ), p );
			}
		} );
	}

}
