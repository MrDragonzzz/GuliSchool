package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
      String fileName="C:\\Users\\龙嘉翼\\Desktop\\01.xlsx";
//
//        EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getData());
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();
    }

//    private static List<DemoData> getData(){
//        ArrayList<DemoData> list = new ArrayList<>();
//        for (int i = 0; i <10; i++) {
//            DemoData demoData = new DemoData();
//            demoData.setSon(i);
//            demoData.setSname("lucy"+i);
//            list.add(demoData);
//        }
//
//        return list;
//    }


}
