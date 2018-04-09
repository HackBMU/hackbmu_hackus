package myguruh.com.booklend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThirdActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonLogout;
    RadioButton r1;
    RadioButton r2;
    EditText phno;


    private DatabaseReference databaseReference;
    private EditText editTextName,editTextAddress;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        firebaseAuth=FirebaseAuth.getInstance(  );
        if(firebaseAuth.getCurrentUser()==null){
            finish();
            startActivity( new Intent( this,MainActivity.class ) );
        }

        databaseReference= FirebaseDatabase.getInstance().getReference();
        editTextName =(EditText)findViewById( R.id.edit1111 );
        editTextAddress=(EditText)findViewById( R.id.edit2222 );
        buttonSave=(Button)findViewById( R.id.btn1111 );
        r1=findViewById(R.id.m);
        r2=findViewById(R.id.f);
        phno=findViewById(R.id.phno);

        FirebaseUser user=firebaseAuth.getCurrentUser();

        textViewUserEmail=(TextView)findViewById( R.id.txt11 );
        textViewUserEmail.setText( "Welcome   "+user.getEmail() );

        buttonLogout=(Button)findViewById( R.id.btn11 );

        buttonLogout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==buttonLogout){
                    firebaseAuth.signOut();
                    finish();
                    startActivity( new Intent( ThirdActivity.this,MainActivity.class ) );
                }
            }
        } );

        buttonSave.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==buttonSave){
                    UserInformation();
                    Intent i=new Intent(ThirdActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        } );

    }

    private void UserInformation(){
        String name=editTextName.getText().toString().trim();
        String address=editTextAddress.getText().toString().trim();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db= FirebaseDatabase.getInstance()
                .getReference().child("USER");
        db.child(uid).child("Name").setValue(name);
        db.child(uid).child("Address").setValue(address);
        db.child(uid).child("mobile number").setValue(phno.getText().toString());
        if(r1.isChecked())
        db.child(uid).child("gender").setValue("MALE");
        else if(r2.isChecked())
        db.child(uid).child("gender").setValue("female");
        Toast.makeText( this,"Information Saved...." ,Toast.LENGTH_LONG).show();

    }


         }



