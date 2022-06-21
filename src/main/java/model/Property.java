package model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import Controller.Observer.Observer;
import Controller.Observer.PropertyObserver;
import Exception.IllegalRuleException;
import Exception.WrongPlayerException;
import enums.BuildingEnum;
import enums.TileColorEnum;
import enums.TileEnum;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 17, 2022
 */
class Property
	extends AbstractTile
{
	public Property( final  Integer boardPosition, Long value, final Long rent, final TileColorEnum color, final Long[] rentIncreases, Long buildingValue )
	{
		setBoardPosition(boardPosition);
		setValue(value);
		setRentIncreases( rentIncreases );
		setGorup( color );
		setRent( rent );
		setCanPurchase( true );
		setSpecialProperty( TileEnum.NONE );
		setOwner( null );
		setBuildingValue(buildingValue);
		this.firstRent = rent;
	}

	public Property (Property property)
	{
		setBoardPosition(property.getBoardPosition());
		setValue(property.getValue());
		setRentIncreases( property.getRentIncreases() );
		setGorup( property.getGorup() );
		setRent( property.getRent() );
		setCanPurchase( property.getCanPurchase() );
		setSpecialProperty( property.getSpecialProperty() );
		setOwner( property.getOwner() );
		setBuildingValue(property.getBuildingValue());
		this.firstRent = rent;
	}

	private void addBuilding( final Player player, final Building building )
		throws WrongPlayerException, IllegalRuleException
	{
		if ( getOwner().equals( player ) )
		{
			if ( checkAddBuilding( building ) )
			{
				this.buildings.add( building );
			}
			else
			{
				throw new IllegalRuleException( "Property owner does not own all properties of this group!" );
			}
		}
		else
		{
			throw new WrongPlayerException();
		}
		increaseRent();
	}

	public boolean buyBuilding( final Integer playerId, final Building building )
	{
		Player player = Game.getPlayerList().get(playerId);
		try
		{
			addBuilding( player, building );
		}
		catch ( final WrongPlayerException e )
		{
			return false;
		}
		catch (final IllegalRuleException c)
		{
			c.printStackTrace();
			return false;
		}

		player.loseMoney( building.getPrice() );
		return true;

	}

	public Long sellBuilding()
	{
		removeBuilding(this.buildings.get(this.buildings.size() - 1).getId());
		return buildingValue;
	}

	public void buyProperty( final Player player )
		throws IllegalRuleException
	{
		if ( null != getOwner() )
		{
			throw new IllegalRuleException( "Propriedade já tem dono!" );
		}
		player.loseMoney( getValue() );
		setOwner( player );
		setCanPurchase( false );
	}

	private boolean canBuild()
	{
		final Stream<AbstractTile> thisGroup = Game
			.getTiles()
			.stream()
			.filter( t -> t.getGorup().equals( getGorup() ) );
		return thisGroup.allMatch( t -> getBuildings().size() <= ( ( Property ) t ).getBuildings().size() );
	}

	private boolean canBuildHotel()
	{

		return ( ( getBuildings().size() > 1 ) && !hasHotel() );
	}

	private boolean checkAddBuilding( final Building building )
		throws IllegalRuleException
	{
		boolean canAddBuilding = canBuild() && checkGroupOwner();
		if ( building.getBuildingType().equals( BuildingEnum.HOTEL ) )
		{
			if ( !canBuildHotel() )
			{
				canAddBuilding = canAddBuilding && canBuildHotel();
			}
		}
		else
		{
			throw new IllegalRuleException( "Não pode adicionar construção!" );
		}
		return canAddBuilding;
	}

	private boolean checkGroupOwner()
	{
		final Player owner = getOwner();
		final Stream<AbstractTile> thisGroup = Game
			.getTiles()
			.stream()
			.filter( t -> t.getGorup().equals( getGorup() ) );
		;

		return ( thisGroup.allMatch( t -> t.getOwner().equals( owner ) ) );
	}

	public Long getBuildingValue() {
		return buildingValue;
	}

	public void setBuildingValue(Long buildingValue) {
		this.buildingValue = buildingValue;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the buildings.
	 * @see #buildings
	 */
	public List<Building> getBuildings()
	{
		return this.buildings;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the rent.
	 * @see #rent
	 */
	public Long getRent()
	{
		return this.rent;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the rentIncreases.
	 * @see #rentIncreases
	 */
	public Long[] getRentIncreases()
	{
		return this.rentIncreases;
	}

	private boolean hasHotel()
	{
		return getBuildings().stream().anyMatch( t -> t.getBuildingType().equals( BuildingEnum.HOTEL ) );
	}

	private void increaseRent()
	{
		if ( null == getBuildings() )
		{
			throw new IllegalStateException();
		}
		setRent( this.rentIncreases[getBuildings().size() - 1] );
	}

	public void removeBuilding( final Integer buildId )
	{
		final Optional<Building> building = this.buildings
			.stream()
			.filter( b -> b.getId().equals( buildId ) )
			.findFirst();

		if ( building.isPresent() )
		{
			this.buildings.remove( building.get() );
			if (getBuildings().size() != 0)
			{
				setRent( this.rentIncreases[getBuildings().size() - 1] );
			}
			else
			{
				setRent(firstRent);
			}
		}
		else
		{
			throw new NullPointerException( "Ineteger expected, recieved null" );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param rent
	 *            The rent to set.
	 * @see #rent
	 */
	public void setRent( final Long rent )
	{
		this.rent = rent;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param rentIncreases2
	 *            The rentIncreases to set.
	 * @see #rentIncreases
	 */
	private void setRentIncreases( final Long[] rentIncreases2 )
	{
		this.rentIncreases = rentIncreases2;
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
			player.loseMoney( getRent() );
		}
	}

	public void add(Observer o)
	{
		this.add((PropertyObserver)o);
	}

	public void remove(Observer o)
	{
		this.remove((PropertyObserver)o);
	}

	public void update(Observer o)
	{
		update((PropertyObserver)o);
	}

	private void add(PropertyObserver o)
	{
		observer.add(o);
		o.notifyBoardPosition(this.getBoardPosition());
		o.notifyCanPurchase(this.getCanPurchase());
		o.notifyGroup(this.getGorup());
		o.notifyOwner(this.getOwner().getColor());
		o.nofityValue(this.getValue());

		o.notifyHasHotel(hasHotel());
		o.nofityNumBuildings(buildings.size());
		o.nofityRent(rent);
	}

	private void remove(PropertyObserver o)
	{
		this.observer.remove( o );
	}

	private void update(PropertyObserver o)
	{
		Optional<PropertyObserver> observerFromList = observer.stream().filter(obs-> obs.equals(o)).findAny();
		if (observerFromList.isPresent())
		{
			o.notifyBoardPosition(this.getBoardPosition());
			o.notifyCanPurchase(this.getCanPurchase());
			o.notifyGroup(this.getGorup());
			o.notifyOwner(this.getOwner().getColor());
			o.nofityValue(this.getValue());

			o.notifyHasHotel(hasHotel());
			o.nofityNumBuildings(buildings.size());
			o.nofityRent(rent);
		}
	}

	public String genSaveString() {
		return String.format("casa %d, hotel %d, comprada %b, dono %d, aluguel %d", buildings.size(), hasHotel()?1:0, getCanPurchase(), getOwner().getColor() == null? -1: getOwner().getColor().getIndex(), getRent());
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}

	private Long buildingValue;

	private List<Building> buildings;

	private final Long firstRent;

	private Long rent;

	private Long[] rentIncreases;

	List<PropertyObserver> observer;
}
