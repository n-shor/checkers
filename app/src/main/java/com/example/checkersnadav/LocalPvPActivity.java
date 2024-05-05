package com.example.checkersnadav;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LocalPvPActivity extends AppCompatActivity {
    private Game game;
    private GridView gridView;
    private CheckersAdapter adapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_pvp);

        String userId = getIntent().getStringExtra("userId");

        game = new Game();
        gridView = findViewById(R.id.grid_view);
        adapter = new CheckersAdapter(this, game.getBoard().getState(), Board.WHITE); // We display the board from white's perspective in local PvP
        gridView.setAdapter(adapter);

        gridView.setOnTouchListener(new View.OnTouchListener()
        {
            private int startX = -1;
            private int startY = -1;

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getActionMasked();
                int position = gridView.pointToPosition((int)event.getX(), (int)event.getY());

                if (position == GridView.INVALID_POSITION)
                {
                    return false;
                }

                int row = position / Board.BOARD_SIZE;
                int col = position % Board.BOARD_SIZE;

                // Flip the rows and cols again because we flipped them in the GUI
                int flippedRow = Board.BOARD_SIZE - 1 - row;
                int flippedCol = Board.BOARD_SIZE - 1 - col;

                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        startX = flippedRow;
                        startY = flippedCol;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (game.makeMove(startX, startY, flippedRow, flippedCol))
                        {
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(LocalPvPActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        }

                        startX = -1;
                        startY = -1;

                        if (!game.isActive())
                        {
                            Intent intent = new Intent(LocalPvPActivity.this, OfflineEndScreenActivity.class);
                            intent.putExtra("userId", userId);
                            intent.putExtra("winner", game.getBoard().getWinner());
                            startActivity(intent);
                            finish();
                        }

                        break;
                }
                return true;
            }
        });
    }
}
