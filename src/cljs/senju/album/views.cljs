(ns senju.album.views
  (:require
   [re-frame.core :as r]
   [senju.routes :refer [rev-route]]
   ))

(defn album-main
  []
  (fn []
    [:div
     [:h2 "アルバム一覧"]
     [:div.album-list-wrap
      [:div.album-list-item
       [:input
        {:type "button"
         :value "20230101_album1"}]]
      [:div.album-list-item
       [:input
        {:type "button"
         :value "20230101_album2"}]]]
     [:a {:href (rev-route :menu)} "メニューへ戻る"]]))
