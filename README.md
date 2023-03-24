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
  :Connect 5876

$ clj -M -m senju.core

$ clj -T:build clean
$ clj -T:build uber
```

### ClojureScript

```
$ npm install
$ npm run watch
$ vim src/cljs/senju/core.cljs
  :Connect 5877 . | CljEval (shadow/repl :app)

$ npm run release
```
