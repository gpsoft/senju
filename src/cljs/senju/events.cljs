(ns senju.events
  (:require
   [re-frame.core :as r]
   [senju.db :as m]
   [senju.util :as u]
   ))

(r/reg-event-db
  :initialize-db
  (fn  [_ _] m/default-db))

(r/reg-event-db
  :move-to-page
  (fn [db [_ new-page]]
    (m/move-page db new-page)))

