package com.example.webprog26.datatask.threads;

import com.example.webprog26.datatask.providers.DBProvider;
import com.example.webprog26.datatask.interfaces.InterruptChecker;
import com.example.webprog26.datatask.interfaces.OnIslandsUploadedListener;
import com.example.webprog26.datatask.models.Island;

import java.util.ArrayList;

/**
 * Created by webprog26 on 15.11.2016.
 */

public class IslandsAdditionalThread extends Thread implements InterruptChecker {

    private static final String TAG = "IslandsAdditionalThread";
    private static final int TABLE_ISLANDS_FILLED_MARKER = -1;

    private DBProvider mDbProvider;
    private ArrayList<Island> mIslands;
    private OnIslandsUploadedListener mIslandsUploadedListener;

    public IslandsAdditionalThread(DBProvider mDbProvider, ArrayList<Island> mIslands, OnIslandsUploadedListener onIslandsUploadedListener) {
        this.mDbProvider = mDbProvider;
        this.mIslands = mIslands;
        this.mIslandsUploadedListener = onIslandsUploadedListener;
    }

    @Override
    public void run() {
        super.run();
        addIslandsToDB(mIslands);
        if(!isThreadInterrupted()){
            interrupt();
        }
    }

    /**
     * Adds islands to data base
     * @param islands {@link ArrayList<Island>}
     */
    private synchronized void addIslandsToDB(ArrayList<Island> islands){
        for(Island island: islands){
            if(mDbProvider.insertIslandToDb(island) == TABLE_ISLANDS_FILLED_MARKER){
                break;
            }
        }
        mIslandsUploadedListener.onIslandsUploaded();
    }

    @Override
    public boolean isThreadInterrupted() {
        return isInterrupted();
    }
}
