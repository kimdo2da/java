package HIHI; //패키지 
//엔터로 실행 가능 방향키로도 가능

//객체
import javafx.application.Application; //Application 
import javafx.geometry.Insets; // 안쪽 여백을 설정 padding을 설정할때 버튼이나 박스 여백을 줌 
import javafx.geometry.Pos; //레이아웃 정렬위치 지정 버튼 위치 
import javafx.scene.Scene; //전체화면 구성 클래스 
import javafx.scene.control.Button; //버튼 요소 만들기 위한 사용 클래스 클릭가능버튼
import javafx.scene.image.Image; // 게임에 넣을 배경이나 캐릭터 경로 파일을 객체로 읽어옴
import javafx.scene.image.ImageView; // 이미지를 실제로 화면 띄움
import javafx.scene.layout.*; // 버튼이나 텍스트를 정렬하고 그룹지을수 있는 기능 모음
import javafx.scene.paint.Color; // 텍스트나 배경 색상 지정
import javafx.scene.text.Text; //게임상태나 설명 출력하는 글자 상자
import javafx.stage.Stage; // 프로그램을 담는 창
import java.util.*; //Map,List,HashMap,ArrayList,Random,Collections와 같은 유틸클래스 한번에

//리스너
public class Main extends Application { //Application 상속받아 javafx앱이됨 main()메서드
	// private MediaPlayer mediaPlayer;
    private final GameManager gameManager = new GameManager(); //gamemanager에서도 읽어오며 전체흐름관리
    private VBox buttonBox = new VBox(10); //랜덤버튼 담는 버튼박스
    private Text statusText = new Text(); //능력치 상태 출력용 텍스트
    private Button nextDayButton = new Button("다음 날 ▶"); //다음날 버튼
    private StackPane root = new StackPane(); 
    private VBox contentBox = new VBox(20); //텍스트 버튼 묶는 레이아웃

    @Override
    public void start(Stage primaryStage) { //
        
        
    	// 텍스트 박스 배경 
    	StackPane statusBox = new StackPane(); 
    	statusBox.setPadding(new Insets(10)); //컴포넌트 안쪽에 10px여백
    	statusBox.setMaxWidth(300);

    	// 반투명 배경 추가
    	BackgroundFill backgroundFill = new BackgroundFill(
    	    Color.rgb(255, 255, 255, 0.7), // 흰색 + 70% 투명도
    	    new CornerRadii(10),
    	    Insets.EMPTY //여백 설정
    	);
    	statusBox.setBackground(new Background(backgroundFill));

    	// 텍스트 스타일
    	statusText.setFill(Color.BLACK); // 검정 글자
    	statusText.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

    	// 텍스트를 박스 안에 넣기
    	statusBox.getChildren().add(statusText);
    	//텍스트 박스 설정 끝

        // 배경 이미지
    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    	//마지막 false > cover가 아니라 contain처럼 동작 크기에 맞게 늘리기
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:/C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/background.png",false),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(backgroundImage));
        //뒤배경이미지끝

        // 캐릭터 이미지
        ImageView characterView = new ImageView(
                new Image("file:/C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/hosik.png")
        );
        characterView.setPreserveRatio(true);
        characterView.fitWidthProperty().bind(primaryStage.widthProperty().multiply(0.2)); // 창 너비의 20%
        //창 크기 비례 자동 조절 윗줄코드 캐릭터 이미지 끝

        // 다음날 버튼 설정
        nextDayButton.setVisible(false);
        nextDayButton.setOnAction(e -> {
            gameManager.nextDay(); //다음날로갈때gm호출!
            if (gameManager.isGameOver()) { //게임이 종료가 됐는지 확인하는!!
                showEnding();
            } else {
                updateUI();
            }
        }); //만약 누르고 모든 날이 끝난거면 엔딩을 보여줌 아니면 updateUI로 화면갱신
        
        contentBox.setPadding(new Insets(20));
        contentBox.getChildren().addAll(statusBox, buttonBox, nextDayButton);
        contentBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, characterView, contentBox);
        layout.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(layout);
        //텍스트 , 버튼, 캐릭터 이미지 등을 VBox로 구성해서 정렬

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("인서울 챌린지 📝");
        primaryStage.setFullScreen(true); 
        primaryStage.show();
        // 화면 생성 > 윈도우에 세팅> 전체화면> 실행

