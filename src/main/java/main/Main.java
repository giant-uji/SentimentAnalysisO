package main;

import data.DataReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataReader dt = new DataReader("src/main/resources/yelp_labelled.txt");
        List<DataReader.SentenceSentimentScore> result = dt.process();
        result.stream()
                .forEach(System.out::println);
    }
}
