package coms.example.puzzlegame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Levi Jansen
 * @author Eric Kirch
 * @author Cristofer Medina
 *
 * Displays the puzzle with the ID given on the SelectPuzzleActivity screen.
 * Will later allow actual playing of the puzzle.
 */
public class PlayPuzzleActivity extends AppCompatActivity {

    private static JSONObject data;
    private NetworkImageView thePuzzle;
    private ImageLoader imgLoad;
    public static long startTime;
    public static long endTime;
    private Button btnFinish;

    // Puzzle difficulty settings for setting Puzzle dimensions
    private static final int EASY = 1;
    private static final int MEDIUM = 2;
    private static final int HARD = 3;
    // Puzzle piece from image
    private ArrayList<Bitmap> puzzlePieces;
    private int Dimension = 0;
    //private GridView puzzleGrid;
    //private Toolbar toolbar;
    private RecyclerView puzzleGrid;


    private TextView msgResponseName;
    private boolean complete = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_puzzle);

        startTime = System.currentTimeMillis();
        msgResponseName = (TextView) findViewById(R.id.msgResponseName);
        thePuzzle = (NetworkImageView) findViewById(R.id.thePuzzle);
        btnFinish = (Button) findViewById(R.id.btnCompletePuzzle);
        btnFinish.setOnClickListener((v) -> { checkComplete(); });

        try {

            data=SelectPuzzleActivity.getPuzzle();
            msgResponseName.setText((String) data.get("puzzleName"));
            String imageURL = "http://coms-309-010.cs.iastate.edu/" + data.get("puzzleData");

            imgLoad = CustomVolleyRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();
            imgLoad.get(imageURL, ImageLoader.getImageListener(thePuzzle,
                    R.drawable.ic_launcher_background, android.R.drawable
                            .ic_dialog_alert));
            thePuzzle.setImageUrl(imageURL, imgLoad);

            //thePuzzle.setDefaultImageResId(R.drawable.default_image); // image for loading...
            //thePuzzle.setImageUrl(imageURL, ImgController.getInstance().getImageLoader()); //ImgController from your code.

            //thePuzzle.setImageBitmap(BitmapFactory.decodeFile(imageURL));
            //puzzleGrid = (RecyclerView)findViewById(R.id.puzzleGrid);
            //puzzleSetup(thePuzzle);
            //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, Dimension);
            //puzzleGrid.setLayoutManager(gridLayoutManager);
            //puzzleGrid.setAdapter(new ImageListAdapter(this, puzzlePieces));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void checkComplete() {
        if (complete) {
            endTime = System.currentTimeMillis();
            startActivity(new Intent(PlayPuzzleActivity.this,
                    PuzzleCompleteActivity.class));
        }

    }

    public static JSONObject getData() {
        return data;
    }

    /**
     * Takes an image and sets up the puzzle with split pieces
     * @param image image from server to use for puzzle
     */
    public ArrayList<Bitmap> puzzleSetup(NetworkImageView image){
        int pieceHeight, pieceWidth;
        puzzlePieces = new ArrayList<Bitmap>(Dimension * Dimension);

        try {
            String difficulty = "http://coms-309-010.cs.iastate.edu/" + data.get("puzzleDifficulty");
            int diffCheck = Integer.parseInt(difficulty);

            if ( diffCheck== EASY) {
                Dimension = 3;
            }
            else if (diffCheck == MEDIUM) {
                Dimension = 5;
            }
            else if(diffCheck == HARD) {
                Dimension = 9;
            }

            // Get scaled bitmap of chosen image
            BitmapDrawable dr = (BitmapDrawable) image.getDrawable();
            Bitmap bitmap = dr.getBitmap();
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

            pieceHeight = bitmap.getHeight() / Dimension;
            pieceWidth = bitmap.getWidth() / Dimension;

            // pixel positions of pieces
            int yCoordinate = 0;
            for (int x = 0; x < Dimension; x++) {
                int xCoordinate = 0;
                for (int y = 0; y < Dimension; y++) {
                    puzzlePieces.add(Bitmap.createBitmap(scaledBitmap, xCoordinate, yCoordinate, pieceWidth, pieceHeight));
                    xCoordinate += pieceWidth;
                }
                yCoordinate += pieceHeight;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Collections.shuffle(puzzlePieces);
        //scramblePuzzleImage(puzzlePieces);
        return puzzlePieces;

    }

    public ArrayList<Bitmap> getPuzzlePieces(){
        return puzzlePieces;
    }

    /**
     * Method for randomizing puzzle pieces
     */
//    private void scramblePuzzleImage(ArrayList<Bitmap> E) {
//        Collections.shuffle(E);
//        int pieceHeight, pieceWidth;
//
//        pieceHeight = E.get(0).getHeight();
//        pieceWidth = E.get(0).getWidth();
//        Bitmap bitmap = Bitmap.createBitmap(pieceWidth * Dimension, pieceHeight * Dimension, Bitmap.Config.ARGB_8888);
//
//        Canvas canvas = new Canvas(bitmap);
//        int count = 0;
//        for(int rows = 0; rows < Dimension; rows++){
//            for(int cols = 0; cols < Dimension; cols++){
//                canvas.drawBitmap(E.get(count), pieceHeight * cols, pieceHeight * rows, null);
//                count++;
//            }
//        }
//    }

//    private void display() {
//        ArrayList<Button> buttons = new ArrayList<>();
//        Button btn;
//
//        for (int i = 0; i < puzzlePieces.size(); i++) {
//            btn = new Button(this);
//
//            //if (puzzlePieces.get(i).equals)
//        }
//    }
}
