package coms.example.puzzlegame.net_utils;

import com.android.volley.toolbox.ImageLoader.ImageCache;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Code in this class is based on the Volley tutorial in the Word document
 * linked in the tutorials GitLab.
 *
 * Defines bitmaps and contains methods for finding size, and reading/writing them to/from urls
 */
public class LruBitmapCache extends LruCache<String, Bitmap> implements
        ImageCache {
    public static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()
                / 1024);
        final int cacheSize = maxMemory / 8;

        return cacheSize;
    }

    /**
     * Creates a default LruCache
     */
    public LruBitmapCache() {
        this(getDefaultLruCacheSize());
    }

    /**
     * Creates a bitmap of size sizeInKiloBytes
     * @param sizeInKiloBytes - the size in kilobytes of the bitmap to be created
     */
    public LruBitmapCache(int sizeInKiloBytes) {
        super(sizeInKiloBytes);

    }

    /**
     * Returns the size of a passed Bitmap
     * @param key
     * @param value - the bitmap of which to find the size
     * @return - the size of the bitmap
     */
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    /**
     * Gets a bitmap from a url
     * @param url - the url from which to get the bitmap
     * @return - the bitmap from the url
     */
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    /**
     * Puts a bitmap in a url
     * @param url - the url in which to place the bitmap
     * @param bitmap - the bitmap to place in the url
     */
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}