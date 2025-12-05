(require '[clojure.string :as string])
(require '[clojure.java.io :as io])

(defn get-rotation [direction clicks-number] (let [clicks (Integer/parseInt clicks-number)]
                                               (if (= direction \L) (- clicks) clicks)))

(with-open [rdr (io/reader "input/day1.txt")]
  (let [[final-position zero-count]
        (reduce (fn [[current-position count] row]
                  (let [rotation (get-rotation (first row) (subs row 1))
                        new-position (mod (+ current-position rotation) 100)
                        stops-at-zero? (= new-position 0)
                        steps (range (abs rotation))
                        sum (if (neg? rotation) - +)
                        clicks-at-zero (reduce (fn [acc i]
                                                 (+ acc (if (= (mod (sum current-position i) 100) 0) 1 0)))
                                               steps)
                        zero-count (+ (if stops-at-zero? 1 0)
                                      clicks-at-zero)]
                    [new-position (+ count zero-count)]))
                [50 0]
                (line-seq rdr))]
    (string/join
     ["Final position: " final-position ", number of times the dial pointed at zero: " zero-count])))
