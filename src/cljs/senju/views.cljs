(ns senju.views
  (:require
   [re-frame.core :as r]
   [senju.routes :refer [rev-route]]
   [senju.styles :as styles]
   [senju.album.views :refer [album-main]]
   [senju.util :as u]
   ))

(defn- current-page
  []
  (let [curpage (r/subscribe [:current-page])]
    (fn []
      (case @curpage
        :menu-page [:div
                    [:h1 "メニュー"]
                    [:div.menu-wrap
                     [:a.menu-item
                      {:href (rev-route :album)} "・写真アルバム"]]]
        :album-page [album-main]
        [:div "Unexpected page"]))))

(defn main-ui
  []
  [:div.main-container
   [current-page]])
