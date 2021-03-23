package com.example.gestionaulas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecuperarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecuperarFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView email;
    Button recuperar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecuperarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecuperarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecuperarFragment newInstance(String param1, String param2) {
        RecuperarFragment fragment = new RecuperarFragment();
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
        progressDialog=new ProgressDialog(getContext());
        firebaseAuth=FirebaseAuth.getInstance();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_recuperar, container, false);
        email=v.findViewById(R.id.restablecer_email);
        recuperar=v.findViewById(R.id.restablecer_boton);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail=email.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)) {
                    Toast.makeText(getContext(), "Debe ingresar un email", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Enviando email de recuperación...");
                progressDialog.show();

                firebaseAuth.sendPasswordResetEmail(mEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), "Correo de recuperación enviado", Toast.LENGTH_SHORT).show();
                            NavHostFragment.findNavController(RecuperarFragment.this)
                                    .navigate(R.id.action_global_loginFragment);
                            email.setText("");

                        } else {

                            Toast.makeText(getContext(), "No se pudo enviar, revisa el email", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();

                    }
                });



            }
        });



        return v;
    }
}