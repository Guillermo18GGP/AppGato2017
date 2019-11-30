package interfaz.app3.pm1.juegodelgato;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Configuracion extends AppCompatActivity {
    private RadioButton sal,maq,mul;
    private EditText j1,j2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        sal = (RadioButton)findViewById(R.id.salir);
        maq = (RadioButton)findViewById(R.id.maquina);
        mul = (RadioButton)findViewById(R.id.multi);
        j1 = (EditText)findViewById(R.id.jugador1);
        j2 = (EditText)findViewById(R.id.jugador2);
    }
    public void onRadioButtonClicked(View view) {
        switch(view.getId()){
            case R.id.multi:
                if(mul.isChecked()==true) {
                    String jug1 = j1.getText().toString();
                    String jug2 = j2.getText().toString();
                    if (jug1.equals("")||jug2.equals("") || jug1.equals(jug2)) {
                        Toast.makeText(this, "Ingresa ambos campos/ Campos Iguales", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent i = new Intent(this,YovsMaquina.class);
                        i.putExtra("JUGADOR1",jug1);
                        i.putExtra("JUGADOR2",jug2);
                        startActivity(i);
                    }
                }
            case R.id.maquina:
                if(maq.isChecked()==true) {
                    String jug1 = j1.getText().toString();
                    if(jug1.equals("")){
                        Toast.makeText(this,"Ingresa el jugador",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent i = new Intent(this,YovsMaquina.class);
                        i.putExtra("JUGADOR1",jug1);
                        startActivity(i);
                    }
                }
            case R.id.salir:
                if(sal.isChecked()==true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Configuracion.this);
                    builder.setMessage("Seguro que desea Salir?")
                            .setTitle("Alerta");
                    builder.setNegativeButton("Cancelar", null);
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.show();
                }
        }
    }
}
