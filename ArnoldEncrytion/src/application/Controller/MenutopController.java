package application.Controller;

import static application.MainApp.radiobutton;
import static application.MainApp.selectphoto;
import static application.MainApp.tabselectende;

import application.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Menu;

public class MenutopController {
	@FXML
	private Menu file;
	@FXML
	private MenuItem open;
	@FXML
	private MenuItem close;
	@FXML
	private Menu manage;
	@FXML
	private MenuItem all;
	@FXML
	private MenuItem noall;
	@FXML
	private Menu help;
	@FXML
	private MenuItem usage;
	@FXML
	private MenuItem aboutus;
	@FXML
	private AnchorPane Menutop;


	private Boolean use=false;
	private Stage ins;
	public void fileevent(Event event) {
		MenuItem item=(MenuItem)event.getTarget();
		if(item==open)
		{
			Stage addpic=new Stage();
		
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("View/Addpic.fxml"));
	        try {
	       	 VBox vbox=(VBox)loader.load();
	       	 Scene scene=new Scene(vbox);
	       	 addpic.setScene(scene);
	       	 addpic.setTitle("选择图像文件");
	       	 Scene rootscene=Menutop.getScene();
	       	 Stage mainstage=(Stage) rootscene.getWindow();
	       	 addpic.initOwner(mainstage);
	     	 addpic.initModality(Modality.WINDOW_MODAL);
	       	 addpic.show();
	      
	        }catch(Exception e) {
	        	e.printStackTrace();
	        }
		}
		else if(item==close)
		{
			MainApp.close();
		}
	}
	public void getmanage(Event event) {
		if(event.getTarget()==all) {
				selectphoto.selectall();
				radiobutton.setSelected(true);
		}
		else if(event.getTarget()==noall)
		{
			selectphoto.undoselectall();
			radiobutton.setSelected(false);
		}
			
	}
	public void gethelp(Event event) {
		if(event.getTarget()==aboutus) {
			Alert alert=new Alert(Alert.AlertType.INFORMATION,"制作者：张园园"+"\n"+"学号：55170607"+"\n"+"联系电话：18709906729");
			alert.setTitle("关于我们");
			alert.show();
			
		}
		else if(event.getTarget()==usage&&!use) {
			ins=new Stage();
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("View/Instructions.fxml"));
	        try {
	        AnchorPane anchorPane=(AnchorPane)loader.load();
	       	Scene scene=new Scene(anchorPane);
	       	ins.setScene(scene);
	       	ins.setTitle("使用说明");
	       	Scene rootscene=Menutop.getScene();
	       	Stage mainstage=(Stage) rootscene.getWindow();
	    //   	ins.initOwner(mainstage);
     		use=true;
	       	ins.show();
	       	
	       	ins.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					// TODO Auto-generated method stub
					if(event.getEventType()==WindowEvent.WINDOW_CLOSE_REQUEST)
					{
						use=false;
					}
				}
				
			});
	       	 
	        }catch(Exception e) {
	        	e.printStackTrace();
	        } 
		}
		else if(event.getTarget()==usage&&use) {
			ins.setIconified(false);
		}
	}

}
