package bqh.cslg.mystudentmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bqh.cslg.mystudentmanager.R;
import bqh.cslg.mystudentmanager.activity.EditActivity;
import bqh.cslg.mystudentmanager.activity.SearchActivity;
import bqh.cslg.mystudentmanager.db.StudentDBUtils;
import bqh.cslg.mystudentmanager.model.Student;

/**
 * Created by baiqihui on 2016/10/15.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context mContext;

    private ArrayList<String> mNumber;
    private ArrayList<String> mName;

    StudentDBUtils dbUtils;

    public final static int maxSize = 50;//最多显示50条

    public SearchAdapter(Context context,ArrayList<String> number,ArrayList<String> name){
        mContext = context;
        mNumber = number;
        mName = name;
        dbUtils = StudentDBUtils.getInstance(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View views = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false);
        return new MyViewHolder(views);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvNumber.setText(mNumber.get(position));
        holder.tvName.setText(mName.get(position));

        holder.llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditActivity.class);
                Student student = dbUtils.getStudentByNumber(mNumber.get(position));
                intent.putExtra("studenInfo",student);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mNumber.size()<maxSize)
            return mNumber.size();
        else
            return maxSize;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout llSearch;
        public TextView tvNumber;
        public TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            llSearch = (LinearLayout) itemView.findViewById(R.id.ll_search);
            tvNumber = (TextView) itemView.findViewById(R.id.tv_search_number);
            tvName = (TextView) itemView.findViewById(R.id.tv_search_name);
        }
    }
}
