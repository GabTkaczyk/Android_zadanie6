package com.example.gabi.zadanie5v2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
    private MediaPlayer mediaPlayer = null;
    //private Boolean ktory_dzwiek;
    private MediaRecorder myAudioRecoreder;
    private  static String mFileName = null;
    private MediaPlayer m;
    private int flaga = 0;

///-----------------------
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


        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
//        myAudioRecoreder = new MediaRecorder();
//        myAudioRecoreder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        myAudioRecoreder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        myAudioRecoreder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
//        myAudioRecoreder.setOutputFile(mFileName);
//        m = new MediaPlayer();

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
                                             showToast("Anulowano wyjście");
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
                            showToast("Wybrano: " + options[position]);

                            if( position == 0 )
                            {
                                mediaPlayer = MediaPlayer.create(context, R.raw.dzwonek_do_drzwi);
                                mediaPlayer.start();
                                flaga = 1;

                                try{
                                    OutputStreamWriter out  = new OutputStreamWriter(openFileOutput("plikDoDzwonka.txt", MODE_APPEND));
                                    EditText EdT  = (EditText)findViewById(R.id.editText1);
                                    String t = EdT.getText().toString();
                                    t = t + " dzwiek dzwonka do drzwi\n";
                                    out.write(t);
                                    out.write('\n');

                                    out.close();
                                    //Toast.makeText(this, "Text saved", Toast.LENGTH_LONG).show();
                                }catch(java.io.IOException e)
                                {
                                    //Toast.makeText(this,"Sorry, nie", Toast.LENGTH_LONG).show();
                                }
                                ///-------------------
                                StringBuilder tekst = new StringBuilder();
                                try
                                {
                                    InputStream instream = openFileInput("plikDoDzwonka.txt");
                                    if( instream != null)
                                    {
                                        InputStreamReader inputsrteader = new InputStreamReader(instream);
                                        BufferedReader buffreader = new BufferedReader(inputsrteader);
                                        String line = null;
                                        while ( (line = buffreader.readLine()) != null )
                                        {
                                            tekst.append(line);
                                            tekst.append('\n');
                                        }
                                    }
                                }catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                                TextView tv = (TextView)findViewById(R.id.textView1);
                                tv.setText(tekst);

                            }
                            if( position == 1 )
                            {
                                mediaPlayer = MediaPlayer.create(context, R.raw.suszarka);
                                mediaPlayer.start();
                                flaga = 2;

                                try{
                                    OutputStreamWriter out  = new OutputStreamWriter(openFileOutput("plikDoSuszarki.txt", MODE_APPEND));
                                    EditText EdT  = (EditText)findViewById(R.id.editText1);
                                    String t = EdT.getText().toString();
                                    t = t + " dzwiek suszarki\n";
                                    out.write(t);
                                    out.write('\n');

                                    out.close();
                                    //Toast.makeText(this, "Text saved", Toast.LENGTH_LONG).show();
                                }catch(java.io.IOException e)
                                {
                                    //Toast.makeText(this,"Sorry, nie", Toast.LENGTH_LONG).show();
                                }
                                ///----------------
                                StringBuilder tekst2 = new StringBuilder();
                                try
                                {
                                    InputStream instream = openFileInput("plikDoSuszarki.txt");
                                    if( instream != null)
                                    {
                                        InputStreamReader inputsrteader = new InputStreamReader(instream);
                                        BufferedReader buffreader = new BufferedReader(inputsrteader);
                                        String line = null;
                                        while ( (line = buffreader.readLine()) != null )
                                        {
                                            tekst2.append(line);
                                            tekst2.append('\n');
                                        }
                                    }
                                }catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                                TextView tv = (TextView)findViewById(R.id.textView1);
                                tv.setText(tekst2);
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
        ///====================================
        ///======ZADANIE NAGRYWANIE============
        final Button nagrywaj = (Button) findViewById(R.id.ID_nagrywaj);
        final Button nagrStop  = (Button) findViewById(R.id.ID_stop_nagrywanie);
        final Button nagrOdtw = (Button) findViewById(R.id.ID_odtworz_nagranie);
        nagrStop.setEnabled(false);
        nagrOdtw.setEnabled(false);


        nagrywaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                myAudioRecoreder = new MediaRecorder();
                myAudioRecoreder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecoreder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                myAudioRecoreder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                myAudioRecoreder.setOutputFile(mFileName);
                nagrywaj.setEnabled(false);
                nagrStop.setEnabled(true);
                nagrOdtw.setEnabled(false);

                try {
                    myAudioRecoreder.prepare();
                } catch (IOException e) {
                    //Log.e(LOG_TAG, "prepare() failed");
                }

                myAudioRecoreder.start();
            }
        });

        //nagr2 = (Button) findViewById(R.id.ID_stop_nagrywanie);
        nagrStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                myAudioRecoreder.stop();
                myAudioRecoreder.release();
                myAudioRecoreder = null;
                nagrOdtw.setEnabled(true);
                nagrywaj.setEnabled(true);
                nagrStop.setEnabled(false);
            }
        });

        //final Button nagr3 = (Button) findViewById(R.id.ID_odtworz_nagranie);
        nagrOdtw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m = new MediaPlayer();

                try {
                    m.setDataSource(mFileName);
                    m.prepare();
                    m.start();
                } catch (IOException e) {
                    //zlapane bardzo
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
///======zadanie zapis================================
    public void SaveText( View view)
    {
        try{
            OutputStreamWriter out  = new OutputStreamWriter(openFileOutput("filename.txt", MODE_APPEND));
            EditText ET  = (EditText)findViewById(R.id.editText1);
            String text = ET.getText().toString();
            out.write(text);
            out.write('\n');

            out.close();
            Toast.makeText(this, "Text saved", Toast.LENGTH_LONG).show();
        }catch(java.io.IOException e)
        {
            Toast.makeText(this,"Sorry, nie", Toast.LENGTH_LONG).show();
        }
    }//savetext

    public void ViewText(View view) throws FileNotFoundException
    {
        StringBuilder text = new StringBuilder();
        try
        {
            InputStream instream = openFileInput("filename.txt");
            if( instream != null)
            {
                InputStreamReader inputsrteader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputsrteader);
                String line = null;
                while ( (line = buffreader.readLine()) != null )
                {
                    text.append(line);
                    text.append('\n');
                }
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText(text);
    }//viewtext

    public void SaveSD (View view)
    {
        // Potrzebujemy ścieżki do karty SD:
        File sdcard = Environment.getExternalStorageDirectory();
        // Dodajemy do ścieżki własny folder:
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki");
        // jeżeli go nie ma to tworzymy:
        dir.mkdir();
        // Zapiszmy do pliku nasz tekst:
        File file = new File(dir, "myfilename.txt");
        EditText ET = (EditText)findViewById(R.id.editText1);
        String text = ET.getText().toString();
        try
        {
            FileOutputStream os = new FileOutputStream(file);
            os.write(text.getBytes());
            os.close();
            Toast.makeText(this, "Text saved", Toast.LENGTH_LONG).show();
        }catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this,"Sorry, nie", Toast.LENGTH_LONG).show();
        }
    }//savesd

    public  void ViewSD ( View view)
    {
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki");
        File file = new File(dir, "myfilename.txt");
        int length = (int)file.length();
        byte[]bytes = new byte[length];
        FileInputStream in;
        try
        {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        }catch (IOException e)
        {
            e.printStackTrace();
        }

        String contents = new String(bytes);
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText(contents);
    }///viewSD

    public void clear( View view)
    {
        //clear text view:
        TextView tv = (TextView)findViewById(R.id.textView1);
        tv.setText(null);
///-------karta:
        File sdcard = Environment.getExternalStorageDirectory();
        File dir = new File(sdcard.getAbsolutePath() + "/MojePliki");
        File file = new File(dir, "myfilename.txt");
        file.delete();
///-----internal:
        String directory = getFilesDir().getAbsolutePath();
        File f0 = new File(directory, "filename.txt");
        f0.delete();

        String dirDzwonek = getFilesDir().getAbsolutePath();
        File f1 = new File(dirDzwonek, "plikDoDzwonka.txt");
        f1.delete();

        String dirSuszarka = getFilesDir().getAbsolutePath();
        File f2 = new File(dirSuszarka, "plikDoSuszarki.txt");
        f2.delete();

        Toast.makeText(this,"usunieto plik z urzadzenia", Toast.LENGTH_LONG).show();
    }

}//klasa