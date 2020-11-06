import React, { Component } from 'react'

export class Sample1 extends Component {
    render() {
        return (
            <form>
                <label for="fname">First name:</label> <br></br>
                <input type="text" id="fname" name="fname"/> <br></br>
                <label for="lname">Last name:</label> <br></br>
                <input type="text" id="lname" name="lname"/>
            </form>
        )
    }
}

export default Sample1
