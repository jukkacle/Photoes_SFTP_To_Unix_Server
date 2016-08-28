package com.vypeensoft.photoes_sftp_to_unix_server;

import android.provider.MediaStore;
import android.database.Cursor;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.content.Context;
import android.content.Intent;
import android.widget.PopupWindow;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.EditText;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Properties;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.text.*;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;

public class MainActivity extends Activity {
  private String LOG_FILE_NAME       = "photoes_copy_to_ftp.log.txt";
  private OnClickListener            lastHourCommandExecuteButtonListener  = null;
  private OnClickListener            todayCommandExecuteButtonListener  = null;
  private OnClickListener            allCommandExecuteButtonListener  = null;
  private OnClickListener            screenshotsCommandExecuteButtonListener  = null;
  private OnClickListener            apksCommandExecuteButtonListener  = null;
  private OnClickListener            calCommandExecuteButtonListener  = null;
  private OnClickListener            renameCalCommandExecuteButtonListener  = null;
  private OnClickListener            downCommandExecuteButtonListener  = null;

  private OnClickListener            profilesButtonClickListener = null;
  private OnClickListener            exitCommandButtonListener = null;

  private String   SERVER   = "192.168.1.30";
  private String   USERNAME = "pi";
  private String   PASSWORD = "Remote$Access";
  //---------------------------------------------------------------------------------------------------------------------
  Button btnProfiles;
  Button btnExit;
  //---------------------------------------------------------------------------------------------------------------------
  Button btn_upload_last_hour;
  Button btn_upload_today;
  Button btn_upload_all;
  Button btn_screenshots;
  Button btn_apks;
  Button btn_cal;
  Button btn_renamecal;
  Button btn_down;
  //***********************************************************************************************************************//
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    writeToLog("Entering Photoes Upload to Unix");
    //---------------------------------------------------------------------------------------------------------------------
    lastHourCommandExecuteButtonListener = new OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
		doFileUpload("LAST_HOUR");
      }
    };
    //---------------------------------------------------------------------------------------------------------------------
    todayCommandExecuteButtonListener = new OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
		doFileUpload("TODAY");
      }
    };
    //---------------------------------------------------------------------------------------------------------------------
    allCommandExecuteButtonListener = new OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
		doFileUpload("ALL");
      }

	};
    //---------------------------------------------------------------------------------------------------------------------
    screenshotsCommandExecuteButtonListener = new OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
		doScreenshotsFileUpload();
      }

	};
    //---------------------------------------------------------------------------------------------------------------------
    apksCommandExecuteButtonListener = new OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
		doAPKFileUpload();
      }

	};
    //---------------------------------------------------------------------------------------------------------------------
    calCommandExecuteButtonListener = new OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
		doCalFileUpload();
      }

	};
    //---------------------------------------------------------------------------------------------------------------------
    renameCalCommandExecuteButtonListener = new OnClickListener() {
      public void onClick(View v) {
        writeToLog("on click...");
        int id = v.getId();
		doRenameCal();
      }

	};
    //---------------------------------------------------------------------------------------------------------------------
    downCommandExecuteButtonListener = new OnClickListener() {
      public void onClick(View v) {
        int id = v.getId();
		doDownload();
      }

	};
    //---------------------------------------------------------------------------------------------------------------------
    exitCommandButtonListener = new OnClickListener() {
      public void onClick(View v) {
        finish();
        System.exit(-1);
      }
    };
    //---------------------------------------------------------------------------------------------------------------------
    profilesButtonClickListener = new OnClickListener() {
      public void onClick(View v) {
         initiateProfilesPopupWindow();
      }
    };
    //---------------------------------------------------------------------------------------------------------------------

    setContentView(R.layout.main);


    //---------------------------------------------------------------------------------------------------------------------
    btnProfiles = (Button) findViewById(R.id.button_profile);
    btnProfiles.setOnClickListener(profilesButtonClickListener);
    //---------------------------------------------------------------------------------------------------------------------
    btnExit = (Button) findViewById(R.id.button_exit);
    btnExit.setOnClickListener(exitCommandButtonListener);
    //---------------------------------------------------------------------------------------------------------------------

    try {
//        btn_upload_last_hour = (Button) findViewById(R.id.btn_upload_last_hour);         
//        btn_upload_last_hour.setOnClickListener(lastHourCommandExecuteButtonListener);         
//        btn_upload_last_hour.setVisibility(View.VISIBLE);         

//        btn_upload_today = (Button) findViewById(R.id.btn_upload_today);         
//        btn_upload_today.setOnClickListener(todayCommandExecuteButtonListener);         
//        btn_upload_today.setVisibility(View.VISIBLE);         

        btn_upload_all = (Button) findViewById(R.id.btn_upload_all);         
        btn_upload_all.setOnClickListener(allCommandExecuteButtonListener);         
        btn_upload_all.setVisibility(View.VISIBLE);         

        btn_screenshots = (Button) findViewById(R.id.btn_screenshots);         
        btn_screenshots.setOnClickListener(screenshotsCommandExecuteButtonListener);         
        btn_screenshots.setVisibility(View.VISIBLE);         

        btn_apks = (Button) findViewById(R.id.btn_apks);         
        btn_apks.setOnClickListener(apksCommandExecuteButtonListener);         
        btn_apks.setVisibility(View.VISIBLE);         

        btn_cal = (Button) findViewById(R.id.btn_cal);         
        btn_cal.setOnClickListener(calCommandExecuteButtonListener);         
        btn_cal.setVisibility(View.VISIBLE);         

        btn_renamecal = (Button) findViewById(R.id.btn_renamecal);         
        btn_renamecal.setOnClickListener(renameCalCommandExecuteButtonListener);         
        btn_renamecal.setVisibility(View.VISIBLE);         

        btn_down = (Button) findViewById(R.id.btn_down);         
        btn_down.setOnClickListener(downCommandExecuteButtonListener);         
        btn_down.setVisibility(View.VISIBLE);         


        writeToLog("Before Screens refresh");

		refreshMainScreen();
    } catch (Exception e) {
        e.printStackTrace();
        showToast(e.getMessage());
    }


  } //end of onCreate()
  //***********************************************************************************************************************//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
  //***********************************************************************************************************************//
     public void doFileUpload(String duration) {
        try {
			//first check in external sd card
            File photoesDirectory = new File("/storage/sdcard1/DCIM/Camera");
            writeToLog("photoesDirectory ="+photoesDirectory.getAbsolutePath());
			if(!photoesDirectory.exists()) {
				//else check in another external sd card location
				photoesDirectory = new File("/storage/extSdCard/DCIM/Camera");
			}
			if(!photoesDirectory.exists()) {
				//else check in internal sd card
				photoesDirectory = new File("/storage/emulated/0/DCIM/Camera");
			}
            showToast("photoesDirectory ="+photoesDirectory );

			long currentTime = System.currentTimeMillis();
            File[] files = photoesDirectory.listFiles();
            for (File currFile : files) {
				boolean fileToBeMoved = false;
				long lastModified = currFile.lastModified();
				if(duration.equals("LAST_HOUR")) {
					if(currentTime <= lastModified + (60 * 60 * 1000)) {
						fileToBeMoved = true;
					}
				}
				if(duration.equals("TODAY")) {
					if(currentTime <= lastModified + (24 * 60 * 60 * 1000)) {
						fileToBeMoved = true;
					}
				}
				if(duration.equals("ALL")) {
					fileToBeMoved = true;
				}
				if(fileToBeMoved == true) {
					writeToLog("CurFile="+currFile.getAbsolutePath());
					executeFTPCommand(USERNAME,PASSWORD,SERVER,22, currFile.getAbsolutePath());
				}
            }
            
            //executeFTPCommand(currentProfile.userName, currentProfile.password, currentProfile.ipAddress, Integer.valueOf(currentProfile.port).intValue(), currentCommand.commandString);
            
        } catch(Exception e) {
            showToast("error="+e.getMessage());
            writeToLog(e);
        }
	 }
  //***********************************************************************************************************************//
  //***********************************************************************************************************************//
     public void doScreenshotsFileUpload() {
        try {
			//first check in external sd card
            File photoesDirectory = new File("/storage/emulated/0/DCIM/Screenshots");
            showToast("screenshotsDirectory ="+photoesDirectory );
            writeToLog("photoesDirectory ="+photoesDirectory.getAbsolutePath());
//			if(!photoesDirectory.exists()) {
//				//else check in another external sd card location
//				photoesDirectory = new File("/storage/extSdCard/DCIM/Camera");
//			}
//			if(!photoesDirectory.exists()) {
//				//else check in internal sd card
//				photoesDirectory = new File("/storage/emulated/0/DCIM/Camera");
//			}
            showToast("screenshotsDirectory ="+photoesDirectory );

			long currentTime = System.currentTimeMillis();
            File[] files = photoesDirectory.listFiles();
            for (File currFile : files) {
				writeToLog("CurFile="+currFile.getAbsolutePath());
				executeFTPCommand(USERNAME,PASSWORD,SERVER,22, currFile.getAbsolutePath());
            }
            
            //executeFTPCommand(currentProfile.userName, currentProfile.password, currentProfile.ipAddress, Integer.valueOf(currentProfile.port).intValue(), currentCommand.commandString);
            
        } catch(Exception e) {
            showToast("error="+e.getMessage());
            writeToLog(e);
        }
	 }
  //***********************************************************************************************************************//
     public void doAPKFileUpload() {
        try {
			//first check in external sd card
            File photoesDirectory = new File("/storage/sdcard1/App_Backup_Restore");
            showToast("screenshotsDirectory ="+photoesDirectory );
            writeToLog("photoesDirectory ="+photoesDirectory.getAbsolutePath());
			if(!photoesDirectory.exists()) {
				//else check in internal sd card
				photoesDirectory = new File("/storage/extSdCard/App_Backup_Restore");
			}

			long currentTime = System.currentTimeMillis();
            File[] files = photoesDirectory.listFiles();
            for (File currFile : files) {
				writeToLog("CurFile="+currFile.getAbsolutePath());
				executeFTPCommand(USERNAME,PASSWORD,SERVER,22, currFile.getAbsolutePath());
            }
            
        } catch(Exception e) {
            showToast("error="+e.getMessage());
            writeToLog(e);
        }
	 }
  //***********************************************************************************************************************//
     public void doCalFileUpload() {
        try {
			//first check in external sd card
            File photoesDirectory = new File("/storage/sdcard1/callx/allcalls");
            showToast("screenshotsDirectory ="+photoesDirectory );
            writeToLog("photoesDirectory ="+photoesDirectory.getAbsolutePath());

			long currentTime = System.currentTimeMillis();
            File[] files = photoesDirectory.listFiles();
            for (File currFile : files) {
				writeToLog("CurFile="+currFile.getAbsolutePath());
				executeFTPCommand(USERNAME,PASSWORD,SERVER,22, currFile.getAbsolutePath());
            }
            
        } catch(Exception e) {
            showToast("error="+e.getMessage());
            writeToLog(e);
        }
	 }
  //***********************************************************************************************************************//
     public void doRenameCal() {
     showToast("entering doRenameCal()");
        try {
			//first check Callx folder
            File photoesDirectory = new File("/storage/sdcard1/callx/allcalls");
            showToast("calDirectory ="+photoesDirectory );
            writeToLog("calDirectory ="+photoesDirectory);
            writeToLog("calDirectory ="+photoesDirectory.getAbsolutePath());

			long currentTime = System.currentTimeMillis();
            File[] files = photoesDirectory.listFiles();
            for (File currFile : files) {
				writeToLog("CurFile="+currFile.getAbsolutePath());
				String oldName = currFile.getAbsolutePath();
				if(oldName.endsWith(".calx")) {
					//don't do anything
				} else {
					String newName = oldName + ".calx";
					FileUtil.renameFile(oldName, newName);
				}
            }

		
		
		
			//then check in CallRecorder folder
            File photoesDirectory1 = new File("/storage/emulated/0/CallRecorder/");
            showToast("calDirectory ="+photoesDirectory1);
            writeToLog("calDirectory ="+photoesDirectory1);
            writeToLog("calDirectory ="+photoesDirectory1.getAbsolutePath());

			long currentTime1 = System.currentTimeMillis();
            File[] files1 = photoesDirectory1.listFiles();
            for (File currFile : files1) {
				writeToLog("CurFile="+currFile.getAbsolutePath());
				String oldName = currFile.getAbsolutePath();
				if(oldName.endsWith(".calx")) {
					//don't do anything
				} else {
					String newName = oldName + ".calx";
					FileUtil.renameFile(oldName, newName);
				}
            }

		
		
		
		
			//then check in SVR folder
            photoesDirectory1 = new File("/storage/emulated/0/SVR/");
            showToast("calDirectory ="+photoesDirectory1);
            writeToLog("calDirectory ="+photoesDirectory1);
            writeToLog("calDirectory ="+photoesDirectory1.getAbsolutePath());

			currentTime1 = System.currentTimeMillis();
            files1 = photoesDirectory1.listFiles();
            for (File currFile : files1) {
				writeToLog("CurFile="+currFile.getAbsolutePath());
				String oldName = currFile.getAbsolutePath();
				if(oldName.endsWith(".calx")) {
					//don't do anything
				} else {
					String newName = oldName + ".calx";
					FileUtil.renameFile(oldName, newName);
				}
            }
		
		
		} catch(Exception e) {
            showToast("error="+e.getMessage());
            writeToLog(e);
        }
	 }
  //***********************************************************************************************************************//

     public String executeFTPCommand(String username,String password,String hostname,int port, String fileName) throws Exception {
        boolean conStatus = false;
        Channel channel = null;
        
        
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();

        channel = (Channel) session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;
        sftp.put(fileName, "/home/pi/data/photoes_upload/");
        sftp.disconnect();

        return null;
    }
  //***********************************************************************************************************************//
     public String doDownload() {
		try {
			doDownload(USERNAME,PASSWORD,SERVER,22, "");			
		}
		catch (Exception e)
		{
		}
		return "";
	 }

     public String doDownload(String username,String password,String hostname,int port, String fileName) throws Exception {
        boolean conStatus = false;
        Channel channel = null;
        
        
		writeToLog("Entering doDownload()");
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);
        session.connect();

        channel = (Channel) session.openChannel("sftp");
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;


		Vector<ChannelSftp.LsEntry> list = sftp.ls("*");
		writeToLog("list ="+list );
		for(ChannelSftp.LsEntry entry : list) {
			sftp.get(entry.getFilename(), "/storage/sdcard1/callx/" + entry.getFilename());
			writeToLog("one File="+entry.getFilename());
		}

        sftp.disconnect();

        return null;
    }
  //***********************************************************************************************************************//
    public void showToast(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
   }
  //***********************************************************************************************************************//
    public void writeToLog(String msg) {
        try {
            FileUtil.appendStringToFile(MainActivity.this, LOG_FILE_NAME, msg);
        } catch(Exception e) {
            showToast("Unable to write to log file");
        }
   }
    public void writeToLog(Throwable t) {
        try {
			Date todayDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd-hh-mm-ss");
			String todayDateStringNewFormat = sdf.format(todayDate.getTime());
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            String msg1 = sw.toString(); // stack trace as a string
            writeToLog(todayDateStringNewFormat +" : " +msg1);
        } catch(Exception e) {
            showToast("Unable to write to log file");
        }
   }
  //***********************************************************************************************************************//
  private void initiateProfilesPopupWindow() {
//    Intent s = new Intent(MainActivity.this, ProfileActivity.class);
//    this.startActivity(s);
  }
  //***********************************************************************************************************************//
  private void refreshMainScreen() throws Exception {
  }
  //***********************************************************************************************************************//
}
