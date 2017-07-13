package com.ricindigus.ricardo.loginfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ricindigus.ricardo.loginfirebase.model.Artista;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String ARTISTA_NODE = "Artistas";
    private DatabaseReference databaseReference;
    private Button btnAgregarArtista;
    private ListView lstArtistas;
    private ArrayAdapter arrayAdapter;
    private List<String> nombreArtistas;
    private List<Artista> artistas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAgregarArtista = (Button) findViewById(R.id.btnAddArtista);
        lstArtistas = (ListView)findViewById(R.id.lstArtistas);
        nombreArtistas = new ArrayList<String>();
        artistas = new ArrayList<Artista>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, nombreArtistas);
        lstArtistas.setAdapter(arrayAdapter);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child(ARTISTA_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nombreArtistas.clear();
                artistas.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Artista artista = snapshot.getValue(Artista.class);
                        nombreArtistas.add(artista.getNombre());
                        artistas.add(artista);
                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        lstArtistas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                String idArtista = artistas.get(position).getId();
                artistas.remove(position);
                nombreArtistas.remove(position);
                databaseReference.child(ARTISTA_NODE).child(idArtista).removeValue();
                return false;
            }
        });
    }

    public void crearArtista(View view){
        Artista artista = new Artista(databaseReference.push().getKey(),"Garbage","Rock");
        databaseReference.child(ARTISTA_NODE).child(artista.getId()).setValue(artista);
    }
}
