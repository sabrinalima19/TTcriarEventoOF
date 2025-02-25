package com.example.tasktide;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tasktide.DAO.DAO;
import com.example.tasktide.Objetos.Evento;

public class CriarEvento extends AppCompatActivity {

    private EditText edtxtNomeEvento;
    private EditText edtxtQuantHoras;
    private Spinner spnTipoEvento;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtxtNomeEvento = findViewById(R.id.edtxtNomeEvento);
        edtxtQuantHoras = findViewById(R.id.edtxtQuantHoras);
        spnTipoEvento = findViewById(R.id.spnTipoEvento);
        radioGroup = findViewById(R.id.radioGroup);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_evento, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipoEvento.setAdapter(adapter);

        spnTipoEvento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                String horas = calcularHorasComplementares(selectedItem);
                edtxtQuantHoras.setText(horas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edtxtQuantHoras.setText("");
            }
        });

        Button btnProximo = findViewById(R.id.btnProximo);
        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserirEvento();
            }
        });
    }

    private String calcularHorasComplementares(String selectedItem) {
        switch (selectedItem) {
            case "Atividade cultural":
                return "3 horas";
            case "Atividade esportiva":
                return "4 horas";
            case "Colóquio":
            case "Conferência":
            case "Congresso":
            case "Encontro":
            case "Fórum":
                return "5 horas";
            case "Mesa-redonda":
            case "Palestra":
                return "2 horas";
            case "Seminário":
            case "Visita técnica":
            case "Workshop":
                return "5 horas";
            default:
                return "";
        }
    }

    private void inserirEvento() {
        String nomeEvento = edtxtNomeEvento.getText().toString().trim();
        String tipoEvento = spnTipoEvento.getSelectedItem().toString();
        String horasComplementares = edtxtQuantHoras.getText().toString().trim();

        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == -1) {
            Toast.makeText(CriarEvento.this, "Selecione uma modalidade.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
        String modalidade = selectedRadioButton.getText().toString();

        Evento evento = new Evento(nomeEvento, tipoEvento, horasComplementares, modalidade);

        DAO dao = new DAO(this);
        long id = dao.inserirEvento(evento);
        if (id != -1) {
            Log.i("CriarEvento", "Evento inserido com sucesso. ID: " + id);

            // Passar o idEvento para a próxima atividade
            Intent intent = new Intent(CriarEvento.this, SobreParticipantes.class);
            intent.putExtra("ID_EVENTO", id);
            startActivity(intent);
            finish(); // Finaliza a atividade atual para evitar voltar para ela pelo botão de voltar
        } else {
            Log.e("CriarEvento", "Erro ao inserir evento.");
        }
    }
}
