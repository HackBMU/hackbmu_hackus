package myguruh.com.booklend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    Button b1=null;
    EditText email=null;
    EditText password=null;


    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);

        TextView t1=findViewById( R.id.text4 );
        t1.setPaintFlags( t1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG );


        firebaseAuth=FirebaseAuth.getInstance();


        email=(EditText) findViewById( R.id.e1 );
        password=(EditText)findViewById( R.id.e2 );
        b1=(Button)findViewById( R.id.btn1 );


        progressDialog = new ProgressDialog( this );
        b1.setOnClickListener( this );
    }



    private void registerUser(){
        final String Email=email.getText().toString().trim();
        String Password=password.getText().toString().trim();

        if(TextUtils.isEmpty( Email )){
            email.setError("ENTER VALID EMAIL ID ");
            email.requestFocus();
            return;
        }

        if(TextUtils.isEmpty( Password ) && Password.length()<6){
            password.setError("ENTER VALID EMAIL ID ");
            password.requestFocus();
            return;
        }

        progressDialog.setMessage("Registering User....");
        progressDialog.show(  );

        firebaseAuth.createUserWithEmailAndPassword( Email,Password )
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SecondActivity.this, "EMAIL SENT:",
                                                Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });
                            Toast.makeText( SecondActivity.this,"Register Sucessfully",Toast.LENGTH_SHORT ).show();
                           String ui= FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase.getInstance().getReference().child("USER").child(ui).child("Email").setValue(Email);
                            startActivity(new Intent(SecondActivity.this,ThirdActivity.class));
                            finish();
                        }else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText( SecondActivity.this,"You are already registered",Toast.LENGTH_SHORT ).show();
                            }else {
                                Toast.makeText( SecondActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT ).show();
                                Toast.makeText( SecondActivity.this,"Could not register. Please try again",Toast.LENGTH_SHORT ).show();
                            }}
                    }
                } );



    }





    public void Login(View view){
        Intent i=new Intent( SecondActivity.this,MainActivity.class );
        startActivity( i );
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v==b1){
            registerUser();
        }
    }
}
