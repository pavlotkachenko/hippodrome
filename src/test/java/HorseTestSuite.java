import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.mockito.MockedStatic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class HorseTestSuite {

    @Test
    void testNullNameParameter() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(null, 10.0, 100.0));

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r", "\t \n"})
    void testEmptyOrBlankNameParameter(String name){
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 10.0, 100.0));

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void testNegativeSpeedParameter(){
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Spirit", -5.0, 100.0));

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void testNegativeDistanceParameter() {
        Throwable exception = assertThrows(IllegalArgumentException.class,
                () -> new Horse("Spirit", 10.0, -100));

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void testGetDistanceReturnsInitialDistance(){
        double initialDistance = 100.0;
        Horse horse = new Horse("Spirit", 10.0, 100.0);

        assertEquals(initialDistance, horse.getDistance());
    }

    @Test
    void testGetDistanceReturnsZeroWithTwoParamConstructor(){
        Horse horse = new Horse("Spirit", 10.0);
        assertEquals(0.0, horse.getDistance());
    }

    @Test
    void testGetNameReturnsInitialName(){
        String initialName = "Spirit";
        Horse horse = new Horse(initialName, 10.00, 100.0);
        assertEquals(initialName, horse.getName());
    }

    @Test
    void testGetSpeedReturnsInitialSpeed(){
        double initialSpeed = 10.0;
        Horse horse = new Horse("Spirit", 10.0, 100.0);
        assertEquals(initialSpeed, horse.getSpeed());
    }

    @Test
    void testMoveMethodCallsGetRandomDouble(){
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)){

            Horse horse = new Horse("Spirit", 10.0, 100.0);
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @Test
    void testMoveMethodCalculatesDistanceCorrectly() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse("Spirit", 10.0, 100.0);

            //Mocking get getRandomDouble to return a specific value
            double mockRandomValue = 0.5;
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(mockRandomValue);

            double initialDistance = horse.getDistance();
            horse.move();
            double expectedDistance = initialDistance + 10.0 * mockRandomValue;

            assertEquals(expectedDistance, horse.getDistance());
        }
    }

}
