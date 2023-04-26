package ie.tudublin;
import java.util.ArrayList;

// The Word class represents a word in the chain model
public class Word {
  public String word;
  public ArrayList<Follow> follows;

  public Word(String word) {
    this.word = word;
    follows = new ArrayList<Follow>();
  }

  public String getWord() {
    return word;
  }

  public ArrayList<Follow> getFollows() {
    return follows;
  }

   // Adds a word to the list of words that follow this word in the chain
  public void addFollow(String word) {
    for (Follow follow : follows) {
      if (follow.getWord().equals(word)) {
        follow.count++;// If the word is already in the list, increment its count
        return;
      }
    }
    follows.add(new Follow(word, 1));// If the word is not in the list, add it with a count of 1
  }

  // Returns a string representation of the Word object
  public String toString() {
    String result = word + ": ";
    for (Follow follow : follows) {
      result += follow.toString() + " ";
    }
    return result;
  }
}
