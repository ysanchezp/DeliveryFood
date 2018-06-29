package com.example.deiyv.deliveryfood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.deiyv.deliveryfood.Model.User;
import com.example.deiyv.deliveryfood.common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    EditText  edtPassword,edtTelefono;
    Button btnSingin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        edtPassword=(MaterialEditText)findViewById(R.id.edtPassword);
        btnSingin=(Button)findViewById(R.id.singIn);
        edtTelefono=(MaterialEditText)findViewById(R.id.edtTelefono);

        //inicia firebase

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        btnSingin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                //conexion
                if (Common.isConecctedToInternet(getBaseContext())) {


                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("Porfavor, Espere un momento...!");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //verificar si el usuario existe en la base de datos
                            if (dataSnapshot.child(edtTelefono.getText().toString()).exists()) {
                                //devuelve informacion del usuario
                                mDialog.dismiss();
                                User user = dataSnapshot.child(edtTelefono.getText().toString()).getValue(User.class);
                                user.setTelefono(edtTelefono.getText().toString());

                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Intent menuIntent = new Intent(SignIn.this, Carta.class);
                                    Common.currentUser = user;
                                    startActivity(menuIntent);
                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, "Contraseña Incorrecta!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "Usuario no existe", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(SignIn.this,"Por favor verifica tu conexion a internet",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        });
    }
}
