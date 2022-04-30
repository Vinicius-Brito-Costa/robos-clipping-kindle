package com.lionfish.robo_clipping_kindle.command.notion;

import com.lionfish.robo_clipping_kindle.command.notion.block.IBlock;
import com.lionfish.robo_clipping_kindle.command.notion.block.Text;
import com.lionfish.robo_clipping_kindle.command.notion.page.*;

import java.util.UUID;

public class BuildPage {

    private PageObject page;

    /***
     * Create a page builder
     */
    public BuildPage(){
        page = new PageObject();
        this.page.setObject("page");
        this.page.setId(UUID.randomUUID().toString());
    }

    public BuildPage setTitle(String title){
        this.page.setProperties(new Property(new Title[]{new Title("text", new Text(title))}));
        return this;
    }

    public BuildPage setIcon(String icon){
        this.page.setIcon(new EmojiObject(icon));
        return this;
    }

    public BuildPage setCover(String cover){
        this.page.setCover(new CoverObject(cover));
        return this;
    }

    public BuildPage setChildren(IBlock[] blocks){
        this.page.setChildren(blocks);
        return this;
    }

    public BuildPage setParent(String parentId){
        this.page.setParent(new PageParent(parentId));
        return this;
    }

    public PageObject build(){
        return this.page;
    }
}
