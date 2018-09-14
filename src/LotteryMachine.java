import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class LotteryMachine {
    private HashMap<String, Team> teams;
    ArrayList<int[]> baseCombinations;
    final int[] NUM_COMBINATIONS = new int[]{140, 140, 140, 125, 105, 90, 75, 60, 45, 30, 20, 15, 10, 5};

    public LotteryMachine(HashMap<String, Team> teams) {
        this.teams = teams;
        baseCombinations = createCombinations();
    }

    public LotteryMachine() {
        //Do nothing
        baseCombinations = createCombinations();
    }

    ArrayList<int[]> createCombinations() {
        ArrayList<int[]> temp = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            for (int j = i + 1; j <= 14; j++) {
                for (int k = j + 1; k <= 14; k++) {
                    for (int l = k + 1; l <= 14; l++) {
                        temp.add(new int[]{i, j, k, l});
                    }
                }
            }
        }
        Random random = new Random(temp.size());
        temp.remove(random.nextInt(temp.size()));
        Collections.shuffle(temp);
        return temp;
    }

    int[] runLottery() {
        Random random = new Random();
        int[] lottery = new int[4];
        for (int i = 0; i < 4; i++) {
            int rand = random.nextInt(baseCombinations.size());
            int[] temp = baseCombinations.get(rand);
            int position = getPosition(baseCombinations.indexOf(temp));
            for (int val : lottery) {
                while (val == position) {
                    rand = random.nextInt(baseCombinations.size());
                    temp = baseCombinations.get(rand);
                    position = getPosition(baseCombinations.indexOf(temp));
                }
            }
            lottery[i] = position;
        }
        return lottery;
    }
    
    void removeTeamCombinations(int position) {
        int start = startingIndex(position);
        for (int i = start; i < start + NUM_COMBINATIONS[position - 1]; i++) {
            baseCombinations.remove(i);
        }
    }

    int startingIndex(int position) {
        int sum = 0;
        for (int i = 1; i < position; i++) {
            sum += NUM_COMBINATIONS[i];
        }
        return sum;
    }

    int getPosition(int index) {
        int sum = 0;
        for (int i = 0; i < NUM_COMBINATIONS.length; i++) {
            if (index >= sum  && index <= sum + NUM_COMBINATIONS[i] - 1) {
                return i + 1;
            }
            sum += NUM_COMBINATIONS[i];
        }
        return -1;
    }

    void reset() {
        baseCombinations = createCombinations();
    }


}



