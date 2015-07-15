(ns ^:figwheel-always graph-paper.core-test
  (:require
    [cljs.test :refer-macros [deftest testing is]]
    [graph-paper.test-formatter :as formatter]
    [figwheel.client :as fw]
    [graph-paper.test-helpers :as th]
    [graph-paper.core :as hw]))

(enable-console-print!)

(deftest test-pizza
  (is (= "pizza" "pizza")))

(deftest test-four
  (is (= 1 1)))

(deftest test-five
  (is (= 1 1)))

(deftest test-tacos
  (is (= 1 1)))

(deftest test-graph-paper
  (is (th/found-in #"Hello" (th/by-id "app"))))

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
           })
