package model;

import enums.BuildingEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 8, 2022
 */
class Building
{

	public Building(Integer boardPosition, BuildingEnum buildingType, Player owner, Long price,  AbstractTile property)
	{
		setBoardPosition(boardPosition);
		setBuildingType(buildingType);
		setOwner(owner);
		setPrice(price);
		setProperty(property);
	}

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
	 * @return Returns the buildingType.
	 * @see #buildingType
	 */
	public BuildingEnum getBuildingType()
	{
		return this.buildingType;
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
	 * @return Returns the price.
	 * @see #price
	 */
	public Long getPrice()
	{
		return this.price;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the property.
	 * @see #property
	 */
	public AbstractTile getProperty()
	{
		return this.property;
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
	 * @param buildingType
	 *            The buildingType to set.
	 * @see #buildingType
	 */
	public void setBuildingType( final BuildingEnum buildingType )
	{
		this.buildingType = buildingType;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param id
	 *            The id to set.
	 * @see #id
	 */
	public void setId( final Integer id )
	{
		this.id = id;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param owner
	 *            The owner to set.
	 * @see #owner
	 */
	public void setOwner( final Player owner )
	{
		this.owner = owner;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param price
	 *            The price to set.
	 * @see #price
	 */
	public void setPrice( final Long price )
	{
		this.price = price;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param property
	 *            The property to set.
	 * @see #property
	 */
	public void setProperty( final AbstractTile property )
	{
		this.property = property;
	}

	private Integer boardPosition;

	private BuildingEnum buildingType;

	private Integer id;

	private Player owner;

	private Long price;

	private AbstractTile property;

}
