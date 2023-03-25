(ns senju.subs
  (:require
   [re-frame.core :as r]
   [senju.db :as m]
   ))

(r/reg-sub
  :current-page
  (fn [db _]
    (m/current-page db)))
