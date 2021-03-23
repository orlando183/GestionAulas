package com.example.gestionaulas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestionaulas.model.Reserva;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaReservaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaReservaFragment extends Fragment {
    RecyclerView rv;
    ReservaAdapter adapter;
    FirebaseAuth mAuth;
    TextView vacio;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaReservaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaReservaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaReservaFragment newInstance(String param1, String param2) {
        ListaReservaFragment fragment = new ListaReservaFragment();
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
        mAuth =FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();

        adapter.startListening();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_lista_reserva, container, false);
        rv=v.findViewById(R.id.recycler_reserva);

        LinearLayoutManager llm=new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        FirebaseUser usuarioActual= FirebaseAuth.getInstance().getCurrentUser();
        String user=usuarioActual.getEmail();
        int pos=user.indexOf("@");
        FirebaseRecyclerOptions<Reserva> options=
                new FirebaseRecyclerOptions.Builder<Reserva>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Reservas").orderByChild("prof").equalTo(user.substring(0, pos)), Reserva.class)
                        .build();
        adapter=new ReservaAdapter(options);
        rv.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),llm.getOrientation());
        dividerItemDecoration.setDrawable(getContext().getResources().getDrawable(R.color.black));
        rv.addItemDecoration(dividerItemDecoration);


        return v;
    }




    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}