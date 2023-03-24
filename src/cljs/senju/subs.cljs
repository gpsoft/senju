(ns senju.subs
  (:require
   [re-frame.core :as r]
   ))

(r/reg-sub
  :current-page
  (fn [db _]
    (:current-page db)))
