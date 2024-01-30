package game2048logic;

import game2048rendering.Board;
import game2048rendering.Side;
import game2048rendering.Tile;
import net.sf.saxon.functions.ConstantFunction;

import java.util.Formatter;


/** The state of a game of 2048.
 *  @author P. N. Hilfinger + Josh Hug
 */
public class Model {
    /** Current contents of the board. */
    private final Board board;
    /** Current score. */
    private int score;

    /* Coordinate System: column x, row y of the board (where x = 0,
     * y = 0 is the lower-left corner of the board) will correspond
     * to board.tile(x, y).  Be careful!
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = 0;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (x, y) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score) {
        board = new Board(rawValues);
        this.score = score;
    }

    /** Return the current Tile at (x, y), where 0 <= x < size(),
     *  0 <= y < size(). Returns null if there is no tile there.
     *  Used for testing. */
    public Tile tile(int x, int y) {
        return board.tile(x, y);
    }

    /** Return the number of squares on one side of the board. */
    public int size() {
        return board.size();
    }

    /** Return the current score. */
    public int score() {
        return score;
    }


    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        board.clear();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        return maxTileExists() || !atLeastOneMoveExists();
    }

    /** Returns this Model's board. */
    public Board getBoard() {
        return board;
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public boolean emptySpaceExists() {
        int size = board.size();
        for (int i = 0; i < size; i++) {
            for (int c = 0; c < size; c++) {
                if (board.tile(i, c) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public boolean maxTileExists() {
        for (int i = 0; i < board.size(); i++) {
            for (int c = 0; c < board.size(); c++) {
                Tile location = board.tile(i, c);
                if (location != null && location.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public boolean atLeastOneMoveExists() {
        for (int i = 0; i < board.size(); i++) {
            for (int c = 0; c < board.size(); c++) {
                Tile location = board.tile(i, c);
                if (location == null) {
                    return true;
                }
                if (c < board.size() - 1) {
                    Tile rightTile = board.tile(i, c + 1);
                    if (rightTile != null && location.value() == rightTile.value()) {
                        return true;
                    }
                }
                if (i < board.size() - 1) {
                    Tile belowTile = board.tile(i + 1, c);
                    if (belowTile != null && location.value() == belowTile.value()) {
                        return true;
                    }
                }
                if (c > 0) {
                    Tile leftTile = board.tile(i, c - 1);
                    if (leftTile != null && location.value() == leftTile.value()) {
                        return true;
                    }
                }
                if (i > 0) {
                    Tile aboveTile = board.tile(i - 1, c);
                    if (aboveTile != null && location.value() == aboveTile.value()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Moves the tile at position (x, y) as far up as possible.
     *
     * Rules for Tilt:
     * 1. If two Tiles are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void moveTileUpAsFarAsPossible(int x, int y) {
        Tile currTile = board.tile(x, y);
        if (currTile == null || currTile.wasMerged()) {
            return; // No tile at the current position
        }
        int myValue = currTile.value();
        int targetY = y;
        boolean merge = false;
        for (int posY = y + 1; posY < board.size(); posY++) {
            Tile tileAbove = board.tile(x, posY);
            if (tileAbove == null) {
                targetY = posY;
            } else if (tileAbove.value() == myValue && !tileAbove.wasMerged()) {
                targetY = posY;
                merge = true;
                break;
            } else {
                break;
            }
        }
        if (targetY != y) {
            board.move(x, targetY, currTile);
            if (merge) {
                score += 2 * myValue;
            }
        }
    }






    /** Handles the movements of the tilt in column x of the board
     * by moving every tile in the column as far up as possible.
     * The viewing perspective has already been set,
     * so we are tilting the tiles in this column up.
     * */
    public void tiltColumn(int x) {
        int size = board.size();
        for (int y = size - 1; y >= 0; y--) {
            if (board.tile(x, y) != null) {
                moveTileUpAsFarAsPossible(x, y);
            }
        }
    }


    public void tilt(Side side) {
        board.setViewingPerspective(side);
        for (int x = 0; x < board.size(); x++) {
            tiltColumn(x);
        }
        board.setViewingPerspective(Side.NORTH);
    }

    /** Tilts every column of the board toward SIDE.
     */
    public void tiltWrapper(Side side) {
        board.resetMerged();
        tilt(side);
    }


    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int y = size() - 1; y >= 0; y -= 1) {
            for (int x = 0; x < size(); x += 1) {
                if (tile(x, y) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(x, y).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (game is %s) %n", score(), over);
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Model m) && this.toString().equals(m.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
