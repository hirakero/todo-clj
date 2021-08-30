(ns todo-clj.view.todo
  (:require [hiccup.form :as hf]
            [todo-clj.view.layout :as layout]))

(defn todo-index-view [req todo-list]
   (->> `([:h1 "todo list"]
        [:ul
         ~@(for [{:keys [title]} todo-list]
             [:li title])])
      (layout/common req)))

(defn todo-new-view [req]
  (->> [:section.card
        [:h2 "add todo"]
        (hf/form-to
         [:post "/todo/new"]
         [:input {:name :title :placeholder "enter your todo"}]
         [:button.bg-blue "add"])]
       (layout/common req)))

(defn todo-complete-view [req]
  (->> [:section.card
        [:h2 "todo added"]]
       (layout/common req)))
