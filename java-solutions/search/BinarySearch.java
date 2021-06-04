package search;

import java.util.Objects;


public class BinarySearch {
    // Pred*: args != null && args.length > 0 &&
    //       ∀ i ∈ [1;args.length): (args[i] has Integer representation) &&
    //       ∀ i,j ∈ [1;args.length): (i < j -> (args[i] as a number >= args[j] as a number))
    // Post: prints result to System.out
    public static void main(final String[] args) {
        Objects.requireNonNull(args);
        assert args.length > 0;
        // Pred: Pred*
        // Post: x = Integer.parseInt(args[0]) && Pred
        int x = Integer.parseInt(args[0]);

        // Pred: Pred*
        // Post: array is an instance of an array with length of args.length - 1
        int[] array = new int[args.length - 1];

        // Inv: array filled with int representation of input in range of [0;i)
        for (int i = 0; i < array.length; i++) {
            // Pred: args != null
            // Post: array[i] = Integer.parseInt(args[i+1]) && i = i' + 1
            array[i] = Integer.parseInt(args[i + 1]);
        }
        // array = instance of an array with length of args.length - 1 && filled with int representation of input && Pred*

//         System.out.println(iterativeBinarySearch(array, x));
        System.out.println(recursiveBinarySearch(array, x));
    }

    // Pred: array != null && ∀ i,j ∈ [0;array.length): (i < j -> array[i] >= array[j]) @sorted
    // let array[-1] = -INFINITY, array[array.length] = +INFINITY
    // Inv: Pred
    // Post: R = min {i | array[i] <= x}
    public static int iterativeBinarySearch(final int[] array, final int x) {
        Objects.requireNonNull(array);
        // Pred: true
        // Post: l == -1
        int l = -1;

        // Pred: true
        // Post: r == array.length && l == -1
        int r = array.length;

        // Inv: R ∈ (l;r] && sorted
        while (r - l != 1) {
            // Pred: r - l != 1
            // Post: m == (l + r) / 2
            int m = (l + r) / 2;
            if (array[m] > x) {
                // Pred: array[m] > x
                // Post: (l' == m && r' == r) && l < m -> Inv && (r' - l' > r - l) && array[l] > x
                l = m;
            } else {
                // Pred: array[m] <= x
                // Post: (l' == l && r' == m) && m <= r -> Inv && (r' - l' > r - l) && array[r] <= x
                r = m;
            }
            // Inv && (r' - l' > r - l)
        }

        // (Inv && (r - l == 1)) -> ((R ∈ (r - 1;r] == (l;r] && (array[l] > x >= array[r]) -> R = r)) -> R = min {i | array[i] <= x}
        return r;
    }

    // Pred: array != null && ∀ i,j ∈ [0;array.length): (i < j -> array[i] >= array[j]) @sorted
    // let array[-1] = -INF && array[array.length] = +INF
    // Inv: R ∈ (l;r] && sorted
    // Post: R = min {i | array[i] <= x}
    public static int recursiveBinarySearch(final int[] array, final int x) {
        Objects.requireNonNull(array);
        // Pred: array != null && ∀ i,j ∈ [0;array.length): (i < j -> array[i] >= array[j])
        // Post: R = min {i | array[i] <= x}
        return recursiveBinarySearch(array, x, -1, array.length);
    }

    // Pred*: array != null && -1 <= l < r <= array.length && ∀ i,j ∈ [0;array.length): (i < j -> array[i] >= array[j]) @sorted
    // let array[-1] = -INF && array[array.length] = +INF
    // Inv: R ∈ (l;r] && sorted
    // Post: R = min {i | array[i] <= x}
    public static int recursiveBinarySearch(final int[] array, final int x, final int l, final int r) {
        Objects.requireNonNull(array);
        if (r - l == 1) {
            // Pred: r - l == 1
            // Post: Inv && r - l == 1 -> R = min {i | array[i] <= x}
            return r;
        }
        // Pred: r - l != 1
        // Post: m == (l + r) / 2 && Inv
        int m = (l + r) / 2;
        if (array[m] > x) {
            // Pred: (l' == m && r' == r) && l < m -> Inv && (r' - l' > r - l) && array[l] > x && Pred*
            // Post: R = min {i | array[i] <= x}
            return recursiveBinarySearch(array, x, m, r);
        }
        // Pred: (l' == l && r' == m) && m < r -> Inv && (r' - l' > r - l) && array[r] <= x && Pred*
        // Post: R = min {i | array[i] <= x}
        return recursiveBinarySearch(array, x, l, m);
    }
}