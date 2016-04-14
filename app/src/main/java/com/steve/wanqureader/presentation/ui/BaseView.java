package com.steve.wanqureader.presentation.ui;

/**
 * Created by dmilicic on 7/28/15.
 * <p/>
 * This interface represents a basic view. All views should implement these common methods.
 */
public interface BaseView {

    /**
     * This is a general method used for showing some kind of progress or hiding progress information during a background task. For example, this
     * method should show a progress bar and/or disable buttons before some background work starts.
     */
    void onSetProgressBarVisibility(int statusCode);

    /**
     * This method is used for showing error messages on the UI.
     *
     * @param message The error message to be dislayed.
     */
    void onError(String message);
}
