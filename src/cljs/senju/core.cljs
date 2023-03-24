(ns senju.core
  (:require
   [pushy.core :as pushy]
   [ajax.core :as ajax]
   [reagent.dom :refer [render]]
   [re-frame.core :as r]
   [dommy.core :as dom]
   [senju.routes :as routes]
   [senju.views :as views]
   senju.events
   senju.subs
   [senju.util :as u]))

(defn init-routes!
  []
  (->>
   routes/match-route
   (pushy/pushy
    (fn [matched]
      (let [page (keyword (str (name (:handler matched)) "-page"))]
        (r/dispatch [:move-to-page page]))))
   pushy/start!))

(defn init []
  (init-routes!)
  (r/dispatch-sync [:initialize-db])
  (r/clear-subscription-cache!)
  (render [views/main-ui]
          (dom/sel1 :#app)))

(comment

 (ajax/POST "http://localhost:8080/api/hey/bar"
            {:params {:message "hey"
                      :id 123}
             :format :json
             :handler (fn [r] (u/tap r))
             :response-format :json
             :keywords? true})

 )
