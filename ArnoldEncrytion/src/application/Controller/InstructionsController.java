package application.Controller;

import java.awt.Toolkit;

import javafx.event.Event;
import javafx.fxml.FXML;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;

public class InstructionsController {
	@FXML
	private Hyperlink usagelink;
	@FXML
	private Hyperlink filelink;
	@FXML
	private Hyperlink menulink;
	@FXML
	private Hyperlink imagelink;
	@FXML
	private Hyperlink waitinglink;
	@FXML
	private Hyperlink attrlink;
	@FXML
	private ScrollPane toolusage;
	@FXML
	private ScrollPane filechoose;
	@FXML
	private ImageView proImage;
	@FXML
	private ScrollPane menu;
	@FXML
	private ImageView waitingImage;
	@FXML
	private ScrollPane imageshow;
	@FXML
	private ImageView photoImage;
	@FXML
	private ScrollPane waitinglist;
	@FXML
	private ImageView menuImage;
	@FXML
	private ScrollPane attributes;
	@FXML
	private ImageView fileImage;
	@FXML
	private ImageView openImage;

	public void select(Event event) {
		if(event.getTarget()==usagelink)
		{
			toolusage.toFront();
		}
		else if(event.getTarget()==filelink) {
			filechoose.toFront();
		}
		else if(event.getTarget()==menulink) {
			menu.toFront();
		}
		else if(event.getTarget()==imagelink) {
			imageshow.toFront();
		}
		else if(event.getTarget()==waitinglink) {
			waitinglist.toFront();
		}
		else if(event.getTarget()==attrlink) {
			attributes.toFront();
		}
	}
	public void showbigImage(Event event) {
		if(event.getTarget()==proImage) {
			Stage pro=new Stage();
			Pane pane=new Pane();
			ImageView image=new ImageView(proImage.getImage());
			image.autosize();
			pane.getChildren().add(image);
			Scene proscene=new Scene(pane);
			pro.setScene(proscene);
			pro.show();
			
		}
		else if(event.getTarget()==waitingImage) {
			Stage pro=new Stage();
			Pane pane=new Pane();
			ImageView image=new ImageView(waitingImage.getImage());
			image.autosize();
			pane.getChildren().add(image);
			Scene proscene=new Scene(pane);
			pro.setScene(proscene);
			pro.show();
		}
		else if(event.getTarget()==photoImage) {
			Stage pro=new Stage();
			Pane pane=new Pane();
			ImageView image=new ImageView(photoImage.getImage());
			image.autosize();
			pane.getChildren().add(image);
			Scene proscene=new Scene(pane);
			pro.setScene(proscene);
			pro.show();
		}
		else if(event.getTarget()==menuImage) {
			Stage pro=new Stage();
			Pane pane=new Pane();
			ImageView image=new ImageView(menuImage.getImage());
			image.autosize();
			pane.getChildren().add(image);
			Scene proscene=new Scene(pane);
			pro.setScene(proscene);
			pro.show();
		}
		else if(event.getTarget()==fileImage) {
			Stage pro=new Stage();
			Pane pane=new Pane();
			ImageView image=new ImageView(fileImage.getImage());
			image.autosize();
			pane.getChildren().add(image);
			Scene proscene=new Scene(pane);
			pro.setScene(proscene);
			pro.show();
		}
		else if(event.getTarget()==openImage) {
			Stage pro=new Stage();
			Pane pane=new Pane();
			ImageView image=new ImageView(openImage.getImage());
			image.autosize();
			pane.getChildren().add(image);
			Scene proscene=new Scene(pane);
			pro.setScene(proscene);
			pro.show();
		}
	}
}
