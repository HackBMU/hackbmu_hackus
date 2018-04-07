package myguruh.com.booklend;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int x;
    public Nav(int x){
        this.x=x;
    }
    public Nav(){
        x=1;
    }
    ProgressDialog p;
TextView t,e;

    int totalElements;
    EditText ed;
    Switch tb;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    p=new ProgressDialog(Nav.this);
        tb=findViewById(R.id.tog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tb=findViewById(R.id.tog);
        setSupportActionBar(toolbar);
        final String[] maintitle ={
                "Book1","Book2",
                "Book3","Book4",
                "Book5",
        };

        final String[] subtitle ={
                "Sub Title 1","Sub Title 2",
                "Sub Title 3","Sub Title 4",
                "Sub Title 5",
        };

        Integer[] imgid={
                R.drawable.book,R.drawable.book,
                R.drawable.book,R.drawable.book,
                R.drawable.book,
        };


        MyListAdapter adapter = new MyListAdapter(Nav.this, maintitle, subtitle, imgid);
        final ListView list = (ListView) findViewById(R.id.list);
        list.setItemsCanFocus(false);
        list.setAdapter(adapter);

        p.setMessage("loading....");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                p.show();

                for(int i =0;i<subtitle.length;i++) {
                 if(position==i){
                   FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(DataSnapshot dataSnapshot) {

                           if (dataSnapshot.child("Books").hasChild(maintitle[position])) {
                               s = dataSnapshot.child("Books").child(maintitle[position]).child("Price").getValue().toString();
                           }
                           else {
                               s="NOT AVAILABLE";
                           }
                       p.dismiss();
                       }
                       @Override
                       public void onCancelled(DatabaseError databaseError) {

                       }
                   });
                   p.show();
                   AlertDialog.Builder builder = new AlertDialog.Builder(Nav.this);
                     LayoutInflater inflater = getLayoutInflater();
                     View dialogView = inflater.inflate(R.layout.cartadd,null);
                     builder.setView(dialogView);

                     // Get the custom alert dialog view widgets reference
                     Button btn_negative = (Button) dialogView.findViewById(R.id.cartd);
                     Button back = (Button) dialogView.findViewById(R.id.backi);
                     final TextView et_name =  dialogView.findViewById(R.id.pri);
                     et_name.setText(s);
                     final AlertDialog dialog = builder.create();
                     btn_negative.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             FirebaseDatabase.getInstance().getReference().child("CART").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(maintitle[position]).setValue(true);
                         Toast.makeText(Nav.this,"ADDED TO CART",Toast.LENGTH_SHORT).show();
                             dialog.dismiss();}


                     });
                     back.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             dialog.dismiss();
                         }
                     });
                     p.dismiss();
                     dialog.show();
                }}
            }
        });



    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        FirebaseAuth mAuth;
        FirebaseAuth.AuthStateListener mauthl;
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        tb=findViewById(R.id.tog);
        tb.setTextOn("Rent your Book");
        tb.setTextOff("BUY A BOOK");
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Nav n;
                if(isChecked) {
              Intent i=new Intent(Nav.this,rent.class);
  startActivity(i);
                }
                else{
                    n=new Nav(1);
                }

            }
        });
        DatabaseReference db= FirebaseDatabase.getInstance()
                .getReference().child("USER");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                t=findViewById(R.id.nam);
                e=findViewById(R.id.textView);
                String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
                if(dataSnapshot.hasChild(uid)) {
                    String name=dataSnapshot.child(uid).child("Name").getValue().toString();
                    t.setText(name);
                    e.setText(dataSnapshot.child(uid).child("Email").getValue().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            Intent i=new Intent(Nav.this,MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i=new Intent( this,Nav.class );
            startActivity( i );
            finish();
        } else if (id == R.id.nav_bookrented) {
            Intent i=new Intent( this,bookrented.class );
            startActivity( i );

        } else if (id == R.id.nav_bookborrowed) {
            Intent i=new Intent( this,bookborrow.class );
            startActivity( i );
        }
        else if (id == R.id.nav_manage) {
      /*      Intent i=new Intent( this,MainActivity.class );
            startActivity( i );
        */}
        else if (id == R.id.nav_payment) {
            Intent i=new Intent( Intent.ACTION_VIEW, Uri.parse( "https://paytm.com" ) );
            startActivity( i );

        }
        else if (id == R.id.ab) {
            Intent i=new Intent( Nav.this,addbook.class);
            startActivity( i );

        }
        else if (id == R.id.nav_rate) {
         //   Intent i=new Intent( this,MainActivity.class );
         //   startActivity( i );
        }else if (id == R.id.nav_about) {
           // Intent i=new Intent( this,MainActivity.class );
            //startActivity( i );
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
