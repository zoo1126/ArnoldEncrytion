package application.Controller;

import static application.MainApp.ImageList;
import static application.MainApp.ImageList2;
import static application.MainApp.TOOL_HEIGHT;
import static application.MainApp.TOOL_WIDTH;
import static application.MainApp.selectphoto;
import static application.MainApp.tabselectende;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
public class AddpicController {
	@FXML
	private RadioButton encry;
	@FXML
	private RadioButton decry;
	@FXML
	private RadioButton all;
	@FXML
	private Button delete;

	@FXML
	private ListView list;
	@FXML
	private Button choose;
	@FXML
	private ImageView imageview;
	@FXML
	private Button confirm;
	@FXML
	private Button cancel;

	private Boolean ende=true;
	private Boolean seleall=false;
	private List<File> selectlist=new ArrayList<File>();
	private LinkedHashMap<File,HBox> addedlist=new LinkedHashMap<File,HBox>();
	private double gridwidth=TOOL_WIDTH*0.6;
	private double gridheight=TOOL_HEIGHT*0.5;
	private double padding=gridwidth*0.01;
	private double imagerate=gridwidth*0.25;
	
	public void select(Event event) {
		if(event.getTarget()==encry) {
			decry.setSelected(false);
			encry.setSelected(true);
			ende=true;
		}
		else if(event.getTarget()==decry) {
			decry.setSelected(true);
			encry.setSelected(false);
			ende=false;
		}
	}

