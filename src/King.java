import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class King extends Piece
{
	
	public King(int row, int column, Color color, boolean firstMove, ChessBoard board)
	{
		super(row, column, color, board);
		this.firstMove = firstMove;
	}
	
	@Override
	public boolean move(int row, int column)
	{
		if (this.legalMove(row, column) && board.turn == this.color && 
				this.hasTeamPiece(row, column))
		{
			Piece castleRook = this.board.pieceAt(row, column);
			if (castleRook.column > this.column)
			{
				this.column = this.column + 2;
				castleRook.column = this.column - 1;
			}
			else
			{
				this.column = this.column - 2;
				castleRook.column = this.column + 1;
			}
			castleRook.firstMove = false;
			this.firstMove = false;
			if (this.board.turn == Color.WHITE)
			{
				this.board.turn = Color.BLACK;
			}
			else
			{
				this.board.turn = Color.WHITE;
			}
			this.board.update();
			return true;
		}
		else
		{
			boolean moved = super.move(row, column);
			if (moved)
			{
				this.firstMove = false;
			}
			return moved;
		}
	}

	@Override
	public boolean moveNormallyLegal(int row, int column)
	{
		if (this.row == row && this.column == column)
		{
			return false;
		}
		if (Math.abs(this.row - row) <= 1 && Math.abs(this.column - column) <= 1)
		{
			return !this.hasTeamPiece(row, column);
		}
		if (this.firstMove && this.hasTeamPiece(row, column))
		{
			if (this.board.pieceAt(row, column) instanceof Rook && 
					((Rook) this.board.pieceAt(row, column)).firstMove)
			{
				for (int i = Math.min(this.column, column); i <= Math.max(this.column, column); i++)
				{
					if (this.spaceInCheck(this.row, i))
					{
						return false;
					}
				}
				for (int i = Math.min(this.column, column) + 1; i < Math.max(this.column, column); i++)
				{
					if (this.board.hasPiece(this.row, i))
					{
						return false;
					}
				}
				return true;
			}
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
				bimg = ImageIO.read(new File("files/wking.png"));
			}
			else
			{
				bimg = ImageIO.read(new File("files/bking.png"));
			}
			Image img = bimg.getScaledInstance(size, size, Image.SCALE_FAST);
			g2.drawImage(img, size * column, size * row, null);
		}
		catch (IOException e)
		{
			System.out.println("Image does not exist");
		}
	}
	
	public boolean isStaleCheckmate()
	{
		for (int row = 0; row < 8; row++)
		{
			for (int column = 0; column < 8; column++)
			{
				if (this.color == Color.WHITE)
				{
					for (Piece p : this.board.whitePieces)
					{
						if (p.legalMove(row, column))
						{
							return false;
						}
					}
				}
				else
				{
					for (Piece p : this.board.blackPieces)
					{
						if (p.legalMove(row, column))
						{
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public boolean isCheck()
	{
		return spaceInCheck(this.row, this.column);
	}
}