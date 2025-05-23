import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import SignupPage from './pages/SignupPage'
import LoginPage from './pages/LoginPage'
import { Route, Router, Routes } from 'react-router-dom'
import Hello from './components/Hello'

function App() {

  return (

    <Routes>
      <Route path="/signup" element={<SignupPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path='/hello' element={<Hello/>} />
    </Routes>

  )
}

export default App
