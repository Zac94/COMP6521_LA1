import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

class TPMMSoperationsTest {

	
	
	@Test
	public void testAvailableMemoryMethod() {
		TPMMSoperations operation=new TPMMSoperations();
		int memory=operation.calculateAvailableMemory();
		assertNotEquals(5, memory);
		
		
	}
	
	@Test
	public void testOutputFIle() {
		TPMMSoperations operation=new TPMMSoperations();
		String path="src/input/";
		boolean flag=false;
		try {
			operation.intializing(path);
			File folder = new File("src/temp/");
			File[] fileNames = folder.listFiles();
			
			for (File file : fileNames) {
				if (file.getName().contains("output")) {
					flag=true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	assertFalse(!flag);
	}


}
