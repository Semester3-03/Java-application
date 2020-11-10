import React, { Component } from 'react';
import { BrowserRouter as Router, Route} from 'react-router-dom';
import Header from "./components/layout/Header";
import MachineList from './components/MachineList';
import Control from './components/Control';
import './App.css';

export class App extends Component {
	state = { 
		currentMachine: {
			ip: "none",
			id: ""
		}
    };


	setCurrentMachine = (machine) => {
		this.setState({ currentMachine: machine });
	}

	render() {
		return (
			<Router>
				<div className="App">
					<Header 
						machine={this.state.currentMachine}
					/>
					<div className="container">
						
						<Route exact path="/" render={props => (
							<React.Fragment>
								<MachineList 
									setCurrentMachine={this.setCurrentMachine}
								/>
							</React.Fragment>
						)} />

						<Route exact path="/control" render={props => (
							<React.Fragment>
								<Control 
									currentMachine={this.state.currentMachine}
								/>
							</React.Fragment>
						)} />

					</div>
				</div>
			</Router>
		)
	}
}

export default App
