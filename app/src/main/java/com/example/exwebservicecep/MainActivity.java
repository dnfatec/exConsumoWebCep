package com.example.exwebservicecep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

//Baseado em: https://medium.com/collabcode/consumindo-web-service-no-android-bca54b9e3ab7
//https://viacep.com.br/ exemplo do site

public class MainActivity extends AppCompatActivity {
Button btBuscarFoto;
EditText edtCep, edEndereco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carregaWidgets();
        click();
    }
    private void carregaWidgets(){
        btBuscarFoto=(Button)findViewById(R.id.btnCep);
        edtCep=(EditText)findViewById(R.id.edtCep);
        edEndereco=(EditText)findViewById(R.id.edtEndereco);
    }

    private void click(){
        btBuscarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CEP retornoJson = new HttpService(edtCep.getText().toString()).execute().get();
                    //O retorno do json sera formatado na classe cep, mas ele sera executada
                    //e passara o cep digitado
                    edEndereco.setText(retornoJson.toString());
                    Toast.makeText(getApplicationContext(), retornoJson.getLocalidade(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
