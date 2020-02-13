import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;



public class TPMMSoperations {
	
	private int runcount=1;
	 public void intializing(String path) throws Exception {
		 File folder=  new File(path);
	        phase1(folder);
	    }

	private void phase1(File folder) throws IOException, ParseException {
		
		File[] fileNames = folder.listFiles();
		//System.out.println(fileNames.length);
		int number_line_to_read=(calculateAvailableMemory()-(1024*1024)) /(200);
		
		for(File file : fileNames){
       int count=0;
       BufferedReader buff = new BufferedReader(new FileReader(file));
       String tuple=null;
			//System.out.println(number_line_to_read);
			String[] records = null;
			tuple = buff.readLine();
			while ( tuple!= null) {
				if (count>=number_line_to_read) {
	              
	                	count=0;
	                	generateSublist(file.getName().substring(0, 2),records);
	                	records= new String[number_line_to_read];
	                }
				  if (!tuple.isEmpty()) {
		                
					  if(count==0) {
						  records= new String[number_line_to_read];
					  }
					  records[count]=tuple;
					//  System.out.println(tuple);
		                }
				  count=count+1;
				  tuple = buff.readLine();
       
		}
			buff.close();
			records=null;
		}
		
		/*
		 * System.gc(); calculateAvailableMemory();
		 */
		
		//phase2();
		
	}
	
	private File generateSublist(String name, String[] records1) throws IOException {

		
		   File temp = File
	                .createTempFile(name+"run"+runcount, null, new File("src/temp/"));
	        OutputStream outputStream = new FileOutputStream(temp);
	        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
	        	
	        Arrays.sort(records1, new Comparator<String>(){	 
	            @Override
	            public int compare(String o1, String o2) {
	                return o1.substring(0,8).compareTo(o2.substring(0,8));
	            }});
	        for(int i=0;i<records1.length;i++) {
	        writer.write(records1[i]);
			writer.newLine();
	        }
	        writer.close();
	        
	        outputStream.close();
	   records1=null;
	        
	        return temp;
	}
	
    public void phase2()
            throws IOException, ParseException {
   
    	File folder=  new File("src/temp/");
    	
    	
    	
    	while(true) {
    		int oldRun = 0;
    		File[] fileNames = folder.listFiles();
    		int count = 0;
        	boolean flag=false;
    		System.gc();
    		int number_line_to_read=Math.abs((calculateAvailableMemory()-((1024*1024))) / (fileNames.length));
    		
    		System.out.println(calculateAvailableMemory());
        	int tmpcount=0;
        	String[] records = new String[number_line_to_read];
    		String tmp="run"+runcount;
    	for(File file : fileNames) {
    		if(file.getName().contains(tmp))	{
    		BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
    		String tuple = br.readLine();
    	
    		while(tuple != null) {
    			if (count>=number_line_to_read) {
  	              
    				if(!flag) {
    					flag=true;
    					oldRun=runcount;
    					runcount=runcount+1;
    				}
                	count=0;
                	generateeSublist(file.getName().substring(0, 2),records);
                	 records= new String[number_line_to_read];
                	 
                	 tmpcount=tmpcount+1;
                }
			  if (!tuple.isEmpty()) {
	              				  
			records[count]=tuple;
	                }
			  count=count+1;
			  tuple = br.readLine();
    		}
    		
    		br.close();
    	}
    	}
    	for (File file : fileNames) {
    		if(file.getName().contains("run"+oldRun)) {
            file.delete();
    		}
        }
    	
    	if((fileNames.length==1)) {
    	
    	break;
    	}
  //  	System.out.println(tmpcount);
    	}
    }
	private File generateeSublist(String name, String[] records) throws IOException {
		//ArrayList<String> tempRecords=new ArrayList<String>();
	//	tempRecords.add(records[0]);
		//List<String> record=;
		
		ArrayList<String> record= new ArrayList(Arrays.asList(records));
		records=null;
		
	//	String rec=null;
		
		int i=0,j=i+1;
		
		
		while(i<record.size())
			while(j < record.size()) {
				if(record.get(i).substring(0,8).equalsIgnoreCase(record.get(j).substring(0, 8))){
	
				 if(Integer.parseInt(record.get(i).substring(8,12))< Integer.parseInt((record.get(j).substring(8, 12)))){
				record.set(i, record.get(j));
				record.remove(j);
				j--;
						//	tempRecords.set(i, record.get(j));
						
					
				}else if(Integer.parseInt(record.get(i).substring(8,12))==Integer.parseInt((record.get(j).substring(8, 12)))){
					if(Integer.parseInt(record.get(i).substring(13,15))<Integer.parseInt((record.get(j).substring(13, 15)))) {
						record.set(i, record.get(j));
						record.remove(j);
						j--;
						}
					else if(Integer.parseInt(record.get(i).substring(13,15))==Integer.parseInt((record.get(j).substring(13, 15)))) {
						if(Integer.parseInt(record.get(i).substring(16,18))<Integer.parseInt((record.get(j).substring(16, 18)))) {
							record.set(i, record.get(j));
							record.remove(j);
							j--;
							}
}
				}
					j++;	
			}
				i++;
			}
		File temp = File
                .createTempFile(name+"run"+runcount, null, new File("src/temp/"));
        OutputStream outputStream = new FileOutputStream(temp);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        	
        for(int ii=0;ii<record.size();ii++) {
        writer.write(record.get(ii));
		writer.newLine();
        }
        writer.close();
        
        outputStream.close();
   record=null;
        
        return temp;
		}
		
		
	

	private int calculateAvailableMemory() {
		System.gc();
	        Runtime runtime = Runtime.getRuntime();
	        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
System.out.println((runtime.maxMemory() - usedMemory));
	        return (int) (runtime.maxMemory() - usedMemory);
	}
	


}
