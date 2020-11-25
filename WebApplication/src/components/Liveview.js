import React, { Component } from 'react'
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

export class Liveview extends Component {

    state = { 
        machineID: "",
        socket: null,
        stompClient: null
    };

    componentDidMount() {
        let socket = new SockJS('http://localhost:8080/websocket')
        let stompClient = Stomp.over(socket);

        this.setState({machineID: this.props.currentMachine.id}, () => 
            this.setState({socket: socket}, () => 
                this.setState({stompClient: stompClient}, () => 
                    this.connect(this.state.machineID, this.testFunction)
                )
            )
        );
    }

    connect(id, func) {
        let stompClient = this.state.stompClient
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/' + id + '/livedata', function (greeting) {
                //this.getLiveData(JSON.parse(greeting.body).content, id)
                func(greeting.body)
                stompClient.send("/app/connect/" + id, {}, JSON.stringify({'name': "filler value"})); 
            });
        });
    }

    testFunction = (data) => {
        console.log("I TESTFUNCTION:", data)
    }

    buttonPress = (e) => {
        this.state.stompClient.send("/app/connect/" + this.state.machineID, {}, JSON.stringify({'name': "filler value"})); 
    }

    render() {
        return (
            <div>
                <p>asd</p>
                <button onClick={this.buttonPress}>
                    Yoooooooo
                </button>
            </div>
        )
    }
}

export default Liveview
