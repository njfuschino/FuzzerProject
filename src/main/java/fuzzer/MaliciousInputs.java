package fuzzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MaliciousInputs {

	public static List<String> getMaliciousInputs(String maliciousInputFilePath)
			throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(
				maliciousInputFilePath));

		try {
			ArrayList<String> inputs = new ArrayList<String>();

			String line = reader.readLine();
			while (line != null) {
				if (!line.equals("")) {
					inputs.add(line);
				}
				line = reader.readLine();
			}

			return inputs;
		} finally {
			reader.close();
		}
	}
}