package com.lionfish.robo_clipping_kindle.domain.notion;

import com.lionfish.robo_clipping_kindle.domain.notion.block.*;
import com.lionfish.robo_clipping_kindle.domain.notion.page.*;

import java.util.UUID;

public class NotionPageBuilder {

    private PageObject page;

    /***
     * Create a page builder
     */
    public NotionPageBuilder(){
        page = new PageObject();
        this.page.setObject("page");
        this.page.setId(UUID.randomUUID().toString());
    }

    public NotionPageBuilder setTitle(String title){
        this.page.setProperties(new Property(new Title[]{new Title("text", new Text(title))}));
        return this;
    }

    public NotionPageBuilder setIcon(String icon){
        this.page.setIcon(new EmojiObject(icon));
        return this;
    }

    public NotionPageBuilder setCover(String cover){
        this.page.setCover(new CoverObject(cover));
        return this;
    }

    public NotionPageBuilder setChildren(IBlock[] blocks){
        this.page.setChildren(blocks);
        return this;
    }

    public NotionPageBuilder setParent(String parentId){
        this.page.setParent(new PageParent(parentId));
        return this;
    }

    public PageObject build(){
        return this.page;
    }
}
