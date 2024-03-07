import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AESTest {
    @Test
    public void testAdd() {
        int result = AES.add(1);
        assertEquals(result, 2);
        assertEquals(result, 3);
    }
}
