package HIHI; //패키지 선언

public class GameManager { //클래스 선언
    private int day = 1; //시작일
    private final int MAX_DAY = 30; //끝

    private final Student student = new Student(); //학생 상태 생성자 호출!

    public boolean isGameOver() {
        return day > MAX_DAY; //끝 확인
    }

    public void nextDay() { //다음날
        day++;
        student.resetDay(); // turn과 stamina 다 초기화와 랜덤행동생성!
    }

    public String getEnding() { //끝났을때 능력치로 합격했는지 함수!!
        int kor = student.getStats().get("국어");
        int eng = student.getStats().get("영어");
        int math = student.getStats().get("수학");
        int phy = student.getStats().get("물리");
        int chem = student.getStats().get("화학"); // Student의 MAP 함수에서 각 과목의 점수를 불러옴!!

        if (kor >= 480 && eng >= 480 && math >= 480 && phy >= 480 && chem >= 480) {
            return "♥서울대 합격! 모든 과목 480점 이상 달성!";
        } else if (kor >= 470 && eng >= 470&& math >= 470 && phy >= 470 && chem >= 470) {
            return "연세대 합격! 모든 과목 470점 이상 달성!";
        } else if (kor >= 460 && eng >= 460 && math >= 460 && phy >= 460 && chem >= 460) {
            return "고려대 합격! 모든 과목 460점 이상 달성!"; 
        } else if (kor >= 450 && eng >= 450 && math >= 450 && phy >= 450 && chem >= 450) {
            return "서강대 합격! 모든 과목 450점 이상 달성!";  
        } else if (kor >= 440 && eng >= 440 && math >= 440 && phy >= 440 && chem >= 440) {
            return "성균관대 합격! 모든 과목 440점 이상 달성!";
        } else if (kor >= 430 && eng >= 430 && math >= 430 && phy >= 430 && chem >= 430) {
            return "한양대 합격! 모든 과목 430점 이상 달성!";
        }  else if (kor >= 420 && eng >= 420 && math >= 420 && phy >= 420 && chem >= 420) {
            return "중앙대 합격! 모든 과목 420점 이상 달성!";
        }  else {
            return "😥 불합격... 내년에 다시 도전하자!";
        }
    } //조건들.

    

    public int getDay() {
        return day;
    } //현재 날짜 확인 해주는 메소드.

    public Student getStudent() {
        return student;
    } // Student 객체를 다른 클래스에서 접근할 수 있게 해주는 메소드
    
    
    
    
    //게임의 전체 흐름 조절, 날짜 진행, 게임 종료 여부 판단, 능력치를 바탕으로 엔딩결정. 
}