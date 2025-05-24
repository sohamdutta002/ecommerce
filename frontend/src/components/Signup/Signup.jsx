import React, { useState } from 'react'
import Step1Form from './Step1Form';
import Step2Form from './Step2Form';
import { useGetReq, usePostReq } from '../../hooks/useHttp';

const Signup = () => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    password: '',
    confirmPassword: '',
    shippingAddress: '',
    paymentDetails: ''
  });
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value })
  };
  const nextStep = () => setStep(step + 1);
  const prevStep = () => setStep(step - 1);
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const { confirmPassword, ...filteredFormData } = formData;
      const response = await usePostReq("user/register", filteredFormData);
      if (!response.ok) {
        throw new Error(response);
      }
      dispatch({
        type: 'LOGIN',
        payload: {
          user: response.user,
          token: response.token,
          role: response.role,
        }
      });
      localStorage.setItem("token", response.token);
      localStorage.setItem("user", JSON.stringify(response.user));
      localStorage.setItem("role", response.role);
      const data = response.json();
      return data;
    } catch (e) {
      throw new Error(e.message);
    }
  }
  return (
    <div className='flex bg-gray-100 h-[100vh] w-[100vw] justify-center items-center md:text-2xl'>
      <div className="flex flex-col border-2 border-gray-400 p-4 md:p-8 rounded-md shadow-md shadow-gray-500">
        <h2 className='text-2xl md:text-4xl text-center mb-3'>Signup</h2>
        {step === 1 && (<Step1Form formData={formData} handleChange={handleChange} nextStep={nextStep} handleSubmit={handleSubmit} />)}
        {step === 2 && (<Step2Form formData={formData} handleChange={handleChange} prevStep={prevStep} handleSubmit={handleSubmit} />)}
      </div>
    </div>
  )
}

export default Signup
