(defn flip [f] (fn [x y] (f y x)))

(defn same-length? [x]
  (apply == (mapv count x)))

(defn valid-vector? [x]
  (and (vector? x) (every? number? x)))

(defn valid-matrix? [x]
  (and (vector? x) (every? valid-vector? x) (same-length? x)))

(defn same-shape? [& x]
  (and
    (every? vector? x)
    (apply == (mapv (partial count) x))
    (every? (partial mapv same-shape?) x)
    ))

(defn valid-simplex? [x]
  (or
    (number? x)
    (valid-vector? x)
    (and
      (= (range (count x) 0 -1) (mapv count x))
      (every? valid-simplex? x))
    ))

(defn simplex-app [f] (fn [& x]
                        (if (vector? (first x))
                          (apply (partial mapv (simplex-app f)) x)
                          (apply f x))))

(defn ap [pre-cond] #(fn [& x] {:pre [(pre-cond x) (same-length? x)]}
                       (apply mapv % x)))

(def apv
  (ap #(every? valid-vector? %)))

(def apm
  (ap #(every? valid-matrix? %)))

(defn apx [f]
  ((ap #(and (every? valid-simplex? %) (apply same-shape? %))) (simplex-app f)))

(def v+ (apv +))
(def v- (apv -))
(def v* (apv *))
(def vd (apv /))

(defn scalar [& x] {:pre [(every? valid-vector? x)]}
  (reduce + (reduce v* x)))

(defn vect [& xs] {:pre [(every? valid-vector? xs) (every? (partial = 3) (mapv count xs))]}
  (reduce (fn [x y] (vector
                      (- (* (nth x 1) (nth y 2)) (* (nth y 1) (nth x 2)))
                      (- (* (nth y 0) (nth x 2)) (* (nth x 0) (nth y 2)))
                      (- (* (nth x 0) (nth y 1)) (* (nth y 0) (nth x 1))))) xs))

(defn v*s [v & s] {:pre [(valid-vector? v) (every? number? s)]}
  (mapv (partial * (reduce * s)) v))

(def m+ (apm v+))
(def m- (apm v-))
(def m* (apm v*))
(def md (apm vd))

(defn m*s [m & s] {:pre [(valid-matrix? m) (every? number? s)]}
  (mapv (partial (flip v*s) (reduce * s)) m))

(defn m*v [m v] {:pre [(valid-matrix? m) (valid-vector? v)]}
  (mapv (partial (flip scalar) v) m))

(defn transpose [x] {:pre [(valid-matrix? x)]}
  (apply mapv vector x))

(defn m*m [& xs] {:pre [(every? valid-matrix? xs)]}
  (reduce (fn [x y] {:pre [(same-length? [(first x) y])]}
            (transpose (mapv (partial m*v x) (transpose y)))) xs))

(def x+ (apx +))
(def x- (apx -))
(def x* (apx *))
(def xd (apx /))