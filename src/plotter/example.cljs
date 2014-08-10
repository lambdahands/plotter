(ns plotter.example
  (:use [plotter.core :only [make-plotter =>]]))

;; Generate RGB noise
(defn rgb-str [a b c]
  (let [[r g b] (map #(mod % 256) [a b c])]
    (str "rgb("r","g","b")")))

(def p (make-plotter js/d3))

(defn props [c]
  [[:select "#content"]
   [:selectAll]
   [:data [(clj->js (take 1000 (repeatedly #(rand-int 255)))) (fn [d] d)]]
   [:enter]
   [:append "div"]
   [:attr ["class" "bar"]]
   [:style ["background" c]]])

(doseq [f [(fn [d] (rgb-str d 0 0))
           (fn [d] (rgb-str 0 d 0))
           (fn [d] (rgb-str 0 0 d))]]
  (=> p (props f)))
