package com.aircraftWar.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.aircraftWar.Dao.UserData;
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
    private ScrollView scrollView;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private List<UserData> dataList;
    private ArrayList<Integer> deleteIndex;
    private TableLayout tab;
    private ArrayList<String> tabCol = new ArrayList<>();
    private ArrayList<String> tabH = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        findViewById(R.id.btn_delete).setOnClickListener(this);
        scrollView = findViewById(R.id.table);

//        List<UserData> dataList = null;
        readFromFileAndShow();
        deleteIndex = new ArrayList<>();

    }

    public void readFromFileAndShow(){
        dataList = null;
        try {
            System.out.println("准备读取数据");
            if(MainActivity.difficulty == 1) {
                fis =openFileInput("easyGameData");
                TextView text = (TextView)findViewById(R.id.model);
                text.setText("简单模式");
            }
            else if(MainActivity.difficulty == 2){
                fis = openFileInput("commonGameData");
                TextView text = (TextView)findViewById(R.id.model);
                text.setText("普通模式");
            }
            else if(MainActivity.difficulty == 3){
                fis = openFileInput("hardGameData");
                TextView text = (TextView)findViewById(R.id.model);
                text.setText("困难模式");
            }
            ois = new MyObjectInputStream(fis);
            dataList = new ArrayList<>();
            while(fis.available()>0){
                System.out.println("fis.available():"+fis.available());
                dataList.add((UserData) ois.readObject());
                System.out.println("正在读取数据");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        dataList.sort(new Comparator<UserData>() {
            @Override
            public int compare(UserData o1, UserData o2) {
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
            UserData userData = dataList.get(row);

            //控制列数
            for (int col = 0; col < colSize; col++) {

                TextView tv = new TextView(this);
//                tv.setIndex(row);
//                tv.setOnClickListener(this);
                if (col == 0) {
                    tv.setText("第" + (row + 1) + "名");
                } else if (col == 1) {
                    tv.setText(userData.getPlayerID());
                } else if (col == 2) {
                    tv.setText(String.valueOf(userData.getScore()));
                } else if (col == 3) {
                    SimpleDateFormat date = new SimpleDateFormat("yyyy--MM--dd HH");
                    tv.setText(date.format(userData.getDate()));

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

            for(int i = 0; i< deleteIndex.size();i++){
                System.out.println(dataList.remove(deleteIndex.get(i).intValue()));
            }
            deleteIndex.clear();
            // 写入文件

            try{
                if(MainActivity.difficulty == 1) {
                    fos =openFileOutput("easyGameData",MODE_PRIVATE);
                }
                else if(MainActivity.difficulty == 2){
                    fos = openFileOutput("commonGameData",MODE_PRIVATE);
                }
                else if(MainActivity.difficulty == 3){
                    fos = openFileOutput("hardGameData",MODE_PRIVATE);
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
    }
}