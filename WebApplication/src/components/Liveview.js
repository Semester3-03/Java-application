import React, { Component } from 'react'
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

export class Liveview extends Component {

    state = { 
        machineID: "",
        socket: null,
        stompClient: null, 
        livedata: {}
    };

    componentDidMount() {
        //Initialize socket and stomp client.
        let socket = new SockJS('http://localhost:8080/websocket')
        let stompClient = Stomp.over(socket);

        //Save machineID -> then socket -> then stomp client -> then connect
        this.setState({machineID: this.props.currentMachine.id}, () => 
            this.setState({socket: socket}, () => 
                this.setState({stompClient: stompClient}, () => 
                    this.connect(this.state.machineID, this.storeData)
                )
            )
        );
    }

    //Connect method that subscribes to the live data
    connect(id, func) {
        let stompClient = this.state.stompClient
        //Start connection
        stompClient.connect({}, function (frame) {
            //Send initial info to backend to start the sending of data
            stompClient.send("/app/connect/" + id, {}, JSON.stringify({'name': "filler value"})); 
            //Start subscription (when we get data we send random data which triggers the backend to send data)
            //Essentially we just loop through this forever.
            stompClient.subscribe('/topic/' + id + '/livedata', function (data) {
                //Send new data to function storeData
                func(data.body)
                //Send random info to backend to "trigger" it to send a few set of data.
                stompClient.send("/app/connect/" + id, {}, JSON.stringify({'name': "filler value"})); 
            });
        });
    }

    //Convert data to json and store it in the state variable
    storeData = (data) => {
        let json = JSON.parse(data);
        if (this.state.livedata !== json)
            this.setState({livedata: json});
    }

    render() {
        return (
            // TODO: ADD LIVEDATA VIEW HERE.
            // DATA CAN BE FOUND IN THE VARIABLE STATE.
            // GET IT WITH this.state.livedata.{variablename}
            <div>
               
            </div>
        )
    }
}

export default Liveview
