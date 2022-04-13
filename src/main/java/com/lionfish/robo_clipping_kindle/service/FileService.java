package com.lionfish.robo_clipping_kindle.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/***
 * Service responsible for the File flow. Create, Delete and Retrieve.
 */
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private static final Path PATH = Path.of(new File("").getAbsolutePath() + "/chunks");
    
    private FileService(){}

    /***
     * Create files in the /chunks folder
     * @param fileName
     * @param data that will be written in the file
     */
    public static void createFile(String fileName, Object data){
        try {
            Path chunkDir = Files.createDirectories(PATH);
            Path tempFile = Files.createFile(chunkDir.resolve(fileName));
            Files.writeString(tempFile, (String) data);
            logger.info("[Message] File {{}} created at path {{}}.", fileName, tempFile);
        } catch (IOException e) {
            logger.error("[Error] Something went wrong trying to create file {{}}", fileName, e);
        }
    }

    /***
     * Returns file from the /chunks folder as a byte[] using its name
     * @param fileName full name(including the file extension)
     * @return
     */
    public static byte[] getFileAsBytes(String fileName){
        byte[] file = null;
        try {
            file = Files.readAllBytes(PATH.resolve(fileName));
            logger.info("[Message] Successfuly retrieved file {{}} as byte[]", fileName);
        } catch (IOException e) {
            logger.error("[Error] Something went wrong trying to retrieve file {{}} as byte[]", fileName, e);
        }
        return file;
    }

    /***
     * Delete a file from the /chunks folder using its name
     * @param fileName full name(including the file extension)
     */
    public static void deleteFile(String fileName){
        try {
            Files.delete(PATH.resolve(fileName));
            logger.info("[Message] Successfuly deleted file {{}}", fileName);
        } catch (IOException e) {
            logger.error("[Error] Something went wrong trying to delete file {{}}", fileName, e);
        }
    }
}
