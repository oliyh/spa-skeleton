# spa-skeleton

A skeleton project for a ClojureScript Single Page Application (SPA) backed by a Swagger API

## Server

The server consists of a Pedestal web server which serves a root for the SPA and a [Swagger API](http://swagger.io/).
You can start it from a new REPL by loading the dev namespace and running start:
```clojure
user> (dev)
dev> (start)
```

## SPA

The SPA is a ClojureScript application which can be built and developed using [Figwheel](https://github.com/bhauman/lein-figwheel).
To build the application and connect to Figwheel, run
```bash
$ lein figwheel
```

Now open http://localhost:8080 to see the front end.
You can make changes to the CLJS source files under `/src/cljs/`, save them, and see the
results hot-loaded into your browser with no refresh required.

## Building and running from uberjar
```bash
$ lein uberjar
$ java -Dnomad.env=dev -jar target/uberjar/spa-skeleton-standalone.jar
```
## License

Copyright © 2016 oliyh

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
