package data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataReader {
    private String fileName;
    private List<SentenceSentimentScore> sentences = null;

    public DataReader(String fileName) {
        this.fileName = fileName;
//        sentences = new ArrayList<>();
    }

    public List<SentenceSentimentScore> process() {
        if(sentences != null) return sentences;
//        sentences = new ArrayList<>();

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
        String sentence = sentenceWithScore.substring(sentenceWithScore.length() - 2).trim();
        return new SentenceSentimentScore(sentence, score);
    }

    public class SentenceSentimentScore {
        public final String sentence;
        public final int score;

        public SentenceSentimentScore(String sentence, int score) {
            this.sentence = sentence;
            this.score = score;
        }
    }
}
