# spa-skeleton

A skeleton project for a ClojureScript Single Page Application (SPA) backed by a Swagger API.
The aim is to provide basic wiring from which an application can be rapidly developed.

## Clojure and ClojureScript, side by side

Include the following in your emacs init.el:
```
(custom-set-variables
 '(cider-cljs-repl
   "(do (use 'figwheel-sidecar.repl-api) (start-figwheel!) (cljs-repl))"))
```

In the older versions of CIDER the variable is called `cider-cljs-repl` instead.

To start both REPLs simultaneously press `C-c M-J`.
Figwheel will be started and a piggieback connection into the browser's Javascript VM will be automatically established.

## Server

The server consists of a Pedestal web server which serves a root for the SPA and a [Swagger API](http://swagger.io/) provided by [pedestal-api](https://github.com/oliyh/pedestal-api).
You can start it from a new Clojure REPL by loading the dev namespace and running start:
```clojure
user> (dev)
dev> (start)
```

## SPA

The SPA is a ClojureScript application which can be built and developed using [Figwheel](https://github.com/bhauman/lein-figwheel).
Once the server is started, open http://localhost:8080 to connect the ClojureScript REPL. You should see the following in the CLJS REPL:
```clojure
Prompt will show when Figwheel connects to your application
To quit, type: :cljs/quit
;; => nil
cljs.user>
```
You can make changes to the CLJS source files under `/src/cljs/`, save them, and see the
results hot-loaded into your browser with no refresh required. You can evaluate code in the CLJS REPL just like you would in a CLJ REPL.

## Building and running from uberjar
```bash
$ lein uberjar
$ java -Dnomad.env=dev -jar target/uberjar/spa-skeleton-standalone.jar
```
## License

Copyright © 2016 oliyh

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
