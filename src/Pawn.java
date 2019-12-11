import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pawn extends Piece
{
	boolean passant = false;
	
	public Pawn(int row, int column, Color color, boolean firstMove, ChessBoard board)
	{
		super(row, column, color, board);
		this.firstMove = firstMove;
	}

	@Override
	public boolean move(int row, int column)
	{
		if (this.legalMove(row, column) && board.turn == this.color && Math.abs(this.row - row) == 1 && 
				Math.abs(this.column - column) == 1 && !this.board.hasPiece(row, column))
		{
			this.board.whitePieces.remove(this.board.pieceAt(this.row, column));
			this.board.blackPieces.remove(this.board.pieceAt(this.row, column));
			this.row = row;
			this.column = column;
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
		else if (this.legalMove(row, column) && board.turn == this.color && Math.abs(this.row - row) == 2)
		{
			this.passant = true;
			boolean ret = super.move(row, column);
			this.firstMove = false;
			return ret;
		}
		else
		{
			boolean moved = super.move(row, column);
			if (moved)
			{
				this.firstMove = false;
			}
			if (this.row == 0 || this.row == 7)
			{
				Game.pawnChange(this, ChessGUI.spaceSize, Game.getChessGUI());
			}
			return moved;
		}
	}
	
	@Override
	public void draw(Graphics2D g2, int size) 
	{
		BufferedImage bimg = null;
		try
		{
			if (this.color == Color.WHITE)
			{
				bimg = ImageIO.read(new File("files/wpawn.png"));
			}
			else
			{
				bimg = ImageIO.read(new File("files/bpawn.png"));
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
		if (this.column == column && !board.hasPiece(row, column)) //moving piece forward
		{
			if (this.color == Color.WHITE)
			{
				if (this.row - row == 1)
				{
					return true;
				}
				if (this.row - row == 2 && !board.hasPiece(this.row - 1, column) && this.firstMove)
				{
					return true;
				}
			}
			if (this.color == Color.BLACK)
			{
				if (this.row - row == -1)
				{
					return true;
				}
				if (this.row - row == -2 && !board.hasPiece(this.row + 1, column) && this.firstMove)
				{
					return true;
				}
			}
		}
		if (Math.abs(this.column - column) == 1 && this.hasEnemyPiece(row, column))	//taking piece diagonally
		{
			if (this.color == Color.WHITE)
			{
				if (this.row - row == 1)
				{
					return true;
				}
			}
			if (this.color == Color.BLACK)
			{
				if (this.row - row == -1)
				{
					return true;
				}
			}
		}
		if (Math.abs(this.column - column) == 1 && this.hasEnemyPiece(this.row, column))	//passant
		{
			Piece p = this.board.pieceAt(this.row, column);
			if (p instanceof Pawn)
			{
				if (((Pawn) p).passant)
				{
					if (this.color == Color.WHITE)
					{
						if (this.row - row == 1)
						{
							return true;
						}
					}
					else
					{
						if (this.row - row == -1)
						{
							return true;
						}
					}
				}
			}
			
		}
		return false;
	}
	
	@Override
	public boolean legalMove(int row, int column)
	{
		if (this.moveNormallyLegal(row, column))
		{
			int or = this.row;
			int oc = this.column;
			this.row = row;
			this.column = column;
			this.board.update();
			boolean kingInCheck = false;
			if (this.color == Color.WHITE)
			{
				for (Piece p : this.board.whitePieces)
				{
					if (p instanceof King)
					{
						kingInCheck = ((King) p).isCheck();
					}
				}
			}
			if (this.color == Color.BLACK)
			{
				for (Piece p : this.board.blackPieces)
				{
					if (p instanceof King)
					{
						kingInCheck = ((King) p).isCheck();
					}
				}
			}
			this.row = or;
			this.column = oc;
			this.board.update();
			return !kingInCheck;
		}
		return false;
		
	}
	
	
}