package htms.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AccountUtil {
    /**
     * Auto generate email for new accounts based on user's name and code
     *
     * @param name {@code User's name}
     * @param code {@code User's code}
     * @return {@code New User's email}
     */
    public static String generateEmail(String name, String code) {
        String[] nameParts = name.split(" ");
        StringBuilder email = new StringBuilder();
        // Vien Quoc Binh, code: FMBZ64 -> binhvqfmbz64@gmail.com
        email.append(nameParts[nameParts.length - 1]);
        for (int i = 0; i < nameParts.length - 1; i++) {
            email.append(nameParts[i].charAt(0));
        }
        email.append(code);
        email.append("@gmail.com");
        return email.toString().toLowerCase();
    }

    public static String generatePassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
