import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Vector;

public class Term {

	static public Vector<String>v=new Vector<String>();
	public static String x="",address="D:\\";
	static	Scanner sc=new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		
		while(true){
			v.clear();
		x=sc.nextLine();
		if(isSemiColon(x)){
			String tmp="";
			for(int i=0 ; i<x.length() ; i++)
			{
				if(x.charAt(i)==';')
				{
					while(tmp.length()>0 && tmp.charAt(0)==' ')	tmp=tmp.substring(1);
					while(tmp.length()>0 && tmp.charAt(tmp.length()-1)==' ')	tmp=tmp.substring(0,tmp.length()-1);
					//System.out.println(tmp +" "+ tmp.length());
					SolveCmd(tmp);
					tmp="";
					continue;
				}
				tmp=tmp+x.charAt(i);
			}
			while(tmp.length()>0 && tmp.charAt(0)==' ')	tmp=tmp.substring(1);
			while(tmp.length()>0 && tmp.charAt(tmp.length()-1)==' ')	tmp=tmp.substring(0,tmp.length()-1);
			if(tmp.length()>0)	SolveCmd(tmp);
			//System.out.println(tmp +" "+ tmp.length());
		}
		else
			{SolveCmd(x);}
		}
	}
	public static boolean isSemiColon(String s) {
		for(int i=0 ; i<s.length() ; i++)
			if(s.charAt(i)==';')	return true;
		return false;
	}
	public static void SolveCmd(String s) throws Exception {
			v.clear();
			Exe(s);
			String s1="";
			if(v.size()==0)	System.out.println("ERROR");
			else{
			s1=v.elementAt(0);
			if(s1.equals("pwd")){
				if(v.size() > 1)	System.out.println("ERROR");	
				else System.out.println(address);
			}
			else if(s1.equals("mkdir")){
				if(v.size()!=2)	System.out.println("ERROR");
				else
				{
					String FileName=v.elementAt(1);
					createFolder(address,FileName);
				}
			}
			else if(s1.equals("rmdir")){
				if(v.size()!=2)	System.out.println("ERROR");
				else
				{
					String FileName=v.elementAt(1);
					File ff= new File(address+FileName);
					RemoveFile(ff);
				}
			}
			else if(s1.equals("cat")){
				if(more()) // if it use more
				{
					ReadFile_more(v.elementAt(1));
				}
				else
				{
					if(v.size()==1)	System.out.println("ERROR");
					else if(v.size()==2 && v.elementAt(1).charAt(0)=='>')	// create file
					{
						String x=v.elementAt(1).substring(1);
						CreatNewFile(x);
					}
					else if(v.size()==3 && v.elementAt(1).equals("<")) // use file as a input for a command
					{
						//System.out.println(v.elementAt(2));
						inputFromFile(v.elementAt(2));
					}
					else if(v.size()==4)
					{
						if(v.elementAt(2).equals(">")) // write text of file1 One in file2
						{
							FromFileToFile_Write(v.elementAt(1),v.elementAt(3));
						}
						else if(v.elementAt(2).equals(">>"))  // Append text of file1 One in file2
						{
							FromFileToFile_Append(v.elementAt(1),v.elementAt(3));
						}
						else	// Print text Off All Files
						{
							Printtt();
						}
 
					}
					else
					{
						//System.out.println("YES");
						Printtt();
					}
				}
			}else if(s1.equals("clear")){
				if(v.size() > 1)System.out.println("ERROR");	
				else clear();
			}
			else if(s1.equals("ls")){
				if(v.size() > 1)System.out.println("ERROR");	
				else ls();
			}
			else if(s1.equals("cd")){
				if(v.size() >2)System.out.println("ERROR");
				else{
					if(v.elementAt(1).charAt(1) == ':'){
						Character  tmp = v.elementAt(0).charAt(0);
						tmp = Character.toUpperCase(tmp);
						cd(tmp+":\\");
					}else{
					String tmp = address + "\\"+v.elementAt(1);
					//System.out.println(v.elementAt(1).charAt(1));
					cd(tmp);
					}

				}
			}
			else if(s1.equals("cp")){
				if(v.size()>3)System.out.println("ERROR");
				else {
					if(v.elementAt(1).equals(v.elementAt(2))){
						System.out.println("ERROR : file can not be copyed on it's self");
					}else{
						cp(v.elementAt(1) , v.elementAt(2));
					}
				}
			}
			else if(s1.equals("rm")){
				if(v.size() >2)System.out.println("ERROR");
				else{
					String tmp = address + "\\"+v.elementAt(1);
					rm(tmp);
				}
			}
			else if(s1.equals("mv")){
				if(v.size()>3)System.out.println("ERROR");
				else {
					if(v.elementAt(1).equals(v.elementAt(2))){
						System.out.println("ERROR : file can not be moved on it's self");
					}else{
						mv(v.elementAt(1) , v.elementAt(2));
					}
				}
			}
			else if(s1.equals("Date") || s1.equals("date")){
				if(v.size()>1)	System.out.println("ERROR");
				else ShowDate();
			}
			else if(s1.equals("exit") || s1.equals("Exit")){
				exit();
			}
			else System.out.println("ERROR");
		}
		
		
	}
	public static void ReadFile_more(String FileName)
	{
		try {
		    BufferedReader in = new BufferedReader(new FileReader(address+FileName));
		    String str;
		    int count=5;
		    String h="";
		    while ((str = in.readLine()) != null && count>0){
		    	System.out.println(str);
		    	count--;
		    }
		    if((str = in.readLine()) != null){
			    do{
			    	h=sc.next();
			    	System.out.println(str);
			    }
			    while(h.equals(".") &&(str = in.readLine()) != null );
		    }
		    in.close();
		} catch (IOException e) {
		}
	}
	public static void inputFromFile(String FileName) throws Exception
	{
		try {
		    BufferedReader in = new BufferedReader(new FileReader(address+FileName));
		    String str;
		    while ((str = in.readLine()) != null){
		    	//System.out.println(str);
		    	SolveCmd(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
	}
	public static void clear() {
		for (int clear = 0; clear < 1000; clear++) {
			System.out.println("\n");
		}
	}
	public static void FromFileToFile_Write(String File1,String File2) throws IOException {
		
	    BufferedReader inputStream = new BufferedReader(new FileReader(address+File1));
	     File f2 = new File(address+File2);
	    FileWriter filewriter = new FileWriter(f2.getAbsoluteFile());
	    BufferedWriter outputStream= new BufferedWriter(filewriter);
	    String str;
	    while ((str = inputStream.readLine()) != null) {
	        outputStream.write(str);
	    }
	    outputStream.flush();
	    outputStream.close();
	    inputStream.close();
	}
	public static void FromFileToFile_Append(String File1,String File2) throws IOException {
		
	    BufferedReader inputStream = new BufferedReader(new FileReader(address+File1));
	     File f2 = new File(address+File2);
	    FileWriter filewriter = new FileWriter(f2.getAbsoluteFile(),true);
	    BufferedWriter outputStream= new BufferedWriter(filewriter);
	    String str;
	    while ((str = inputStream.readLine()) != null) {
	        outputStream.write(str);
	    }
	    outputStream.flush();
	    outputStream.close();
	    inputStream.close();
	}
	public static Void cd(String name) {
		File folder = new File(name);
		if(folder.isDirectory()){
			address = name;
		}else {
			System.out.println("Error it is not a directory");
		}
		return null;
	}
	public static void ls() {
		File folder = new File(address);
		if(!folder.exists()){
			System.out.println("Error the directory doesn't exis");
			return;
		}
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory())
				System.out.println(listOfFiles[i].getName() + " <dir>");
			else
				System.out.println(listOfFiles[i].getName());
		}
	 
	}
	// how to use this shit function
	// first take the source path which is the folder or the file
	// which suppose to be copy send it as source path with \\ at the end of
	// it's name and send the target with the name of the file but take it from
	// the user as onlt the dest
	public static void cp(String source , String target ){
	    File src = new File(source);
	    File tar = new File(target);
	    if(src.isDirectory() && !tar.exists()){
	    	tar.mkdir();
	    }
		if (src.isDirectory()) {
			File[] listOfFiles = src.listFiles();
		    for (int i = 0; i < listOfFiles.length; i++) {
		        	cp(source + "\\" + listOfFiles[i].getName() , target + "\\" + listOfFiles[i].getName());
			    }
		    } else {
	    	try (
	                InputStream in = new FileInputStream(source);
	                OutputStream out = new FileOutputStream(target)
	        ) {
	            byte[] buf = new byte[1024];
	            int length;
	            while ((length = in.read(buf)) > 0) {
	                out.write(buf, 0, length);
	            }
	        } catch (FileNotFoundException e) {
				//e.printStackTrace();
				System.out.println("Error file not found");
			} catch (IOException e) {
				System.out.println("Error file not found");
			}
	    }
	}
	public static void rm(String tar){
		File test = new File(tar);
		if(!test.exists()){
			System.out.println("File not exist");
		}else if(test.isDirectory()){
			System.out.println("Error : it is a Directory");
		}else if(test.isFile()){
			test.delete();
		}
	}
	public static void RemoveFolderRecrsive(File f) throws Exception {
		  if (f.isDirectory()) {
		    for (File c : f.listFiles())
		    	RemoveFolderRecrsive(c);
		  }
		  f.delete();
		  if (!f.delete())
		    throw new FileNotFoundException("Failed to delete file: " + f);
		}
	
	public static void mv(String src , String tar){
		cp(src , tar);
		File tmp = new File(src);
		if(tmp.isDirectory()){
			try {
				RemoveFolderRecrsive(tmp);
			} catch (Exception e) {
			//	e.printStackTrace();
			}
		}else rm(src);
		
	}
	public static void ShowDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println(dateFormat.format(cal.getTime()));
	}
	public static void exit(){
		System.exit(0);
	}

    public static void createFolder(String path,String FileName) throws Exception {
        File dir = new File(path+FileName);
        dir.mkdir();
    }
    public static void RemoveFile(File f) throws Exception {
    	  if ((f.isDirectory() && f.listFiles().length>0)||!f.isDirectory()) {
    	    System.out.println("This Directory is not empty \n");
    	  }
    	  else if (!f.delete())
    	    System.out.println("Failed to delete file: " + f);
    	}
    public static void Printtt() throws Exception{ // print vector files 
    	if(!valid())	System.out.println("ERROR");
    	else
    	{
    		for(int i=1 ; i<v.size(); i++){
    			//System.out.println(v.elementAt(i));
    			PrintFile(v.elementAt(i));}
    	}
    }     
    public static void PrintFile(String FileName) throws Exception { // print data from a file
		try {
		    BufferedReader in = new BufferedReader(new FileReader(address+FileName));
		    String str;
		    while ((str = in.readLine()) != null){
		    	System.out.println(str);
		    }
		    in.close();
		} catch (IOException e) {
		}
    }    
    public static void Exe(String s){  // extract the input string in vector
    	String tt="";
    	for(int i=0 ; i<s.length() ; i++)
    	{
    		if(s.charAt(i)==' ')
    		{
    			if(tt.length()>0)	v.addElement(tt);
    			tt="";
    			continue;
    		}
    		else if(s.charAt(i)=='"')
    		{
    			tt="";
    			i++;
    			for( ; s.charAt(i)!='"' ; i++)
    				tt+=s.charAt(i);
    			i++;
    			continue;
    		}
    		tt=tt+s.charAt(i);
    	}
    	if(tt.length()>0)	v.addElement(tt);
    }
    public static boolean more(){ // check if need more or not
    	if(v.size()!=4)	return false;
    	for(int i=0 ; i<v.size() ; i++)
    	{
    		if(v.elementAt(i).equals("|"))	return true;
    	}
    	return false;
    }
    public static void CreatNewFile(String FileName)
    {
    	try {

  	      File file = new File(address+FileName);

  	      if (file.createNewFile()){
  	        //System.out.println("File is created!");
  	      }else{
  	        System.out.println("ERROR");
  	      }

      	} catch (IOException e) {
      	}
    	
    }
    public static boolean valid(){ // check the validaty of the vector strings;
    	boolean M=true;
    	for(int i=0 ; i<v.size() ; i++)
    	{
    		String tt=v.elementAt(i);
    		for(int j=0 ; j<tt.length() ; j++)
    		{
    			if(!Character.isLetter(tt.charAt(j)))
    			{
    				if(tt.charAt(j)!='.')	{M=false;
    				System.out.println(tt.charAt(j)+" YEEEEEEES \n");}
    			}
    		}
    	}
    	return M;
    }
    
    public static void args(int x){
    	if(x == 0){
    		System.out.println("Clear : no parameters");
    	}else if(x == 1){
    		System.out.println("CD : directory ");
    	}else if(x == 2){
    		System.out.println("ls : no parameters ");
    	}else if(x == 3){
    		System.out.println("cp : (directory,file) - > (directory) ");
    	}else if(x == 4){
    		System.out.println("mv :(directory,file) - > (directory,file) ");
    	}else if(x == 5){
    		System.out.println("rm : file  ");
    	}else if(x == 6){
    		System.out.println("mkdir : name of the wanted folder ");
    	}else if(x == 7){
    		System.out.println("rmdir : name of the folder to be remove  ");
    	}else if(x == 8){
    		System.out.println("cat : ");//to be edited
    	}else if(x == 9){
    		System.out.println("more : ");// to be edited
    	}else if(x == 10){
    		System.out.println("pwd : no paremater ");
    	}else if(x == 11){
    		System.out.println("date : no paremater ");
    	}else if(x == 12){
    		System.out.println("Exit : no paremater ");
    	}
    }
    public static void helpv(int x){
    	if(x == 0){
    		System.out.println("Clear : just clear the console");
    	}else if(x == 1){
    		System.out.println("CD : change the directory  ");
    	}else if(x == 2){
    		System.out.println("ls : list all the files and folder in the directory ");
    	}else if(x == 3){
    		System.out.println("cp : copy file or folder into other directory");
    	}else if(x == 4){
    		System.out.println("mv :move file or folder into onther directory ");
    	}else if(x == 5){
    		System.out.println("rm : remove file  ");
    	}else if(x == 6){
    		System.out.println("mkdir : make directory with name given ");
    	}else if(x == 7){
    		System.out.println("rmdir : remove directory with name given  ");
    	}else if(x == 8){
    		System.out.println("cat : ");// to be edited
    	}else if(x == 9){
    		System.out.println("more : ");// to be edited
    	}else if(x == 10){
    		System.out.println("pwd : show the directory address ");
    	}else if(x == 11){
    		System.out.println("Date : show the current date and time ");
    	}else if(x == 12){
    		System.out.println("Exit : exit the prgramme ");
    	}
    }
    public static void help(){
    	for(int i=0;i<=12;i++){
    		helpv(i);
    		args(i);
    	}
    }
}
