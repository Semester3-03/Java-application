import React, { Component } from 'react'

export class AuthorizationService extends Component {

    state = {
        LoginSucces: false,
        LoginClicked: false,
        username: '',
        password: ''
    };

    //sends a request to the api /login with the user credentials.
    login = (username, password, clicked) => {
        if(clicked){ 
        console.log("login called!!");
        fetch("http://localhost:8080/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username: username, password: password })
        })
            .then(response => {
                if (response.status === 200) {
                    //pulls out the jwt token payload and saves it in localstorage for later use.
                    let token = response.headers.get("Authorization");
                    localStorage.setItem("token", token);
                    this.setState({
                        LoginSucces: true,
                        username: '',
                        password: ''
                    });
                    window.location.href = "/"
                } else {

                }
            })
        }
    };


    signup = (username, password, clicked) => {
        if(clicked){
            console.log("signup called!!");
            fetch("http://localhost:8080/signup", {
                method: "POST",
                headers: { "content-type": "application/json" },
                body: JSON.stringify({ id: 100, username: username, password: password })
            }).then(reponse => {
                if (reponse.status === 200) {
                  //  this.login(username, password, clicked);
                }
            })
        }
    };

    usernameChanged = (e) => {
        console.log("paswword called!!");
        this.setState({ username: e.target.value });
    };
    passwordChanged = (e) => {
        console.log("username called!!");
        this.setState({ password: e.target.value });
    };

    render() {
        return (
        
                <div>
                    <div>
                    <form>
                    <input style={inputStyle} placeholder="Username" onChange={this.usernameChanged} type="text"></input>
                        <br></br>
                       <input style={inputStyle} placeholder="Passowrd" onChange={this.passwordChanged} type="password"></input>
                       </form>
                   <form>
                    <br></br>
                    <br></br>
                    <div style={{ padding: "200px" }}>
                        <button style={btnStyle} onClick={(e) => {e.preventDefault(); this.login(this.state.username, this.state.password, true)}}>login</button>
                        <button style={btnStyle} onClick={(e) => {e.preventDefault(); this.signup(this.state.username, this.state.password, true)}}>signup</button>
                    </div>
                    </form>
                </div>
            </div>
        );
    }



};
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
    width: "10%"
}

const inputStyle = {
    width: "50%",
    padding: "12px 20px",
    margin: "8px 0",
    boxSizing: "border-box",
    border: "none",
    borderBottom: "4px solid grey"
}



export default AuthorizationService