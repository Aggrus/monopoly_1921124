package model;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 17, 2022
 */
class FreeRideCard
	extends AbstractCard
{

	FreeRideCard(Integer id, String link)
	{
		setId(id);
		setLink(link);
		setLuck(true);
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
		player.setFreeRide( true );
	}

}
