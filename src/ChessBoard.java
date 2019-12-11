import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ChessBoard
{
	Piece[][] board;
	Color turn;
	List<Piece> whitePieces;
	List<Piece> blackPieces;
	
	public ChessBoard(Color color)
	{
		this.board = new Piece[8][8];
		this.turn = color;
		this.whitePieces = new ArrayList<>();
		this.blackPieces = new ArrayList<>();
	}
	
	public void update()
	{
		this.board = new Piece[8][8];
		for (Piece p : whitePieces)
		{
			this.board[p.row][p.column] = p;
		}
		for (Piece p : blackPieces)
		{
			this.board[p.row][p.column] = p;
		}
	}
	
	public Piece[][] getBoard()
	{
		return board.clone();
	}
	
	public boolean hasPiece(int row, int column)
	{
		return (board[row][column] != null);
	}
	
	public Piece pieceAt(int row, int column)
	{
		return board[row][column];
	}
	
	public void addPiece(Piece p)
	{
		if (p.color == Color.WHITE)
		{
			this.whitePieces.add(p);
		}
		else
		{
			this.blackPieces.add(p);
		}
		this.update();
	}
	
	static ChessBoard newGameBoard()
	{
		ChessBoard gb = new ChessBoard(Color.WHITE);
		for (int i = 0; i < 8; i++)
		{
			gb.addPiece(new Pawn(6, i, Color.WHITE, true, gb));
			gb.addPiece(new Pawn(1, i, Color.BLACK, true, gb));
		}
		gb.addPiece(new King(7, 4, Color.WHITE, true, gb));
		gb.addPiece(new King(0, 4, Color.BLACK, true, gb));
		
		gb.addPiece(new Queen(7, 3, Color.WHITE, gb));
		gb.addPiece(new Queen(0, 3, Color.BLACK, gb));

		gb.addPiece(new Rook(7, 0, Color.WHITE, true, gb));
		gb.addPiece(new Rook(0, 0, Color.BLACK, true, gb));
		gb.addPiece(new Rook(7, 7, Color.WHITE, true, gb));
		gb.addPiece(new Rook(0, 7, Color.BLACK, true, gb));

		gb.addPiece(new Knight(7, 1, Color.WHITE, gb));
		gb.addPiece(new Knight(0, 1, Color.BLACK, gb));
		gb.addPiece(new Knight(7, 6, Color.WHITE, gb));
		gb.addPiece(new Knight(0, 6, Color.BLACK, gb));
		
		gb.addPiece(new Bishop(7, 2, Color.WHITE, gb));
		gb.addPiece(new Bishop(0, 2, Color.BLACK, gb));
		gb.addPiece(new Bishop(7, 5, Color.WHITE, gb));
		gb.addPiece(new Bishop(0, 5, Color.BLACK, gb));
		
		return gb;
	}
}