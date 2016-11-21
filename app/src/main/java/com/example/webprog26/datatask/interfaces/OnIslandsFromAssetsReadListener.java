package com.example.webprog26.datatask.interfaces;

import com.example.webprog26.datatask.models.Island;

import java.util.ArrayList;

/**
 * Created by webprog26 on 15.11.2016.
 */

public interface OnIslandsFromAssetsReadListener {
    /**
     * Processes {@link ArrayList<Island>} to GUI
     * @param islands {@link ArrayList}
     */
    void onIslandsFromAssetsReadFinished(ArrayList<Island> islands);
}
