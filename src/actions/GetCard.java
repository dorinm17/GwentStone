package actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import constants.CardOutput;
import constants.Environment;
import fileio.CardInput;

public abstract class GetCard {
    /**
     */
    public static ObjectNode execute(final CardInput card) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode cardObject = objectMapper.createObjectNode();

        cardObject.put(CardOutput.MANA.getOutput(), card.getMana());

        boolean environment = false;
        for (Environment e : Environment.values()) {
            if (e.getName().equals(card.getName())) {
                environment = true;
                break;
            }
        }

        if (!environment) {
            cardObject.put(CardOutput.ATTACK_DAMAGE.getOutput(),
                    card.getAttackDamage());
            cardObject.put(CardOutput.HEALTH.getOutput(), card.getHealth());
        }

        cardObject.put(CardOutput.DESCRIPTION.getOutput(),
                card.getDescription());

        ArrayNode colors = objectMapper.createArrayNode();
        for (String color : card.getColors()) {
            colors.add(color);
        }
        cardObject.set(CardOutput.COLORS.getOutput(), colors);

        cardObject.put(CardOutput.NAME.getOutput(), card.getName());

        return cardObject;
    }
}
