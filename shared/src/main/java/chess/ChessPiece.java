package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
        switch (board.getPiece(myPosition).pieceType) {
            case KING -> {return kingPieceMoves(board, myPosition);}
            case QUEEN -> {return queenPieceMoves(board, myPosition);}
            case ROOK -> {return rookPieceMoves(board, myPosition);}
            case BISHOP -> {return bishopPieceMoves(board, myPosition);}
            case KNIGHT -> {return knightPieceMoves(board, myPosition);}
            case PAWN -> {return pawnPieceMoves(board, myPosition);}
        }
        return null;
    }

    ////

    private Collection<ChessMove> kingPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();

        for (int row = pieceRow-1; row <= pieceRow+1; ++row) {
            for (int col = pieceCol-1; col <= pieceCol+1; ++col) {
                if ((row >= 1) && (row <= 8) && (col >= 1) && (col <= 8)) {
                    ChessPiece existingPiece = board.getPiece(new ChessPosition(row,col));
                    if (existingPiece==null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(row,col), null));
                    }
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> queenPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

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

        checkNorthMoves(moves, board, myPosition);
        checkEastMoves(moves, board, myPosition);
        checkSouthMoves(moves, board, myPosition);
        checkWestMoves(moves, board, myPosition);

        return moves;
    }

    private Collection<ChessMove> bishopPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

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

        //north-most
        if (pieceRow+2 <= 8) {
            //left
            if (pieceCol-1 >= 1) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow+2,pieceCol-1));
                if (existingPiece == null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow+2,pieceCol-1), null));
                }
            }
            //right
            if (pieceCol+1 <= 8) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow+2,pieceCol+1));
                if (existingPiece == null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow+2,pieceCol+1), null));
                }
            }
        }
        //east-most
        if (pieceCol+2 <= 8) {
            //bottom
            if (pieceRow-1 >= 1) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow-1,pieceCol+2));
                if (existingPiece == null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow-1,pieceCol+2), null));
                }
            }
            //top
            if (pieceRow+1 <= 8) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow+1,pieceCol+2));
                if (existingPiece == null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow+1,pieceCol+2), null));
                }
            }
        }
        //south-most
        if (pieceRow-2 >= 1) {
            //left
            if (pieceCol-1 >= 1) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow-2,pieceCol-1));
                if (existingPiece == null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow-2,pieceCol-1), null));
                }
            }
            //right
            if (pieceCol+1 <= 8) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow-2,pieceCol+1));
                if (existingPiece == null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow-2,pieceCol+1), null));
                }
            }
        }
        //west-most
        if (pieceCol-2 >= 1) {
            //bottom
            if (pieceRow-1 >= 1) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow-1,pieceCol-2));
                if (existingPiece == null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow-1,pieceCol-2), null));
                }
            }
            //top
            if (pieceRow+1 <= 8) {
                existingPiece = board.getPiece(new ChessPosition(pieceRow+1,pieceCol-2));
                if (existingPiece == null || existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow+1,pieceCol-2), null));
                }
            }
        }

        return moves;
    }

    private Collection<ChessMove> pawnPieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        ChessPiece existingPiece;

        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            if(pieceRow < 8) {
                //one space forward
                existingPiece = board.getPiece(new ChessPosition(pieceRow+1,pieceCol));
                if (existingPiece == null) {
                    if(pieceRow == 7) {
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow+1,pieceCol), PieceType.QUEEN));
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow+1,pieceCol), PieceType.BISHOP));
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow+1,pieceCol), PieceType.ROOK));
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow+1,pieceCol), PieceType.KNIGHT));
                    }
                    else {
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow+1,pieceCol), null));
                    }
                    //two space forward (depends on one space forward)
                    if (pieceRow == 2) {
                        existingPiece = board.getPiece(new ChessPosition(pieceRow+2, pieceCol));
                        if (existingPiece == null) {
                            moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                    new ChessPosition(pieceRow+2,pieceCol), null));
                        }
                    }
                }
                //left diagonal
                if (pieceCol > 1) {
                    existingPiece = board.getPiece(new ChessPosition(pieceRow + 1, pieceCol - 1));
                    if (existingPiece != null &&
                            existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        if (pieceRow == 7) {
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol - 1), PieceType.QUEEN));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol - 1), PieceType.BISHOP));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol - 1), PieceType.ROOK));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol - 1), PieceType.KNIGHT));
                        } else {
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol - 1), null));
                        }
                    }
                }
                //right diagonal
                if (pieceCol < 8) {
                    existingPiece = board.getPiece(new ChessPosition(pieceRow + 1, pieceCol + 1));
                    if (existingPiece != null &&
                            existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        if (pieceRow == 7) {
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol + 1), PieceType.QUEEN));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol + 1), PieceType.BISHOP));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol + 1), PieceType.ROOK));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol + 1), PieceType.KNIGHT));
                        } else {
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow + 1, pieceCol + 1), null));
                        }
                    }
                }
            }
        }
        else {
            if(pieceRow > 1) {
                //one space forward
                existingPiece = board.getPiece(new ChessPosition(pieceRow-1,pieceCol));
                if (existingPiece == null) {
                    if(pieceRow == 2) {
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow-1,pieceCol), PieceType.QUEEN));
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow-1,pieceCol), PieceType.BISHOP));
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow-1,pieceCol), PieceType.ROOK));
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow-1,pieceCol), PieceType.KNIGHT));
                    }
                    else {
                        moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                new ChessPosition(pieceRow-1,pieceCol), null));
                    }
                    //two space forward (depends on one space forward)
                    if (pieceRow == 7) {
                        existingPiece = board.getPiece(new ChessPosition(pieceRow-2, pieceCol));
                        if (existingPiece == null) {
                            moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                                    new ChessPosition(pieceRow-2,pieceCol), null));
                        }
                    }
                }
                //left diagonal
                if (pieceCol > 1) {
                    existingPiece = board.getPiece(new ChessPosition(pieceRow - 1, pieceCol - 1));
                    if (existingPiece != null &&
                            existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        if (pieceRow == 2) {
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol - 1), PieceType.QUEEN));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol - 1), PieceType.BISHOP));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol - 1), PieceType.ROOK));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol - 1), PieceType.KNIGHT));
                        } else {
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol - 1), null));
                        }
                    }
                }
                //right diagonal
                if (pieceCol < 8) {
                    existingPiece = board.getPiece(new ChessPosition(pieceRow - 1, pieceCol + 1));
                    if (existingPiece != null &&
                            existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                        if (pieceRow == 2) {
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol + 1), PieceType.QUEEN));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol + 1), PieceType.BISHOP));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol + 1), PieceType.ROOK));
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol + 1), PieceType.KNIGHT));
                        } else {
                            moves.add(new ChessMove(new ChessPosition(pieceRow, pieceCol),
                                    new ChessPosition(pieceRow - 1, pieceCol + 1), null));
                        }
                    }
                }
            }
        }

        return moves;
    }

    ////

    private void checkNorthMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //north
        for (int row = pieceRow+1; row <= 8; ++row) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(row, pieceCol));
            if(existingPiece==null) {
                moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                        new ChessPosition(row,pieceCol), null));
            }
            else {
                if (existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(row,pieceCol), null));
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
                moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                        new ChessPosition(row,col), null));
            }
            else {
                if (existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(row,col), null));
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
                moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                        new ChessPosition(pieceRow,col), null));
            }
            else {
                if (existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow,col), null));
                }
                break;
            }
        }
    }

    private void checkSouthEastMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //south-east
        for(int row = pieceRow-1, col = pieceCol+1; row >= 1 && col <= 8; --row, ++col) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(row, col));
            if(existingPiece==null) {
                moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                        new ChessPosition(row,col), null));
            }
            else {
                if (existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(row,col), null));
                }
                break;
            }
        }
    }

    private void checkSouthMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //south
        for (int row = pieceRow-1; row >= 1; --row) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(row, pieceCol));
            if(existingPiece==null) {
                moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                        new ChessPosition(row,pieceCol), null));
            }
            else {
                if (existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(row,pieceCol), null));
                }
                break;
            }
        }
    }

    private void checkSouthWestMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //south-west
        for(int row = pieceRow-1, col = pieceCol-1; row >= 1 && col >= 1; --row, --col) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(row, col));
            if(existingPiece==null) {
                moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                        new ChessPosition(row,col), null));
            }
            else {
                if (existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(row,col), null));
                }
                break;
            }
        }
    }

    private void checkWestMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //west
        for (int col = pieceCol-1; col >= 1; --col) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(pieceRow, col));
            if(existingPiece==null) {
                moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                        new ChessPosition(pieceRow,col), null));
            }
            else {
                if (existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(pieceRow,col), null));
                }
                break;
            }
        }
    }

    private void checkNorthWestMoves(Collection<ChessMove> moves, ChessBoard board, ChessPosition myPosition) {
        int pieceRow = myPosition.getRow();
        int pieceCol = myPosition.getColumn();
        //north-west
        for(int row = pieceRow+1, col = pieceCol-1; row <= 8 && col >= 1; ++row, --col) {
            ChessPiece existingPiece = board.getPiece(new ChessPosition(row, col));
            if(existingPiece==null) {
                moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                        new ChessPosition(row,col), null));
            }
            else {
                if (existingPiece.getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    moves.add(new ChessMove(new ChessPosition(pieceRow,pieceCol),
                            new ChessPosition(row,col), null));
                }
                break;
            }
        }
    }
}
