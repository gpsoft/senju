(ns senju.core
  (:require
   [senju.util :as u])
  (:gen-class))

(defn -main [& args]
  (println (u/tap "hey")))

(comment
 (-main)
 )
