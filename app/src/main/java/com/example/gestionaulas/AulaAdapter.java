package com.example.gestionaulas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionaulas.model.Aula;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import static com.example.gestionaulas.MainActivity.aulasOcupadas;
import static com.example.gestionaulas.MainActivity.aulasReservadas;

public class AulaAdapter extends FirebaseRecyclerAdapter<Aula,AulaAdapter.aulaViewHolder>{
    public static final String RESERVADO="RESERVADO";
    public static final String OCUPADO="OCUPADO";
    public static final String LIBRE="LIBRE";
    private Context context;

    public AulaAdapter(@NonNull FirebaseRecyclerOptions<Aula> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull aulaViewHolder holder, int position, @NonNull Aula model) {
            String variable=LIBRE;

            holder.codigo.setText(model.getCodigo());
            holder.aula.setText(model.getNombre());
           holder.estado.setTextColor(ColorStateList.valueOf(Color.parseColor("#399113")));
        for (String s:aulasOcupadas) {
            Log.d("OCUPADDDAA",s);
                if (s.equals(model.getCodigo())) {
                    variable =OCUPADO;
                    holder.estado.setTextColor(ColorStateList.valueOf(Color.parseColor("#d11b1b")));
                }
        }
        for (String r:aulasReservadas) {
            if(r.equals(model.getCodigo())){
                variable=RESERVADO;
                holder.estado.setTextColor(ColorStateList.valueOf(Color.parseColor("#c76904")));
            }
        }
        holder.estado.setText(variable);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((holder.estado.getText().toString().equals(RESERVADO)) || (holder.estado.getText().toString().equals(OCUPADO)) ){
                    Toast.makeText(context, "No disponible", Toast.LENGTH_SHORT).show();
                }else {
                    String cod=holder.codigo.getText().toString();
                    Bundle b=new Bundle();
                    b.putString("AULA",cod);

                    Navigation.findNavController((Activity) context, R.id.fragment).navigate(R.id.action_global_reservaFragment,b);

                }

            }
        });
    }

    @NonNull
    @Override
    public aulaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        context=parent.getContext();


        return new aulaViewHolder(v);
    }

    class aulaViewHolder extends  RecyclerView.ViewHolder{
       TextView codigo,aula,estado;
        public aulaViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo=itemView.findViewById(R.id.item_codigo);
            aula=itemView.findViewById(R.id.item_aula);
            estado=itemView.findViewById(R.id.item_estado);
        }
    }

}
