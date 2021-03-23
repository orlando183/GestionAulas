package com.example.gestionaulas;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.gestionaulas.model.Aula;
import com.example.gestionaulas.model.Horario;
import com.example.gestionaulas.model.Reserva;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    static List<Horario> listaHora = new ArrayList<>();
    static List<Horario> listaDia = new ArrayList<>();
    static List<Reserva> listaReservaHora = new ArrayList<>();
    static List<Reserva> listaReservaDia = new ArrayList<>();
    List<Reserva> listaReservaFecha = new ArrayList<>();
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static SimpleDateFormat formatoFecha = new SimpleDateFormat(FORMATO_FECHA, Locale.getDefault());
    public static List<String> aulasOcupadas = new ArrayList<>();
    public static List<String> aulasReservadas = new ArrayList<>();
    static Date date1;
    static Date date2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        mostrarAulas(1,2);


    }
    public static void mostrarAulas(int s, int s1) {
        int periodo = s;
        int dia = s1;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Horarios").orderByChild("periodo").equalTo(periodo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaHora.clear();
                aulasOcupadas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                ) {
                    Horario h = snapshot.getValue(Horario.class);
                    listaHora.add(h);
                    compararHorario(dia);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
    public static void compararHorario(int dia) {
        listaDia.clear();
        for (Horario h : listaHora) {
            Horario mC = h;
            if (mC.getDia() == dia) {
                Log.d("FIlTRADO", mC.toString());
                listaDia.add(mC);
            }
        }
        for (Horario h2 : listaDia) {
            Horario ocu = h2;
            if (ocu.getCodigoAula().equals("NULL")) {
                Log.d("DIS", "DISPONIBLE");
            } else {
                aulasOcupadas.add(ocu.getCodigoAula());

            }
        }
        Set<String> hashSet = new HashSet<String>(aulasOcupadas);
        aulasOcupadas.clear();
        aulasOcupadas.addAll(hashSet);
        Log.d("AGREGADOO", String.valueOf(aulasOcupadas.size()));

    }
    public static void mostrarReservas(int s, int s1) {

        int periodo = s;
        int dia = s1;
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Reservas").orderByChild("periodo").equalTo(periodo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaReservaHora.clear();
                aulasReservadas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()
                ) {
                    Reserva h = snapshot.getValue(Reserva.class);
                    Log.d("RESERVA", h.toString());
                    listaReservaHora.add(h);
                    Log.d("AGREGADOORESERVA", String.valueOf(listaReservaHora.size()));
                    compararReserva(dia);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

  public static void compararReserva(int dia) {

        listaReservaDia.clear();
        Date fechaactual = new Date();
        String fecha = formatoFecha.format(fechaactual);
        try {
            date1 = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Reserva h : listaReservaHora) {
            Reserva mC = h;

            if (mC.getDia() == dia) {
                try {
                    date2 = new SimpleDateFormat("dd/MM/yyyy").parse(h.getFechafin());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (date2.after(date1)) {
                    Log.d("RERE", mC.getCodigoAula());
                    aulasReservadas.add(mC.getCodigoAula());
                }


                Set<String> hashSet2 = new HashSet<String>(aulasReservadas);
                aulasReservadas.clear();
                aulasReservadas.addAll(hashSet2);
                Log.d("AGREGADORESERVA", String.valueOf(aulasReservadas.size()));

            }
        }
    }
    public static String obtenerDia(String s) {
        String res;
        switch (s) {
            case "Lunes":
                res = "1";
                break;
            case "Martes":
                res = "2";
                break;
            case "Miercoles":
                res = "3";
                break;
            case "Jueves":
                res = "4";
                break;
            case "Viernes":
                res = "5";
                break;
            default:
                res = "0";

        }
        return res;

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser()!=null){
            Navigation.findNavController(this, R.id.fragment).navigate(R.id.action_global_consultaFragment2);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_aulas,menu);
        return  true;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_perfil:
                Navigation.findNavController(this, R.id.fragment).navigate(R.id.action_global_cuentaFragment);

                return true;
            case R.id.menu_salir:
                salir();
                return  true;
            case R.id.menu_consulta:
                Navigation.findNavController(this, R.id.fragment).navigate(R.id.action_global_consultaFragment2);
                return  true;
            case R.id.menu_reservas:
                Navigation.findNavController(this, R.id.fragment).navigate(R.id.action_global_listaReservaFragment);
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private  void salir() {
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Seguro que quieres salir?");
        builder.setTitle("Salir");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

}