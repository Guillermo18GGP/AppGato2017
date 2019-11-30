package interfaz.app3.pm1.juegodelgato;
/************************************************
 * Codigo elaborado por: Guillermo García Perez *
 ************************************************/
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class YovsMaquina extends AppCompatActivity {
    String  m2, m3;
    TextView Juega1, Juega2;
    ImageView  tener;
    boolean cambio = true, maquina=true;
    ImageView [][] casillas = new ImageView[3][3];
    String [][] juego = {{"im1","im2","im3"},{"im4","im5","im6"},{"im7","im8","im9"}};
    int count = 0;
    Button ex,about,reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yovs_maquina);
        Juega1 = (TextView)findViewById(R.id.juega1);
        Juega2 = (TextView)findViewById(R.id.juega2);
        ex = (Button)findViewById(R.id.exit);
        about = (Button)findViewById(R.id.acercade);
        reset = (Button)findViewById(R.id.reiniciar);

        /***********************************************
        *Rastreo del ImageView por su ID en la Activity*
        ************************************************/
        casillas[0][0]= (ImageView) findViewById(R.id.im1);
        casillas[0][1]= (ImageView) findViewById(R.id.im2);
        casillas[0][2]= (ImageView) findViewById(R.id.im3);
        casillas[1][0]= (ImageView) findViewById(R.id.im4);
        casillas[1][1]= (ImageView) findViewById(R.id.im5);
        casillas[1][2]= (ImageView) findViewById(R.id.im6);
        casillas[2][0]= (ImageView) findViewById(R.id.im7);
        casillas[2][1]= (ImageView) findViewById(R.id.im8);
        casillas[2][2]= (ImageView) findViewById(R.id.im9);
        Bundle b = getIntent().getExtras();
        String jugador = b.getString("JUGADOR1");
        String jugador2 = b.getString("JUGADOR2");

        if(jugador2==null){//Solo 1 jugador
            Juega1.setText(jugador);
            maquina=true;
        }

        else{//Dos jugadores
            if(jugador!=null && jugador2!=null){
                Juega1.setText(jugador);
                Juega2.setText(jugador2);
                maquina=false;
            }
        }

    }//fin dle metodo onCreate

    public void menu(View v){
        finish();
    }

    public void salir(View v){
        /*********************************************************************
         * Mostrar mensaje de Alerta por si el Usuario desea Salir de la App *
         *********************************************************************/
        AlertDialog.Builder builder = new AlertDialog.Builder(YovsMaquina.this);
        builder.setMessage("Seguro que desea Salir?")
                .setTitle("Alerta");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){//Si pulsa Aceptar
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();//finalizar la app por completo
            }
        });//fin del onClick

        builder.setNegativeButton("Cancelar", null);//Si pulsa cancelar
        builder.show();
    }//Fin del metodo Salir

    protected void onSaveInstanceState(Bundle contenedor) {

        for (int i=0 ; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                contenedor.putString("casillas" + i + j, juego[i][j]);
            }
        }
        contenedor.putInt("count", count);
        contenedor.putBoolean("cambio", cambio);
        contenedor.putBoolean("maquina", maquina);
        super.onSaveInstanceState(contenedor);
    }

    @Override
    public void onRestoreInstanceState(Bundle contenedor) {
        super.onRestoreInstanceState(contenedor);

        for (int i=0 ; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                juego[i][j] = contenedor.getString("casillas" + i + j);
                if(juego[i][j].equals("O")){casillas[i][j].setImageResource(R.drawable.circulo);
                    casillas[i][j].setEnabled(false);}else{
                    if (juego[i][j].equals("x")){
                        casillas[i][j].setImageResource(R.drawable.cruz);
                        casillas[i][j].setEnabled(false);
                    }
                }
            }
        }
        count = contenedor.getInt("count");
        cambio = contenedor.getBoolean("cambio");
        maquina = contenedor.getBoolean("maquina");
    }

    public void seleccionar(View v) {
        if (count<9) {
            tener = (ImageView) findViewById(v.getId());
            tener.setEnabled(false);
            String t = v.getId()+"";
            if (maquina==true) {//Si la maquina fue selecciona
                JugarMaquina(t, tener);
            }
            else {//Si son 2 jugadores
                JugarJugador(t, tener);
            }
            count++;//Quitar un turno
        }
        else{
            Toast.makeText(this, "EMPATE", Toast.LENGTH_LONG).show();
            restartActivity();
        }
    }

    public void JugarMaquina(String t, ImageView tenido)
    {
        maquina=true;
        tenido.setImageResource(R.drawable.cruz);
        añadir("x",t);
        comprobar("x", t, m2);
        if(maquina == true)
        {
            int datob;
            datob = (int)(Math.random()*3)+0;
            int datob2;
            datob2 = (int)(Math.random()*3)+0;

            tratar(datob,datob2, t);
        }else{
            maquina=true;
        }
    }

    public void tratar(int datob, int datob2, String t)
    {
        System.out.println("datos a="+datob+"  b="+datob2);
        if(juego[datob][datob2].equals("O")||juego[datob][datob2].equals("x"))
        {int dato1=0;
            dato1 = (int)(Math.random()*3)+0;
            int dato2=0;
            dato2 = (int)(Math.random()*3)+0;
            tratar(dato1,dato2, t);}
        else {
            casillas[datob][datob2].setImageResource(R.drawable.circulo);
            casillas[datob][datob2].setEnabled(false);
            juego[datob][datob2] = "O";
            count++;
            comprobar("O", t, "Maquina");
        }
    }


    public void JugarJugador(String t, ImageView tenido)
    {
        if(cambio==true)
        {
            Juega2.setBackgroundColor(getResources().getColor(R.color.rosaPAstel));
            Juega1.setBackgroundColor(getResources().getColor(R.color.nigga));
            tenido.setImageResource(R.drawable.cruz);
            añadir("x",t);
            comprobar("x", t, m2);
            cambio = false;
        }
        else{
            Juega1.setBackgroundColor(getResources().getColor(R.color.rosaPAstel));
            Juega2.setBackgroundColor(getResources().getColor(R.color.nigga));
            tenido.setImageResource(R.drawable.circulo);
            añadir("O",t);
            comprobar("O", t, m3);
            cambio = true;
        }
    }

    public void añadir(String simbolo, String t)
    {
        for (int i= 0; i<3;i++)
        {
            for (int j=0; j<3; j++)
            {
                if(Integer.parseInt(t) == casillas[i][j].getId())
                {
                    juego[i][j] = simbolo;
                }
            }
        }
    }

    public void comprobar (String simbolo, String t, String j)
    {
        if(simbolo.equals("x"))
        {
            j = Juega1.getText().toString();
            if (juego[0][0]== "x" && juego[0][1]== "x" && juego [0][2]== "x") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                maquina=false;
                restartActivity();
            }

            if (juego[1][0]== "x" && juego[1][1]== "x" && juego [1][2]== "x") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                maquina=false;
                restartActivity();
            }
            if (juego[2][0]== "x" && juego[2][1]== "x" && juego [2][2]== "x") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                maquina=false;
                restartActivity();
            }
            if (juego[0][0]== "x" && juego[1][0]== "x" && juego [2][0]== "x") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                maquina=false;
                restartActivity();
            }
            if (juego[0][1]== "x" && juego[1][1]== "x" && juego [2][1]== "x") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                maquina=false;
                restartActivity();
            }
            if (juego[0][2]== "x" && juego[1][2]== "x" && juego [2][2]== "x") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                maquina=false;
                restartActivity();
            }
            if (juego[0][0]== "x" && juego[1][1]== "x" && juego [2][2]== "x") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                maquina=false;
                restartActivity();
            }
            if (juego[0][2]== "x" && juego[1][1]== "x" && juego [2][0]== "x") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                maquina=false;
                restartActivity();
            }

        }
        else
        {
            j = Juega2.getText().toString();
            if (juego[0][0]== "O" && juego[0][1]== "O" && juego [0][2]== "O") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                restartActivity();
            }
            if (juego[1][0]== "O" && juego[1][1]== "O" && juego [1][2]== "O") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                restartActivity();
            }
            if (juego[2][0]== "O" && juego[2][1]== "O" && juego [2][2]== "O") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                restartActivity();
            }
            if (juego[0][0]== "O" && juego[1][0]== "O" && juego [2][0]== "O") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                restartActivity();
            }
            if (juego[0][1]== "O" && juego[1][1]== "O" && juego [2][1]== "O") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                restartActivity();
            }
            if (juego[0][2]== "O" && juego[1][2]== "O" && juego [2][2]== "O") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                restartActivity();
            }
            if (juego[0][0]== "O" && juego[1][1]== "O" && juego [2][2]== "O") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                restartActivity();
            }
            if (juego[0][2]== "O" && juego[1][1]== "O" && juego [2][0]== "O") {
                Toast.makeText(this, "Gana "+j, Toast.LENGTH_LONG).show();
                restartActivity();
            }

        }

    }

    public  void restartActivity(){
        count=0;
        if (cambio== true){
            cambio=false;
        }else{
            cambio=true;
        }
        for (int i= 0; i<3;i++) {
            for (int j = 0; j < 3; j++) {
                casillas[i][j].setEnabled(true);
                casillas[i][j].setImageResource(R.drawable.casilla);
                juego[i][j] = "";
            }
        }
    }
    public void reset(View v)
    {
        restartActivity();
    }

}//fin de la clase
