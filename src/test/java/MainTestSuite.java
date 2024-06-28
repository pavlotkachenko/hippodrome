import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

public class MainTestSuite {

    @Test
    @Timeout(value = 22)
    @Disabled("Disabled until manual execution is needed")
    void testMainTimeout(){
        assertTimeoutPreemptively(Duration.ofSeconds(22), () -> {
            Main.main(new String[]{});
        });
    }
}
