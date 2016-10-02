package br.com.anascoding.eventoapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetalheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Intent intent = getIntent();
        final ItemVideo itemVideo = (ItemVideo) intent.getSerializableExtra("AULA");

        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setText(itemVideo.getTitulo());

        TextView data = (TextView) findViewById(R.id.data);
        data.setText(itemVideo.getData());

        Button btnURL = (Button) findViewById(R.id.btnURL);
        btnURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse(itemVideo.getUrl()));
                startActivity(intentNavegador);
            }
        });
    }
}
