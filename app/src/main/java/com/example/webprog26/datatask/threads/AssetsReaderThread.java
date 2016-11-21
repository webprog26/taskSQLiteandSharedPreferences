package com.example.webprog26.datatask.threads;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.interfaces.OnIslandsFromAssetsReadListener;
import com.example.webprog26.datatask.models.Island;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by webprog26 on 15.11.2016.
 */

public class AssetsReaderThread extends Thread implements InterruptChecker {

    private static final String TAG = "AssetsReaderThread";

    private OnIslandsFromAssetsReadListener mOnIslandsFromAssetsReadListener;
    private String mFilename;
    private AssetManager mAssetManager;

    public AssetsReaderThread(OnIslandsFromAssetsReadListener onIslandsFromAssetsReadListener, String filename, Activity activity) {
        this.mOnIslandsFromAssetsReadListener = onIslandsFromAssetsReadListener;
        this.mFilename = filename;
        this.mAssetManager = activity.getAssets();
    }

    @Override
    public void run() {
        super.run();
        Log.i(TAG, "AssetsReaderThread run()");
        final ArrayList<Island> islands = getTxtFile(mFilename);
        Log.i(TAG, islands.toString());
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mOnIslandsFromAssetsReadListener.onIslandsFromAssetsReadFinished(islands);
            }
        });

        if(!isThreadInterrupted()){
            interrupt();
        }
    }

    /**
     * Reads islands from .txt file located in the assets
     * @param fileName {@link String}
     * @return {@link ArrayList<{Island>}
     */
    private synchronized ArrayList<Island> getTxtFile(String fileName)
    {
        BufferedReader reader = null;
        InputStream inputStream = null;
        ArrayList<Island> islands = new ArrayList<>();

        try{
            inputStream = mAssetManager.open(fileName);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while((line = reader.readLine()) != null)
            {
                  Island island = new Island();
                  island.setIslandName(line);
                  islands.add(island);

                Log.i(TAG, island.getIslandName());
            }
        } catch (IOException ioe){
            ioe.printStackTrace();
        } finally {

            if(inputStream != null)
            {
                try {
                    inputStream.close();
                } catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }

            if(reader != null)
            {
                try {
                    reader.close();
                } catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
        return islands;
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
