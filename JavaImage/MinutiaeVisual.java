import javax.imageio.*;
import javax.imageio.spi.*;
import java.io.*;
import java.io.BufferedWriter;
import java.awt.image.*;
import java.awt.Image;
import java.awt.*;
import javax.swing.*;
import java.util.*;
public class MinutiaeVisual{
	protected static int scale=1;
	protected static double reliabilityThrehold=0;
	private static double everageReliability=0.0;
	
	public static String decodePath(String path){
		String absolutepath=path;
	try{
		System.out.println("before decode:"+absolutepath);
		absolutepath=java.net.URLDecoder.decode(absolutepath,"utf-8");
		System.out.println("after decode:"+absolutepath);
	}catch(Exception ex){
		ex.printStackTrace();
	}
		return absolutepath;
	}
	public static void main(String[] args){
			String path=TextReader.class.getResource("").getFile();
			String imgpath=path;
			String minpath=path+"IMG_2710_1.min";
			String minOutputPath="";
			String bildName="IMG_2708_0";
			
				imgpath=decodePath(path+args[0].toString());
				minpath=decodePath(path+args[1].toString());
				minOutputPath=decodePath(path+args[2].toString());
			
		if(args.length==3){
			

				minutaeVisual(imgpath,minpath,minOutputPath);
				
		}else if (args.length==4){

				scale=Integer.parseInt(args[3].toString());
				minutaeVisual(imgpath,minpath,minOutputPath);
		}else if (args.length==5){

				scale=Integer.parseInt(args[3].toString());
				reliabilityThrehold=Double.parseDouble(args[4].toString());
				minutaeVisual(imgpath,minpath,minOutputPath);
		}
		
		
		else{
			// System.out.println(imgpath);
			 
			System.out.println("Usage: java Imagetest Path1 Path2 Path3");
			System.out.println("Path1 : the path of Image");
			System.out.println("Path2 : the path of minutae data. *.min");
			System.out.println("Path3 : the destination Image");
			System.out.println("[int pixel scale]");
			
		
		}

	
	}//end of static main
	
	public static void minutaeVisual(String imgpath,String minpath,String minOutputPath){
		try{			
				
			BufferedImage bi;			
			InputStream is=new BufferedInputStream(new FileInputStream(imgpath));
			bi=ImageIO.read(is);
			
			int x=bi.getWidth();
			int y=bi.getMinY();
			
			Color cc=new Color(255,0,0);
						
			TextReader tr=new TextReader(minpath);
			//ArrayList<int[]> minutaeArray=tr.readMinutaeCoordinate();//read coordinate only
			ArrayList<String[]> minutiaeInformationArray=tr.readMinuateInformation();//read four parameters
	
			int size=minutiaeInformationArray.size();
	
			
			for(int i=0;i<size;i++){
				String str[]=new String[4];
				str=minutiaeInformationArray.get(i);
				int a[]=new int[2];
				a[0]=Integer.parseInt(str[0]);
				a[1]=Integer.parseInt(str[1]);
				String angle=str[2];
				double reliability=Double.parseDouble(str[3]);
				everageReliability=everageReliability+reliability;
				if(reliability>reliabilityThrehold){
					//System.out.println("before("+a[0]+","+a[1]+"):RGB"+bi.getRGB(a[0],a[1]));
					for (int px=-1;px<=scale;px++){
						for(int py=-1;py<=scale;py++){
							bi.setRGB(a[0]+px,a[1]+py,cc.getRGB());
						}//end of third for						
					}//end of second for
				}
				
			}//end of frist for
			if(true){
				
				everageReliability=everageReliability/size;
				FileWriter fw=new FileWriter("reliability.txt",true);
				BufferedWriter bw=new BufferedWriter(fw);
				bw.write(minOutputPath+":"+everageReliability);
				bw.newLine();
				bw.close();
			}
			ImageIO.write(bi,"JPEG",new File(minOutputPath));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}//end of catch
	
	}//end of function minutiaevisual
}
