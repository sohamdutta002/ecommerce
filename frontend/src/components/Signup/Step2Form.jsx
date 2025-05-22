import React from 'react'

const Step2Form = ({formData,handleChange,prevStep,handleSubmit}) => {
    return (
        <div>
            <form className='flex flex-col gap-4'>
                <div className="flex flex-col gap-2">
                    <label htmlFor='shippingAddress' className='text-gray-600 font-medium'>Shipping Address</label>
                    <input type='text' name='shippingAddress' placeholder='Shipping Address' value={formData.shippingAddress} onChange={handleChange} className='p-2 border-2 border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400' required />
                </div>
                <div className="flex flex-col gap-2">
                    <label htmlFor='shippingAddress' className='text-gray-600 font-medium'>Payment Details</label>
                    <input type='text' name='paymentDetails' placeholder='Payment Details' value={formData.paymentDetails} onChange={handleChange} className='p-2 border-2 border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400' required />
                </div>
                
                
                <button onClick={prevStep} className='bg-blue-500 text-white rounded-md hover:bg-blue-400 cursor-pointer p-2 md:p-3'>Back</button>
                <button onClick={handleSubmit} className='bg-green-500 text-white rounded-md hover:bg-green-400 cursor-pointer p-2 md:p-3'>Submit</button>
            </form>
        </div>
    )
}

export default Step2Form
