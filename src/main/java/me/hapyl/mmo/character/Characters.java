package me.hapyl.mmo.character;

public enum Characters {

    PLAYER(new PlayerCharacter()),

    ;

    private final Character character;

    Characters(Character character) {
        this.character = character;
    }


    public Character getCharacter() {
        return character;
    }

    public <T extends Character> T getCharacter(Class<T> cast) throws IllegalArgumentException {
        if (!cast.isInstance(character)) {
            throw new IllegalArgumentException("%s cannot be cast to %s".formatted(character.getClass().getSimpleName(), cast.getSimpleName()));
        }

        return cast.cast(character);
    }
}
