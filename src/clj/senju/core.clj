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

(def res-404 (res/not-found ""))

(def storage-path "/tmp/pics/")

(defn- index
  [_]
  (or (res/resource-response "public/index.html")
      res-404))

(defn- api-test
  [_]
  {:status 200
   :headers {}
   :body "Good"})

(defn- storage
  [m]
  (let [path (get-in m [:route-params :storage-file])]
    (or (res/file-response (str storage-path path))
        res-404)))

(defn- not-found
  [_]
  res-404)

(def routes
  [
   "/" [["" (bidi/->Redirect 307 index)]
        ["index.html" index]
        ["api/" {"test" api-test}]
        [["storage/" :storage-file] storage]
        ["css/" (bidi/->Resources {:prefix "public/css/"})]
        ["img/" (bidi/->Resources {:prefix "public/img/"})]
        ["js/" (bidi/->Resources {:prefix "public/js/"})]
        ;; FIX: 冗長?
        ;; やりたいことは…
        ;; ・publicの下のリソースは /css/reset.css のようなURLで公開
        ;; ・/index.htmlもリソース
        ;; ・"/"は"index.html"へリダイレクト
        ["favicon" (bidi/->Resources {:prefix "public/favicon"})]
        ;; FIX: トリッキー
        ;; favicon.icoをリソースから探してほしいんだけど…
        [true not-found]]
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
     (match-route routes "/")
     (match-route routes "/index.html")
     (match-route routes "/favicon.ico")
     (match-route routes "/js/compiled/app.js")
     (match-route routes "/css/reset.css"))

 )
