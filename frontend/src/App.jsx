import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import SignupPage from './pages/SignupPage'
import LoginPage from './pages/LoginPage'
import { Route, Router, Routes } from 'react-router-dom'
import Hello from './components/Hello'
import PublicLayout from './layouts/PublicLayout'
import PrivateLayout from './layouts/PrivateLayout'

function App() {

  return (

    <Routes>
      {/* Public Layout */}
      <Route element={<PublicLayout />}>
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/login" element={<LoginPage />} />
      </Route>
      {/* Private Layout */}
      <Route element={<PrivateLayout />}>
        <Route path='/hello' element={<Hello />} />
      </Route>
    </Routes>

  )
}

export default App
