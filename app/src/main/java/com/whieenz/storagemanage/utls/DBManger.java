package com.whieenz.storagemanage.utls;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.whieenz.storagemanage.base.GoodsVO;
import com.whieenz.storagemanage.base.UserInfo;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 对数据库操作的数据类
 * Created by heziwen on 2017/3/8.
 */

public class DBManger {
    private static  MySqlitHelper helper;


    public static MySqlitHelper getIntance (Context context){
        if(helper==null){
            helper = new MySqlitHelper(context);
        }
        return helper;
    }

    public static void exeSQL(SQLiteDatabase db,String sql){
        if (db != null) {
            if (sql != null &&!"".equals(sql) ) {
                db.execSQL(sql);
            }
        }
    }

    /**
     * 封装查询
     * @param db   数据库对象
     * @param sql   差选的SQL语句
     * @param conditions   查询条件的占位符
     * @return  查询结果
     */
    public static Cursor QueryDataBySql(SQLiteDatabase db, String sql,String[] conditions){
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(sql,conditions);
        }
        return cursor;
    }

    /**
     * 将查询结果转化为list集合
     * @param cursor
     * @return
     */
    public static List<UserInfo> cursorToUserList(Cursor cursor){
        List<UserInfo> list = new ArrayList<>();
        //moveToNext() 返回true 表示下一条记录存在 否则表示游标中数据读取完毕
        Log.d(TAG, "cursorToUserList: cursor.getCount() :"+cursor.getCount());
        if (cursor.getCount()==0){
            return null;
        }
        while (cursor.moveToNext()){
            Log.d(TAG, "cursorToUserList: in moveToNext() ");
            String name = cursor.getString(cursor.getColumnIndex(SQLitConstant.USER_NAME));
            String job = cursor.getString(cursor.getColumnIndex(SQLitConstant.USER_JOB));
            String num = cursor.getString(cursor.getColumnIndex(SQLitConstant.USER_NUM));
            String key = cursor.getString(cursor.getColumnIndex(SQLitConstant.USER_KEY));

            UserInfo user =new UserInfo(name,job,num,key);
            Log.d(TAG, "cursorToUserList: "+user.getNum()+user.getKey());
            list.add(user);
        }
        return list;
    }

    /**
     * 将查询结果转化为 goodslist
     * @param
     * @return
     */
    public static List<GoodsVO> cursorToGoodsList(Cursor cursor){
        List<GoodsVO> list = new ArrayList<>();
        //moveToNext() 返回true 表示下一条记录存在 否则表示游标中数据读取完毕
        Log.d(TAG, "cursorToGoodsList: cursor.getCount() :"+cursor.getCount());
        if (cursor.getCount()==0){
            return null;
        }
        while (cursor.moveToNext()){
            GoodsVO goodsVO = new GoodsVO();
            goodsVO.setWzbm(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_WZBM)));
            goodsVO.setWzmc(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_WZMC)));
            goodsVO.setWzlx(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_WZLX)));
            goodsVO.setGgxh(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_GGXH)));
            goodsVO.setJldw(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_JLDW)));
            goodsVO.setScrq(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_SCRQ)));
            goodsVO.setCd(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_CD)));
            goodsVO.setBz(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_BZ)));
            goodsVO.setBzq(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_BZQ)));
            goodsVO.setDj(Integer.valueOf(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_DJ))));
            goodsVO.setSize(Double.parseDouble(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_SIZE))));
            goodsVO.setTime(cursor.getString(cursor.getColumnIndex(SQLitConstant.GOODS_TIME)));
            list.add(goodsVO);
            Log.d(TAG, "cursorToGoodsList: Wzbm  :"+goodsVO.getWzbm());
        }
        return list;
    }

    /**
     * 判断是否存在本条数据
     * @param tableName
     * @param whereCase
     * @param whereCaseValues
     * @return ture 为存在 false 不存在
     */
   public static boolean isUniqueExist(String tableName,String whereCase,String[] whereCaseValues){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor =  db.query(tableName,null,whereCase,whereCaseValues,null,null,null);
        if(cursor.moveToFirst() ==false){
            return  false;
        }
       return true;
   }
/**
 * String table :查询的表名
 * String[] columns :查询的表中的字段名称 null 表示查询所有
 * String selection :表示查询条件 where子句
 * String[] selectionArgs :表示查询条件占位符的取值
 * String groupBy : 表示分组条件 group By 子句
 * String having : 表示筛选条件 having 子句
 * String orderby : 表示排序条件 order by 子句
 * db.query(table,columns,selection,selectionArgs,groupBy,having,orderby);
 */

}
