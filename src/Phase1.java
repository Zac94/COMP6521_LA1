import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Phase1 {
	
	private File folder;
	
	private long availableMemory; 
    
	private TreeMap<String, String> sortedRecords = new TreeMap<>();
	private  long blockSize ;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private List<File> subLists = new ArrayList<>();
	  public void intializing(String path) throws Exception {
		  folder=  new File(path);
	        availableMemory = calculateAvailableMemory();
	        //System.out.println("memo"+availableMemory);
	        createSublist();
	    }


	private void createSublist() throws Exception {
		
	        //long maxBlockSize = availableMemory / 2;
	    List<String> writeTuple = new ArrayList<>();
	        File[] fileNames = folder.listFiles();
	        
	         for(File file : fileNames){
	        	 blockSize = 0;
	        try {
	        	BufferedReader buff = new BufferedReader(new FileReader(file));
				String tuple = buff.readLine();
	         
	           
	            while ((tuple = buff.readLine()) != null) {
	            //	System.out.println("block size"+blockSize);
	            	
	    	      //  System.out.println("calculateAvailableMemory();"+calculateAvailableMemory());
	                if (blockSize >= availableMemory/2) {
	                //	System.out.println("inside 1st if");
	                	checkingAndClearingMemory(blockSize,file.getName());
	                }

	                if (!tuple.isEmpty()) {
	                //	System.out.println("2nd if" );
	                	//System.out.println(tuple);
	                	if(!sortedRecords.containsKey(tuple.substring(0, 8))) {
	                	System.out.println("Before");
	                		checkingAndClearingMemory(blockSize,file.getName());
	                		System.out.println("After");
	                		sortedRecords.put(tuple.substring(0, 8), tuple);
	    				}else {
	    					Date dateNew = sdf.parse(tuple.substring(8, 18));
	    					Date dateOld = sdf.parse(sortedRecords.get(tuple.substring(0, 8)).substring(8, 18));
	    					if(dateNew.after(dateOld)) {
	    						checkingAndClearingMemory(blockSize,file.getName());
	    						sortedRecords.put(tuple.substring(0, 8), tuple);
	    					}
	    				}
	                	//writeTuple.add(tuple);
	                    blockSize += calculateStringSize(tuple);
	                }
	                
	            }
	        }catch(Exception e){
	         throw(e);
	        }
	       finally {
	        
	    	   subLists.add(generateSublist(file.getName(), sortedRecords));
	    	   sortedRecords.clear();
	        }
	         }
		
	}


	private void checkingAndClearingMemory(long size, String fileName) throws IOException {
		System.out.println("Before ---------If ");
		//System.out.println("---------If "+calculateAvailableMemory());
		if(size>=calculateAvailableMemory())
		{
			 subLists.add(generateSublist(fileName, sortedRecords));
			 blockSize = 0;
             sortedRecords.clear();
		}
		System.out.println("After ---------If");
		
	}


	private File generateSublist(String fileName, TreeMap<String, String> sortedRecords2) throws IOException {
		
	        File temp = File
	                .createTempFile(fileName, null, new File("C://Twilight Series"));
	        OutputStream outputStream = new FileOutputStream(temp);
	        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"))) {
	        	for(Map.Entry<String, String> e : sortedRecords2.entrySet()) {
	    			writer.write(e.getValue());
	    			writer.newLine();
	    		}
	        }
	        return temp;
		
	}


	private long calculateAvailableMemory() {
	        Runtime runtime = Runtime.getRuntime();
	        long usedMemory = runtime.totalMemory() - runtime.freeMemory();
	        return runtime.maxMemory() - usedMemory;
	}
	
	
	 private long calculateStringSize(String line) {
	        return 8 * (int) ((((line.length()) * 2) + 45) / 8);
	    }

}
