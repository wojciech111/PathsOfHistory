import React, { Component } from 'react';
import 'rxjs';
import { Provider } from 'react-redux'
import { ConnectedRouter } from 'react-router-redux';
import store, { history } from './store';

// Styles
import 'bootstrap/dist/css/bootstrap.css';
import './App.css';

// routes
import routes from './routes';

// common components

class App extends Component {

  render() {
    return (
      <Provider store={store}>
        <ConnectedRouter history={history}>
          <div className="App">
            <div className="wrap">
              {routes}
            </div>
          </div>
        </ConnectedRouter>
      </Provider>
    );
  }
}

export default App;
