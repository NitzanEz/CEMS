package gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logic.AddedTime;
import logic.DownloadManualExaminController;
import logic.FileDownloadInfo;
import logic.Request;
import logic.Test;
import logic.TestSourceTime;
import logic.Users;

import java.util.Timer;
import java.util.TimerTask;

import client.ChatClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.sql.Blob;

// try push
public class StudentManualTestController {
	private DownloadManualExaminController downloadFile;
	private String ToUploadPath;
	private  int remainingTime;
	private boolean StartedTime=false;
    private Test test;
	private ChatClient client;
	private Users student;
	private boolean locked=false;
	private AddedTime added=new AddedTime();
    public AddedTime getAdded() {
		return added;
	}
    public void setStudentAndClient(Users Student,ChatClient client,StudentManualTestController controller) {
    	this.student=Student;
    	this.client=client;
    	this.client.setStudentManualTestController(controller);
    }


	public DownloadManualExaminController getDownloadFile() {
		return downloadFile;
	}
	public void setDownloadFile(DownloadManualExaminController downloadFile) {
		this.downloadFile = downloadFile;
	}
	public void setAdded(AddedTime added) {
		this.added = added;
	}
	public void setTest(Test test){
		this.test=test;

	}
	@FXML
    private Label downloadMes;
	@FXML
    private Button subBtn;
	@FXML
    private Label crsName;
	@FXML
	private Button download;
	@FXML
	private Button upload;
	@FXML
	private Label timeLabel;
	@FXML
	private Label selectedFileLabel;

	private File selectedFile;
	
	 @FXML
	    void submit(ActionEvent event) {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ApproveSubmit.fxml"));
			Parent root;
			try {
			     
				root = loader.load();
				//Stage window = (Stage) subBtn.getScene().getWindow();
				Stage window = new Stage();
				ApproveSubmitController controller=loader.getController();
				//controller.setTest(test);
				controller.setStudentAndClient2(student ,client,this.getController());
				controller.setDigOrMan(1);
				window.setScene(new Scene(root));
				window.show();
			    }
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }

