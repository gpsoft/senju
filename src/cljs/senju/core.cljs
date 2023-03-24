(ns senju.core
  (:require
   [ajax.core :as ajax]
   [senju.util :as u]))


(defn init []
  (println (u/tap "cljs!"))
  )

(comment

 (ajax/POST "http://localhost:8080/api/hey/bar"
            {:params {:message "hey"
                      :id 123}
             :format :json
             :handler (fn [r] (u/tap r))
             :response-format :json
             :keywords? true})

 )
