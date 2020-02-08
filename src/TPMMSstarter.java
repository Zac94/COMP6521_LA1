import java.io.File;
import java.io.IOException;


public class TPMMSstarter {
    public static void main(String[] args)  {
    	String path="src/input/";
    	Phase1 phase1=new Phase1();
    	try {
			phase1.intializing(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
}
