import React, { Component } from 'react'

export class Control extends Component {

    buttonPress = (e) => {
        let data = {
            command: e.target.value
        }

        fetch("http://localhost:8080/api/machines/" + this.props.currentMachine.id + "/command", {
            method: "PUT",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
    }

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
