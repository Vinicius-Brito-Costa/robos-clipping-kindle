package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.domain.request.DownloadRequest;
import com.lionfish.robo_clipping_kindle.domain.request.IntegrationRequest;
import com.lionfish.robo_clipping_kindle.service.ClippingService;
import com.lionfish.robo_clipping_kindle.service.IntegrationService;
import com.lionfish.robo_clipping_kindle.service.NotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping(value = "/api/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ClippingController {

    private static final Logger logger = LoggerFactory.getLogger(ClippingController.class);

    ICommand responseCommand = CommandMapEnum.getCommandClass("internal-response").getCommandClass();

    /**
     * Download clippings with specified format(eg: Docx, Json, etc...)
     * @param format desired format to process the clipping file
     * @param downloadRequest string containing all data from 'My Clippings'
     * @return ResponseEntity
     */
    @PostMapping("/download/{format}")
    public Object download(@PathVariable(value = "format") String format, @RequestBody DownloadRequest downloadRequest){
        logger.info("[Message] Download process initiated...");
        return responseCommand.execute(ClippingService.buildResponseMessage(CommandType.DOWNLOAD, format, downloadRequest));
    }

    /**
     * Export clippings with specified format(eg: Notion, etc...)
     * @param integration desired integration to export the clipping file
     * @param integrationRequestDTO string containing all data from 'My Clippings'
     * @return ResponseEntity
     */
    @PostMapping("/integration/{integration}")
    public Object export(@PathVariable(value = "integration") String integration, @RequestBody IntegrationRequest integrationRequestDTO){
        logger.info("[Message] Integration process initiated...");
        return responseCommand.execute(IntegrationService.buildResponseMessage(CommandType.INTEGRATION, integration, integrationRequestDTO));
    }
}
