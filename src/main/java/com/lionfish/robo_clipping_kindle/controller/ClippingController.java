package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.domain.request.ExportRequestDTO;
import com.lionfish.robo_clipping_kindle.service.ClippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ClippingController {

    private static final Logger logger = LoggerFactory.getLogger(ClippingController.class);

    ICommand responseCommand = CommandMapEnum.getCommandClass("internal-response").getCommandClass();

    /**
     * Download clippings with especified format(eg: Docx, Json, etc...)
     * @param format desired format to process the clippingFile
     * @param exportRequestDTO string containing all data from 'My Clippings'
     * @return ResponseEntity
     */
    @PostMapping("/download/{format}")
    public Object download(@PathVariable(value = "format") String format, @RequestBody ExportRequestDTO exportRequestDTO){
        logger.info("[Message] Download process initiated...");
        return responseCommand.execute(ClippingService.buildResponseMessage(CommandType.DOWNLOAD, format, exportRequestDTO));
    }

    /**
     * Export clippings with especified format(eg: Notion, etc...)
     * @param integration desired format to process the clippingFile
     * @param exportRequestDTO string containing all data from 'My Clippings'
     * @return ResponseEntity
     */
    @PostMapping("/export/{integration}")
    public Object export(@PathVariable(value = "integration") String integration, @RequestBody ExportRequestDTO exportRequestDTO){
        logger.info("[Message] Download process initiated...");
        return responseCommand.execute(ClippingService.buildResponseMessage(CommandType.EXPORT, integration, exportRequestDTO));
    }
}
