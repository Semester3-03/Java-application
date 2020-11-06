import React, { Component } from 'react';
import { BrowserRouter as Router, Route} from 'react-router-dom';
import Header from "./components/layout/Header";
import About from './components/pages/About';
import Sample1 from './components/Sample1';
import Sample2 from './components/Sample2';
import MachineList from './components/MachineList';

import './App.css';

export class App extends Component {
	render() {
		return (
			<Router>
				<div className="App">
					<Header />
					<div className="container">
						<Route exact path="/" render={props => (
							<React.Fragment>
								<MachineList />
							</React.Fragment>
						)} />
						<Route exact path="/contact" render={props => (
							<React.Fragment>
								<Sample1 />
								<Sample2 />
							</React.Fragment>
						)} />

						<Route path="/about" component={About} />

					</div>
				</div>
			</Router>
		)
	}
}

export default App
