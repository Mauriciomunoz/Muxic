package msfot.muxic;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PlayingActivity extends Activity {

    magic_playlist mList;

    Button playNow;
    Button catalogo;
    Button sugerencias;

    JSONParser jParser = new JSONParser();
    JSONArray columnas = null;
    ArrayList<String> col;

    static ArrayList<String> songs;

    String tag;

    boolean error=false;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);

        col = new ArrayList<String>();


        playNow=(Button)findViewById(R.id.playingNow);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/fontawesome.ttf");

        playNow.setTypeface(font);
        playNow.setText(new String(Character.toChars(0xf01D)));

        playNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flipCard(2);
                Intent intent = new Intent(PlayingActivity.this, PlayingActivity.class);
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
                Intent intent = new Intent(PlayingActivity.this, MainActivity.class);
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
                Intent intent = new Intent(PlayingActivity.this, SugerenciaActivity.class);
                //intent.putExtra("", message);
                startActivity(intent);
                finish();
            }
        });

        new LoadAllProducts().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playing, menu);
        return true;
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

    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * Muestra un Progress Dialog mientras carga los datos
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PlayingActivity.this);
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

                String js=getJSON("http://192.168.211.206:3000/current");



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
                        String album=obj.getString("album");
                        album=album.replace("{\"name\":\"","");
                        album=album.replace("\"}","");
                        */



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

                        artist=artist.substring(0, artist.length() - 2);

                        String song=obj.getString("name");

                        String informacion=song+"##"+artist+"##"+album+"##"+imgAlbum;
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
            mList.llenaLista2(columnasInfo);
        }
    }
}
