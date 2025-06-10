package HIHI; //패키지 선언

import javafx.application.Application; //java fx 기본 클래스 launch()나 start()를 제공
import javafx.scene.Scene;//화면 나타냄 버튼,텍스트,ui요소
import javafx.scene.control.Alert; // 팝업창을 띄우기 위함.
import javafx.scene.control.Button; //말 그대로 버튼 생성용 클래스
import javafx.scene.text.Text; // 일반 텍스트 표시용
import javafx.scene.text.Font; //글꼴 스타일 지정
import javafx.scene.layout.*; //VBox나 AnchorPane을 사용하기 위해
import javafx.scene.image.Image; //이미지 
import javafx.stage.Stage; //창을 의미 실제 윈도우창 자체와 내용
import javafx.geometry.Insets; // 여백을 설정할때 사용됨
import javafx.geometry.Pos; // vBox나 stackpane 안에서 어디에 정렬할지 설정 센터나 오른쪽 등
import javafx.scene.media.Media; //음악 미디어 파일 객체 생성용
import javafx.scene.media.MediaPlayer; // 실제 재생 컨트롤러
import javafx.scene.paint.Color; // 색
import javafx.scene.effect.DropShadow; //그림자

import java.io.File; //로컬 파일의 경로를 가져올때 사용 new File(path).toURI().toString() 이 코드 
//자바 FX관련 기능들 버튼, 텍스트, 배치와 음악재생용 클래스, 로컬 음악파일 불러올때 쓰는 것.

public class MainMenu extends Application { //Application을 상속받음. 실행하면 start부터 작동
	private MediaPlayer mediaPlayer; //음악 재생 객체 클래스 전체에서 사용되도록 필드로 선언.

    @Override
    public void start(Stage primaryStage) { //실행
    	//미디어,  음악 파일 경로를 지정해서 MediaPlayer로 재생 시작 
    	String path = "C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/music2.mp3";
    	Media media = new Media(new File(path).toURI().toString());//로컬 음악파일
        mediaPlayer = new MediaPlayer(media); // 필드에 대입
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //setCycleCount(INDEFINITE)로 무한 반복 재생
        mediaPlayer.setVolume(1.0); // 볼륨 100%
        mediaPlayer.play(); // 실제 재생
        // 타이틀 텍스트
        Text title = new Text("인서울 챌린지 🎓");
        title.setFont(new Font("Arial", 40)); //폰트와 크기
        title.setFill(Color.WHEAT);  // 또는 LIGHTYELLOW
        
        
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        shadow.setColor(Color.rgb(0, 0, 0, 0.4)); // 부드러운 그림자
        title.setEffect(shadow);
        
        
        
        // 버튼 생성, 크기 키우기
        Button startButton = new Button("게임 시작");
        Button guideButton = new Button("게임 방법");
        Button musicToggleButton = new Button("🎵 음악 끄기");
        //버튼 정렬
        startButton.setStyle("-fx-font-size: 18px;");
        guideButton.setStyle("-fx-font-size: 18px;");
        musicToggleButton.setStyle("-fx-font-size: 14px;");
        
        //음악 on/off
        musicToggleButton.setOnAction(e -> {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                musicToggleButton.setText("🎵 음악 켜기");
            } else {
                mediaPlayer.play();
                musicToggleButton.setText("🎵 음악 끄기");
            }
        });

        // 버튼 클릭 이벤트
        startButton.setOnAction(e -> {
            Main game = new Main();  //버튼을 누르면 Main 클래스로 화면전환 
            try {
                game.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //게임 방법 버튼 이벤트
        guideButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); //Alert창이 팝업으로뜸
            alert.setTitle("게임 방법");
            alert.setHeaderText("인서울 챌린지 게임 방법 🎮");
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

        // 버튼과 타이틀 배치 타이틀,시작버튼,방법버튼순서로 세로정렬되고 중앙정렬됨
        VBox centerBox = new VBox(20, title, startButton, guideButton); //수직으로 노드를 쌓는 레이아웃
        centerBox.setAlignment(Pos.CENTER); //가운데 정렬하라는거
        centerBox.setPrefWidth(800);
        centerBox.setPrefHeight(600); 
        
        // 🎨 전체 레이아웃 AnchorPane 사용
        AnchorPane anchorPane = new AnchorPane(); // 노드를 특정 위치에 고정 시킬수 있음
        AnchorPane.setTopAnchor(centerBox, 150.0); //위에서 150떨어지게
        AnchorPane.setLeftAnchor(centerBox, 0.0);
        AnchorPane.setRightAnchor(centerBox, 0.0); //좌우 0으로 centerBox 가운데 고정처럼
        
        AnchorPane.setTopAnchor(musicToggleButton, 10.0); //음악 토글 위에서 10떨어지게
        AnchorPane.setRightAnchor(musicToggleButton, 10.0); //우측 10 여백줌
        
        anchorPane.getChildren().addAll(centerBox, musicToggleButton); //중앙 정렬된 메인버튼과 음악토글을 자식노드로 추가
        
        Scene scene = new Scene(anchorPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("인서울 챌린지 - 메인 메뉴");
        primaryStage.show();
        //뒷배경
        BackgroundImage bgImage = new BackgroundImage(
        	    new Image("file:///C:/Users/김도현/Desktop/moum/java/eclipse/JavaFx/src/Images/city1.png"),
        	    BackgroundRepeat.NO_REPEAT,
        	    BackgroundRepeat.NO_REPEAT,
        	    BackgroundPosition.CENTER,
        	    new BackgroundSize(100, 100, true, true, true, false) // < 이게 최종 size 설정
        	);
        	anchorPane.setBackground(new Background(bgImage));
      
    }

    public static void main(String[] args) { //main()부터 메서드 실행
        launch(args); //launch()는 Application 클래스의 static 메서드로 
    } // javafx시스템 초기화 및 start()를 자동으로 호출해줌
}




//main메서드 > launch() > 시스템 초기화 > start()자동호출 > 기능 실행.