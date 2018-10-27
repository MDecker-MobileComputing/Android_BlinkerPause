package de.mide.blinkerpause;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * App demonstriert wie unter Verwendung geeigneter Lifecycle-Methoden ein
 * Thread pausiert werden kann, solange die zugehörige Activity-Instanz
 * nicht sichtbar ist.
 * <br><br>
 *
 * This project is licensed under the terms of the BSD 3-Clause License.
 */
public class MainActivity extends Activity {

    /** Tag für Log-Messages der ganzen App. */
    public static final String TAG4LOGGING = "BlinkerPause";


    /** Hintergrund-Farbe dieses TextViews wird durch Thread regelmäßig zwischen zwei
     *  Farben gewechselt, so dass "Blink-Effekt" entsteht. */
    protected TextView _blinkendesTextview = null;

    /** Thread-Instanz, die für den "Blink-Effekt" verantwortlich ist. */
    protected BlinkerThread _blinkerThread = null;


    /**
     * Lifecycle-Methode: Layout-Datei laden und Referenz auf
     * blinkendes TextView-Element holen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _blinkendesTextview = findViewById(R.id.textview_blinkend);
    }


    /**
     * Lifecycle-Methode zum Starten des Blinkers.
     */
    @Override
    protected void onStart() {
        super.onStart();

        _blinkerThread = new BlinkerThread();
        _blinkerThread.start();
        Log.i(TAG4LOGGING, "Blinker-Thread wurde gestartet.");
    }


    /**
     * Lifecycle-Methode zum Beenden des Blinkers.
     */
    @Override
    protected void onStop() {

        _blinkerThread.beenden();
        Log.i(TAG4LOGGING, "Blinker-Thread wurde gestoppt.");

        super.onStop();
    }


    /**
     * Event-Handler für den Button, öffnet mit einem expliziten
     * Intent eine andere Activity.
     *
     * @param view  Button-Objekt, auf das geklickt wurde
     */
    public void onKlickAufButton(View view) {

        Intent intent = new Intent(this, ZweiteActivity.class);
        startActivity(intent);
    }


    /* *************************** */
    /* *** Start innere Klasse *** */
    /* *************************** */
    protected class BlinkerThread extends Thread {

        /** Anhand dieser Member-Variable erkennt der Thread, dass er sich beenden soll. */
        protected boolean  __beenden = false;


        /**
         * Setzt ein Thread-internes Flag, damit der Thread Bescheid weiß, dass er nach
         * der nächsten Iteration sich beenden soll.
         */
        public void beenden() {
            __beenden = true;
        }


        /**
         * Der Inhalt dieser Methode wird von einem Hintergrund-Thread (Worker-Thread)
         * ausgeführt; hier ist das "Blinken" implementiert.
         */
        @Override
        public void run() {

            int zaehler = 0;

            while(true) { // Endlos-Schleife

                if (__beenden == true) {
                    break;
                }

                // Farbe in Abhängigkeit davon, ob Wert in "zaehler" gerade oder ungerade ist, setzen.
                if (++zaehler % 2 == 0) {

                    _blinkendesTextview.post(new Runnable() {
                        @Override
                        public void run() {
                            _blinkendesTextview.setBackgroundColor( 0xFFFFFF00 ); // Gelb mit voller Deck-Kraft
                        }
                    });
                    Log.i(TAG4LOGGING, "Blinker: EIN (" + zaehler + ")");

                } else {

                    _blinkendesTextview.post(new Runnable() {
                        @Override
                        public void run() {
                            _blinkendesTextview.setBackgroundColor( 0xFFD0D0D0 ); // Grauton
                        }
                    });
                    Log.i(TAG4LOGGING, "Blinker: AUS (" + zaehler + ")");
                }


                try {
                    Thread.sleep(1000); // Eine Sekunde warten, ohne dabei CPU-Zeit zu verschwenden
                }
                catch (InterruptedException ex) {
                    Log.e(TAG4LOGGING, "Exception während Warten aufgetreten: " + ex);
                }

            } // Endlos-Schleife

            // Wenn wir bis zu dieser Stelle kommen (da Endlos-Schleife geht dies nur über "break"),
            // dann wird der Thread beendet (Ende run()-Methode => Ende Thread).
        }

    };
    /* *************************** */
    /* *** Ende innere Klasse  *** */
    /* *************************** */

};
