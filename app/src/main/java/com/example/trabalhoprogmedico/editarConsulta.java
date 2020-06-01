package com.example.trabalhoprogmedico;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class editarConsulta extends AppCompatActivity {
    SQLiteDatabase db;

    EditText dataInicio , dataFim , observacao;
    Spinner paciente, medico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_consulta);

        dataInicio = findViewById(R.id.editInicio);
        dataFim = findViewById(R.id.editFim);
        observacao = findViewById(R.id.editObervacao);

        paciente = findViewById(R.id.editPacienteConsultaSpinner);
        medico = findViewById(R.id.editMedicoConsultaSpinner);

        Intent valores = getIntent();
        dataInicio.setText(valores.getStringExtra("data_hora_inicio"));
        dataFim.setText(valores.getStringExtra("data_hora_fim"));
        observacao.setText(valores.getStringExtra("observacao"));
        final String id = valores.getStringExtra("id");

        Button clickEditar = findViewById(R.id.editButtonConsultaEditar);
        clickEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarBD(id);
            }
        });

        Button clickExcluir = findViewById(R.id.editButtonConsultaExcluir);
        clickExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirBD(id);
            }
        });

    }

    private void salvarBD(String id) {
        String inicio = dataInicio.getText().toString().trim();
        String fim = dataFim.getText().toString().trim();
        String obs = observacao.getText().toString().trim();
        if(inicio.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o inicio da consulta!", Toast.LENGTH_LONG).show();
        } else if (fim.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o fim da consulta!", Toast.LENGTH_LONG).show();
        } else if (obs.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe as observacoes!", Toast.LENGTH_LONG).show();
        }else {
            db = openOrCreateDatabase("consultas.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String med = medico.getSelectedItem().toString();
            String pac = paciente.getSelectedItem().toString();
            sql.append("UPDATE consulta SET ");
            sql.append("paciente_id = '" + pac + "', ");
            sql.append("medico_id = '" + med + "' ");
            sql.append("data_hora_inicio = " + inicio + ", ");
            sql.append("data_hora_fim = '" + fim + "' ");
            sql.append("observacao = '" + obs + "' ");
            sql.append("WHERE _id = " + id + ";");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Consulta atualizada", Toast.LENGTH_LONG).show();
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
        sql.append("DELETE FROM consulta ");
        sql.append("WHERE _id = " + id + ";");
        try {
            db.execSQL(sql.toString());
            Toast.makeText(getApplicationContext(), "Consulta excluída", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(getApplicationContext(), listar.class);
        startActivity(i);
        db.close();
    }
}
