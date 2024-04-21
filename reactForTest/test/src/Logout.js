import React from 'react';
import axios from 'axios';

  
const Logout = () => {
    const logout = () => {
        axios
        .post("http://localhost:8080/tokenCheck", null, { withCredentials: true })
        .then((res) => {
            axios
            .post("http://localhost:8080/logout", null,  { withCredentials: true })
            .then((res) => {
                window.location.href = "http://localhost:3000/";})
                .catch((error) => alert(error));
        })
        .catch((error) => window.location.href = "http://localhost:3000/");
    }

    return (
        <>
            <h1>Logout</h1>
            <button onClick={logout}>Logout</button>
        </>
    );
}


export default Logout;