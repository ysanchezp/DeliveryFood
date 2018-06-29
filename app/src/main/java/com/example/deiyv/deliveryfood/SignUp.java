package com.example.deiyv.deliveryfood;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.deiyv.deliveryfood.Model.User;
import com.example.deiyv.deliveryfood.common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignUp extends AppCompatActivity {
    MaterialEditText edtDni, edtName, edtPassword,edtTelefono;
    Button btnsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = (MaterialEditText) findViewById(R.id.edtName);
        edtDni = (MaterialEditText) findViewById(R.id.edtDNI);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        edtTelefono=(MaterialEditText)findViewById(R.id.edtTelefono);

        btnsignup = (Button) findViewById(R.id.singUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Common.isConecctedToInternet(getBaseContext())) {
                    final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                    mDialog.setMessage("Espere un momento");
                    mDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //verificar si usuario ya esta registrado
                            if (dataSnapshot.child(edtTelefono.getText().toString()).exists()) {
                                mDialog.dismiss();
                                Toast.makeText(SignUp.this, "Usuario ya esta registrado", Toast.LENGTH_SHORT).show();
                            } else {
                                mDialog.dismiss();
                                User user = new User(edtName.getText().toString(), edtPassword.getText().toString(), edtDni.getText().toString());

                                table_user.child(edtTelefono.getText().toString()).setValue(user);
                                Toast.makeText(SignUp.this, "Se Registró Correctamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(SignUp.this,"Por favor verifica tu conexion a internet",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        });
    }
}
