package com.gisbert.it.pedidos.actividad;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.gisbert.it.pedidos.R;
import com.gisbert.it.pedidos.serv.OrdenServicios;
import com.gisbert.it.pedidos.serv.RestLink;

import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Pablo Pincheira on 08/12/2015.
 */
public class OrdenServicioBusListActivity extends Activity {
    /**
     @Override
     protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_equipo_list);
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
     // Inflate the menu; this adds items to the action bar if it is present.
     getMenuInflater().inflate(R.menu.menu_equipo_list, menu);
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

     **/
    String url;
    String user;
    String pass;
    String numeroOrdenDeServicio;
    OrdenServicios ordenServicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orden_bus_list);

        final Button button_buscar = (Button)findViewById(R.id.button_orden_Buscar);
        button_buscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ListView listview = (ListView) findViewById(R.id.listView_orden_list);
                Intent intent = getIntent();
                url = intent.getStringExtra("url") + "services/OrdenServicioRepositorio/actions/buscarPorNumero/invoke";
                user = intent.getStringExtra("user");
                pass = intent.getStringExtra("pass");
                final EditText editTextNumero=(EditText) findViewById(R.id.editText_orden_numero);
                try {
                    numeroOrdenDeServicio=editTextNumero.getText().toString();

                    ordenServicios = new FillListOfOrdenServiciosThread().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                List<RestLink> LinksEquiposList = null;
                final List<String> listNombres = new ArrayList<String>();
                if ((ordenServicios != null) && (ordenServicios.getResult().getValue().size()!=0)){
                    LinksEquiposList = ordenServicios.getResult().getValue();

                    //tomar nombres de los alumnos

                    for (RestLink equipoLink : LinksEquiposList) {
                        listNombres.add(equipoLink.getTitle());
                    }
                }else
                mostrarMensaje("No Existen Orden de Servicios");


                //llenar la lista
                final StableArrayAdapter adapter = new StableArrayAdapter(getBaseContext(),
                        android.R.layout.simple_list_item_1, listNombres);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, final long id) {
                        final String item = (String) parent.getItemAtPosition(position);

                        Log.v("nombre", ordenServicios.getResult().getValue().get(position).getTitle());
                        Log.v("link", ordenServicios.getResult().getValue().get(position).getHref());

                        String urlEquipo = ordenServicios.getResult().getValue().get(position).getHref();

                        Intent newIntent = new Intent("android.intent.action.ORDEN_DETALLE");
                        newIntent.putExtra("user",user);
                        newIntent.putExtra("pass",pass);
                        newIntent.putExtra("url",urlEquipo);

                        startActivity(newIntent);


                    }
                });       }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equipo_list, menu);
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

    private class FillListOfOrdenServiciosThread extends AsyncTask<Void, Void, OrdenServicios> {
        @Override
        protected OrdenServicios doInBackground(Void...  params) {
            try {


                //Services services = null;
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                Log.v("ingresando URL", url);
                RestTemplate restTemplate = new RestTemplate();

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                restTemplate.getMessageConverters().add(converter);


                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("numeroOrdenDeServicio", numeroOrdenDeServicio);
                HttpEntity<?> entity = new HttpEntity<>(headers);

                ResponseEntity<OrdenServicios> response = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.POST, requestEntity, OrdenServicios.class);

                OrdenServicios ordenes = response.getBody();

                Log.v("listado OS contiene", ordenes.getResult().getValue().size() +"");
                int arraySize = ordenes.getResult().getValue().size();
                RestLink[] equiposArray = new RestLink[arraySize];
                for (int i=0; i< arraySize;i++){
                    equiposArray[i] = ordenes.getResult().getValue().get(i);
                    Log.v("os Encontrado", equiposArray[i].getTitle());
                }
                return ordenes;

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }
    private void mostrarMensaje(CharSequence text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public String loadJSON() {
        String json = null;
        try {
            FileInputStream fis = openFileInput("config.json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            json = sb.toString();

        } catch (FileNotFoundException e) {
            json = "{ \"urlRestful\" : \"\",  \"user\" : \"\",  \"pass\" : \"\",  \"save\" : \"\"}";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
