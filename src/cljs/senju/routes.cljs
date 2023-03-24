(ns senju.routes
  (:require
   [bidi.bidi :as bidi]
   [pushy.core :as pushy]
   [re-frame.core :as r]))

(def ^{:private true} routes
  ["/" {"" :menu
        "album" :album}])

(def match-route
  (partial bidi/match-route routes))

(defn rev-route
  [handler]
  (bidi/path-for routes handler))
