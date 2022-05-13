package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.notion.block.WorkspaceParent;
import com.lionfish.robo_clipping_kindle.domain.notion.page.PageObjectDTO;
import com.lionfish.robo_clipping_kindle.domain.request.IntegrationRequest;
import com.lionfish.robo_clipping_kindle.domain.response.IntegrationResponse;
import com.lionfish.robo_clipping_kindle.domain.response.NotionSearchResponse;
import com.lionfish.robo_clipping_kindle.domain.response.NotionTokenResponse;
import com.lionfish.robo_clipping_kindle.feign.NotionFeignClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
class NotionCommandTest {

    private String clippings = "A Philosophy of Software Design (John Ousterhout)\n- Seu destaque na página 239 | posição 3651-3684 | Adicionado: sábado, 19 de março de 2022 11:32:43\nSummary of Red Flags Here are a few of of the most important red flags discussed in this book. The presence of any of these symptoms in a system suggests that there is a problem with the system’s design: Shallow Module: the interface for a class or method isn’t much simpler than its implementation (see pp. 25, 110). Information Leakage: a design decision is reflected in multiple modules (see p. 31). Temporal Decomposition: the code structure is based on the order in which operations are executed, not on information hiding (see p. 32). Overexposure: An API forces callers to be aware of rarely used features in order to use commonly used features (see p. 36). Pass-Through Method: a method does almost nothing except pass its arguments to another method with a similar signature (see p. 46). Repetition: a nontrivial piece of code is repeated over and over (see p. 62). Special-General Mixture: special-purpose code is not cleanly separated from general purpose code (see p. 65). Conjoined Methods: two methods have so many dependencies that its hard to understand the implementation of one without understanding the implementation of the other (see p. 72). Comment Repeats Code: all of the information in a comment is immediately obvious from the code next to the comment (see p. 104). Implementation Documentation Contaminates Interface: an interface comment describes implementation details not needed by users of the thing being documented (see p. 114). Vague Name: the name of a variable or method is so imprecise that it doesn’t convey much useful information (see p. 123). Hard to Pick Name: it is difficult to come up with a precise and intuitive name for an entity (see p. 125). Hard to Describe: in order to be complete, the documentation for a variable or method must be long. (see p. 131). Nonobvious Code: the behavior or meaning of a piece of code cannot be understood easily. (see p. 148). About the Author John Ousterhout is the Bosack Lerner Professor of Computer Science at Stanford University.\n==========\n";

    @InjectMocks
    private NotionCommand notionCommand;

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

    }

    @Test
    void commandExecutionIsOk(){
        IntegrationRequest requestDTO = new IntegrationRequest("test", clippings);
        IntegrationResponse response = (IntegrationResponse) notionCommand.execute(requestDTO);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.getBookCount());
        Assertions.assertEquals(1, response.getClippingCount());
    }
}
