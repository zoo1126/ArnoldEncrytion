package application.Controller;

import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;

import javafx.scene.control.TabPane;

import javafx.scene.control.Label;

import javafx.scene.control.ScrollPane;

import javafx.scene.control.TextArea;

import javafx.scene.control.Tab;

public class AttributeController {
	@FXML
	private TabPane tabpane;
	@FXML
	private Tab tab1;
	@FXML
	private HBox hbox;
	@FXML
	private Label inputlabel;
	@FXML
	public static TextField inputtext;
	@FXML
	private TextField location;
	@FXML
	private Button b2;
	@FXML
	private Button b3;
	@FXML
	private Tab tab2;
	@FXML
	private ScrollPane textscroll;
	@FXML
	private TextArea console;
	@FXML
	private Tab tab3;
	@FXML
	private ScrollPane textscroll2;
	@FXML
	private TextArea console2;

	public void onselectchanged(Event event) {
		if(event.getTarget()==tab1) {
			
		}
	}
	public void changtime(Event event) {
		
	}
}
