package bqh.cslg.mystudentmanager.adapter;

import android.view.View;

/**
 * Created by baiqihui on 2016/10/13.
 */

public interface RecyclerViewItemClickListener {
    void onItemClick(View view, String data);
    void onItemLongClick(View view,String data);
}
