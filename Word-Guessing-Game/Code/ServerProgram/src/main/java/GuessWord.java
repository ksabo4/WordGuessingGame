import java.util.ArrayList;

public class GuessWord {
    private String word;
    //Stores word, but with only letters used has guessed
    private ArrayList<Character> wordArray;
    private int guessesRemaining;
    private ArrayList<Character> guessesLetters;

    public GuessWord (String word) {
        this.word = word;
        wordArray = new ArrayList<>();
        for (int i = 0; i < this.word.length(); i++) {
            wordArray.add('_');
        }
        guessesRemaining = 6;
        guessesLetters = new ArrayList<>();
    }

    public int getGuessesRemaining() {
        return guessesRemaining;
    }

    public String getWord() {
        return this.word;
    }

    public void setWord(String newWord) {
        this.word = newWord;
        wordArray.clear();
        wordArray = new ArrayList<>();
        for (int i = 0; i < this.word.length(); i++) {
            wordArray.add('_');
        }
        guessesRemaining = 6;
        guessesLetters.clear();
    }

    private void updateWordArray(int index) {
        wordArray.set(index, word.charAt(index));
    }

    //outputs number depending on what guess user makes
    //-2 - user already guessed that letter
    //-1 - letter is not in word
    //(0, n) - letter is in word at that index
    public ArrayList<String> updateGuesses(char c) {
        ArrayList<String> messagesToSend = new ArrayList<>();
        boolean letterwasFound = false;
        //Checks if guess has already been made
        for (char pastGuesses: guessesLetters) {
            if (c == pastGuesses) {
                messagesToSend.add("already made");
                return messagesToSend;
            }
        }

        //Checks if word is in letter
        for (int index = 0; index < word.length(); index++) {
            if (c == word.charAt(index)) {
                messagesToSend.add("found at index " + index);
                wordArray.set(index, c);
                letterwasFound = true;
            }
        }

        guessesLetters.add(c);
        if (!letterwasFound) {
            guessesRemaining--;
            messagesToSend.add("not in word");
        }

        //Check if user lost
        if (guessesRemaining == 0) {
            messagesToSend.add("successful in him/her/them losing the attempt");
        } else {
            //Checks if user won
            boolean didUserWin = true;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) != wordArray.get(i)) {
                    didUserWin = false;
                    break;
                }
            }
            if (didUserWin) {
                messagesToSend.add("successful in him/her/them winning! :)");
            }
        }


        return messagesToSend;
    }
}
