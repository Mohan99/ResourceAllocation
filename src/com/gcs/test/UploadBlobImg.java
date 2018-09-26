package com.gcs.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class UploadBlobImg {
    // https://qa.fankick.io/rest/generateSASToken
	public static String URL = "https://fankickuat.blob.core.windows.net/images/";
	public static String TOKEN = "st=2018-09-21T06%3A56%3A53Z&se=2018-09-21T10%3A16%3A53Z&sp=w&sv=2017-07-29&sr=c&sig=7AwZwQxK%2BaqIK9yzSf46ELmLXBD7uq6ognAJdP2FVVU%3D";

	public static void main(String[] args) throws Exception {

		Map<String, String> imgPathMap = new HashMap<String, String>();

		String[] pathList = { "C:\\Users\\pdasineni\\Desktop\\imgs",

		};
		for (String path : pathList) {
			moveToBlob(imgPathMap, path);
			System.out.println(imgPathMap.size() + " images moved to Blob Storage");
			imgPathMap = new HashMap<String, String>();
		}

	}

	public static void moveToBlob(Map<String, String> imgPathMap, String path) {
		String fileType = "";

		listAllFilesFromDirectory(path, imgPathMap);
		for (Map.Entry<String, String> imgSet : imgPathMap.entrySet()) {
			if (imgSet.getKey().endsWith(".jpg") || imgSet.getKey().endsWith(".JPG")
					|| imgSet.getKey().endsWith(".jpeg") || imgSet.getKey().endsWith(".JPEG")) {
				fileType = "image/jpeg";
			} else if (imgSet.getKey().endsWith(".png") || imgSet.getKey().endsWith(".PNG")) {
				fileType = "image/png";
			} else if (imgSet.getKey().endsWith(".gif") || imgSet.getKey().endsWith(".GIF")) {
				fileType = "image/gif";
			} else if (imgSet.getKey().endsWith(".mp3") || imgSet.getKey().endsWith(".MP3")
					|| imgSet.getKey().endsWith(".m4a") || imgSet.getKey().endsWith(".m4a")) {
				fileType = "audio/m4a";
			} else if (imgSet.getKey().endsWith(".mp4") || imgSet.getKey().endsWith(".MP4")) {
				fileType = "video/mp4";
			}

			try {
				System.out.println("File Name:" + imgSet.getKey());
				uploadToBlob(imgSet.getKey(), imgSet.getValue(), fileType);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/** Display All files from a directory */
	public static void listAllFilesFromDirectory(String directoryName, Map<String, String> imgPathMap) {

		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {

			if (file.getAbsolutePath().endsWith(".jpg") || file.getAbsolutePath().endsWith(".JPG")
					|| file.getAbsolutePath().endsWith(".png") || file.getAbsolutePath().endsWith(".PNG")
					|| file.getAbsolutePath().endsWith(".jpeg") || file.getAbsolutePath().endsWith(".JPEG")) {
				if (!imgPathMap.containsKey(file.getName())) {
					imgPathMap.put(file.getName(), file.getAbsolutePath());
					// System.out.println(file.getName()+" ");
					// System.out.println(file.getAbsolutePath());
				}
			} else if (file.getAbsolutePath().endsWith(".m4a") || file.getAbsolutePath().endsWith(".M4A")) {
				if (!imgPathMap.containsKey(file.getName())) {
					imgPathMap.put(file.getName(), file.getAbsolutePath());
					// System.out.println(file.getName()+" ");
					// System.out.println(file.getAbsolutePath());
				}
			} else if (file.getAbsolutePath().endsWith(".mp4") || file.getAbsolutePath().endsWith(".MP4")) {
				if (!imgPathMap.containsKey(file.getName())) {
					imgPathMap.put(file.getName(), file.getAbsolutePath());
					// System.out.println(file.getName()+" ");
					// System.out.println(file.getAbsolutePath());
				}
			}

		}
		// System.out.println();
	}

	public static void uploadToBlob(String fileName, String filePath, String fileType) throws Exception {

		URL url = new URL(URL + fileName + "?" + TOKEN);

		HttpsURLConnection conn = getHttpURLConnection(url, "PUT", fileType);
		conn.setDoOutput(true);

		// Index definition
		OutputStream osw = conn.getOutputStream();

		File f = new File(filePath);
		FileInputStream fis = new FileInputStream(f);
		byte[] buf = new byte[1024];
		int numRead;
		while ((numRead = fis.read(buf)) >= 0) {
			osw.write(buf, 0, numRead);
		}

		System.out.println(conn.getResponseCode());
		fis.close();
		osw.close();
		System.out.println("https://fankickuat.blob.core.windows.net/images/" + fileName);
	}

	public static HttpsURLConnection getHttpURLConnection(URL url, String method, String fileType) throws IOException {
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type", fileType);// "image/jpeg"
		connection.setRequestProperty("x-ms-blob-type", "BlockBlob");

		return connection;
	}

}
