package com.snahva.commphoneapp;

public class CaesarCipher {

    public static final char START_LOWER_CASE_ALPHABET = 'a'; // ASCII-97
    public static final char END_LOWER_CASE_ALPHABET = 'z';   // ASCII-122

    public static final char START_UPPER_CASE_ALPHABET = 'A'; // ASCII-65
    public static final char END_UPPER_CASE_ALPHABET = 'Z';   // ASCII-90

    public static final int ALPHABET_SIZE = 'Z' - 'A' + 1;    // 26 of course

    /**
     * Performs a single encrypt followed by a single decrypt of the Caesar
     * cipher, prints out the intermediate values and finally validates
     * that the decrypted plaintext is identical to the original plaintext.
     *
     * <p>
     * This method outputs the following:
     *
     * <pre>
     * Plaintext  : The quick brown fox jumps over the lazy dog
     * Ciphertext : Qeb nrfzh yoltk clu grjmp lsbo qeb ixwv ald
     * Decrypted  : The quick brown fox jumps over the lazy dog
     * Successful decryption: true
     * </pre>
     * </p>
     *
     * @param args (ignored)
     */
//    public static void main(String[] args) {
//
//        int shift = 23;
//        String plainText = "The quick brown fox jumps over the lazy dog";
//
//        System.out.println("Plaintext  : " + plainText);
//
//        String ciphertext = caesarCipherEncrypt(plainText, shift);
//        System.out.println("Ciphertext : " + ciphertext);
//
//        String decrypted = caesarCipherDecrypt(ciphertext, shift);
//        System.out.println("Decrypted  : " + decrypted);
//        System.out.println("Successful decryption: "
//                + decrypted.equals(plainText));
//    }

    public static String caesarCipherEncrypt(String plaintext, int shift) {
        return caesarCipher(plaintext, shift, true);
    }

    public static String caesarCipherDecrypt(String ciphertext, int shift) {
        return caesarCipher(ciphertext, shift, false);
    }

    private static String caesarCipher(
            String input, int shift, boolean encrypt) {

        // create an output buffer of the same size as the input
        StringBuilder output = new StringBuilder(input.length());

        for (int i = 0; i < input.length(); i++) {

            // get the next character
            char inputChar = input.charAt(i);

            // calculate the shift depending on whether to encrypt or decrypt
            int calculatedShift = (encrypt) ? shift : (ALPHABET_SIZE - shift);

            char startOfAlphabet;
            if ((inputChar >= START_LOWER_CASE_ALPHABET)
                    && (inputChar <= END_LOWER_CASE_ALPHABET)) {

                // process lower case
                startOfAlphabet = START_LOWER_CASE_ALPHABET;
            } else if ((inputChar >= START_UPPER_CASE_ALPHABET)
                    && (inputChar <= END_UPPER_CASE_ALPHABET)) {

                // process upper case
                startOfAlphabet = START_UPPER_CASE_ALPHABET;
            } else {

                // retain all other characters
                output.append(inputChar);

                // and continue with the next character
                continue;
            }

            // index the input character in the alphabet with 0 as base
            int inputCharIndex =
                    inputChar - startOfAlphabet;

            // cipher / decipher operation (rotation uses remainder operation)
            int outputCharIndex =
                    (inputCharIndex + calculatedShift) % ALPHABET_SIZE;

            // convert the new index in the alphabet to an output character
            char outputChar =
                    (char) (outputCharIndex + startOfAlphabet);

            // add character to temporary-storage
            output.append(outputChar);
        }

        return output.toString();
    }
}
