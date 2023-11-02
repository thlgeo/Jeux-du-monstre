package fr.univlille.sae.model.events;

import java.util.HashMap;
import java.util.Map;

public class ParameterEvent {

    protected Map<String, String> parameters;

    public static final String NB_ROWS = "nbRows";
    public static final String NB_COLS = "nbCols";
    public static final String NAME_MONSTER = "nameMonster";
    public static final String NAME_HUNTER = "nameHunter";

    public ParameterEvent() {
        this.parameters = new HashMap<>();
    }

    public String getValue(String key) {
        return parameters.get(key);
    }

    public boolean setValue(String key, String value) {
        if(parameters.containsKey(key)) {
            parameters.put(key, value);
            return true;
        }
        return false;
    }

    public boolean addParameter(String key, String value) {
        if(!parameters.containsKey(key)) {
            parameters.put(key, value);
            return true;
        }
        return false;
    }

    public boolean remove(String key) {
        if(!parameters.containsKey(key)) return false;
        parameters.remove(key);
        return true;
    }

}
