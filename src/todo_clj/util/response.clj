(ns todo-clj.util.response
  (:require [ring.util.response :as res]))

;;ring.util.response/responseのVarをtodo-clj.util.response/responseに渡して再定義
;;この時、引数の数などのメタ情報が失われるので↓
(def response #'res/response)
;;メタ情報を更新
;;{:arglists ([body]), :doc "Returns a skeletal Ring response with the given body, status of 200, and no\n  headers.", :line 54, :column 1, :file "ring/util/response.clj", :name response, :ns #object[clojure.lang.Namespace 0x21ee1bcb "ring.util.response"]}
(alter-meta! #'response #(merge % (meta #'res/response)))

(defn html [res]
  (res/content-type res "text/html; charset=utf-8"))

(def redirect #'res/redirect)
(alter-meta! #'redirect #(merge % (meta #'res/redirect)))
