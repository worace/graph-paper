### Graph Paper - ASCII Drawing Tool

This (very work in progress) app is a playground for exploring
drawing in a web browser using basic ascii shapes. Implemented
using Clojurescript and Reagent (an interface to React).

#### To Run

Install dependencies with:

```
lein deps
```

The project includes 2 builds -- `dev` for building just the app itself and `test` for the test suite.

Run just dev with:

```
lein figwheel dev
```

or just test:

```
lein figwheel test
```

or both:

```
lein figwheel dev test
```

View the app at `http://localhost:3449/` and the tests at `http://localhost:3449/test.html`.

(`3449` is the default port, but this is configurable under the
`:figwheel` settings in `project.clj`)

__Test Display__

Tests run in the browser at [http://localhost:3449/test.html](http://localhost:3449/test.html).

Currently test output is just displayed in the JS dev console. Maybe
will add some HTML display at some point.

### Design Todos / Notes

* Text rendering: SVG seems preferable (also experimented with canvas) since
it can be natively integrated with React

__Data / State Storage__

* 1 big string?
* seq of row strings?
* matrix of rows and chars?
* map of [x y] => char?

__Click Handling__

* [ ] Detect clicks on SVG
* [ ] Determine click location format (probably x,y?)
* [ ] How to map click locations to chars in the grid structure

__Drawing Tools__

* [ ] Lines (90-degrees? Diagonal?)
* [ ] Squares
* [ ] Text
* [ ] Selection tool


### Credit

Thanks to @bhauman for figwheel! Most of the ideas in this example were
cobbled together from his [crashverse](https://github.com/bhauman/crashverse) example
or from various figwheel readmes I found.
