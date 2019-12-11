import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ChessGUI extends JPanel
{
	public static int spaceSize = 70;
	public ChessBoard cb;
	private Piece p = null;
	
	public ChessGUI()
	{
		this.cb = ChessBoard.newGameBoard();
	}
	
	public ChessGUI(String filePath)
	{
		BufferedReader br = null;
		List<String> pieceLines = new ArrayList<>();
		List<List<String>> pieceSplit = new ArrayList<>();
		try 
		{
			br = new BufferedReader(new FileReader(filePath));
			String next = br.readLine();
			while (next != null)
			{
				pieceLines.add(next);
				next = br.readLine();
			}
			br.close();
		}
		catch (Exception e) 
		{
			throw new IllegalArgumentException();
		}
		for (String line : pieceLines)
		{
			List<String> splitString = new ArrayList<>();
			for (String s : line.split(","))
			{
				splitString.add(s);
			}
			pieceSplit.add(splitString);
		}
		Iterator<List<String>> iter = pieceSplit.iterator();

		Map<String, Color> colorMap = Map.of("BLACK", Color.BLACK, "WHITE", Color.WHITE);
		ChessBoard cb = new ChessBoard(colorMap.get(iter.next().get(0)));
		this.cb = cb;
		while (iter.hasNext())
		{
			List<String> list = iter.next();
			try 
			{
				if (list.size() == 5)
				{
					cb.addPiece((Piece)Class.forName(list.get(0)).getConstructor(
						int.class, int.class, Color.class, boolean.class, ChessBoard.class).newInstance(
						Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), colorMap.get(list.get(3)), Boolean.parseBoolean(list.get(4)), cb));
				}
				else
				{
					cb.addPiece((Piece)Class.forName(list.get(0)).getConstructor(
						int.class, int.class, Color.class, ChessBoard.class).newInstance(
						Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2)), colorMap.get(list.get(3)), cb));
				}
			} 
			catch (Exception e) 
			{
				System.out.println("failed adding piece from csv");
			}
		}
	}
	
	public void paintBackground(Graphics2D g2)
	{
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				Rectangle2D rec = new Rectangle(i * spaceSize, j * spaceSize, spaceSize, spaceSize);
				if ((i + j) % 2 == 0)
				{
					g2.setColor(Color.WHITE);
				}
				else
				{
					g2.setColor(Color.gray);
				}
				g2.fill(rec);
			}
		}
	}
	
	@Override 
	public Dimension getPreferredSize()
	{
		return new Dimension(spaceSize * 8, spaceSize * 8);
	}
	
	public void paintPieces(ChessBoard cb, Graphics2D g2)
	{
		for (Piece p : cb.whitePieces)
		{
			p.draw(g2, spaceSize);
		}
		for (Piece p : cb.blackPieces)
		{
			p.draw(g2, spaceSize);
		}
		
	}
	
	public void paintLegalMoves(Graphics2D g2)
	{
		if (p == null)
		{
			return;
		}
		else if (p.color != cb.turn)
		{
			return;
		}
		Color transparentBlue = new Color(0, 0, 255, 100);
		g2.setColor(transparentBlue);
		for (int row = 0; row < 8; row++)
		{
			for (int column = 0; column < 8; column++)
			{
				if(p.legalMove(row, column))
				{
					g2.fillRect(column * spaceSize, row * spaceSize, spaceSize, spaceSize);
					System.out.println(row + " " + column);
				}
			}
		}
	}
	
	public void setClickedPiece(Piece p)
	{
		this.p = p;
	}
	
	public void paintCheck(Graphics2D g2)
	{
		for (Piece p : cb.blackPieces)
		{
			if (p instanceof King)
			{
				if (((King) p).isCheck())
				{
					g2.setColor(new Color(255, 0, 0, 100));
					g2.fillRect(p.column * spaceSize, p.row * spaceSize, spaceSize, spaceSize);
				}
			}
		}
		for (Piece p : cb.whitePieces)
		{
			if (p instanceof King)
			{
				if (((King) p).isCheck())
				{
					g2.setColor(new Color(255, 0, 0, 100));
					g2.fillRect(p.column * spaceSize, p.row * spaceSize, spaceSize, spaceSize);
				}
			}
		}
	}
	
	public void paintStaleCheckmate(Graphics g2)
	{
		for (Piece p : cb.blackPieces)
		{
			if (p instanceof King)
			{
				if (((King) p).isStaleCheckmate())
				{
					g2.setFont(new Font("", Font.BOLD, spaceSize));
					g2.setColor(Color.RED);
					if (((King) p).isCheck())
					{
						g2.drawString("CHECKMATE!", 0, 3 * spaceSize);
						g2.drawString("WHITE WINS!", 0, 5 * spaceSize);
					}
					else
					{
						g2.drawString("STALEMATE!", 0, 5 * spaceSize);
					}
				}
			}
		}
		for (Piece p : cb.whitePieces)
		{
			if (p instanceof King)
			{
				if (((King) p).isStaleCheckmate())
				{
					g2.setFont(new Font("", Font.BOLD, spaceSize));
					g2.setColor(Color.RED);
					if (((King) p).isCheck())
					{
						g2.drawString("CHECKMATE!", 0, 3 * spaceSize);
						g2.drawString("BLACK WINS!", 0, 5 * spaceSize);
					}
					else
					{
						g2.drawString("STALEMATE!", 0, 5 * spaceSize);
					}
				}
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(new Font("", Font.BOLD, ChessGUI.spaceSize / 5));
		paintBackground(g2);
		paintPieces(cb, g2);
		paintLegalMoves(g2);
		paintCheck(g2);
		paintStaleCheckmate(g2);
		g2.setColor(new Color(0, 255, 0, 100));
		if (p != null)
		{
			g2.fillRect(p.column * spaceSize, p.row * spaceSize, spaceSize, spaceSize);
		}
	}
	
	public void saveGame(String filePath)
	{
		File file = Paths.get(filePath).toFile();
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(file, false));
			if (cb.turn == Color.WHITE)
			{
				bw.write("WHITE");
			}
			if (cb.turn == Color.BLACK)
			{
				bw.write("BLACK");
			}
			for (Piece p : cb.whitePieces)
			{
				bw.newLine();
				bw.write(p.getClass().getName() + "," + p.row + "," + p.column + "," + "WHITE");
				if (p instanceof Pawn || p instanceof King || p instanceof Rook)
				{
					bw.write("," + p.firstMove);
				}
			}
			for (Piece p : cb.blackPieces)
			{
				bw.newLine();
				bw.write(p.getClass().getName() + "," + p.row + "," + p.column + "," + "BLACK");
				if (p instanceof Pawn || p instanceof King || p instanceof Rook)
				{
					bw.write("," + p.firstMove);
				}
			}
			bw.close();
		} catch (IOException e1) {
			System.out.println("IOException in writeStringsToFile");
		}
	}
}