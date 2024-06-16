package models;

import java.util.Scanner;

public class HumanPlayer extends Player{

    public Scanner scanner;

    public HumanPlayer(String name, int id, char symbol, PlayerType playerType, Scanner scanner) {
        super(name, id, symbol, playerType);
        this.scanner = scanner;
    }

    @Override
    public Cell makeMove(Board board) {
        System.out.println(this.getName() + ", Its your turn to enter the row and column: ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();

        //validate the inputs
        while (!validateRowAndCol(row, col, board)) {
            System.out.println(this.getName() + ", Its an invalid input, please re-enter again ");
             row = scanner.nextInt();
             col = scanner.nextInt();
        }

        //If its a valid move
        Cell cell = board.getBoard().get(row).get(col);
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(this);
        return cell;
    }

    private boolean validateRowAndCol(int row, int col, Board board) {
        if (row >= board.getDimension() || row < 0){
            return false;
        }
        if (col >= board.getDimension() || col < 0){
            return false;
        }

        if (CellState.FILLED.equals(board.getBoard().get(row).get(col).getCellState())){
            return false;
        }
        return true;
    }


}
