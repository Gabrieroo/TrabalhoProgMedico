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

public class editarPaciente extends AppCompatActivity {
    SQLiteDatabase db;
    EditText paciente, logradouro ,cidade , numero , telefone, celular;
    Spinner pacienteSangue , uf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_paciente);

        paciente = findViewById(R.id.editNomePaciente);
        logradouro = findViewById(R.id.editPacienteLogradouro);
        cidade = findViewById(R.id.editPacienteCidade);
        numero = findViewById(R.id.editPacienteNumero);
        telefone = findViewById(R.id.editPacienteFixo);
        celular = findViewById(R.id.editPacienteCelular);

        pacienteSangue = findViewById(R.id.editSangueSpinner);
        uf  =findViewById(R.id.editUfSpinner);

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

        uf.setAdapter(spArrayAdapter);

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

        Intent valores = getIntent();
        paciente.setText(valores.getStringExtra("nome"));
        logradouro.setText(valores.getStringExtra("crm"));
        cidade.setText(valores.getStringExtra("logradouro"));
        numero.setText(valores.getStringExtra("numero"));
        telefone.setText(valores.getStringExtra("fixo"));
        celular.setText(valores.getStringExtra("celular"));



        final String id = valores.getStringExtra("id");

        Button clickEditar = findViewById(R.id.editButtonPacienteEditar);
        clickEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD(id);
            }
        });

        Button clickExcluir = findViewById(R.id.editButtonPacienteExcluir);
        clickExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirBD(id);
            }
        });




    }

    private void salvarBD(String id) {
        String nomePaciente = paciente.getText().toString().trim();
        String pacienteLogradouro = logradouro.getText().toString().trim();
        String pacienteCidade = cidade.getText().toString().trim();
        String pacienteNumero = numero.getText().toString().trim();
        String pacienteTelefone = telefone.getText().toString().trim();
        String pacienteCelular = celular.getText().toString().trim();



        if(nomePaciente.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o nome do Paciente!", Toast.LENGTH_LONG).show();
        } else if (pacienteLogradouro.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o logradouro!", Toast.LENGTH_LONG).show();
        } else if (pacienteCidade.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a cidade!", Toast.LENGTH_LONG).show();
        }else if (pacienteNumero.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o numero!", Toast.LENGTH_LONG).show();
        }else if (pacienteTelefone.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone!", Toast.LENGTH_LONG).show();
        }else if (pacienteCelular.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        }else {
            db = openOrCreateDatabase("consultas.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String ufs = uf.getSelectedItem().toString();
            String sangue = pacienteSangue.getSelectedItem().toString();

            sql.append("UPDATE paciente SET ");
            sql.append("nome = '" + nomePaciente + "', ");
            sql.append("logradouro = '" + pacienteLogradouro + "' ");
            sql.append("cidade = " + pacienteCidade + ", ");
            sql.append("numero = '" + pacienteNumero + "' ");
            sql.append("fixo = '" + pacienteTelefone + "' ");
            sql.append("uf = '" + ufs + "' ");
            sql.append("celular = '" + pacienteCelular + "' ");
            sql.append("grp_sanguineo = '" + sangue + "' ");
            sql.append("WHERE _id = " + id + ";");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Paciente atualizado", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            Intent i = new Intent(getApplicationContext(), listar.class);
            startActivity(i);
            db.close();
        }
    }
    private void excluirBD(String id) {
        db = openOrCreateDatabase("consultas.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM paciente ");
        sql.append("WHERE _id = " + id + ";");
        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Paciente excluído", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(getApplicationContext(), listar.class);
        startActivity(i);
        db.close();
    }
}
