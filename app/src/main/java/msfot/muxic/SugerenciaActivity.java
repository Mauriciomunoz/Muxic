package msfot.muxic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SugerenciaActivity extends ActionBarActivity {

    Button playNow;
    Button catalogo;
    Button sugerencias;


    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencia);

        playNow=(Button)findViewById(R.id.playingNow);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");

        playNow.setTypeface(font);
        playNow.setText(new String(Character.toChars(0xf01D)));
        playNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flipCard(2);
                Intent intent = new Intent(SugerenciaActivity.this, PlayingActivity.class);
                //intent.putExtra("", message);
                startActivity(intent);
                finish();
            }
        });

        catalogo = (Button)findViewById(R.id.catalogo);
        catalogo.setTypeface(font);
        catalogo.setText(new String(Character.toChars(0xf001)));
        catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SugerenciaActivity.this, MainActivity.class);
                //intent.putExtra("", message);
                startActivity(intent);
                finish();
            }
        });


        sugerencias=(Button)findViewById(R.id.sugerencias);
        sugerencias.setTypeface(font);
        sugerencias.setText(new String(Character.toChars(0xf075)));
        sugerencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SugerenciaActivity.this, SugerenciaActivity.class);
                //intent.putExtra("", message);
                startActivity(intent);
                finish();
            }
        });

        send=(Button)findViewById(R.id.sendBtn);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert("Sugerencia enviada",SugerenciaActivity.this);
            }
        });

    }

    public void showAlert(String cadena, Context ctx){
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
        //seleccionamos la cadena a mostrar
        alertbox.setMessage(cadena);
        alertbox.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sugerencia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
