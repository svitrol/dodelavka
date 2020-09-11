package com.example.ctecka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ctecka.knizniDatabaze.Knizky.DbKnihomol;
import com.example.ctecka.knizniDatabaze.Knizky.Knihovnice;
import com.example.ctecka.knizniDatabaze.Knizky.Knizka;
import com.example.ctecka.pridaniKnizky.pridaniI;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Dotek {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.Recyklos);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dostanKnizky();


    }
    private void setniDataRecyklaci(List<Knizka> knihovna){
        mAdapter = new MyAdapter(knihovna,MainActivity.this);
        recyclerView.setAdapter(mAdapter);
    }
    private void dostanKnizky(){
        class dostanPrvky extends AsyncTask<Void, Void, List<Knizka>> {

            @Override
            protected List<Knizka> doInBackground(Void... voids) {
                List<Knizka> komponenty =DbKnihomol
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .knizkyDao()
                        .getAll();
                return komponenty;
            }

            @Override
            protected void onPostExecute(List<Knizka> knihovna) {
                super.onPostExecute(knihovna);
                setniDataRecyklaci(knihovna);
            }
        }

        dostanPrvky gt = new dostanPrvky();
        gt.execute();
    }
    public void novaKnizka(View view){
        Intent dalsi=new Intent(MainActivity.this, pridaniI.class);
        startActivity(dalsi);
    }

    @Override
    public void onTouch(Knizka knizka) {
    }

    @Override
    public void onLongTouch(Knizka knizka) {

    }
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
        List<Knizka> knihovna;
        Dotek akce;

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView NezevKnizky;
            TextView DatumPosledniAktualizace;
            TextView PocetKapitol;
            public MyViewHolder( View itemView) {
                super(itemView);
                NezevKnizky=itemView.findViewById(R.id.nazevKnizke);
                DatumPosledniAktualizace=itemView.findViewById(R.id.DatumPosledniKapitoly);
                PocetKapitol=itemView.findViewById(R.id.pocetKapitol);

            }
        }

        public MyAdapter(List<Knizka> knihovna,Dotek akce) {
            this.knihovna=knihovna;
            this.akce=akce;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View novy= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.knizka, parent, false);
            return new MyViewHolder(novy);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final Knizka aktualni=knihovna.get(position);
            holder.NezevKnizky.setText(aktualni.Title);
            holder.DatumPosledniAktualizace.setText(aktualni.PoslAktualizace.toString());
            holder.PocetKapitol.setText(""+aktualni.PocetKapitol);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    akce.onTouch(aktualni);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    akce.onLongTouch(aktualni);
                    return true;
                }
            });


        }

        @Override
        public int getItemCount() {
            return knihovna.size();
        }

    }
}
