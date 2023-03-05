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
	
	
		private File srcImageFile;//Դ�ļ�
		private BufferedImage srcImage, desImage;
		//srcImage��ԭͼ��Ļ���ͼ��desImage��Ŀ��ͼ��Ļ���ͼ��
		private int[][] srcMatrix,desMatrix;
		//srcMatrix��ʾԭͼ�����desMatrix��ʾĿ��ͼ�����
		private int[][] blocksrcMatrix , blockdesMatrix;
		//blocksrcMatrix��ʾ��ԭͼ�������ȡ���ķֿ����blockdesMatrix��ʾ�任��ķֿ����
		private int N;// �ֿ�Ľ���
		private int time;// ���ܱ任����
		private int antitime;//���ܱ任����
		private int blocknum=1;//Ĭ�Ϸֿ�Ϊ1�飬���Ǿ���ֿ�������ϵͳ�õ�ͼ������ȷ��
		private int blockleast;//���зֿ鴦�����������ص����ֵĳ���
		private int filetype=0;//ԭͼ���ļ��ĺ�׺��ʽ��1.jpg 2.jpeg 3.bmp 4.png
		private String endesName,dedesName;//���ܺ�Ŀ���ļ������ֺͽ��ܺ�Ŀ���ļ�������
		private String enpath,depath;//���ܺ�Ŀ���ļ��ı���·���ͽ��ܺ�Ŀ���ļ��ı���·��
		private String errormessgae="��ͼ�����δ�����ܣ�";
	
		
		public String getErrormessgae() {
			return errormessgae;
		}
	
		/**
		 * 
		 * @param srcImageFileԴ�ļ�
		 * @param desImageFileĿ���ļ�
		 * @param time����
		 */
		public Arnold(File srcImageFile,Boolean ende) {//���캯�� 
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
		public void setTime(int time){//���ñ任����
			this.time=time;
		}
		public BufferedImage readImage(File imageFile) {//��ȡͼ���ļ�
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageFile);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return image;
		}
		public int[][] getMatrixRGB(BufferedImage image) {//��ȡͼ��RGB����
			/**
			 * @param image ͼ�񻺳�
			 * @return imageMatrix ���ؾ���
			 */
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage original=new BufferedImage(width,height,image.getType());
			original.getGraphics().drawImage(image, 0, 0, null);
			int[][] imageMatrix;
			imageMatrix= new int[width][height];//��ȡͼƬʱͼƬ���Բ��������Σ�ֻ��Ϊ�˴���һ��ͼƬ����
			for(int i=0;i<width;i++)
				for(int j=0;j<height;j++)
				{
					
					imageMatrix[i][j]=original.getRGB(i, j);
				}
	
			return imageMatrix;
		}
		public File writeImage() {//�������ͼ��
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
		public File writeantiImage() {//�������ͼ��
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
		public boolean initArnold() {//���ܳ�ʼ��
			/**
			 * Arnold�任��ʼ��
			* 
			* @param image
			* @return boolean �ж��Ƿ���������ͼƬ
			*/
			try {
				srcImage=readImage(srcImageFile);
				int width = srcImage.getWidth();
				int height = srcImage.getHeight();
				if(width<height)	N=width;
				else				N=height;//С��ֵ�ǽ���
				System.out.println("����="+N);
				srcMatrix = getMatrixRGB(srcImage);//��ȡͼƬ����
				
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
		public boolean initantiArnold() {//���ܳ�ʼ��
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
						errormessgae="��ͼ�����δ���ܻ�ԭͼ����ܴ���Խ�磡ͼ����ܱ��۸ģ�";
						return false;
					}
					filetype=c.getRed();
					if(filetype!=1&&filetype!=2&&filetype!=3&&filetype!=4)
					{
						errormessgae="��ͼ�����δ���ܻ�ԭͼ���ʽ����ȷ��ͼ����ܱ��۸ģ�";
						return false;
					}
					int note=c.getGreen();
					if(note!=(ori_height%ori_width)%255)
					{
						errormessgae="��ͼ�����δ���ܻ��־λ��֤����ͼ����ܱ��۸ģ�";
						return false;
					}
				}
				else if(width>height)	
				{
					N=height;//С��ֵ�ǽ���
					ori_width=width-1;
					ori_height=height;
					Color c=new Color(srcImage.getRGB(width-1, ori_width%ori_height));
					antitime=c.getBlue();
					if(antitime>255||antitime<1)
					{
						errormessgae="ԭͼ����ܴ���Խ�磡ͼ����ܱ��۸ģ�";
						return false;
					}
					filetype=c.getRed();
					if(filetype!=1&&filetype!=2&&filetype!=3&&filetype!=4)
					{
						errormessgae="ԭͼ���ʽ����ȷ��ͼ����ܱ��۸ģ�";
						return false;
					}
					int note=c.getGreen();
					if(note!=(ori_width%ori_height)%255)
					{
						errormessgae="��־λ��֤����ͼ����ܱ��۸ģ�";
						return false;
					}
				}
				else
				{
					return false;
				}
				System.out.println("����="+N+"����="+antitime);
				srcImage=srcImage.getSubimage(0, 0, ori_width, ori_height);
				srcMatrix = getMatrixRGB(srcImage);//��ȡͼƬ����
				
				desMatrix = new int[ori_width][ori_height];
				desImage = new BufferedImage(ori_width, ori_height, srcImage.getType());
				System.out.println(desImage.getWidth()+"   "+desImage.getHeight());
						
				return true;
			}catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		public void arnoldTransform() {//Arnold�任
			/**
			* 
			* @param blocksrcMatrix���ⲿ���Ѿ���ֵ
			* @param blockdesMatrix�ڱ������ڲ��´��������ڸ����У��Ա�任��ֵ���ܵ�ͼƬ����
			* 
			* �����о���任��������ͼ��洢
			*/
				blockdesMatrix=new int[N][N];//�����ֿ�任��ľ���
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
		//��ʱblockdesMatrix������Ƿֿ�任��ľ���
		}
		public void antiarnold() {//Arnold��任
			/**
			 * arnold��任
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
				//��ʱblockdesMatrix������Ƿֿ�任��ľ���
		}
		public void block_Arnold() {//�ֿ��Arnold�任
			/**�ֿ�Arnold
			 * ����������ͼƬ
			 * arnold�任������arnoldTransform��
			 * �ú���ֻ�Ƿֿ鴦�����arnold
			 */
			if(srcImage.getWidth()==srcImage.getHeight()) {//������ȣ�������ͼƬֱ�ӱ任
				blocksrcMatrix=srcMatrix;//������ͼƬֻ��һ���ֿ飬��������
				arnoldTransform();
				desMatrix=blockdesMatrix;//Ψһ�ķֿ����������յľ���
			}
			else {//���߲���
				desMatrix=srcMatrix;
				int more,less;//more�ǳ���һ�ߣ�less�Ƕ̵�һ��
				boolean wh=false;
				if(srcImage.getWidth()>srcImage.getHeight())//�����ڸ�
				{
					more=srcImage.getWidth();
					less=srcImage.getHeight();
					wh=true;
				}
				else//�ߴ��ڳ�
				{
					more=srcImage.getHeight();
					less=srcImage.getWidth();
				}
				
				
				blockleast=more%less;//�鿴��������Ƿ���������������������� ��4/2=2û����������ô�ֿ�պ�2�飬�����5/2=2****1��ô����Ӧ��+1
				if(blockleast==0)   blocknum=more/less;
				else                blocknum=more/less+1;//�������ܿ����߿�ʼ�����μ��ܣ�����һ����ұ߼��㣬������ұߵڶ����ص��Ĳ��ֳ���Ӧ����blockleast�ĳ���
				
				for(int i=0;i<blocknum-1;i++)//�������һ������ֿ����
				{
					blocksrcMatrix=new int[N][N];
					if(wh)//�����ڸ�
					{
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								blocksrcMatrix[x][y]=desMatrix[i*N+x][y];
							}
						//ȡ�������һ������ķֿ����
						arnoldTransform();
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								desMatrix[i*N+x][y]=blockdesMatrix[x][y];
							}
						//���任��ľ���Ż�desMatrix��
					}
					else
					{
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								blocksrcMatrix[x][y]=desMatrix[x][i*N+y];
							}
						//ȡ�������һ������ķֿ����
						arnoldTransform();
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								desMatrix[x][i*N+y]=blockdesMatrix[x][y];
							}
						//���任��ľ���Ż�desMatrix��
					}
				}
		
			//�������һ��
				if(wh)//�����ڸ�
				{
					blocksrcMatrix=new int[N][N];
					for(int x=0;x<N;x++)
					{
						for(int y=0;y<N;y++)
						{
							blocksrcMatrix[x][y]=desMatrix[srcImage.getWidth()-N+x][y];
						}
					}
					
					//ȡ�������һ������ķֿ����
					arnoldTransform();
					for(int x=0;x<N;x++)
						for(int y=0;y<N;y++)
						{
							desMatrix[srcImage.getWidth()-N+x][y]=blockdesMatrix[x][y];
						}
					//���任��ľ���Ż�desMatrix��
				}
				else
				{
					blocksrcMatrix=new int[N][N];
					for(int x=0;x<N;x++)
						for(int y=0;y<N;y++)
						{
							blocksrcMatrix[x][y]=desMatrix[x][srcImage.getHeight()-N+y];
						}
					//ȡ�������һ������ķֿ����
					arnoldTransform();
					for(int x=0;x<N;x++)
						for(int y=0;y<N;y++)
						{
							desMatrix[x][srcImage.getHeight()-N+y]=blockdesMatrix[x][y];
						}
					//���任��ľ���Ż�desMatrix��
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
				System.out.println("���һ�г��˱�־λ�Ƿ���Խ�������ֵ��ͬ��"+no+"  ��־λ��Ϊ��("+index+","+srcImage.getHeight()+")");
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
				System.out.println("ͼ���ʽ��"+type+"  ��־ֵ��"+g+"  �任����:"+b);
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
				System.out.println("���һ�г��˱�־λ�Ƿ���Խ�������ֵ��ͬ��"+no+"    ��־λ��Ϊ��("+srcImage.getWidth()+","+index+")");
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
				System.out.println("ͼ���ʽ��"+type+"  ��־ֵ��"+g+"  �任����:"+b);
			}
			
		}
		public void anti_block_Arnold() {//�ֿ��Arnold��任
			/**�ֿ�arnold��任
 * 
 * 
 * @param args
 */		
/**����ͼ����뵥�����ر�ʾN��ֵ
 * 
 */
			
			if(srcImage.getWidth()==srcImage.getHeight()) {//������ȣ�������ͼƬֱ�ӱ任
			
				blocksrcMatrix=srcMatrix;//������ͼƬֻ��һ���ֿ飬��������
				antiarnold();
				desMatrix=blockdesMatrix;//Ψһ�ķֿ����������յľ���
				
			}
			else {//���߲���
				
				
				
				desMatrix=srcMatrix;
				int more,less;//more�ǳ���һ�ߣ�less�Ƕ̵�һ��
				boolean wh=false;
				if(srcImage.getWidth()>srcImage.getHeight())//�����ڸ�
				{
					more=srcImage.getWidth();
					less=srcImage.getHeight();
					wh=true;
				}
				else//�ߴ��ڳ�
				{
					more=srcImage.getHeight();
					less=srcImage.getWidth();
				}
				
				
				blockleast=more%less;//�鿴��������Ƿ���������������������� ��4/2=2û����������ô�ֿ�պ�2�飬�����5/2=2****1��ô����Ӧ��+1
				if(blockleast==0)   blocknum=more/less;
				else                blocknum=more/less+1;//�������ܿ����߿�ʼ�����μ��ܣ�����һ����ұ߼��㣬������ұߵڶ����ص��Ĳ��ֳ���Ӧ����blockleast�ĳ���
				
			//����ͼ��ܹ����෴�����ڼ���ʱ���ұߵĿ�����ص������Ҫ�Ƚ����ұ�
				if(wh) {//�����ڸ����
					blocksrcMatrix=new int[N][N];
					//ȡ�����Ҳ�ľ���
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
				//���ұߵĿ���ܺ������������
				for(int i=0;i<blocknum-1;i++)//�������һ������ֿ����
				{
					blocksrcMatrix=new int[N][N];
					if(wh)//�����ڸ�
					{
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								blocksrcMatrix[x][y]=desMatrix[i*N+x][y];
							}
						//ȡ�������һ������ķֿ����
						antiarnold();
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								desMatrix[i*N+x][y]=blockdesMatrix[x][y];
							}
						//���任��ľ���Ż�desMatrix��
					}
					else
					{
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								blocksrcMatrix[x][y]=desMatrix[x][i*N+y];
							}
						//ȡ�������һ������ķֿ����
						antiarnold();
						for(int x=0;x<N;x++)
							for(int y=0;y<N;y++)
							{
								desMatrix[x][i*N+y]=blockdesMatrix[x][y];
							}
						//���任��ľ���Ż�desMatrix��
					}
				}
			}
			for(int i=0;i<srcImage.getWidth();i++)
				for(int j=0;j<srcImage.getHeight();j++)
					desImage.setRGB(i, j, desMatrix[i][j]);
		}
		public void setenName(String name)//���ü���ͼ������
		{
			endesName=name;
		}
		public void setenpath(String path)//���ý���ͼ��·��
		{
			enpath=path;
		}
		public void setdeName(String name)//���ý���ͼ������
		{
			dedesName=name;
		}
		public void setdepath(String path)//���ý���ͼ��·��
		{
			depath=path;
		}





}

