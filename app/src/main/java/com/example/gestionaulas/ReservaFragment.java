package com.example.gestionaulas;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionaulas.model.Horario;
import com.example.gestionaulas.model.Reserva;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;



import static com.example.gestionaulas.ListaFragment.DIA;
import static com.example.gestionaulas.ListaFragment.HORA;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReservaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReservaFragment extends Fragment {

    EditText curso,asig;
    TextView codigoAula,fecha,dia,hora;
    Button calendario,guardar;
    Calendar c;
    DatePickerDialog dpd;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    public static final String ID_DEFECTO = "O";
    String idActual=ID_DEFECTO;
    String aulaEdit,cursoEdit,asigEdit;
    int diaEdit,horaEdit;
    String fechaeditada;
public static final String DEF_AULA="NULL";
    final Reserva[] h = new Reserva[1];
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReservaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReservaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReservaFragment newInstance(String param1, String param2) {
        ReservaFragment fragment = new ReservaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        inicializarFirebase();
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(getContext());
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        mAuth =FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v= inflater.inflate(R.layout.fragment_reserva, container, false);
          dia=v.findViewById(R.id.reserva_dia);
          hora=v.findViewById(R.id.reserva_hora);
        curso=v.findViewById(R.id.reserva_curso);
        asig=v.findViewById(R.id.reserva_asignatura);
         codigoAula=v.findViewById(R.id.reserva_aula);
         fecha=v.findViewById(R.id.reserva_fecha);
        calendario=v.findViewById(R.id.reserva_fechaboton);
        guardar=v.findViewById(R.id.reserva_boton);
        FirebaseUser usuarioActual= FirebaseAuth.getInstance().getCurrentUser();
        String user=usuarioActual.getEmail();
        String auxDia="";
        final String[] reservaFecha = {""};
        int pos=user.indexOf("@");
        final String[] mDia = new String[1];
        final String[] mHora = new String[1];
        final Date[] date1 = new Date[1];
        hora.setText(HORA+"");
        auxDia = devuelveDia(DIA);
        dia.setText(auxDia);
        calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=Calendar.getInstance();
                int dia=c.get(Calendar.DAY_OF_MONTH);
                int mes=c.get(Calendar.MONTH);
                int anio=c.get(Calendar.YEAR);
                dpd=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mAnio, int mMes, int mDia) {
                        fecha.setText(mDia + "/" +(mMes+1) + "/" +mAnio);

                    }
                },anio,mes,dia);
                  dpd.show();

            }
        });
        Bundle bundle=getArguments();
        if(bundle!=null && bundle.containsKey("AULA")){
            codigoAula.setText(bundle.getString("AULA",DEF_AULA));

        }
        if(bundle!=null && bundle.containsKey("ID")) {
            idActual=bundle.getString("ID",ID_DEFECTO);
            diaEdit=bundle.getInt("DIA");
            horaEdit=bundle.getInt("HORA");
            fechaeditada=bundle.getString("FECHA");
            cursoEdit=bundle.getString("CURSO");
            aulaEdit=bundle.getString("AULA");
            asigEdit=bundle.getString("ASIG");

            codigoAula.setText(aulaEdit);
            dia.setText(devuelveDia(diaEdit));
            hora.setText(String.valueOf(horaEdit));
            curso.setText(cursoEdit);
            asig.setText(asigEdit);
            fecha.setText(fechaeditada);
            guardar.setText("MODIFICAR");


        }

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idActual==ID_DEFECTO) {
                    if (curso.getText().toString().isEmpty() || asig.getText().toString().isEmpty() || (fecha.getText()).equals("")) {
                        Toast.makeText(getContext(), "Inserte datos validos", Toast.LENGTH_SHORT).show();
                    } else {
                        Reserva r = new Reserva();
                        r.setId(UUID.randomUUID().toString());
                        r.setProf(user.substring(0, pos));
                        r.setDia(DIA);
                        r.setPeriodo(HORA);
                        r.setCurso(curso.getText().toString().toUpperCase());
                        r.setCodigoAula(codigoAula.getText().toString().toUpperCase());
                        r.setAsig(asig.getText().toString().toUpperCase());
                        r.setFechafin(fecha.getText().toString());
                        databaseReference.child("Reservas").child(r.getId()).setValue(r);
                        Toast.makeText(getContext(), "Insertado correctamente", Toast.LENGTH_SHORT).show();
                        curso.setText("");
                        fecha.setText("");
                        asig.setText("");
                        NavHostFragment.findNavController(ReservaFragment.this)
                                .navigate(R.id.action_global_consultaFragment2);

                    }
                }else {

                    Reserva r = new Reserva();
                    r.setId(idActual);
                    r.setProf(user.substring(0, pos));
                    r.setDia(diaEdit);
                    r.setPeriodo(horaEdit);
                    r.setCurso(curso.getText().toString().toUpperCase());
                    r.setCodigoAula(codigoAula.getText().toString().toUpperCase());
                    r.setAsig(asig.getText().toString().toUpperCase());
                    r.setFechafin(fecha.getText().toString());
                    databaseReference.child("Reservas").child(r.getId()).setValue(r);
                    Toast.makeText(getContext(), "Modificado correctamente", Toast.LENGTH_SHORT).show();
                    curso.setText("");
                    fecha.setText("");
                    asig.setText("");
                    NavHostFragment.findNavController(ReservaFragment.this)
                            .navigate(R.id.action_global_consultaFragment2);


                }
            }
        });

       return v;
    }

    @NotNull
    public static String devuelveDia(int d) {
        String auxDia;
        switch (d){
            case 1:
                auxDia="Lunes";
                break;
            case 2:
                auxDia="Martes";
                break;
            case 3:
                auxDia="Miercoles";
                break;
            case 4:
                auxDia="Jueves";
                break;
            case 5:
                auxDia="Viernes";
                break;
            default:
                auxDia="NADA";
        }
        return auxDia;
    }
}