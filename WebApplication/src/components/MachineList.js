import React, { Component } from 'react'
import { Link } from 'react-router-dom'

export class MachineList extends Component {
    //State contains all the variables of the class
    state = { 
        machines: [],
        selectedMachine: {
            ip: "default",
            id: "default"
        }
    };


    //Is forced to complete before render()
    componentDidMount() {
        fetch('http://localhost:8080/api/machines')
        .then(response => response.json())
        .then(data => {
            //Loops through and add all machines to the list of machines
            data.forEach(element => {
                this.setState({ machines: [...this.state.machines, {label: element.ip, value: element.id}] });
            });

            //Sets first machine as default as the list will have it selected initially
            this.setState({selectedMachine: {
                ip: this.state.machines[0].label,
                id: this.state.machines[0].value
            }})
        });
    }

    //When button is pressed we send the 
    selectMachineHandler = () => {
        //Sends the current machine to App.js
        this.props.setCurrentMachine(this.state.selectedMachine)
    }

    //Handler for the selection of a new machine in the list
    change = (e) => {
        let selectedJSON = JSON.parse(e.target.value)

        this.setState({selectedMachine: {
            ip: selectedJSON.label,
            id: selectedJSON.value
        }})
    }
    
    //Contains the HTML that is to be rendered for the user
    render() {
        return (
            <div>
                <h1>Machines:</h1>

                <select 
                        size="10"
                        onChange={this.change}
                >
                    {this.state.machines.map((option) => (
                        <option 
                            value={JSON.stringify(option)}
                            key={option.value}
                        >
                            {option.label} 
                        </option>
                    ))}
                </select>

                <br></br>

                <Link to="/control">
                    <button onClick={this.selectMachineHandler} style={btnStyle}>Connect</button>
                </Link> 
                
            </div>
        )
    }
}

//Styling of the button
const btnStyle = {
    backgroundColor: "#696969",
    border: "1px solid #000",
    display: "inline-block",
    color: "#fff",
    fontSize: "14px",
    fontWeight: "bold",
    padding: "8px 12px",
    margin: "0px 5px",
    textDecoration: "none",
    width: "10%"
}

export default MachineList
