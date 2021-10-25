package id.budhiarta.simpandatasql.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.DatabaseMetaData;
import java.util.List;

import id.budhiarta.simpandatasql.MainActivity;
import id.budhiarta.simpandatasql.Model.ModelToDo;
import id.budhiarta.simpandatasql.R;
import id.budhiarta.simpandatasql.TambahTaksBaru;
import id.budhiarta.simpandatasql.Utils.HalperDataBase;

public class AdapterToDo extends RecyclerView.Adapter<AdapterToDo.MyViewHolder> {

    private List<ModelToDo> mList;
    private MainActivity activity;
    private HalperDataBase myDB;

    public AdapterToDo(HalperDataBase myDB, MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ModelToDo item = mList.get(position);
        holder.mCheckBox.setText(item.getTask());
        holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    myDB.updateStatus(item.getId(), 1);
                }else {
                    myDB.updateStatus(item.getId(), 0);
                }
            }
        });

    }

    public boolean toBoolean(int num){
        return num != 0;
    }

    public Context getContext(){
        return activity;
    }

    public void  setTasks(List<ModelToDo> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteTaks(int position){
        ModelToDo item = mList.get(position);
        myDB.deleteTaks(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);

    }

    public void editItem(int position){
        ModelToDo item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("task", item.getTask());

        TambahTaksBaru tambahTaksBaru = new TambahTaksBaru();
        tambahTaksBaru.setArguments(bundle);
        tambahTaksBaru.show(activity.getSupportFragmentManager(), tambahTaksBaru.getTag());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.mcb_task);
        }
    }
}

