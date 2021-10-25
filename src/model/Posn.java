package model;

/**
 * A class to represent the x and y position of an object.
 */
public class Posn {

  private final int x;
  private final int y;

  /**
   * Constructs a Posn (position) based on x and y graphical coordinates.
   *
   * @param x the x-position
   * @param y the y-position
   */
  public Posn(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Gets the x-position of this Posn.
   *
   * @return the x-position
   */
  public int getX() {
    return x;
  }

  /**
   * Gets the y-position of this Posn.
   *
   * @return the y-position
   */
  public int getY() {
    return y;
  }

  /**
   * Translates the x and y position of this Posn based on given deltas.
   *
   * @param dx the change in x-position
   * @param dy the change in y-position
   * @return the new position
   */
  public Posn move(int dx, int dy) {
    return new Posn(x + dx, y + dy);
  }
}
