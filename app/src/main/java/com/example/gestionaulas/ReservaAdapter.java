package com.example.gestionaulas;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionaulas.model.Reserva;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.gestionaulas.ReservaFragment.devuelveDia;

public class ReservaAdapter extends FirebaseRecyclerAdapter<Reserva,ReservaAdapter.reservaViewHolder> {
    private Context context;
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static SimpleDateFormat formatoFecha = new SimpleDateFormat(FORMATO_FECHA, Locale.getDefault());
     Date date1;
     Date date2;

    public ReservaAdapter(@NonNull FirebaseRecyclerOptions<Reserva> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull reservaViewHolder holder, int position, @NonNull Reserva model) {
        holder.dia.setText(devuelveDia(model.getDia()));
        holder.hora.setText(String.valueOf(model.getPeriodo()));
        holder.curso.setText(model.getCurso());
        holder.asignatura.setText(model.getAsig());
        holder.aula.setText(model.getCodigoAula());
        holder.fecha.setText(model.getFechafin());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date fechaactual = new Date();
                String fecha = formatoFecha.format(fechaactual);
                try {
                    date1 = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
                    date2 = new SimpleDateFormat("dd/MM/yyyy").parse(model.getFechafin());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date2.before(date1)) {
                    Toast.makeText(context,"No se puede modificar",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,"MODIFICANDO",Toast.LENGTH_SHORT).show();
                   Bundle b=new Bundle();
                    b.putString("ID",model.getId());
                    b.putInt("DIA",model.getDia());
                    b.putInt("HORA",model.getPeriodo());
                    b.putString("CURSO",model.getCurso());
                    b.putString("AULA",model.getCodigoAula());
                    b.putString("FECHA",model.getFechafin());
                    b.putString("ASIG",model.getAsig());

                    Navigation.findNavController((Activity) context, R.id.fragment).navigate(R.id.action_global_reservaFragment,b);

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @NonNull
    @Override
    public reservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2,parent,false);
        context=parent.getContext();
        return new reservaViewHolder(v);
    }

    class reservaViewHolder extends RecyclerView.ViewHolder{
        TextView dia,hora, aula,fecha,curso,asignatura;
        public reservaViewHolder(@NonNull View itemView) {
            super(itemView);
            dia=itemView.findViewById(R.id.itemreserva_dia);
            hora=itemView.findViewById(R.id.itemreserva_hora);
            aula =itemView.findViewById(R.id.itemreserva_cursoreservado);
            curso=itemView.findViewById(R.id.itemreserva_curso);
            asignatura=itemView.findViewById(R.id.itemreserva_asig);
            fecha=itemView.findViewById(R.id.itemreserva_fecha);

        }
    }
}
