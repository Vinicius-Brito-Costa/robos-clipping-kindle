package com.lionfish.robo_clipping_kindle.controller;

import javax.websocket.server.PathParam;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ClippingController {
    
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
     * @return byte[]
     */
    @PostMapping("/export")
    public Object exportClippings(@PathParam(value = "command") String command, @RequestBody String data){
        ICommand comm = CommandMapEnum.getCommandClass(command);
        if(data == null || comm == null){
            return ResponseEntity.badRequest().body("");
        }
        
        return comm.execute(data);
    }
}
