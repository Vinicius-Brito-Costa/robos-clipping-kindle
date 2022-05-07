package com.lionfish.robo_clipping_kindle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lionfish.robo_clipping_kindle.domain.book.Book;
import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import com.lionfish.robo_clipping_kindle.domain.notion.UploadedPages;
import com.lionfish.robo_clipping_kindle.domain.notion.block.CalloutBlock;
import com.lionfish.robo_clipping_kindle.domain.notion.block.HeadingTwoBlock;
import com.lionfish.robo_clipping_kindle.domain.notion.block.IBlock;
import com.lionfish.robo_clipping_kindle.domain.notion.block.WorkspaceParent;
import com.lionfish.robo_clipping_kindle.domain.notion.page.PageObject;
import com.lionfish.robo_clipping_kindle.domain.notion.page.PageObjectDTO;
import com.lionfish.robo_clipping_kindle.domain.response.NotionSearchResponse;
import com.lionfish.robo_clipping_kindle.domain.response.NotionTokenResponse;
import com.lionfish.robo_clipping_kindle.feign.NotionFeignClient;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
class NotionServiceTest {

    private static final String CLIPPING_EXCEED_WORD_COUNT = "Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective Reality is about perspective ";

    @Mock
    private NotionFeignClient notion;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);
        Mockito.when(notion.getToken(anyString(), anyString(), anyString())).thenReturn(new NotionTokenResponse());
        Mockito.when(notion.search(any(), any(), any()))
                .thenReturn(
                        new NotionSearchResponse("test", new PageObjectDTO[]{
                                new PageObjectDTO("test", "test", new WorkspaceParent(true))}));
        Mockito.when(notion.createPage(any(), any())).thenReturn(new PageObjectDTO());
        Mockito.when(notion.addBlockToPage(any(), any(), any())).thenReturn(new Object());

    }


    @Test
    void shouldUploadBookPage(){
        List<Book> books = new ArrayList<>();
        List<Clipping> clippings = new ArrayList<>();
        clippings.add(new Clipping("Think and Pad", 1, "1-10", new Date(), "Le vono"));
        books.add(new Book("Think and Pad", 1, clippings));

        UploadedPages pages = NotionService.uploadBookPages(books,"test", "test", notion);
        Assertions.assertEquals(1, pages.getPages());
        Assertions.assertEquals(1, pages.getClippings());
        Assertions.assertEquals(2, pages.getBlocks());
    }
    @Test
    void shouldUploadBookPageAndPageBlocks(){
        List<Book> books = new ArrayList<>();

        books.add(new Book("Think and Pad", 1, generateClippings()));

        UploadedPages pages = NotionService.uploadBookPages(books,"test", "test", notion);
        Assertions.assertEquals(1, pages.getPages());
        Assertions.assertEquals(1000, pages.getClippings());
        Assertions.assertEquals(2000, pages.getBlocks());
    }

    @Test
    void shouldGenerateBlocks(){
        List<Clipping> clippings = new ArrayList<>();
        clippings.add(new Clipping("Simulacrum", 10, "10-22", new Date(), "Reality is about perspective"));
        List<IBlock> blocks = NotionService.generateBlocks(clippings);

        Assertions.assertNotNull(blocks);
        Assertions.assertEquals(2, blocks.size());
        Assertions.assertTrue(blocks.get(0) instanceof HeadingTwoBlock);
        Assertions.assertTrue(blocks.get(1) instanceof CalloutBlock);
    }

    @Test
    void missingHeaderFeatures(){
        List<Clipping> clippings = new ArrayList<>();
        clippings.add(new Clipping("Simulacrum", 10, "", new Date(), "Reality is about perspective"));
        Date mockDate = mock(Date.class);
        when(mockDate.toString()).thenReturn("");
        clippings.add(new Clipping("Simulacrum", 10, "", mockDate, "Reality is about perspective"));
        clippings.add(new Clipping("Simulacrum", null, "10", new Date(), "Reality is about perspective"));
        clippings.add(new Clipping("Simulacrum", 1, null, new Date(), "Reality is about perspective"));
        List<IBlock> blocks = NotionService.generateBlocks(clippings);

        Assertions.assertNotNull(blocks);
        Assertions.assertEquals(8, blocks.size());
        Assertions.assertTrue(blocks.get(0) instanceof HeadingTwoBlock);
        Assertions.assertTrue(blocks.get(1) instanceof CalloutBlock);
        Assertions.assertTrue(blocks.get(2) instanceof HeadingTwoBlock);
        Assertions.assertTrue(blocks.get(3) instanceof CalloutBlock);
        Assertions.assertTrue(blocks.get(4) instanceof HeadingTwoBlock);
        Assertions.assertTrue(blocks.get(5) instanceof CalloutBlock);
        Assertions.assertTrue(blocks.get(6) instanceof HeadingTwoBlock);
        Assertions.assertTrue(blocks.get(7) instanceof CalloutBlock);
    }

    @Test
    void shouldGenerateBlocksBasedOnClippingSize(){
        List<Clipping> clippings = new ArrayList<>();
        clippings.add(new Clipping("Simulacrum", 10, "10-22", new Date(), CLIPPING_EXCEED_WORD_COUNT));
        List<IBlock> blocks = NotionService.generateBlocks(clippings);

        Assertions.assertNotNull(blocks);
        Assertions.assertEquals(3, blocks.size());
        Assertions.assertTrue(blocks.get(0) instanceof HeadingTwoBlock);
        Assertions.assertTrue(blocks.get(1) instanceof CalloutBlock);
        Assertions.assertTrue(blocks.get(2) instanceof CalloutBlock);
    }

    List<Clipping> generateClippings(){
        List<Clipping> clippings = new ArrayList<>();

        for(int index = 0; index <= NotionService.MAX_BLOCK_COUNT; index++){
            clippings.add(new Clipping("Think and Pad", 1, "1-10", new Date(), "Le vono - " + index));
        }
        return clippings;
    }
}
