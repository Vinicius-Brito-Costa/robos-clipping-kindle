package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.notion.block.CalloutBlock;
import com.lionfish.robo_clipping_kindle.domain.notion.block.HeadingTwoBlock;
import com.lionfish.robo_clipping_kindle.domain.notion.block.IBlock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class NotionServiceTest {

    @Test
    void generateBlocks(){
        List<Clipping> clippings = new ArrayList<>();
        clippings.add(new Clipping("Simulacrum", 10, "10-22", new Date(), "Reality is about perspective"));
        List<IBlock> blocks = NotionService.generateBlocks(clippings);
        Assertions.assertNotNull(blocks);
        Assertions.assertEquals(2, blocks.size());
        Assertions.assertTrue(blocks.get(0) instanceof HeadingTwoBlock);
        Assertions.assertTrue(blocks.get(1) instanceof CalloutBlock);

        clippings.add(new Clipping("Simulacrum", 10, "10-22", new Date(), "Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective "));
        blocks = NotionService.generateBlocks(clippings);
        Assertions.assertNotNull(blocks);
        Assertions.assertEquals(5, blocks.size());
        Assertions.assertTrue(blocks.get(2) instanceof HeadingTwoBlock);
        Assertions.assertTrue(blocks.get(3) instanceof CalloutBlock);
        Assertions.assertTrue(blocks.get(4) instanceof CalloutBlock);
    }
}
