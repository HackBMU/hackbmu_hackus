package myguruh.com.booklend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class addbook extends AppCompatActivity {
    EditText e;
    EditText e3;
    EditText e2;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);
        ProgressDialog p=new ProgressDialog(addbook.this);
        p.setMessage("LOADING...");
        p.show();
e=findViewById(R.id.text2);
e2=findViewById(R.id.text4);
e3=findViewById(R.id.text6);
b1=findViewById(R.id.savebtn);
b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        FirebaseDatabase.getInstance().getReference().child("Books").child(e.getText().toString()).child("Subtitle").setValue(e2.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("Books").child(e.getText().toString()).child("Price").child(e3.getText().toString()).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
        Intent i=new Intent(addbook.this,Nav.class);
        startActivity(i);

    }
});
p.dismiss();
    }
}
