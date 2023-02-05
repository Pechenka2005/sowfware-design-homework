package tests;

import junit.framework.Assert;
import junit.framework.TestCase;
import main.LRUCache;
import org.testng.annotations.Test;

import java.util.Optional;

public class LRUCacheTest extends TestCase {

    @Test
    public void checkSize() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        int s1 = cache.size();
        boolean e1 = cache.isEmpty();
        cache.put(1, 2);
        cache.put(2, 1);
        Assert.assertEquals(s1, 0);
        Assert.assertTrue(e1);
        Assert.assertEquals(cache.size(), 2);
        Assert.assertFalse(cache.isEmpty());
    }

    @Test
    public void checkCorrectPut() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(10);
        cache.put(1, 2);
        cache.put(2, 1);
        Optional<Integer> g1 = Optional.ofNullable(cache.get(1));
        Optional<Integer> g2 = Optional.ofNullable(cache.get(2));
        Assert.assertEquals(g1, Optional.of(2));
        Assert.assertEquals(g2, Optional.of(1));
    }
}
