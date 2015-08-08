package msfot.muxic;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

public class MainActivity extends ActionBarActivity {

    magic_playlist mList;

    ArrayList<String> foods=null;

    JSONParser jParser = new JSONParser();
    JSONArray columnas = null;
    ArrayList<String> col;

    boolean error=false;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        col = new ArrayList<String>();

        new LoadAllProducts().execute();
    }

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

                // Response response = client.target("http://private-29a5c-saborellatest.apiary-mock.com").path("/notes").request(MediaType.TEXT_PLAIN_TYPE).get();

                // getting JSON string from URL
                //Obteniendo datos del JSON desde la URL

                String js=getJSON("http://private-29a5c-saborellatest.apiary-mock.com/notes");

                String urlSongs="http://open.spotify.com/user/jesux.q/playlist/0GYFVJIwBhnkjZBe4J40kK";
                String myToken="f3a022849f0b4a1daec8ad00e3cc9ca6";

                //String js=getJSON("http://open.spotify.com/user/jesux.q/playlist/0GYFVJIwBhnkjZBe4J40kK");
                // JSONObject json = jParser.makeHttpRequest("http://private-29a5c-saborellatest.apiary-mock.com/notes", "GET", params);

                // Check your log cat for JSON reponse
                //Muestra los datos en el LogCat
                //Log.i("All Products: ", json.toString());


                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlSongs);

                httppost.setHeader("Authorization", "Bearer "+myToken);


                try {

                    HttpResponse response = httpclient.execute(httppost);

                    HttpEntity entity = response.getEntity();

                    InputStream inputStream = null;
                    String result = null;
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


                    JSONObject jObject = new JSONObject(result);

                    JSONObject json = new JSONObject("{\"food\":"+js+"}");

                    columnas = json.getJSONArray("food");
                    // looping through All Products
                    for (int i = 0; i < columnas.length(); i++) {
                        JSONObject c = columnas.getJSONObject(i);

                        // Storing each json item in variable
                        String informacion = c.getString("titulo")+"##"+c.getString("subtitulo")+"##"+c.getString("precio")+"##"+c.getString("imagen");
                        col.add(informacion);

                    }

			                /*
			                } else {
			                	Log.i("No Products found", "Nada encontrado");

			                }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
                    if(error!=true){

                        //setContentView(R.layout.activity_main);
                        // setContentView(R.layout.activity_main);


                        llenaPortada(col);

                    }
                    else{
                        showAlert("No hay conexion",getBaseContext());
                    }

                }
            });

        }

        public void llenaPortada(ArrayList<String> columnasInfo){
            mList = (magic_playlist) findViewById(R.id.listSong);
            mList.llenaLista(columnasInfo);
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
}
