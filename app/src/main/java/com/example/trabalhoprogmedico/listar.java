package com.example.trabalhoprogmedico;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class listar extends AppCompatActivity {
    SQLiteDatabase db;
    ListView lvConsultas, lvMedicos, lvPacientes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        lvConsultas = findViewById(R.id.lvConsultas);
        listarConsultas();

        lvConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvConsultas.getChildAt(position);
                TextView paciente = v.findViewById(R.id.editPacienteConsultaSpinner);
                TextView medico = v.findViewById(R.id.editMedicoConsultaSpinner);
                TextView inicio = v.findViewById(R.id.editInicio);
                TextView fim = v.findViewById(R.id.editFim);
                TextView observacoes = v.findViewById(R.id.editObervacao);

                Intent i = new Intent(getApplicationContext(), editarConsulta.class);
                i.putExtra("paciente_id", paciente.getText().toString());
                i.putExtra("medico_id", medico.getText().toString());
                i.putExtra("data_hora_inicio", inicio.getText().toString());
                i.putExtra("data_hora_fim", fim.getText().toString());
                i.putExtra("observacao", observacoes.getText().toString());
                startActivity(i);
            }
        });

        lvMedicos = findViewById(R.id.lvMedicos);
        listarMedicos();
        lvMedicos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvMedicos.getChildAt(position);
                TextView nomeMedico = v.findViewById(R.id.editMedicoNome);
                TextView crm = v.findViewById(R.id.editCrm);
                TextView medicoLogradouro = v.findViewById(R.id.editMedicoLogradouro);
                TextView medicoNumero = v.findViewById(R.id.editMedicoNumero);
                TextView medicoFixo = v.findViewById(R.id.editMedicoFixo);
                TextView medicoCelular = v.findViewById(R.id.editMedicoCelular);
                TextView medicoCidade = v.findViewById(R.id.editMedicoCidade);
                TextView medicoUf = v.findViewById(R.id.editUfMedicoSpinner);

                Intent i = new Intent(getApplicationContext(), editarConsulta.class);
                i.putExtra("nome", nomeMedico.getText().toString());
                i.putExtra("crm", crm.getText().toString());
                i.putExtra("logradouro", medicoLogradouro.getText().toString());
                i.putExtra("numero", medicoNumero.getText().toString());
                i.putExtra("fixo", medicoFixo.getText().toString());
                i.putExtra("celular", medicoCelular.getText().toString());
                i.putExtra("cidade", medicoCidade.getText().toString());
                i.putExtra("uf", medicoUf.getText().toString());
                startActivity(i);
            }
        });

        lvPacientes = findViewById(R.id.lvPacientes);
        lvPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View v = lvPacientes.getChildAt(position);
                TextView nomePaciente = v.findViewById(R.id.editNomePaciente);
                TextView sangue = v.findViewById(R.id.editSangueSpinner);
                TextView pacienteLogradouro = v.findViewById(R.id.editPacienteLogradouro);
                TextView pacienteNumero = v.findViewById(R.id.editPacienteNumero);
                TextView pacienteFixo = v.findViewById(R.id.editPacienteFixo);
                TextView pacienteCelular = v.findViewById(R.id.editPacienteCelular);
                TextView pacienteCidade = v.findViewById(R.id.editPacienteCidade);
                TextView pacienteUf = v.findViewById(R.id.editUfSpinner);

                Intent i = new Intent(getApplicationContext(), editarConsulta.class);
                i.putExtra("nome", nomePaciente.getText().toString());
                i.putExtra("grp_sanguineo", sangue.getText().toString());
                i.putExtra("logradouro", pacienteLogradouro.getText().toString());
                i.putExtra("numero", pacienteNumero.getText().toString());
                i.putExtra("cidade", pacienteFixo.getText().toString());
                i.putExtra("uf", pacienteCelular.getText().toString());
                i.putExtra("celular", pacienteCidade.getText().toString());
                i.putExtra("fixo", pacienteUf.getText().toString());
                startActivity(i);
            }
        });

    }

    private void listarConsultas () {
        db = openOrCreateDatabase("consultas.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM consulta;");
        Cursor dados = db.rawQuery(sql.toString(), null);
        String[] from = {"paciente_id", "medico_id", "data_hora_inicio", "data_hora_fim", "observacao"};
        int[] to = {R.id.editPacienteConsultaSpinner, R.id.editMedicoConsultaSpinner, R.id.editInicio, R.id.editFim, R.id.editObervacao};

        SimpleCursorAdapter scAdapter =
                new SimpleCursorAdapter(getApplicationContext(), R.layout.dados, dados, from, to, 0);

        lvConsultas.setAdapter(scAdapter);
        db.close();
    }
    private void listarMedicos () {
        db = openOrCreateDatabase("consultas.db", Context.MODE_PRIVATE, null);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM medico;");
        Cursor dados = db.rawQuery(sql.toString(), null);
        String[] from = {"nome", "crm", "logradouro", "numero", "cidade","uf","celular","fixo"};
        int[] to = {R.id.editMedicoNome, R.id.editCrm, R.id.editMedicoLogradouro, R.id.editMedicoNumero, R.id.editMedicoCidade, R.id.editUfMedicoSpinner, R.id.editMedicoCelular, R.id.editMedicoFixo};

        db.close();
    }
}
