package actions;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;

import com.fasterxml.jackson.databind.node.ObjectNode;

import constants.CardOutput;

import fileio.CardInput;

public abstract class GetHero {
    /**
     */
    public static ObjectNode execute(final CardInput hero) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode heroObject = objectMapper.createObjectNode();

        heroObject.put(CardOutput.MANA.getOutput(), hero.getMana());
        heroObject.put(CardOutput.DESCRIPTION.getOutput(),
                hero.getDescription());

        ArrayNode colors = objectMapper.createArrayNode();
        for (String color : hero.getColors()) {
            colors.add(color);
        }
        heroObject.set(CardOutput.COLORS.getOutput(), colors);

        heroObject.put(CardOutput.NAME.getOutput(), hero.getName());
        heroObject.put(CardOutput.HEALTH.getOutput(), hero.getHealth());

        return heroObject;
    }
}
