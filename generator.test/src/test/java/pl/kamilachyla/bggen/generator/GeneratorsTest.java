package pl.kamilachyla.bggen.generator;

import com.kamilachyla.bggen.api.Rect;
import com.kamilachyla.bggen.generator.Generators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeneratorsTest {

    @Test
    public void count() {
        int namesSize = Generators.getNames().size();
        long allGensCount = Generators.all().count();
        Assertions.assertEquals(namesSize, allGensCount);
        Assertions.assertEquals(namesSize, 3);
    }

    @Test
    public void checkTooSmall() {
        var rectList = Generators.byName("grid");
        Assertions.assertTrue(rectList.isPresent());
        Optional<Rect> first = rectList.get().generate(10, 10).findFirst();
        first.ifPresentOrElse(
                r -> {
                    Rect expected = Rect.from(0, 0, 10, 10);
                    Assertions.assertEquals(expected, r);
                    },
                () -> Assertions.fail("One rect should be found"));
    }

    @Test
    public void checkInvalidInput() {
        var rectList = Generators.byName("grid");
        Assertions.assertTrue(rectList.isPresent());
        Optional<Rect> first = rectList.get().generate(10, -3).findFirst();
        first.ifPresent(r -> Assertions.fail());
    }

    @Test
    public void checkTooBig() {
        var rectList = Generators.byName("grid");
        Assertions.assertTrue(rectList.isPresent());
        var li = rectList.get().generate(21, 21).collect(Collectors.toList());
        List<Rect> expected = List.of(
                Rect.from(0, 0, 20, 20),
                Rect.from(20, 0, 1, 20),
                Rect.from(0, 20, 20, 1),
                Rect.from(20, 20, 1, 1)
        );
        expected.forEach( r -> {
            System.out.printf("Test check %s%n", r);
            Assertions.assertTrue(li.contains(r));});
    }
}