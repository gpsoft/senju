(ns user
  (:require
   clojure.pprint
   [senju.core :as s]))

(defn dev
  []
  (s/go! s/default-config)
  (in-ns 'senju.core))

