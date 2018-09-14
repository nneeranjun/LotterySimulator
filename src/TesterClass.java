import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class TesterClass {
    public static void main(String[] args) {
        String url = "https://erikberg.com/nba/standings.json";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet();
        request.addHeader("User-agent", "LotterySimulator/1.0 (nneeranjun@berkeley.edu)");
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testNumCombinations() {
        LotteryMachine lotteryMachine = new LotteryMachine();
        assertEquals(lotteryMachine.createCombinations().size(), 1000);
    }

    @Test
    public void testPosition() {
        LotteryMachine lotteryMachine = new LotteryMachine();
        assertEquals(1, lotteryMachine.getPosition(0));
        assertEquals(2, lotteryMachine.getPosition(140));
        assertEquals(14, lotteryMachine.getPosition(998));
        assertEquals(1, lotteryMachine.getPosition(25));
    }
    @Test
    public void testStartingIndex() {
        LotteryMachine lotteryMachine = new LotteryMachine();
        assertEquals(280, lotteryMachine.startingIndex(3));
    }

    @Test
    public void testLottery() {
        LotteryMachine lotteryMachine = new LotteryMachine();
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            int val = lotteryMachine.runLottery()[0];
            if (!counts.containsKey(val)) {
                counts.put(val, 1);
            }
            counts.put(val, counts.get(val) + 1);
            lotteryMachine.reset();
        }
        for (int val : counts.keySet()) {
            System.out.println(val + ": " + (double) counts.get(val) / 1000000);
        }
    }
    @Test
    public void testRemove() {
        LotteryMachine lotteryMachine = new LotteryMachine();
        lotteryMachine.removeTeamCombinations(1);
        assertEquals(lotteryMachine.baseCombinations.size(), 860);


    }

}
