package br.com.integrador.adm.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

import br.com.integrador.adm.R;

public class VisualActivity extends AppCompatActivity {
    private PhotoView ftescolhida;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual);

        ftescolhida = findViewById(R.id.ftescolhida);
        PhotoView photoView = (PhotoView) findViewById(R.id.ftescolhida);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            byte[] dadosdaimagem = bundle.getByteArray("fotoescolhida");
            imagem = BitmapFactory.decodeByteArray(dadosdaimagem, 0, dadosdaimagem.length);

            photoView.setImageBitmap(imagem);
        }
    }
}
