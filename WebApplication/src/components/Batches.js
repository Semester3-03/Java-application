import React, { Component } from 'react'

export class Batches extends Component {
    //State contains all the variables of the class
    state = {
        searchVar : ""
    };

    search = (e) => {
        fetch('http://localhost:8080/api/batches/' + document.getElementById("searchField").value)
        .then(data => console.log(data))
    }

    render() {
        return (
            <div>
                <div>
                    <input id="searchField" placeholder="Batch ID"></input> 
                    <button onClick={this.search}>Search</button>
                </div>
               
            </div>
        )
    }
}

export default Batches