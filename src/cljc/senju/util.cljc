(ns senju.util
  (:require
   #?(:clj [clojure.java.io :as io])
   #?(:clj [clojure.pprint :as pprint]
      :cljs [cljs.pprint :as pprint])
   [clojure.edn :as edn]))

(defn tap [v] (pprint/pprint v) v)

#?(:clj (defn mac-os?
          []
          (let [os-name (System/getProperty "os.name")]
            (.startsWith os-name "Mac"))))

#?(:clj (defn read-edn
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
              {}))))

(defn move-ix
  [ix n forward?]
  (if (zero? n)
    0
    (mod ((if forward? inc dec) ix) n)))
