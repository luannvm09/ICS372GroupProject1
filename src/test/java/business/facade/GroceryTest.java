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
                RandomHelper.randomString(), Calendar.getInstance(), RandomHelper.randomDouble(1.0, 100.0));
    }

    @Test
    public void addMember() {
        // Setup the Request DTO
        Request.instance().setMemberName(testMember.getMemberName());
        Request.instance().setMemberAddress(testMember.getMemberAddress());
        Request.instance().setMemberPhoneNumber(testMember.getMemberPhoneNumber());
        Request.instance().setDateJoined(testMember.getDateJoined());
        Request.instance().setFeePaid(testMember.getFeePaid());
        grocery.addMember(Request.instance());
        /**
         * Iterate through all the members and ensure that at least one returned member
         * has the same instance values as the testMember
         */
        boolean memberPresent = false;
        Iterator<Member> members = grocery.getMembers();
        while (members.hasNext()) {
            Member fetchedMember = members.next();
            if (testMember.getMemberName() == fetchedMember.getMemberName()) {
                Calendar testJoinDate = testMember.getDateJoined();
                Calendar fetchedJoinDate = fetchedMember.getDateJoined();
                assertEquals(testMember.getDateJoined(), fetchedMember.getDateJoined());
                assertEquals(testMember.getMemberName(), fetchedMember.getMemberName());
                assertEquals(testMember.getMemberAddress(), fetchedMember.getMemberAddress());
                assertEquals(testMember.getMemberPhoneNumber(), fetchedMember.getMemberPhoneNumber());
                assertEquals(testMember.getFeePaid(), fetchedMember.getFeePaid(), 0.001);
                memberPresent = true;
            }
            members.remove();
        }

        assertTrue("Added member was present in members list.", memberPresent);

    }
}