import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Score {
	File path = new File("Pisti Scores.txt");
	public String scores = "";

	public Score() {
		Read();
	}
	public void Write(String score) {
		try {
			CheckFileExist();
			scores += (scores.isEmpty() ? "" : "\r\n") + score;
			String[] s = scores.split("\r\n");
			scores = "";
			for (int i = 0; i < s.length; i++) {
				String[] a = s[i].split(" ");
				for (int j = i + 1; j < s.length; j++) {
					String[] b = s[j].split(" ");
					if (Integer.parseInt(a[a.length - 1]) < Integer.parseInt(b[b.length - 1])) {
						String temp = s[i];
						s[i] = s[j];
						s[j] = temp;
					}
				}
				scores += (scores.isEmpty() ? "" : "\r\n") + s[i];
				if (i == 9)
					break;
			}
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
			out.write(scores);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Read() {
		try {
			CheckFileExist();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
			scores = "";
			String line = br.readLine();
			while (line != null) {
				scores += line;
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void CheckFileExist() throws Exception {
		if (path.exists() == false)
			path.createNewFile();
	}

	public void ShowScoreBoard() {
		if (scores.isEmpty() == false)
			System.out.println("\r\n-------LeadershipTable-------\r\n" + scores + "\r\n");
	}
}
