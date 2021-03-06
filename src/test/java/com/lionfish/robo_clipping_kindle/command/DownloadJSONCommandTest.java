package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.book.Book;
import com.lionfish.robo_clipping_kindle.domain.response.DownloadResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DownloadJSONCommandTest {

    private String okPayload = "A Philosophy of Software Design (John Ousterhout)\n" +
            "- Seu destaque na página 179 | posição 2739-2747 | Adicionado: sábado, 19 de março de 2022 09:39:58\n" +
            "\n" +
            "Write the comments first I use a different approach to writing comments, where I write the comments at the very beginning: For a new class, I start by writing the class interface comment. Next, I write interface comments and signatures for the most important public methods, but I leave the method bodies empty. I iterate a bit over these comments until the basic structure feels about right. At this point I write declarations and comments for the most important class instance variables in the class. Finally, I fill in the bodies of the methods, adding implementation comments as needed. While writing method bodies, I usually discover the need for additional methods and instance variables. For each new method I write the interface comment before the body of the method; for instance variables I fill in the comment at the same time that I write the variable declaration. When the code is done, the comments are also done. There is never a backlog of unwritten comments.\n" +
            "==========\n" +
            "A Philosophy of Software Design (John Ousterhout)\n" +
            "- Seu destaque na página 180 | posição 2748-2751 | Adicionado: sábado, 19 de março de 2022 09:40:28\n" +
            "\n" +
            "The comments-first approach has three benefits. First, it produces better comments. If you write the comments as you are designing the class, the key design issues will be fresh in your mind, so it’s easy to record them. It’s better to write the interface comment for each method before its body, so you can focus on the method’s abstraction and interface without being distracted by its implementation.\n" +
            "==========\n" +
            "A Philosophy of Software Design (John Ousterhout)\n" +
            "- Seu destaque na página 179 | posição 2739-2747 | Adicionado: sábado, 19 de março de 2022 09:39:58\n" +
            "\n" +
            "Write the comments first I use a different approach to writing comments, where I write the comments at the very beginning: For a new class, I start by writing the class interface comment. Next, I write interface comments and signatures for the most important public methods, but I leave the method bodies empty. I iterate a bit over these comments until the basic structure feels about right. At this point I write declarations and comments for the most important class instance variables in the class. Finally, I fill in the bodies of the methods, adding implementation comments as needed. While writing method bodies, I usually discover the need for additional methods and instance variables. For each new method I write the interface comment before the body of the method; for instance variables I fill in the comment at the same time that I write the variable declaration. When the code is done, the comments are also done. There is never a backlog of unwritten comments.\n" +
            "==========\n" +
            "A Philosophy of Software Design (John Ousterhout)\n" +
            "The comments-first approach has three benefits. First, it produces better comments. If you write the comments as you are designing the class, the key design issues will be fresh in your mind, so it’s easy to record them. It’s better to write the interface comment for each method before its body, so you can focus on the method’s abstraction and interface without being distracted by its implementation.\n" +
            "==========";
    private String okPayloadBook = "A Philosophy of Software Design (John Ousterhout)";

    @Test
    @SuppressWarnings("unchecked")
    void downloadCommandIsOK(){
        DownloadJSONCommand downloadJSONCommand = new DownloadJSONCommand();
        DownloadResponse result = downloadJSONCommand.execute(okPayload);
        Assertions.assertEquals(1, result.getBookCount());
        Assertions.assertEquals(3, ((List<Book>) result.getResult()).get(0).getClippingCount());
    }
}
