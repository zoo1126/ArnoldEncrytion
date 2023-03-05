package application.Controller;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
 

public class Arnold {
	
	
		private File srcImageFile;//源文件
		private BufferedImage srcImage, desImage;
		//srcImage是原图像的缓冲图像，desImage是目标图像的缓冲图像
		private int[][] srcMatrix,desMatrix;
		//srcMatrix表示原图像矩阵，desMatrix表示目标图像矩阵
		private int[][] blocksrcMatrix , blockdesMatrix;
		//blocksrcMatrix表示从原图像矩阵中取出的分块矩阵，blockdesMatrix表示变换后的分块矩阵
		private int N;// 分块的阶数
		private int time;// 加密变换周期
		private int antitime;//解密变换周期
		private int blocknum=1;//默认分块为1块，但是具体分块数量由系统得到图像后分析确定
		private int blockleast;//进行分块处理后，最后两块重叠部分的长度
		private int filetype=0;//原图像文件的后缀格式，1.jpg 2.jpeg 3.bmp 4.png
		private String endesName,dedesName;//加密后目标文件的名字和解密后目标文件的名字
		private String enpath,depath;//加密后目标文件的保存路径和解密后目标文件的保存路径
		private String errormessgae="该图像可能未被加密！";
	
		
		public String getErrormessgae() {
			return errormessgae;
		}
	
