package com.example.checkersnadav;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Adapter class for managing the display of the checkers board in a GridView.
 * This adapter handles the creation and updating of view elements representing
 * each cell on the board, including setting the appropriate background,
 * piece images, and king images based on the game state.
 */
public class CheckersAdapter extends BaseAdapter {
    private final Context context; // Context in which the adapter is running
    private final Piece[][] boardState; // Current state of the board

    /**
     * Constructs a new CheckersAdapter.
     * @param context The current context.
     * @param boardState The 2D array representing the state of the checkers board.
     */
    public CheckersAdapter(Context context, Piece[][] boardState) {
        this.context = context;
        this.boardState = boardState;
    }

    /**
     * Returns the number of items in the data set represented by this Adapter.
     * @return The number of items in the data set.
     */
    @Override
    public int getCount() {
        return Board.BOARD_SIZE * Board.BOARD_SIZE; // Total number of cells on the board
    }

    /**
     * Gets the data item associated with the specified position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @return The object at the specified position.
     */
    @Override
    public Object getItem(int position) {
        int row = position / Board.BOARD_SIZE;
        int col = position % Board.BOARD_SIZE;
        return boardState[row][col];
    }

    /**
     * Gets the row id associated with the specified position in the list.
     * @param position The position of the item within the adapter's data set.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Gets a View that displays the data at the specified position in the data set.
     * @param position The position of the item within the adapter's data set.
     * @param convertView The old view to reuse, if possible.
     * @param parent The parent that this view will eventually be attached to.
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("CheckersAdapter", "Getting view for position: " + position);
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 150)); // Check size
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }

        int row = position / Board.BOARD_SIZE;
        int col = position % Board.BOARD_SIZE;

        // Flip the rows and cols for piece setup so white pieces start at the bottom
        int flippedRow = Board.BOARD_SIZE - 1 - row;
        int flippedCol = Board.BOARD_SIZE - 1 - col;

        Piece piece = boardState[flippedRow][flippedCol];

        if ((flippedRow + flippedCol) % 2 == 0)
        {
            imageView.setBackgroundColor(Color.WHITE); // Light squares
        }
        else
        {
            imageView.setBackgroundColor(Color.DKGRAY); // Dark squares
        }

        // Set the piece image based on the type of piece and whether it's a king
        if (piece != null)
        {
            imageView.setImageResource(piece.getPictureID());
        }
        else
        {
            imageView.setImageDrawable(null); // Clear any previous image if no piece is present
        }

        return imageView;
    }

}
