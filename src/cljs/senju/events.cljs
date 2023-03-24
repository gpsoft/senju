(ns senju.events
  (:require
   [re-frame.core :as r]
   ))

(r/reg-event-db
  :initialize-db
  (fn  [_ _]
    {:current-page :menu-page
     }))

(r/reg-event-db
  :move-to-page
  (fn [db [_ new-page]]
    (assoc db :current-page new-page)))

