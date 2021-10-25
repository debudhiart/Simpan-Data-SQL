package id.budhiarta.simpandatasql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import id.budhiarta.simpandatasql.Adapter.AdapterToDo;
import id.budhiarta.simpandatasql.Model.ModelToDo;
import id.budhiarta.simpandatasql.Utils.HalperDataBase;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {

    private RecyclerView mRecyclerview;
    private FloatingActionButton fab_tambah;
    private HalperDataBase myDB;
    private List<ModelToDo> mList;
    private AdapterToDo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = findViewById(R.id.rv_taks_to_do);
        fab_tambah = findViewById(R.id.fab_tambah);
        myDB = new HalperDataBase(MainActivity.this);
        mList = new ArrayList<>();
        adapter = new AdapterToDo(myDB, MainActivity.this);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);

        mList = myDB.getAllTaks();
        Collections.reverse(mList);
        adapter.setTasks(mList);

        fab_tambah.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TambahTaksBaru.newInstance().show(getSupportFragmentManager(), TambahTaksBaru.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerview);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = myDB.getAllTaks();
        Collections.reverse(mList);
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();
    }
}