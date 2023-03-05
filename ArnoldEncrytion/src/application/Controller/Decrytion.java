package application.Controller;
import static application.MainApp.clearconsole2;
import static application.MainApp.corrent2;
import static application.MainApp.in2;
import static application.MainApp.readyarray2;
import static application.MainApp.selected;
import static application.MainApp.selected2;
import static application.MainApp.setconsole;
import static application.MainApp.setconsole2;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.lang.Thread;

public class Decrytion extends Task<Void>{
private Arnold arnold;
//private static int num=0;
	private String depath=null;
	public static HashMap<File,Integer> finished2;
	
	public String getDepath() {
		return depath;
	}
	public void setDepath(String depath) {
		this.depath = depath;
	}
	public void decryt(File inputfile) {
		   
			
			String filename;
			
		
			filename=inputfile.getName();
			System.out.println(filename);
			if(filename.toLowerCase().endsWith(".jpg")||filename.toLowerCase().endsWith(".bmp")||filename.toLowerCase().endsWith(".png")||filename.toLowerCase().endsWith(".jpeg"))
			{
		
				arnold=new Arnold(inputfile,false);
				if(depath!=null)
				{
					arnold.setdepath(depath);
				}
				if(arnold.initantiArnold())
				{
					arnold.anti_block_Arnold();
					File outputfile=arnold.writeantiImage();
					setconsole2("finished:原图："+inputfile.getAbsolutePath()+"    解密后图片："+outputfile.getAbsolutePath()+"\n");
				}
				else {
					setconsole2("!Error:原图："+inputfile.getAbsolutePath()+"\n");
					setconsole2("!Error:该图像可能未被加密！\n");
				}
				
				//Iterator it=readyarray.getChildren().iterator();
			/*	Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	for(int i=1;i<readyarray.getChildren().size();i++)
						{
							ImageArray ia=(ImageArray) readyarray.getChildren().get(i);
							if(ia.getFile().getAbsolutePath().contentEquals(inputfile.getAbsolutePath()))
							{
								ia.setCancel();
							}
							System.out.println(ia.getFile().getAbsolutePath());
						}
				    }
				});*/
				
			}
		
	}
	public void batch_decryt() {
		if(selected2!=null&&selected2.size()>0) {
			 clearconsole2();
			setconsole2("解密队列数："+selected2.size()+"\n");
			
			for(int i=0;i<selected2.size();i++)
			{
				setconsole2(i+1+".");
				decryt(selected2.get(i));
				
			}
			
		}
	}
	@Override
	protected Void call(){
		// TODO Auto-generated method stub
	try {

		if(selected2!=null&&selected2.size()>0) {
			clearconsole2();
			setconsole2("解密队列数："+selected2.size()+"\n");
			finished2=new HashMap<File,Integer>();
			for(int i=0;i<selected2.size();i++)
			{
				setconsole2(i+1+".");
			//	decryt(selected2.get(i));
				String filename;
				File inputfile=selected2.get(i);
				corrent2=inputfile;
				filename=inputfile.getName();
				
				if(filename.toLowerCase().endsWith(".jpg")||filename.toLowerCase().endsWith(".bmp")||filename.toLowerCase().endsWith(".png")||filename.toLowerCase().endsWith(".jpeg"))
				{
			
					arnold=new Arnold(inputfile,false);
					if(depath!=null)
					{
						arnold.setdepath(depath);
					}
					if(arnold.initantiArnold())
					{
						arnold.anti_block_Arnold();
						File outputfile=arnold.writeantiImage();
						finished2.put(selected2.get(i), 1);
						setconsole2("finished:原图："+inputfile.getAbsolutePath()+"    解密后图片："+outputfile.getAbsolutePath()+"\n");
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	
						    	for(int i=0;i<readyarray2.getItems().size();i++)
								{
									ImageArray ia=(ImageArray) readyarray2.getItems().get(i);
									if(ia.getFile().getAbsolutePath().contentEquals(inputfile.getAbsolutePath()))
									{
										ia.setCancel();
									}
								}
						    }
						});
					}
					else {
						
						finished2.put(selected2.get(i), 2);
						System.out.println("here");
						setconsole2("!Error:原图："+inputfile.getAbsolutePath()+"    "+arnold.getErrormessgae()+"\n");
					
						Platform.runLater(new Runnable() {
						    @Override
						    public void run() {
						    	
						    	for(int i=0;i<readyarray2.getItems().size();i++)
								{
									ImageArray ia=(ImageArray) readyarray2.getItems().get(i);
									if(ia.getFile().getAbsolutePath().contentEquals(inputfile.getAbsolutePath()))
									{
										ia.setfail();
									}
								}
						    }
						});
					}
					corrent2=null;
					//Iterator it=readyarray.getChildren().iterator();
					
					
				}
			}
			updateMessage("Finish"); 
            selected2.clear();
            int succeed=0;
            int fail=0;
            int cancel=0;
            Iterator<Integer> it=finished2.values().iterator();
            while(it.hasNext()) {
          	  int get=it.next();
          	  if(get==1) {succeed++;}
          	  else if(get==2) {fail++;}
          	  else if(get==3) {cancel++;}
            }
            setconsole2("成功数："+succeed+"  失败数："+fail+"  取消数："+cancel);
            in2=false;
		}
		return null;
	}catch(Exception e)
	{
		Alert a=new Alert(Alert.AlertType.ERROR,e.getMessage()+"  请稍后再试！");
		System.out.println(Thread.currentThread().isInterrupted());
		e.printStackTrace();
		a.show();
		return null;
	}
	}
	
	
	
}

