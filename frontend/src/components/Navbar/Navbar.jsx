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
            Navbar
            {user && (
                <>
                    <span>{user.name}</span>
                    <button onClick={handleLogout}>Logout</button>
                </>
            )}
        </div>
    )
}

export default Navbar
