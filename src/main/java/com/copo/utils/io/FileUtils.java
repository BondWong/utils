package com.copo.utils.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File Manipulation Tool
 * 
 * @author Junbang Huang
 * @version 2014-3-20
 */
public final class FileUtils extends org.apache.commons.io.FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {

    }

    /**
     * Close File Stream
     * 
     * @param w created by Scott(Jujiang)
     */
    public final static void closeWriter(Writer w) {
        if (w != null)
            try {
                w.close();
            } catch (Exception e) {
            }
    }

    /**
     * Close File Stream
     * 
     * @param r created by Scott(Jujiang)
     */
    public final static void closeReader(Reader r) {
        if (r != null)
            try {
                r.close();
            } catch (Exception e) {
            }
    }

    /**
     * Close File Stream
     * 
     * @param os created by Scott(Jujiang)
     */
    public final static void closeOutputStream(OutputStream os) {
        if (os != null)
            try {
                os.close();
            } catch (Exception e) {
            }
    }

    /**
     * Close File Stream
     * 
     * @param is created by Scott(Jujiang)
     */
    public final static void closeInputStream(InputStream is) {
        if (is != null)
            try {
                is.close();
            } catch (Exception e) {
            }
    }

    /**
     * dependencies：commons-codec-1.4.jaruse md5 to compare <br/>
     * http://blog.csdn.net/very365_1208/article/details/8824033
     * 
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean isFileUpdate(String oldName, String newName) {

        return isFileEqual(new File(oldName), new File(newName));
    }

    /**
     * http://blog.csdn.net/very365_1208/article/details/8824033
     * http://www.avajava.com/tutorials/lessons/whats-a-quick-way
     * -to-tell-if-the-contents-of-two-files-are-identical-or-not.html
     * 
     * @param oldName
     * @param newName
     * @return
     */
    public static boolean isFileEqual(File oldFile, File newFile) {

        try {

            return contentEquals(oldFile, newFile);

        } catch (IOException e) {

            logger.warn(e.toString());
            return false;
        }

    }
}
