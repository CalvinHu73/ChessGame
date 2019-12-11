import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Queen extends Piece
{

	public Queen(int row, int column, Color color, ChessBoard board)
	{
		super(row, column, color, board);
	}

	@Override
	public boolean moveNormallyLegal(int row, int column)
	{
		Bishop b = new Bishop(this.row, this.column, this.color, this.board);
		Rook r = new Rook(this.row, this.column, this.color, false, this.board);
		return b.moveNormallyLegal(row, column) || r.moveNormallyLegal(row, column);
	}
	
	@Override
	public void draw(Graphics2D g2, int size) 
	{
		BufferedImage bimg = null;
		try
		{
			if (this.color == Color.WHITE)
			{
				bimg = ImageIO.read(new File("files/wqueen.png"));
			}
			else
			{
				bimg = ImageIO.read(new File("files/bqueen.png"));
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