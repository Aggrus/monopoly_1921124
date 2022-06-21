package Exception;

/**
 * <p>
 * </p>
 * 
 * @author gugaz
 * @version 1.0 Created on May 17, 2022
 */
public class IllegalRuleException
	extends IllegalArgumentException
{

	/**
	 * <p>
	 * Field <code>serialVersionUID</code>
	 * </p>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * </p>
	 */
	public IllegalRuleException( final String message )
	{
		super( message );
	}
}
