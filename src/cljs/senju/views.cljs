(ns senju.views
  (:require
   [re-frame.core :as r]
   [senju.routes :refer [rev-route]]
   [senju.styles :as styles]
   ))

(defn- current-page
  []
  (let [curpage (r/subscribe [:current-page])]
    (fn []
      (case @curpage
        :menu-page [:div
                    [:h1 "Menu"]
                    [:a {:href (rev-route :album)} "view photo album"]]
        :album-page [:div
                     [:p "Showing photo album"]
                     [:a {:href (rev-route :menu)} "back to menu"]]
        [:div "Unexpected page"]))))

(defn main-ui
  []
  [:div.main-container
   [current-page]])
