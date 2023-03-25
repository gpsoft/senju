(ns senju.db
  (:require
   [senju.util :as u]))

(def default-db
  {:current-page :menu-page
   })

(defn current-page
  [db]
   (:current-page db))

(defn move-page
  [db page]
  (assoc db :current-page page))
