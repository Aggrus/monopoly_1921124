package model;

import java.util.List;
import java.util.Optional;

import Controller.Observer.Observable;
import Controller.Observer.Observer;
import Controller.Observer.TileObserver;
import enums.TileColorEnum;
import enums.TileEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 8, 2022
 */

abstract class AbstractTile implements Observable
{

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the boardPosition.
	 * @see #boardPosition
	 */
	public Integer getBoardPosition()
	{
		return this.boardPosition;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the canPurchase.
	 * @see #canPurchase
	 */
	public boolean getCanPurchase()
	{
		return this.canPurchase;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the gorup.
	 * @see #group
	 */
	public TileColorEnum getGorup()
	{
		return this.group;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the owner.
	 * @see #owner
	 */
	public Player getOwner()
	{
		return this.owner;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the specialProperty.
	 * @see #specialProperty
	 */
	public TileEnum getSpecialProperty()
	{
		return this.specialProperty;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the value.
	 * @see #value
	 */
	public Long getValue()
	{
		return this.value;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param boardPosition
	 *            The boardPosition to set.
	 * @see #boardPosition
	 */
	public void setBoardPosition( final Integer boardPosition )
	{
		this.boardPosition = boardPosition;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param canPurchase
	 *            The canPurchase to set.
	 * @see #canPurchase
	 */
	protected void setCanPurchase( final boolean canPurchase )
	{
		this.canPurchase = canPurchase;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param gorup
	 *            The gorup to set.
	 * @see #group
	 */
	protected void setGorup( final TileColorEnum gorup )
	{
		this.group = gorup;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param owner
	 *            The owner to set.
	 * @see #owner
	 */
	protected void setOwner( final Player owner )
	{
		this.owner = owner;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param specialProperty
	 *            The specialProperty to set.
	 * @see #specialProperty
	 */
	protected void setSpecialProperty( final TileEnum specialProperty )
	{
		this.specialProperty = specialProperty;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param value
	 *            The value to set.
	 * @see #value
	 */
	protected void setValue( final Long value )
	{
		this.value = value;
	}

	public void add(Observer o)
	{
		this.add((TileObserver)o);
	}

	public void remove(Observer o)
	{
		this.remove((TileObserver)o);
	}

	public void update(Observer o)
	{
		update((TileObserver)o);
	}

	private void add(TileObserver o)
	{
		observer.add(o);
		o.notifyBoardPosition(this.boardPosition);
		o.notifyCanPurchase(this.canPurchase);
		o.notifyGroup(this.group);
		o.notifyOwner(this.owner.getColor());
		o.nofityValue(this.value);
	}

	private void remove(TileObserver o)
	{
		this.observer.remove( o );
	}

	private void update(TileObserver o)
	{
		Optional<TileObserver> observerFromList = observer.stream().filter(obs-> obs.equals(o)).findAny();
		if (observerFromList.isPresent())
		{
			observerFromList.get().notifyBoardPosition(this.boardPosition);
			observerFromList.get().notifyCanPurchase(this.canPurchase);
			observerFromList.get().notifyGroup(this.group);
			observerFromList.get().notifyOwner(this.owner.getColor());
			observerFromList.get().nofityValue(this.value);
		}
	}

	public boolean hasObserver(Observer o)
	{
		Optional<TileObserver> observerFromList = observer.stream().filter(obs-> obs.equals(o)).findAny();
		return observerFromList.isPresent();
	}


	public abstract void tileRule( Integer playerId );

	private Integer boardPosition;

	private boolean canPurchase;

	private TileColorEnum group;

	private Player owner;

	private TileEnum specialProperty;

	private Long value;

	protected List<TileObserver> observer;

}
