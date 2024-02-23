import java.util.ArrayList;

public class TetrisShape {
    private ArrayList<Rectangle> blocks;
    private int[][] shape;
    private int x, y; // Position of the top-left block

    public TetrisShape(int[][] shape, int x, int y, String color) {
        blocks = new ArrayList<>();
        this.shape = shape;
        this.x = x;
        this.y = y;

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    Rectangle block = new Rectangle(x + j * 30, y + i * 30, 30, 30, color);
                    blocks.add(block);
                }
            }
        }
    }
    public int getX() {
        return x; 
    }

    public int getY() {
        return y; 
    }

    public void moveDown() {
        y += 30;
        for (Rectangle block : blocks) {
            block.setYPosition(block.getYPosition() + 30);
        }
    }

    public void moveLeft() {
        x -= 30;
        for (Rectangle block : blocks) {
            block.setXPosition(block.getXPosition() - 30);
        }
    }

    public void moveRight() {
        x += 30;
        for (Rectangle block : blocks) {
            block.setXPosition(block.getXPosition() + 30);
        }
    }

    public void rotate() {
        int[][] rotatedShape = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                rotatedShape[j][shape.length - 1 - i] = shape[i][j];
            }
        }
        shape = rotatedShape;
        updateBlocksPosition();
    }

    private void updateBlocksPosition() {
        blocks.clear();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    Rectangle block = new Rectangle(x + j * 30, y + i * 30, 30, 30, "BLUE");
                    blocks.add(block);
                }
            }
        }
    }

    public ArrayList<Rectangle> getBlocks() {
        return blocks;
    }
}