        updateUI();
        //첫 실행시 버튼 생성하는 메서드 호출
    }

    private void updateUI() {
        buttonBox.getChildren().clear();
        nextDayButton.setVisible(false);
        updateButtons();
    } //버튼 초기화 새 랜덤 과목 버튼 생성 updateButtonds() 호출
    
        // 버튼 랜덤 생성 메서드 !!! 행동 버튼 및 버튼들 생성
        private void updateButtons() {
            buttonBox.getChildren().clear();
            nextDayButton.setVisible(false);

            // 매번 새로운 랜덤 4개 생성 > ui버튼용 외부로 보여주는 표현 실제 저장되있는걸 화면에 보여지게 해주는건 여기임!
            List<String> possibleActions = Arrays.asList("국어", "영어", "수학", "물리", "화학", "휴식");
            Collections.shuffle(possibleActions);
            List<String> actions = possibleActions.subList(0, 4);  // 4개만 뽑기
            //버튼 생성 끝

            for (String action : actions) {
                Button button = new Button(action + " 선택");
                button.setMinWidth(300);
                //버튼 위치 설정

                // 버튼 색상 설정
                switch (action) {
                    case "국어": button.setStyle("-fx-background-color: red; -fx-text-fill: white;"); break;
                    case "영어": button.setStyle("-fx-background-color: green; -fx-text-fill: white;"); break;
                    case "수학": button.setStyle("-fx-background-color: blue; -fx-text-fill: white;"); break;
                    case "화학": button.setStyle("-fx-background-color: orange; -fx-text-fill: white;"); break;
                    case "휴식": button.setStyle("-fx-background-color: white; -fx-text-fill: black;"); break;
                }
                // 버튼 클릭시 > 체력이 없을때를 조사해서 다음날 버튼, 턴이 남으면 새로운 행동생성
                button.setOnAction(e -> {
                    Student student = gameManager.getStudent();
                    boolean success = student.act(action); //행동 실행 공부나 휴식
                    updateStatus(); //텍스트 업데이트
                    if (!success || student.isDayOver()) {
                        showNextDayButton(); //체력이 없거나 턴 다 다음날 보여주는
                    } else {
                        updateButtons();  // 턴 남으면 새로운 버튼 다음 턴용 랜덤 버튼 갱신
                    }
                });

                buttonBox.getChildren().add(button);
            }

            updateStatus();
        }
         
    
    private void updateStatus() { //!!! 현재 능력치 턴 체력등을 외부 표시 시각적
        Student s = gameManager.getStudent(); //능력치 가져옴
        Map<String, Integer> stats = s.getStats();  //맵으로 과목 능력치 가져옴!!

        statusText.setText(
            "Day " + gameManager.getDay() + "\n" +
            "체력: " + s.getStamina() + "\n" +
            "국어: " + stats.get("국어") + ", 영어: " + stats.get("영어") +
            "\n수학: " + stats.get("수학") + ", 물리: " + stats.get("물리") +
            ", 화학: " + stats.get("화학") +
            "\n턴: " + s.getTurn() + "/10"
        ); //표시
    }

    private void showNextDayButton() {
        buttonBox.getChildren().clear();  // 기존 버튼 제거
        nextDayButton.setVisible(true);   // 다음날 버튼 보이기
    }

    private void showEnding() { 
        statusText.setText("🎓 " + gameManager.getEnding());
        buttonBox.getChildren().clear(); //버튼 없애기!!
        nextDayButton.setVisible(false); //다음날 버튼 없애기!!
    } //엔딩 출력 함수!!

 
}



//미디어
//String path = "C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/music2.mp3";
//Media media = new Media(new File(path).toURI().toString());
//mediaPlayer = new MediaPlayer(media); // 필드에 대입
//mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 반복 재생
//mediaPlayer.setVolume(1.0); // 볼륨 100%
//mediaPlayer.play();



/* 오류문구 *mediaPlayer.setOnError(() -> {
    System.out.println("MediaPlayer Error: " + mediaPlayer.getError());
});

media.setOnError(() -> {
    System.out.println("Media Error: " + media.getError());
});
System.out.println("Loaded Path: " + new File(path).toURI().toString()); */
