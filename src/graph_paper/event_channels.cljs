(ns graph-paper.event-channels
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [put! chan <!]]))

(enable-console-print!)

(def event-handlers (atom {}))
(def event-stream (chan))

(defn log [event]
  (put! event-stream event))

(defn register-handler! [event-type fn]
  (swap! event-handlers assoc-in [event-type] fn))

(go (while true
      (let [event (<! event-stream)
            type (event :type)
            handler (@event-handlers type)]
        (println "handler: " handler)
        (when handler (handler)))))
