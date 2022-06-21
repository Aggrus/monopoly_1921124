package model;

/**
 * <p>
 * </p>
 * 
 * @author gugaz
 * @version 1.0 Created on May 17, 2022
 */
class PrisionCard
	extends AbstractCard
{

	PrisionCard(Integer id, boolean luck, String link) 
	{

		this.setId(id);
		this.setLuck(luck);
		this.setLink(link);
		this.setMoney(Long.valueOf(0));

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
		player.setInPrision( true );
		player.setBoardPosition(10);
	}

}
