import React from 'react'

const Step1Form = ({ formData, handleChange, nextStep, handleSubmit }) => {

    const isValidEmail=()=>{
        const emailRegex=/^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(formData.email);
    }
    const checkPassword = () => {
        return formData.password === formData.confirmPassword && formData.password !== '';
    }
    const checkDetails = () => {
        return formData.name !== '' && formData.email !== '' && checkPassword()
    }
    let role = 'user'
    return (
        <form className='flex flex-col gap-4'>
            <div className="flex flex-col gap-2">
                <label htmlFor='name' className='text-gray-600 font-medium'>Name</label>
                <input type='text' id='name' name='name' placeholder='Name' value={formData.name} onChange={handleChange} className='p-2 border-2 border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400' required />
            </div>
            <div className="flex flex-col gap-2">
                <label htmlFor='email' className='text-gray-600 font-medium'>Email</label>
                <input type='email' id='email' name='email' placeholder='Email' value={formData.email} onChange={handleChange} className={`p-2 border-2 border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400 ${!isValidEmail()&&formData.email?'border-red-500 focus:ring-neutral-50':''}`} required />
            </div>
            <div className="flex flex-col gap-2">
                <label htmlFor='password' className='text-gray-600 font-medium'>Password</label>
                <input type='text' id='password' name='password' placeholder='Password' value={formData.password} onChange={handleChange} className='p-2 border-2 border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400' required />
            </div>
            <div className="flex flex-col gap-2">
                <label htmlFor='confpass' className='text-gray-600 font-medium'>Confirm Password</label>
                <input type='password' id='confpass' name='confirmPassword' placeholder='Confirm Password' value={formData.confirmPassword} onChange={handleChange} className='p-2 border-2 border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400' required />
            </div>

            {!checkPassword() && formData.confirmPassword && (<p className='text-red-500 text-sm'>Passwords do not match</p>)}
            {role === 'user' && <button onClick={nextStep} disabled={!checkDetails()} className={`rounded-md font-medium p-2 md:p-3 ${checkDetails() ? 'bg-blue-500 text-white hover:bg-blue-400 cursor-pointer' : 'bg-gray-300 text-gray-500 cursor-not-allowed'}`}>Next</button>}
            {role === 'admin' && <button onClick={handleSubmit} disabled={!checkDetails()} className={`rounded-md font-medium p-2 md:p-3 ${checkDetails() ? 'bg-green-500 text-white hover:bg-green-400 cursor-pointer' : 'bg-gray-300 text-gray-500 cursor-not-allowed'}`}>Submit</button>}
        </form>
    )
}

export default Step1Form
