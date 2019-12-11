import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game implements Runnable
{
	private static ChessGUI cg;
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Game());
	}

	@Override
	public void run() {
		final JFrame frame = new JFrame("Chess Game by Calvin");
		frame.setLayout(new BorderLayout());
		frame.setSize(8 * ChessGUI.spaceSize, 8 * ChessGUI.spaceSize);
		Game.cg = new ChessGUI("files/newGame.txt");
		JPanel buttonPanel = new JPanel();
		
		JPanel instruction = new JPanel(new GridLayout(12, 0));
		instruction.add(new JLabel("INSTRUCTIONS:"));
		instruction.add(new JLabel("The rules are the same as the classic game of chess"));
		instruction.add(new JLabel("Move by clicking."));
		instruction.add(new JLabel("Click to select piece and click again to attempt to move and unselect piece"));
		instruction.add(new JLabel("If move is illegal, you will have to reselect a piece to move, and it is still your turn."));
		instruction.add(new JLabel("When the pawn gets to the other side, a screen with Queen, Rook, Knight, and a Bishop will show."));
		instruction.add(new JLabel("Click on the piece you to swap the pawn with."));
		instruction.add(new JLabel("Due to technicalities, the piece on the board won't update until you click on the board again."));
		instruction.add(new JLabel("Click the new game button to start a new game."));
		instruction.add(new JLabel("Click the save game button to save game. This will erase the previous saved game. You can only save 1 game."));
		instruction.add(new JLabel("Click the load game button to load the last saved game."));
		instruction.add(new JLabel("Current player's turn is displayed on the top right in red."));
		JButton saveButton = new JButton("SAVE GAME");
		JButton loadButton = new JButton("LOAD SAVED GAME");
		JButton newGameButton = new JButton("NEW GAME");
		String turn = "";
		if (cg.cb.turn == Color.WHITE)
		{
			turn = "WHITE's TURN";
		}
		else
		{
			turn = "BLACK's TURN";
		}
		JLabel turnLabel = new JLabel(turn);
		turnLabel.setForeground(Color.RED);
		buttonPanel.add(saveButton);
		buttonPanel.add(loadButton);
		buttonPanel.add(newGameButton);
		buttonPanel.add(turnLabel);
		buttonPanel.setBackground(Color.DARK_GRAY);
		saveButton.addMouseListener(new saveButtonListener(cg));
		loadButton.addMouseListener(new loadButtonListener(cg));
		newGameButton.addMouseListener(new newGameButtonListener(cg));
		cg.addMouseListener(new ChessBoardListener(cg, turnLabel, buttonPanel));
		cg.addComponentListener(new ComponentAdapter() 
		{
		    public void componentResized(ComponentEvent componentEvent) 
		    {
		        int newSize = Math.min(componentEvent.getComponent().getHeight(), componentEvent.getComponent().getWidth());
		        ChessGUI.spaceSize = newSize / 8;
		        cg.repaint();
		    }
		});

		frame.add(cg, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.NORTH);
		frame.add(instruction, BorderLayout.EAST);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static ChessGUI getChessGUI()
	{
		return Game.cg;
	}
	
	public static void pawnChange(Pawn p, int size, ChessGUI cg)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		frame.add(panel);
		try {
			if (p.color == Color.BLACK)
			{
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("files/bqueen.png")).getScaledInstance(size, size, Image.SCALE_FAST))));
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("files/brook.png")).getScaledInstance(size, size, Image.SCALE_FAST))));
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("files/bbishop.png")).getScaledInstance(size, size, Image.SCALE_FAST))));
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("files/bknight.png")).getScaledInstance(size, size, Image.SCALE_FAST))));
			}
			else
			{
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("files/wqueen.png")).getScaledInstance(size, size, Image.SCALE_FAST))));
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("files/wrook.png")).getScaledInstance(size, size, Image.SCALE_FAST))));
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("files/wbishop.png")).getScaledInstance(size, size, Image.SCALE_FAST))));
				panel.add(new JLabel(new ImageIcon(ImageIO.read(new File("files/wknight.png")).getScaledInstance(size, size, Image.SCALE_FAST))));	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PawnChangeListener pcl = new PawnChangeListener(p, frame, cg);
		panel.addMouseListener(pcl);
		frame.pack();
		frame.setVisible(true);
	}
	
}


class saveButtonListener implements MouseListener {
	
	ChessGUI cg;
	
	saveButtonListener(ChessGUI cg)
	{
		this.cg = cg;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		cg.saveGame("files/savedGame.txt");
		System.out.println("Current game saved");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

class loadButtonListener implements MouseListener {
	
	ChessGUI cg;
	
	loadButtonListener(ChessGUI cg)
	{
		this.cg = cg;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		cg.cb = new ChessGUI("files/savedGame.txt").cb;
		cg.repaint();
		System.out.println("Game loaded");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

class newGameButtonListener implements MouseListener {
	
	ChessGUI cg;
	
	newGameButtonListener(ChessGUI cg)
	{
		this.cg = cg;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		cg.cb = new ChessGUI("files/newGame.txt").cb;
		cg.repaint();
		System.out.println("new game loaded");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

class ChessBoardListener implements MouseListener
{
	private ChessGUI cg;
	private JLabel jl;
	private JPanel jp;
	Piece p = null;
	
	public ChessBoardListener (ChessGUI cg, JLabel jl, JPanel jp)
	{
		this.cg = cg;
		this.jl = jl;
		this.jp = jp;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		System.out.println("clicked on game board");
		if (cg.cb.hasPiece(e.getY() / ChessGUI.spaceSize, e.getX() / ChessGUI.spaceSize) && p == null)
		{
			p = cg.cb.pieceAt(e.getY() / ChessGUI.spaceSize, e.getX() / ChessGUI.spaceSize);
			cg.setClickedPiece(p);
		}
		else if (p != null)
		{
			p.move(e.getY() / ChessGUI.spaceSize, e.getX() / ChessGUI.spaceSize);
			cg.setClickedPiece(null);
			p = null;
		}
		cg.repaint();
		if (cg.cb.turn == Color.WHITE)
		{
			jl.setText("WHITE's TURN");
		}
		else
		{
			jl.setText("BLACK's TURN");
		}
		jp.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

class PawnChangeListener implements MouseListener
{
	private Pawn p;
	private ChessGUI cg;
	private JFrame frame;
	
	public PawnChangeListener (Pawn p, JFrame frame, ChessGUI cg)
	{
		this.p = p;
		this.frame = frame;
		this.cg = cg;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		p.board.whitePieces.remove(p);
		p.board.blackPieces.remove(p);
		System.out.println("clicked on pop-up and chose piece to swap");
		if ((e.getX() / ChessGUI.spaceSize) == 0)
		{
			p.board.addPiece(new Queen(p.row, p.column, p.color, p.board));
		}
		else if (e.getX() / ChessGUI.spaceSize == 1)
		{
			p.board.addPiece(new Rook(p.row, p.column, p.color, false, p.board));
		}
		else if (e.getX() / ChessGUI.spaceSize == 2)
		{
			p.board.addPiece(new Bishop(p.row, p.column, p.color, p.board));
		}
		else //if (e.getX() / ChessGUI.spaceSize == 3)
		{
			p.board.addPiece(new Knight(p.row, p.column, p.color, p.board));
		}
		cg.repaint();
		System.out.println("here");
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}