package View;

import java.util.ArrayList;
import java.util.List;


class PlayerView
{

	public static List<Integer> getPlayerPos( final Integer playerNum, final Integer boardPos )
	{
		final List<Integer> position = new ArrayList<Integer>( 2 );
		Integer width = BoardView.squareSide, height = BoardView.squareSide;
		Integer posX = BoardView.posIni - width;
		Integer posY = BoardView.posIni - height;

		if ( ( boardPos % 10 ) != 0 )
		{
			if ( ( boardPos < 10 ) || ( ( boardPos > 20 ) && ( boardPos < 30 ) ) )
			{
				width = BoardView.otherSide;
			}
			else if ( ( ( boardPos > 10 ) && ( boardPos < 20 ) ) || ( boardPos > 30 ) )
			{
				height = BoardView.otherSide;
			}
		}

		// Slide em x até 10
		posX -= ( ( BoardView.otherSide ) * ( ( boardPos < 10 ) ? boardPos : 9 ) ) + 1;
		// Slide se for 10 ou maior
		if ( boardPos > 9 )
		{
			posX -= ( BoardView.squareSide + 1 );
		}
		// Slide se for maior que 10
		if ( boardPos > 10 )
		{
			posY -= ( ( BoardView.otherSide ) * ( ( boardPos < 20 ) ? ( boardPos - 10 ) : 9 ) );
		}
		if ( boardPos > 19 )
		{
			posY -= ( BoardView.squareSide + 1 );
		}
		if ( boardPos > 20 )
		{
			posX += ( ( BoardView.otherSide ) * ( ( boardPos < 30 ) ? ( boardPos - 21 ) : 9 ) )
				+ 1
				+ BoardView.squareSide;
		}
		if ( boardPos > 30 )
		{
			posY += ( ( BoardView.otherSide ) * ( ( boardPos < 40 ) ? ( boardPos - 31 ) : 9 ) )
				+ 1
				+ BoardView.squareSide;
		}

		Integer numRows, numColumns = 0;
		if ( ( ( boardPos > 10 ) && ( boardPos < 20 ) ) || ( boardPos > 30 ) )
		{
			numRows = 3;
			numColumns = 2;
		}
		else
		{
			numRows = 2;
			numColumns = 3;
		}
		position.add( posX + ( ( width / numRows ) * ( playerNum % numRows ) ) );

		position.add( posY + ( ( height / numColumns ) * ( playerNum % numColumns ) ) );
		position.add( width / numRows );
		position.add( height / numColumns );

		return position;
	}
}
