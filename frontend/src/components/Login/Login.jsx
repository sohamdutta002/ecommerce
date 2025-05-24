import React, { useContext, useState } from 'react'
import { AuthContext } from '../../context/AuthContext';
import { usePostReq } from '../../hooks/useHttp';
import { useNavigate } from 'react-router-dom';

const Login = () => {
    const navigate = useNavigate();
    const { dispatch } = useContext(AuthContext);
    const [user, setUser] = useState({
        email: '',
        password: '',
        role: 'USER'
    });
    const handleChange = (e) => {
        const { name, value } = e.target;
        setUser({ ...user, [name]: value });
    }
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            // console.log(user);
            const response = await usePostReq("api/user/login", user);
            dispatch({
                type: "LOGIN",
                payload: {
                    user: response.user,
                    token: response.token,
                    role: response.role,
                }
            });
            // console.log(response.user);
            localStorage.setItem("token", response.token);
            localStorage.setItem("user", JSON.stringify(response.user));
            localStorage.setItem("role",response.role);
            navigate("/");
        } catch (e) {
            console.log("Login failed", e);
        }
    }
    return (
        <div className='flex bg-gray-100 h-[100vh] w-[100vw] justify-center items-center md:text-2xl'>
            <div className="rounded-2xl border-2 border-gray-400 p-4 md:p-8 shadow-md shadow-gray-500">
                <h2 className='text-2xl md:text-4xl md:mb-6 text-center font-bold'>Login</h2>
                <form className='flex flex-col p-2 gap-2 md:gap-6' onSubmit={handleLogin}>
                    <div className="flex flex-col gap-2 p-2">
                        <label htmlFor='email' className='text-gray-600 font-medium'>Email</label>
                        <input type='text' name='email' value={user.email} onChange={handleChange} placeholder='Email' className='border-2 border-gray-400 rounded-md p-2' />
                    </div>
                    <div className="flex flex-col gap-2 p-2">
                        <label htmlFor='email' className='text-gray-600 font-medium'>Password</label>
                        <input type='password' name='password' value={user.password} onChange={handleChange} placeholder='Password' className='border-2 border-gray-400 rounded-md p-2' />
                    </div>
                    <button type='submit' className='bg-blue-500 text-white rounded-md p-2 md:p-3 hover:bg-blue-400 cursor-pointer'>Login</button>
                </form>
                <p className='text-center text-sm md:text-lg'>Don't have an account? <a href='/signup' className='text-blue-500 hover:text-blue-400'>Register</a></p>
            </div>
        </div>
    )
}

export default Login
