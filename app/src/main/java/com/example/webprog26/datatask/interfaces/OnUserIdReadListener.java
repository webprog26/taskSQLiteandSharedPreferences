package com.example.webprog26.datatask.interfaces;

/**
 * Created by webprog26 on 16.11.2016.
 */

public interface OnUserIdReadListener {
    /**
     * Processes with user found by given id
     * @param userId long
     */
    void onUserIdFound(long userId);
}
