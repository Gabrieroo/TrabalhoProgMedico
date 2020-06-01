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

public class editarMedico extends AppCompatActivity {
    SQLiteDatabase db;
    EditText medico , crm , logradouro , numero, telefone , celular, cidade;
    Spinner Uf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_medico);

        medico = findViewById(R.id.editMedicoNome);
        crm = findViewById(R.id.editCrm);
        logradouro = findViewById(R.id.editMedicoLogradouro);
        numero = findViewById(R.id.editMedicoNumero);
        telefone = findViewById(R.id.editMedicoFixo);
        celular = findViewById(R.id.editMedicoCelular);
        cidade = findViewById(R.id.editMedicoCidade);

        Uf = findViewById(R.id.editUfMedicoSpinner);
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

        Uf.setAdapter(spArrayAdapter);

        Intent valores = getIntent();
        medico.setText(valores.getStringExtra("nome"));
        crm.setText(valores.getStringExtra("crm"));
        logradouro.setText(valores.getStringExtra("logradouro"));
        numero.setText(valores.getStringExtra("numero"));
        telefone.setText(valores.getStringExtra("fixo"));
        celular.setText(valores.getStringExtra("celular"));
        cidade.setText(valores.getStringExtra("cidade"));


        final String id = valores.getStringExtra("id");

        Button clickEditar = findViewById(R.id.editButtonMedicoEditar);
        clickEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD(id);
            }
        });

        Button clickExcluir = findViewById(R.id.editButtonMedicoExcluir);
        clickExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirBD(id);
            }
        });


    }
    private void salvarBD(String id) {
        String nomeMedico = medico.getText().toString().trim();
        String medicoCrm = crm.getText().toString().trim();
        String medicoLogradouro = logradouro.getText().toString().trim();
        String medicoNumero = numero.getText().toString().trim();
        String medicoTelefone = telefone.getText().toString().trim();
        String medicoCelular = celular.getText().toString().trim();
        String medicoCidade = cidade.getText().toString().trim();
        if(nomeMedico.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o Nome do Medico!", Toast.LENGTH_LONG).show();
        } else if (medicoCrm.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
        } else if (medicoLogradouro.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o logradouro!", Toast.LENGTH_LONG).show();
        }else if (medicoNumero.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o numero!", Toast.LENGTH_LONG).show();
        }else if (medicoTelefone.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o telefone!", Toast.LENGTH_LONG).show();
        }else if (medicoCelular.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        }else if (medicoCidade.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a cidade!", Toast.LENGTH_LONG).show();
        }else {
            db = openOrCreateDatabase("consultas.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String ufs = Uf.getSelectedItem().toString();

            sql.append("UPDATE medico SET ");
            sql.append("nome = '" + nomeMedico + "', ");
            sql.append("crm = '" + medicoCrm + "' ");
            sql.append("logradouro = " + medicoLogradouro + ", ");
            sql.append("numero = '" + medicoNumero + "' ");
            sql.append("cidade = '" + medicoCidade + "' ");
            sql.append("uf = '" + ufs + "' ");
            sql.append("celular = '" + medicoCelular + "' ");
            sql.append("fixo = '" + medicoTelefone + "' ");
            sql.append("WHERE _id = " + id + ";");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Medico atualizado", Toast.LENGTH_LONG).show();
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
        sql.append("DELETE FROM medico ");
        sql.append("WHERE _id = " + id + ";");
        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Medico excluído", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(getApplicationContext(), listar.class);
        startActivity(i);
        db.close();
    }
}
