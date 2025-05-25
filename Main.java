package HIHI;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.util.*;

public class Main extends Application {
	// private MediaPlayer mediaPlayer;
    private final GameManager gameManager = new GameManager();
    private VBox buttonBox = new VBox(10);
    private Text statusText = new Text();
    private Button nextDayButton = new Button("다음 날 ▶");
    private StackPane root = new StackPane();
    private VBox contentBox = new VBox(20);

    @Override
    public void start(Stage primaryStage) {
    	//미디어
//    	String path = "C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/music2.mp3";
//    	Media media = new Media(new File(path).toURI().toString());
//        mediaPlayer = new MediaPlayer(media); // 필드에 대입
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 반복 재생
//        mediaPlayer.setVolume(1.0); // 볼륨 100%
//        mediaPlayer.play();
        
        
        
        /* 오류문구 *mediaPlayer.setOnError(() -> {
            System.out.println("MediaPlayer Error: " + mediaPlayer.getError());
        });

        media.setOnError(() -> {
            System.out.println("Media Error: " + media.getError());
        });
        System.out.println("Loaded Path: " + new File(path).toURI().toString()); */
        
        
    	// 텍스트 박스 배경
    	StackPane statusBox = new StackPane();
    	statusBox.setPadding(new Insets(10));
    	statusBox.setMaxWidth(300);

    	// 반투명 배경 추가
    	BackgroundFill backgroundFill = new BackgroundFill(
    	    Color.rgb(255, 255, 255, 0.7), // 흰색 + 70% 투명도
    	    new CornerRadii(10),
    	    Insets.EMPTY
    	);
    	statusBox.setBackground(new Background(backgroundFill));

    	// 텍스트 스타일
    	statusText.setFill(Color.BLACK); // 검정 글자
    	statusText.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

    	// 텍스트를 박스 안에 넣기
    	statusBox.getChildren().add(statusText);

        // 배경 이미지
    	BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
    	//마지막 false > cover가 아니라 contain처럼 동작 크기에 맞게 늘리기
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:/C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/background.png",false),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(backgroundImage));

        // 캐릭터 이미지
        ImageView characterView = new ImageView(
                new Image("file:/C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/hosik.png")
        );
        characterView.setPreserveRatio(true);
        characterView.fitWidthProperty().bind(primaryStage.widthProperty().multiply(0.2)); // 창 너비의 20%

        // 다음날 버튼 설정
        nextDayButton.setVisible(false);
        nextDayButton.setOnAction(e -> {
            gameManager.nextDay();
            if (gameManager.isGameOver()) {
                showEnding();
            } else {
                updateUI();
            }
        });

        contentBox.setPadding(new Insets(20));
        contentBox.getChildren().addAll(statusBox, buttonBox, nextDayButton);
        contentBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10, characterView, contentBox);
        layout.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(layout);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("수험생 키우기 📝");
        primaryStage.setFullScreen(true); 
        primaryStage.show();

        updateUI();
    }

    private void updateUI() {
        buttonBox.getChildren().clear();
        nextDayButton.setVisible(false);
        updateButtons();
    }
    
        // 버튼 랜덤 생성
        private void updateButtons() {
            buttonBox.getChildren().clear();
            nextDayButton.setVisible(false);

            // 매번 새로운 랜덤 4개 생성
            List<String> possibleActions = Arrays.asList("국어", "영어", "수학", "물리", "화학", "휴식");
            Collections.shuffle(possibleActions);
            List<String> actions = possibleActions.subList(0, 4);  // 4개만 뽑기

            for (String action : actions) {
                Button button = new Button(action + " 선택");
                button.setMinWidth(300);

                // 색상 설정
                switch (action) {
                    case "국어": button.setStyle("-fx-background-color: red; -fx-text-fill: white;"); break;
                    case "영어": button.setStyle("-fx-background-color: green; -fx-text-fill: white;"); break;
                    case "수학": button.setStyle("-fx-background-color: blue; -fx-text-fill: white;"); break;
                    case "화학": button.setStyle("-fx-background-color: orange; -fx-text-fill: white;"); break;
                    case "휴식": button.setStyle("-fx-background-color: white; -fx-text-fill: black;"); break;
                }

                button.setOnAction(e -> {
                    Student student = gameManager.getStudent();
                    boolean success = student.act(action);
                    updateStatus();
                    if (!success || student.isDayOver()) {
                        showNextDayButton();
                    } else {
                        updateButtons();  // 다음 턴용 랜덤 버튼 갱신
                    }
                });

                buttonBox.getChildren().add(button);
            }

            updateStatus();
        }
         
    
    private void updateStatus() {
        Student s = gameManager.getStudent();
        Map<String, Integer> stats = s.getStats();

        statusText.setText(
            "Day " + gameManager.getDay() + "\n" +
            "체력: " + s.getStamina() + "\n" +
            "국어: " + stats.get("국어") + ", 영어: " + stats.get("영어") +
            "\n수학: " + stats.get("수학") + ", 물리: " + stats.get("물리") +
            ", 화학: " + stats.get("화학") +
            "\n턴: " + s.getTurn() + "/10"
        );
    }

    private void showNextDayButton() {
        buttonBox.getChildren().clear();  // 기존 버튼 제거
        nextDayButton.setVisible(true);   // 다음날 버튼 보이기
    }

    private void showEnding() {
        statusText.setText("🎓 " + gameManager.getEnding());
        buttonBox.getChildren().clear();
        nextDayButton.setVisible(false);
    }

 
}
