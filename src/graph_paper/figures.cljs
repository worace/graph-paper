(ns graph-paper.figures
  (:require [graph-paper.helpers :as h]))

(defn y-diff [x y1 y2]
  (let [dist (h/abs (- y1 y2))
        start (min y1 y2)]
    (if (> dist 0)
      (into {}
          (for [y (range start (+ start dist 1))]
            {[x y] "|"}))
      {})))

(defn x-diff [x1 x2 y]
  (let [dist (h/abs (- x1 x2))
        start (min x1 x2)]
    (if (> dist 0)
      (into {}
          (for [x (range start (+ start dist 1))]
            {[x y] "-"}))
      {})))

(defn line [[x1 y1] [x2 y2]]
  (merge (y-diff (max x1 x2) y1 y2)
         (x-diff x1 x2 (min y1 y2))))
