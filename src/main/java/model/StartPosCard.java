package model;

/**
 * <p>
 * </p>
 * 
 * @author gugaz
 * @version 1.0 Created on May 17, 2022
 */
class StartPosCard
	extends AbstractCard
{
	StartPosCard(Integer id, String link, Long money)
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
		player.setBoardPosition( 0 );
		changeMoney( getGainOrLoss(), player );
	}

}
