package application.Controller;

import static application.MainApp.clearconsole;
import static application.MainApp.corrent;
import static application.MainApp.in;
import static application.MainApp.readyarray;
import static application.MainApp.selected;
import static application.MainApp.selected2;
import static application.MainApp.setconsole;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
public class Encrytion extends Task<Void>{

	private Arnold arnold;
	private String enpath=null;
	private int times;
    public static HashMap<File,Integer> finished;
	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public Encrytion(int times) {
		this.times=times;
	}
	
	public String getEnpath() {
		return enpath;
	}
	
	public void setEnpath(String enpath) {
		this.enpath = enpath;
	}
	public void encryt(File inputfile,int times) {
		
			String filename;
			
			filename=inputfile.getName();
			System.out.println(filename);
			if(filename.toLowerCase().endsWith(".jpg")||filename.toLowerCase().endsWith(".bmp")||filename.toLowerCase().endsWith(".png")||filename.toLowerCase().endsWith(".jpeg"))
			{
				
				File outputfile;
				arnold=new Arnold(inputfile,true);
				arnold.setTime(times);
				if(enpath!=null)
				{
					arnold.setenpath(enpath);
				}
				if(arnold.initArnold())
				{
					arnold.block_Arnold();
				}
				outputfile=arnold.writeImage();
				
				for(int i=0;i<readyarray.getItems().size();i++)
				{
					ImageArray ia=(ImageArray) readyarray.getItems().get(i);
					System.out.println("here circle");
					if(ia.getFile()==inputfile)
					{
						ia.setCancel();
					}
				}
					
				setconsole("finished:原图："+inputfile.getAbsolutePath()+"    加密后图片："+outputfile.getAbsolutePath()+"   加密参数："+times+"\n");
			}
				
	}
	public void batch_encryt(int times) {
		if(selected!=null&&selected.size()>0) {
			clearconsole();
			setconsole("加密队列数："+selected.size()+"\n");
			for(int i=0;i<selected.size();i++)
			{
				setconsole(i+1+".");
				encryt(selected.get(i),times);
				File inputfile=selected.get(i);
				String filename;
				
				filename=inputfile.getName();
				
				if(filename.toLowerCase().endsWith(".jpg")||filename.toLowerCase().endsWith(".bmp")||filename.toLowerCase().endsWith(".png")||filename.toLowerCase().endsWith(".jpeg"))
				{
					
					File outputfile;
					arnold=new Arnold(inputfile,true);
					arnold.setTime(times);
					if(enpath!=null)
					{
						arnold.setenpath(enpath);
					}
					if(arnold.initArnold())
					{
						arnold.block_Arnold();
					}
					outputfile=arnold.writeImage();
					

						
					setconsole("finished:原图："+inputfile.getAbsolutePath()+"    加密后图片："+outputfile.getAbsolutePath()+"   加密参数："+times+"\n");
				
				
				}
				else
				{
					setconsole("!Error:原图："+inputfile.getAbsolutePath()+"\n");
					setconsole("!Error:该文件可能不是图像文件！\n");
				}
				Platform.runLater(new Runnable() {
				    @Override
				    public void run() {
				    	for(int i=1;i<readyarray.getItems().size();i++)
						{
							ImageArray ia=(ImageArray) readyarray.getItems().get(i);
							if(ia.getFile().getAbsolutePath().contentEquals(inputfile.getAbsolutePath()))
							{
								ia.setCancel();
							}
							
						}
				    }
				});
			}
		}
	}

	@Override
	protected Void call() {
		// TODO Auto-generated method stub
	try {
		if(selected!=null&&selected.size()>0) {
			clearconsole();
			setconsole("加密队列数："+selected.size()+"\n");
			finished=new HashMap<File,Integer>();
		
			for(int i=0;i<selected.size();i++)
			{
				setconsole(i+1+".");
				
				File inputfile=selected.get(i);
				corrent=inputfile;
				String filename;
				
				filename=inputfile.getName();
				System.out.println(filename);
				if(filename.toLowerCase().endsWith(".jpg")||filename.toLowerCase().endsWith(".bmp")||filename.toLowerCase().endsWith(".png")||filename.toLowerCase().endsWith(".jpeg"))
				{
					
					File outputfile;
					arnold=new Arnold(inputfile,true);
					arnold.setTime(times);
					if(enpath!=null)
					{
						arnold.setenpath(enpath);
					}
					if(arnold.initArnold())
					{
						arnold.block_Arnold();
					}
					outputfile=arnold.writeImage();
					

					finished.put(selected.get(i), 1);
					setconsole("finished:原图："+inputfile.getAbsolutePath()+"    加密后图片："+outputfile.getAbsolutePath()+"   加密参数："+times+"\n");
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	for(int i=0;i<readyarray.getItems().size();i++)
							{
								ImageArray ia=(ImageArray) readyarray.getItems().get(i);
								if(ia.getFile().getAbsolutePath().contentEquals(inputfile.getAbsolutePath()))
								{
									ia.setCancel();
								}
							//	System.out.println(ia.getFile().getAbsolutePath());
							}
					    }
					});
				
				}
				else
				{
					finished.put(selected.get(i), 2);
					setconsole("!Error:原图："+inputfile.getAbsolutePath()+"\n");
					setconsole("!Error:该文件可能不是图像文件！\n");
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	for(int i=0;i<readyarray.getItems().size();i++)
							{
								ImageArray ia=(ImageArray) readyarray.getItems().get(i);
								if(ia.getFile().getAbsolutePath().contentEquals(inputfile.getAbsolutePath()))
								{
									ia.setfail();
								}
						//		System.out.println(ia.getFile().getAbsolutePath());
							}
					    }
					});
				}
				corrent=null;
				
			}
			  updateMessage("Finish");  
              selected.clear();
              int succeed=0;
              int fail=0;
              int cancel=0;
              Iterator<Integer> it=finished.values().iterator();
              while(it.hasNext()) {
            	  int get=it.next();
            	  if(get==1) {succeed++;}
            	  else if(get==2) {fail++;}
            	  else if(get==3) {cancel++;}
              }
              setconsole("成功数："+succeed+"  失败数："+fail+"  取消数："+cancel);
              in=false;
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
