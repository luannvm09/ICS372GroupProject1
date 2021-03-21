package business.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Iterator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import business.entities.Member;
import helpers.RandomHelper;

public class GroceryTest {
    Grocery grocery;
    Member testMember;

    @BeforeClass
    public static void initGrocery() {
        System.setOut(System.out);
    }

    @Before
    public void setup() {
        this.grocery = Grocery.instance();
        this.testMember = new Member(RandomHelper.randomString(), RandomHelper.randomString(),
                RandomHelper.randomString(), Calendar.getInstance(),
                RandomHelper.randomDouble(1.0, 100.0));
    }
}
