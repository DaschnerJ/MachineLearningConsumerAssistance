import java.util.ArrayList;

public class Random {

    public static void main(String[] args)
    {
        for(int i = 0; i < 1154; i++)
        {
            int a = getNum(0,5);
            if(a == 0)
            System.out.println("Yes");
            else
                System.out.println("No");
        }
    }

    public static int getNum(int min, int max)
    {
        Random r = new Random();
        int random = (int )(Math.random() * max + min);
        return random;
    }
}
