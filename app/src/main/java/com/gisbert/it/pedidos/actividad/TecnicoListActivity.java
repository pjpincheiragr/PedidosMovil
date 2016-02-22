package com.pluscel.pluscelmovil.actividad;

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
import com.pluscel.pluscelmovil.R;
import com.pluscel.pluscelmovil.serv.Clientes;
import com.pluscel.pluscelmovil.serv.RestLink;
import com.pluscel.pluscelmovil.serv.Tecnicos;

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
 * Created by Pablo Pincheira on 21/11/2015.
 */
public class TecnicoListActivity  extends Activity {

    String url;
    String user;
    String pass;
    String apellido;
    String nombre;
    Tecnicos tecnicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tecnico_list);


        final Button button_buscar = (Button)findViewById(R.id.button_tecnico_Buscar);
        button_buscar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ListView listview = (ListView) findViewById(R.id.listView_tecnico_list);
                Intent intent = getIntent();
                url = intent.getStringExtra("url") + "services/TecnicoRepositorio/actions/buscarPorApellidoNombre/invoke";
                user = intent.getStringExtra("user");
                pass = intent.getStringExtra("pass");
                final EditText editTextApellido=(EditText) findViewById(R.id.editText_tecnico_apellido);
                final EditText editTextNombre=(EditText) findViewById(R.id.editText_tecnico_nombre);
                apellido = editTextApellido.getText().toString();
                nombre=editTextNombre.getText().toString();
                try {
                    tecnicos = new FillListOfTecnicosThread().execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                List<RestLink> LinksEquiposList = null;
                final List<String> listNombres = new ArrayList<String>();
                if((tecnicos != null) &&(tecnicos.getResult().getValue().size()!=0)){
                    LinksEquiposList = tecnicos.getResult().getValue();

                    //tomar nombres de los alumnos

                    for (RestLink equipoLink : LinksEquiposList) {
                        listNombres.add(equipoLink.getTitle());
                    }
                }else
                mostrarMensaje("No Existen Tecnicos");


                //llenar la lista
                final StableArrayAdapter adapter = new StableArrayAdapter(getBaseContext(),
                        android.R.layout.simple_list_item_1, listNombres);
                listview.setAdapter(adapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, final long id) {
                        final String item = (String) parent.getItemAtPosition(position);

                        Log.v("nombre", tecnicos.getResult().getValue().get(position).getTitle());
                        Log.v("link", tecnicos.getResult().getValue().get(position).getHref());

                        String urlEquipo = tecnicos.getResult().getValue().get(position).getHref();

                        Intent newIntent = new Intent("android.intent.action.TECNICO_DETALLE");
                        newIntent.putExtra("user",user);
                        newIntent.putExtra("pass",pass);
                        newIntent.putExtra("url",urlEquipo);

                        startActivity(newIntent);


                    }
                });       }});

    }

    private void mostrarMensaje(CharSequence text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
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

    private class FillListOfTecnicosThread extends AsyncTask<Void, Void, Tecnicos> {
        @Override
        protected Tecnicos doInBackground(Void...  params) {
            try {
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
                Log.v("ingresando URL",url);
                RestTemplate restTemplate = new RestTemplate();
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                restTemplate.getMessageConverters().add(converter);
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                        .queryParam("apellido", apellido)
                        .queryParam("nombre", nombre);
                HttpEntity<?> entity = new HttpEntity<>(headers);
                ResponseEntity<Tecnicos> response = restTemplate.exchange(builder.build().encode().toUri(),HttpMethod.GET, requestEntity, Tecnicos.class);

                // Make the HTTP GET request to the Basic Auth protected URL
                //ResponseEntity<Clientes> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Clientes.class);
                // Equipos equipos=restTemplate.getForObject(url,Equipos.class);
                //  Greeting greeting = restTemplate.getForObject("http://rest-service.guides.spring.io/greeting", Greeting.class);
                Tecnicos tecnicos = response.getBody();

                Log.v("listado Tecnicos contiene", tecnicos.getResult().getValue().size() +"");
                int arraySize = tecnicos.getResult().getValue().size();
                RestLink[] tecnicosArray = new RestLink[arraySize];
                for (int i=0; i< arraySize;i++){
                    tecnicosArray[i] = tecnicos.getResult().getValue().get(i);
                    Log.v("Tecnico Encontrado", tecnicosArray[i].getTitle());
                }
                return tecnicos;
            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
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
