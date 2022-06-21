package enums;

/**
 * <p>
 * </p>
 *
 * @author gugaz
 * @version 1.0 Created on May 8, 2022
 */
public enum PlayerColorEnum
{
	RED(0), BLUE(1), ORANGE(2), YELLOW(3), PURPLE(4),  GREY(5);

	private Integer index;

	PlayerColorEnum( final Integer index )
	{
		this.index = index;
	}

	public Integer getIndex()
	{
		return this.index;
	}
}
