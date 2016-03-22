package com.copo.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Junbang Huang
 */
public final class OsUtil {

    private static final Logger logger = LoggerFactory.getLogger(OsUtil.class);

    private OsUtil() {

    }

    /**
     * create recursive directories
     *
     * @param filePath
     *
     * @return boolean
     *
     * @throws
     * @Description: make directory
     */
    public static boolean makeDirs(final String filePath) {
        File f = new java.io.File(filePath);
        if (!f.exists()) {
            return f.mkdirs();
        }

        return true;
    }

    /**
     * @param filePathString
     *
     * @return boolean
     *
     * @throws Exception
     * @Description: does File or Derectory exists
     * @author junbanghuang
     * @date 2013-6-13
     */
    public static boolean isFileExist(final String filePathString) throws Exception {

        File f = new File(filePathString);
        return f.exists();
    }

    /**
     * @param pathElements
     *
     * @return boolean
     *
     * @throws Exception
     * @Description: JOIN PATH
     * @author junbanghuang
     * @date 2013-6-13
     */
    public static String pathJoin(final String... pathElements) {

        final String path;

        if (pathElements == null || pathElements.length == 0) {
            path = File.separator;

        } else {

            final StringBuffer sb = new StringBuffer();

            for (final String pathElement : pathElements) {

                if (pathElement.length() > 0) {
                    sb.append(pathElement);
                    sb.append(File.separator);
                }
            }

            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            path = sb.toString();
        }

        return (path);
    }

    /**
     * get File path relating to Folder's path
     * <p/>
     * returns null if file isn't relative to folder
     */
    public static String getRelativePath(File file, File folder) {

        String filePath = file.getAbsolutePath();
        String folderPath = folder.getAbsolutePath();

        if (filePath.startsWith(folderPath)) {
            return filePath.substring(folderPath.length() + 1);
        } else {
            return null;
        }
    }

    /**
     * @param src
     * @param dest
     *
     * @return void
     *
     * @Description: transfer file
     * @author junbanghuang
     * @date 2013-6-20
     */
    public static void transferFile(File src, File dest) throws Exception {

        // 删除文件
        // LOGGER.info("start to remove download file: " + ""
        // + dest.getAbsolutePath());
        if (dest.exists()) {
            dest.delete();
        }

        // 转移临时下载文件至下载文件夹
        FileUtils.copyFile(src, dest);
    }

    /**
     * @param src
     * @param dest
     *
     * @return void
     *
     * @Description: ATOM File transfer with retry mechanism
     * @author junbanghuang
     * @date 2013-6-20
     */
    public static void transferFileAtom(File src, File dest, boolean isDeleteSource) throws Exception {

        // File that needs to lock
        File lockFile = new File(dest + ".lock");
        FileOutputStream outStream = null;
        FileLock lock = null;

        try {
            outStream = new FileOutputStream(lockFile);
            FileChannel channel = outStream.getChannel();
            try {

                int tryTime = 0;
                while (tryTime < 3) {

                    lock = channel.tryLock();
                    if (lock != null) {

                        if (dest.exists()) {
                            // is eqaul
                            if (FileUtils.isFileEqual(src, dest)) {
                                // delete source
                                if (isDeleteSource) {
                                    src.delete();
                                }
                                break;
                            }
                        }

                        logger.debug("start to replace " + src.getAbsolutePath() + " to " + dest.getAbsolutePath());

                        // transfer
                        transferFile(src, dest);

                        // delete source
                        if (isDeleteSource) {
                            src.delete();
                        }

                        break;
                    }

                    logger.warn("try lock failed. sleep and try " + tryTime);
                    tryTime++;

                    try {
                        Thread.sleep(1000 * tryTime);
                    } catch (Exception e) {
                    }
                }

            } catch (IOException e) {
                logger.warn(e.toString());
            }

        } catch (FileNotFoundException e) {
            logger.warn(e.toString());

        } finally {

            if (null != lock) {
                try {
                    lock.release();
                } catch (IOException e) {
                    logger.warn(e.toString());
                }
            }
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    logger.warn(e.toString());
                }
            }
        }
    }
}