		/**
		 * 
		 * @param srcImageFile源文件
		 * @param desImageFile目标文件
		 * @param time周期
		 */
		public Arnold(File srcImageFile,Boolean ende) {//构造函数 
			this.srcImageFile = srcImageFile;
			String filepath=srcImageFile.getAbsolutePath().toLowerCase();
			if(filepath.endsWith(".jpg"))
				filetype=1;
			else if(filepath.endsWith(".jpeg"))
				filetype=2;
			else if(filepath.endsWith(".bmp"))
				filetype=3;
			else if(filepath.endsWith(".png"))
				filetype=4;
			else
				filetype=0;
			if(ende)
			{
				endesName=srcImageFile.getName().substring(0, srcImageFile.getName().indexOf("."))+"_encry";
				enpath=srcImageFile.getParentFile().getAbsolutePath();
				
				
			}
			else
			{
				depath=srcImageFile.getParentFile().getAbsolutePath();
				dedesName=srcImageFile.getName();
				if(dedesName.contains("_encry"))
				{
					
					dedesName=dedesName.substring(0, dedesName.indexOf("_encry"));
					
				}
				else
				{
					dedesName.substring(0, dedesName.indexOf("."));
				}
				dedesName=dedesName+"_decry";
				
	
			}
			
			
		}
		public void setTime(int time){//设置变换次数
			this.time=time;
		}
		public BufferedImage readImage(File imageFile) {//读取图像文件
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageFile);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return image;
		}
		public int[][] getMatrixRGB(BufferedImage image) {//获取图像RGB矩阵
			/**
			 * @param image 图像缓冲
			 * @return imageMatrix 像素矩阵
			 */
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage original=new BufferedImage(width,height,image.getType());
			original.getGraphics().drawImage(image, 0, 0, null);
			int[][] imageMatrix;
			imageMatrix= new int[width][height];//读取图片时图片可以不是正方形，只是为了创建一个图片矩阵
			for(int i=0;i<width;i++)
				for(int j=0;j<height;j++)
				{
					
					imageMatrix[i][j]=original.getRGB(i, j);
				}
	
			return imageMatrix;
		}
		public File writeImage() {//输出加密图像
			try {
				String desImageName;
				File file;
				int i=0;
				desImageName=enpath+"\\"+endesName;
				File desfile=new File(desImageName+".png");
				while(desfile.exists())
				{
					i++;
					desfile=new File(desImageName+"("+i+").png");
				}
				if(i!=0)
					file=new File(enpath+"\\"+endesName+"("+i+")"+".png");
				else
					file=new File(enpath+"\\"+endesName+".png");
				ImageIO.write(desImage, "png", file);
				return file;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		public File writeantiImage() {//输出解密图像
			try {
				String type="png";
				if(filetype==1)
					type="jpg";
				else if(filetype==2)
					type="jpeg";
				else if(filetype==3)
					type="bmp";
				else if(filetype==4)
					type="png";
				String desImageName;
				int i=0;
				desImageName=depath+"\\"+dedesName;
				
				File desfile=new File(desImageName+"."+type);
				while(desfile.exists())
				{
					i++;
					desfile=new File(desImageName+"("+i+")."+type);
				}
				
				File imageFile;
				if(i!=0)
					imageFile=new File(depath+"\\"+dedesName+"("+i+")."+type);
				else
					imageFile=new File(depath+"\\"+dedesName+"."+type);
				ImageIO.write(desImage, type, imageFile);
				return imageFile;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		public boolean initArnold() {//加密初始化
			/**
			 * Arnold变换初始化
			* 
			* @param image
			* @return boolean 判断是否是正方形图片
			*/
			try {
				srcImage=readImage(srcImageFile);
				int width = srcImage.getWidth();
				int height = srcImage.getHeight();
				if(width<height)	N=width;
				else				N=height;//小的值是阶数
				System.out.println("阶数="+N);
				srcMatrix = getMatrixRGB(srcImage);//获取图片矩阵
				
				desMatrix = new int[width][height];
				if(width<height)	
					desImage = new BufferedImage(width, height+1, srcImage.getType());	
				else				
					desImage = new BufferedImage(width+1, height, srcImage.getType());
				System.out.println(srcImage.getWidth()+"   "+srcImage.getHeight());
						
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}		
		public boolean initantiArnold() {//解密初始化
			try {
				srcImage=readImage(srcImageFile);
				int width = srcImage.getWidth();
				int height = srcImage.getHeight();
				int ori_width,ori_height;
				if(width<height)	
				{
					N=width;
					ori_width=width;
					ori_height=height-1;
					Color c=new Color(srcImage.getRGB(ori_height%ori_width, height-1));
					antitime=c.getBlue();
					if(antitime>255||antitime<1)
					{
						errormessgae="该图像可能未加密或原图像加密次数越界！图像可能被篡改！";
						return false;
					}
					filetype=c.getRed();
					if(filetype!=1&&filetype!=2&&filetype!=3&&filetype!=4)
					{
						errormessgae="该图像可能未加密或原图像格式不正确！图像可能被篡改！";
						return false;
					}
					int note=c.getGreen();
					if(note!=(ori_height%ori_width)%255)
					{
						errormessgae="该图像可能未加密或标志位验证错误！图像可能被篡改！";
						return false;
					}
				}
				else if(width>height)	
				{
					N=height;//小的值是阶数
					ori_width=width-1;
					ori_height=height;
					Color c=new Color(srcImage.getRGB(width-1, ori_width%ori_height));
					antitime=c.getBlue();
					if(antitime>255||antitime<1)
					{
						errormessgae="原图像加密次数越界！图像可能被篡改！";
						return false;
					}
					filetype=c.getRed();
					if(filetype!=1&&filetype!=2&&filetype!=3&&filetype!=4)
					{
						errormessgae="原图像格式不正确！图像可能被篡改！";
						return false;
					}
					int note=c.getGreen();
					if(note!=(ori_width%ori_height)%255)
					{
						errormessgae="标志位验证错误！图像可能被篡改！";
						return false;
					}
				}
				else
				{
					return false;
				}
				System.out.println("阶数="+N+"次数="+antitime);
				srcImage=srcImage.getSubimage(0, 0, ori_width, ori_height);
				srcMatrix = getMatrixRGB(srcImage);//获取图片矩阵
				
				desMatrix = new int[ori_width][ori_height];
				desImage = new BufferedImage(ori_width, ori_height, srcImage.getType());
				System.out.println(desImage.getWidth()+"   "+desImage.getHeight());
						
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		public void arnoldTransform() {//Arnold变换
			/**
			* 
			* @param blocksrcMatrix在外部就已经赋值
			* @param blockdesMatrix在本函数内部新创并保存在该类中，以便变换后赋值给总的图片矩阵
			* 
			* 仅进行矩阵变换，不负责图像存储
			*/
				blockdesMatrix=new int[N][N];//创建分块变换后的矩阵
				for (int n = 0; n < time; n++) {
					if (n != 0) {
						blocksrcMatrix = blockdesMatrix;
						blockdesMatrix=new int[N][N];
					}
					for (int i = 0; i < N; i++) {
						for (int j = 0; j < N; j++) {
							blockdesMatrix[(i + j) % N][(i + 2 * j) % N] = blocksrcMatrix[i][j];
						}
					}
				}
		//此时blockdesMatrix里面就是分块变换后的矩阵
		}
		public void antiarnold() {//Arnold逆变换
			/**
			 * arnold逆变换
			 * @param srcImageFile
			 * 
			 */
				blockdesMatrix=new int[N][N];
				for (int n = 0; n < antitime; n++) {
					if (n != 0) {
						blocksrcMatrix = blockdesMatrix;
						blockdesMatrix=new int[N][N];
					}
					for (int i = 0; i < N; i++) {
						for (int j = 0; j < N; j++) {
							blockdesMatrix[((i+ i - j)%N+N)%N][((j-i) % N+N)%N] = blocksrcMatrix[i][j];  
						}
					}
				}
				//此时blockdesMatrix里面就是分块变换后的矩阵
		}
		public void block_Arnold() {//分块的Arnold变换
			/**分块Arnold
			 * 不包括处理图片
			 * arnold变换本体在arnoldTransform里
			 * 该函数只是分块处理调用arnold
			 */
			if(srcImage.getWidth()==srcImage.getHeight()) {//长宽相等，正方形图片直接变换
				blocksrcMatrix=srcMatrix;//正方形图片只有一个分块，就是自身
				arnoldTransform();
				desMatrix=blockdesMatrix;//唯一的分块矩阵就是最终的矩阵
			}
			else {//长高不等
				desMatrix=srcMatrix;
				int more,less;//more是长的一边，less是短的一边
				boolean wh=false;
				if(srcImage.getWidth()>srcImage.getHeight())//长大于高
				{
					more=srcImage.getWidth();
					less=srcImage.getHeight();
					wh=true;
				}
				else//高大于长
				{
					more=srcImage.getHeight();
					less=srcImage.getWidth();
				}
				
				
				blockleast=more%less;//查看长宽比例是否是整数，如果比例是整数 如4/2=2没有余数，那么分块刚好2块，如果是5/2=2****1那么块数应当+1
				if(blockleast==0)   blocknum=more/less;
				else                blocknum=more/less+1;//正常加密块从左边开始，依次加密，最后的一块从右边计算，因此与右边第二块重叠的部分长度应该是blockleast的长度
				
				for(int i=0;i<blocknum-1;i++)//除了最后一块以外分块加密
				{
					blocksrcMatrix=new int[N][N];
					if(wh)//长大于高
					{
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								blocksrcMatrix[x][y]=desMatrix[i*N+x][y];
							}
						//取出除最后一块以外的分块矩阵
						arnoldTransform();
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								desMatrix[i*N+x][y]=blockdesMatrix[x][y];
							}
						//将变换完的矩阵放回desMatrix中
					}
					else
					{
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								blocksrcMatrix[x][y]=desMatrix[x][i*N+y];
							}
						//取出除最后一块以外的分块矩阵
						arnoldTransform();
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								desMatrix[x][i*N+y]=blockdesMatrix[x][y];
							}
						//将变换完的矩阵放回desMatrix中
					}
				}
		
			//加密最后一块
				if(wh)//长大于高
				{
					blocksrcMatrix=new int[N][N];
					for(int x=0;x<N;x++)
					{
						for(int y=0;y<N;y++)
						{
							blocksrcMatrix[x][y]=desMatrix[srcImage.getWidth()-N+x][y];
						}
					}
					
					//取出除最后一块以外的分块矩阵
					arnoldTransform();
					for(int x=0;x<N;x++)
						for(int y=0;y<N;y++)
						{
							desMatrix[srcImage.getWidth()-N+x][y]=blockdesMatrix[x][y];
						}
					//将变换完的矩阵放回desMatrix中
				}
				else
				{
					blocksrcMatrix=new int[N][N];
					for(int x=0;x<N;x++)
						for(int y=0;y<N;y++)
						{
							blocksrcMatrix[x][y]=desMatrix[x][srcImage.getHeight()-N+y];
						}
					//取出除最后一块以外的分块矩阵
					arnoldTransform();
					for(int x=0;x<N;x++)
						for(int y=0;y<N;y++)
						{
							desMatrix[x][srcImage.getHeight()-N+y]=blockdesMatrix[x][y];
						}
					//将变换完的矩阵放回desMatrix中
				}
				
			}
			
			for(int i=0;i<srcImage.getWidth();i++)
			{
				for(int j=0;j<srcImage.getHeight();j++)
				{	desImage.setRGB(i, j, desMatrix[i][j]);}
			}
			int index;
			if(srcImage.getHeight()>srcImage.getWidth())
			{
				index=srcImage.getHeight()%srcImage.getWidth();
				int note=index%255;
				
				Color c=new Color(filetype,note,time);
				for(int i=0;i<srcImage.getWidth();i++)
				{
					if(i!=index)
						desImage.setRGB(i, srcImage.getHeight(), desImage.getRGB(i, i));
					else
						desImage.setRGB(i, srcImage.getHeight(), c.getRGB());
				}
				Boolean no=true;
				for(int i=0;i<srcImage.getWidth();i++)
				{
					
						if(i!=index&&desImage.getRGB(i,srcImage.getHeight()) !=desImage.getRGB(i, i)) {
							no=false;
							break;
						}
					
					
				}
				System.out.println("最后一列除了标志位是否与对角线像素值相同："+no+"  标志位置为：("+index+","+srcImage.getHeight()+")");
				int pixel=desImage.getRGB(index,srcImage.getHeight());
				System.out.println("1111111111111111");
				int r=(pixel&0xff0000)>>16;
				int g=(pixel&0xff00)>>8;
				int b=(pixel&0xff);
				String type="png";
				if(r==1)
					type="jpg";
				else if(r==2)
					type="jpeg";
				else if(r==3)
					type="bmp";
				else if(r==4)
					type="png";
				System.out.println("图像格式："+type+"  标志值："+g+"  变换次数:"+b);
			}
			else
			{
				index=srcImage.getWidth()%srcImage.getHeight();
				int note=index%255;
				Color c=new Color(filetype,note,time);
				for(int i=0;i<srcImage.getHeight();i++)
				{
					if(i!=index)
					{
						
						desImage.setRGB(srcImage.getWidth(),i,  desImage.getRGB(i, i));
					}
					else
						desImage.setRGB( srcImage.getWidth(),i, c.getRGB());
					
				}
				Boolean no=true;
				for(int i=0;i<srcImage.getHeight();i++)
				{
					
						if(i!=index&&desImage.getRGB(srcImage.getWidth(),i) !=desImage.getRGB(i, i)) {
							no=false;
							break;
						}
					
					
				}
				System.out.println("最后一列除了标志位是否与对角线像素值相同："+no+"    标志位置为：("+srcImage.getWidth()+","+index+")");
				int pixel=desImage.getRGB(srcImage.getWidth(),index);
				int r=(pixel&0xff0000)>>16;
				int g=(pixel&0xff00)>>8;
				int b=(pixel&0xff);
				String type="png";
				if(r==1)
					type="jpg";
				else if(r==2)
					type="jpeg";
				else if(r==3)
					type="bmp";
				else if(r==4)
					type="png";
				System.out.println("图像格式："+type+"  标志值："+g+"  变换次数:"+b);
			}
			
		}
		public void anti_block_Arnold() {//分块的Arnold逆变换
			/**分块arnold逆变换
 * 
 * 
 * @param args
 */		
/**后期图像加入单个像素表示N的值
 * 
 */
			
			if(srcImage.getWidth()==srcImage.getHeight()) {//长宽相等，正方形图片直接变换
			
				blocksrcMatrix=srcMatrix;//正方形图片只有一个分块，就是自身
				antiarnold();
				desMatrix=blockdesMatrix;//唯一的分块矩阵就是最终的矩阵
				
			}
			else {//长高不等
				
				
				
				desMatrix=srcMatrix;
				int more,less;//more是长的一边，less是短的一边
				boolean wh=false;
				if(srcImage.getWidth()>srcImage.getHeight())//长大于高
				{
					more=srcImage.getWidth();
					less=srcImage.getHeight();
					wh=true;
				}
				else//高大于长
				{
					more=srcImage.getHeight();
					less=srcImage.getWidth();
				}
				
				
				blockleast=more%less;//查看长宽比例是否是整数，如果比例是整数 如4/2=2没有余数，那么分块刚好2块，如果是5/2=2****1那么块数应当+1
				if(blockleast==0)   blocknum=more/less;
				else                blocknum=more/less+1;//正常加密块从左边开始，依次加密，最后的一块从右边计算，因此与右边第二块重叠的部分长度应该是blockleast的长度
				
			//这里和加密过程相反，由于加密时最右边的块可能重叠，因此要先解密右边
				if(wh) {//长大于高情况
					blocksrcMatrix=new int[N][N];
					//取出最右侧的矩阵
					for(int i=0;i<N;i++)
						for(int j=0;j<N;j++)
							blocksrcMatrix[i][j]=srcMatrix[more-N+i][j];
					antiarnold();
					for(int i=0;i<N;i++)
						for(int j=0;j<N;j++)
							desMatrix[more-N+i][j]=blockdesMatrix[i][j];
				}
				else {
					blocksrcMatrix=new int[N][N];
					for(int i=0;i<N;i++)
						for(int j=0;j<N;j++)
							blocksrcMatrix[i][j]=srcMatrix[i][more-N+j];
					antiarnold();
					for(int i=0;i<N;i++)
						for(int j=0;j<N;j++)
							desMatrix[i][more-N+j]=blockdesMatrix[i][j];
				}
				//最右边的块解密后继续其他解密
				for(int i=0;i<blocknum-1;i++)//除了最后一块以外分块加密
				{
					blocksrcMatrix=new int[N][N];
					if(wh)//长大于高
					{
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								blocksrcMatrix[x][y]=desMatrix[i*N+x][y];
							}
						//取出除最后一块以外的分块矩阵
						antiarnold();
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								desMatrix[i*N+x][y]=blockdesMatrix[x][y];
							}
						//将变换完的矩阵放回desMatrix中
					}
					else
					{
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								blocksrcMatrix[x][y]=desMatrix[x][i*N+y];
							}
						//取出除最后一块以外的分块矩阵
						antiarnold();
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								desMatrix[x][i*N+y]=blockdesMatrix[x][y];
							}
						//将变换完的矩阵放回desMatrix中
					}
				}
			}
			for(int i=0;i<srcImage.getWidth();i++)
				for(int j=0;j<srcImage.getHeight();j++)
					desImage.setRGB(i, j, desMatrix[i][j]);
		}
		public void setenName(String name)//设置加密图像名字
		{
			endesName=name;
		}
		public void setenpath(String path)//设置解密图像路径
		{
			enpath=path;
		}
		public void setdeName(String name)//设置解密图像名字
		{
			dedesName=name;
		}
		public void setdepath(String path)//设置解密图像路径
		{
			depath=path;
		}





}

