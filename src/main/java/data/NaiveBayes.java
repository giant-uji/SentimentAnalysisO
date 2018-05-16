package data;

import util.PurgeString;

import java.util.List;

public class NaiveBayes {
    private WordCounter positiveCounter;
    private WordCounter negativeCounter;
    private WordCounter allCounter;
    private DataReader dt;

    public NaiveBayes() {
        super();
    }

    public void initialize(String fileName) {
        dt = new DataReader(fileName, 80);
//        List<DataReader.SentenceSentimentScore> result = dt.process();
        dt.process();
//        SentencesGroupper gs = new SentencesGroupper(result);
        SentencesGroupper gs = new SentencesGroupper(dt.getTrainSet());
        positiveCounter = new WordCounter();
        positiveCounter.countWords(gs.getPositiveSentences());
        negativeCounter = new WordCounter();
        negativeCounter.countWords(gs.getNegativeSentences());
        allCounter = new WordCounter();
        allCounter.countWords(gs.allSentences());
    }

    public double positiveProbability(final String input) {
        String sentence = new String(input).toLowerCase();
        sentence = PurgeString.removePunctuationSymbols(sentence);
        sentence = PurgeString.removeDeterminants(sentence);
        double positive, result = 0;
        String[] vectorString = sentence.split(" ");
        for(String word: vectorString) {
            positive = positiveCounter.frequency(word);
            if(positive != 0 ) result += Math.log(positive) / allCounter.logarithmicFrequency(word);
        }
        for (int i=0 ; i<vectorString.length-1 ; i++){
            String twoWords = vectorString[i] + " " +vectorString[i+1];
            positive = positiveCounter.frequency(twoWords);
            if (positive != 0) result += Math.log(positive) / allCounter.logarithmicFrequency(twoWords);
        }
        return result;
    }

    public double negativeProbability(final String input) {
        String sentence = new String(input).toLowerCase();
        sentence = PurgeString.removePunctuationSymbols(sentence);
        sentence = PurgeString.removeDeterminants(sentence);
        double negative, result = 0;
        String[] vectorString = sentence.split(" ");
        for(String word: vectorString) {
            negative = negativeCounter.frequency(word);
            if(negative != 0) result += Math.log(negative) / allCounter.logarithmicFrequency(word);
        }
        for (int i=0 ; i<vectorString.length-1 ; i++){
            String twoWords = vectorString[i] + " " +vectorString[i+1];
            negative = positiveCounter.frequency(twoWords);
            if (negative != 0) result += Math.log(negative) / allCounter.logarithmicFrequency(twoWords);
        }
        return result;
    }

    public int sentimentScore(final String sentece) {
        if(positiveProbability(sentece) > negativeProbability(sentece)) return 1;
        return 0;
    }

    public double test() {
        List<DataReader.SentenceSentimentScore> test = dt.getTestSet();
        long score = 0;
        for(DataReader.SentenceSentimentScore sentenceSentimentScore: test) {
            if(sentimentScore(sentenceSentimentScore.sentence) == sentenceSentimentScore.score) score++;
        }
        return (double)score / test.size() * 100.0;
    }
}
