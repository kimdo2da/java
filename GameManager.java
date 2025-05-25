package HIHI;

public class GameManager {
    private int day = 1;
    private final int MAX_DAY = 30;

    private final Student student = new Student();

    public boolean isGameOver() {
        return day > MAX_DAY;
    }

    public void nextDay() {
        day++;
        student.resetDay(); // turn/stamina 다 초기화
    }

    public String getEnding() {
        int kor = student.getStats().get("국어");
        int eng = student.getStats().get("영어");
        int math = student.getStats().get("수학");
        int phy = student.getStats().get("물리");
        int chem = student.getStats().get("화학");

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
    }

    

    public int getDay() {
        return day;
    }

    public Student getStudent() {
        return student;
    }
}