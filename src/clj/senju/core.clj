(ns senju.core
  (:require
   [bidi.ring :as bidi]
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
  (res/response (slurp (io/resource "public/index.html"))))

(defn- api-test
  [_]
  {:status 200
   :headers {}
   :body "Good"})

(defn- not-found
  [_]
  {:status 404})

(def routes
  [
   "/" {"" (bidi/->Redirect 307 index)
        "index.html" #'index
        "api/" {"test" #'api-test}
        "pics/" (bidi/->Files {:dir "/tmp/pics"})
        "css/" (bidi/->Resources {:prefix "public/css/"})
        "img/" (bidi/->Resources {:prefix "public/img/"})
        "js/" (bidi/->Resources {:prefix "public/js/"})
        ;; FIX:
        ;; イマイチ。やりたいことは…
        ;; ・publicの下のリソースは /css/reset.css のようなURLで公開
        ;; ・/index.htmlもリソース
        ;; ・"/"は"index.html"へリダイレクト
        true #'not-found
        }
   ])

(defn config
  []
  (->> (u/read-edn "." "config.edn")
       (merge default-config)))

(defn- application
  []
  (-> (bidi/make-handler routes)
      #_wrap-hoge))


(defn go!
  ([cfg] (go! cfg false))
  ([{:keys [port] :as cfg} from-main?]
   (let [app (application)
         opts {:port port
               :join? false}]
     (jetty/run-jetty app opts))))

(defn -main [& args]
  (-> (config)
      (go! true)))

(comment

 (config)
 (go! default-config)
 (clojure.java.io/resource "public/css/reset.css")

 (do (require '[bidi.bidi :refer [match-route]])
     (match-route routes "/index.html"))

 )
