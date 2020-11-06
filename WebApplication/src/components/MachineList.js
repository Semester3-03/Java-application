import React, { Component } from 'react'

export class MachineList extends Component {
    //State contains all the variables of the class
    state = { 
        machines: [],
        selectedMachine: "default"
    };


    //Is forced to complete before render()
    componentDidMount() {
        fetch('http://localhost:8080/api/machines')
        .then(response => response.json())
        .then(data => {
            data.forEach(element => {
                this.setState({ machines: [...this.state.machines, {label: element.ip, value: element.id}] });
            });
            console.log(this.state)
        });
    }

    //When button is pressed we send the 
    selectMachineHandler = () => {
        //console.log(this.state.selectedMachine)
        let data = {
            id: this.state.selectedMachine
        }

        fetch("http://localhost:8080/api/currentmachine/", {
            method: "PUT",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.status === 200) {
                window.location.href = '/about';
            }
        })
    }

    change = (e) => {
        this.setState({selectedMachine: e.target.value})
    }

    //Contains everything we wish to render to the user
    render() {
        return (
            <div>
                <h1>Machines:</h1>

                <select 
                        size="10"
                        value={this.state.selectedMachine}
                        onChange={(e) => this.setState({selectedMachine: e.target.value})}
                >
                    <option disabled selected value value="default"> -- select an option -- </option>
                    {this.state.machines.map((option) => (
                        <option 
                            value={option.value}
                            key={option.value}
                        >
                            {option.label} 
                        </option>
                    ))}
                </select>

                <br></br>
                <button onClick={this.selectMachineHandler}>Select machine</button>
            </div>
        )
    }
}




export default MachineList
