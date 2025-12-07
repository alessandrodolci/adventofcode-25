(require '[clojure.string :as string])
(require '[clojure.java.io :as io])

(defn has-id-two-identical-halves? [id] (let [half-length (quot (count id) 2)
                                              first-half (subs id 0 half-length)
                                              second-half (subs id half-length (count id))]
                                          (= first-half second-half)))

(defn has-id-only-identical-seqs? [id] (some (fn [len]
                                               (and (= (mod (count id) len) 0)
                                                    (apply = (partition len id))))
                                             (range 1 (+ (quot (count id) 2) 1))))

(with-open [rdr (io/reader "input/day2.txt")]
  (let [ranges (string/split (first (line-seq rdr)) #",")
        result (reduce (fn [acc current-range]
                         (let [start (first (string/split current-range #"\-"))
                               end (nth (string/split current-range #"\-") 1)]
                           (+ acc (reduce (fn [acc id]
                                            (+ acc (if (has-id-only-identical-seqs? (str id)) id 0)))
                                          0
                                          (range (Long/parseLong start) (+ (Long/parseLong end) 1))))))
                       0
                       ranges)]
    (string/join ["The sum of all the invalid ids is " result])))
