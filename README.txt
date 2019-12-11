=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: calvinhu
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

==============
=: Overview :=
==============

This is a chess game that I coded for my final project in CIS120 at the 
University of Pennsylvania. This code is not perfect by any means. For example,
I should have used get methods and made instance variables private. If you find
any bugs with the functionality of the game, please email me at 
calvinhu.73@gmail.com so I can fix it. Thank you and enjoy! :)


===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Appropriately modeling state using 2-D arrays
		I used a 8x8 array to model the chess board. 
		This is appropriate because a chess board is labeled by rows and columns.
		I can label positions of pieces with rows and columns on the board easily
		through a 8x8 2-D array
  2. File I/O: using I/O to parse a novel file format.
		I used writers to write state of games to a text file in order
		to save it. I used readers to read the files and load a game state.
		This is appropriate because one might want to save a game to continue later.
  3. Using inheritance/subtyping for dynamic dispatch.
		I used an abstract class Piece and extended the different pieces from this
		class. This is appropriate because the different pieces share a lot of
		instance variables and methods. I chose abstract class over interface, 
		because the implementation of some methods is the same for all classes.
  4. Complex game logic
  		Chess has complex game logic. Each piece has a different way to move. There
  		are special situations like en passant, check, checkmate, and castling.
  		So this makes complex game logic appropriate for my game.
		

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  Pawn, Rook, Knight, Bishop, King, Queen classes: represents chess pieces and holds
  properties, such as positions and ways that they move.
  Piece.java : the basic structure of all the pieces above.
  ChessBoard.java : represents the chess board and with the different pieces.
  ChessGUI.java : draws the chess board to a JPanel.
  ChessMain.java : displays chess board, buttons, and everything onto the JFrame.
  Also adds mouse listeners so the user can control the from the display.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
	Often times, I would need access to properties that I couldn't get access to.
	To solve this problem, I added objects into constructors of other objects,
	such as a ChessBoard to the Piece constructor.
	I found difficulty implementing the stale/checkmate method; my original code was
	faulty and 100+ lines. I rewrote it later when I had more methods implemented and
	brought the line count down a lot and made it simpler.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
	Going off of the answer to the previous question, I think the separation
	of functionality can be improved. The Piece constructor probably doesn't 
	need a ChessBoard argument. Also I often access instance variable directly instead
	of doing it through a get method, which would prevent unintended changes to 
	the game state. Further improvement the the game would be to redesign the classes
	so that objects wouldn't be arguments to constructors of other objects unless
	it made sense. I would also use accessor methods to get values of instance 
	variables instead of directly accessing them.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  I used java libraries.
  Chess piece images: https://commons.wikimedia.org/wiki/Category:PNG_chess_pieces/Standard_transparent
  I googled how to draw images, display multiple windows, create transparent colors, and other
  technicalities. Most of the answers I used were found on stackoverflow.
