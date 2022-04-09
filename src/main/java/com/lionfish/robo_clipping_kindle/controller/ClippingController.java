package com.lionfish.robo_clipping_kindle.controller;

import java.util.HashMap;

import javax.websocket.server.PathParam;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.controller.Response.ResponseMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ClippingController {

    ICommand responseCommand = CommandMapEnum.getCommandClass("response");
    
    /**
     * Used to separate and format clippings of a file
     * @param byte[] File containing clippings
     * @return String[] Each String is a clipping
     */
    @PostMapping("/get-clippings")
    public String[] getClippings(){
        return new String[]{"Olá, mundo!"};
    }

    /**
     * Export clippings with especified command(eg: Notion, Markdown, Json, etc...)
     * @param String command
     * @param RequestBody string containing all data from 'My Clippings'
     * @return byte[]
     */
    @PostMapping("/export")
    @SuppressWarnings("unchecked")
    @CrossOrigin
    public Object exportClippings(@PathParam(value = "command") String command, @RequestBody String data){
        ICommand comm = CommandMapEnum.getCommandClass(command);
        ResponseMap responseMap;
        if(data == null || comm == null){
            responseMap = ResponseMap.BAD_REQUEST;
            return responseCommand.execute(responseMap);
        }
        HashMap<Object, Object> commandResponse = (HashMap<Object, Object>) comm.execute(data);
        responseMap = ResponseMap.BAD_REQUEST;
        if(commandResponse != null && commandResponse.size() > 0){
            responseMap = ResponseMap.OK;
            responseMap.setResponseDataBody(commandResponse);
        }
        return responseCommand.execute(responseMap);
    }

    @PostMapping("/exportMulti")
    @CrossOrigin
    public Object exportClippingsMulti(@PathParam(value = "command") String command, @RequestBody String data){
        ICommand comm = CommandMapEnum.getCommandClass(command);
        ResponseMap responseMap;
        System.out.println("yea");
        return "responseCommand.execute(responseMap)";
    }
}
