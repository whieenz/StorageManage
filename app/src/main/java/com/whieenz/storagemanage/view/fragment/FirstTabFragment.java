package com.whieenz.storagemanage.view.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.whieenz.storagemanage.R;
import com.whieenz.storagemanage.base.MyApp;
import com.whieenz.storagemanage.utls.DBManger;
import com.whieenz.storagemanage.utls.SQLitConstant;
import com.whieenz.storagemanage.view.myView.LoadListView;
import com.whieenz.storagemanage.view.myView.ReFlashListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by whieenz on 2017/3/4.
 *
 * 第一个标签  聊天
 */
public class FirstTabFragment extends Fragment implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {

    private LoadListView listView;
    private SimpleAdapter simp_adapter;
    private List<Map<String,Object>> datalist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.tab01,container,false);


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (LoadListView) getView().findViewById(R.id.lv_listview);
        datalist = new ArrayList<Map<String,Object>>();

        getData();
        if(datalist.size() > 0){
            simp_adapter = new SimpleAdapter(getActivity(),datalist,R.layout.dj_item,new String[]{"djbm","djlx","time","djzt"},new int[]{R.id.tv_item_djbm,R.id.tv_item_djlx,R.id.tv_item_time,R.id.tv_item_djzt});
            //3.视图（ListView）加载适配器
            listView.setAdapter(simp_adapter);
            //加载监听器
            listView.setOnItemClickListener(this);
            listView.setOnScrollListener(this);
        }



    }

    private List<Map<String,Object>> getData(){
        MyApp myApp = (MyApp)getActivity().getApplication();
        String userNum = myApp.getUserInfo().getNum();
        SQLiteDatabase db = DBManger.getIntance(getActivity()).getWritableDatabase();
        Cursor cursor = db.query(SQLitConstant.TABLE_KCDJ,null,"DCLR=?",new String[]{userNum},null,null,null);
        if (cursor.getCount()==0){
            return null;
        }
        while (cursor.moveToNext()){
            Map<String,Object> map = new HashMap<String,Object>();
            String djlx = cursor.getString(cursor.getColumnIndex(SQLitConstant.KCDJ_DJLX));
            String djbh = cursor.getString(cursor.getColumnIndex(SQLitConstant.KCDJ_DJBM));
            String time = cursor.getString(cursor.getColumnIndex(SQLitConstant.KCDJ_TIME));

            map.put("djbm",djbh);
            map.put("djlx","单据类型："+djlx);
            map.put("time",time);
            map.put("djzt","待处理");
            datalist.add(map);
        }
        db.close();
        return  datalist;
    }
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

        switch (i){
            case  SCROLL_STATE_FLING:
                Log.i(TAG, "用户在离开屏幕前，用力滑了一下，视图任然在滑动");

//                Map<String,Object> map = new HashMap<String,Object>();
//                map.put("pic",R.mipmap.ic_launcher);
//                map.put("text","新增内容"+num);
//                datalist.add(map);
                simp_adapter.notifyDataSetChanged();
                break;
            case SCROLL_STATE_IDLE:
                break;
            case  SCROLL_STATE_TOUCH_SCROLL:
                break;
        }

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String text = listView.getItemAtPosition(i)+"";
        HashMap<String,String> map=(HashMap<String,String>)listView.getItemAtPosition(i);
        String djbm=map.get("djbm");
        String time=map.get("time");
        Toast.makeText(getActivity(),"position:"+i+"\ndjbm:"+djbm+"\ntime:"+time,Toast.LENGTH_SHORT).show();
    }
}
