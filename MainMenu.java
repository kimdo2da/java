package HIHI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;


public class MainMenu extends Application {
	private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
    	//미디어
    	String path = "C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/music2.mp3";
    	Media media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media); // 필드에 대입
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // 반복 재생
        mediaPlayer.setVolume(1.0); // 볼륨 100%
        mediaPlayer.play();
        // 타이틀 텍스트
        Text title = new Text("수험생 키우기 🎓");
        title.setFont(new Font("Arial", 40));

        // 버튼들
        Button startButton = new Button("게임 시작");
        Button guideButton = new Button("게임 방법");

        startButton.setStyle("-fx-font-size: 18px;");
        guideButton.setStyle("-fx-font-size: 18px;");

        // 버튼 클릭 이벤트
        startButton.setOnAction(e -> {
            Main game = new Main();  // 너의 게임 본편 클래스!
            try {
                game.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        guideButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("게임 방법");
            alert.setHeaderText("수험생 키우기 게임 방법 🎮");
            alert.setContentText(
                "📚 하루에 최대 10턴 동안 무작위로 나오는 과목과 휴식 중 하나를 선택해 공부하세요!\n" +
                "💤 체력이 부족하면 자동으로 하루가 끝나고, 다음 날이 시작됩니다.\n" +
                "🎓 30일 동안 국어, 영어, 수학, 물리, 화학 능력치를 키워 대학에 도전해보세요!\n\n" +
                "✅ 과목 버튼을 누르면 능력치가 증가하고 체력이 감소합니다.\n" +
                "✅ 휴식을 선택하면 체력을 회복할 수 있어요.\n" +
                "🔥 최종 능력치 총합으로 대학에 합격할 수 있을지 결정됩니다!\n\n" +
                "좋은 결과를 위해 전략적으로 공부해봐요! 😎"
            );
            alert.showAndWait();
        });

        // 버튼과 타이틀 배치
        VBox vbox = new VBox(20, title, startButton, guideButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 800, 600); // 배경 없는 화면 사이즈

        primaryStage.setTitle("수험생 키우기 - 메인 메뉴");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}