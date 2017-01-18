package com.chinawiserv.wsmp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

	public static void deleteDir(File dir) {
		File[] filelist = dir.listFiles();
		for (File file : filelist) {
			if (file.isFile()) {
				file.delete();
			} else {
				deleteDir(file);
			}
		}
		dir.delete();
	}

	public static void copy(File origin, File newfile) throws FileNotFoundException, IOException {
		if (!newfile.getParentFile().exists()) {
			newfile.getParentFile().mkdirs();
		}

		try (FileInputStream fis = new FileInputStream(origin); FileOutputStream fos = new FileOutputStream(newfile);) {
			byte[] buf = new byte[2048];
			int read;
			while ((read = fis.read(buf)) != -1) {
				fos.write(buf, 0, read);
			}
		}
	}

	public static void writeFile(String filename, String contentStr, String charset)
			throws FileNotFoundException, IOException {
		byte[] content = contentStr.getBytes(charset);
		try (FileOutputStream fos = new FileOutputStream(filename);) {
			fos.write(content);
		}
	}

	public static void writeFile(File file, String contentStr, String charset)
			throws FileNotFoundException, IOException {
		byte[] content = contentStr.getBytes(charset);
		try (FileOutputStream fos = new FileOutputStream(file);) {
			fos.write(content);
		}
	}

	public static void writeFileWithParent(String filename, String contentStr, String charset)
			throws FileNotFoundException, IOException {
		File file = new File(filename);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		byte[] content = contentStr.getBytes(charset);
		try (FileOutputStream fos = new FileOutputStream(file);) {
			fos.write(content);
		}
	}

	public static void writeFileWithParent(File file, String contentStr, String charset)
			throws FileNotFoundException, IOException {
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		byte[] content = contentStr.getBytes(charset);
		try (FileOutputStream fos = new FileOutputStream(file);) {
			fos.write(content);
		}
	}

	public static void writeFile(String filename, byte[] content) throws FileNotFoundException, IOException {
		try (FileOutputStream fos = new FileOutputStream(filename);) {
			fos.write(content);
		}
	}

	public static void writeFile(File file, byte[] content) throws FileNotFoundException, IOException {
		try (FileOutputStream fos = new FileOutputStream(file);) {
			fos.write(content);
		}
	}

	public static void writeFileWithParent(String filename, byte[] content) throws FileNotFoundException, IOException {
		File file = new File(filename);
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		try (FileOutputStream fos = new FileOutputStream(file);) {
			fos.write(content);
		}
	}

	public static void writeFileWithParent(File file, byte[] content) throws FileNotFoundException, IOException {

		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		try (FileOutputStream fos = new FileOutputStream(file);) {
			fos.write(content);
		}
	}

	public static byte[] readFile(File file) throws IOException {
		try(FileInputStream fis = new FileInputStream(file);){

			byte[] buf = new byte[2048];
			int read;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((read = fis.read(buf)) != -1) {
				bos.write(buf, 0, read);
			}
			return bos.toByteArray();
		}
		
	}

}
