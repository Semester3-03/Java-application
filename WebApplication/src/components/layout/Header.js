import React from 'react'
import { Link } from 'react-router-dom'

export default function Header() {
    return (
        <header style={headerStyle}>
            <h1>BrewMES</h1>
            <Link style={linkStyle} to="/">Home</Link>
            <Link style={linkStyle} to="/about">About</Link>
            <Link style={linkStyle} to="/contact">Contact</Link> 
        </header>
    )
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