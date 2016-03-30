package application;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {
  private final Text text;
  private final Rectangle background;
  private final DropShadow drop;

  public MenuButton(String name) {
    text = new Text(name);
    text.getFont();
    text.setFont(Font.font(60));
    text.setFill(Color.PURPLE);

    background = new Rectangle(300, 80);
    background.setOpacity(0.6);
    background.setFill(Color.SKYBLUE);
    background.setEffect(new GaussianBlur(10));

    setAlignment(Pos.TOP_CENTER);
    setRotate(0);
    getChildren().addAll(background, text);

    setOnMouseEntered(event -> {
      background.setTranslateX(10);
      text.setTranslateX(10);
      background.setFill(Color.WHITE);
      text.setFill(Color.BLUEVIOLET);
    });

    setOnMouseExited(event -> {
      background.setTranslateX(0);
      text.setTranslateX(0);
      background.setFill(Color.SKYBLUE);
      text.setFill(Color.PURPLE);
    });

    drop = new DropShadow(50, Color.WHITESMOKE);
    drop.setInput(new Glow());

    setOnMousePressed(event -> setEffect(drop));
    setOnMouseReleased(event -> setEffect(null));
  }
}

