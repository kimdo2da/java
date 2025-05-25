package HIHI;

import java.util.*;

public class Student {
    private int stamina = 100;
    private int turn = 0;
    private final int MAX_TURN = 10;
    private final int MAX_STAMINA = 100;

    private Map<String, Integer> stats = new HashMap<>();
    private List<String> todayActions = new ArrayList<>();
    private Random random = new Random();

    public Student() {
        stats.put("국어", 0);
        stats.put("영어", 0);
        stats.put("수학", 0);
        stats.put("물리", 0);
        stats.put("화학", 0);
        generateNewActions();
    }

    // 오늘의 행동 4개를 랜덤 생성
    public void generateNewActions() {
        todayActions.clear();
        List<String> possibleActions = Arrays.asList("국어", "영어", "수학", "물리", "화학", "휴식");
        Collections.shuffle(possibleActions);
        for (int i = 0; i < 4; i++) {
            todayActions.add(possibleActions.get(i));
        }
    }

    // 행동 실행
    public boolean act(String action) {
        if (isExhausted() || isDayOver()) return false;

        if (action.equals("휴식")) {
            rest();
        } else {
            study(action);
        }

        turn++;
        return true;
    }

    public void study(String subject) {
        int increase = random.nextInt(11) + 5; // 5~15
        stats.put(subject, stats.get(subject) + increase);
        stamina -= 17;
        if (stamina < 0) stamina = 0;
    }

    public void rest() {
        stamina += 20;
        if (stamina > MAX_STAMINA) stamina = MAX_STAMINA;
    }

    public boolean isExhausted() {
        return stamina <= 0;
    }

    public boolean isDayOver() {
        return turn >= MAX_TURN || isExhausted();
    }

    public void resetDay() {
        stamina = MAX_STAMINA;
        turn = 0;
        generateNewActions();
    }

    public void resetStamina() {
        stamina = MAX_STAMINA;
    }

    public int getStamina() {
        return stamina;
    }

    public int getTurn() {
        return turn;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public List<String> getTodayActions() {
        return todayActions;
    }

    public int getTotalScore() {
        return stats.get("국어") + stats.get("영어") + stats.get("수학") +
               stats.get("물리") + stats.get("화학");
    }
}


