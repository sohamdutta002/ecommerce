import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import SignupPage from './pages/SignupPage'
import LoginPage from './pages/LoginPage'
import { Route, Router, Routes } from 'react-router-dom'

function App() {

  return (

    <Routes>
      <Route path="/signup" element={<SignupPage />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>

  )
}

export default App
