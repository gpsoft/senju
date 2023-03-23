(ns senju.util
  (:require
   [clojure.java.io :as io]
   [clojure.edn :as edn]))

(defn tap [v] (prn v) v)

(defn mac-os?
  []
  (let [os-name (System/getProperty "os.name")]
    (.startsWith os-name "Mac")))

(defn read-edn
  [dir-path-str fname-str]
  (let [d (io/file dir-path-str)
        f-str (-> d
                  (.toPath)
                  (.resolve fname-str)
                  (.toString))]
    (if (.exists (io/file f-str))
      (-> f-str
          (slurp)
          (edn/read-string))
      {})))

(defn move-ix
  [ix n forward?]
  (if (zero? n)
    0
    (mod ((if forward? inc dec) ix) n)))

(defn scale-to-contain
  [tow toh fromw fromh]
  (let [rw (/ tow fromw)
        rh (/ toh fromh)
        r (min rw rh)]
    [(int (* fromw r)) (int (* fromh r))]))

