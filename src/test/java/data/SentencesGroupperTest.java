package data;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class SentencesGroupperTest {
    @Test
    public void positiveSentecesTest() {
        DataReader dt = new DataReader("src/main/resources/yelp_labelled.txt");
        List<DataReader.SentenceSentimentScore>result = dt.process();
        SentencesGroupper gs = new SentencesGroupper(result);

        long positiveSenteces = gs.getPositiveSentences().stream()
                .count();

        assertThat(positiveSenteces, is(500L));
    }
}
