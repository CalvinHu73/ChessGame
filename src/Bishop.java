import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Bishop extends Piece
{

	public Bishop(int row, int column, Color color, ChessBoard board)
	{
		super(row, column, color, board);
	}

	@Override
	public boolean moveNormallyLegal(int row, int column)
	{
		if (Math.abs(this.row - row) == Math.abs(this.column - column) && this.row != row)
		{
			if (this.row > row)
			{
				if (this.column > column)
				{
					for (int i = 1; i < Math.abs(this.row - row); i++)
					{
						if (this.board.hasPiece(this.row - i, this.column - i))
						{
							return false;
						}
					}
				}
				else
				{
					for (int i = 1; i < Math.abs(this.row - row); i++)
					{
						if (this.board.hasPiece(this.row - i, this.column + i))
						{
							return false;
						}
					}
				}
			}
			else
			{
				if (this.column > column)
				{
					for (int i = 1; i < Math.abs(this.row - row); i++)
					{
						if (this.board.hasPiece(this.row + i, this.column - i))
						{
							return false;
						}
					}
				}
				else
				{
					for (int i = 1; i < Math.abs(this.row - row); i++)
					{
						if (this.board.hasPiece(this.row + i, this.column + i))
						{
							return false;
						}
					}
				}
			}
			return !this.hasTeamPiece(row, column);
		}
		return false;
	}

	@Override
	public void draw(Graphics2D g2, int size) 
	{
		BufferedImage bimg = null;
		try
		{
			if (this.color == Color.WHITE)
			{
				bimg = ImageIO.read(new File("files/wbishop.png"));
			}
			else
			{
				bimg = ImageIO.read(new File("files/bbishop.png"));
			}
			Image img = bimg.getScaledInstance(size, size, Image.SCALE_FAST);
			g2.drawImage(img, size * column, size * row, null);
		}
		catch (IOException e)
		{
			System.out.println("Image does not exist");
		}
	}
	
}