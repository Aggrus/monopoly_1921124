package model;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 8, 2022
 */
abstract class AbstractCard
{

	public abstract void cardRule( Player player );

	protected void changeMoney( final Long amount, final Player player )
	{
		final Long newMoney = player.getMoney() + amount;
		player.setMoney( newMoney );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the description.
	 * @see #link
	 */
	public String getDescription()
	{
		return this.link;
	}

	public Long getGainOrLoss()
	{
		final Long amount = this.isLuck ? this.money : ( this.money * -1 );
		return amount;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the id.
	 * @see #id
	 */
	public Integer getId()
	{
		return this.id;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the money.
	 * @see #money
	 */
	public Long getMoney()
	{
		return this.money;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the isLuck.
	 * @see #isLuck
	 */
	protected boolean isLuck()
	{
		return this.isLuck;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param id
	 *            The id to set.
	 * @see #id
	 */
	protected void setId( final Integer id )
	{
		this.id = id;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param link
	 *            The link to set.
	 * @see #link
	 */
	protected void setLink( final String link )
	{
		this.link = link;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param isLuck
	 *            The isLuck to set.
	 * @see #isLuck
	 */
	protected void setLuck( final boolean isLuck )
	{
		this.isLuck = isLuck;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param money
	 *            The money to set.
	 * @see #money
	 */
	protected void setMoney( final Long money )
	{
		this.money = money;
	}

	private Integer id;

	private boolean isLuck;

	private String link;

	private Long money;

}
