package app.service;

import java.util.Scanner;

/**
 * Get the input of user or use the default test cases
 * @return: the URL
 */
public class UserInputService {

    public String getUserUrl() {
        String url = "";
        Scanner sc = new Scanner(System.in);
        System.out.println("Menu: ");
        System.out.println("Choose an option from below by inputting 1,2,3,4,or 5");
        System.out.println("1) Use test case 1: https://www.amazon.com/Cuisinart-CPT-122-Compact-2-SliceToaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster");
        System.out.println("2) Use test case 2: http://www.cnn.com/2013/06/10/politics/edward-snowden-profile/");
        System.out.println("3) Use test case 3: http://blog.rei.com/camp/how-to-introduce-your-indoorsy-friend-to-the-outdoors/");
        System.out.println("4) Use my own URL...");
        System.out.println("5) Exit");

        int selection = sc.nextInt();
        switch (selection) {
            case 1:
                url = "https://www.amazon.com/Cuisinart-CPT-122-Compact-2-SliceToaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster";
                break;
            case 2:
                url = "http://www.cnn.com/2013/06/10/politics/edward-snowden-profile/";
                break;
            case 3:
                url = "http://blog.rei.com/camp/how-to-introduce-your-indoorsy-friend-to-the-outdoors/";
                break;
            case 4:
                url = sc.next();
                break;
            case 5:
                System.exit(0);
                break;
            default:
                System.out.println("Please input valid number");
                System.exit(1);
                break;
        }

        return url;
    }
}
