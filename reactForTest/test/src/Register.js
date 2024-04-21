import React, {useState, useEffect} from 'react';
import axios from 'axios';
const Register = () => {
  const [formData, setFormData] = useState({
    nickname: '',
    addr: '',
    birth: '',
    phone: '',
    profile_img: '',
    gender: '',
    genre1: '',
    genre2: '',
    genre3: '',
    is_suspended: '',
    is_secessioned: '',
    // 서버에서 받은 값으로 고정된 값 설정
    email: '',
    username: '',
    name: '',
    role: ''
  });

  useEffect(() => {
    // 서버로부터 고정된 값 받아오기
    async function fetchUserData() {
      try {
        const response = await axios.get('http://localhost:8080/register', {withCredentials : true});
        const userData = response.data;
        setFormData({
          ...formData,
          email: userData.email,
          username: userData.username,
          name: userData.name,
          role: userData.role
        });
      } catch (error) {
        console.error('Failed to fetch user data:', error);
      }
    }

    fetchUserData();
  }, []);

  const handleChange = (e) => {
    console.log(formData)
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const reg = () => {
    console.log(formData);
    axios
    .post("http://localhost:8080/register", formData,  { withCredentials: true}
     )
    .then((res) => {
      window.location.href = "http://localhost:3000/main"
    })
    .catch((error) => 
      console.error(error))
  };



    return (
      <div>
      <h2>회원 가입</h2>
        <div>
          <label>Nickname:</label>
          <input type="text" name="nickname" value={formData.nickname} onChange={handleChange} />
        </div>
        <div>
          <label>Address:</label>
          <input type="text" name="addr" value={formData.addr} onChange={handleChange} />
        </div>
        <div>
          <label>Birth:</label>
          <input type="datetime-local" name="birth" value={formData.birth} onChange={handleChange} />
        </div>
        <div>
          <label>Phone:</label>
          <input type="text" name="phone" value={formData.phone} onChange={handleChange} />
        </div>
        <div>
          <label>Profile Image:</label>
          <input type="text" name="profile_img" value={formData.profile_img} onChange={handleChange} />
        </div>
        <div>
          <label>Gender:</label>
          <input type="text" name="gender" value={formData.gender} onChange={handleChange} />
        </div>
        <div>
          <label>Genre1:</label>
          <input type="text" name="genre1" value={formData.genre1} onChange={handleChange} />
        </div>
        <div>
          <label>Genre2:</label>
          <input type="text" name="genre2" value={formData.genre2} onChange={handleChange} />
        </div>
        <div>
          <label>Genre3:</label>
          <input type="text" name="genre3" value={formData.genre3} onChange={handleChange} />
        </div>
        {/* 고정된 값은 화면에 표시하지 않음 */}
        <input type="text" name="email" value={formData.email} readOnly/>
        <input type="text" name="username" value={formData.username} readOnly/>
        <input type="text" name="name" value={formData.name} readOnly />
        <input type="text" name="role" value={formData.role} readOnly/>
        <button onClick={reg}>가입하기</button>
    </div>
    );
  }
  
export default Register;