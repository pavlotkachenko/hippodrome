import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HippodromeTestSuite {
    @Test
    void testConstructorNullParameter() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null), "Horses cannot be null.");
    }

    @Test
    void testConstructorEmptyList() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()), "Horses cannot be empty.");
    }

    @Test
    void testGetHorsesReturnsSameList() {
        List<Horse> horses = createDummyHorses(30);
        Hippodrome hippodrome = new Hippodrome(horses);

        List<Horse> returnedHorses = hippodrome.getHorses();

        assertEquals(horses.size(), returnedHorses.size());
        for (int i = 0; i < horses.size(); i++) {
            assertSame(horses.get(i), returnedHorses.get(i));
        }
    }

    private List<Horse> createDummyHorses(int count) {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            horses.add(new Horse("Horse " + (i + 1), 10.0, 100.0));
        }
        return horses;
    }

    @Test
    void testMoveCallsMoveOnAllHorses(){
        List<Horse> mockHorses = createMockHorses(50);
        Hippodrome hippodrome = new Hippodrome(mockHorses);

        hippodrome.move();

        for (Horse horse : mockHorses){
            verify(horse, times(1)).move();
        }
    }

    private List<Horse> createMockHorses(int count){
        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < count; i++){
            Horse mockHorse = Mockito.mock(Horse.class);
            mockHorses.add(mockHorse);
        }
        return mockHorses;
    }

    @Test
    void testGetWinnerReturnsHorseWithMaxDistance(){
        List<Horse> horses = createDummyHorses(10);   // Create 10 dummy horses
        horses.get(0).move();                               // Move one of the horses to increase its distance

        Hippodrome hippodrome = new Hippodrome(horses);
        Horse winner = hippodrome.getWinner();

        assertEquals(horses.get(0), winner); // Verify that the horse with the max distance is returned as winner
    }
}
