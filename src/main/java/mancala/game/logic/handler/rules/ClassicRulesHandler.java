package mancala.game.logic.handler.rules;

import mancala.game.logic.state.TurnState;

/**
 * Created by Alexander on 12/13/2023
 */
public class ClassicRulesHandler implements IRulesHandler {

    CaptureRule captureRule = new ClassicCaptureRule();
    ExtraTurnRule extraTurnRule = new ClassicExtraTurnRule();

    @Override
    public TurnState checkFinalStoneRules(TurnState state) {
        if (captureRule.check(state)) {
            return captureRule.enact(state);
        }
        if (extraTurnRule.check(state)) {
            return extraTurnRule.enact(state);
        }
        return null;
    }

}
