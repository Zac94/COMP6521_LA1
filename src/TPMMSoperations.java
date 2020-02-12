import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;



public class TPMMSoperations {
	private List<File> subLists = new ArrayList<>();
	 public void intializing(String path) throws Exception {
		 File folder=  new File(path);
	        phase1(folder);
	    }

	private void phase1(File folder) throws IOException {
		
		File[] fileNames = folder.listFiles();
		//System.out.println(fileNames.length);
		int number_line_to_read=(calculateAvailableMemory()-(1024*1024)) / (250);
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
	                	 subLists.add(generateSublist(file.getName(),records));
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
		}
		phase2(subLists);
		
	}
	
	private File generateSublist(String name, String[] records1) throws IOException {
	///	System.out.println("Size---->:"+records1.length);
		   File temp = File
	                .createTempFile(name, null, new File("C://Twilight Series"));
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
	
    private void phase2(List<File> fileList)
            throws IOException {
    	
    }

	private int calculateAvailableMemory() {
		System.gc();
	        Runtime runtime = Runtime.getRuntime();
	        long usedMemory = runtime.totalMemory() - runtime.freeMemory();

	        return (int) (runtime.maxMemory() - usedMemory);
	}
	


}
