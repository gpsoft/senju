(ns senju.core
  (:require
   [bidi.ring :as bidi]
   [ring.adapter.jetty :as jetty]
   [ring.util.response :as res]
   [ring.middleware.defaults :refer [wrap-defaults api-defaults site-defaults]]
   [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
   [ring.middleware.cors :refer [wrap-cors]]
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
  (res/response {:result "Okay" :message "Good!"}))

(defn- hey-app
  [ch params]
  (res/response {:res :hey-app :ch ch :msg (:message params)}))

(defn- yo-app
  [ch m]
  (res/response {:res :yo-app :ch ch}))

(defn- api-dispatch
  [m]
  (let [apps {:hey #'hey-app
              :yo #'yo-app}
        {:keys [app ch]} (:route-params m)
        response ((app apps) ch (:body m))]
    (or response res-404)))

(defn- storage
  [m]
  (let [path (get-in m [:route-params :storage-file])]
    (or (res/file-response (str storage-path path))
        res-404)))

(defn- not-found
  [_]
  res-404)

(def site-routes
  [
   "/" [["" (bidi/->Redirect 307 index)]
        ["index.html" index]
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

(def api-routes
  [
   "/api" {"/test" api-test
           ["/" [keyword :app] "/" [keyword :ch]] api-dispatch}
   ])

(defn config
  []
  (->> (u/read-edn "." "config.edn")
       (merge default-config)))

(defn- application
  []
  (let [api-h (-> (bidi/make-handler api-routes)
                  (wrap-json-body {:keywords? true})
                  wrap-json-response
                  (wrap-cors :access-control-allow-origin [#"http://localhost:8280"]  ;; CORS for dev
                             :access-control-allow-methods [:get :put :post])
                  (wrap-defaults api-defaults))
        site-h (-> (bidi/make-handler site-routes)
                   (wrap-defaults site-defaults))]
    (fn [req]
      (if-let [response (api-h req)]
        response
        (site-h req)))))


(defn go!
  ([cfg] (go! cfg false))
  ([{:keys [port] :as cfg} from-main?]
   (let [app (application)
         opts {:port port
               :join? false}]
     (jetty/run-jetty app opts)
     (u/open-browser! (str "http://localhost:" port "/")))))

(defn -main [& args]
  (-> (config)
      (go! true)))

(comment

 (config)
 (go! default-config)
 (clojure.java.io/resource "public/css/reset.css")

 (do (require '[bidi.bidi :refer [match-route]])
     (match-route site-routes "/")
     (match-route site-routes "/index.html")
     (match-route site-routes "/favicon.ico")
     (match-route site-routes "/js/compiled/app.js")
     (match-route site-routes "/css/reset.css"))

 )
