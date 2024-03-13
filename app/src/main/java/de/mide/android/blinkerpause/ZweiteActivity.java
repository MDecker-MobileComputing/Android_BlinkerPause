package de.mide.android.blinkerpause;

import android.app.Activity;
import android.os.Bundle;


/**
 * Zweite Activity in der App, um die {@link MainActivity} zu überdecken;
 * auch dann sollte der Blinker auf der überdeckten Activity pausieren.
 * <br><br>
 *
 * This project is licensed under the terms of the BSD 3-Clause License.
 */
public class ZweiteActivity extends Activity {

    /**
     * Lifecycle-Methode, lädt Layout-Datei.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zwei);
    }

}