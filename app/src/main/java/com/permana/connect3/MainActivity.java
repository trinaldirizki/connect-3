package com.permana.connect3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    GridLayout gridLayout;
    LinearLayout layout;
    TextView gameText;
    // 0 = red, 1 = blue
    byte activePlayer = 0;
    // 2 = unplayed
    byte[] gameState = {2,2,2,2,2,2,2,2,2};
    // winning positions
    byte[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    // is the game active
    boolean gameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameText = (TextView) findViewById(R.id.gameText);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        layout = (LinearLayout) findViewById(R.id.gameOverLayout);
    }

    public void playAgain(View view){
        activePlayer = 0;
        for (int i = 0; i< gameState.length;i++){
            gameState[i] = 2;
        }
        for (int i = 0; i < gridLayout.getChildCount();i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        layout.setVisibility(View.INVISIBLE);
        gameActive = true;
    }

    public void isGameDraw(){
        boolean winning = checkWinningPosition();
        if  (winning == true) {
            gameActive = false;
        } else {
            boolean isGameOver = true;
            for (byte counterState : gameState){
                if (counterState == 2) isGameOver = false;
            }
            if (isGameOver){
                gameText.setText("It's a draw");
                layout.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean checkWinningPosition(){
        for (byte[] winningPosition : winningPositions){
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2){
                String winner = gameState[winningPosition[0]] == 0 ? "Red" : "Blue";
                layout.setVisibility(View.VISIBLE);
                gameText.setText(winner + " has won!");
                     return true;
            }
        }
        return false;
    }

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        byte tappedCounter = Byte.parseByte(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.blue);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
        }
        isGameDraw();
    }
}
