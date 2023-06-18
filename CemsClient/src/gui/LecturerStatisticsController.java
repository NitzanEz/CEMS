package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.Exam;
import logic.Request;
import logic.Users;

public class LecturerStatisticsController implements Initializable {
	private ChatClient client;
	private Users lecturer;
	private ArrayList<Exam> exams;
	private ArrayList<Exam> gradesArr;
	private float GPA;
	private float MEDIAN;
	@FXML
	private TableColumn<Exam, String> courseName;

	@FXML
	private TableColumn<Exam, Integer> examId;

	@FXML
	private TableView<Exam> examsTable;

	@FXML
	private TableColumn<Exam, String> lComments;

	@FXML
	private Label label;

	@FXML
	private TableColumn<Exam, String> sComments;

	@FXML
	private TableColumn<Exam, Integer> testTime;

	@FXML
	private Button backBtn;

	@FXML
	private TextField gpa;
	@FXML
	private TextField median;

	@FXML
	void viewStats(ActionEvent event) {
		Exam exam = examsTable.getSelectionModel().getSelectedItem();
		if (exam == null) {
			label.setText("Please select an exam");
			return;
		}
		calculateGpa();
		calculateMedian();

		// if (gradesArr == null) {
		// 	label.setText("No grades for this exam");
		// 	return;
		// }else {
		// 	System.out.println(gradesArr.size());
		// }
		// for (Exam e : gradesArr) {
		// 	System.out.println(e.getGrade());

		//}

		Platform.runLater(()->{
			gpa.setText(String.valueOf(GPA));
			median.setText(String.valueOf(MEDIAN));
		});

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		examId.setCellValueFactory(new PropertyValueFactory<Exam, Integer>("examId"));
		courseName.setCellValueFactory(new PropertyValueFactory<Exam, String>("courseName"));
		lComments.setCellValueFactory(new PropertyValueFactory<Exam, String>("lecturerComments"));
		sComments.setCellValueFactory(new PropertyValueFactory<Exam, String>("studentComments"));
		testTime.setCellValueFactory(new PropertyValueFactory<Exam, Integer>("testTime"));

	}

	public void setExamsTable(ArrayList<Exam> exam) {
		examsTable.getItems().clear();
		examsTable.getItems().addAll(exam);
	}

	public void getExamsTable() {

		Request request = new Request("getExamsByLecturer", lecturer);
		try {
			client.openConnection();
			client.sendToServer(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setClientAndLecturer(ChatClient client, Users lecturer) {
		this.client = client;
		this.lecturer = lecturer;
	}

	public void setExams(ArrayList<Exam> exams) {
		this.exams = exams;
	}

	public ArrayList<Exam> getExams() {
		return exams;
	}

	public void setLabel(String label) {
		this.label.setText(label);
	}

	@FXML
	void backToMainScreen(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/LecturerPage.fxml")); // specify the path to the
																								// main screen FXML file
		Parent parent = null;
		try {
			parent = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene mainScene = new Scene(parent);

		// Get the main screen's controller and pass the ChatClient and lecturer
		// instances to it
		LecturerPageController controller = loader.getController();
		controller.setLecturerAndClient(lecturer, client);
		controller.getOngoingExamsTable();
		client.setController(controller);

		// Get the Stage information
		Stage window = (Stage) backBtn.getScene().getWindow();
		window.setScene(mainScene);
		window.show();
	}

	public void startExamSuccess() {
		Platform.runLater(() -> {
			label.setText("Exam started successfully");
		});

	}

	public void startExamFailed() {
		Platform.runLater(() -> {
			label.setText("Exam already started with this code");
		});
	}

	public void getGrades() {
		lecturer.setFlag(1);
		Request request = new Request("getGrades", lecturer);

		try {
			client.openConnection();
			client.sendToServer(request);
			lecturer.setFlag(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setGradesArr(ArrayList<Exam> gradesArr) {
		this.gradesArr = gradesArr;
	}

	public void calculateGpa() {
		int sum = 0;
		for (Exam exam : gradesArr) {
			sum += exam.getGrade();
		}
		GPA = sum / gradesArr.size();
	}

	public void calculateMedian(){
		int medianIndex = gradesArr.size() / 2;
		if (gradesArr.size() % 2 == 0) {
			MEDIAN = (gradesArr.get(medianIndex).getGrade() + gradesArr.get(medianIndex - 1).getGrade()) / 2;
		} else {
			MEDIAN = gradesArr.get(medianIndex).getGrade();
		}
	}

}
