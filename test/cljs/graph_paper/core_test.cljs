(ns ^:figwheel-always graph-paper.core-test
  (:require
    [cljs.test :refer-macros [deftest testing is]]
    [graph-paper.test-formatter :as formatter]
    [figwheel.client :as fw]
    [graph-paper.helpers :as h]
    [dommy.core :refer-macros [sel sel1]]
    [graph-paper.core :as hw]
    [graph-paper.grid :as grid]
    [graph-paper.figures :as figs]))

(enable-console-print!)

(deftest test-graph-paper
  (is (h/found-in #"welcome" (sel1 "#app"))))

(deftest test-renders-canvas
  (is (= 1 (count (sel ".graph-paper-grid")))))

(deftest test-abs
  (is (= 5 (h/abs -5)))
  (is (= 5 (h/abs 5))))

(deftest test-making-simple-horiz-lines
  (let [line (figs/line [0 0] [1 0])]
    (is (= "-" (line [0 0])))
    (is (= "-" (line [1 0]))))
  (let [line (figs/line [0 0] [4 0])]
    (is (= "-" (line [0 0])))
    (is (= "-" (line [1 0])))
    (is (= "-" (line [2 0])))
    (is (= "-" (line [3 0])))
    (is (= "-" (line [4 0])))
    ))

(deftest test-making-simple-vertical-lines
  (let [line (figs/line [0 0] [0 2])]
    (is (= "|" (line [0 0])))
    (is (= "|" (line [0 1])))
    (is (= "|" (line [0 2])))
    ))

(deftest test-making-hooked-lines
  (let [line (figs/line [0 0] [2 2])]
    (is (= "-" (line [0 0])))
    (is (= "-" (line [1 0])))
    (is (= "-" (line [2 0])))
    (is (= "|" (line [2 1])))
    (is (= "|" (line [2 2])))
    ))


;;SETUP
(defn run-tests []
  (.clear js/console)
  (cljs.test/run-all-tests #"graph-paper.*-test"))
(run-tests)

;; FW connection is optional in order to simply run tests,
;; but is needed to connect to the FW repl and to allow
;; auto-reloading on file-save
(fw/start {
           :websocket-url "ws://localhost:3449/figwheel-ws"
           ;; :autoload false
           :build-id "test"
           :js-load (fn [] (println "JSLoad connected"))
           })
