package core.utils;

import java.util.Random;

public class RandomGenerator {

    private static final String ALLOWED_USERNAME_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,/'_ -";
    private static final String DISALLOWED_USERNAME_CHARACTERS = "!@#$%^&*(){}[]<>?;:\"|\\";

    private static Random random = new Random();

    public static String generateUsername(boolean isValid, int length) {
        StringBuilder username = new StringBuilder();
        if (!isValid)
            length = length - 1;
        for (int i = 0; i < length; i++) {
            username.append(ALLOWED_USERNAME_CHARACTERS.charAt(random.nextInt(ALLOWED_USERNAME_CHARACTERS.length())));
        }
        if (!isValid)
            username.append(DISALLOWED_USERNAME_CHARACTERS.charAt(random.nextInt(DISALLOWED_USERNAME_CHARACTERS.length())));
        return username.toString();
    }

    public static String generatePass(int length) {
        StringBuilder pass = new StringBuilder();
        String allowedPassCharacters = ALLOWED_USERNAME_CHARACTERS + ALLOWED_USERNAME_CHARACTERS;
        for (int i = 0; i < length; i++) {
            pass.append(allowedPassCharacters.charAt(random.nextInt(allowedPassCharacters.length())));
        }
        return pass.toString();
    }
}
