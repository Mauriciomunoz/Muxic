package msfot.muxic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    magic_playlist mList;

    JSONParser jParser = new JSONParser();
    JSONArray columnas = null;
    ArrayList<String> col;

    static ArrayList<String> songs;

    String tag;

    boolean error=false;

    private ProgressDialog pDialog;
    FrameLayout frame;

    Button playNow;
    Button catalogo;
    Button sugerencias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.container, new CardFrontFragment()).commit();
        }*/

        col = new ArrayList<String>();

        //frame = (FrameLayout) findViewById(R.id.container);

        playNow=(Button)findViewById(R.id.playingNow);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");

        playNow.setTypeface(font);
        playNow.setText(new String(Character.toChars(0xf01D)));


        playNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flipCard(2);
                Intent intent = new Intent(MainActivity.this, PlayingActivity.class);
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
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
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
                Intent intent = new Intent(MainActivity.this, SugerenciaActivity.class);
                //intent.putExtra("", message);
                startActivity(intent);
                finish();
            }
        });

        new LoadAllProducts().execute();
    }

/*
    private void flipCard(int tipo) {
       //
       // if (mShowingBack) {
         //   getFragmentManager().popBackStack();
           // mShowingBack = false;
           // return;
       // }

        // Flip to the back.

        //mShowingBack = true;

        // Create and commit a new fragment transaction that adds the fragment for the back of
        // the card, uses custom animations, and is part of the fragment manager's back stack.

        if(tipo==1) {
            getFragmentManager()
                    .beginTransaction()

                            // Replace the default fragment animations with animator resources representing
                            // rotations when switching to the back of the card, as well as animator
                            // resources representing rotations when flipping back to the front (e.g. when
                            // the system Back button is pressed).
                    .setCustomAnimations(
                            R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                            R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                            // Replace any fragments currently in the container view with a fragment
                            // representing the next page (indicated by the just-incremented currentPage
                            // variable).

                    .replace(R.id.container, new CardFrontFragment())

                            // Add this transaction to the back stack, allowing users to press Back
                            // to get to the front of the card.
                    //.addToBackStack(null)

                            // Commit the transaction.
                    .commit();
        }else{
            if(tipo==2){
                getFragmentManager()
                        .beginTransaction()

                                // Replace the default fragment animations with animator resources representing
                                // rotations when switching to the back of the card, as well as animator
                                // resources representing rotations when flipping back to the front (e.g. when
                                // the system Back button is pressed).
                        .setCustomAnimations(
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                                // Replace any fragments currently in the container view with a fragment
                                // representing the next page (indicated by the just-incremented currentPage
                                // variable).

                        .replace(R.id.container, new CardBackFragment())

                                // Add this transaction to the back stack, allowing users to press Back
                                // to get to the front of the card.
                        //.addToBackStack(null)

                                // Commit the transaction.
                        .commit();
            }else{
                getFragmentManager()
                        .beginTransaction()

                                // Replace the default fragment animations with animator resources representing
                                // rotations when switching to the back of the card, as well as animator
                                // resources representing rotations when flipping back to the front (e.g. when
                                // the system Back button is pressed).
                        .setCustomAnimations(
                                R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                                R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                                // Replace any fragments currently in the container view with a fragment
                                // representing the next page (indicated by the just-incremented currentPage
                                // variable).

                        .replace(R.id.container, new CardThirdFragment())

                                // Add this transaction to the back stack, allowing users to press Back
                                // to get to the front of the card.
                        //.addToBackStack(null)

                                // Commit the transaction.
                        .commit();
            }
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void showAlert(String cadena, Context ctx){
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder alertbox = new AlertDialog.Builder(ctx);
        //seleccionamos la cadena a mostrar
        alertbox.setMessage(cadena);
        alertbox.show();

    }

    //Verifica si existe una conexion a internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * Muestra un Progress Dialog mientras carga los datos
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Cargando informacion...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * Obtiene todos los datos de la pagina dada
         * */
        protected String doInBackground(String... args) {

            //Verifica que la conexion en el dispositivo este activada(Conexion a internet)
            if(isNetworkAvailable()){
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                Log.i("Si hay", "RED");


                // getting JSON string from URL
                //Obteniendo datos del JSON desde la URL

                String js2=getJSON("http://private-29a5c-saborellatest.apiary-mock.com/notes");

                String js=getJSON("http://192.168.211.206:3000/catalog");



                try {


                    JSONObject json = new JSONObject(js);


                    columnas = json.getJSONArray("items");
                    // looping through All Products
                    for (int i = 0; i < columnas.length(); i++) {
                        JSONObject c = columnas.getJSONObject(i);

                        String col2=c.getString("track");


                        JSONObject obj = new JSONObject(col2);

                        /*
                        JSONArray albumObj=obj.getJSONArray("album");
                        String album="";
                        for(int j=0;j<albumObj.length();j++){
                            JSONObject albums = albumObj.getJSONObject(i);

                            album=albums.getString("name");



                        }*/


                        String album=obj.getString("album");

                        String auxAlbum[]=album.split("\"");

                        ArrayList<String> auxDataAlbum=new ArrayList<String>();
                        for(int x=0;x<auxAlbum.length;x++){
                            if(auxAlbum[x].equals(":")){
                                auxDataAlbum.add(auxAlbum[x+1]);
                            }
                        }

                        album=auxDataAlbum.get(auxDataAlbum.size()-1);


                        String imgAlbum=auxDataAlbum.get(0);
                        String imgAlbumAux[]=imgAlbum.split("/");
                        imgAlbum=imgAlbumAux[imgAlbumAux.length-1];

                        /*
                        album=album.replace("{\"name\":\"","");
                        album=album.replace("\"}","");*/


                        String trackid=obj.getString("id");

                        /*
                        JSONArray artObj=obj.getJSONArray("artists");
                        String artist="";
                        String separador="";

                        for(int j=0;j<artObj.length();j++){
                            JSONObject arts = artObj.getJSONObject(i);

                            artist=separador+arts.getString("name");
                            separador=separador+",";

                        }*/

                        String artist="";
                        String separador="";

                        JSONArray arrayArtist=obj.getJSONArray("artists");
                        ArrayList<String> artistList=new ArrayList<String>();


                        for(int y=0;y<arrayArtist.length();y++){
                            JSONObject artObj = arrayArtist.getJSONObject(y);
                            artist=artist+artObj.getString("name")+", ";

                        }

                        artist=artist.substring(0,artist.length()-2);

                        /*
                        String artist=obj.getString("artists");
                        artist=artist.replace("[{\"name\":\"", "");
                        artist=artist.replace("\"}]","");
                        */

                        String song=obj.getString("name");

                        String informacion=song+"##"+artist+"##"+album+"##"+trackid+"##"+imgAlbum;
                        col.add(informacion);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                error=true;
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            //Log.i("QUITANDO","YA");
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    //Si no hubo error de conexion en el dispositivo se crea la interfaz
                    //se añaden las acciones de los botones y se llenan los datos
                    if (error != true) {

                        //setContentView(R.layout.activity_main);
                        // setContentView(R.layout.activity_main);

                        songs=col;
                        llenaPortada(col);

                    } else {
                        showAlert("No hay conexion", getBaseContext());
                    }

                }
            });

        }

        public void llenaPortada(ArrayList<String> columnasInfo){
            mList = (magic_playlist)findViewById(R.id.listSongs);
            mList.llenaLista(columnasInfo);
        }
    }

    /**
     * A fragment representing the second page of the card.
     */
    public class CardBackFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_back, container, false);
        }
    }

    /**
     * A fragment representing the first page of the card.
     */
    public class CardFrontFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_front, container, false);
        }
    }

    /**
     * A fragment representing the third page of the card.
     */
    public class CardThirdFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_third_card, container, false);
        }
    }

    public void clickHandler(View v)
    {

        RelativeLayout vwParentRow = (RelativeLayout)v.getParent();
        tag = v.getTag().toString();



        //get the row the clicked button is in
        new moveSong().execute();



    }

    public void clearSongs(ArrayList<String> strList){

        for(int i=0;i<strList.size();i++){
            String aux=strList.get(i);
            String aux2[]=aux.split(">");
            String aux3[]=aux2[1].split("<");
            aux=aux3[0];
            strList.remove(i);
            strList.add(i,aux);
        }

    }

    public void clearArtist(ArrayList<String> strList){

        for(int i=0;i<strList.size();i++){
            String aux=strList.get(i);
            String aux2[]=aux.split(">");
            String aux3[]=aux2[1].split("<");
            aux=aux3[0];
            strList.remove(i);
            strList.add(i,aux);
        }

    }

    public void clearAlbum(ArrayList<String> strList){

        for(int i=0;i<strList.size();i++){
            String aux=strList.get(i);
            String aux2[]=aux.split(">");
            String aux3[]=aux2[1].split("<");
            aux=aux3[0];
            strList.remove(i);
            strList.add(i,aux);
        }

    }

    public String getJSON(String address){
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(address);
        try{
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if(statusCode == 200){
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while((line = reader.readLine()) != null){
                    builder.append(line);
                }
            } else {
                Log.e(MainActivity.class.toString(),"Failed to get JSON object");
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return builder.toString();
    }

    private class moveSong extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            postData();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent intent = new Intent(MainActivity.this, PlayingActivity.class);
            //intent.putExtra("", message);
            startActivity(intent);
            finish();
            // Dismiss the progress dialog

        }
    }

    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.211.206:3000/add");


        //httppost.setHeader("Authorization","Token "+myToken);

        InputStream inputStream = null;
        String result = null;
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("track_id", tag));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            result = sb.toString();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }


    }


}
