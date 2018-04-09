package myguruh.com.booklend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class rent extends AppCompatActivity {
    ListView list;
    EditText ed;
    int totalElements;
String ss;
    ArrayList subtitle;
    ArrayList maintitle;
    ArrayList imgid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
         maintitle = new ArrayList();
         subtitle=new ArrayList();
        maintitle.add("Book1");
        maintitle.add("book2");
        maintitle.add("book3");
        maintitle.add("book4");
        maintitle.add("book5");
        subtitle.add("Subtitle1");
        subtitle.add("Subtitle2");
        subtitle.add("Subtitle3");
        subtitle.add("Subtitle4");
        subtitle.add("Subtitle5");

        imgid=new ArrayList();
        imgid.add(R.drawable.book);
        imgid.add(R.drawable.book);
        imgid.add(R.drawable.book);
        imgid.add(R.drawable.book);
        imgid.add(R.drawable.book);
        imgid.add(R.drawable.book);

        totalElements = maintitle.size();

        MyListAdapters adapter=new MyListAdapters(rent.this, maintitle, subtitle,imgid);
        list=findViewById(R.id.lis);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ss=maintitle.get(position).toString();
               for(int i=0;i<totalElements;i++){
                if(position == i) {
                    Toast.makeText(rent.this, "hey", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(rent.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.price,null);
                    builder.setView(dialogView);

                    // Get the custom alert dialog view widgets reference
                    Button btn_negative = (Button) dialogView.findViewById(R.id.savep);
                    final EditText et_name = (EditText) dialogView.findViewById(R.id.write);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                    btn_negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ProgressDialog p=new ProgressDialog(rent.this);
                                    p.setMessage("Saving...");
                                    p.show();
                                    if(dataSnapshot.child("Books").hasChild(ss)){
                                        long c=dataSnapshot.child("Books").child(ss).child("Price").getChildrenCount();
                                        FirebaseDatabase.getInstance().getReference().child("Books").child(ss).child("Price").setValue(et_name.getText().toString());
                                    dialog.dismiss();

                                    p.dismiss();}

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    });
                    }

                }
            }
        });
    }
}
