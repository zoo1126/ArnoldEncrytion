package application.Controller;

import static application.MainApp.ImageList;
import static application.MainApp.ImageList2;
import static application.MainApp.TOOL_HEIGHT;
import static application.MainApp.TOOL_WIDTH;
import static application.MainApp.selected;
import static application.MainApp.selected2;
import static application.MainApp.selectphoto;
import static application.MainApp.tabselectende;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.IconView;

import application.Controller.MenutopController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class selectPhoto {

	
	private FlowPane enflowPane=new FlowPane();
	private FlowPane deflowPane=new FlowPane();
	private int colnum,rownum;
	private double collength;
	private double gridwidth=TOOL_WIDTH*0.6;
	private double gridheight=TOOL_HEIGHT*0.5;
	private double padding=gridwidth*0.01;
	private double imagerate=gridwidth*0.25;
	private double margin=padding;
	private static File seeingfile;
	public Boolean enall=false;
	public Boolean deall=false;
	
	public selectPhoto() {


		collength=imagerate;
	
		enflowPane.setPrefWidth(gridwidth);
		enflowPane.setHgap(gridwidth*0.01);
		enflowPane.setVgap(gridheight*0.01);
		enflowPane.setPadding(new Insets(10,10,10,10));
		enflowPane.setOrientation(Orientation.HORIZONTAL);
		deflowPane.setPrefWidth(gridwidth);
		deflowPane.setHgap(gridwidth*0.01);
		deflowPane.setVgap(gridheight*0.01);
		deflowPane.setPadding(new Insets(10,10,10,10));
		deflowPane.setOrientation(Orientation.HORIZONTAL);
	}
	

	public FlowPane getflowPane() {
		if(tabselectende)
			return enflowPane;	
		else
			return deflowPane;
	}
	public FlowPane getenflowPane() {
	
			return enflowPane;	
	} 
	public FlowPane getdeflowPane() {
	
			return deflowPane;
	}
	
	public void selectpho(File file)
	{
		if(tabselectende&&ImageList.containsKey(file))
			System.out.println("已存在");
		else if(!tabselectende&&ImageList2.containsKey(file))
		{
			System.out.println("已存在");
		}
		else if(tabselectende&&!ImageList.containsKey(file))
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
				StackPane stackPane=createPhoPane(imageView,file);
				enflowPane.getChildren().add(stackPane);
				enflowPane.setMargin(stackPane, new Insets(10,10,10,10));
				ImageList.put(file,stackPane );
			}
		}
		else if(!tabselectende&&!ImageList2.containsKey(file))
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
				StackPane stackPane=createPhoPane(imageView,file);
				deflowPane.getChildren().add(stackPane);
				System.out.println(deflowPane.getChildren().size());
				deflowPane.setMargin(stackPane, new Insets(10,10,10,10));
				ImageList2.put(file,stackPane );
			}
		}
	//如果不是图片格式就不做任何操作
	}
	
	public void secondStage() {
		Stage secondStage = new Stage();
		secondStage.setTitle(seeingfile.getName());
        BorderPane secondPane = new BorderPane();
        double size,rate;
        if(TOOL_HEIGHT>TOOL_WIDTH)
        {
        	size=TOOL_WIDTH;
        }
        else
        {
        	size=TOOL_HEIGHT;
        }
        
        
		Image showimage=new Image("file:"+seeingfile.getAbsolutePath());
		ImageView showimageview=new ImageView(showimage);
		if(showimage.getHeight()>showimage.getWidth())
		{
			showimageview.setFitHeight(size);
			showimageview.setFitWidth(size/showimage.getHeight()*showimage.getWidth());
			
		}
		else
		{
			showimageview.setFitHeight(size/showimage.getWidth()*showimage.getHeight());
			showimageview.setFitWidth(size);
		}
		
		
		secondPane.setCenter(showimageview);
		secondPane.setPrefSize(size+100, size);
		//加入工具栏进行图片处理
		Button lbt=new Button();
		Button rbt=new Button();
		
		
		Image lefticon=new Image("images/left.jpeg");
		ImageView left=new ImageView(lefticon);
		left.setFitHeight(50);
		left.setFitWidth(50/lefticon.getHeight()*lefticon.getWidth());
		lbt.setGraphic(left);
		left.setVisible(false);
		lbt.setStyle("-fx-border-color:transparent;"+"-fx-border-width:0;"+"-fx-background-color:transparent;"+"-fx-background-radius:0;");
		lbt.setCursor(Cursor.HAND);
		
		lbt.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				left.setVisible(true);
			}
			
		
		});
		lbt.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				left.setVisible(false);
			}
		});
		lbt.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(event.getClickCount()==1)
				{
					Iterator<Entry<File,StackPane>> iterator=null;
					if(tabselectende)
					{
						iterator=ImageList.entrySet().iterator();
					}
					else
					{
						iterator=ImageList2.entrySet().iterator();
					}
					
					File beforefile=null;
					
					while(iterator.hasNext())
					{
						File getfile=iterator.next().getKey();
						if(getfile==seeingfile&&beforefile!=null)
						{
							
							System.out.println(getfile.getName());
							
							secondStage.setTitle(beforefile.getName());
							Image showimage=new Image("file:"+beforefile.getAbsolutePath());
							ImageView showimageview=new ImageView(showimage);
							 double size;
						        if(TOOL_HEIGHT>TOOL_WIDTH)
						        	size=TOOL_WIDTH;
						        else
						        	size=TOOL_HEIGHT;
						    
							if(showimage.getHeight()>showimage.getWidth())
							{
								showimageview.setFitHeight(size);
								showimageview.setFitWidth(size/showimage.getHeight()*showimage.getWidth());	
							}
							else
							{
								showimageview.setFitHeight(size/showimage.getWidth()*showimage.getHeight());
								showimageview.setFitWidth(size);	
							}
							
							
							secondPane.setCenter(showimageview);
							seeingfile=beforefile;
							break;
						}
						
						beforefile=getfile;
					}
				}
			}
		
		});
		secondPane.setLeft(lbt);
		secondPane.setAlignment(lbt, Pos.CENTER);

		Image righticon=new Image("images/right.jpeg");
		ImageView right=new ImageView(righticon);
		right.setFitHeight(50);
		right.setFitWidth(50/righticon.getHeight()*righticon.getWidth());
		rbt.setGraphic(right);
		right.setVisible(false);
		rbt.setStyle("-fx-border-color:transparent;"+"-fx-border-width:0;"+"-fx-background-color:transparent;"+"-fx-background-radius:0;");
		rbt.setCursor(Cursor.HAND);
		
		rbt.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				right.setVisible(true);
			}
			
		});
		rbt.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				right.setVisible(false);
			}
			
		
		});
		rbt.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(event.getClickCount()==1)
				{
					Iterator<Entry<File,StackPane>> iterator=null;
					if(tabselectende)
					{
						iterator=ImageList.entrySet().iterator();
					}
					else
					{
						iterator=ImageList2.entrySet().iterator();
					}
					
					
					File afterfile=null;
					
					while(iterator.hasNext())
					{
						
						File getfile=iterator.next().getKey();
						if(getfile==seeingfile&&iterator.hasNext())
						{
							afterfile=iterator.next().getKey();
							System.out.println(getfile.getName());
							
							secondStage.setTitle(afterfile.getName());
							Image showimage=new Image("file:"+afterfile.getAbsolutePath());
							ImageView showimageview=new ImageView(showimage);
							 double size;
						        if(TOOL_HEIGHT>TOOL_WIDTH)
						        	size=TOOL_WIDTH;
						        else
						        	size=TOOL_HEIGHT;
						    
							if(showimage.getHeight()>showimage.getWidth())
							{
								showimageview.setFitHeight(size);
								showimageview.setFitWidth(size/showimage.getHeight()*showimage.getWidth());	
							}
							else
							{
								showimageview.setFitHeight(size/showimage.getWidth()*showimage.getHeight());
								showimageview.setFitWidth(size);	
							}
							
							
							secondPane.setCenter(showimageview);
							seeingfile=afterfile;
							break;
						}
					
					}
				}
			}
		
		});
		secondPane.setRight(rbt);
		secondPane.setAlignment(rbt, Pos.CENTER);
		
       
        Scene secondScene = new Scene(secondPane);
        secondStage.setScene(secondScene);
        secondStage.show();
	}
	public StackPane createPhoPane(ImageView imageView,File file) {
		StackPane imaPane=new StackPane();
		BorderStroke borderstroke=new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(0.5, 0.5, 0.5, 0.5));
		imaPane.setBorder(new Border(borderstroke));
		imaPane.setPrefSize(collength, collength);
		//imaPane.setPadding(new Insets(0,5,0,5));
		imaPane.getChildren().add(0,imageView);
		imaPane.setAlignment(imageView, Pos.CENTER);
		RadioButton radio=new RadioButton("选择");
		radio.setId("selectbutton");
		
		Button close=new Button();//放大照片的icon
		Image bigericon=new Image("images/biger.jpeg");
		ImageView iconview=new ImageView(bigericon);
		iconview.setFitHeight(gridwidth*0.03);
		iconview.setFitWidth(gridwidth*0.03);
		iconview.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(event.getClickCount()==1)
				{
					seeingfile=file;
					if(seeingfile!=null)
						secondStage();
					
				}
			}
			
		});
		Label label=new Label(file.getName());
		radio.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				ArrayList<File> selectlist;
				if(tabselectende) {
					selectlist=selected;
					if(enall&&!newValue) {
						enall=false;
						
						
					}
					
				}
				else
					{
					selectlist=selected2;
					if(deall&&!newValue) {
						deall=false;
					
					}
					}
				// TODO Auto-generated method stub
				if(newValue&&!oldValue)
				{
					selectlist.add(file);
					imaPane.setStyle("-fx-border-radius:5;"+"-fx-border-color:blue;"+"-fx-border-style:solid inside;");
				}
				else if(!newValue&&oldValue)
				{
					if(selectlist.contains(file)) {
						selectlist.remove(file);
						imaPane.setStyle("");
					}
				}
			}
			
		});
		if(tabselectende&&selectphoto.enall)
			radio.setSelected(true);
		else if(tabselectende&&!selectphoto.enall)
			radio.setSelected(false);
		else if(!tabselectende&&selectphoto.deall)
			radio.setSelected(true);
		else if(!tabselectende&&!selectphoto.deall)
			radio.setSelected(false);
		imaPane.getChildren().add(1,radio);
		imaPane.getChildren().add(2,iconview);
		imaPane.getChildren().add(3,label);
		imaPane.setAlignment(radio, Pos.TOP_LEFT);
		imaPane.setAlignment(iconview, Pos.TOP_RIGHT);
		imaPane.setAlignment(label, Pos.BOTTOM_CENTER);
		System.out.println(tabselectende);
		return imaPane;
	}
	
	
	public void freshflow() {
		ArrayList<File> selectlist;
		FlowPane selectPane;
		LinkedHashMap<File,StackPane> selectimagelist;
		if(tabselectende) {
			selectlist=selected;
			selectPane=enflowPane;
			selectimagelist=ImageList;
			
		}
		else {
			selectlist=selected2;
			selectPane=deflowPane;
			selectimagelist=ImageList2;
		}

		if(selectlist!=null&&selectlist.size()!=0)
		{
			
			for(int i=selectlist.size()-1;i>=0;i--)
			{
				selectPane.getChildren().remove(selectimagelist.get(selectlist.get(i)));
				selectimagelist.remove(selectlist.get(i), selectimagelist.get(selectlist.get(i)));
				selectlist.remove(i);
			
			}
		}
	}
	public void clearflow() {
		ArrayList<File> selectlist;
		FlowPane selectPane;
		LinkedHashMap<File,StackPane> selectimagelist;
		if(tabselectende) {
			selectlist=selected;
			selectPane=enflowPane;
			selectimagelist=ImageList;
		}
		else {
			selectlist=selected2;
			selectPane=deflowPane;
			selectimagelist=ImageList2;
		}
		if(selectlist!=null&&selectlist.size()!=0)
		{
			
			for(int i=0;i<selectlist.size();i++)
			{
				selectPane.getChildren().remove(selectimagelist.get(selectlist.get(i)));
				
				selectimagelist.remove(selectlist.get(i), selectimagelist.get(selectlist.get(i)));

			
			}
		}
	}

	public void selectall() {//选择所有图片
		if(tabselectende)
		{
			for(int i=0;i<enflowPane.getChildren().size();i++)
			{
				StackPane sp=(StackPane)enflowPane.getChildren().get(i);
				RadioButton rb=(RadioButton)sp.getChildren().get(1);
				rb.setSelected(true);
			}
			enall=true;
		}
		else {
			for(int i=0;i<deflowPane.getChildren().size();i++)
			{
				StackPane sp=(StackPane)deflowPane.getChildren().get(i);
				RadioButton rb=(RadioButton)sp.getChildren().get(1);
				rb.setSelected(true);
			}
			deall=true;
		}
	}
	
	public void undoselectall() {//取消选择所有图片
		if(tabselectende)
		{
			for(int i=0;i<enflowPane.getChildren().size();i++)
			{
				StackPane sp=(StackPane)enflowPane.getChildren().get(i);
				RadioButton rb=(RadioButton)sp.getChildren().get(1);
				rb.setSelected(false);
			}
			enall=false;
		}
		else {
			for(int i=0;i<deflowPane.getChildren().size();i++)
			{
				StackPane sp=(StackPane)deflowPane.getChildren().get(i);
				RadioButton rb=(RadioButton)sp.getChildren().get(1);
				rb.setSelected(false);
			}
			deall=false;
		}
	}
}
