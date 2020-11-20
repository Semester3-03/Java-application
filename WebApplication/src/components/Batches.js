import React, { Component } from 'react'

export class Batches extends Component {
    //State contains all the variables of the class
    state = {
        searchVar: "",
        selectedBatchID: "",
        selectSuccess: false,
        errorMessage: ""
    };

    search = (e) => {
        fetch('http://localhost:8080/api/batches/' + document.getElementById("searchField").value)
        .then(response => response.json())    
        .then(data => {
                if (data.status === 200) {
                    let body = JSON.parse(data.body);
                    this.setState({ selectedBatchID: body.respose, selectSuccess: true, errorMessage: "" });
                } else if (data.status === 400) {
                    this.setState({ selectedBatchID: "", selectSuccess: false, errorMessage: "Something went wrong, have you entered a valid UUID?" })
                } else {
                    //REBASE AND CHANGE RESPOSE TO RESPONSE
                    this.setState({ selectedBatchID: "", selectSuccess: false, errorMessage: data.respose});
                }
            })
    }

    generatePDF = (e) => {
        fetch('http://localhost:8080/api/batches/' + this.state.selectedBatchID + '/generate')
    }

    render() {
        let selectedBatchMessage;
        let errorMessage;

        if (this.state.selectSuccess) {
            selectedBatchMessage = <p>Selected batch: {this.state.selectedBatchID}</p>
            errorMessage = <p></p>
        } else {
            selectedBatchMessage = <p>Selected batch: none</p>
            errorMessage = <p style={{color: "red"}}>{this.state.errorMessage}</p>
        }

        return (
            <div>
                <div>
                    {selectedBatchMessage}
                </div>
                <div>
                    {errorMessage}
                </div>
                <div>
                    <input id="searchField" placeholder="Batch ID"></input>
                    <button onClick={this.search}>Search</button>
                </div>
                <div>
                    <button onClick={this.generatePDF}>Generate PDF Report</button>
                </div>

            </div>
        )
    }
}

export default Batches