(ns graph-paper.grid)

(defn generate [width height]
  (into (sorted-map)
        (for [x (range 0 width)
              y (range 0 height)]
          {[x y] "."})))
