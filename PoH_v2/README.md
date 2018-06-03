# cra-boilerplate


This project is an [Create React App - v1.1.4](https://github.com/facebookincubator/create-react-app) boilerplate
with integration of Redux, React Router, Redux observable & Reactstrap(Bootstrap v4) and Scss for quick start enterprise level applications.

[![Build Status](https://travis-ci.org/mohandere/cra-boilerplate.svg?branch=master)](https://travis-ci.org/mohandere/cra-boilerplate) [![Dependency Status](https://dependencyci.com/github/mohandere/cra-boilerplate/badge)](https://dependencyci.com/github/mohandere/cra-boilerplate)

### [Demo](https://mohandere.github.io/cra-boilerplate/#/)

Before starting with project, please headover to [CRA](https://github.com/facebook/create-react-app/blob/master/packages/react-scripts/template/README.md
) documentation.


### Supported Integrations

- [React js - ^16.4.0](https://facebook.github.io/react/)
- [Redux js - ^4.0.0-rc.1](http://redux.js.org/)
- [react-router-dom - ^4.2.2](https://github.com/ReactTraining/react-router)
- [react-redux - ^5.0.7](https://github.com/reactjs/react-redux)
- [react-router-dom - ^4.2.2](https://github.com/ReactTraining/react-router/tree/master/packages/react-router-dom)
- [react-router-redux - ^5.0.0-alpha.9](https://github.com/ReactTraining/react-router/tree/master/packages/react-router-redux)
- [redux-observable - ^1.0.0-alpha.2](https://redux-observable.js.org)
- [Rxjs - Rxjs v6](https://github.com/ReactiveX/rxjs)
- [reactstrap - Easy to use React Bootstrap 4 components](https://reactstrap.github.io/)
- [react-loadable - 5.4.0](https://github.com/jamiebuilds/react-loadable)


#### Features

- Scss
- [Domain-style](https://github.com/reactjs/redux/blob/master/docs/faq/CodeStructure.md) for code structure
- Bundle Size analysis
- Code splitting with [react-loadable](https://github.com/jamiebuilds/react-loadable)

## Getting Started

1. Clone this repo

`https://github.com/mohandere/cra-boilerplate.git`

2. To run, go to project folder and run

`$ npm install`
or
`$ yarn install` (if you are using yarn)

3. Now start dev server by running -

`$ npm run start`
or
`$ yarn start`

4. visit - http://localhost:3000/

To create production ready codes -

`$ npm run build`

5. Analyze source code / bundle size

`$ npm run analyze`

for more commands refer `package.json`


## Roadmap

Before starting development please go through -

- [Presentational and Container Components
](https://medium.com/@dan_abramov/smart-and-dumb-components-7ca2f9a7c7d0)
- [All the fundamental React.js concepts, jammed into this single Medium article](https://medium.freecodecamp.org/all-the-fundamental-react-js-concepts-jammed-into-this-single-medium-article-c83f9b53eac2)
- [Tips to learn React + Redux](https://www.robinwieruch.de/tips-to-learn-react-redux/)
- [When do I know I’m ready for Redux?](https://medium.com/dailyjs/when-do-i-know-im-ready-for-redux-f34da253c85f)

## Code structure

Refer `src/home/` module for an ideal directory structure

Project uses `Domain-style` for code structure-

Domain-style : separate folders per feature or domain, possibly with sub-folders per file type

For more details refer `/src/home` folder.

Reference -

- http://redux.js.org/docs/faq/CodeStructure.html
- http://engineering.kapost.com/2016/01/organizing-large-react-applications/

#### Common components

Place all common components such as Header/Footer in `src/common/components` folder.


### Adding new Module/Feature

- Create a Module/Feature folder at `src/`
like - - `src/home`
Feature folder must contain booststrap file named `index.js` and css file 'style.css' at root

Like -

- `src/home/index.js`
- `src/home/style.css`

Next as per need, add sub folders like -

- `src/home/actions/`
- `src/home/reducers/`
- `src/home/epics/`
- `src/home/containers/`
- `src/home/components/`


### Actions

- Create folder named `actions` inside Feature folder like - `src/home/actions`
- Place `actionTypes.js` which contains all actions to be exported

### Reducers

 - Create folder named `reducers` inside Feature folder like - `src/home/reducers`
- Place `index.js` which combines all reducers using `combineReducers`

### Epics

- Create folder named `epics` inside feature/domain folder like - `src/home/epics`
- Place `index.js` which combines all epics using `combineEpics`

## Ajax Handling

This boierplate comes with `rxjs` to handle ajax. Additionally as per need we can use other libs like `axios`.

Using Rxjs DOM api for ajax see file - `rxjs/observable/dom/ajax`
[AjaxObservable](http://reactivex.io/rxjs/file/es6/observable/dom/AjaxObservable.js.html)

## Styling

we are using `scss` Preprocessor. Create a feature/domain specfic scss file, example - `src/home/style.scss`

After compilation the new corresponding CSS file next to it.
example - `src/home/style.css`

Finally you can import that css file in `index.js` file
example - `src/home/index.js` will import `src/home/style.css`

## Analyzing the Bundle Size

We can Analyze and debug JavaScript (or Sass or LESS) code bloat through source maps and [source-map-explorer](https://www.npmjs.com/package/source-map-explorer) great tool for this.

The source map explorer determines which file each byte in your minified code came from. It shows you a treemap visualization to help you debug where all the code is coming from.

To analyzing bundle, run command -

`$ npm run analyze` / `$ yarn analyze`


## Code splitting

Create React App(CRA) by default bundle out entire app into single main.*.js file with Webpack. As our app grows, bundle grows. So instead of downloading the entire app(This hurts the initial load time of our app.) before users can use it, We can split code into small chunks which we can then load on demand and Code splitting is really helpful for larger React apps.

Check out [this](https://mohandere.github.io/mplayground/#/blog/post/2018/03/01/analyzing-the-bundle-size-code-splitting-in-create-react-app/).  example for how to implementation Code splitting and how it helps to load application faster.

This boilerplate using [react-loadable](https://github.com/jamiebuilds/react-loadable) for code splitting.

Open `src/App.js` and edit this line to -

`import routes from './routes';`

 To

`import routes from './asyncRoutes';`

## Deployment

Refer [deployment](https://github.com/facebook/create-react-app/blob/master/packages/react-scripts/template/README.md#deployment) section from CRA doc.


## Something Missing?

File an issue [here](https://github.com/mohandere/cra-boilerplate/issues).


## License

MIT

