import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Knight extends Piece
{

	public Knight(int row, int column, Color color, ChessBoard board)
	{
		super(row, column, color, board);
	}
	
	@Override
	public void draw(Graphics2D g2, int size) 
	{
		BufferedImage bimg = null;
		try
		{
			if (this.color == Color.WHITE)
			{
				bimg = ImageIO.read(new File("files/wknight.png"));
			}
			else
			{
				bimg = ImageIO.read(new File("files/bknight.png"));
			}
			Image img = bimg.getScaledInstance(size, size, Image.SCALE_FAST);
			g2.drawImage(img, size * column, size * row, null);
		}
		catch (IOException e)
		{
			System.out.println("Image does not exist");
		}
	}

	@Override
	public boolean moveNormallyLegal(int row, int column)
	{
		if (this.hasTeamPiece(row, column))
		{
			return false;
		}
		if (Math.abs((this.row - row) * (this.column - column)) == 2)
		{
			return true;
		}
		return false;
	}
}