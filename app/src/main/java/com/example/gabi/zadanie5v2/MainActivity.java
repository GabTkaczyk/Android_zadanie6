package com.example.gabi.zadanie5v2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.v7.appcompat.R.styleable.CompoundButton_buttonTint;
import static android.support.v7.appcompat.R.styleable.View;
import static com.example.gabi.zadanie5v2.R.id.buttonShowCustomDialog;
import static com.example.gabi.zadanie5v2.R.id.buttonWyjscie;
import static com.example.gabi.zadanie5v2.R.id.glowny_layout;
import static com.example.gabi.zadanie5v2.R.id.idProstyDialog;
import static com.example.gabi.zadanie5v2.R.id.parent;
//import static com.example.gabi.zadanie5v2.R.id.playSound;
import static com.example.gabi.zadanie5v2.R.id.stopSound;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String[] modele;
    private String[] opisy;
//    private Button button;
//    private Button ButtWyjscie;
    private MediaPlayer mediaPlayer = null;
    private Boolean ktory_dzwiek;

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RelativeLayout lajout = (RelativeLayout)findViewById(glowny_layout);
        lv = (ListView) findViewById(R.id.listView);
        initResources();
        initLanguagesListView();
        final Context context = this;

        //  trzeba dodac listener
        Button a = (Button)findViewById(buttonShowCustomDialog);
        a.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_dla_dziwnego_czegos);
                dialog.setTitle("dziwne okienko");

                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // po kliknieciu na przycisk zamykamy dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
           }
       });
        ///==================================================================

        //===================================================================
        //buttonWyjscie = (Button)findViewById(R.id.buttonWyjscie);
        Button b = (Button)findViewById(buttonWyjscie);
        b.setOnClickListener(new View.OnClickListener()
                             {
                                 @Override
                                 public void onClick(View v) {
                                     AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                     dialogBuilder.setTitle("Wyjscie");
                                     dialogBuilder.setMessage("Na pewno chcesz wyjsc?");
                                     dialogBuilder.setCancelable(false);
                                     dialogBuilder.setPositiveButton("Tak", new Dialog.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int whichButton) {
                                             MainActivity.this.finish();
                                         }
                                     });

                                     dialogBuilder.setNegativeButton("Nie", new Dialog.OnClickListener() {
                                         @Override
                                         public void onClick(DialogInterface dialog, int whichButton) {
                                             showToast("Anulowaleś wyjście");
                                         }
                                     });

                                     AlertDialog alertDialogMain = dialogBuilder.create();
                                     alertDialogMain.show();
                                 }
                             }
        );
        ///=========================================================

        ///---------------

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),opisy[position], Toast.LENGTH_SHORT).show();
                if( position == 0 )
                {
                    Intent pierwsze = new Intent(MainActivity.this, Telefon1.class);
                    startActivity(pierwsze);
                }
                if( position == 1 )
                {
                    Intent drugie = new Intent(MainActivity.this, Telefon2.class);
                    startActivity(drugie);
                }
                if( position == 2 )
                {
                    Intent trzecie = new Intent(MainActivity.this, Telefon3.class);
                    startActivity(trzecie);
                }
                if( position == 3 )
                {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    final String[] options = {"Dzwonek", "Suszarka"};
                    dialogBuilder.setTitle("Lista opcji");
                    dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            showToast("Wybrałeś: " + options[position]);

                            if( position == 0 )
                            {
                                mediaPlayer = MediaPlayer.create(context, R.raw.dzwonek_do_drzwi);
                                mediaPlayer.start();
                            }
                            if( position == 1 )
                            {
                                mediaPlayer = MediaPlayer.create(context, R.raw.suszarka);
                                mediaPlayer.start();

                            }
                        }
                    });
                    AlertDialog alertDialogBardzo = dialogBuilder.create();
                    alertDialogBardzo.show();
                }
            }
        });
        Button c = (Button)findViewById(stopSound);
        c.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick( View v)
            {
                if( mediaPlayer != null )
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        });

    }///on create


    private void initResources(){
        Resources res = getResources();
        modele = res.getStringArray(R.array.modele);
        opisy = res.getStringArray(R.array.hello_world);
    }

    private void initLanguagesListView(){
        lv.setAdapter(new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1,modele));
    }

    public void ProstyDialog (View v)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("taki dialog");
        alert.setMessage("taka wiadomosc");
        alert.show();
    }
}