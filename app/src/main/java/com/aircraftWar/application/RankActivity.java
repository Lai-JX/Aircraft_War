package com.aircraftWar.application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.aircraftWar.GameDataDao.GameData;
import com.aircraftWar.myIOFunction.MyObjectInputStream;
import com.aircraftWar.myIOFunction.MyObjectOutputStream;
import com.example.aircraftwar.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RankActivity extends AppCompatActivity implements View.OnClickListener{
    private final int H = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int W = ViewGroup.LayoutParams.MATCH_PARENT;
    private AlertDialog.Builder builder;

    private FileInputStream fis = null;
    private FileOutputStream fos = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private List<GameData> dataList;
    private ArrayList<Integer> deleteIndex;
    private TableLayout tab;
    private ArrayList<String> tabCol = new ArrayList<>();
    private ArrayList<String> tabH = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        findViewById(R.id.btn_delete).setOnClickListener(this);

        builder = new AlertDialog.Builder(this);

        readFromFileAndShow();
        deleteIndex = new ArrayList<>();

    }

    public void readFromFileAndShow(){

        dataList = null;
        try {
            System.out.println("准备读取数据");
            if(MainActivity.difficulty == 1) {
                fis =openFileInput("easyMode_GameData");
                TextView text = (TextView)findViewById(R.id.model);
                text.setText("简单模式");
            }
            else if(MainActivity.difficulty == 2){
                fis = openFileInput("commonMode_GameData");
                TextView text = (TextView)findViewById(R.id.model);
                text.setText("普通模式");
            }
            else if(MainActivity.difficulty == 3){
                fis = openFileInput("hardMode_GameData");
                TextView text = (TextView)findViewById(R.id.model);
                text.setText("困难模式");
            }
            ois = new MyObjectInputStream(fis);
            dataList = new ArrayList<>();
            while(fis!=null && fis.available()>0){
                System.out.println("fis.available():"+fis.available());
                dataList.add((GameData) ois.readObject());
                System.out.println("正在读取数据");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        dataList.sort(new Comparator<GameData>() {
            @Override
            public int compare(GameData o1, GameData o2) {
                return o2.getScore() - o1.getScore();
            }
        });
        int rowSize = dataList.size();
        System.out.println("rowSize = "+rowSize);
        int colSize = 4;
        tab = (TableLayout) findViewById(R.id.tableData);
        tab.removeAllViews();
        //控制行数
        for (int row = 0; row < rowSize; row++) {
            mTableRow tabRow = new mTableRow(this);
            tabRow.setIndex(row);
            tabRow.setOnClickListener(this);
            GameData gameData = dataList.get(row);

            //控制列数
            for (int col = 0; col < colSize; col++) {

                TextView tv = new TextView(this);
//                tv.setIndex(row);
//                tv.setOnClickListener(this);
                if (col == 0) {
                    tv.setText("第" + (row + 1) + "名");
                } else if (col == 1) {
                    tv.setText(gameData.getPlayerID());
                } else if (col == 2) {
                    tv.setText(String.valueOf(gameData.getScore()));
                } else if (col == 3) {
                    SimpleDateFormat date = new SimpleDateFormat("yyyy--MM--dd HH");
                    tv.setText(date.format(gameData.getDate()));

                }
                tv.setGravity(Gravity.CENTER);
                tabRow.addView(tv);

            }
            tab.addView(tabRow, new TableLayout.LayoutParams(W, H));
        }

    }

    @Override
    public void onClick(View v){
        if(v instanceof mTableRow){
            mTableRow tableRow = ((mTableRow) v);
            int index = tableRow.getIndex();

            tableRow.setChosen(!tableRow.isChosen());
            if(tableRow.isChosen()){
                System.out.println("要删除数据的所引:"+index);
                tableRow.setBackgroundResource(R.color.grey);
                deleteIndex.add(new Integer(index));
            }else{
                System.out.println("取消删除数据的所引:"+index);
                tableRow.setBackgroundResource(R.color.white);
                deleteIndex.remove(new Integer(index));
            }
            System.out.println("所有要删除的数据"+deleteIndex);

        }else if(v.getId() == R.id.btn_delete){
            delete();
        }
    }

    // 先判断是否确认删除
    private void delete(){
        System.out.println("xxx");
        builder.setMessage("是否确定删除选中的数据")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        confirmDelete();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog ad = builder.create();
        ad.show();
    }
    // 确认删除后
    private void confirmDelete(){
        for(int i = 0; i< deleteIndex.size();i++){
            System.out.println(dataList.remove(deleteIndex.get(i).intValue()));
        }
        deleteIndex.clear();
        // 写入文件

        try{
            if(MainActivity.difficulty == 1) {
                fos =openFileOutput("easyMode_GameData",MODE_PRIVATE);
            }
            else if(MainActivity.difficulty == 2){
                fos = openFileOutput("commonMode_GameData",MODE_PRIVATE);
            }
            else if(MainActivity.difficulty == 3){
                fos = openFileOutput("hardMode_GameData",MODE_PRIVATE);
            }
            oos = new MyObjectOutputStream(fos);
            for(int i = 0;i<dataList.size();i++){
//                    System.out.println(dataList.get(i));
                oos.writeObject(dataList.get(i));
            }
            oos.close();

            readFromFileAndShow();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            this.finish();
        }
        return true;
    }
}