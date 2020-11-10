import React, { Component } from 'react'

export class Control extends Component {

    //Handler method for the buttons.
    buttonPress = (e) => {
        //Created a JSON object with "command: {the command stored on the respective button}"
        let data = {
            command: e.target.value
        }

        //Sends the HTTP request to the API
        fetch("http://localhost:8080/api/machines/" + this.props.currentMachine.id + "/command", {
            method: "PUT",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
    }

    //Contains the HTML that is to be rendered for the user
    render() {
        return (
            <div>
                <button 
                    style={btnStyle} 
                    value="start"
                    onClick={this.buttonPress}
                >
                    Start
                </button>
                <button 
                    style={btnStyle} 
                    value="stop"
                    onClick={this.buttonPress}
                >
                    Stop
                </button>
                <button 
                    style={btnStyle} 
                    value="reset"
                    onClick={this.buttonPress}
                >
                    Reset
                </button>
                <button 
                    style={btnStyle} 
                    value="clear"
                    onClick={this.buttonPress}
                >
                    Clear
                </button>
                <button 
                    style={btnStyle} 
                    value="abort"
                    onClick={this.buttonPress}
                >
                    Abort
                </button>
            </div>
        )
    }
}

//Styling for the buttons
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
}

export default Control
