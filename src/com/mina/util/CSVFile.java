/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author mina
 */
public class CSVFile extends ArrayList<ArrayList<String>>{
    ArrayList<String> header;
    public CSVFile(String path) throws IOException{
        File file = new File(path);
        //System.out.println(file.getAbsolutePath());
        Reader in = new FileReader(file);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        header = null;
        for (CSVRecord record : records) {
            ArrayList<String> row = new ArrayList<>();
            for(String s:record){
                row.add(s);
            }
            if(header == null){
                header = row;
            }else{
                add(row);
            }
        }
    }
    public boolean isEmpty(int row, int col){
        if(size() <= row || get(row).size() <= col){
            return true;
        }
        return getString(row, col).isEmpty();
    }
    public Double getDouble(int row, int col){
        return toDouble(getString(row, col));
    }
    public static Double toDouble(String str){
        if(str == null){
            return null;
        }else if(str.isEmpty()){
            return 0.;
        }
        try{
            return Double.parseDouble(str);
        }catch(NumberFormatException e){
            return null;
        }
    }
    public String getString(int row, int col){
        return get(row).get(col);
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        CSVFile file = new CSVFile("doc/oscillator/primum.csv");
        for (ArrayList<String> row : file) {
            for(String cell:row){
                //System.out.print(cell + "(" + cell.isEmpty() + "),");
            }
            //System.out.println();
        }
    }
}
