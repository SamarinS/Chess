package ru.samarin.chess;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.app.AlertDialog;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class MainActivity extends ActionBarActivity {

    Game game;

    MyImageView selectedSquare;
    TextView gameStatusText;
    boolean selectedInvalidSquare = true;

    MyImageView[][] squareImageArray = new  MyImageView[8][8];
    Button unmakeMoveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new Game();
//        game.setInitialPosition();
        game.setTestPosition();

        gameStatusText = (TextView) findViewById(R.id.game_status_text);


        selectedSquare = null;


        unmakeMoveButton = (Button) findViewById(R.id.unmakeMoveButton);
        unmakeMoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = MainActivity.this.game.unmakeMove();
                onGameStateChanged();
            }
        });




        TableLayout table = (TableLayout) findViewById(R.id.table_chessboard);
        for(int i = 7;i>=0;i--) {
            TableRow rowTest = new TableRow(this);
            for(int j = 0;j<8;j++) {
                MyImageView myImage = new MyImageView(this, i, j);
                rowTest.addView(myImage);
                squareImageArray[i][j] = myImage;
            }
            table.addView(rowTest);
        }


        onGameStateChanged();
    }


    private void onGameStateChanged() {
        for(int i = 0;i<8;i++) {
            for(int j = 0;j<8;j++) {
                squareImageArray[i][j].invalidate();
            }
        }
        setGameStatusText();
        unmakeMoveButton.setEnabled(game.hasPositionChanged());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setGameStatusText() {
        if(game.getSideToMove() == Color.WHITE) {
            gameStatusText.setText("Ход белых");
        } else {
            gameStatusText.setText("Ход черных");
        }
    }

    private void tryMakeMove(Square from, Square to) {
        ArrayList<Square> changedSquares = new ArrayList<Square>();
        boolean flag = game.makeMove(from, to);
        if (!flag) {
            if (game.getColor(from) == game.getSideToMove() &&
                    game.getColor(to) != game.getSideToMove()
            ) {
                new AlertDialog.Builder(MainActivity.this)/*.setTitle("Argh")*/.setMessage("Невозможный ход!").setNeutralButton("Закрыть", null).show();
            }
        }
        onGameStateChanged();
    }

    public class MyImageView extends ImageView{

        private int i;
        private int j;
        Paint p = new Paint();

        public MyImageView(Context context, int i, int j) {
            super(context);
            this.i = i;
            this.j = j;

            p.setColor(android.graphics.Color.RED);
            p.setStrokeWidth(10);
            p.setStyle(Paint.Style.STROKE);

            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyImageView myView = (MyImageView) v;
                    if(selectedSquare != myView)
                    {
                        MyImageView oldSelectedSquare = selectedSquare;
                        selectedSquare = myView;

                        if(oldSelectedSquare!=null) {
                            Square from = new Square(oldSelectedSquare.i, oldSelectedSquare.j);
                            Square to = new Square(selectedSquare.i, selectedSquare.j);
                            tryMakeMove(from, to);
                        } else {
                            selectedSquare.invalidate();
                        }
                    }
                }
            });

            resetImage();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            resetImage();

            if(this == selectedSquare) {
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), p);
            }
        }

        private void resetImage() {
            String squareColor;
            if((i+j)%2 == 0) {
                squareColor = "black";
            } else {
                squareColor = "white";
            }
            String name = squareColor + "_square";

            Square sq = new Square(i, j);
            Piece piece = game.getPiece(sq);
            if(piece != Piece.NONE) {
                Color color = game.getColor(sq);
                name += "_" + color.fullString + "_" + piece.fullString;
            }

            int resId=MainActivity.this.getResources().getIdentifier(name, "drawable", MainActivity.this.getPackageName());

            this.setImageResource(resId);
        }

    }
}
