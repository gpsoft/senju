(ns senju.styles
  (:require
    [spade.core   :refer [defglobal]]))

(def color-main "hsl(24deg 70% 90%)")
(def color-accent "hsl(24deg 85% 58%)")
(def color-text-main  "hsl(24deg 90% 20%)")

(defglobal defaults
  [:body
   {:color               color-text-main
    :background-color    color-main
    :font-size :16px
    }]
  [:#app
   {:margin :8px
    ; :border (str "1px solid " color-accent)
    }]
  [:.main-container
   {:padding :8px
    ; :background-color color-accent
    }]
  )
