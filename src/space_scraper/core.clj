(ns space-scraper.core)

;; (defn main
;;   "I don't do a whole lot."
;;   [x]
;;   (println x "Hello, World!"))

(require '(clojure [string :as string]))
(require '(net.cgrand [enlive-html :as html]))
(use 'incanter.core)
(import [java.net URL])

;;; FIXME We will have to get all the pages eventually!
(def secondpage-url "http://hackerspaces.org/w/index.php?title=Special:Ask&offset=200&limit=500&q=%5B%5BCategory%3AHackerspace%5D%5D%5B%5Bhackerspace+status%3A%3Aactive%5D%5D&p=format%3Dbroadtable%2Fmainlabel%3Dhackerspace&po=%3F%3Dhackerspace%23%0A%3FCountry%0A%3FState%0A%3FCity%0A%3FWebsite%0A%3FLast+Updated%0A&sort=Last+Updated")

(defn to-keyword [input]
  "This takes a string and returns a normalised keyword."
  (keyword (string/replace (string/lower-case input) \space \-)))

(defn load-page [url]
  "Consumes an URL and yields the result as rows."
  (let [html (html/html-resource (URL. url))
        table (html/select html [:table])
        headers (->> (html/select table [:thead :th])
                     (map html/text)
                     (map to-keyword)
                     vec)
        rows (->> (html/select table [:tr])
                  (map #(html/select % [:td]))
                  (map #(map html/text %))
                  (filter seq))]
    (dataset headers rows)))

(defn -main
  "The main function."
  []
  (let [data (load-page secondpage-url)]
    (println (:website (second (second (second data)))))))


(defn -main
  "The main function."
  []
  (let [data (load-page secondpage-url)]
    (println (:website (second (second (second data)))))))


;; (defn -main
;;   "The main function."
;;   []
;;   (let [data (load-page secondpage-url)]
;;     (map (fn [x] (:website x)) (second (second data)))))

(defn -main
  "The main function -- now it shows only the data."
  []
  (let [data (load-page secondpage-url)]
    (println (second (second data)))))
