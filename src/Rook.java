import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Rook extends Piece
{
	
	public Rook(int row, int column, Color color, boolean firstMove, ChessBoard board)
	{
		super(row, column, color, board);
		this.firstMove = firstMove;
	}
	
	@Override
	public boolean move(int row, int column)
	{
		boolean moved = super.move(row, column);
		if (moved)
		{
			this.firstMove = false;
		}
		return moved;
	}
	
	@Override
	public void draw(Graphics2D g2, int size) 
	{
		BufferedImage bimg = null;
		try
		{
			if (this.color == Color.WHITE)
			{
				bimg = ImageIO.read(new File("files/wrook.png"));
			}
			else
			{
				bimg = ImageIO.read(new File("files/brook.png"));
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
		if (this.row == row && this.column == column)
		{
			return false;
		}
		if (this.row == row)
		{
			for (int i = Math.min(this.column, column) + 1; i < Math.max(this.column, column); i++)
			{
				if (this.board.hasPiece(row, i))
				{
					return false;
				}
			}
			return !this.hasTeamPiece(row, column);
		}
		if (this.column == column)
		{
			for (int i = Math.min(this.row, row) + 1; i < Math.max(this.row, row); i++)
			{
				if (this.board.hasPiece(i, column))
				{
					return false;
				}
			}
			return !this.hasTeamPiece(row, column);
		}
		return false;
	}
	
}