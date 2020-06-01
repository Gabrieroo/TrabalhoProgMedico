package com.example.trabalhoprogmedico;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class medico extends AppCompatActivity {
    SQLiteDatabase db;
    EditText nomeMedico , medicoCrm , logradouroMedico , numeroMedico , fixoMedico , celularMedico, medicoCidade;
    Spinner ufMedico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medico);

        nomeMedico = findViewById(R.id.medicoNome);
        medicoCrm = findViewById(R.id.crm);
        logradouroMedico = findViewById(R.id.MedicoLogradouro);
        numeroMedico = findViewById(R.id.medicoNumero);
        fixoMedico = findViewById(R.id.medicoFixo);
        celularMedico = findViewById(R.id.medicoCelular);
        medicoCidade = findViewById(R.id.medicoCidade);

        ufMedico = findViewById(R.id.UfMedicoSpinner);

        String[] UFS = new String[] {
                "Rondônia",
                "Acre",
                "Amazonas",
                "Roraima",
                "Pará",
                "Amapá",
                "Tocantins",
                "Maranhão",
                "Piauí",
                "Ceará",
                "Rio Grande do Norte",
                "Paraíba",
                "Pernambuco",
                "Alagoas",
                "Sergipe",
                "Bahia",
                "Minas Gerais",
                "Espírito Santo",
                "Rio de Janeiro",
                "São Paulo",
                "Paraná",
                "Santa Catarina",
                "Rio Grande do Sul",
                "Mato Grosso do Sul",
                "Mato Grosso",
                "Goiás",
                "Distrito Federal"
        };
        ArrayAdapter<String> spArrayAdapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, UFS);
        ufMedico.setAdapter(spArrayAdapter);

        Button clickAdicionar = findViewById(R.id.button3);
        clickAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD();
            }
        });

        clickAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), consulta.class);
                startActivity(i);
            }
        });



    }
    private void salvarBD () {
        String nome = nomeMedico.getText().toString().trim();
        String crm = medicoCrm.getText().toString().trim();
        String Logradouro = logradouroMedico.getText().toString().trim();
        String cidade = medicoCidade.getText().toString().trim();
        String numero = numeroMedico.getText().toString().trim();
        String celular = celularMedico.getText().toString().trim();
        String fixo = fixoMedico.getText().toString().trim();
        if(nome.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (Logradouro.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o logradouro!", Toast.LENGTH_LONG).show();
        }else if (cidade.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a cidade!", Toast.LENGTH_LONG).show();
        }else if (numero.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o numero!", Toast.LENGTH_LONG).show();
        }else if (celular.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        }else if (fixo.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone fixo!", Toast.LENGTH_LONG).show();
        } else {
            db = openOrCreateDatabase("consultas.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String UF = ufMedico.getSelectedItem().toString();
            sql.append("INSERT INTO medico(nome,crm,logradouro,numero,cidade,uf,celular,fixo) VALUES (");
            sql.append("'" + nome + "', ");
            sql.append(crm + ", ");
            sql.append("'" + Logradouro + "'");
            sql.append(numero + ", ");
            sql.append("'" + cidade + "'");
            sql.append(UF + ", ");
            sql.append("'" + celular + "'");
            sql.append(fixo + ", ");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Medico inserido", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            nomeMedico.setText("");
            medicoCrm.setText("");
            logradouroMedico.setText("");
            numeroMedico.setText("");
            fixoMedico.setText("");
            celularMedico.setText("");
            medicoCidade.setText("");

            ufMedico.setSelection(0);

            db.close();

        }
    }
}
