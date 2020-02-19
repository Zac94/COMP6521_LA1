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
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class TPMMSoperations {

	private int runcount = 1;

	public void intializing(String path) throws Exception {
		File folder = new File(path);
		phase1(folder);
	}

	private void phase1(File folder) throws IOException, ParseException {
		double startTime = (double) System.currentTimeMillis();
		File[] fileNames = folder.listFiles();
		int number_line_to_read = (calculateAvailableMemory()) / (2 * 200);

		int file_lines = 0;
		for (File file : fileNames) {

			BufferedReader buff1 = new BufferedReader(new FileReader(file));
			while (buff1.readLine() != null) {
				file_lines++;
			}
			buff1.close();
		}

		if (file_lines < number_line_to_read) {
			number_line_to_read = file_lines;
		}
		System.out.println("Number of Lines to read:"+number_line_to_read);

		int count = 0;
		String tuple = null;
		String[] records = null;
		String filName = null;
		for (File file : fileNames) {

			BufferedReader buff = new BufferedReader(new FileReader(file));

			tuple = buff.readLine();
			while (tuple != null || (count >= number_line_to_read)) {

				if (count >= number_line_to_read) {

					count = 0;
					generateSublist(file.getName().substring(0, 2), records);
					records = new String[number_line_to_read];
				}
				if (!(tuple == null)) {

					if (count == 0) {
						records = new String[number_line_to_read];
					}
					records[count] = tuple;
					count = count + 1;
					// System.out.println(tuple);
				}
				filName = file.getName();
				tuple = buff.readLine();

			}

			buff.close();

		}
		if (records.length > 0) {
			ArrayList<String> j = new ArrayList<String>();
			for (int i = 0; records[i] != null; i++) {
				j.add(records[i]);
			}
			generateSublist(filName.substring(0, 2), j.toArray(new String[j.size()]));
		}

		records = null;

		System.gc();
		double endTime = (double) System.currentTimeMillis();
		System.out.println("Processing time of Phase 1:" + (endTime - startTime) / 1000);
		
		phase2(file_lines);

	}

	private File generateSublist(String name, String[] records1) throws IOException {
		/// System.out.println("phase1 tuple generatelist");

		File temp = File.createTempFile(name + "run" + runcount, null, new File("src/temp/"));
		OutputStream outputStream = new FileOutputStream(temp);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
		// calculateAvailableMemory();
		Arrays.sort(records1, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.substring(0, 8).compareTo(o2.substring(0, 8));
			}
		});
		for (int i = 0; i < records1.length; i++) {
			writer.write(records1[i]);
			writer.newLine();
		}
		writer.close();

		outputStream.close();
		records1 = null;

		return temp;
	}

	public void phase2(int file_lines) throws IOException, ParseException {
		double startTime = (double) System.currentTimeMillis();
		File folder = new File("src/temp/");
		int sublistCount=0;
		int ioCount=0;
		for (File file : folder.listFiles()) {
			sublistCount=sublistCount+1;
		}
		System.out.println("Number of Sublist created:" + sublistCount);
		while (true) {
			int oldRun = 0;
			File[] fileNames = folder.listFiles();
			int count = 0;
			boolean flag = false;
			System.gc();
			int number_line_to_read = Math.abs((calculateAvailableMemory() - ((1024 * 1024))) / (2 * 200));

			for (File file : fileNames) {

				BufferedReader buff1 = new BufferedReader(new FileReader(file));
				while (buff1.readLine() != null) {
					file_lines++;
				}
				buff1.close();
			}

			if (file_lines < number_line_to_read) {
				ArrayList<String> j = new ArrayList<String>();
				for (File file : fileNames) {

					BufferedReader buff1 = new BufferedReader(new FileReader(file));
					String line = buff1.readLine();
					while (line != null) {
						j.add(line);
						line = buff1.readLine();
					}
					buff1.close();
				}
				generateeSublist("output", j.toArray(new String[j.size()]));
				for (File file : fileNames) {
					if (!file.getName().contains("output")) {
						file.delete();
					}
				}
				break;
			}
			// System.out.println(calculateAvailableMemory());
			int tmpcount = 0;
			String[] records = new String[number_line_to_read];
			String tmp = "run" + runcount;
			String filName = null;
			for (File file : fileNames) {
				if (file.getName().contains(tmp)) {
					BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
					String tuple = br.readLine();

					while (tuple != null || (count >= number_line_to_read)) {
						if (count >= number_line_to_read) {

							if (!flag) {
								flag = true;
								oldRun = runcount;
								runcount = runcount + 1;
							}
							count = 0;
							ioCount=ioCount+1;
							generateeSublist(file.getName().substring(0, 2), records);
							records = new String[number_line_to_read];

							tmpcount = tmpcount + 1;
						}
						if (!(tuple == null)) {

							records[count] = tuple;
							count = count + 1;
							filName = file.getName();
							tuple = br.readLine();
						}

					}

					br.close();
				}
			}
			if (records.length > 0) {
				ArrayList<String> j = new ArrayList<String>();
				for (int i = 0; records[i] != null; i++) {
					j.add(records[i]);
				}
				ioCount=ioCount+1;
				generateeSublist(filName.substring(0, 2), j.toArray(new String[j.size()]));
			}
			for (File file : fileNames) {
				if (file.getName().contains("run" + oldRun)) {
					file.delete();
				}
			}
			records = null;
			File[] fileNames1 = folder.listFiles();
			if ((fileNames1.length <= 2)) {
				ArrayList<String> j = new ArrayList<String>();
				for (File file : fileNames1) {

					BufferedReader buff1 = new BufferedReader(new FileReader(file));
					String line = buff1.readLine();
					while (line != null) {
						j.add(line);
						line = buff1.readLine();
					}
					buff1.close();
				}
				ioCount=ioCount+1;
				generateeSublist("output", j.toArray(new String[j.size()]));
				for (File file : fileNames1) {
					if (!file.getName().contains("output")) {
						file.delete();
					}
				}
				break;
			}

			// System.out.println(tmpcount);
		}
		double endTime = (double) System.currentTimeMillis();
		System.out.println("Processing time of Phase 2:" + (endTime - startTime) / 1000);
		System.out.println("No.of io:"+(ioCount+(2*sublistCount)));
		
	}

	private File generateeSublist(String name, String[] records) throws IOException, ParseException {
		// ArrayList<String> tempRecords=new ArrayList<String>();
		// tempRecords.add(records[0]);
		// List<String> record=;
		TreeMap<String, String> tuples = new TreeMap<>();
		String dateFormat = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		// System.gc();
		for (int i = 0; i < records.length; i++) {
			if (!tuples.containsKey(records[i].substring(0, 8))) {
				tuples.put(records[i].substring(0, 8), records[i]);
			} else {
				Date dateNew = sdf.parse(records[i].substring(8, 18));
				Date dateOld = sdf.parse(tuples.get(records[i].substring(0, 8)).substring(8, 18));
				if (dateNew.after(dateOld)) {
					tuples.put(records[i].substring(0, 8), records[i]);
				}
			}

		}

		File temp = File.createTempFile(name + "run" + runcount, null, new File("src/temp/"));
		OutputStream outputStream = new FileOutputStream(temp);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

		for (Map.Entry<String, String> e : tuples.entrySet()) {
			writer.write(e.getValue());
			writer.newLine();
		}
		writer.close();

		outputStream.close();
		records = null;
		tuples.clear();
		return temp;
	}

	public int calculateAvailableMemory() {
		System.gc();
		Runtime runtime = Runtime.getRuntime();
		long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		/// System.out.println((runtime.maxMemory() - usedMemory));
		return (int) (runtime.maxMemory() - usedMemory);
	}

	 private int calculateStringSize(String line) {
	        return 8 * (int) ((((line.trim().length()) * 2) + 45) / 8);
	    }
}
