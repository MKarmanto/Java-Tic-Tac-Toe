import java.util.Scanner;

public class Ristinolla {
    static Scanner userInput = new Scanner(System.in);
    static char[][] gameBoard;
    static int winCondition;
    static boolean gameOver = false;
    static int size;
    static char winner;

    public static void main(String[] args) {       
        System.out.println("Tervetuloa ristinollaan!");
        size = getSizeFromUser();                       //Ask for size from user. Needs to be 3-16
        gameBoard = new char[size + 2][size + 2];       //Create gameBoard array based on user given size.
        winCondition = winCondition(size);              //Scale winCondition depending on size


        initializeBoard();                              //Initialize the array with gameboard chars
        printBoard();                                   //print the board

        while (gameOver == false) {                     //game loop
            getPlayerMove();                                                //Player sets his move
            printBoard();                                                   //Print the board
            winner = checkWinner(gameBoard);                                //Check if there's a winner
            announceWinner(winner);                                         //Who is the winner
            hasTied(gameBoard);                                             //Check for tie (Is board full?)
            getComputerMove();                                              //Get random computer move
            printBoard();                                                   //Print the board
            winner = checkWinner(gameBoard);                                //Check if there's a winner
            announceWinner(winner);                                         //Who is the winner
            hasTied(gameBoard);                                             //Check for tie (Is board full?)
        }
    }

    public static int getSizeFromUser() {                   //Ask for size from user. Needs to be 3-16
        System.out.print("Anna pelialueen koko (3-16): ");
        int size;

        while (true) {
            try { 
                size = Integer.parseInt(userInput.nextLine());

                if (size >= 3 && size <= 16 ) {
                    break;
                }
                System.out.println("Pelialueen pitää olla vähintään 3 ja enintään 16");
            } catch (Exception e) {
                System.out.println("Pelialueen pitää olla vähintään 3 ja enintään 16");
            }
        }

        return size;
    }

    public static int winCondition(int size) {              //Wincondtion scaling depending on size
        int winCondition = 0;
        if (size <= 16 && size > 12 ) {
            winCondition = 8;
        } else if (size <= 12 && size > 8) {
            winCondition = 6;
        } else if (size <= 8 && size > 4) {
            winCondition = 4;
        } else if (size <= 4 && size >= 3) {
            winCondition = 3;
        }
        return winCondition;
    }

    public static void getPlayerMove() {                    //Get valid player move
        int row;
        int col;
        while (true) {
            try {
                System.out.print("Anna siirron rivi: ");
                row = Integer.parseInt(userInput.nextLine());
                System.out.print("Anna siirron sarake: ");
                col = Integer.parseInt(userInput.nextLine());

                if (row <= 0 || col <= 0 || row > size || row > size ) {
                    System.out.println("Yritit laittaa merkkisi pelialueen ulkopuolelle. Yritä uudestaan.");
                } else if (gameBoard[row][col] != '_') {
                    System.out.println("Ruudussa on jo merkki. Yritä uudestaan.");
                } else {
                    gameBoard[row][col] = 'X';
                    break;
                }
            } catch (Exception e) {
                System.out.println("Jotain meni vikaan, yritä uudestaan.");
            }
        }
    }

    public static void getComputerMove() {                  //Get valid random computer move
        while (true) {
            int randomRow = (int) (Math.random() * size) + 1;
            int randomCol = (int) (Math.random() * size) + 1;

            if (randomRow <= size && randomCol <= size && gameBoard[randomRow][randomCol] != 'O' && gameBoard[randomRow][randomCol] != 'X' ) {
                gameBoard[randomRow][randomCol] = 'O';
                break;
            }
        }
    }
    
    public static void initializeBoard(){                   //Initialize the board
        for (int y = 0; y < gameBoard.length; y++) {
            for (int x = 0; x < gameBoard[y].length; x++) {
                if (x == 0 || y == 0 || x == gameBoard[y].length - 1|| y == gameBoard.length - 1) {
                    gameBoard[y][x] = '|';
                } else {
                    gameBoard[y][x] = '_';
                }
            }
        }
    }

