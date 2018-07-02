package com.gcs.test;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		String ip = "183.82.125.183";
		int port = 50088;
		Socket s = new Socket(ip, port);

		String str = "Gemini";
		OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
		PrintWriter out = new PrintWriter(os);
		out.println(str);
		os.flush();
	}
}
