package ie.tudublin;

import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

public class DANI extends PApplet {

    public ArrayList<Word> model;
    public Random random;

    public void settings() {
        size(800, 800);
        // fullScreen(SPAN);
    }

	// Declaring a String array to store the sonnet
	public String[] sonnet;

    public void setup() {
        colorMode(HSB);
        model = new ArrayList<Word>();
        random = new Random();
        loadFile("shakespere.txt");
        printModel();
		sonnet = writeSonnet();
    }

    public void draw() {
        background(0);
        fill(255);
        noStroke();
        textSize(20);
        textAlign(CENTER, CENTER);

		// If the sonnet exists, display it on the screen
        if (sonnet != null) {
            // display the sonnet on the screen
            float x = width / 2;
            float y = height / 3;
			// Looping through the sonnet and displaying each line
            for (int i = 0; i < sonnet.length; i++) {
                text(sonnet[i], x, y);
                y += 25;
            }
        }
    }

	//pressing the space bar to generate a new sonnet
    public void keyPressed() {
		if (key == ' ') {
			sonnet = writeSonnet();
		}
	}	

	// The loadFile method that reads a text file and constructs a model of the words and their frequencies
    public void loadFile(String filename) {
        String[] lines = loadStrings(filename);
		// Looping through each line in the file.
        for (String line : lines) {
            String[] words = split(line, ' ');
			 // Looping through each word in the line
            for (int i = 0; i < words.length; i++) {
                String w = words[i].toLowerCase().replaceAll("[^\\w\\s]", "");
                if (w.equals("")) {
                    continue;
                }
                Word word = findWord(w);
                if (word == null) {
                    word = new Word(w);
                    model.add(word);
                }
                if (i < words.length - 1) {
                    String next = words[i + 1].toLowerCase().replaceAll("[^\\w\\s]", "");
                    if (!next.equals("")) {
                        word.addFollow(next);
                    }
                }
            }
        }
    }

	//find the word
    public Word findWord(String str) {
        for (Word word : model) {
            if (word.getWord().equals(str)) {
                return word;
            }
        }
        return null;
    }

	//printing the model 
    public void printModel() {
        for (Word word : model) {
            println(word.toString());
        }
    }

	//find the follow up word
    public Follow findFollow(Word word, String str) {
        for (Follow follow : word.getFollows()) {
            if (follow.getWord().equals(str)) {
                return follow;
            }
        }
        return null;
    }

	//generating a sonnet
    public String[] writeSonnet() {
        String[] sonnet = new String[14];
		// Looping through each line of the sonnet
        for (int i = 0; i < 14; i++) {
			// Selecting a random word from the model
            Word word = model.get(random.nextInt(model.size()));
            String sentence = word.getWord();
			// Looping through each word of the sentence
            for (int j = 0; j < 7; j++) {
                Follow follow = null;
				// Checking if the word has any follow up words
                if (word.getFollows().size() > 0) {
                    int total = 0;
                    for (Follow f : word.getFollows()) {
                        total += f.getCount();
                    }
                    int rand = random.nextInt(total);
                    int count = 0;
					// Counting the total number of occurrences of all the follow up words of the current word
                    for (Follow f : word.getFollows()) {
                        count += f.getCount();
                        if (count > rand) {
                            follow = f;
                            break;
                        }
                    }
                }
                if (follow == null) {
                    break;
                }
				//create sentence
                sentence += " " + follow.getWord();
                word = findWord(follow.getWord());
            }
            sonnet[i] = sentence;
        }
        return sonnet;
    }

}
