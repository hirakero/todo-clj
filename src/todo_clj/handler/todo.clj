(ns todo-clj.handler.todo
  (:require [bouncer.validators :as v]
            [compojure.core :refer [defroutes context GET POST]]
            [todo-clj.db.todo :as todo]
            [todo-clj.util.response :as res]
            [todo-clj.util.validation :as uv]
            [todo-clj.view.todo :as view]))

(def todo-validator {:title [[v/required :message "enter a todo"]]})

(defn todo-index [req]
  (let [todo-list (todo/find-todo-all)]
    (-> (view/todo-index-view req todo-list)
        res/response
        res/html)))

(defn todo-new [req]
  (-> (view/todo-new-view req)
      res/response
      res/html))

(defn todo-new-post [{:as req :keys [params]}]
  (uv/with-fallback #(todo-new (assoc req :errors %))
    (let [params (uv/validate params todo-validator)]
      (if-let [todo (first (todo/save-todo (:title params)))]
        (-> (res/redirect (str "/todo/" (:id todo)))
            (assoc :flash {:msg "Added todo successfully"})
            res/html)))))

(defn todo-search [req] "TODO search")

(defn todo-show [{:as req :keys [params]}]
  (if-let [todo (todo/find-first-todo (Long/parseLong (:todo-id params)))]
    (-> (view/todo-show-view req todo)
        res/response
        res/html)))

(defn todo-edit [{:as req :keys [params]}]
  (if-let [todo (todo/find-first-todo (Long/parseLong (:todo-id params)))]
    (-> (view/todo-edit-view req todo)
        res/response
        res/html)))

(defn todo-edit-post [{:as req :keys [params]}]
  (uv/with-fallback #(todo-edit (assoc req :errors %))
    (let [params (uv/validate params todo-validator)
          todo-id (Long/parseLong (:todo-id params))]
      (if (pos? (first (todo/update-todo todo-id (:title params))))
        (-> (res/redirect (str "/todo/" todo-id))
            (assoc :flash {:msg "updated todo successflly"})
            res/html)))))

(defn todo-delete [{:as req :keys [params]}]
  (if-let [todo (todo/find-first-todo (Long/parseLong (:todo-id params)))]
    (-> (view/todo-delete-view req todo)
        res/response
        res/html)))

(defn todo-delete-post [{:as req :keys [params]}]
  (let [todo-id (Long/parseLong (:todo-id params))]
    (if (pos? (first (todo/delete-todo todo-id)))
      (-> (res/redirect "/todo")
          (assoc :flash {:msg "successflly deleted todo"})
          res/html))))

(defroutes todo-routes
  (context "/todo" _
    (GET "/" _ todo-index)
    (GET "/new" _ todo-new)
    (POST "/new" _ todo-new-post)
    (GET "/search" _ todo-search)
    (context "/:todo-id" _
      (GET "/" _ todo-show)
      (GET "/edit" _ todo-edit)
      (POST "/edit" _ todo-edit-post)
      (GET "/delete" _ todo-delete)
      (POST "/delete" _ todo-delete-post))))
