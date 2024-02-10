import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameData {
    private final String categoryOne;
    private final ArrayList<String> categoryOneWords;
    private int categoryOneAttempts;
    private final String categoryTwo;
    private final ArrayList<String> categoryTwoWords;
    private int categoryTwoAttempts;
    private final String categoryThree;
    private final ArrayList<String> categoryThreeWords;
    private int categoryThreeAttempts;

    private boolean[] categoryWon;
    private int currentTheme;

    public GameData() {
        categoryOne = "Fall";
        categoryOneWords = new ArrayList<String>(Arrays.asList("coat", "jacket", "leaves", "fall", "cold", "orange", "red", "yellow", "brown"));
        categoryOneAttempts = 3;

        categoryTwo = "Winter";
        categoryTwoWords = new ArrayList<String>(Arrays.asList("snow", "white", "hat", "jacket", "scarf", "gloves", "mittens", "snowman", "angels", "coat", "snow-pants", "boots", "snow-angels", "hot-cocoa", "slushy", "blue"));
        categoryTwoAttempts = 3;

        categoryThree = "Summer";
        categoryThreeWords = new ArrayList<String>(Arrays.asList("sunscreen", "yellow", "sun", "flip-flops", "sandals", "shorts", "beach", "sea", "water", "sunglasses", "icecream", "pineapples"));
        categoryThreeAttempts = 3;

        categoryWon = new boolean[] {false, false, false};
    }

    public String getWord(int theme) {
        int index;
        String word;
        switch (theme) {
            case 1:
                index = (int)(Math.random() * categoryOneWords.size());
                System.out.println("Index: " + index + " Length: " + categoryOneWords.size());
                word = categoryOneWords.get(index).toLowerCase();
                categoryOneWords.remove(index);
                return word;
            case 2:
                index = (int)(Math.random() * categoryTwoWords.size());
                System.out.println("Index: " + index + " Length: " + categoryTwoWords.size());
                word = categoryTwoWords.get(index).toLowerCase();
                categoryTwoWords.remove(index);
                return word;
            case 3:
                index = (int)(Math.random() * categoryThreeWords.size());
                System.out.println("Index: " + index + " Length: " + categoryThreeWords.size());
                word = categoryThreeWords.get(index).toLowerCase();
                categoryThreeWords.remove(index);
                return word;
            default:
                System.out.println("Something wrong happened to getWord()\nTheme: "+ theme);
                return "";
        }
    }

    public String getCategory(int theme) {
        int index;
        switch (theme) {
            case 1:
                return categoryOne;
            case 2:
                return categoryTwo;
            case 3:
                return categoryThree;
            default:
                System.out.println("Something wrong happened to getCategory()\nTheme: "+ theme);
                return "";
        }
    }

    public int getAttempts(int theme) {
        int index;
        switch (theme) {
            case 1:
                return categoryOneAttempts;
            case 2:
                return categoryTwoAttempts;
            case 3:
                return categoryThreeAttempts;
            default:
                System.out.println("Something wrong happened to getAttempts()\nTheme: "+ theme);
                return -1;
        }
    }

    public void decrementCategoryAttempt(int theme) {
        switch (theme) {
            case 1:
                categoryOneAttempts--;
                break;
            case 2:
                categoryTwoAttempts--;
                break;
            case 3:
                categoryThreeAttempts--;
                break;
            default:
                System.out.println("Something wrong happened to getWord()\nTheme: "+ theme);
                break;
        }
    }

    public void setCategoryWon(int index) {
        categoryWon[index] = true;
    }

    public boolean getCategoryWon(int index) {
        return categoryWon[index];
    }

    public int getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(int num) {
        currentTheme = num;
    }

    public boolean isGameOver() {
        return categoryOneAttempts == 0 || categoryTwoAttempts == 0 || categoryThreeAttempts == 0;
    }

    public boolean didUserWin() {
        return categoryWon[0] && categoryWon[1] && categoryWon[2];
    }

}
