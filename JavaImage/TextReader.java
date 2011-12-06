import java.io.*;
import java.util.*;

public class TextReader{
	String path;
	File f;
	FileReader fr;
	BufferedReader br;
	ArrayList<int[]> minArray;
	ArrayList<String[]> minInfoArray;
	public static boolean bool=true;
	public TextReader(String str){
		//int[][] minLocation;
		path=str;
		try{
			
			f=new File(path);
			System.out.println(f.getPath());
			fr=new FileReader(f);
			br=new BufferedReader(fr);
			
		}catch(Exception ex){
			System.out.println("can not find the files. please make sure that your source path");
			ex.printStackTrace();
		
		}
	}//end of constructor TextReader
	private void closeReader(){
		try{
			//br.flush();
			br.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private int[] getMinLocation(String str){
		int coordinate[]=new int[2];
		String line=str;
		String strCoor;
		StringTokenizer st=new StringTokenizer(line,":");
		st.nextToken();
		strCoor=st.nextToken();
		String[] strArray=strCoor.split(",");
		coordinate[0]= Integer.parseInt(strArray[0].trim());		
		coordinate[1]= Integer.parseInt(strArray[1].trim());
				
		return coordinate;
	}//end of method getMinlocation
	private String[] getMinInformation(String str){//return four parameter of the min file
		//0-x 1-y 2-angel 3- reliability 4 type
		
		String[] minInfo=new String[4];
		String line=str;
		String coordinate="";
		StringTokenizer st=new StringTokenizer(line,":");
		st.nextToken();
		coordinate=st.nextToken();
	
		minInfo[0]=coordinate.split(",")[0].trim();
		minInfo[1]=coordinate.split(",")[1].trim();
		minInfo[2]=st.nextToken();
		minInfo[3]=st.nextToken();
		if(false){
			for(int i=0;i<4;i++){
				System.out.println("x:   "+minInfo[0]+"; y:   "+minInfo[1]+";   angle:  "+minInfo[2]+";  type:  "+minInfo[3]);
			}
		
		}
		return minInfo;	
		
	}
	private void skipLine(int n){
		int i=0;
		if(br!=null){
			//System.out.println("skip"+ n+" line");
			while(i<n){			
				readOneLine();
				//System.out.println("skiped :" +readOneLine());
				i++;
			}
		}//end if
		
	}//end of the method skipLine
	private String readOneLine(){
		String line="";
		try{
		if(br!=null){
			line=br.readLine();			
		}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return line;
	}//end of method readLine
	
	public ArrayList<int[]> readMinutaeCoordinate(){
		minArray=new ArrayList<int[]>();
		String data;
		skipLine(4);
		while((data=readOneLine())!=null){
			//System.out.println(data);
			minArray.add(getMinLocation(data));
		}

		return minArray;
	}//end of readMIn
	
	public ArrayList<String[]> readMinuateInformation(){
			minInfoArray=new ArrayList<String[]>();
			String data;
			skipLine(4);
			while((data=readOneLine())!=null){
				minInfoArray.add(getMinInformation(data));
				}
			return minInfoArray;
		}
	
	/*public static void main(String[] args){
		String path=TextReader.class.getResource("").getFile();
		TextReader.Printme(path);
		TextReader tr=new TextReader(path+"IMG_2708_1.min");
		//String line=tr.readOneLine();
		ArrayList<String[]> arra=tr.readMinuateInformation();
		
		int size=arra.size();
		
		for(int i=0;i<size;i++){
			String a[]=new String[4];
			a=arra.get(i);
			System.out.println(i+"   "+a[0] + "  "+ a[1] +"    "+a[2]+ "     " +a[3]);			
		}	

	}//end of method main*/
	public static void Printme(String str)
	{
		if(bool)
		System.out.println(str);
	}
}
