import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
public class Driver {
	static TreeMap<String, String> tuples = new TreeMap<>();

	public static void main(String[] args) throws IOException, ParseException{
		String file1 = "src/input/T1.txt";
		String file2 = "src/input/T2.txt";
		SortFile(file1);
		SortFile(file2);
	}

	private static void SortFile(String file) throws IOException, ParseException {
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		BufferedWriter write = new BufferedWriter(new FileWriter("src/output/file1.txt"));
		String s = "";
		try {
			BufferedReader buff = new BufferedReader(new FileReader(file));
			s = buff.readLine();
			while(s != null) {
				if(!tuples.containsKey(s.substring(0, 8))) {
					tuples.put(s.substring(0, 8), s);
				}else {
					Date dateNew = sdf.parse(s.substring(8, 18));
					Date dateOld = sdf.parse(tuples.get(s.substring(0, 8)).substring(8, 18));
					if(dateNew.after(dateOld)) {
						tuples.put(s.substring(0, 8), s);
					}
				}
				s = buff.readLine();
				
			}
			for(Map.Entry<String, String> e : tuples.entrySet()) {
				write.write(e.getValue());
				write.newLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			write.close();
		}
		
	}

}