    public static void printBoard() {                       //Print the boards
        System.out.print("\033[H\033[2J");
        for (int y = 0; y < gameBoard.length; y++) {
            for (int x = 0; x < gameBoard[y].length; x++) {
                System.out.print(gameBoard[y][x]);
            }
            System.out.println("");
        }
        System.out.println("Voittoon tarvittavat merkit: " + winCondition);
    }

    public static void announceWinner(char winner) {       //Announce the winner
        if (winner == 'X') {
            System.out.println("X voitti!");
            gameOver = true;
        } else if (winner == 'O') {
            System.out.println("O voitti!");
            gameOver = true;
        }
    }

    public static char checkWinner(char[][] gameBoard) { //Check winner row/col/diag/reverse diag
        boolean winner = false;
        char winnerChar = ' ';
        int counterForX = 0;
        int counterForO = 0;
        //Row wincheck
	    for(int i = 1; i < gameBoard.length; i++) {
			for(int j = 1; j < gameBoard[i].length; j++) {
                char value = gameBoard[i][j];
                if(winner == true) {
				    break;
			    } else if (value == 'X') {
				    counterForX++;
                    counterForO = 0;
                    if (counterForX == winCondition) {
                    winner = true;
                    winnerChar = 'X';
                    }
			    } else if (value == 'O') {
                    counterForO++;
                    counterForX = 0;
                    if (counterForO == winCondition) {
                        winner = true;
                        winnerChar = 'O';
                    }
                } else if (value == '_' || value == '|') {
                    counterForX = 0;
                    counterForO = 0;
				}
			}
		}
        //Column wincheck
	    for(int i = 1; i < gameBoard.length; i++) {
			for(int j = 1; j < gameBoard[i].length; j++) {
                char value = gameBoard[j][i];
                if(winner == true) {
				    break;
			    } else if (value == 'X') {
				    counterForX++;
                    counterForO = 0;
                    if (counterForX == winCondition) {
                        winner = true;
                        winnerChar = 'X';
                    }
			    } else if (value == 'O') {
                    counterForO++;
                    counterForX = 0;
                    if (counterForO == winCondition) {
                        winner = true;
                        winnerChar = 'O';
                    }
                } else if (value == '_' || value == '|') {
                    counterForX = 0;
                    counterForO = 0;
				}
			}
		}
        //Diagonal wincheck
        for(int i = 1; i < gameBoard.length; i++) {
			for(int j = 1; j < gameBoard[i].length; j++) {
                char value = gameBoard[j][j];
                if(winner == true) {
				    break;
			    } else if (value == 'X') {
				    counterForX++;
                    counterForO = 0;
                    if (counterForX == winCondition) {
                        winner = true;
                        winnerChar = 'X';
                    }
			    } else if (value == 'O') {
                    counterForO++;
                    counterForX = 0;
                    if (counterForO == winCondition) {
                        winner = true;
                        winnerChar = 'O';
                    }
                } else if (value == '_' || value == '|') {
                    counterForX = 0;
                    counterForO = 0;
				}
			}
		}
        //Reverse Diagonal wincheck
        for(int i = 1; i < gameBoard.length; i++) {
			for(int j = 1; j < gameBoard[i].length; j++) {
                char value = gameBoard[j][size + 1 - j];
                if(winner == true) {
				    break;
			    } else if (value == 'X') {
				    counterForX++;
                    counterForO = 0;
                    if (counterForX == winCondition) {
                        winner = true;
                        winnerChar = 'X';
                    }
			    } else if (value == 'O') {
                    counterForO++;
                    counterForX = 0;
                    if (counterForO == winCondition) {
                        winner = true;
                        winnerChar = 'O';
                    }
                } else if (value == '_' || value == '|') {
                    counterForX = 0;
                    counterForO = 0;
				}
			}
        }
        return winnerChar;
    }
    //Tasapelin tarkistus
    public static void hasTied(char[][] gameBoard) {
        boolean hasTied = true;
        for(int i = 0; i < gameBoard.length; i++) {
            for(int j = 0; j < gameBoard[i].length; j++) {
                if(gameBoard[i][j] == '_') {
                    hasTied = false;
                }
            }
        }
        if (hasTied == true) {
            System.out.print("Tasapeli!");
            gameOver = true; 
        }
    }
}