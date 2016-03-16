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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.gisbert.it.pedidos.R;
import com.gisbert.it.pedidos.serv.PedidoItems;
import com.gisbert.it.pedidos.serv.Pedidos;
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
 * Created by Juli on 6/3/2016.
 */
public class PedidoItemListActivity extends Activity{
    String url;
    String user;
    String pass;
    String estado;
    PedidoItems pedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        ListView listview = (ListView) findViewById(R.id.list_items);

        Intent intent = getIntent();
        url =  intent.getStringExtra("url")+ "/collections/pedidoItem";
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");
        estado=intent.getStringExtra("estado");

        try {
            pedidos = new FillListOfPedidoItemsThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<RestLink> LinksPedidoItemsList = null;
        final List<String> listNombres = new ArrayList<String>();
        if ((pedidos !=null)&&(pedidos.getValue().size()!=0)) {
            LinksPedidoItemsList = pedidos.getValue();

            //tomar nombres de los alumnos

            for (RestLink pedidoLink : LinksPedidoItemsList) {
                listNombres.add(pedidoLink.getTitle());
            }
        }else mostrarMensaje("No Existen ITEMS");

        //llenar la lista
      /*  final StableArrayAdapter adapter = new StableArrayAdapter(getBaseContext(),
                android.R.layout.simple_list_item_1, listNombres);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, listNombres);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        /*
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {




            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, final long id) {
                final String item = (String) parent.getItemAtPosition(position);

                Log.v("nombre", pedidos.getValue().get(position).getTitle());
                Log.v("link", pedidos.getValue().get(position).getHref());

                String urlEquipo = pedidos.getValue().get(position).getHref();

                Intent newIntent = new Intent("android.intent.action.PEDIDO_ITEM_DETALLE");
                newIntent.putExtra("user",user);
                newIntent.putExtra("pass",pass);
                newIntent.putExtra("link",pedidos.getValue().get(position).getHref());

                startActivity(newIntent);


            }
        });*/

    }
    public void Guardar()
    {
        setContentView(R.layout.activity_items_list);
        ListView lv = (ListView)findViewById(R.id.list_items);
        int count = lv.getAdapter().getCount();


        for (int i = 0; i < count; i++)
        {
            ViewGroup row = (ViewGroup) lv.getChildAt(i);
            CheckBox tvTest = (CheckBox) row.findViewById(R.id.checkedTextView1);
            //  Get your controls from this ViewGroup and perform your task on them =)

            if (tvTest.isChecked())
            {
                // DO SOMETHING
            }

        }
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

    private class FillListOfPedidoItemsThread extends AsyncTask<Void, Void, PedidoItems> {
        @Override
        protected PedidoItems doInBackground(Void...  params) {
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
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
                HttpEntity<?> entity = new HttpEntity<>(headers);
                ResponseEntity<PedidoItems> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, requestEntity, PedidoItems.class);
                PedidoItems pedidos = response.getBody();
                Log.v("listado Equipos contiene", pedidos.getValue().size() +"");
                int arraySize = pedidos.getValue().size();
                RestLink[] equiposArray = new RestLink[arraySize];
                for (int i=0; i< arraySize;i++){
                    equiposArray[i] = pedidos.getValue().get(i);
                    Log.v("Equipo Encontrado", equiposArray[i].getTitle());
                    Log.v("URL", equiposArray[i].getHref());
                }
                return pedidos;

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
    private void mostrarMensaje(CharSequence text) {
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void onClickButton_Salir(View view) {

        finish();
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
