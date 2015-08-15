package com.vypeensoft.photoes_sftp_to_unix_server;

import java.io.*;
import java.util.*;
import android.app.Activity;

public class FileUtil {
    //=========================================================================================
    public static String readFileContentsAsString(Activity act, String fileName) throws Exception {
        StringBuffer returnStringBuffer = new StringBuffer();
        InputStream instream = act.openFileInput(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(instream));
        try {
            String line = null; // not declared within while loop
            while ((line = bufferedReader.readLine()) != null) {
                returnStringBuffer.append(line+"\n");
            }
        } finally {
            bufferedReader.close();
            instream.close();
        }
        return returnStringBuffer.toString();
    }
    //=========================================================================================
    public static List<String> readFileContentsAsStringList(Activity act, String fileName) throws Exception {
        List<String> returnArray = new ArrayList<String>();
        InputStream instream = act.openFileInput(fileName);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(instream));
        try {
            String line = null; // not declared within while loop
            while ((line = bufferedReader.readLine()) != null) {
                returnArray.add(line);
            }
        } finally {
            bufferedReader.close();
            instream.close();
        }
        return returnArray;
    }
    //=========================================================================================
    public static void writeStringToNewFile(Activity act, String fileListLocation, String str) throws Exception {
       FileOutputStream fOut = act.openFileOutput(fileListLocation, Activity.MODE_WORLD_READABLE);
       OutputStreamWriter osw = new OutputStreamWriter(fOut); 
       osw.write((str + System.getProperty("line.separator")));
       osw.flush();
       osw.close();
       fOut.close();
    }
    //=========================================================================================
    public static void appendStringToFile(Activity act, String fileListLocation, String str) throws Exception {
       FileOutputStream fOut = act.openFileOutput(fileListLocation, Activity.MODE_APPEND | Activity.MODE_WORLD_READABLE);
       OutputStreamWriter osw = new OutputStreamWriter(fOut); 
       osw.write((str + System.getProperty("line.separator")));
       osw.flush();
       osw.close();
       fOut.close();
    }
    //=========================================================================================
	public static List<String> removeBlanks(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			String oneLine = list.get(i);
			if(oneLine.trim().equals("")) {
				list.remove(i);
				i--;
			}
		}
		return list;
	}
    //=========================================================================================
    //=========================================================================================
    //=========================================================================================
    //=========================================================================================
    //=========================================================================================
}
