(ns graph-paper.figures
  (:require [graph-paper.helpers :as h]))

(defn vertical [x y1 y2]
  (let [dist (h/abs (- y1 y2))
        start (min y1 y2)]
    (if (> dist 0)
      (into {}
          (for [y (range start (+ start dist 1))]
            {[x y] "|"}))
      {})))

(defn horizontal [x1 x2 y]
  (let [dist (h/abs (- x1 x2))
        start (min x1 x2)]
    (if (> dist 0)
      (into {}
          (for [x (range start (+ start dist 1))]
            {[x y] "-"}))
      {})))

(defn line [[x1 y1] [x2 y2]]
  (let [y-diff (h/abs (- y1 y2))
        x-diff (h/abs (- x1 x2))]
    (if (> x-diff y-diff)
      (horizontal x1 x2 y1)
      (vertical x1 y1 y2))))