	public void getpath(Event event) {
		if(event.getTarget()==choose)
		{
			
			FileChooser filechoose=new FileChooser();
			filechoose.getExtensionFilters().add(new ExtensionFilter("All Images","*.jpg","*.png","*.jpeg","*.bmp"));
			filechoose.getExtensionFilters().add(new ExtensionFilter("JPG","*.jpg"));
			filechoose.getExtensionFilters().add(new ExtensionFilter("PNG","*.png"));
			filechoose.getExtensionFilters().add(new ExtensionFilter("JPEG","*.jpeg"));
			filechoose.getExtensionFilters().add(new ExtensionFilter("BMP","*.bmp"));
			List<File> files=filechoose.showOpenMultipleDialog(null);
			if(files==null)
				return;
			for(int i=0;i<files.size();i++)
			{
				if(addedlist!=null&&!addedlist.keySet().contains(files.get(i))) 
				{
					
					HBox hbox=new HBox();
					CheckBox checkbox=new CheckBox("");
					File thisfile=files.get(i);
					checkbox.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							// TODO Auto-generated method stub
							if(selectlist!=null&&!selectlist.contains(thisfile))
								selectlist.add(thisfile);
						}
						
					});
					
					Label path=new Label(files.get(i).getAbsolutePath());
					Image image=new Image("file:"+files.get(i).getAbsolutePath());
					ImageView imageview=new ImageView(image);
					double width=image.getWidth();
					double height=image.getHeight();
					if(width>height) {
						imageview.setFitWidth(20);
						imageview.setFitHeight(20/width*height);
					}
					else
					{
						imageview.setFitHeight(20);
						imageview.setFitWidth(20/height*width);
					}
					hbox.getChildren().addAll(checkbox,imageview,path);
					
					list.getItems().add(hbox);
					addedlist.put(files.get(i),hbox);
					System.out.println(addedlist.size());
				}
			}	
		}
	}
	public void delete(Event event) {
		if(event.getTarget()==delete) {
			for(int i=0;i<selectlist.size();i++)
			{
				if(addedlist.containsKey(selectlist.get(i)))
				{
					list.getItems().remove(addedlist.get(selectlist.get(i)));
					addedlist.remove(selectlist.get(i), addedlist.get(selectlist.get(i)));
				}
			}
			selectlist.clear();
			
		}
	}
	public void selectall(Event event) {
		if(event.getTarget()==all&&!seleall) {
			if(addedlist!=null&&addedlist.size()!=0) {
				
					Iterator<HBox> it= addedlist.values().iterator();
					
					while(it.hasNext())
					{
						HBox hbox=it.next();
						CheckBox checkbox=(CheckBox) hbox.getChildren().get(0);
						checkbox.setSelected(true);
					}
				
				selectlist.addAll(addedlist.keySet());
			}
			seleall=true;
		}
		else if(event.getTarget()==all&&seleall) {
			if(addedlist!=null&&addedlist.size()!=0) {
				Iterator<HBox> it= addedlist.values().iterator();
				
				while(it.hasNext())
				{
					HBox hbox=it.next();
					CheckBox checkbox=(CheckBox) hbox.getChildren().get(0);
					checkbox.setSelected(false);
				}
				selectlist.removeAll(addedlist.keySet());
			}
			seleall=false;
		}
	}
	public void getconfirm(Event event) {
		if(event.getTarget()==confirm) {
		if(selectlist!=null&&selectlist.size()!=0)
		{	
			for(int i=0;i<selectlist.size();i++) {
				
				File file=selectlist.get(i);
				if(ende&&ImageList.containsKey(file))
					System.out.println("已存在");
				else if(!ende&&ImageList2.containsKey(file))
				{
					System.out.println("已存在");
				}
				else if(ende&&!ImageList.containsKey(file))
				{
					if(file.getName()!=null&&!file.getName().contentEquals("")&&(file.getName().toUpperCase().endsWith(".JPG")||file.getName().toUpperCase().endsWith(".JPEG")||file.getName().toUpperCase().endsWith(".PNG")||file.getName().toUpperCase().endsWith(".BMP")))
					{
						Image image=new Image("file:"+file.getAbsolutePath());
						//System.out.println(image.isError());
						//一行四个 
						ImageView imageView=new ImageView(image);
					 
						if(image.getHeight()>image.getWidth())
						{
							imageView.setFitHeight(imagerate*0.7/image.getHeight()*image.getHeight());
							imageView.setFitWidth(imagerate*0.7/image.getHeight()*image.getWidth());	
						}
						else
						{
							imageView.setFitHeight(imagerate*0.7/image.getWidth()*image.getHeight());
							imageView.setFitWidth(imagerate*0.7/image.getWidth()*image.getWidth());	
						}
						
						StackPane stackPane=selectphoto.createPhoPane(imageView,file);
						selectphoto.getenflowPane().getChildren().add(stackPane);
						selectphoto.getenflowPane().setMargin(stackPane, new Insets(10,10,10,10));
						ImageList.put(file,stackPane );
					}
				}
				else if(!ende&&!ImageList2.containsKey(file))
				{
					if(file.getName()!=null&&!file.getName().contentEquals("")&&(file.getName().toUpperCase().endsWith(".JPG")||file.getName().toUpperCase().endsWith(".JPEG")||file.getName().toUpperCase().endsWith(".PNG")||file.getName().toUpperCase().endsWith(".BMP")))
					{
						Image image=new Image("file:"+file.getAbsolutePath());
						//System.out.println(image.isError());
						//一行四个 
						ImageView imageView=new ImageView(image);
					 
						if(image.getHeight()>image.getWidth())
						{
							imageView.setFitHeight(imagerate*0.7/image.getHeight()*image.getHeight());
							imageView.setFitWidth(imagerate*0.7/image.getHeight()*image.getWidth());	
						}
						else
						{
							imageView.setFitHeight(imagerate*0.7/image.getWidth()*image.getHeight());
							imageView.setFitWidth(imagerate*0.7/image.getWidth()*image.getWidth());	
						}
						StackPane stackPane=selectphoto.createPhoPane(imageView,file);
						selectphoto.getdeflowPane().getChildren().add(stackPane);
					//	System.out.println(deflowPane.getChildren().size());
						selectphoto.getdeflowPane().setMargin(stackPane, new Insets(10,10,10,10));
						ImageList2.put(file,stackPane );
					}
				}
		}
		
		Scene scene=cancel.getScene();
		Stage stage=(Stage) scene.getWindow();
		stage.close();
		}
		
		else
		{
			Alert al=new Alert(Alert.AlertType.WARNING,"尚未选择图像文件！");
			Scene scene=cancel.getScene();
			Stage stage=(Stage) scene.getWindow();
			al.initOwner(stage);
			al.initModality(Modality.WINDOW_MODAL);
			al.show();
		}
		}
	}
	public void getcancel(Event event) {
		if(event.getTarget()==cancel) {
			Scene scene=cancel.getScene();
			Stage stage=(Stage) scene.getWindow();
			stage.close();
		}
	}

}
