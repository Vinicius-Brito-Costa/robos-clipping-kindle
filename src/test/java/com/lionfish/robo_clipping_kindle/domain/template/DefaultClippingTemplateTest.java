package com.lionfish.robo_clipping_kindle.domain.template;

import com.lionfish.robo_clipping_kindle.domain.clipping.Clipping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class DefaultClippingTemplateTest {

    List<String> clippingSample = Arrays.asList(
            "Trabalhe 4 horas por semana (Timothy Ferriss)",
            "Seu destaque na página 15 | posição 225-229 | Adicionado: segunda-feira, 24 de janeiro de 2022 22:23:54",
            "Suas decisões se alterariam se a aposentadoria não fosse uma opção? Que tal se você pudesse ter uma miniaposentadoria para experimentar o seu plano de vida adiada antes de trabalhar 40 anos em favor dele? É realmente necessário trabalhar como um escravo para viver como um milionário?");

    @Test
    void formattingClippingIsOK(){
        Clipping clipping = new DefaultClippingTemplate().formatClipping(clippingSample);
        Assertions.assertNotNull(clipping);
        Assertions.assertNotNull(clipping.getDate());
        Assertions.assertNotNull(clipping.getPage());
        Assertions.assertNotNull(clipping.getPosition());
        Assertions.assertNotNull(clipping.getTitle());
        Assertions.assertNotNull(clipping.getHighlight());
        Assertions.assertTrue(clipping.getTitle().length() > 0);
        Assertions.assertTrue(clipping.getHighlight().length() > 0);
        Assertions.assertEquals(clippingSample.get(0), clipping.getTitle());
        Assertions.assertEquals(clippingSample.get(2), clipping.getHighlight());
    }
}
