import java.io.File;
import java.io.IOException;


public class TPMMSstarter {
    public static void main(String[] args)  {
    	String path="src/input/";
    	TPMMSoperations phase1=new TPMMSoperations();
    	try {
			phase1.intializing(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
