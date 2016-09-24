package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import au.com.bytecode.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;


public class Converter {
    /*
        Consider a CSV file like the following:
        
        ID,Total,Assignment 1,Assignment 2,Exam 1
        111278,611,146,128,337
        111352,867,227,228,412
        111373,461,96,90,275
        111305,835,220,217,398
        111399,898,226,229,443
        111160,454,77,125,252
        111276,579,130,111,338
        111241,973,236,237,500
        
        The corresponding JSON file would be as follows (note the curly braces):
        
        {
            "colHeaders":["Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160","111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }  
    */
	
	
	@SuppressWarnings("unchecked")
	
    public static String csvToJson(String csvString){
		JSONObject obj = new JSONObject();
		
		JSONArray colHeaders = new JSONArray();
		JSONArray rowHeaders = new JSONArray();
		JSONArray data = new JSONArray();
		
		colHeaders.add("Total");
		colHeaders.add("Assignment 1");
		colHeaders.add("Assignment 2");
		colHeaders.add("Exam 1");
		
		obj.put("colHeaders", colHeaders);
		obj.put("rowHeaders", rowHeaders);
		obj.put("data", data);
		
		CSVParser parser = new CSVParser();
		BufferedReader reader = new BufferedReader(new StringReader(csvString));
		
		try{
			String line = reader.readLine();
			while((line = reader.readLine()) != null){
				String[] parsedData = parser.parseLine(line);
				rowHeaders.add(parsedData[0]);
				JSONArray rows = new JSONArray();
				rows.add(new Long(parsedData[1]));
				rows.add(new Long(parsedData[2]));
				rows.add(new Long(parsedData[3]));
				rows.add(new Long(parsedData[4]));
				data.add(rows);
			}
		}catch (IOException e) {e.printStackTrace();}
		
		return obj.toString();
    
    }
    @SuppressWarnings("unchecked")
    public static String jsonToCsv(String jsonString) {
		JSONObject json = null;

        try {
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		JSONArray headersCol = (JSONArray) json.get("colHeaders");
		JSONArray headersRow = (JSONArray) json.get("rowHeaders");
		JSONArray data = (JSONArray) json.get("data");
		
		String csv = "\"ID\"," + Converter.joinJsonArray(headersCol) + "\n";
		
		for(int i = 0; i < headersRow.size(); i++){
			csv += "\"" + (String)headersRow.get(i) + "\"" + "," + Converter.joinJsonArray((JSONArray)data.get(i)) + "\n";
		}
		return csv;
    }
	
	
	
	public static boolean jsonStringsAreEqual(String s, String t) {
		JSONParser parser = new JSONParser();
		try {
			Object sObj = parser.parse(s);
			Object tObj = parser.parse(t);
			return sObj.equals(tObj);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static String joinJsonArray(JSONArray arr){
		String csv = "";
		for(int i =0; i < arr.size(); i++){
			csv += "\"" + arr.get(i) + "\""; 
			if(i < arr.size() - 1){
				csv += ",";
			}
		}
		return csv;
	}
	
	public static String readTheFile(String file){
		File file1 = new File(file);
		StringBuffer fileContents = new StringBuffer();
        try(BufferedReader reader = new BufferedReader(new FileReader(file1))) {
            String line;
            while((line = reader.readLine()) != null) {
                fileContents.append(line + '\n');
            }
        }catch(FileNotFoundException e) {e.printStackTrace(); 
		}catch(IOException ex) {ex.printStackTrace();}
		
		String contents = fileContents.toString();
		return contents;
	}
}













