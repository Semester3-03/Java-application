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
            .then(response => {
                let json = response.json();
                json.then(data => {
                    if (response.status === 200) {
                        console.log("Inside 200 ok if");
                        this.setState({ selectedBatchID: data.id, selectSuccess: true, errorMessage: "" });
                    } else if (response.status === 400) {
                        this.setState({ selectedBatchID: "", selectSuccess: false, errorMessage: "Something went wrong, have you entered a valid UUID?" })
                    } else {
                        console.log(data.response)
                        this.setState({ selectedBatchID: "", selectSuccess: false, errorMessage: data.response });
                    }
                })
            }
            )

    }

    generatePDF = (e) => {
        fetch('http://localhost:8080/api/batches/' + this.state.selectedBatchID + '/generate')
        //.then(response => response.json())
        //.then(data => console.log(data))
    }

    render() {
        let selectedBatchMessage;
        let errorMessage;

        if (this.state.selectSuccess) {
            selectedBatchMessage = <p>Selected batch: {this.state.selectedBatchID}</p>
            errorMessage = <p></p>
        } else {
            selectedBatchMessage = <p>Selected batch: none</p>
            errorMessage = <p style={{ color: "red" }}>{this.state.errorMessage}</p>
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
                    {this.state.selectSuccess === true ? (<button onClick={this.generatePDF}>Generate PDF Report</button>) : (<p></p>)}
                </div>

            </div>
        )
    }
}

export default Batches