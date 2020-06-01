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

public class paciente extends AppCompatActivity {
    SQLiteDatabase db;
    EditText pacienteNome, pacienteLogradouro , pacienteCidade , pacienteNumero , pacienteCelular , pacienteFixo;
    Spinner pacienteSangue , pacienteUf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        pacienteNome = findViewById(R.id.nomePaciente);
        pacienteLogradouro = findViewById(R.id.pacienteLogradouro);
        pacienteCidade = findViewById(R.id.pacienteCidade);
        pacienteNumero = findViewById(R.id.pacienteNumero);
        pacienteCelular = findViewById(R.id.pacienteCelular);
        pacienteFixo = findViewById(R.id.pacienteFixo);

        pacienteSangue = findViewById(R.id.sangueSpinner);
        pacienteUf = findViewById(R.id.pacienteUfSpinner);

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
        pacienteUf.setAdapter(spArrayAdapter);
        String[] sangue = new String[] {
                "A+",
                "A-",
                "B+",
                "B-",
                "AB+",
                "AB-",
                "O+",
                "O-"
        };
        ArrayAdapter<String> sppArrayAdapter =
                new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, sangue);
        pacienteSangue.setAdapter(sppArrayAdapter);

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
                Intent i = new Intent(getApplicationContext(), medico.class);
                startActivity(i);
            }
        });


    }
    private void salvarBD () {
        String nome = pacienteNome.getText().toString().trim();
        String Logradouro = pacienteLogradouro.getText().toString().trim();
        String cidade = pacienteCidade.getText().toString().trim();
        String numero = pacienteNumero.getText().toString().trim();
        String celular = pacienteCelular.getText().toString().trim();
        String fixo = pacienteFixo.getText().toString().trim();
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
            String sangue = pacienteSangue.getSelectedItem().toString();
            String UF = pacienteUf.getSelectedItem().toString();
            sql.append("INSERT INTO paciente(nome,grp_sanguineo,logradouro,numero,cidade,uf,celular,fixo) VALUES (");
            sql.append("'" + nome + "', ");
            sql.append(Logradouro + ", ");
            sql.append("'" + cidade + "'");
            sql.append(numero + ", ");
            sql.append("'" + celular + "'");
            sql.append(fixo + ", ");
            sql.append("'" + sangue + "'");
            sql.append(UF + ", ");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Paciente inserido", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            pacienteNome.setText("");
            pacienteLogradouro.setText("");
            pacienteCidade.setText("");
            pacienteNumero.setText("");
            pacienteCelular.setText("");
            pacienteFixo.setText("");

            pacienteSangue.setSelection(0);
            pacienteUf.setSelection(0);
            db.close();

        }
    }
}
