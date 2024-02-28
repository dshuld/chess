package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", pieceType=" + pieceType +
                '}';
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //call helper function for each piece type to evaluate possible moves
        return switch (this.pieceType) {
            case KING -> kingPieceMoves(board, myPosition);
            case QUEEN -> queenPieceMoves(board, myPosition);
            case ROOK -> rookPieceMoves(board, myPosition);
            case BISHOP -> bishopPieceMoves(board, myPosition);
            case KNIGHT -> knightPieceMoves(board, myPosition);
            case PAWN -> (this.getTeamColor()== ChessGame.TeamColor.WHITE) ?
                    whitePawnPieceMoves(board, myPosition) : blackPawnPieceMoves(board, myPosition);
        };
    }

    //// Individual piece move methods

    private Collection<ChessMove> kingPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();

        //check each space in 3x3 centered on king, allow capture or empty space, don't allow off-board move
        for (int row = pieceRow-1; row <= pieceRow+1; ++row) {
            for (int col = pieceCol-1; col <= pieceCol+1; ++col) {
                if ((row >= 1) && (row <= 8) && (col >= 1) && (col <= 8)) {
                    ChessPiece existingPiece = board.getPiece(new ChessPosition(row,col));
                    if (existingPiece==null ||
                            existingPiece.getTeamColor() != this.getTeamColor()) {
                        addMove(moves, pieceRow, pieceCol, row, col);
                    }
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> queenPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        //call helper methods for cardinal directions and diagonals
        checkNorthMoves(moves, board, myPosition);
        checkEastMoves(moves, board, myPosition);
        checkSouthMoves(moves, board, myPosition);
        checkWestMoves(moves, board, myPosition);
        checkNorthEastMoves(moves, board, myPosition);
        checkSouthEastMoves(moves, board, myPosition);
        checkSouthWestMoves(moves, board, myPosition);
        checkNorthWestMoves(moves, board, myPosition);

        return moves;
    }

    private Collection<ChessMove> rookPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        //call helper functions for cardinal directions
        checkNorthMoves(moves, board, myPosition);
        checkEastMoves(moves, board, myPosition);
        checkSouthMoves(moves, board, myPosition);
        checkWestMoves(moves, board, myPosition);

        return moves;
    }

    private Collection<ChessMove> bishopPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        //call helper functions for diagonals
        checkNorthEastMoves(moves, board, myPosition);
        checkSouthEastMoves(moves, board, myPosition);
        checkSouthWestMoves(moves, board, myPosition);
        checkNorthWestMoves(moves, board, myPosition);

        return moves;
    }

    private Collection<ChessMove> knightPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        ChessPiece existingPiece;

        for (int row = -2; row <= 2; ++row) {
            if (row != 0) {
                if ((pieceRow + row) >= 1 && (pieceRow + row) <= 8) {
                    int column = (abs(row) == 1) ? 2 : 1;
                    if ((pieceCol + column) >= 1 && (pieceCol + column) <= 8) {
                        existingPiece = board.getPiece(new ChessPosition(pieceRow+row,pieceCol+column));
                        if (existingPiece == null || existingPiece.getTeamColor() != this.getTeamColor()) {
                            addMove(moves,pieceRow,pieceCol,pieceRow+row,pieceCol+column);
                        }
                    }
                    column *= -1;
                    if ((pieceCol + column) <= 8 && (pieceCol + column) >= 1) {
                        existingPiece = board.getPiece(new ChessPosition(pieceRow+row,pieceCol+column));
                        if (existingPiece == null || existingPiece.getTeamColor() != this.getTeamColor()) {
                            addMove(moves,pieceRow,pieceCol,pieceRow+row,pieceCol+column);
                        }
                    }
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> whitePawnPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        ChessPiece existingPiece;

        if(pieceRow < 8) {
            //one space forward
            existingPiece = board.getPiece(new ChessPosition(pieceRow+1,pieceCol));
            if (existingPiece == null) {
                if(pieceRow == 7) {
                    addMove(moves, pieceRow, pieceCol, pieceRow+1, pieceCol, true);
                }
                else {
                    addMove(moves, pieceRow, pieceCol, pieceRow+1, pieceCol);
                }
                //two spaces forward (depends on one space forward)
                if (pieceRow == 2) {
                    existingPiece = board.getPiece(new ChessPosition(pieceRow+2, pieceCol));
                    if (existingPiece == null) {
                        addMove(moves, pieceRow, pieceCol, pieceRow+2, pieceCol);
                    }
                }
            }
            //left diagonal
            if (pieceCol > 1) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow + 1, pieceCol - 1));
                if (existingPiece != null &&
                        existingPiece.getTeamColor() != this.getTeamColor()) {
                    if (pieceRow == 7) {
                        addMove(moves, pieceRow, pieceCol, pieceRow+1, pieceCol-1, true);
                    } else {
                        addMove(moves, pieceRow, pieceCol, pieceRow+1, pieceCol-1);
                    }
                }
            }
            //right diagonal
            if (pieceCol < 8) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow + 1, pieceCol + 1));
                if (existingPiece != null &&
                        existingPiece.getTeamColor() != this.getTeamColor()) {
                    if (pieceRow == 7) {
                        addMove(moves, pieceRow, pieceCol, pieceRow+1, pieceCol+1, true);
                    } else {
                        addMove(moves, pieceRow, pieceCol, pieceRow+1, pieceCol+1);
                    }
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> blackPawnPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        ChessPiece existingPiece;

        if(pieceRow > 1) {
            //one space forward
            existingPiece = board.getPiece(new ChessPosition(pieceRow-1,pieceCol));
            if (existingPiece == null) {
                if(pieceRow == 2) {
                    addMove(moves, pieceRow, pieceCol, pieceRow-1, pieceCol, true);
                }
                else {
                    addMove(moves, pieceRow, pieceCol, pieceRow-1, pieceCol);
                }
                //two spaces forward (depends on one space forward)
                if (pieceRow == 7) {
                    existingPiece = board.getPiece(new ChessPosition(pieceRow-2, pieceCol));
                    if (existingPiece == null) {
                        addMove(moves, pieceRow, pieceCol, pieceRow-2, pieceCol);
                    }
                }
            }
            //left diagonal
            if (pieceCol > 1) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow - 1, pieceCol - 1));
                if (existingPiece != null &&
                        existingPiece.getTeamColor() != this.getTeamColor()) {
                    if (pieceRow == 2) {
                        addMove(moves, pieceRow, pieceCol, pieceRow-1, pieceCol-1, true);
                    } else {
                        addMove(moves, pieceRow, pieceCol, pieceRow-1, pieceCol-1);
                    }
                }
            }
            //right diagonal
            if (pieceCol < 8) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow - 1, pieceCol + 1));
                if (existingPiece != null &&
                        existingPiece.getTeamColor() != this.getTeamColor()) {
                    if (pieceRow == 2) {
                        addMove(moves, pieceRow, pieceCol, pieceRow-1, pieceCol+1, true);
                    } else {
                        addMove(moves, pieceRow, pieceCol, pieceRow-1, pieceCol+1);
                    }
                }
            }
        }

        return moves;
    }

    ////Helper methods for queen, rook, and bishop

    private void checkNorthMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //north
        for (int row = pieceRow+1; row <= 8; ++row) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(row, pieceCol));
            if(existingPiece==null) {
                addMove(moves, pieceRow, pieceCol, row, pieceCol);
            }
            else {
                if (existingPiece.getTeamColor() != this.getTeamColor()) {
                    addMove(moves, pieceRow, pieceCol, row, pieceCol);
                }
                break;
            }
        }
    }

    private void checkNorthEastMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //north-east
        for(int row = pieceRow+1, col = pieceCol+1; row <= 8 && col <= 8; ++row, ++col) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(row, col));
            if(existingPiece==null) {
                addMove(moves, pieceRow, pieceCol, row, col);
            }
            else {
                if (existingPiece.getTeamColor() != this.getTeamColor()) {
                    addMove(moves, pieceRow, pieceCol, row, col);
                }
                break;
            }
        }
    }

    private void checkEastMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //east
        for (int col = pieceCol+1; col <= 8; ++col) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(pieceRow, col));
            if(existingPiece==null) {
                addMove(moves, pieceRow, pieceCol, pieceRow, col);
            }
            else {
                if (existingPiece.getTeamColor() != this.getTeamColor()) {
                    addMove(moves, pieceRow, pieceCol, pieceRow, col);
                }
                break;
            }
        }
    }

    private void checkSouthEastMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //south-east
        for(int column = pieceCol+1, row = pieceRow-1; row >= 1 && column <= 8; --row, ++column) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(row, column));
            if(existingPiece==null) {
                addMove(moves, pieceRow, pieceCol, row, column);
            }
            else {
                if (existingPiece.getTeamColor() != this.getTeamColor()) {
                    addMove(moves, pieceRow, pieceCol, row, column);
                }
                break;
            }
        }
    }

    private void checkSouthMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //south
        for (int r = pieceRow-1; r >= 1; --r) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(r, pieceCol));
            if(existingPiece==null) {
                addMove(moves, pieceRow, pieceCol, r, pieceCol);
            }
            else {
                if (existingPiece.getTeamColor() != this.getTeamColor()) {
                    addMove(moves, pieceRow, pieceCol, r, pieceCol);
                }
                break;
            }
        }
    }

    private void checkSouthWestMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //south-west
        for(int r = pieceRow-1, c = pieceCol-1; r >= 1 && c >= 1; --r, --c) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(r, c));
            if(existingPiece==null) {
                addMove(moves, pieceRow, pieceCol, r, c);
            }
            else {
                if (existingPiece.getTeamColor() != this.getTeamColor()) {
                    addMove(moves, pieceRow, pieceCol, r, c);
                }
                break;
            }
        }
    }

    private void checkWestMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //west
        for (int c = pieceCol-1; c >= 1; --c) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(pieceRow, c));
            if(existingPiece==null) {
                addMove(moves, pieceRow, pieceCol, pieceRow, c);
            }
            else {
                if (existingPiece.getTeamColor() != this.getTeamColor()) {
                    addMove(moves, pieceRow, pieceCol, pieceRow, c);
                }
                break;
            }
        }
    }

    private void checkNorthWestMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //north-west
        for(int r = pieceRow+1, col = pieceCol-1; r <= 8 && col >= 1; ++r, --col) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(r, col));
            if(existingPiece==null) {
                addMove(moves, pieceRow, pieceCol, r, col);
            }
            else {
                if (existingPiece.getTeamColor() != this.getTeamColor()) {
                    addMove(moves, pieceRow, pieceCol, r, col);
                }
                break;
            }
        }
    }

    ////Helper functions for adding moves

    private void addMove(Collection<ChessMove> moves, int startRow, int startCol, int endRow, int endCol) {
        moves.add(new ChessMove(new ChessPosition(startRow,startCol),
                new ChessPosition(endRow,endCol), null));
    }

    private void addMove(Collection<ChessMove> moves, int startRow, int startCol,
                         int endRow, int endCol, boolean isPromoting) {
        if(isPromoting) {
            moves.add(new ChessMove(new ChessPosition(startRow, startCol),
                    new ChessPosition(endRow, endCol), PieceType.QUEEN));
            moves.add(new ChessMove(new ChessPosition(startRow, startCol),
                    new ChessPosition(endRow, endCol), PieceType.BISHOP));
            moves.add(new ChessMove(new ChessPosition(startRow, startCol),
                    new ChessPosition(endRow, endCol), PieceType.ROOK));
            moves.add(new ChessMove(new ChessPosition(startRow, startCol),
                    new ChessPosition(endRow, endCol), PieceType.KNIGHT));
        }
        else {
            //call other method for non-promotion
            addMove(moves, startRow, startCol, endRow, endCol);
        }
    }
}