	    	/*try {
				client.sendToServer(new Request("SubmitExam", studentInTest));
				int index = test.getStudentsIdForTest().indexOf(student.getId());
				test.getStudentsIdForTest().remove(index);
				System.out.println("Submitted");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	    }
	    public void CloseWindow() {
        Platform.runLater(() -> {
            Stage currentStage = (Stage) upload.getScene().getWindow();
            currentStage.close();
        });
    }
	    public void setWelcomeLabel() {
	    	
	    	crsName.setText(test.getCourseName() + " Test");
	    	subBtn.setDisable(true);
	    }

	    @FXML
	    void click_download(ActionEvent event) {
	       /*  DirectoryChooser directoryChooser = new DirectoryChooser();
	        directoryChooser.setTitle("Select Download Directory");
	        File selectedDirectory = directoryChooser.showDialog(null);

	        if (selectedDirectory != null) {
	            String selectedDirectoryPath = selectedDirectory.getAbsolutePath();
	            selectedFileLabel.setText("Selected Directory: " + selectedDirectoryPath);
	            String path=selectedDirectory.getPath();
	            if(path.equals("C:\\") | path.equals("D:\\") |  path.equals("F:\\")) {
	               downloadMes.setText("Please choose folder path!");
	            }
	            else {
	            path+= "\\" +"TestId_" + test.getTestId()+"-StId_" + student.getId() + ".docx";
	            FileDownloadInfo fileDownloadinfo = new FileDownloadInfo (path,test.getTestId());

	             download.setDisable(true);
	        		//}
	        		try {
	        			client.openConnection();
	        			if (client.isConnected()) {
	        				client.sendToServer(new Request("DownloadManualExam", fileDownloadinfo));
	        				ServerSocket servsock = new ServerSocket(4444);
	        				 File myFile = new File(fileDownloadinfo.getFileDownloadPath());
	        				      Socket sock = servsock.accept();
	        				      byte[] mybytearray = new byte[4096];
	        				      int bytesRead = -1;
	        				      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
	        				      while(bytesRead=bis.read(mybytearray, 0, mybytearray.length) !=-1) {
	        				      
	        				      OutputStream os = sock.getOutputStream();
	        				      os.write(mybytearray, 0, mybytearray.length);}
	        				      os.flush();
	        				      sock.close();
	        				      servsock.close();
	        			        }

	        			    System.out.println("File downloaded successfully!");
	        			  //  outputStream.close();
	        	            downloadMes.setText("downloaded successfully to " + path);
	        				if(StartedTime == false) {
	        	        		startTimer(test.getDuration() * 60,test.getDuration());
	        	        		StartedTime=true;}
	        			}
	           // }
	        		catch (IOException e) {
	        			e.printStackTrace();}
	        	}
	        }*/
	        
	    }
	    
	    
		
		
		
		

	@FXML
	void click_upload(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Modified File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Documents", "*.doc", "*.docx"));
		selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			ToUploadPath = selectedFile.getPath();
			selectedFileLabel.setText("Selected File: " + selectedFile.getName());
			subBtn.setDisable(false);
		}
	}


	
	 private void updateTimerLabel(int timeInSeconds) {
	        // Convert seconds to minutes and seconds
	        int minutes = timeInSeconds / 60;
	        int seconds = timeInSeconds % 60;

	        String timeText = String.format("%02d:%02d", minutes, seconds);
	        // Update timeLabel on the FX application thread
	        Platform.runLater(() -> timeLabel.setText(timeText));
	    }
	
	private volatile boolean stopThread = false;
    private Thread timeThread;
    boolean addedTime=false;
    private void startTimer(int timeInSeconds,int duration) {
    	TestSourceTime testSourceTime = new TestSourceTime(test.getDuration(),test.getTestId());
        stopThread = false; // Reset stopThread flag
        timeThread = new Thread(() -> {
            try {
                remainingTime = timeInSeconds;
                while (remainingTime >= 0 && !stopThread) {
                	if( remainingTime <=5 && addedTime==false ) {
                		//checkIfDurationChanged
                		//if yes then
                		//addedTime=true;
                		try {
                			client.openConnection();
                			if (client.isConnected()) {
                				client.sendToServer(new Request("CheckIfDurationChanged", testSourceTime));
                				if(added.getAdded() != 0) {
                					remainingTime += added.getAdded() *60;
                					addedTime=true;
                				}
                			} else {
                				System.out.println("Not connected to server.");
                			}
                		} catch (IOException e) {
                			e.printStackTrace();
                		}
                	}
                    updateTimerLabel(remainingTime);
                    Thread.sleep(1000);
                    remainingTime--;
                }
                
                // Timer has finished
                if (!stopThread) {
                	subBtn.setDisable(true);
                	forceSubmit();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timeThread.start();
    }

    public void stopTimer() {
        stopThread = true;
        remainingTime=0;
        updateTimerLabel(remainingTime);
    }
	private void forceSubmit() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ApproveSubmit.fxml"));

        try {
            Parent root = loader.load();
            ApproveSubmitController controller = loader.getController();
            controller.setStudentAndClient2(student, client, this.getController());
            controller.setDigOrMan(1);
            controller.forceSubmit();

            Platform.runLater(() -> {
                Stage window = new Stage();
                window.setScene(new Scene(root));
                window.show();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		public void LockExam(int testid){
		if(testid==test.getTestId()) {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/ApproveSubmit.fxml"));
        try {
            Parent root = loader.load();
            ApproveSubmitController controller = loader.getController();
            controller.setStudentAndClient2(student, client, this.getController());
            controller.setDigOrMan(1);
            controller.examIsLocked();
            Platform.runLater(() -> {
                Stage window = new Stage();
                window.setScene(new Scene(root));
                window.show();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
	}
    
	 public StudentManualTestController getController() {
			return this;
		}
	 
}
