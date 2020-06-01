package com.example.trabalhoprogmedico;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class consulta extends AppCompatActivity {
    SQLiteDatabase db;
    EditText consultaInicio , consultaFim , consultaObservacao;
    Spinner pacienteSpinner , medicoSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        pacienteSpinner = findViewById(R.id.pacienteConsultaSpinner);
        medicoSpinner = findViewById(R.id.medicoConsultaSpinner);

        consultaInicio = findViewById(R.id.inicio);
        consultaFim = findViewById(R.id.fim);
        consultaObservacao = findViewById(R.id.obervacao);

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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });







    }
    private void salvarBD () {
        String inicio = consultaInicio.getText().toString().trim();
        String fim = consultaFim.getText().toString().trim();
        String observacao = consultaObservacao.getText().toString().trim();

        if(inicio.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe a data da consulta!", Toast.LENGTH_LONG).show();
        } else if (fim.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe o fim da data de consulta!", Toast.LENGTH_LONG).show();
        }else if (observacao.equals("")) {
            Toast.makeText(getApplicationContext(), "Por favor, informe as Observacoes!", Toast.LENGTH_LONG).show();
        } else {
            db = openOrCreateDatabase("consultas.db", Context.MODE_PRIVATE, null);
            StringBuilder sql = new StringBuilder();
            String medico = medicoSpinner.getSelectedItem().toString();
            String paciente = pacienteSpinner.getSelectedItem().toString();
            sql.append("INSERT INTO consulta(paciente_id,medico_id,data_hora_inicio,data_hora_fim,observacao) VALUES (");
            sql.append("'" + inicio + "', ");
            sql.append(fim + ", ");
            sql.append("'" + observacao + "'");
            sql.append(medico + ", ");
            sql.append("'" + paciente + "'");
            sql.append(");");

            try {
                db.execSQL(sql.toString());
                Toast.makeText(getApplicationContext(), "Consulta incerida", Toast.LENGTH_LONG).show();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            pacienteSpinner.setSelection(0);
            medicoSpinner.setSelection(0);

            consultaInicio.setText("");
            consultaFim.setText("");
            consultaObservacao.setText("");

            db.close();

        }
    }
}
