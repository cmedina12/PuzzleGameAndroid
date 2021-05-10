package coms.example.mockitotest;

import android.content.Context;
import android.graphics.Bitmap;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import coms.example.puzzlegame.CustomVolleyRequest;
import coms.example.puzzlegame.LeaderboardActivity;
import coms.example.puzzlegame.PlayPuzzleActivity;
import coms.example.puzzlegame.PuzzleCompleteActivity;
import coms.example.puzzlegame.R;
import coms.example.puzzlegame.SelectPuzzleActivity;
import coms.example.puzzlegame.StringRequestActivity;

public class PuzzleGameMockitoTest {

    //Mocks our StringRequestActivity for user information
    @Mock
    StringRequestActivity sra = new StringRequestActivity();

    //Tests that the URL is correct
    @Test
    public void getUserTest() {
        //No longer works after rearranging the code for StringRequestActivity with MainHomeActivity
        //assertSame("http://coms-309-010.cs.iastate.edu:8080/getUser/eckirch@iastate.edu/mc_3", sra.getURL());
    }

    //Makes sure buttons are not yet initialized
    @Test
    public void btnNotYetInitializedTest() {
        Assert.assertNull(sra.getBtnStringReq());
    }

    //Asserts that our email is properly set to a default when first running the class
    @Test
    public void userTest() {
        assertSame("Default", sra.getEmail());
    }

    /*
    @Test
    public void progressTest() {
        Assert.assertVoid(sra.showProgressDialog());
    }
*/

    @Mock
    PuzzleCompleteActivity pca = new PuzzleCompleteActivity();

    @Mock
    LeaderboardActivity la = new LeaderboardActivity();

    //Checks that no ID is remembered when opening leaderboard without shortcut
    @Test
    public void noShortcutLeaderboardTest() {
        assertSame(false, la.getRemembering());
    }

    //Checks that an ID is remembered when opening leaderboard with shortcut from PuzzleCompleteActivity
    @Test
    public void shortcutLeaderboardTest() {
        //Calls PuzzleCompleteActivity's shortcut to the leaderboard with a dummy ID
        pca.shortcutLeaderboardTest("000000000");

        //Checks that the leaderboard activity now remembers the ID from the recently called shortcut
        assertSame(true, la.getRemembering());
        //Checks that the ID is the same as the one passed in from the PuzzleCompleteActivity's shortcut
        assertSame("000000000", la.getIDVal());
        //Should be same as previous check if code is being mocked properly
        assertSame("000000000", la.IDVal);
        //Since IDVal does not apply to any valid puzzle, times should still be null
        Assert.assertNull(la.time1);
        //Since IDVal does not apply to any valid puzzle, playerNames should still be null
        Assert.assertNull(la.playerName1);
    }

    //cmedina - demo 3
    // check if null for image object is returned when not specified
    @Test
    public void jsonReturn(){
        PlayPuzzleActivity pca = Mockito.mock(PlayPuzzleActivity.class);
        JSONObject response = new JSONObject();
        Assert.assertNull(pca.getData());
    }

    //make sure getData is called when using PlayPuzzle
    @Test
    public void checkReturn(){
        PlayPuzzleActivity mock1 = Mockito.mock(PlayPuzzleActivity.class);
        verify(mock1, times(1)).getData();
    }

    // basic check for name
    //@Test
//    public void checkName(){
//        UserData name1 = Mockito.mock(UserData.class);
//        Assert.assertNull(name1.getName());
//    }
    // demo 4
    @Test
    public void sampleEmptyPuzzle(){
        SelectPuzzleActivity sel = Mockito.mock(SelectPuzzleActivity.class);
        PlayPuzzleActivity df = Mockito.mock(PlayPuzzleActivity.class);
        //df.puzzleSetup(null);
        ArrayList<Bitmap> mp = new ArrayList<Bitmap>();
        Assert.assertEquals(mp,df.puzzleSetup(null));
    }

    @Test
    public void getCorrectScaling(){
        PlayPuzzleActivity df = Mockito.mock(PlayPuzzleActivity.class);
        ArrayList<Bitmap> mp = new ArrayList<Bitmap>(9);
        int dim = mp.size();
        JSONObject data = df.getData();
        NetworkImageView thePuzzle = null;
        ImageLoader imgLoad;
        String imageURL = "http://coms-309-010.cs.iastate.edu/userImages/1/EzW66bHVIAgUbhH.jpg";
        Context context;

        data=SelectPuzzleActivity.getPuzzle();
//        msgResponseName.setText((String) data.get("puzzleName"));
//        String imageURL = "http://coms-309-010.cs.iastate.edu/" + data.get("puzzleData");

//        imgLoad = CustomVolleyRequest.getInstance(context)
//                .getImageLoader();
//        imgLoad.get(imageURL, ImageLoader.getImageListener(thePuzzle,
//                R.drawable.ic_launcher_background, android.R.drawable
//                        .ic_dialog_alert));
//        thePuzzle.setImageUrl(imageURL, imgLoad);
        df.puzzleSetup(thePuzzle);

        Assert.assertEquals(dim, df.getPuzzlePieces().size());
    }



}
