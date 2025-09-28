public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Test 1 starts");
            test1();
        } catch (Exception e) {
            System.out.println("exception " + e);
        } finally {
            System.out.println("Test 1 done");
            System.out.println("--------------------------------------------");
        }
        try {
            System.out.println("Test 2 starts");
            test2();
        } catch (Exception e) {
            System.out.println("exception " + e);
        } finally {
            System.out.println("Test 2 done");
            System.out.println("--------------------------------------------");
        }
        try {
            System.out.println("Test 3 starts");
            test3();
        } catch (Exception e) {
            System.out.println("exception " + e);
        } finally {
            System.out.println("Test 3 done");
            System.out.println("--------------------------------------------");
        }

        try {
            System.out.println("Test 4 starts");
            test4();
        } catch (Exception e) {
            System.out.println("exception " + e);
        } finally {
            System.out.println("Test 4 done");
            System.out.println("--------------------------------------------");
        }

        try {
            System.out.println("Test 5 starts");
            test5();
        } catch (Exception e) {
            System.out.println("exception " + e);
        } finally {
            System.out.println("Test 5 done");
            System.out.println("--------------------------------------------");
        }
    }

    public static void test1(){
        Game game = new Game(3, 3, 3, 2, 1);
        game.runGame();
        System.out.println("Run the game again.");
        game.runGame();
    }

    public static void test2(){
        Board board1 = new Board(3,3,3,3);
        Board board2 = new Board(3,3,3,3);

        System.out.println("Is board1 equal to board2: " + board1.equals(board2));
        System.out.println("Is board2 equal to board1: " + board2.equals(board1));
    }

    public static void test3(){
        Game game = new Game(4, 4, 2, 2, 10);
        game.runGame();
    }

    public static void test4(){
        Game game = new Game(4, 4, 16, 2, 10);
        game.runGame();
    }

    public static void test5(){
        Game game = new Game(12, 12, 16, 2, 100);
        game.runGame();
    }
}
