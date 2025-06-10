package HIHI; //패키지 선언

import java.util.*; //Map,List,HashMap,ArrayList,Random,Collections와 같은 유틸클래스 한번에

public class Student { // 클래스 = 게임속 플레이어
    private int stamina = 100; //체력
    private int turn = 0;      // 하루동안 행동한 횟수
    private final int MAX_TURN = 10; //최대10턴 final> 절대 못 바꿈. 또한 상수로 절대 바뀌지 않아야하기때문 
    private final int MAX_STAMINA = 100; //체력은 100 위와 같은 이유 final 사용 규칙임 얘낸

    private Map<String, Integer> stats = new HashMap<>(); // 과목 능력치를 저장하는 맵 과목을 key, 점수가 value 공부하는 능력치 오르는건 put()으로 갱신!!
    private List<String> todayActions = new ArrayList<>(); // 오늘 선택 가능한 랜덤과목 4개를 저장한 리스트!
    private Random random = new Random(); // 랜덤 수 생성을 위한 객체(공부시 능력치 랜덤 오르는)

    public Student() { //객체가 생성될때 자동 호출 초기 능력치 0 
        stats.put("국어", 0);
        stats.put("영어", 0);
        stats.put("수학", 0);
        stats.put("물리", 0);
        stats.put("화학", 0);
        generateNewActions(); //오늘의 4가지 랜덤행동 코드 호출
    }

    //핵심! 행동 리스트 초기화 및 생성, 6가지 중 오늘의 행동 4개를 랜덤 생성 저장용,기록용임 내부적 저장
    public void generateNewActions() {
        todayActions.clear(); //행동 리스트 비우기
        List<String> possibleActions = Arrays.asList("국어", "영어", "수학", "물리", "화학", "휴식");
        Collections.shuffle(possibleActions); //가능한 행동을 섞음
        for (int i = 0; i < 4; i++) {
            todayActions.add(possibleActions.get(i));
        } //반복문으로 4개만 선택해서 오늘의 행동으로 얻어냄.
    }

    // !!! 행동 실행 
    public boolean act(String action) { //행동을 클릭했을때 실행되는 메소드
        if (isExhausted() || isDayOver()) return false; //체력이 없거나 10턴을 쓰면 false 리턴 즉 행동불가

        if (action.equals("휴식")) { //휴식을 누른경우 체력 회복
            rest(); //휴식 메서드
        } else {
            study(action); //공부를 누른 경우 능력치 증가, 공부 메서드
        }

        turn++; //턴 증가
        return true; //행동 성공 true
    }

    public void study(String subject) { //공부
        int increase = random.nextInt(11) + 5; // 5~15 능력치 랜덤 증가
        stats.put(subject, stats.get(subject) + increase); //능력치 증가!! put()
        stamina -= 17; //체력17 감소
        if (stamina < 0) stamina = 0; // 0밑으로는 안 가게 설정
    }

    public void rest() { //체력회복
        stamina += 20;
        if (stamina > MAX_STAMINA) stamina = MAX_STAMINA; //100넘지 않게 설정
    }

    public boolean isExhausted() { //체력이 0이하이면 true
        return stamina <= 0;
    }

    public boolean isDayOver() { // 하루 10턴을 넘었거나 기절 했으면 하루 종료
        return turn >= MAX_TURN || isExhausted();
    }

    public void resetDay() { //하루당 최대 턴수나 체력 초기화
        stamina = MAX_STAMINA; //체력회복
        turn = 0; //턴 초기화
        generateNewActions(); //즉 하루 끝나고 다음날이 시작 될때 랜덤 4개 생성하는 코드 호출!
    }

    public void resetStamina() { //체력만 리셋
        stamina = MAX_STAMINA; //필요시 체력만 초기화할때.
    }
    //Getter 둘 다른 클래스들에서 읽을수 있도록
    public int getStamina() { //현재 체력 
        return stamina;
    }

    public int getTurn() { //턴 수
        return turn;
    }

    public Map<String, Integer> getStats() {
        return stats;
    } //능력치 상태를 외부에서 읽을 수 있게 과목 별 점수를 개별적으로 참조 가능 gamemanager에서 엔딩판단용으로쓰임!!

    public List<String> getTodayActions() { //오늘 과목 리스트
        return todayActions;
    }

}






// 하루에 10턴 랜덤 과목과 랜덤 능력치 증가 체력과 하루 끝나기 휴식 등. 능력치와 상태는 map,list,random
// 유저의 하루활동가 능력치를 관리하는 클래스 버튼 클릭시 act()가 호출됨.

