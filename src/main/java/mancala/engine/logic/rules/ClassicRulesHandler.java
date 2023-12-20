package mancala.engine.logic.rules;

import mancala.engine.logic.state.TurnState;

/**
 * Created by Alexander on 12/13/2023
 */
public class ClassicRulesHandler implements IRulesHandler {

    CaptureRule captureRule = new ClassicCaptureRule();
    ExtraTurnRule extraTurnRule = new ClassicExtraTurnRule();

    @Override
    public TurnState checkFinalStoneRules(TurnState state) {
        //these rules are considered mutually exclusive
        if (extraTurnRule.check(state)) {
            return extraTurnRule.enact(state);
        }
        if (captureRule.check(state)) {
            return captureRule.enact(state);
        }
        return null;
    }

}
