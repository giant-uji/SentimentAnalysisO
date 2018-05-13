package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {
    private String fileName;
    private List<SentenceSentimentScore> sentences = null;
    private List<String> punctuationSymbols = Arrays.asList(",", ".", "\"", "!", "?");

    public DataReader(String fileName) {
        this.fileName = fileName;
    }

    public List<SentenceSentimentScore> process() {
        if(sentences != null) return sentences;

        Path path = Paths.get(fileName);
        try {
            sentences = Files.lines(path)
                    .map(e -> sentenceScore(e))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sentences;
    }

    private SentenceSentimentScore sentenceScore(String sentenceWithScore) {
        char charScore = sentenceWithScore.charAt(sentenceWithScore.length() - 1);
        int score = Integer.parseInt(charScore + "");
        String sentence = sentenceWithScore.substring(0, sentenceWithScore.length() - 2).trim();
        sentence = purgePunctuation(sentence);
        return new SentenceSentimentScore(sentence, score);
    }

    private String purgePunctuation(final String input) {
        String output = new String(input);
        for(String symbol: punctuationSymbols) {
            output = output.replace(symbol, "");
        }
        return output;
    }

    public class SentenceSentimentScore {
        public final String sentence;
        public final int score;

        public SentenceSentimentScore(String sentence, int score) {
            this.sentence = sentence;
            this.score = score;
        }

        @Override
        public String toString() {
            return sentence + ": " + score;
        }
    }
}
