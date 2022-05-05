package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.response.DownloadResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadDocxCommandTest {
    private String okPayload = "A Philosophy of Software Design (John Ousterhout)\n" +
            "- Seu destaque na página 179 | posição 2739-2747 | Adicionado: sábado, 19 de março de 2022 09:39:58\n" +
            "\n" +
            "Write the comments first I use a different approach to writing comments, where I write the comments at the very beginning: For a new class, I start by writing the class interface comment. Next, I write interface comments and signatures for the most important public methods, but I leave the method bodies empty. I iterate a bit over these comments until the basic structure feels about right. At this point I write declarations and comments for the most important class instance variables in the class. Finally, I fill in the bodies of the methods, adding implementation comments as needed. While writing method bodies, I usually discover the need for additional methods and instance variables. For each new method I write the interface comment before the body of the method; for instance variables I fill in the comment at the same time that I write the variable declaration. When the code is done, the comments are also done. There is never a backlog of unwritten comments.\n" +
            "==========\n";

    @Test
    void buildBook(){
        DownloadResponse response = new DownloadDocxCommand().execute(okPayload);
        Assertions.assertEquals(1, response.getBookCount());
        Assertions.assertNotNull(response.getResult());
        Assertions.assertEquals(5, countMatches((String) response.getResult(), "<div"));
        Assertions.assertEquals(1, countMatches((String) response.getResult(), "<br/>"));
        Assertions.assertEquals(1, countMatches((String) response.getResult(), "<h1"));
        Assertions.assertEquals(2, countMatches((String) response.getResult(), "<h2"));
    }

    public static int countMatches(String text, String pattern)
    {
        if (!StringUtils.hasText(text) || !StringUtils.hasText(pattern)) {
            return 0;
        }

        Matcher matcher = Pattern.compile(pattern).matcher(text);

        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }
}
