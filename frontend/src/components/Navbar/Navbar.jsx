import React, { useContext } from 'react'
import { AuthContext } from '../../context/AuthContext'
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
    const { logout, user } = useContext(AuthContext);
    const navigate = useNavigate();
    console.log(user);
    const handleLogout = () => {
        logout();
        navigate('/login');
    }
    return (
        <div>
            {user && (
                <div className='bg-purple-400 flex justify-between items-center p-2'>
                    <span>{user.name}</span>
                    <button onClick={handleLogout} className='p-2 bg-blue-500 rounded-md hover:bg-blue-400 hover:cursor-pointer'>Logout</button>
                </div>
            )}
        </div>
    )
}

export default Navbar
