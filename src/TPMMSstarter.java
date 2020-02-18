import java.io.File;
import java.io.IOException;


public class TPMMSstarter {
    public static void main(String[] args)  {
    	double startTime = (double) System.currentTimeMillis();

    
    	String path="src/input/";
    	TPMMSoperations phase1=new TPMMSoperations();
    	try {
			phase1.intializing(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	double endTime   = (double) System.currentTimeMillis();
    	double totalTime = endTime - startTime;
    	System.out.println("Total time:"+totalTime/1000);

    }
}
