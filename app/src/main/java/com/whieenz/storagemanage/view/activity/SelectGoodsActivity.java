package com.whieenz.storagemanage.view.activity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.whieenz.storagemanage.R;
import com.whieenz.storagemanage.utls.DBManger;
import com.whieenz.storagemanage.utls.SGAdapter;
import com.whieenz.storagemanage.utls.SQLitConstant;
import com.whieenz.storagemanage.view.myView.LoadListView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;
import static android.content.ContentValues.TAG;

/**
 * Created by heziwen on 2017/3/17.
 */

public class SelectGoodsActivity extends Activity implements AbsListView.OnScrollListener,AdapterView.OnItemClickListener{

    private LoadListView listView;
    private SGAdapter simp_adapter;
    private List<Map<String,Object>> datalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_goods);
        listView = (LoadListView) findViewById(R.id.select_loadlist);
        datalist = new ArrayList<Map<String,Object>>();
        simp_adapter = new SGAdapter(this,getData(),R.layout.goods_item,new String[]{"wzmc","wzbm","ggxh","zkc"},new int[]{R.id.tv_item_wzmc,R.id.tv_item_wzbm,R.id.tv_item_ggxh,R.id.tv_item_zkc});
        //3.视图（ListView）加载适配器
        listView.setAdapter(simp_adapter);
        //加载监听器
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private List<Map<String,Object>> getData(){
        SQLiteDatabase db = DBManger.getIntance(this).getWritableDatabase();
        //ContentValues values = new ContentValues();
        Cursor cursor = db.query(SQLitConstant.TABLE_GOODS,null,null,null,null,null,null);
        if (cursor.getCount()==0){
            return null;
        }
        while (cursor.moveToNext()){
            Map<String,Object> map = new HashMap<String,Object>();
            Log.d(TAG, "cursorToUserList: in moveToNext() ");
            String wzbm = cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_WZBM));
            String wzmc = cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_WZMC));
            String ggxh = cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_GGXH));
            String jldw = cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_JLDW));
            map.put("wzbm","物资编码："+wzbm);
            map.put("wzmc",wzmc);
            map.put("ggxh","规格型号："+ggxh);
            map.put("zkc","总库存："+0+" "+jldw);
            datalist.add(map);
        }

        return  datalist;
    }
}
