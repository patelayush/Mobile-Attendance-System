package com.example.jarvis.mas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.*;

import static android.content.ContentValues.TAG;

/**
 * Created by jarvis on 25/4/16.
 */
public class WriteExcel {

    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat  times;
    private WritableCellFormat timesBold;

    private String inputfile;
    private String deptname,divname,sub;
    private int sem;
    ArrayList<Integer> sids=new ArrayList<>();
    ArrayList<Integer> csids=new ArrayList<>();
    ArrayList<Integer> totals=new ArrayList<>();
    ArrayList<Integer> presents=new ArrayList<>();
    ArrayList<Integer> percents=new ArrayList<>();
    public void setOutputFile(String inputfile)
    {
        this.inputfile=inputfile;
    }

    public void write() throws IOException , WriteException {




        File sdcard= Environment.getExternalStorageDirectory();
        File directory=new File(sdcard.getPath()+"/MAS");
        System.out.println(directory);
        DatabaseManager databaseManager=new DatabaseManager(Excelclass.getInstance());
        System.out.println(Excelclass.getInstance().cid);
        deptname=databaseManager.getDepartmentname(Excelclass.getInstance().cid);
        divname=databaseManager.getDivname(Excelclass.getInstance().cid);
        sem=databaseManager.getSemester(Excelclass.getInstance().cid);
        sub=databaseManager.getSubject(Excelclass.getInstance().cid);
        for(int k=0;k<Excelclass.getInstance().snames.size();k++)
        {
            sids.add(databaseManager.getSid(Excelclass.getInstance().snames.get(k),
                    Excelclass.getInstance().cid));
        }
        for(int a=0;a<sids.size();a++)
            csids.add(databaseManager.showcsid(sids.get(a),Excelclass.getInstance().cid ));
        for(int b=0;b<csids.size();b++) {
            percents.add(databaseManager.getattendancepercentage(csids.get(b)));
            totals.add(databaseManager.gettotalattendance(csids.get(b)));
            presents.add(databaseManager.getpresentcount(csids.get(b)));
        }


        String filename= deptname+"-"+divname+"-"+sub+"-"+sem+"th sem"+".xls";
        if(!directory.isDirectory())
        {
            directory.mkdir();
            Log.d("Making directpry", directory.toString());
        }
        File file=new File(directory,filename);
        System.out.println(file);

        WorkbookSettings workbookSettings=new WorkbookSettings();
        workbookSettings.setLocale(new Locale("en", "EN"));


        WritableWorkbook workbook= Workbook.createWorkbook(file, workbookSettings);
        workbook.createSheet(deptname+"-"+divname, 0);
        WritableSheet excelsheet=workbook.getSheet(0);
        createLabel(excelsheet);
        createContent(excelsheet);

        workbook.write();
        workbook.close();
        
    }

    public void createLabel(WritableSheet sheet) throws WriteException {
        WritableFont times10pt=new WritableFont(WritableFont.TIMES,12);
        times=new WritableCellFormat(times10pt);
        times.setWrap(true);

        WritableFont times10ptbold=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,false,
                UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptbold);
        timesBoldUnderline.setWrap(true);


        WritableFont timesbold=new WritableFont(WritableFont.TIMES,12,WritableFont.BOLD,false);
        timesBold = new WritableCellFormat(timesbold);
        timesBold.setWrap(true);

        CellView cellView=new CellView();
        cellView.setFormat(times);
        cellView.setFormat(timesBoldUnderline);
        cellView.setAutosize(true);

        addCaption(sheet, 0, 0, "Roll No.");
        addCaption(sheet, 0, 1, "Name");
        addCaption(sheet, 0, 2, "Present");
        addCaption(sheet, 0, 3, "Total Classes");
        addCaption(sheet, 0, 4, "Percentage");

    }
    public void createContent(WritableSheet sheet) throws WriteException{
        for(int i=0;i<Excelclass.getInstance().srolls.size();i++)
        {
            addNumber(sheet, (i+1), 0, Excelclass.getInstance().srolls.get(i));
            addLabel(sheet, (i+1), 1, Excelclass.getInstance().snames.get(i));
            addNumber(sheet, (i+1), 2, presents.get(i));
            addNumber(sheet, (i+1), 3, totals.get(i));
            addNumberbold(sheet, (i+1), 4, percents.get(i));
        }
    }

    private void addCaption(WritableSheet sheet, int row, int column,String s) throws WriteException {
        Label label;
        label=new Label(column,row,s,timesBoldUnderline);
        sheet.addCell(label);
    }

    private void addLabel(WritableSheet sheet, int row,int column,String s) throws WriteException {
        Label label;
        label=new Label(column,row,s,times);
        sheet.addCell(label);
    }

    private void addNumber(WritableSheet sheet, int row,int column,Integer integer) throws WriteException {
        jxl.write.Number number;
        number=new jxl.write.Number(column,row,integer,times);
        sheet.addCell(number);
    }
    private void addNumberbold(WritableSheet sheet, int row,int column,Integer integer) throws WriteException {
        jxl.write.Number number;
        number=new jxl.write.Number(column,row,integer,timesBold);
        sheet.addCell(number);
    }
}
