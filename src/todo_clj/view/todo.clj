(ns todo-clj.view.todo
  (:require [hiccup.form :as hf]
            [todo-clj.view.layout :as layout]))

(defn todo-index-view [req todo-list]
  (->> [:section.card
        (when-let [{:keys [msg]} (:flash req)]
          [:div.alert.alert-success [:strong msg]])
        [:h2 "todo list"]
        [:ul
         (for [{:keys [id title]} todo-list]
           [:li [:a {:href (str "/todo/" id)} title]])]]
        (layout/common req)))

(defn todo-new-view [req]
  (->> [:section.card
        [:h2 "add todo"]
        (hf/form-to
         [:post "/todo/new"]
         [:input {:name :title :placeholder "enter your todo"}]
         [:button.bg-blue "add"])]
       (layout/common req)))

(defn todo-show-view [req todo]
  (let [todo-id (:id todo)]
    (->> [:section.card
          (when-let [{:keys [msg]} (:flash req)]
            [:div.alert.alert-success [:strong msg]])
          [:h2 (:title todo)]
          [:a.wide-link {:href (str "/todo/" todo-id "/edit")} "edit"]
          [:a.wide-link {:href (str "/todo/" todo-id "/delete")} "delete"]]
         (layout/common req))))

(defn todo-edit-view [req todo]
  (let [todo-id (get-in req [:params :todo-id])]
    (->> [:section.card
          [:h2 "todo edit"]
          (hf/form-to
           [:post (str "/todo/" todo-id "/edit")]
           [:input {:name :title :value (:title todo)
                    :placeholder "enter todo"}]
           [:button.bg-blue "update"])]
         (layout/common req))))

(defn todo-delete-view [req todo]
  (let [todo-id (get-in req [:params :todo-id])]
    (->> [:section.card
          [:h2 "todo delete"]
          (hf/form-to
           [:post (str "/todo/" todo-id "/delete")]
           [:p "are you sure?"]
           [:p "*" (:title todo)]
           [:button.bg-red "delete"])]
         (layout/common req))))
