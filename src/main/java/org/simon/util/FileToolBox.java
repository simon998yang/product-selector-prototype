package org.simon.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A toolbox for file manipulation
 * 
 * @author Simon Yang
 */
public class FileToolBox {

	/**
	 * Commons Logging instance.
	 */
	protected static Log logger = LogFactory.getLog(FileToolBox.class);

	/**
	 * Appends a String to a file fileName The file to update content the String to append
	 */
	public static void append(String fileName, String content) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
		out.write(content + "\r\n");
		out.close();
	}

	/**
	 * Copy a file (or a dir) to another one recursively
	 * 
	 * @param src
	 *            The file to copy
	 * @param src
	 *            The dir where to copy the file
	 */
	public static void copy(File src, File dst) throws IOException {
		if (src.isDirectory()) {
			dst.mkdir();

			String[] dirList = src.list();
			for (int i = 0; i < dirList.length; i++) {
				String entry = dirList[i];
				copy(new File(src, entry), new File(dst, entry));
			}
		} else {
			FileInputStream in = new FileInputStream(src);
			FileOutputStream out = new FileOutputStream(dst);

			int c;
			byte[] buf = new byte[32768];
			while ((c = in.read(buf)) > 0)
				out.write(buf, 0, c);

			in.close();
			out.close();
		}
	}

	/**
	 * Create a directory with the given <b>full path name</b>. If the directory already exist then it is overriden.
	 * 
	 * @param directoryName
	 *            The directory to create (path)
	 * @exception IOException
	 */
	public static File createDirectory(String directoryName) throws IOException {
		File newFile = new File(directoryName);
		boolean result = newFile.mkdirs();
		if (result == false && !newFile.exists()) {
			throw new IOException("failed to create directory " + directoryName);
		}
		return newFile;
	}

	/**
	 * Create a file with the given <b>full path name</b>. If the file already exist then it is overriden.
	 * 
	 * @param filename
	 *            The file to create (path + filename)
	 * @exception IOException
	 */
	public static File createFile(String fileName) throws IOException {
		return createFile(fileName, new byte[] {});
	}

	/**
	 * Create a file with the given full path name and the given content. If the file already exist then it is
	 * overriden.
	 * 
	 * @param filename
	 *            The file to create (path + filename)
	 * @param content
	 *            The content of the file
	 * @exception java.io.IOException
	 */
	public static File createFile(String filename, byte[] content) throws IOException {
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(content);
		fos.flush();
		fos.close();
		return f;
	}

	/**
	 * Create a file with the given full path name and the given content. If the file already exist then it is
	 * overriden. The content will be stored using the UTF-8 encoding style.
	 * 
	 * @param filename
	 *            The file to create (path + filename)
	 * @param content
	 *            The content of the file
	 * @exception java.io.IOException
	 */
	public static File createFile(String filename, String content) throws IOException {
		return createFile(filename, content.getBytes("UTF8"));
	}

	/**
	 * Delete the specified directory and its content.
	 * 
	 * @param dir
	 *            the dir to delete
	 */
	public static void deleteDirectory(File dir) {
		deleteDirectoryContent(dir);
		dir.delete();
	}

	/**
	 * Delete the content of the specified directory.
	 * 
	 * @param dir
	 *            java.io.File
	 */
	public static void deleteDirectoryContent(File dir) {
		// check if the param is a dir
		if (!dir.isDirectory())
			return;
		// delete the dir content
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory())
				deleteDirectory(files[i]);
			else
				files[i].delete();
		}
	}

	/**
	 * Delete a file Creation date: (26/09/2002 17:12:27)
	 * 
	 * @param fileName
	 *            java.lang.String
	 * @author Stéphane Van Espen
	 */
	public static void deleteFile(String fileName) {
		try {
			File f = getExistingFile(fileName);
			if (!f.isDirectory() && f.exists())
				f.delete();
		} catch (FileNotFoundException e) { // do nothing. File already removed
		}
	}

	/**
	 * Search for a file in the classpath used by the classloader of the specified class and its parents
	 * 
	 * @param c
	 *            The classloader which loaded this class is used
	 * @param filename
	 *            The file to search
	 * @return The file pathname found
	 */
	public static String findInClasspath(Class c, String filename) {
		ClassLoader loader = c.getClassLoader();
		URL url = loader.getResource(filename);
		if (url == null)
			return null;
		return url.getFile();
	}

	public static InputStream findResourceInClasspath(Class c, String filename) {
		ClassLoader loader = c.getClassLoader();
		InputStream stream = loader.getResourceAsStream(filename);
		return stream;
	}

	public static InputStream findResourceInClasspath(String filename) {
		return findResourceInClasspath(FileToolBox.class, filename);
	}

	/**
	 * Search for a file in the classpath used by the current classloader and its parents
	 * 
	 * @param filename
	 *            The file to search
	 * @return The file pathname found
	 */
	public static String findInClasspath(String filename) {
		return findInClasspath(FileToolBox.class, filename);
	}

	/**
	 * Returns the entire list of file with a certain extension, in a directory ( and in the sub hierarchy).
	 *
	 * @return java.lang.String[]
	 * @param absolutePath
	 *            java.lang.String
	 * @param extension
	 *            java.lang.String
	 */
	public static String[] getAllFilesWithExtension(String absolutePath, String extension) {
		if (absolutePath == null || extension == null) {
			return new String[0];
		}

		List result = new ArrayList();
		// Research in absolutePath.
		File root = new File(absolutePath);
		File[] rootListFile = root.listFiles();
		if (rootListFile != null) {
			for (int j = 0; j < rootListFile.length; j++) {
				if (hasThisExtension(rootListFile[j], extension)) {
					result.add(rootListFile[j].getAbsolutePath());
				}
			}
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("\nThere is no file with extension '" + extension + "' in : " + absolutePath);
			}
		}

		// Research in sub folder.
		String[] directoryList = getDirectoryHierarchy(absolutePath);
		if (directoryList != null) {
			for (int i = 0; i < directoryList.length; i++) {
				File folder = new File(directoryList[i]);
				File[] listFile = folder.listFiles();
				for (int j = 0; j < listFile.length; j++) {
					if (hasThisExtension(listFile[j], extension)) {
						result.add(listFile[j].getAbsolutePath());
					}
				}
			}
		}

		return (String[]) result.toArray(new String[0]);
	}

	/**
	 * This method returns the list of the directories in the root folder. If the file doesn't exist null is returned.
	 *
	 * Creation date: (11/20/2002 10:19:33 AM)
	 * 
	 * @return java.lang.String[]
	 * @param absolutePath
	 *            java.lang.String
	 */
	public static String[] getDirectories(String absolutePath) {
		File root = new File(absolutePath);
		int j = 0;
		if (root.exists()) {
			File[] list = root.listFiles();
			String[] result = new String[list.length];
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					result[j] = list[i].getAbsolutePath();
					j++;
				}
			}
			return result;
		} else
			return null;

	}

	/**
	 * This method will return the directory identified by its <code>absolutePath</code>. If this directory does not
	 * exist yet, it is created at this moment and returned.
	 *
	 * @param absolutePath
	 *            String
	 * @return File The directory.
	 * @exception IOException
	 */
	public static File getDirectory(String absolutePath) throws IOException {
		File newDir = new File(absolutePath);
		if (!newDir.exists())
			return createDirectory(absolutePath);
		else
			return newDir;
	}

	/**
	 * Returns the sub hierarchy of this directory. . Creation date: (11/20/2002 11:15:06 AM)
	 * 
	 * @return java.lang.String[]
	 * @param absolutePath
	 *            java.lang.String
	 */
	public static String[] getDirectoryHierarchy(String absolutePath) {
		List directoryList = new ArrayList();
		directoryList = getSubDirectories(absolutePath);
		return (String[]) directoryList.toArray(new String[0]);
	}

	/**
	 * This method will return any file located in a directory known by the classpath. If this file does not exist, an
	 * exception is thrown
	 *
	 * @param fileName
	 *            String The file name (f.i.: description.xml).
	 * @return File The file.
	 * @exception FileNotFoundException
	 */
	public static File getExistingFile(String fileName) throws FileNotFoundException {
		String path = findInClasspath(fileName);
		if (path != null) {
			return new File(path);
		} else
			throw new FileNotFoundException("Could not find file for path " + path);
	}

	/**
	 * This method will return the file identified by its <code>absolutePath</code>. If this file does not exist yet, it
	 * is created at this moment and returned.
	 *
	 * @param absolutePath
	 *            String
	 * @return File The file.
	 * @exception IOException
	 */
	public static File getFile(String absolutePath) throws IOException {
		String fileName = absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1);
		try {
			return getExistingFile(fileName);
		} catch (FileNotFoundException e) {
			return createFile(absolutePath);
		}
	}

	/**
	 * Return the content of a file as a byte[].
	 * 
	 * @param filename
	 *            The file and path name requested
	 * @return byte[] The file content
	 * @exception java.io.IOException
	 */
	public static byte[] getFileContent(String filename) throws IOException {
		FileInputStream fis = new FileInputStream(new File(filename));
		int size = fis.available();
		byte content[] = new byte[size];
		for (int cpt = 0; cpt < size; cpt += fis.read(content, cpt, size - cpt))
			;
		fis.close();
		return content;
	}

	/**
	 * Return the content of a file as a byte[].
	 * 
	 * @return byte[] The file content
	 * @param filename
	 *            The file and path name requested
	 * @param alternativeContent
	 *            The byte[] to return if the file doesn't exist
	 * @exception java.io.IOException
	 */
	public static byte[] getFileContent(String filename, byte[] alternativeContent) throws IOException {
		try {
			return getFileContent(filename);
		} catch (FileNotFoundException fnfe) {
			return alternativeContent;
		}
	}

	/**
	 * Return the content of a file as a String.
	 * 
	 * @param filename
	 *            The file and path name requested
	 * @return byte[] The file content
	 * @exception java.io.IOException
	 */
	public static String getFileContentAsString(String filename) throws IOException {
		return new String(getFileContent(filename));
	}

	/**
	 * Return the content of a file as a String.
	 * 
	 * @param filename
	 *            The file and path name requested
	 * @param alternativeContent
	 *            The String to return if the file doesn't exist
	 * @return byte[] The file content
	 * @exception java.io.IOException
	 */
	public static String getFileContentAsString(String filename, String alternativeContent) throws IOException {
		try {
			return getFileContentAsString(filename);
		} catch (FileNotFoundException fnfe) {
			return alternativeContent;
		}
	}

	/**
	 * Returns the name of the given without it's extension.
	 * 
	 * @return java.lang.String
	 * @param file
	 *            java.io.File
	 */
	public static String getFileName(java.io.File file) {
		int indexOfDot = file.getName().indexOf(".");
		if (indexOfDot == -1)
			return file.getName();
		else {
			return file.getName().substring(0, indexOfDot);

		}
	}

	/**
	 * Returns a buffered reader for the given file in the given encoding
	 * 
	 * @return java.io.BufferedReader
	 * @param file
	 *            java.io.File
	 * @param encoding
	 *            java.lang.String
	 */
	public static java.io.BufferedReader getFileReader(java.io.File file) throws FileNotFoundException {
		BufferedReader in = null;

		InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
		in = new BufferedReader(reader);

		return in;
	}

	/**
	 * Returns a buffered reader for the given file in the given encoding
	 * 
	 * @return java.io.BufferedReader
	 * @param file
	 *            java.io.File
	 * @param encoding
	 *            java.lang.String
	 */
	public static java.io.BufferedReader getFileReader(java.io.File file, String encoding)
			throws UnsupportedEncodingException, FileNotFoundException {
		BufferedReader in = null;

		InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encoding);
		in = new BufferedReader(reader);

		return in;
	}

	/**
	 * returns a list of a sub directory.
	 *
	 * Creation date: (11/20/2002 10:59:12 AM)
	 * 
	 * @return java.util.List
	 * @param absolutePath
	 *            java.lang.String
	 */
	private static List getSubDirectories(String absolutePath) {
		String[] list = getDirectories(absolutePath);
		List result = new ArrayList();
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				if (list[i] != null) {
					result.add(list[i].toString());
					List subList = getSubDirectories(list[i].toString());
					if (!subList.isEmpty()) {
						for (int j = 0; j < subList.size(); j++) {
							result.add(subList.get(j));
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Tell if the name of file <code>file</code> is ending by extension <code>extension</code>.
	 */
	public static boolean hasThisExtension(File file, String extension) {
		String fn = file.getAbsolutePath();
		int ind = fn.lastIndexOf('.');
		return fn.substring(ind + 1).equals(extension);
	}

	/**
	 * Tell if the name of file <code>file</code> contains the signature <code>signature</code>.
	 */
	public static boolean hasThisSignature(File file, String signature) {
		String fn = file.getAbsolutePath();
		if (signature == null) {
			return false;
		}
		fn = fn.toLowerCase();
		int ind = fn.indexOf(signature);
		if (ind != -1) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Read a bunch of lines corresponding to the bunch size.
	 * 
	 * @return java.util.List
	 * @param br
	 *            java.io.BufferedReader
	 * @param bunchSize
	 *            int
	 */
	public static java.util.List readLines(BufferedReader br, int bunchSize) throws IOException {
		List msgBunch = new ArrayList();
		int nbLines = 0;
		String line;
		while ((line = br.readLine()) != null) {
			msgBunch.add(line);
			nbLines++;
			if (nbLines == bunchSize)
				break;
		}
		return msgBunch;
	}

	/**
	 * This method transforms each line of a file in a String object of a List.
	 *
	 * Creation date: (10/18/2002 4:57:33 PM)
	 * 
	 * @return java.util.List
	 * @param p_FileName
	 *            java.lang.String
	 * @exception java.io.IOException
	 *                The exception description.
	 */
	public static List readLines(String p_FileName) throws java.io.IOException {
		FileInputStream csvFile = new FileInputStream(FileToolBox.getExistingFile(p_FileName));
		List inputList = new ArrayList();
		InputStreamReader inReader = new InputStreamReader(csvFile);

		BufferedReader inBuffer = new BufferedReader(inReader);
		while (inBuffer.ready()) {
			String l_String = inBuffer.readLine();
			inputList.add(l_String);
		}

		return inputList;
	}

	/**
	 * Rename a file and throw a java.io.IOException in all cases where the rename fail.
	 * 
	 * @param from
	 *            The original file name
	 * @param to
	 *            The destination file name
	 */
	public static void renameFile(String from, String to) throws IOException {
		boolean success = new File(from).renameTo(new File(to));
		if (!success)
			throw new IOException("Renaming file " + from + " to " + to + " failed");
	}

}
