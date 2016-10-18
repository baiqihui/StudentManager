package bqh.cslg.mystudentmanager.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bqh.cslg.mystudentmanager.R;
import bqh.cslg.mystudentmanager.model.Student;

/**
 * Created by baiqihui on 2016/10/13.
 */

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.MyViewHolder> implements View.OnClickListener,View.OnLongClickListener {

    private List<Student> studentList;
    private Context mContext;
    private LayoutInflater inflater;
    private RecyclerViewItemClickListener mOnItemClickListener=null;

    public ShowAdapter(Context context, List<Student> studentList){
        mContext = context;
        this.studentList = studentList;
        inflater = LayoutInflater.from(mContext);
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.number.setText(studentList.get(position).getNumber()+"");
        holder.name.setText(studentList.get(position).getName());
        holder.gender.setText(studentList.get(position).getGender());

        holder.itemView.setTag(position);
    }

    public void setOnItemClickListener(RecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.act_main_item,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return holder;
    }


    @Override
    public int getItemCount() {
        return studentList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v,v.getTag()+"");
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mOnItemClickListener!=null){
            mOnItemClickListener.onItemLongClick(v,v.getTag()+"");
        }
        return true;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView number;
        TextView name;
        TextView gender;

        public MyViewHolder(View view) {
            super(view);
            number = (TextView) view.findViewById(R.id.tv_show_number);
            name = (TextView) view.findViewById(R.id.tv_show_name);
            gender = (TextView) view.findViewById(R.id.tv_show_gender);
        }
    }


}
