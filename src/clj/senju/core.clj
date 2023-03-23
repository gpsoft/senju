(ns senju.core
  (:require
   [bidi.ring :refer [make-handler ->Redirect]]
   [ring.adapter.jetty :as jetty]
   [ring.util.response :as res]
   [clojure.java.io :as io]
   [senju.util :as u])
  (:gen-class))

(def default-config
  {:port 8080
   })

(defn- index
  [_]
  (res/response "Index!"))

(defn- api-test
  [_]
  {:status 200
   :headers {}
   :body "Good"})

(defn- not-found
  [_]
  {:status 404})

(def routes
  ["/" {"" (->Redirect 307 index)
        "index.html" #'index
        "api/" {"test" #'api-test}
        true #'not-found}
   ; "/resources" (->ResourcesMaybe {:prefix "public/"})
   ; "pics/" (->Files {:dir "/tmp/pics"})
   ])

(defn config
  []
  (->> (u/read-edn "." "config.edn")
       (merge default-config)))

(defn application
  []
  (-> (make-handler routes)
      #_wrap-hoge))

(defn go
  ([cfg] (go cfg false))
  ([{:keys [port] :as cfg} from-main?]
   (let [app (application)
         opts {:port port
               :join? false}]
     (jetty/run-jetty app opts))))

(defn -main [& args]
  (-> (config)
      (go true)))

(comment

 (config)
 (go (config))

 )
