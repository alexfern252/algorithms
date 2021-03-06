package iurii.job.interview.amazon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Amazon task
 * <p>
 * There is a list of objects.
 * Return list of objects, which number is odd in list (according to equals test).
 * Ordering can be no sorting, ordering by appearance in the list, ordering by natural order or Comparator
 * <p>
 * Time complexity O(n) for traversal
 * Auxiliary space O(n) for storing in LinkedHashMap/LinkedHashSet/etc
 * Created by iurii.dziuban on 19/05/2017.
 */
public class TakeOddObjects {

    //General ideas:
    // Objects should be immutable or copied before going to method
    // - otherwise we can not guarantee that someone could change internals.

    // Potentially Map quicker than Set
    // , but in case small amount of duplicates Set solution can be good
    // Note: order might be important

    // Approach 1. HashMap and ArrayList. General Type

    /**
     * Good.
     * 1) Easy approach
     * 2) Quick in general if hashcode is ok. 3 times O(n)
     * 3) Null elements are possible
     * 4) Preconditions
     * Bad
     * 1) No concreate types
     * 2) No order
     * 3) No support for mutability - we can not do much
     * 4) A lot of memory for HashMap and List
     */
    public List<Object> takeOddHashMapAndArrayListNoOrderingOrSortingNotTypeSafed(List<Object> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        Map<Object, Integer> countMap = new HashMap<>();
        for (Object obj : list) {
            countMap.merge(obj, 1, (oldValue, value) -> value + oldValue);
        }
        List<Object> oddObjects = new ArrayList<>();
        for (Map.Entry<Object, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() % 2 == 1) {
                oddObjects.add(entry.getKey());
            }
        }
        return oddObjects;
    }

    // Approach 2. HashMap and ArrayList. Concreate type

    /**
     * Good.
     * 1) Easy approach
     * 2) Concreate type
     * 3) Quick in general if hashcode is ok 3 times O(n)
     * 4) Null elements are possible
     * 5) Preconditions
     * Bad
     * 1) No order
     * 2) No support for mutability - we can not do much
     * 3) A lot of memory for HashMap and List
     */
    public <T> List<T> takeOddHashMapAndArrayListNoOrderingTypeSafe(List<T> list) {
        if (list == null) {
            return new ArrayList<T>();
        }
        Map<T, Integer> countMap = new HashMap<>();
        fillCountMap(list, countMap);
        List<T> oddObjects = new ArrayList<>();
        for (Map.Entry<T, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() % 2 == 1) {
                oddObjects.add(entry.getKey());
            }
        }
        return oddObjects;
    }

    // Approach 3. LinkedHashMap and ArrayList. Concrete type

    /**
     * Good.
     * 1) Easy approach
     * 2) Concreate type
     * 3) Quick in general if hashcode is ok.
     * 4) Order by appearance
     * 5) Null elements are possible
     * 6) Preconditions
     * Bad
     * 1) No support for mutability - we can not do much
     */
    public <T> List<T> takeOddLinkedHashMapAndArrayListTypeSafeOrderByAppearance(List<T> list) {
        if (list == null) {
            return new ArrayList<T>();
        }
        Map<T, Integer> countMap = new LinkedHashMap<>();
        return fillSortedMapAndGetListOfOddObjects(list, countMap);
    }

    private <T> List<T> fillSortedMapAndGetListOfOddObjects(List<T> list, Map<T, Integer> countMap) {
        fillCountMap(list, countMap);

        return countMap.entrySet().stream().filter(entry -> entry.getValue() % 2 == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    // Approach 4. LinkedHashSet and HashSet for duplicates. Concreate type

    /**
     * Good.
     * 1) Easy approach
     * 2) Concreate type
     * 3) Quick in general if hashcode is ok. O n log n
     * 4) Order by appearance
     * 5) Null elements are possible
     * 6) Preconditions
     * Bad
     * 1) No support for mutability - we can not do much
     * 2) More memory than LinkedHashMap
     * 3) More operations than LinkedHashMap
     */
    public <T> List<T> takeOddLinkedHashSetAndHashSetForDuplicatesTypeSafeOrderByAppearance(List<T> list) {
        if (list == null) {
            return new ArrayList<T>();
        }
        Set<T> countSet = new LinkedHashSet<T>();
        Set<T> duplicatedSet = new HashSet<T>();
        for (T obj : list) {
            if (!countSet.add(obj) && !duplicatedSet.add(obj)) {
                duplicatedSet.remove(obj);
            }
        }
        countSet.removeAll(duplicatedSet);
        return new ArrayList<T>(countSet);
    }

    // Approach 5. TreeMap and ArrayList from it. Concreate type. Default Comparator

    /**
     * Good.
     * 1) Easy approach
     * 2) Concreate type
     * 3) Quick in general if hashcode is ok.
     * 4) Order by natural ordering
     * 5) Preconditions
     * Bad
     * 1) No support for mutability - we can not do much
     * 2) Null elements are not possible
     */
    public <T> List<T> takeOddTreeMapAndArrayListFromItOrderByDefaultComparator(List<T> list) {
        if (list == null) {
            return new ArrayList<T>();
        }
        Map<T, Integer> countMap = new TreeMap<>();

        return fillSortedMapAndGetListOfOddObjects(list, countMap);
    }

    // Approach 6. TreeMap and ArrayList from it. Exact type. Provided comparator

    /**
     * Good.
     * 1) Easy approach
     * 2) Concreate type
     * 3) Quick in general if hashcode is ok.
     * 4) Order by comparator
     * 5) Preconditions
     * 6) Null elements are possible depending on comparator
     * Bad
     * 1) No support for mutability - we can not do much
     */
    public <T> List<T> takeOddTreeMapAndArrayListFromItOrderByComparator(List<T> list, Comparator<T> comparator) {
        if (list == null) {
            return new ArrayList<T>();
        }
        Map<T, Integer> countMap = new TreeMap<>(comparator);
        return fillSortedMapAndGetListOfOddObjects(list, countMap);
    }

    private <T> void fillCountMap(List<T> list, Map<T, Integer> countMap) {
        for (T obj : list) {
            countMap.merge(obj, 1, (oldValue, value) -> value + oldValue);
        }
    }
}
