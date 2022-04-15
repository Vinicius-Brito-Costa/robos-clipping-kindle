package com.lionfish.robo_clipping_kindle.domain.template;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DefaultClippingTemplateTest {

    List<String> allOk = Arrays.asList(
            "Trabalhe 4 horas por semana (Timothy Ferriss)",
            "Seu destaque na página 15 | posição 225-229 | Adicionado: segunda-feira, 24 de janeiro de 2022 22:23:54",
            "Suas decisões se alterariam se a aposentadoria não fosse uma opção? Que tal se você pudesse ter uma miniaposentadoria para experimentar o seu plano de vida adiada antes de trabalhar 40 anos em favor dele? É realmente necessário trabalhar como um escravo para viver como um milionário?");

    List<String> onlyTwoClippingInfo = Arrays.asList(
            "Trabalhe 4 horas por semana (Timothy Ferriss)",
            "posição 225-229 | Adicionado: segunda-feira, 24 de janeiro de 2022 22:23:54",
            "Suas decisões se alterariam se a aposentadoria não fosse uma opção? Que tal se você pudesse ter uma miniaposentadoria para experimentar o seu plano de vida adiada antes de trabalhar 40 anos em favor dele? É realmente necessário trabalhar como um escravo para viver como um milionário?");

    List<String> onlyOneClippingInfo = Arrays.asList(
            "Trabalhe 4 horas por semana (Timothy Ferriss)",
            "Adicionado: segunda-feira, 24 de janeiro de 2022 22:23:54",
            "Suas decisões se alterariam se a aposentadoria não fosse uma opção? Que tal se você pudesse ter uma miniaposentadoria para experimentar o seu plano de vida adiada antes de trabalhar 40 anos em favor dele? É realmente necessário trabalhar como um escravo para viver como um milionário?");

    List<String> clippingDateWithFour = Arrays.asList(
            "The Josiah Generation: New Dawn, New Rules, New Rulers (Olly Goldenberg)",
            "- Highlight on Page 23 | Loc. 347-54  | Added on Monday, 23 April 12 22:51:41 GMT+01:00",
            "Children are not simply cute, they are powerful. If we limit their spiritual actions by labelling them");
    @Test
    void formattingClippingIsOK(){
        Clipping clipping = new DefaultClippingTemplate().formatClipping(allOk);
        Assertions.assertNotNull(clipping);
        Assertions.assertNotNull(clipping.getDate());
        Assertions.assertNotNull(clipping.getPage());
        Assertions.assertNotNull(clipping.getPosition());
        Assertions.assertNotNull(clipping.getTitle());
        Assertions.assertNotNull(clipping.getHighlight());
        Assertions.assertTrue(clipping.getTitle().length() > 0);
        Assertions.assertTrue(clipping.getHighlight().length() > 0);
        Assertions.assertEquals(allOk.get(0), clipping.getTitle());
        Assertions.assertEquals(allOk.get(2), clipping.getHighlight());
    }

    @Test
    void formattingClippingWithOnlyTwoInfo(){
        Clipping clipping = new DefaultClippingTemplate().formatClipping(onlyTwoClippingInfo);
        Assertions.assertNotNull(clipping);
        Assertions.assertNull(clipping.getPage());
        Assertions.assertNotNull(clipping.getPosition());
    }

    @Test
    void formattingClippingWithOnlyOneInfo(){
        Clipping clipping = new DefaultClippingTemplate().formatClipping(onlyOneClippingInfo);
        Assertions.assertNotNull(clipping);
        Assertions.assertNull(clipping.getPage());
        Assertions.assertNull(clipping.getPosition());
    }

    @Test
    void englishClippingDateIsOK(){
        Clipping clipping = new DefaultClippingTemplate().formatClipping(clippingDateWithFour);
        Assertions.assertNotNull(clipping);
        Assertions.assertNotNull(clipping.getDate());
    }

    @Test
    void invalidDateIsNotOK(){
        Assertions.assertNull(DefaultClippingTemplate.formatClippingDate("Added on Monday, XX 2022 22:XX:54"));
    }
}
