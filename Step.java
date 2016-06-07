package entity;

import java.io.Serializable;


/**
 * This class contains coordinates of the sell and a player who made a move
 */
public class Step implements Serializable {
  private static final long serialVersionUID = 5143031257300009288L;
  private double appleX;
  private double appleY;
  private double snakeXBody;
  private double snakeYBody;
  private int length;
  private int numberOfTurns;

  public Step() {}

  public Step(int length, double appleX, double appleY, double snakeXBody, double snakeYBody) {
    this.appleX = appleX;
    this.appleY = appleY;
    this.length = length;
    this.snakeXBody = snakeXBody;
    this.snakeYBody = snakeYBody;
  }

  public Step(Step step) {
    this.appleX = step.appleX;
    this.appleY = step.appleY;
    this.length = step.length;
    this.snakeXBody = step.snakeXBody;
    this.snakeYBody = step.snakeYBody;
  }

  /**
   * @return appleX
   */
  public double getAppleX() {
    return appleX;
  }

  /**
   * @return appleY
   */
  public double getAppleY() {
    return appleY;
  }

  /**
   * @return snakeXBody
   */
  public double getSnakeXBody() {
    return snakeXBody;
  }

  /**
   * @return snakeYBody
   */
  public double getSnakeYBody() {
    return snakeYBody;
  }

  /**
   * @return length
   */
  public int getLength() {
    return length;
  }

  /**
   * @param appleX
   */
  public void setAppleX(double appleX) {
    this.appleX = appleX;
  }

  /**
   * @param appleY
   */
  public void setAppleY(double appleY) {
    this.appleY = appleY;
  }

  /**
   * @param snakeXBody
   */
  public void setSnakeX(double snakeXBody) {
    this.snakeXBody = snakeXBody;
  }

  /**
   * @param snakeYBody
   */
  public void setSnakeY(double snakeYBody) {
    this.snakeYBody = snakeYBody;
  }

  /**
   * @param length
   */
  public void setLength(int length) {
    this.length = length;
  }

  public Step clone() {
    Step newStep = new Step(this);
    return newStep;
  }

  public void getNumberOfTurns(int numberOfTurns) {
    this.numberOfTurns = numberOfTurns;
  }

  public int getNumberOfTurns() {
    return numberOfTurns;
  }
}
