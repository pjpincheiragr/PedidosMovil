package com.gisbert.it.pedidos.actividad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
 * Created by jotero on 6/3/2016.
 */

public class PedidoItemListActivity extends Activity{
    String url;
    String user;
    String pass;
    String estado;
    String itemsList="";
    String itemsList2="";
    String newUrl;
    String newUrl2;
    String urlUpdateEstadoPedido;
    PedidoItems pedidos;
    ListView listview;
    String clave;
    private ArrayList<RowObject> mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

         listview= (ListView) findViewById(R.id.list_items);

        Intent intent = getIntent();
        url =  intent.getStringExtra("url");
        user =  intent.getStringExtra("user");
        pass =  intent.getStringExtra("pass");
        clave=intent.getStringExtra("clave");
        Log.v("ingresando Clave", clave);
        int index=url.indexOf("/objects/");
        urlUpdateEstadoPedido=url.substring(0,index)+"/services/RepositorioPedido/actions/updatePedido/invoke";
        newUrl=url.substring(0,index)+"/services/RepositorioPedidoItem/actions/updatePedidoItemLista/invoke";
        newUrl2=url.substring(0,index)+"/services/RepositorioPedidoItem/actions/updatePedidoItemListaNoResuelto/invoke";
        Log.v("salida",newUrl);
        try {
            pedidos = new FillListOfPedidoItemsThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        mSource = new ArrayList<RowObject>();
        List<RestLink> linksPedidoItemsList = null;
        int size=pedidos.getValue().size();
        if ((pedidos !=null)&&(pedidos.getValue().size()!=0)) {
            linksPedidoItemsList = pedidos.getValue();
            int aux=0;
            //tomar nombres de los items

            for (RestLink pedidoLink : linksPedidoItemsList) {
                mSource.add(new RowObject(aux,false,pedidoLink.getTitle()));
                aux++;
            }
        }else mostrarMensaje("No Existen ITEMS");


        listview = (ListView) findViewById(R.id.list_items);
        listview.setAdapter(new RadioButtonAdapter(getApplicationContext(), mSource));
        marcarItemsResueltos(linksPedidoItemsList);
    }

    public void marcarItemsResueltos(List tittles){
        ListView auxList=(ListView) findViewById(R.id.list_items);
        int count = tittles.size();
        String tittle;
        for (int i = 0; i < count; i++)
        {
            tittle= mSource.get(i).getHeading();
            if (tittle.endsWith("RESUELTO"))
                mSource.get(i).setFirstChecked(true);
        }
    }

    public void guardarNuevosEstados(View view)
    {
        int count = listview.getAdapter().getCount();
        RadioButtonAdapter auxAdapter=(RadioButtonAdapter)listview.getAdapter();
        itemsList=auxAdapter.getStringWithClavesResueltos();
        itemsList2=auxAdapter.getStringWithClavesNoResueltos();
        try {
             new UpdateListPedidoItemThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            new UpdateListPedidoItemThreadNoResueltos().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            new UpdatePedidoEstadoThread().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /*Intent intent = new Intent("android.intent.action.PEDIDO_ITEMS_LIST");

        intent.putExtra("url", url);
        intent.putExtra("user", user);
        intent.putExtra("pass", pass);

        startActivity(intent);*/
        this.finish();


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

    private class UpdateListPedidoItemThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void...  params) {
            try {


                //Services services = null;
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                Log.v("ingresando URL", newUrl);
                RestTemplate restTemplate = new RestTemplate();

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                restTemplate.getMessageConverters().add(converter);
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(newUrl);
                builder.queryParam("lista",itemsList);
                restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, Void.class);

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }

    private class UpdateListPedidoItemThreadNoResueltos extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void...  params) {
            try {


                //Services services = null;
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                Log.v("ingresando URL", newUrl2);
                RestTemplate restTemplate = new RestTemplate();

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                restTemplate.getMessageConverters().add(converter);
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(newUrl2);
                builder.queryParam("lista",itemsList2);
                restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, Void.class);

            } catch (Exception e) {
                Log.e("main_activity", e.getMessage(), e);
            }

            return null;
        }
    }

    private class UpdatePedidoEstadoThread extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void...  params) {
            try {


                //Services services = null;
                Log.v("ingresando User y Pass", user + " : " + pass);
                // Set the username and password for creating a Basic Auth request
                HttpAuthentication authHeader = new HttpBasicAuthentication(user, pass);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                Log.v("ingresando URL", urlUpdateEstadoPedido);
                RestTemplate restTemplate = new RestTemplate();

                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                restTemplate.getMessageConverters().add(converter);
                HttpHeaders headers = new HttpHeaders();
                headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(urlUpdateEstadoPedido);
                Log.v("ingresando Clave", clave);
                builder.queryParam("clave",clave);
                restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, requestEntity, Void.class);

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

    private class RadioButtonAdapter extends ArrayAdapter<RowObject> {

        ArrayList<RowObject> mSource1;

        class ViewHolder {
            RadioGroup rbGroup;
            RadioButton btn1;
            RadioButton btn2;
            TextView tittle;
        }

        private LayoutInflater mInflater;

        public RadioButtonAdapter(Context context, ArrayList<RowObject> mSource) {
            super(context, R.layout.row, mSource);
            mSource1=mSource;
            mInflater = LayoutInflater.from(context);
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.row, null);

                holder = new ViewHolder();
                holder.rbGroup = (RadioGroup) convertView.findViewById(R.id.mRadioGroup);
                holder.btn1 = (RadioButton) convertView.findViewById(R.id.mRadio1);
                holder.btn2 = (RadioButton) convertView.findViewById(R.id.mRadio2);
                holder.tittle=(TextView) convertView.findViewById(R.id.nombre_item);
                String titulo=mSource1.get(position).getHeading();
                titulo=titulo.substring(3);
                titulo=titulo.replace("Resuelto: RESUELTO", "");
                titulo=titulo.replace("Resuelto: PENDIENTE","");
                titulo=titulo.replace("Resuelto: NO_RESUELTO","");
                holder.tittle.setText(titulo);
                convertView.setTag(holder);
                if(mSource1.get(position).getHeading().endsWith("RESUELTO")){
                    holder.btn1.setChecked(true);
                }else{
                    holder.btn1.setChecked(true);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.mRadio1:
                            mSource.get(position).setFirstChecked(true);
                            break;

                        case R.id.mRadio2:
                            mSource.get(position).setFirstChecked(false);
                            break;
                    }
                }
            });

            if (mSource.get(position).isFirstChecked()) {
                holder.btn1.setChecked(true);
                holder.btn2.setChecked(false);
            } else {
                holder.btn1.setChecked(false);
                holder.btn2.setChecked(true);
            }

            return convertView;
        }

        private int getNumberOfFirstCheckedViews() {
            int count = 0;
            for (RowObject object : mSource) {
                if (object.isFirstChecked()) {
                    count++;
                }
            }
            return count;
        }

        private String getStringWithClavesResueltos() {
            int count = 0;
            String salida="";
            for (RowObject object : mSource) {
                if (object.isFirstChecked()) {
                    salida=salida+object.getHeading().charAt(0)+"&";
                }
            }
            //if(salida!="")
            //   salida=salida.substring(0,salida.length()-1);
            return salida;
        }

        private String getStringWithClavesNoResueltos() {
            int count = 0;
            String salida="";
            for (RowObject object : mSource) {
                if (!object.isFirstChecked()) {
                    salida=salida+object.getHeading().charAt(0)+"&";
                }
            }
            //if(salida!="")
             //   salida=salida.substring(0,salida.length()-1);
            return salida;
        }
    }
}
