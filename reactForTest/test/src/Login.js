import React from 'react';


const onNaverLogin = () => {

    window.location.href = "http://localhost:8080/oauth2/authorization/naver"
  }
  
  const onGoogleLogin = () => {
  
    window.location.href = "http://localhost:8080/oauth2/authorization/google"
  }
  
  function Login() {
    return (
        <>
            <h1>Login</h1>
            <button onClick={onNaverLogin}>naver login</button>
            <button onClick={onGoogleLogin}>google login</button>
        </>
    );
  }
  
export default Login;