package coms.example.puzzlegame.app;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import coms.example.puzzlegame.net_utils.LruBitmapCache;

/**
 * Code in this class is based on the Volley tutorial in the Word document
 * linked in the tutorials GitLab.
 *
 * Defines the controller to be used when making requests.
 * Contains methods for dealing with/viewing the request queue.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;

    /**
     * Creates a new instance of an AppController
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * @return - the current instance of AppController
     */
    public static synchronized AppController getInstance()
    {
        return mInstance;
    }

    /**
     * Returns the request queue
     * @return - request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Gets the image loader
     * @return - the image loader
     */
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    /**
     * Adds a request to the queue with a passed tag if tag is empty
     * @param req - the request to be added
     * @param tag - the tag associated with the request
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
    // set the default tag if tag is empty
    req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    getRequestQueue().add(req);
    }

    /**
     * Adds a request to the queue with an already given tag
     * @param req  - the request to be added
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests, such as when closing the program.
     * @param tag - tags of requests to be cancelled
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
             mRequestQueue.cancelAll(tag);
        }
    }
}