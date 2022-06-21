package model;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 17, 2022
 */
class DefaultCard
	extends AbstractCard
{

	DefaultCard(Integer id, boolean luck, String link, Long money) {
		this.setId(id);
		this.setLuck(luck);
		this.setLink(link);
		this.setMoney(money);
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see model.AbstractCard#cardRule()
	 */
	@Override
	public void cardRule( final Player player )
	{
		changeMoney( getGainOrLoss(), player );
	}
}
