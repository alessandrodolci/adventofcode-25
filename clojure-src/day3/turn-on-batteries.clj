(require '[clojure.string :as string])
(require '[clojure.java.io :as io])

; Dumb solution, ok for part 1
(defn get-max-joltage-with-two-batteries [bank]
  (apply max (flatten (map (fn [index]
                       (map (fn [second-battery]
                              (Integer/parseInt (str (get bank index) second-battery))) (nthnext bank (+ index 1))))
                     (range (count bank))))))

; Better and more general solution
(defn get-best-n-batteries [n batteries result index]
  (if (= index n)
    (string/join result)
    (let [best (apply max (drop-last (- n 1 index) batteries))
          remaining (drop (+ (string/index-of (string/join batteries) (str best)) 1) batteries)]
      (print (conj result best))
      (print remaining)
      (println)
      (get-best-n-batteries n remaining (conj result best) (+ index 1)))))

(defn get-max-joltage-with-n-batteries [bank n]
  (let [best-battery (get-best-n-batteries n (map (fn [char-digit] (Character/digit char-digit 10)) (seq bank)) [] 0)]
    (Long/parseLong best-battery)))

(with-open [rdr (io/reader "input/day3.txt")]
  (let [banks (line-seq rdr)
        result (reduce (fn [acc bank] (+ acc (get-max-joltage-with-n-batteries bank 12))) 0 banks)]
    (string/join ["The total output joltage is " result])))
