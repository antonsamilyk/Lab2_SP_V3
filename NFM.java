//Variant -3, modeling Nondet finite machine

import java.util.*;

public class NFM {
    String start;
    Set<String> finish;
    Map<String, Map<Character, List<String>>> trans;

    NFM(String start_state, String finish_states, String[] transitions) {
        trans = new HashMap<String, Map<Character, List<String>>>();
        finish = new HashSet<String>();
        start = start_state;
        String[] subStr = finish_states.split(",");
        for (int i = 0; i < subStr.length; i++)
            finish.add(subStr[i]);

        System.out.println("Starting with: " + start);
        System.out.println("Finish: " + finish);


        for (String ts : transitions) {
            String[] sub_trans = ts.split(",");
            String from = sub_trans[0];
            String to = sub_trans[1];
            if (!trans.containsKey(from)) trans.put(from, new HashMap<Character, List<String>>());
            for (int i = 2; i < sub_trans.length; i++) {
                char key = sub_trans[i].charAt(0);
                if (!trans.get(from).containsKey(key)) trans.get(from).put(key, new ArrayList<String>());
                trans.get(from).get(key).add(to);
            }
        }
        System.out.println("Transitions:" + trans);


    }

    public boolean matching(String str) {
        boolean isMatched = false;
        Set<String> currStates = new HashSet<String>();
        currStates.add(start);
        for (int i = 0; i < str.length(); i++) {

            char c = str.charAt(i);
            Set<String> nextStates = new TreeSet<String>();
            for (String state : currStates)
                if (trans.get(state).containsKey(c)) nextStates.addAll(trans.get(state).get(c));
            System.out.println(str.charAt(i) + ": " + currStates);
            currStates = nextStates;

        }
        System.out.println("Finale states are: ");
        for (String state : currStates) {
            System.out.println(state);
            if (finish.contains(state)) isMatched = true;
        }
        return isMatched;
    }

    public void test(String name, String[] testing) {
        System.out.println("Name of test:" + name + "\n");
        for (String str : testing) {
            System.out.println(str + ":" + matching(str));
            System.out.println("\n");
        }
    }

    public static void main(String[] args) {
        String start = "1";
        String finishes = "4,2";
        String[] transitions = {"1,1,a,b,c", "2,1,b", "2,2,c", "4,4,a,b,c", "1,5,b,c", "2,3,a", "3,5,a", "5,5,a,b", "1,4,a"};
        NFM nfm2 = new NFM(start, finishes, transitions);
        String[] test = {"aca", "acab", "baaca", "aaabba", "bbaaacbbab", "ababcaba"};
        nfm2.test("test", test);
    }

}
