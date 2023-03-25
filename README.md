# Senju(千手)

## Ideas

- Electron的なやつ
- ClojureでWeb API
- ClojureScriptでUI

## 開発

### 登場人物

- deps.edn
- nrepl
- node.js
- shadow-cljs
- re-frame
- garden

### Clojure

```
$ clj -M:dev
$ vim src/clj/senju/core.clj
  :Connect 5876 src/clj

$ clj -M -m senju.core
```

### ClojureScript

```
$ npm install
$ npm run watch
$ firefox http://localhost:8280
$ vim src/cljs/senju/core.cljs
  :Connect 5877 src/cljs | CljEval (shadow/repl :app)
```

### Release

```
$ npm run release
$ clj -T:build clean
$ clj -T:build uber
```
