import React, { useState } from 'react'

const Login = () => {
    const [user,setUser]=useState({
        email:'',
        password:'',
        role:'USER'
    });
    const handleChange=(e)=>{
        const {name,value}=e.target;
        setUser({...user,[name]:value});
    }
    return (
        <div className='flex bg-gray-100 h-[100vh] w-[100vw] justify-center items-center md:text-2xl'>
            <div className="rounded-2xl border-2 border-gray-400 p-4 md:p-8 shadow-md shadow-gray-500">
                <h2 className='text-2xl md:text-4xl md:mb-6 text-center font-bold'>Login</h2>
                <form className='flex flex-col p-2 gap-2 md:gap-6'>
                    <div className="flex flex-col gap-2 p-2">
                        <label htmlFor='email' className=''>Email</label>
                        <input type='text' name='email' value={user.email} onChange={handleChange} placeholder='Username' className='border-2 border-gray-400 rounded-md p-2' />
                    </div>
                    <div className="flex flex-col gap-2 p-2">
                        <label htmlFor='email' className=''>Email</label>
                        <input type='password' name='password' value={user.password} onChange={handleChange} placeholder='Password' className='border-2 border-gray-400 rounded-md p-2' />
                    </div>                   
                    <button className='bg-blue-500 text-white rounded-md p-2 md:p-3 hover:bg-blue-400 cursor-pointer'>Login</button>
                </form>
                <p className='text-center text-sm md:text-lg'>Don't have an account? <a href='/signup' className='text-blue-500 hover:text-blue-400'>Register</a></p>
            </div>
        </div>
    )
}

export default Login
