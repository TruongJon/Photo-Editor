import static org.junit.Assert.assertEquals;
import model.Posn;
import org.junit.Test;

/**
 * To test the methods within the Pixel class.
 */
public class PosnTest {
  //to test the Posn constructor.
  Posn p1 = new Posn(0, 0);
  Posn p2 = new Posn(0, 1);
  Posn p3 = new Posn(0, 2);
  Posn p4 = new Posn(1, 0);
  Posn p5 = new Posn(1, 1);
  Posn p6 = new Posn(1, 2);
  Posn p7 = new Posn(2, 0);
  Posn p8 = new Posn(2, 1);
  Posn p9 = new Posn(2, 2);

  //to test the getX method in the Posn class.
  @Test
  public void testGetX() {
    assertEquals(0, p1.getX());
    assertEquals(0, p2.getX());
    assertEquals(0, p3.getX());
    assertEquals(1, p4.getX());
    assertEquals(1, p5.getX());
    assertEquals(1, p6.getX());
    assertEquals(2, p7.getX());
    assertEquals(2, p8.getX());
    assertEquals(2, p9.getX());
  }

  //to test the getY method in the Posn class.
  @Test
  public void testGetY() {
    assertEquals(0, p1.getY());
    assertEquals(0, p4.getY());
    assertEquals(0, p7.getY());
    assertEquals(1, p2.getY());
    assertEquals(1, p5.getY());
    assertEquals(1, p8.getY());
    assertEquals(2, p3.getY());
    assertEquals(2, p6.getY());
    assertEquals(2, p9.getY());
  }

  //to test the move method in the Posn class.
  @Test
  public void testMove() {
    assertEquals(1, p1.move(1, 1).getX());
    assertEquals(1, p1.move(1, 1).getY());
    assertEquals(1, p9.move(-1, -1).getX());
    assertEquals(1, p9.move(-1, -1).getY());
    //to check that the x-position can be negative while the y-position remains positive.
    assertEquals(-1, p5.move(-2, 0).getX());
    assertEquals(1, p5.move(-2, 0).getY());
    //to check that the y-position can be negative while the x-position remains positive.
    assertEquals(1, p5.move(0, -2).getX());
    assertEquals(-1, p5.move(0, -2).getY());
    //to check that the x-position and y-position can be negative.
    assertEquals(-1, p5.move(-2, -2).getX());
    assertEquals(-1, p5.move(-2, -2).getY());
  }
}