(ns plotter.core)

(defn chain [e]
  (fn
   ([k] (aget e (name k)))
   ([k d]
    (.bind (aget d (name k)) d))))

(defprotocol IRealize
  (realize [this]))

;; A Grapher that uses mutability.
;; Testing for performance reasons.
#_(deftype Grapher [obj ^:mutable res]
  IRealize
  (realize [this] res)
  IFn
  (invoke [this] (Grapher. obj (res)))
  (invoke [this n]
    (if (nil? res) nil
      (let [m (if (vector? n) (apply res n) (res n))]
        (set! res m)
        this)))
  ILookup
  (-lookup [this n]
    (-lookup this n nil))
  (-lookup [this n not-found]
    (if (nil? res)
      (let [o (chain obj)]
        (Grapher. o (o n)))
      (do (set! res (obj n res)) this))))

(deftype Grapher [obj res]
  IRealize
  (realize [_] res)
  IFn
  (invoke [this] (Grapher. obj (res)))
  (invoke [this n]
    (if (nil? res) nil
      (let [m (if (vector? n) (apply res n) (res n))]
        (Grapher. obj m))))
  ILookup
  (-lookup [this n]
    (-lookup this n nil))
  (-lookup [this n not-found]
    (if (nil? res)
      (let [o (chain obj)]
        (Grapher. o (o n)))
      (Grapher. obj (obj n res)))))


(defn make-plotter [obj]
  (Grapher. obj nil))

(defn plot [plotter [k vs]]
  (if (nil? vs)
    ((k plotter))
    ((k plotter) vs)))

(defn plot- [plotter [k vs]]
  (if (nil? vs)
    ((plotter k))
    ((plotter k) vs)))

(defn => [plotter props]
  (reduce plot plotter props))
