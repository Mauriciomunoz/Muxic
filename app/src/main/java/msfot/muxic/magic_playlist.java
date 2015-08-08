package msfot.muxic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Mau on 08/08/15.
 */
public class magic_playlist extends RelativeLayout {

    static ListView myList;
    String urlImagen;

    public magic_playlist(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout vistaCompuesta = (RelativeLayout) inflater.inflate(R.layout.magic_playlist, this);

        myList=(ListView) findViewById(R.id.music_list);
    }




    public void llenaLista(ArrayList<String> songs){

        String[] songsList = new String[songs.size()];
        songsList = songs.toArray(songsList);


        myList.setAdapter(new list_adapter(getContext(), R.layout.item_song, songsList));



        /*
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //showAlert("Test "+i,getContext());
                Intent intento=new Intent();
                Bundle b=new Bundle();
                b.putStringArray("datos", finalAlimentos);


                intento = new Intent(getContext(), DescriptionActivity.class);
                intento.putExtras(b);
                getContext().startActivity(intento);
            }
        });*/


    }

    public void showAlert(String cadena, Context ctx){
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
        //seleccionamos la cadena a mostrar
        alertbox.setMessage(cadena);
        alertbox.show();

    }


}
