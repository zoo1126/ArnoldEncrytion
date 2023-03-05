package application.Controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import static application.Controller.Decrytion.finished2;
import static application.Controller.Encrytion.finished;
import static application.MainApp.corrent;
import static application.MainApp.selected;
import static application.MainApp.selected2;
public class ImageArray  extends FlowPane{
	
	private File file;
	private ImageView imageView;
	private Image image;
	private Label name;
	private Label ende;
	private Label savepath;
	private Label times;


	private Button cancel=new Button("取消");
	
	public Button getCancel() {
		return cancel;
	}
	public void setCancel() {
		int i=this.getChildren().indexOf(cancel);
		this.getChildren().remove(cancel);
		this.getChildren().add(0, new Label("完成"));
	}
	public void cancel() {
		int i=this.getChildren().indexOf(cancel);
		this.getChildren().remove(cancel);
		if(ende.getText().contentEquals("加密"))
			finished.put(file, 3);
		else
			finished2.put(file, 3);
		this.getChildren().add(0, new Label("已取消"));
	}
	public void setfail() {
		int i=this.getChildren().indexOf(cancel);
		this.getChildren().remove(cancel);
		this.getChildren().add(0, new Label("错误"));
	}
	public ImageArray(File file,Boolean ende,String savepath,int times)
	{
		
		this.file=file;
		if(ende) 
			{
			this.ende=new Label("加密");
			this.times=new Label("变换次数:"+String.valueOf(times));
			}
		else 
			{
			this.ende=new Label("解密");
			this.times=new Label("自解密");
			}
		image=new Image("file:"+file.getAbsolutePath());
		imageView=new ImageView(image);
		double width=image.getWidth();
		double height=image.getHeight();
		if(width>height)
		{
			imageView.setFitHeight(30/width*height);
			imageView.setFitWidth(30);
		}
		else
		{
			imageView.setFitHeight(30);
			imageView.setFitWidth(30/height*width);
		}
		name=new Label(file.getName());
		this.savepath=new Label("存储路径:"+file.getParentFile().getAbsolutePath());
		
		
		this.getChildren().addAll(cancel,this.ende,this.imageView,this.name,this.times,this.savepath);
		this.setMargin(this.ende,  new Insets(5,5,5,5));
		this.setMargin(imageView, new Insets(5,5,5,5));
		this.setMargin(name,  new Insets(5,5,5,5));
		this.setMargin(this.times,  new Insets(5,5,5,5));
		this.setMargin(cancel,  new Insets(5,5,5,5));
		this.setStyle("-fx-border-radius:5;"+"-fx-border-color:#778899;"+"-fx-border-style:solid inside;");
		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(corrent!=file&&(ende&&!finished.containsKey(file)||!ende&&!finished2.containsKey(file)))
				{	
					if(ende)
					{
						selected.remove(file);
						
					}
					else
					{
						selected2.remove(file);
						
					}
					
					cancel();
					
				}
				else
				{
					Alert alert=new Alert(Alert.AlertType.INFORMATION,"该图像正在处理中！");
					alert.show(); 
					
				
				}
			}
			
		});
	//	this.getChildren().addAll(this.ende,)
	}
	public FlowPane getimagearray()
	{
		return this;
	}
	public File getFile() {
		return file;
	}
}
