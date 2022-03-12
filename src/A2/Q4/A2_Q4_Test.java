package A2.Q4;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

public class A2_Q4_Test {

    @Test
    public void smallTest0() {
        assertEquals("Y", A2_Q4.mod_fibonacci(7, BigInteger.valueOf(7)));
    }

    @Test
    public void smallTest1() {
        assertEquals("Y", A2_Q4.mod_fibonacci(3, BigInteger.valueOf(2)));
    }

    @Test
    public void bigTest0() {
        assertEquals("X", A2_Q4.mod_fibonacci(7777, new BigInteger("474150155627499133")));
    }

    @Test
    public void minimalTest0() {
        assertEquals("X", A2_Q4.mod_fibonacci(1, new BigInteger("1")));
    }
}