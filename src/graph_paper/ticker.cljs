(ns graph-paper.ticker)

;; auto-ticker for generating unique ids
(def ticker
  (let [tick (atom 0)]
    #(swap! tick inc)))
(defn tick [] (first (repeatedly ticker)))
