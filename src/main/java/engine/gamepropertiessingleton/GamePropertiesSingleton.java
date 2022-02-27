package engine.gamepropertiessingleton;

public class GamePropertiesSingleton {

    private static GameProperties instance;

    private GamePropertiesSingleton() {

    }

    public static GameProperties getInstance() {
        if ( null == instance) {
            instance = new GameProperties();
        }

        return instance;
    }
}
