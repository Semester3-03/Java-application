import React, { Component } from 'react'
import { Link } from 'react-router-dom'

export class Header extends Component {

    textStyle = () => {
        return {
            color: this.props.machine.ip === "none" ? 
            "#fc0303" : "#00bf06"
        }
    }
    
    render() {
        return (
            <header style={headerStyle}>
                <h1>BrewMES</h1>
                <p>Current machine: <i style={this.textStyle()}>{this.props.machine.ip}</i></p>
                <Link style={linkStyle} to="/">Home</Link>                  
            </header>
        )
    }
}


const headerStyle = {
    background: "#444444",
    color: "#ffffff",
    textAlign: "center",
}

const linkStyle = {
    color: "#ffffff",
    textDecoration: "underline",
    padding: "15px"
}

export default Header


