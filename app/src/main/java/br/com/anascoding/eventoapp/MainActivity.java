package br.com.anascoding.eventoapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ItemVideoAdapter adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lista = (ListView) findViewById(R.id.lista);
        adapater = new ItemVideoAdapter(this, new ArrayList<ItemVideo>());
        lista.setAdapter(adapater);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetalheActivity.class);
                ItemVideo aula = (ItemVideo) parent.getItemAtPosition(position);
                intent.putExtra("AULA", aula);
                startActivity(intent);
            }
        });

        new EventoTask().execute();

        //final String[] arrayAulas = getResources().getStringArray(R.array.array_aulas);

        /*final List<ItemVideo> itemVideos = new ArrayList<>();
        itemVideos.add(new ItemVideo("Principais erros", "26/09/2016", "http://"));
        itemVideos.add(new ItemVideo("Videoaula pratica 1", "28/09/2016", "http://"));
        itemVideos.add(new ItemVideo("Videoaula pratica 2", "29/09/2016", "http://"));
        itemVideos.add(new ItemVideo("Duvidas respondidas", "30/09/2016", "http://"));

        ListView lista = (ListView) findViewById(R.id.lista);

        ItemVideoAdapter adapater = new ItemVideoAdapter(this, itemVideos);

        lista.setAdapter(adapater);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetalheActivity.class);
                ItemVideo aula = itemVideos.get(position);
                intent.putExtra("AULA", aula);
                startActivity(intent);
            }
        });*/
    }

    //Primeiro Void: o tipo dos parâmetros que o método doInBackground recebe
    //Segundo Void: evolução da tarefa onProgressUpdate
    //Terceiro Void: retorno do método doInBackground; o mesmo retorno do doInBackground será o parâmetro do método onPostExecute
    class EventoTask extends AsyncTask<Void, Void, List<ItemVideo>> { //generics do Java.
        @Override
        protected List<ItemVideo> doInBackground(Void... voids) {
            //Executa a tarefa em background; chamada da API
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL("http://private-d88f6f-semanadevandroid.apiary-mock.com/listar");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader((inputStream)));

                String linha;
                StringBuilder buffer = new StringBuilder();
                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                    buffer.append("\n");
                }

                return JsonUtil.fromJson(buffer.toString());
            } catch (Exception e) {
                if (connection != null) {
                    connection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            //Configura a tarefa. Exemplo: mostrar uma barra de progresso na interface do usuário
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Exibe qualquer forma de progresso na interface do usuário enquanto a tarefa ainda está em execução
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(final List<ItemVideo> itemVideos) {
            //O resultado da execução em background é passado para esse método por parâmetro
            adapater.clear();
            adapater.addAll(itemVideos);
            adapater.notifyDataSetChanged();
        }
    }
}
