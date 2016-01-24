package accenture.amref;
/**
 * Author: Charles Zhang
 * Date: 1/14/15
 * Purpose: A class to read in the curriculum.
 */

import android.content.Context;
import android.content.res.AssetManager;

import android.util.Log;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;


public class ReadFile {

    private ArrayList<Message> list;
    private static final String [] FILE_HEADER_MAPPING = {"Txt","Type","Subtype","Answer","File"};
    public ReadFile(final Context context, final int id) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final Context x = context;
                AssetManager manager;
                String line = null;
                String[] parts;
                FileReader fileReader = null;
                List messages = new ArrayList();
                list = new ArrayList<Message>();
                CSVParser csvFileParser = null;
                try{

                    //Create the CSVFormat object with the header mapping
                    CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING).withDelimiter(',');
                    //initialize FileReader object
                    InputStream in = x.getResources().openRawResource(id);
                    InputStreamReader isr = new InputStreamReader(in);
                    //initialize CSVParser object
                    csvFileParser = new CSVParser(isr, csvFileFormat);
                    List csvRecords = csvFileParser.getRecords();

                    //Read the CSV file records starting from the second record to skip the header
                    for (int i = 1; i < csvRecords.size(); i++) {

                        CSVRecord record = (CSVRecord) csvRecords.get(i);
                        //Log.i("records",record.toString());
                        //get message record
                        String text = record.get(0);
                        String type = record.get(1);
                        String subtype = record.get(2);
                        String answer = record.get(3);
                        String filename = record.get(4);
                        if(record.get("Txt") == null){
                            text = "";
                        }
                        if(record.get("Type") == null){
                            type = "";
                        }
                        if(record.get("Subtype") == null){
                            subtype = "";
                        }
                        if(record.get("Answer") == null){
                            answer = "";
                        }
                        if(record.get("File") == null){
                            filename = "";
                        }
                        Message message1 = new Message(text, type, subtype, answer, filename);
                        //Log.i("readFile size", Integer.toString(list.size()));
                        if(message1 != null){
                            list.add(message1);
                        }

                    }
                }
                catch(Exception e2){
                    e2.printStackTrace();
                    //Log.d("readFiles", e2.toString());
                }
                finally {
                    try {
                        csvFileParser.close();
                    } catch (Exception e) {

                        //System.out.println("Error while closing fileReader/csvFileParser !!!");
                        //Log.d("readFiles", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public ArrayList<Message> getList(){
        return list;
    }
}

