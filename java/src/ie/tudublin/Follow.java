package ie.tudublin;

// The Follow class represents a word that follows another word in the Markov chain model
public class Follow {
    public String word;
    public int count;
  
    public Follow(String word, int count) {
      this.word = word;
      this.count = count;
    }
  
    public String getWord() {
      return word;
    }
  
    public int getCount() {
      return count;
    }
  
    public String toString() {
      return word + "(" + count + ")";
    }
  }
  