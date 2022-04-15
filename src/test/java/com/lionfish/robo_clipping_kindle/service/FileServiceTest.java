package com.lionfish.robo_clipping_kindle.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lionfish.robo_clipping_kindle.domain.file.ClippingFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class FileServiceTest {

    @Test
    void fileCreationIsOK(){
        String fileName = UUID.randomUUID().toString();
        String fileData = UUID.randomUUID().toString();
        FileService.createFile(fileName, fileData.getBytes(StandardCharsets.UTF_8));
        byte[] createdFileData = FileService.getFileAsBytes(fileName);
        Assertions.assertNotNull(createdFileData);
        Assertions.assertEquals(new String(createdFileData), fileData);
        FileService.deleteFile(fileName);
    }

    @Test
    void fileCreationIsNotOK(){
        String fileName = "/:";
        FileService.createFile(fileName, null);
        Assertions.assertNull(FileService.getFileAsBytes(fileName));
    }

    @Test
    void fileDeletionIsOK(){
        String fileName = UUID.randomUUID().toString();
        String fileData = UUID.randomUUID().toString();
        FileService.createFile(fileName, fileData.getBytes(StandardCharsets.UTF_8));
        FileService.deleteFile(fileName);
        Assertions.assertNull(FileService.getFileAsBytes(fileName));
    }

    @Test
    void fileRetrieveIsOK() throws IOException {
        String fileName = UUID.randomUUID().toString();
        String fileData = UUID.randomUUID().toString();
        FileService.createFile(fileName, fileData.getBytes(StandardCharsets.UTF_8));
        byte[] createdFileData = FileService.getFileAsBytes(fileName);
        Assertions.assertNotNull(createdFileData);
        Assertions.assertEquals(new String(createdFileData, StandardCharsets.UTF_8), fileData);
        FileService.deleteFile(fileName);

        fileName = UUID.randomUUID().toString();
        ClippingFile clippingFile = new ClippingFile("Hello World!", "Hello World!");
        ObjectMapper mapper = new ObjectMapper();
        FileService.createFile(fileName, mapper.writeValueAsString(clippingFile).getBytes(StandardCharsets.UTF_8));
        createdFileData = FileService.getFileAsBytes(fileName);
        Assertions.assertNotNull(createdFileData);
        Assertions.assertEquals(clippingFile, mapper.readValue(createdFileData, ClippingFile.class));
        FileService.deleteFile(fileName);
    }
}
