(ns senju.core
  (:require
   [bidi.ring :refer [make-handler ->Redirect ->ResourcesMaybe ->Files]]
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
  ["/" {;"" (->Redirect 307 index)
        ;"index.html" #'index
        "" (->ResourcesMaybe {:prefix "public/"})
        ; FIX: how to redirect "/" to public/index.html in the resource?
        "api/" {"test" #'api-test}
        "pics/" (->Files {:dir "/tmp/pics"})
        ; "css/" (->Resources {:prefix "public/css/"})
        ; "img/" (->Resources {:prefix "public/img/"})
        ; "js/" (->Resources {:prefix "public/js/"})
        true #'not-found
        }
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
 (clojure.java.io/resource "public/css/reset.css")

 )
